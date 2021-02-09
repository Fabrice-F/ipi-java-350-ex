package com.ipiecoles.java.java350.model;


import com.ipiecoles.java.java350.Employe;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

public class EmployeTest {


    @Test
    void testIfNombreAnneeAncienneteIsInfNow() {
    // Given: Initialisation des données d'entrée.
        Employe employee = new Employe("Gates","Bill","B00001",
                LocalDate.now().minusYears(21),1200.0d,1,1.0d);

    // When : Exécution de la méthode à tester.
        Integer AnneeAnciennete =  employee.getNombreAnneeAnciennete();

    // Then: Vérifications de ce qu'a fait la méthode.
        Assertions.assertThat(AnneeAnciennete).isEqualTo(21);
    }

    @Test
    void testIfNombreAnneeAncienneteIsNotNull() {
    // Given: Initialisation des données d'entrée.
        Employe employee = new Employe();
        employee.setDateEmbauche(null);
        
    // When : Exécution de la méthode à tester.
        Integer AnneeAnciennete =  employee.getNombreAnneeAnciennete();

    // Then: Vérifications de ce qu'a fait la méthode.
        Assertions.assertThat(AnneeAnciennete).isNull();
    }

    @Test
    void testIfNombreAnneeAncienneteIsSupNow() {
        // Given: Initialisation des données d'entrée.
        Employe employee = new Employe("Gates","Bill","B00001",
                LocalDate.now().plusYears(7),1200.0d,1,1.0d);

        // When : Exécution de la méthode à tester.
        Integer AnneeAnciennete =  employee.getNombreAnneeAnciennete();

        // Then: Vérifications de ce qu'a fait la méthode.
        Assertions.assertThat(AnneeAnciennete).isNull();
    }
    @Test
    void testIfNombreAnneeAncienneteIsNow() {
        // Given: Initialisation des données d'entrée.
        Employe employee = new Employe("Gates","Bill","B00001",
                LocalDate.now(),1200.0d,1,1.0d);

        // When : Exécution de la méthode à tester.
        Integer AnneeAnciennete =  employee.getNombreAnneeAnciennete();

        // Then: Vérifications de ce qu'a fait la méthode.
        Assertions.assertThat(AnneeAnciennete).isEqualTo(0);
    }

    // element fixe :
        // prime de base manager
        // prime indice manager
        // prime de base employé
        // prime ancienneté

    // Trouver les éléments qui vont influencer le calcul de la prime:
        //  le poste : manager ou employer
        //  indice de performance
        //  Date d'embauche
        //  temps partiel

    // écrire des scénarios pertinents faisant varier ses éléments.
        //
        //
        //
        //
    // écrire un premier test unitaire classique testant un de vos scénarios.
    // dupliquer et transformer ce test en test paramétré pour ajouter facilement les autres scénarios.
    // résultat avec isequalTo.



    @Test
    void testIfMatriculeEmployeIsEmpty(){
        // GIVEN
        Employe employee = new Employe("JOB","Steve","",
                LocalDate.now().minusYears(7),1200.0d,1,1.0d);

        //WHEN
        Double prime = employee.getPrimeAnnuelle();

        //THEN
        Assertions.assertThat(prime).isEqualTo(1700d);
    }

    @ParameterizedTest(name = "matricule: {0},  anciennete: {1}, performance: {2}, temps partiel: {3} => prime: {4}€")
    @CsvSource({

            "'T001',0,1,1,1000", // test de base
            "'T002',0,1,0.5d,500",  // tst a temps partiel
            "'T003',0,2,1,2300",    // test avec une perf a 2
            "'T004',2,1,1,1200",    // test avec 2 ans d'anciennete
            "'T005',1,2,1,2400",
            "'M005',0,1,1,1700",
            "'M006',3,1,1,2000",

    })
    void TestGetPrimeAnnuel(String matricule,Long nbAnneesAnciennete,Integer performance,Double tauxActivite,Double primeAttendue ){
        // GIVEN

        Employe employee = new Employe("JOB","Steve",matricule,
                LocalDate.now().minusYears(nbAnneesAnciennete),1400d,performance,tauxActivite);

        //WHEN
        Double prime = employee.getPrimeAnnuelle();

        //THEN

        Assertions.assertThat(prime).isEqualTo(primeAttendue);
    }

    @Test
    void TestGetPrimeAnnuelMatriculeNull(){
        // GIVEN

        Employe employee = new Employe("JOB","Steve",null,
                LocalDate.now(),1400d,1,1.0d);

        //WHEN
        Double prime = employee.getPrimeAnnuelle();

        //THEN

        Assertions.assertThat(prime).isEqualTo(1000.0);
    }

}
