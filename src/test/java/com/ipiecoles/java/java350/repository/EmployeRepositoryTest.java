package com.ipiecoles.java.java350.repository;

import com.ipiecoles.java.java350.Employe;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
    public void testFindLastMatriculeMatricule1Employe(){
        Employe e = new Employe("Depardeux","Gérard","T1234", LocalDate.now(),1400d,1,1d);
        Employe emplSave2 = employeRepository.save(e);


        String MatriculeEmplRechercher = employeRepository.findLastMatricule();

        //THEN
        Assertions.assertThat(MatriculeEmplRechercher).isEqualTo("1234");
    }

    @Test
    public void testFindLastMatriculeMatricule0Employe(){
        String lastMatricule = employeRepository.findLastMatricule();
        Assertions.assertThat(lastMatricule).isNull();
    }

    @Test
    public void testFindLastMatriculeMatriculeIfMatriculeIsNull(){
        //GIVEN
        Employe e = new Employe("Depardeux","Gérard",null, LocalDate.now(),1400d,1,1d);
        Employe emplSave2 = employeRepository.save(e);

        //WHEN
        String MatriculeEmplRechercher = employeRepository.findLastMatricule();

        //THEN
        Assertions.assertThat(MatriculeEmplRechercher).isNull();
    }

    @Test
    public void testFindLastMatriculeMatriculeIfMatriculeIsEmpty(){
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
    public void testFindLastMatriculeMatriculeIsResultOk(){
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


    @BeforeEach
    @AfterEach
    public void purgeBDD(){
        employeRepository.deleteAll();
    }

}
