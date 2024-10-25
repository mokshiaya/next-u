package com.example.NextU.services;

import com.example.NextU.models.Employer;

public interface EmployerService {
    boolean saveEmployer(Employer employer);
    Employer loginEmployer(String email, String password);  // New method for login
}
