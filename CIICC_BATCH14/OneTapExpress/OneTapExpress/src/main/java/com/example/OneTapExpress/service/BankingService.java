package com.example.OneTapExpress.service;

import com.example.OneTapExpress.model.Transaction;
import com.example.OneTapExpress.model.User;
import com.example.OneTapExpress.repository.TransactionRepository;
import com.example.OneTapExpress.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class BankingService {

    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    public BankingService(
            UserRepository userRepository,
            TransactionRepository transactionRepository) {

        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    public BigDecimal checkBalance(Long userId) {
        User user = findUser(userId);
        return user.getBalance();
    }

    @Transactional
    public void cashIn(Long userId, BigDecimal amount) {

        validateAmount(amount);

        User user = findUser(userId);

        user.setBalance(user.getBalance().add(amount));
        userRepository.save(user);

        saveTransaction(
                user,
                "CASH_IN",
                amount,
                "Money added to account"
        );
    }

    @Transactional
    public void transfer(
            Long senderId,
            String receiverMobile,
            BigDecimal amount) {

        validateAmount(amount);

        User sender = findUser(senderId);

        User receiver = userRepository
                .findByMobileNumber(receiverMobile)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Receiver account not found."));

        if (sender.getId().equals(receiver.getId())) {
            throw new IllegalArgumentException(
                    "You cannot transfer money to yourself.");
        }

        if (sender.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException(
                    "Insufficient balance.");
        }

        sender.setBalance(sender.getBalance().subtract(amount));
        receiver.setBalance(receiver.getBalance().add(amount));

        userRepository.save(sender);
        userRepository.save(receiver);

        saveTransaction(
                sender,
                "TRANSFER_SENT",
                amount,
                "Money sent to " + receiverMobile
        );

        saveTransaction(
                receiver,
                "TRANSFER_RECEIVED",
                amount,
                "Money received from " + sender.getMobileNumber()
        );
    }

    private User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "User account not found."));
    }

    private void validateAmount(BigDecimal amount) {
        if (amount == null
                || amount.compareTo(BigDecimal.ZERO) <= 0
                || amount.scale() > 2) {

            throw new IllegalArgumentException(
                    "Enter a valid amount greater than zero with up to 2 decimal places.");
        }
    }

    private void saveTransaction(
            User user,
            String type,
            BigDecimal amount,
            String details) {

        Transaction transaction = new Transaction();

        transaction.setUser(user);
        transaction.setTransactionType(type);
        transaction.setAmount(amount);
        transaction.setDetails(details);

        transactionRepository.save(transaction);
    }
}