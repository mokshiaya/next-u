package com.example.NextU.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.NextU.models.Employer;
import com.example.NextU.services.EmployerService;
import com.example.NextU.services.EmailService;

@Controller
public class EmployerController {

    @Autowired
    private EmployerService employerService;
    
    @Autowired
    private EmailService emailService;

    private String otpCode;  // Store OTP temporarily (or use session)

    @GetMapping("/employer-register")
    public String showEmployerRegistrationForm(Model model) {
        model.addAttribute("employer", new Employer());
        return "employer-register";
    }

    @PostMapping("/registerEmployer")
    public String registerEmployer(@ModelAttribute("employer") Employer employer,                               
                                   @RequestParam("confirmPassword") String confirmPassword, Model model) {
        // Check if passwords match
        if (!employer.getPassword().equals(confirmPassword)) {
            model.addAttribute("errorMsg", "Passwords do not match");
            return "employer-register";
        }

        // Generate and send OTP
        otpCode = emailService.generateOtp();
        emailService.sendOtpEmail(employer.getEmail(), otpCode);

        model.addAttribute("successMsg", "OTP has been sent to your email. Please enter the OTP to verify.");
        model.addAttribute("employer", employer);  // To retain data
        return "otp-verification";  // Redirect to OTP verification page
    }

    @PostMapping("/verifyOtp")
    public String verifyOtp(@RequestParam("enteredOtp") String enteredOtp, 
                            @ModelAttribute("employer") Employer employer, Model model) {
        if (enteredOtp.equals(otpCode)) {
            employer.setOtpVerified(true);  // Mark as verified
            boolean status = employerService.saveEmployer(employer);
            if (status) {
                model.addAttribute("successMsg", "User registered successfully");
                return "employer-login";  // Redirect to login page
            } else {
                model.addAttribute("errorMsg", "User not registered");
                return "employer-register";
            }
        } else {
            model.addAttribute("errorMsg", "Invalid OTP. Please try again.");
            model.addAttribute("employer", employer);  
            return "otp-verification";
        }
    }

    // New: Show login form
    @GetMapping("/employer-login")
    public String showEmployerLoginPage() {
        System.out.println("Login page requested");  // Simple log to ensure mapping is hit
        return "employer-login";  // Ensure this returns the correct view name
    }


    // New: Handle login
    @PostMapping("/loginEmployer")
    public String loginEmployer(@RequestParam("email") String email, 
                                @RequestParam("password") String password, Model model) {
        // Validate the login credentials
        Employer employer = employerService.loginEmployer(email, password);
        if (employer != null) {
            // If the credentials are correct, redirect to the employer dashboard
            return "redirect:/employer-dashboard";
        } else {
            // If login fails, return the login page with an error message
            model.addAttribute("errorMsg", "Invalid email or password");
            return "employer-login";
        }
    }
}

//package com.example.NextU.controllers;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import com.example.NextU.models.Employer;
//import com.example.NextU.services.EmailService;
//import com.example.NextU.services.EmployerService;
//
//@Controller
//public class EmployerController {
//
//    @Autowired
//    private EmployerService employerService;
//    
//    @Autowired
//    private EmailService emailService;
//
//    private String otpCode;  // Store OTP temporarily (or use session)
//
//    @GetMapping("/employer-register")
//	public String showEmployerRegistrationForm(Model model) {
//		model.addAttribute("employer", new Employer());
//		return "employer-register";
//	}
//	
//	//@ModelAttribute bind the form data
//
//
//    @PostMapping("/registerEmployer")
//    public String registerEmployer(@ModelAttribute("employer") Employer employer,                               
//    		@RequestParam("confirmPassword") String confirmPassword, Model model) {
//        // Check if passwords match
//        if (!employer.getPassword().equals(confirmPassword)) {
//            model.addAttribute("errorMsg", "Passwords do not match");
//            return "employer-register";
//        }
//        //**
////        else {
////        	employerService.saveEmployer(employer);
////        }
//        // Generate and send OTP
//        otpCode = emailService.generateOtp();
//        emailService.sendOtpEmail(employer.getEmail(), otpCode);
//
//        model.addAttribute("successMsg", "OTP has been sent to your email. Please enter the OTP to verify.");
//        model.addAttribute("employer",  employer);  // To retain data
//        return "otp-verification";  // Redirect to OTP verification page
//    }
//
//    @PostMapping("/verifyOtp")
//    public String verifyOtp(@RequestParam("enteredOtp") String enteredOtp, @ModelAttribute("employer") Employer employer, Model model) {
//        if (enteredOtp.equals(otpCode)) {
//            employer.setOtpVerified(true);  // Mark as verified
//            boolean status = employerService.saveEmployer(employer);
//            if (status) {
//                model.addAttribute("successMsg", "User registered successfully");
//                return "employer-login";  // Redirect to login page
//            } else {
//                model.addAttribute("errorMsg", "User not registered");
//                return "employer-register";
//            }
//        } else {
//            model.addAttribute("errorMsg", "Invalid OTP. Please try again.");
//            model.addAttribute("employer",  employer);  
//            return "otp-verification";
//        }
//    }
//}
