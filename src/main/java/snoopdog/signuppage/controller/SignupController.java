package snoopdog.signuppage.controller;

import snoopdog.signuppage.model.User;
import snoopdog.signuppage.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Controller
public class SignupController {

    @Autowired
    private UserRepository userRepository;

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/signup")
    public String showSignUpForm(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/signup")
    public String processSignUp(@ModelAttribute User user, @RequestParam String confirmPassword, Model model) {

        // Server-side field validation
        if (user.getUsername() == null || user.getUsername().isBlank() ||
                user.getEmail() == null || !user.getEmail().contains("@") ||
                user.getPassword() == null || user.getPassword().length() < 8 && user.getPassword().length() > 16) {
            model.addAttribute("error", "Invalid input. Make sure all fields are correctly filled.");
            return "signup";
        }

        // Password confirmation check
        if (!user.getPassword().equals(confirmPassword)) {
            model.addAttribute("error", "Password and confirmation do not match.");
            return "signup";
        }

        // Check for existing user by email or username
        Optional<User> existingUser = userRepository.findByUsernameOrEmail(user.getUsername(), user.getEmail());
        if (existingUser.isPresent()) {
            model.addAttribute("error", "An account with this username or email already exists.");
            return "signup";
        }

        // Make a logging API call (handle errors appropriately)
        try {
            String loggingUrl = "localhost:8080/signin";
            ResponseEntity<String> response = restTemplate.postForEntity(loggingUrl, user, String.class);
            // Optionally check response status
        } catch (Exception e) {
            System.err.println("Logging API call failed: " + e.getMessage());
        }

        userRepository.save(user);
        model.addAttribute("message", "Signup successful!");
        return "signup";
    }
}
