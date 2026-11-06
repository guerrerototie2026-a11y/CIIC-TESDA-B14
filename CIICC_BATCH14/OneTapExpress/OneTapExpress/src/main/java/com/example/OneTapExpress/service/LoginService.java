package com.example.OneTapExpress.service;

import com.example.OneTapExpress.model.User;
import com.example.OneTapExpress.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class LoginService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final ConcurrentMap<String, Integer> failedAttempts =
            new ConcurrentHashMap<>();

    public LoginService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User login(String mobileNumber, String pin) {

        if (failedAttempts.getOrDefault(mobileNumber, 0) >= 3) {
            throw new IllegalArgumentException(
                    "Account temporarily locked. Contact support.");
        }

        Optional<User> optionalUser =
                userRepository.findByMobileNumber(mobileNumber);

        if (optionalUser.isEmpty()
                || !passwordEncoder.matches(
                pin, optionalUser.get().getPinHash())) {

            failedAttempts.merge(mobileNumber, 1, Integer::sum);

            throw new IllegalArgumentException(
                    "Invalid mobile number or PIN.");
        }

        failedAttempts.remove(mobileNumber);

        return optionalUser.get();
    }
}