package com.iuh.edu.fit;

import com.iuh.edu.fit.backend.model.Address;
import com.iuh.edu.fit.backend.model.Candidate;
import com.iuh.edu.fit.backend.repository.AddressRepository;
import com.iuh.edu.fit.backend.repository.CandidateRepository;
import com.iuh.edu.fit.backend.repository.CompanyRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.time.LocalDate;
import java.util.Random;

@SpringBootApplication
public class NguyenTanNhut20096581Application {

    public static void main(String[] args) {
        SpringApplication.run(NguyenTanNhut20096581Application.class, args);
    }

//    @Autowired
//    private CandidateRepository candidateRepository;
//
//    @Autowired
//    private AddressRepository addressRepository;

//    @Bean
//    @Transactional
//    CommandLineRunner initData(){
//        return args -> {
//            Random rand = new Random();
//            for (int i = 0; i < 1000; i++) {
//                Address address = new Address(
//                        rand.nextInt(1, 1000) + "" ,
//                        "Quang Trung",
//                        "HCM",
//                        rand.nextInt(70000, 80000) + "" ,
//                        "VietNam");
//                address = addressRepository.save(address);
//
//                Candidate can = new Candidate("Name #" + i,
//                        LocalDate.of(1998, rand.nextInt(1, 13), rand.nextInt(1, 29)),
//                        address,
//                        rand.nextLong(1111111111L, 9999999999L) + "",
//                        "email_" + i + "@gmail.com");
//                candidateRepository.save(can);
//                System.out.println("Added: " + can);
//            }
//        };
//    }
}
