package snoopdog.signuppage.controller;

import snoopdog.signuppage.model.User;
import snoopdog.signuppage.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class SigninController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/signin")
    public String showSignInForm(Model model) {
        model.addAttribute("user", new User());
        return "signin";
    }

    @PostMapping("/signin")
    public String processSignIn(@ModelAttribute User user, Model model) {
        String email = user.getEmail();
        String password = user.getPassword();


        if (email == null || !email.contains("@") || password == null) {
            model.addAttribute("error", "Please enter a valid email and password.");
            return "signin";
        }


        Optional<User> existingUser = userRepository.findByEmail(email);

        if (existingUser.isEmpty()) {
            model.addAttribute("error", "No account found with that email.");
            return "signin";
        }

        User storedUser = existingUser.get();


        if (!storedUser.getPassword().equals(password)) {
            model.addAttribute("error", "Incorrect password.");
            return "signin";
        }


        model.addAttribute("message", "Login successful!");
        return "home"; // Redirect to homepage or dashboard
    }
}
