package com.example.OneTapExpress.service;

import com.example.OneTapExpress.model.User;
import com.example.OneTapExpress.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(
            String fullName,
            String mobileNumber,
            String pin) {

        if (userRepository.existsByMobileNumber(mobileNumber)) {
            throw new IllegalArgumentException(
                    "Mobile number is already registered.");
        }

        if (pin == null || !pin.matches("\\d{6}")) {
            throw new IllegalArgumentException(
                    "PIN must contain exactly 6 digits.");
        }

        User user = new User();

        user.setFullName(fullName);
        user.setMobileNumber(mobileNumber);
        user.setPinHash(passwordEncoder.encode(pin));
        user.setBalance(BigDecimal.ZERO);

        return userRepository.save(user);
    }
}