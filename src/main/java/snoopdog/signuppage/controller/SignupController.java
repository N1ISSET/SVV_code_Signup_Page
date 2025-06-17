package snoopdog.signuppage.controller;

import snoopdog.signuppage.model.User;
import snoopdog.signuppage.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class SignupController {

    @Autowired
    private UserRepository userRepository;

    // Show the signup form
    @GetMapping("/signup")
    public String showSignUpForm(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    // Process the signup form
    @PostMapping("/signup")
    public String processSignUp(@ModelAttribute("user") User user,
                                @RequestParam("confirmPassword") String confirmPassword,
                                Model model) {

        // Check if email already exists
        if (userRepository.existsByEmail(user.getEmail())) {
            model.addAttribute("error", "Email already registered!");
            return "signup";
        }

        // Check if username already exists
        if (userRepository.existsByUsername(user.getUsername())) {
            model.addAttribute("error", "Username already taken!");
            return "signup";
        }

        // Check if passwords match
        if (!user.getPassword().equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match!");
            return "signup";
        }

        // Check password length
        int passwordLength = user.getPassword().length();
        if (passwordLength < 6 || passwordLength > 16) {
            model.addAttribute("error", "Password must be between 6 and 16 characters.");
            return "signup";
        }

        // Check username length
        int usernameLength = user.getUsername().length();
        if (usernameLength < 3 || usernameLength > 16) {
            model.addAttribute("error", "Username must be between 3 and 16 characters.");
            return "signup";
        }

        // Save user to database
        userRepository.save(user);
        model.addAttribute("success", "Sign up successful!");
        return "signup";
    }
}
