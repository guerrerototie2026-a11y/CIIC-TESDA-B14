package com.example.OneTapExpress.controller;

import com.example.OneTapExpress.model.User;
import com.example.OneTapExpress.repository.UserRepository;
import com.example.OneTapExpress.repository.TransactionRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    public DashboardController(
            UserRepository userRepository,
            TransactionRepository transactionRepository) {

        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    @GetMapping("/dashboard")
    public String showDashboard(
            HttpSession session,
            Model model) {

        Long userId = (Long) session.getAttribute("loggedInUserId");

        if (userId == null) {
            return "redirect:/login";
        }

        User user = userRepository.findById(userId)
                .orElse(null);

        if (user == null) {
            session.invalidate();
            return "redirect:/login";
        }

        model.addAttribute("user", user);

        model.addAttribute(
                "transactions",
                transactionRepository
                        .findByUserIdOrderByTransactionDateDesc(userId)
        );

        return "dashboard";
    }
}