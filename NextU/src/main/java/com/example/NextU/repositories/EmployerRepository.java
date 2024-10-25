package com.example.NextU.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.NextU.models.Employer;

@Repository
public interface EmployerRepository extends JpaRepository<Employer, Long> {
    Employer findByEmail(String email);  // Method to find employer by email
}


//package com.example.NextU.repositories;
//import org.springframework.data.jpa.repository.JpaRepository;
//import com.example.NextU.models.Employer;
//
////CRUD operation related methods
////App-->Controller-->Service-->Repository(JAO)-->Database
//public interface EmployerRepository extends JpaRepository<Employer, Integer>{
//	
//}
