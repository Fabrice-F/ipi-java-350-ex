package com.ipiecoles.java.java350.repository;

import com.ipiecoles.java.java350.Employe;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.annotation.PostConstruct;
import java.time.LocalDate;


// de base spring boot n'est pas charger dans les tests:

/* utiliser spring dans un test :
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {Java350Application.class})

si on test un repo : @DataJpaTest

pour tout char gerr ( spring boot, repo , etc ..) : @SpringBootTest
*/


@DataJpaTest
class EmployeRepositoryTest {

    @Autowired
    EmployeRepository employeRepository;


    @Test
    public void TestIntegrationFindLastMatriculeMatricule1Employe(){
        Employe e = new Employe("Depardeux","Gérard","T1234", LocalDate.now(),1400d,1,1d);
        Employe emplSave2 = employeRepository.save(e);


        String MatriculeEmplRechercher = employeRepository.findLastMatricule();

        //THEN
        Assertions.assertThat(MatriculeEmplRechercher).isEqualTo("1234");
    }

    @Test
    public void TestIntegrationFindLastMatriculeMatricule0Employe(){
        String lastMatricule = employeRepository.findLastMatricule();
        Assertions.assertThat(lastMatricule).isNull();
    }

    @Test
    public void TestIntegrationFindLastMatriculeMatriculeIfMatriculeIsNull(){
        //GIVEN
        Employe e = new Employe("Depardeux","Gérard",null, LocalDate.now(),1400d,1,1d);
        Employe emplSave2 = employeRepository.save(e);

        //WHEN
        String MatriculeEmplRechercher = employeRepository.findLastMatricule();

        //THEN
        Assertions.assertThat(MatriculeEmplRechercher).isNull();
    }

    @Test
    public void TestIntegrationLastMatriculeMatriculeIfMatriculeIsEmpty(){
        employeRepository.deleteAll();
        //GIVEN
        Employe e = new Employe("Depardeux","Gérard","", LocalDate.now(),1400d,1,1d);
        Employe emplSave2 = employeRepository.save(e);

        //WHEN
        String MatriculeEmplRechercher = employeRepository.findLastMatricule();

        //THEN
        Assertions.assertThat(MatriculeEmplRechercher).isEmpty();
    }

    @Test
    public void TestIntegrationFindLastMatriculeMatriculeIsResultOk(){
        //GIVEN
        Employe e = new Employe("Depardeux","Gérard","T1263", LocalDate.now(),1400d,1,1d);
        Employe e2 = new Employe("test","Bill","M4453", LocalDate.now(),1400d,1,1d);
        Employe e3 = new Employe("ola","Oda","C00326", LocalDate.now(),1400d,1,1d);

        Employe emplSave = employeRepository.save(e);
        Employe emplSave2 = employeRepository.save(e2);
        Employe emplSave3 = employeRepository.save(e3);

        //WHEN
        String MatriculeEmplRechercher = employeRepository.findLastMatricule();

        //THEN
        String matriculeSplit = e.getMatricule().substring(1);
        Assertions.assertThat(MatriculeEmplRechercher).isEqualTo("4453");
    }

   /* =====================================================================================================
                                    TEST avgPerformanceWhereMatriculeStartsWith EXERCICE 4 

     ===================================================================================================== */

    /**
     * Test la moyenne sur des nombres simples et des nombres qui donne une moyenne a virgules
     * la moyenne peut être un double avec x chiffre après la virgule
     */
    @ParameterizedTest(name = "perf1: {0}, perf2: {1}, perf3: {2}, perfAttendue: {3}")
    @CsvSource({
            "1, 1, 1, 1",
            "1, 1, 2, 1.3333333333333333",
            " 2, 4, 6, 4 ",
            " 5 , 10 ,15 , 10",
            " 1, 3, 4, 2.6666666666666665 ",
    })
    void TestIntegrationAvgPerformanceWhereMatriculeStartsWith(Integer perf1 , Integer perf2, Integer perf3, double perfAttendu){
        //GIVEN
        Employe Commercial1 = new Employe();
        Employe Commercial2 = new Employe();
        Employe Commercial3 = new Employe();

        Commercial1.setPerformance(perf1);
        Commercial1.setMatricule("C00001");

        Commercial2.setPerformance(perf2);
        Commercial2.setMatricule("C00002");

        Commercial3.setPerformance(perf3);
        Commercial3.setMatricule("C00003");

        employeRepository.save(Commercial1);
        employeRepository.save(Commercial2);
        employeRepository.save(Commercial3);

        //WHEN
        Double MoyennePerf = employeRepository.avgPerformanceWhereMatriculeStartsWith("C");

        // THEN

        Assertions.assertThat(MoyennePerf).isEqualTo(perfAttendu);
    }


    @BeforeEach
    @AfterEach
    public void purgeBDD(){
        employeRepository.deleteAll();
    }

}
