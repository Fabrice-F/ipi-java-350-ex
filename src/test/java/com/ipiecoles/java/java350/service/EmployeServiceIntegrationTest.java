package com.ipiecoles.java.java350.service;


import com.ipiecoles.java.java350.Employe;
import com.ipiecoles.java.java350.NiveauEtude;
import com.ipiecoles.java.java350.Poste;
import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.repository.EmployeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;


@SpringBootTest
public class EmployeServiceIntegrationTest {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(EmployeServiceIntegrationTest.class);
    @Autowired
    EmployeService employeService;

    @Autowired
    EmployeRepository employeRepository;

    @Test
    void TestEmbauchePremierEmploye() throws EmployeException {
        //GIVEN
        String nom = "Doe";
        String prenom ="John";
        Poste poste =Poste.TECHNICIEN;
        NiveauEtude niveauEtude = NiveauEtude.BTS_IUT;
        Double tempPartiel = 1.0;

        //WHEN
        employeService.embaucheEmploye(nom,prenom, poste, niveauEtude,tempPartiel);
        //THEN
        List<Employe> employeList = employeRepository.findAll();
        Assertions.assertThat(employeList).hasSize(1);
        Employe employe = employeRepository.findAll().get(0);
        Assertions.assertThat(employe.getNom()).isEqualTo(nom);
        Assertions.assertThat(employe.getPrenom()).isEqualTo(prenom);
        Assertions.assertThat(employe.getSalaire()).isEqualTo(1825.46);
        Assertions.assertThat(employe.getTempsPartiel()).isEqualTo(1.0);
        Assertions.assertThat(employe.getDateEmbauche()).isEqualTo(LocalDate.now());
        Assertions.assertThat(employe.getMatricule()).isEqualTo("T00001");

    }



            /* =====================================================================================================
                   TEST INTEGRATION calculPerformanceCommercial d'EmployeService EXERCICE 4
             =======================================================================================================*/

    /* Objectifs = 1000
    *   CAS 1 : si CA inférieur à 800 alors perf =1
    *   CAS 2 : si CA entre 800 et 950 alors perf -= 2;
    *   CAS 3 : si CA entre 950 et 1050 alors perf = perf;
    *   CAS 4 : si CA entre 1050 et 1200 alors perf += 1
    *   CAS 5 : si CA supérieur à 1200 alors perf +=4
    *
    * Si new perf supérieur a la moyenne des perf alors perf += 1
    */

    @ParameterizedTest(name = "chiffre affaire: {0} , perf attendu {1}")
    @CsvSource({
        " 0, 1 ", // CAS 1
        " 850, 1 ", // CAS 2
        " 1050, 1 ", // CAS 3
        " 1051, 3 ", // CAS 4 (+1 perf > avg)
        " 1201, 6 ", // CAS 5 (+1 perf > avg)
    })
    void TestCalculCommercialTestAllCas(long chiffreAffaire, Integer perfAttendue) throws EmployeException {
        // GIVEN

        employeService.embaucheEmploye("Joel","Bill",Poste.COMMERCIAL,NiveauEtude.LICENCE,1.0);

        //WHEN

        employeService.calculPerformanceCommercial("C00001",chiffreAffaire,1000L);
        Employe employe = employeRepository.findAll().stream().findFirst().get();

        //THEN
        Assertions.assertThat(employe.getPerformance()).isEqualTo(perfAttendue);
    }

    /**
     *  Test si l'employe est en cas 5 mais avec une perf inférieur a la moyenne de commerciaux.
     *
     *
     * @throws EmployeException Si le matricule ,le caTraite ou objectifCa n'as pas une valeur correcte
     */
    @Test
    // CAS SI L'EMPLOYE EST EN CAS 5 MAIS AVEC UNE PERF INFERIEUR A LA MOYENNE DE COMMERCIAUX.
    void TestCalculPerformanceCommercialCAS5WithAvgInf() throws EmployeException {
        // GIVEN

        employeService.embaucheEmploye("Joel","Bill",Poste.COMMERCIAL,NiveauEtude.LICENCE,1.0);

        // Ajout d'un second commercial => matricule C00002 et perf 1 :
        employeService.embaucheEmploye("Jobs","Steve",Poste.COMMERCIAL,NiveauEtude.LICENCE,1.0);

        //WHEN
        employeService.calculPerformanceCommercial("C00001",1201l,1000L); // Bill perf = 6
        employeService.calculPerformanceCommercial("C00001",1201l,1000L); // Bill perf = 11 car currentPerf ici est inf à AvgBDD qui est à 6.
        employeService.calculPerformanceCommercial("C00002",1201l,1000L); //Steve = 5  (car AVG = 11 (bill) + 1 (steve old perf) /2)
        List<Employe> employeList = employeRepository.findAll();

        Employe employe =  employeList.get(employeList.size()-1);
        //THEN
         Assertions.assertThat(employe.getPerformance()).isEqualTo(5);
    }


    @BeforeEach
    @AfterEach
    void Purgebdd(){
        employeRepository.deleteAll();
    }

}
