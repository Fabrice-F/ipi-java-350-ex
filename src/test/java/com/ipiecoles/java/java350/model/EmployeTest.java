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
    void testIfNombreAnneeAncienneteIsNull() {
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

    /* =====================================================================================================
                                        TEST AUGMENTATION SALAIRE EXERCICE 1

        * Augmentation salaire : Augmente le salaire de l'employé  en fonction du pourcentage passé en paramètre *

        Formule :
            [Valeur 1] + ([Valeur 1] x [Pourcentage] / 100) = résultat
     =====================================================================================================*/

    // cas testé et cas limite :
        // AugmentationPar0
        // SimpleAugmentation
        // IfAugmentationNegative
        // chiffre après la virgule < 2
        // si salaire de base non renseigné.


    /**
     * Test si l'employe n'as pas d'augmentation si le pourcentage est égale à 0
     */
    @Test
    void testAugmentationSalaireIfPourcentageZero(){
        //GIVEN
        Employe employe = new Employe();
        employe.setSalaire(1200.0);

        //WHEN
        employe.augmenterSalaire(0.0);

        //THEN

        Assertions.assertThat(employe.getSalaire()).isEqualTo(1200.0);
    }

    /**
     * Test qui va vérifié si lorsque l'on appelle la méthode
     * avec en paramètre pourcentage le salaire de l'employé s'augmente correctement.
     */
    @ParameterizedTest(name = "Salaire : {0} augmenté a {1}% cela donne : {2}")
    @CsvSource({
            "1000 , 10.0 , 1100",
            "1300 , 23 , 1599",
            "2200 , 50, 3300",
    })
    void TestAugmentationSalaireSimpleAugmentation(double AncienSalaire, double pourcentage ,double salaireAttendu ){
        //GIVEN
        Employe employe = new Employe();
        employe.setSalaire(AncienSalaire);

        // WHEN
        employe.augmenterSalaire(pourcentage);

        //THEN
        Assertions.assertThat(employe.getSalaire()).isEqualTo(salaireAttendu);
    }

    /**
     *
     * test qui vérifie si l'arrondis ce fait correctement
     * si un résultat à plus de deux chiffres après la virgule
     */
    @ParameterizedTest(name = "salaire avec virgule {0}, agmentation avec virgule {1}, salaire attendue {2}")
    @CsvSource({
            "1399.99 , 33.9987 , 1875.97 ", // arrondi supérieur : 1875.96840013
            "1288.66 , 3.123, 1328.90 ",    // arrondi inférieur : 1328.9048518
    })
    public void TestAugmentationSalaireMax2ChiffreApresVirgule(double AncienSalaire, double pourcentage ,double salaireAttendu ){
        //GIVEN

        Employe employe = new Employe();
        employe.setSalaire(AncienSalaire);

        // WHEN
        employe.augmenterSalaire(pourcentage);

        //THEN
        Assertions.assertThat(employe.getSalaire()).isEqualTo(salaireAttendu);

    }

    /**
     * Test si lorsqu'un pourcentage négatif est passé en paramètre
     * le salaire n'est pas impacté car cela est interdit.
     */
    @Test
    void TestAugmentationSalaireIfAugmentationNegative(){
        //GIVEN
        Employe employe = new Employe();
        employe.setSalaire(1000.0);

        // WHEN
        employe.augmenterSalaire(-10.0);

        //THEN
        Assertions.assertThat(employe.getSalaire()).isEqualTo(1000.0);
    }
    /**
     * Test si le salaire de l'employé est null alors il est impossible de l'augmenter
     */
    @Test
    void TestAugmentationSalaireIfSalaireEmployeIsNull(){
        //GIVEN
        Employe employe = new Employe();
        employe.setSalaire(null);

        // WHEN
        employe.augmenterSalaire(10.0);

        //THEN
        Assertions.assertThat(employe.getSalaire()).isEqualTo(null);
    }



    /* =====================================================================================================
                                    TEST getNbRtt SALAIRE EXERCICE 2

        * Méthode permettant de calculer le nombre de jour dans l'année *

        Formule :
            Nombre de jours dans l'année - Nombre de jours travaillés dans l'année en plein temps -
            Nombre de samedi et dimanche dans l'année - Nombre de jours fériés ne tombant pas le week-end -
            Nombre de congés payés = Nombre de jour RTT

        Cas particulier :
            - Temps activité employé
            - Année bisextile.
            - Jour ou commence l'année.
     =====================================================================================================*/

    // TODO : A parametrer et a tester les autres parties.
    @Test
    void TestgetNbRttTempsPlein2019(){
        //GIVEN
            Employe e = new Employe();
            e.setTempsPartiel(1.0);
        //WHEN
            Integer nbRtt =  e.getNbRtt(LocalDate.of(2019,1,01));
        //THEN
            Assertions.assertThat(nbRtt).isEqualTo(8);
    }

    @Test
    void TestgetNbRttMiTemps2019(){
        //GIVEN
            Employe e = new Employe();
            e.setTempsPartiel(0.5);
        //WHEN
            Integer nbRtt =  e.getNbRtt(LocalDate.of(2019,1,01));
        //THEN
            Assertions.assertThat(nbRtt).isEqualTo(4);
    }
}
