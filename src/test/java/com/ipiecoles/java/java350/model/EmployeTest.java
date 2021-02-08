package com.ipiecoles.java.java350.model;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class EmployeTest {


    @Test
    public void testIfNombreAnneeAncienneteIsInfNow() {
    // Given: Initialisation des données d'entrée.
        Employe employee = new Employe("Gates","Bill","B00001",
                LocalDate.now().minusYears(21),1200.0d,1,1.0d);

    // When : Exécution de la méthode à tester.
        Integer AnneeAnciennete =  employee.getNombreAnneeAnciennete();

    // Then: Vérifications de ce qu'a fait la méthode.
        Assertions.assertThat(AnneeAnciennete).isEqualTo(21);
    }

    @Test
    public void testIfNombreAnneeAncienneteIsNotNull() {
    // Given: Initialisation des données d'entrée.
        Employe employee = new Employe("Gates","Bill","B00001",
                null,1200.0d,1,1.0d);

    // When : Exécution de la méthode à tester.
        Integer AnneeAnciennete =  employee.getNombreAnneeAnciennete();

    // Then: Vérifications de ce qu'a fait la méthode.
        Assertions.assertThat(AnneeAnciennete).isNull();
    }

    @Test
    public void testIfNombreAnneeAncienneteIsSupNow() {
        // Given: Initialisation des données d'entrée.
        Employe employee = new Employe("Gates","Bill","B00001",
                LocalDate.now().plusYears(7),1200.0d,1,1.0d);

        // When : Exécution de la méthode à tester.
        Integer AnneeAnciennete =  employee.getNombreAnneeAnciennete();

        // Then: Vérifications de ce qu'a fait la méthode.
        Assertions.assertThat(AnneeAnciennete).isNull();
    }
    @Test
    public void testIfNombreAnneeAncienneteIsNow() {
        // Given: Initialisation des données d'entrée.
        Employe employee = new Employe("Gates","Bill","B00001",
                LocalDate.now(),1200.0d,1,1.0d);

        // When : Exécution de la méthode à tester.
        Integer AnneeAnciennete =  employee.getNombreAnneeAnciennete();

        // Then: Vérifications de ce qu'a fait la méthode.
        Assertions.assertThat(AnneeAnciennete).isEqualTo(0);
    }


}
