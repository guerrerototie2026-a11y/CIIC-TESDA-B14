package com.example.OneTapExpress.controller;

import com.example.OneTapExpress.model.User;
import com.example.OneTapExpress.service.LoginService;
import com.example.OneTapExpress.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UserService userService;
    private final LoginService loginService;

    public AuthController(
            UserService userService,
            LoginService loginService) {

        this.userService = userService;
        this.loginService = loginService;
    }

    @GetMapping("/register")
    public String showRegistrationPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(
            @RequestParam String fullName,
            @RequestParam String mobileNumber,
            @RequestParam String pin,
            Model model) {

        try {
            userService.registerUser(fullName, mobileNumber, pin);

            return "redirect:/login?registered";

        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String mobileNumber,
            @RequestParam String pin,
            HttpSession session,
            Model model) {

        try {
            User user = loginService.login(mobileNumber, pin);

            session.setAttribute("loggedInUserId", user.getId());

            return "redirect:/dashboard";

        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}