package com.ipiecoles.java.java350.service;


import com.ipiecoles.java.java350.Employe;
import com.ipiecoles.java.java350.Entreprise;
import com.ipiecoles.java.java350.NiveauEtude;
import com.ipiecoles.java.java350.Poste;
import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.repository.EmployeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityExistsException;
import java.time.LocalDate;
import java.util.logging.Logger;


@ExtendWith(MockitoExtension.class)
public class EmployeServiceTest {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(EmployeServiceTest.class);

    @InjectMocks
    EmployeService employeService;

    @Mock
    EmployeRepository employeRepository;

    @Test
    public void testEmbauchePremierEmploye() throws EmployeException {
        //GIVEN
        String nom = "Doe";
        String prenom ="John";
        Poste poste =Poste.TECHNICIEN;
        NiveauEtude niveauEtude = NiveauEtude.BTS_IUT;
        Double tempPartiel = 1.0;
        //Simuler qu'aucun matricule n'est présent
        Mockito.when(employeRepository.findLastMatricule()).thenReturn(null);


        Mockito.when(employeRepository.findByMatricule("T00001")).thenReturn(null);
        Mockito.when(employeRepository.save(Mockito.any(Employe.class))).thenAnswer(AdditionalAnswers.returnsFirstArg());
        //WHEN

        employeService.embaucheEmploye(nom,prenom, poste, niveauEtude,tempPartiel);


        //THEN

        // creer un capteur d'argument de la classe employe
        ArgumentCaptor<Employe> employeCaptor = ArgumentCaptor.forClass(Employe.class);

        // le mock va récuperer la valeur passer en parametre de la méthode save.lors de son premiere appel
        Mockito.verify(employeRepository).save(employeCaptor.capture());

        // grace au captor
        Employe employe = employeCaptor.getValue();

        Assertions.assertThat(employe.getNom()).isEqualTo(nom);
        Assertions.assertThat(employe.getPrenom()).isEqualTo(prenom);
        Assertions.assertThat(employe.getSalaire()).isEqualTo(1825.46);
        Assertions.assertThat(employe.getTempsPartiel()).isEqualTo(1.0);
        Assertions.assertThat(employe.getDateEmbauche()).isEqualTo(LocalDate.now());
        Assertions.assertThat(employe.getMatricule()).isEqualTo("T00001");

    }


    @Test
    public void testEmbaucheLimiteMatricule() throws EmployeException {
        //GIVEN
        String nom = "Doe";
        String prenom ="John";
        Poste poste =Poste.TECHNICIEN;
        NiveauEtude niveauEtude = NiveauEtude.BTS_IUT;
        Double tempPartiel = 1.0;
        //Simuler que la matriucle le plus haut est 99999
        Mockito.when(employeRepository.findLastMatricule()).thenReturn("99999");
        //WHEN
        try{

            employeService.embaucheEmploye(nom,prenom, poste, niveauEtude,tempPartiel);
            Assertions.fail("embauche employé aurait dû lancer une exception");
        }
        catch (EmployeException e){
            //THEN
            Assertions.assertThat(e.getMessage()).isEqualTo("Limite des 100000 matricules atteinte !");

            // permets de s'assurer que la méthode n' est jamais appeler !
            Mockito.verify(employeRepository,Mockito.never()).save(Mockito.any(Employe.class));
        }
    }

    @Test
    public void testEmbaucheEmpoyeExisteDeja() throws EmployeException {
        //GIVEN
        String nom = "Doe";
        String prenom ="John";
        Poste poste =Poste.TECHNICIEN;
        NiveauEtude niveauEtude = NiveauEtude.BTS_IUT;
        Double tempPartiel = 1.0;

        Employe employeExistant = new Employe("Doe","Jane","T00001",LocalDate.now(),1500d,1,1.0);
        //Simuler qu'aucun matricule n'est présent
        Mockito.when(employeRepository.findLastMatricule()).thenReturn(null);
        Mockito.when(employeRepository.findByMatricule("T00001")).thenReturn(employeExistant);

        //WHEN
        try{
            employeService.embaucheEmploye(nom,prenom, poste, niveauEtude,tempPartiel);
            Assertions.fail("existe déja  aurait du lancer une exception");
        }
        catch (Exception  e){
            Assertions.assertThat(e).isInstanceOf(EntityExistsException.class);
            Assertions.assertThat(e.getMessage()).isEqualTo("L'employé de matricule T00001 existe déjà en BDD");
        }

    }


