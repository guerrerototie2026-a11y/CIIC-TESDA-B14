
package com.example.OneTapExpress.controller;

import com.example.OneTapExpress.service.BankingService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Controller
public class BankingController {

    private final BankingService bankingService;

    public BankingController(BankingService bankingService) {
        this.bankingService = bankingService;
    }

    @GetMapping("/cash-in")
    public String showCashInPage(HttpSession session) {

        if (session.getAttribute("loggedInUserId") == null) {
            return "redirect:/login";
        }

        return "cash-in";
    }

    @PostMapping("/cash-in")
    public String cashIn(
            @RequestParam BigDecimal amount,
            HttpSession session,
            Model model) {

        Long userId = (Long) session.getAttribute("loggedInUserId");

        if (userId == null) {
            return "redirect:/login";
        }

        try {
            bankingService.cashIn(userId, amount);

            return "redirect:/dashboard?cashInSuccess";

        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "cash-in";
        }
    }

    @GetMapping("/transfer")
    public String showTransferPage(HttpSession session) {

        if (session.getAttribute("loggedInUserId") == null) {
            return "redirect:/login";
        }

        return "transfer";
    }

    @PostMapping("/transfer")
    public String transfer(
            @RequestParam String receiverMobile,
            @RequestParam BigDecimal amount,
            HttpSession session,
            Model model) {

        Long senderId = (Long) session.getAttribute("loggedInUserId");

        if (senderId == null) {
            return "redirect:/login";
        }

        try {
            bankingService.transfer(senderId, receiverMobile, amount);

            return "redirect:/dashboard?transferSuccess";

        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "transfer";
        }
    }

}

