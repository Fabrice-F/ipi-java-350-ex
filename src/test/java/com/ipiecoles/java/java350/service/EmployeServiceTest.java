package com.ipiecoles.java.java350.service;


import com.ipiecoles.java.java350.Employe;
import com.ipiecoles.java.java350.NiveauEtude;
import com.ipiecoles.java.java350.Poste;
import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.repository.EmployeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityExistsException;
import java.time.LocalDate;


@ExtendWith(MockitoExtension.class)
public class EmployeServiceTest {

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


}
