package com.example.NextU.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.NextU.models.Employer;
import com.example.NextU.repositories.EmployerRepository;

@Service
public class EmployerServiceImpl implements EmployerService {

    @Autowired
    private EmployerRepository employerRepository;

    @Override
    public boolean saveEmployer(Employer employer) {
        try {
            employerRepository.save(employer);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // New method to handle login
    @Override
    public Employer loginEmployer(String email, String password) {
        // Fetch employer by email
        Employer employer = employerRepository.findByEmail(email);
        if (employer != null && employer.getPassword().equals(password)) {
            // If the email exists and password matches, return the employer
            return employer;
        }
        // Return null if authentication fails
        return null;
    }
}
