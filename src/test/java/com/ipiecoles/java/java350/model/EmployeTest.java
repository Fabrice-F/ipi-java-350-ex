package com.ipiecoles.java.java350.model;


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
        Employe employee = new Employe("Gates","Bill","B00001",
                null,1200.0d,1,1.0d);

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
    
}
