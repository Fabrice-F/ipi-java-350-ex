package com.ipiecoles.java.java350.service;


import com.ipiecoles.java.java350.Employe;
import com.ipiecoles.java.java350.NiveauEtude;
import com.ipiecoles.java.java350.Poste;
import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.repository.EmployeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;


@SpringBootTest
public class EmployeServiceIntegrationTest {


    @Autowired
    EmployeService employeService;

    @Autowired
    EmployeRepository employeRepository;


//    @Test
//    public void testEmbaucheEmployeSalaire() throws EmployeException {
//        //GIVEN
//        employeService.embaucheEmploye("Geof","Phil", Poste.TECHNICIEN, NiveauEtude.LICENCE,1.0);
//
//        //WHEN
//        String mat = employeRepository.findLastMatricule();
//        Employe e = employeRepository.findByMatricule(mat);
//        //THEN
//
//        Assertions.assertThat(e.getSalaire()).isEqualTo(1825.464);
//    }

    @Test
    public void testEmbauchePremierEmploye() throws EmployeException {
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



}
