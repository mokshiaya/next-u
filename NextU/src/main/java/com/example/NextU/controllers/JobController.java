package com.example.NextU.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.NextU.models.Job;
import com.example.NextU.services.JobService;

@Controller
public class JobController {
	
    @Autowired
    private JobService jobService;

    @GetMapping("/employer-dashboard")
    public String showEmployerDashboard(Model model) {
        model.addAttribute("job", new Job());  // Add a new Job object to bind form data
        model.addAttribute("jobs", jobService.getAllJobs());  // Fetch all jobs
        return "employer-dashboard";  // HTML page for job posting form
    }

    @PostMapping("/addJob")
    public String addJob(@ModelAttribute("job") Job job, Model model) {
        jobService.saveJob(job);  // Save job details
        model.addAttribute("successMsg", "Job posted successfully!");
        return "employer-dashboard";
    }
}
