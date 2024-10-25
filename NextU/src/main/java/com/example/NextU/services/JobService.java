package com.example.NextU.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;  // Import statement for List

import com.example.NextU.models.Job;
import com.example.NextU.repositories.JobRepository;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    public void saveJob(Job job) {
        jobRepository.save(job);  // Save job to the database
    }

    public List<Job> getAllJobs() {
        return jobRepository.findAll();  // Return all jobs from the database
    }
}