        /* =====================================================================================================
                   TEST sans dépendance calculPerformanceCommercial d'EmployeService EXERCICE 3

        * Méthode calculant la performance d'un commercial en fonction de ses objectifs et du chiffre d'affaire traité dans l'année *

        Formule :
            Nombre de jours dans l'année - Nombre de jours travaillés dans l'année en plein temps -
            Nombre de samedi et dimanche dans l'année - Nombre de jours fériés ne tombant pas le week-end -
            Nombre de congés payés = Nombre de jour RTT

        Cas particulier :
             * 1 : Si le chiffre d'affaire est inférieur de plus de 20% à l'objectif fixé, le commercial retombe à la performance de base
             * 2 : Si le chiffre d'affaire est inférieur entre 20% et 5% par rapport à l'ojectif fixé, il perd 2 de performance (dans la limite de la performance de base)
             * 3 : Si le chiffre d'affaire est entre -5% et +5% de l'objectif fixé, la performance reste la même.
             * 4 : Si le chiffre d'affaire est supérieur entre 5 et 20%, il gagne 1 de performance
             * 5 : Si le chiffre d'affaire est supérieur de plus de 20%, il gagne 4 de performance
             * Si la performance ainsi calculée est supérieure à la moyenne des performances des commerciaux, il reçoit + 1 de performance.

        Ce que l'on va devoir Mocké :
        - employeRepository.findByMatricule(matricule);
        - employeRepository.avgPerformanceWhereMatriculeStartsWith("C");
        - employeRepository.save(employe);

        ETAPES 1:
            - Creer un test d'integration sans mocker les repo.
            - Comprendre comment marche la méthode pour cela :
                - Création d'un employé de type commercial avec un chiffre d'affaire simple pour une performance de base..
        ETAPES 2:
            - Création des mocks pour transformer .

     =====================================================================================================*/
        /* Objectifs = 1000
         *   CAS 1 : si CA inférieur à 800 alors perf =1
         *   CAS 2 : si CA entre 800 et 950 alors perf -= 2;
         *   CAS 3 : si CA entre 950 et 1050 alors perf = perf;
         *   CAS 4 : si CA entre 1050 et 1200 alors perf += 1
         *   CAS 5 : si CA supérieur à 1200 alors perf +=4
         *
         * Si new perf supérieur a la moyenne des perf alors perf += 1
         */

    /**
     *
     * @throws EmployeException Si le matricule ,le caTraite ou objectifCa n'as pas une valeur correcte
     */
    @ParameterizedTest(name = "chiffre affaire: {0} , performance resultat {1}")
    @CsvSource({
            " 0 , 1 ",
            " 850 , 1 ",
            " 1050 , 1 ",
            " 1051 , 3 ",
            " 1201 , 6 ",
    })
    void UnitTestCalculPerformanceCommercialAugmentation(long chiffreAffaire,Integer performanceAttendu) throws EmployeException {
        //GIVEN
        String matricule = "C00001";
        long objectif= 1000L;
        Employe employe = new Employe("Musk","Ellon",matricule,LocalDate.now(), Entreprise.SALAIRE_BASE,1,1.0);
        Mockito.when(employeRepository.findByMatricule(matricule)).thenReturn(employe);
        Mockito.when(employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(1.0);
        // permet au mock de renvoyé l'employé passe en argument à la méthode save:
        Mockito.when(employeRepository.save(Mockito.any(Employe.class))).thenAnswer(AdditionalAnswers.returnsFirstArg());

        //WHEN
        employeService.calculPerformanceCommercial(matricule,chiffreAffaire,objectif);

        //THEN

        ArgumentCaptor<Employe> employeCaptor = ArgumentCaptor.forClass(Employe.class);
        Mockito.verify(employeRepository).save(employeCaptor.capture());
        Employe employeWithNewPerformance =employeCaptor.getValue();
        Assertions.assertThat(employeWithNewPerformance.getPerformance()).isEqualTo(performanceAttendu);
    }


    // TODO tester la diminution de la performance.

    // TODO CAS5WithAvgInf.





}
