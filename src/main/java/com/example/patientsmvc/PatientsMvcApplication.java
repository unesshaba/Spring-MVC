package com.example.patientsmvc;

import com.example.patientsmvc.entities.Patient;
import com.example.patientsmvc.repositories.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class PatientsMvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(PatientsMvcApplication.class, args);
    }
//    @Bean
    CommandLineRunner commandLineRunner(PatientRepository patientRepository){
        return args -> {
            patientRepository.save(new Patient(null,"youness",new Date(),false,134));
            patientRepository.save(new Patient(null,"karim",new Date(),true,716));
            patientRepository.save(new Patient(null,"omar",new Date(),false,214));
            patientRepository.save(new Patient(null,"mohammed",new Date(),true,187));
            patientRepository.findAll().forEach(p -> {
                System.out.println(p.getNom());
            });
        };
    }


}
