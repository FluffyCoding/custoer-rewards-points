package com.restapi.rewards.service.impl;

import com.restapi.rewards.dto.CustomersTransactionSummryDto;
import com.restapi.rewards.dto.RewardPointsByCustomerDto;
import com.restapi.rewards.dto.TransactionRequestDto;
import com.restapi.rewards.dto.TransactionResponseDto;
import com.restapi.rewards.entity.Transaction;
import com.restapi.rewards.repository.CustomerRepository;
import com.restapi.rewards.repository.TransactionRepository;
import com.restapi.rewards.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImplementation implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final ModelMapper transactionMapper;

    private final CustomerRepository customerRepository;

    @Override
    public List<TransactionResponseDto> getAllTransaction() {
        return transactionRepository.findAll().stream().map(
                transaction -> transactionMapper.map(transaction, TransactionResponseDto.class)).toList();
    }

    @Override
    public TransactionResponseDto commitNewTransaction(TransactionRequestDto transactionDto) {

        var rewardPoints = calculateRewardPoints(transactionDto.getTransactionAmount().intValue());

        var customer = customerRepository.findById(transactionDto.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("Invalid Customer Id"));

        var transaction = Transaction.builder()
                .transactionAmount(transactionDto.getTransactionAmount())
                .transactionDate(LocalDate.now())
                .pointsAwarded(rewardPoints)
                .customer(customer)
                .build();
        return transactionMapper.map(transactionRepository.save(transaction), TransactionResponseDto.class);
    }

    @Override
    public List<TransactionResponseDto> findTransactionByEmail(String email) {
        var transactions = transactionRepository.findAllByCustomer_Email(email);
        return transactions.stream().map(transaction -> transactionMapper.map(transaction, TransactionResponseDto.class)).toList();
    }

    @Override
    public List<TransactionResponseDto> findTransactionByDateAndCustomerId(RewardPointsByCustomerDto rewardPointsByCustomerDto) {
        var transactions = transactionRepository.findByCustomer_IdAndTransactionDateBetweenOrderByTransactionDateAsc(rewardPointsByCustomerDto.getCustomerId(), rewardPointsByCustomerDto.getStartDate(), rewardPointsByCustomerDto.getEndDate());
        return transactions.stream().map(transaction -> transactionMapper
                .map(transaction, TransactionResponseDto.class)).toList();
    }


    @Override
    public List<CustomersTransactionSummryDto> transactionSummry(LocalDate startDate, LocalDate endDate) {
        return transactionRepository.getTransactionsSummaryByDate(startDate, endDate);
    }

    @Override
    public Integer rewardPointsCalculator(BigDecimal amount) {
        return calculateRewardPoints(amount.intValue());
    }

    private int calculateRewardPoints(int purchase) {

        int rewardPointsUnderFifty = 50;
        int rewardPointsOverHundred = 100;
        int towPoints = 2;

        if (purchase < rewardPointsUnderFifty) {
            return 0;
        } else if (purchase <= rewardPointsOverHundred) {
            return purchase - rewardPointsUnderFifty;
        } else {
            return ((purchase - rewardPointsOverHundred) * towPoints) + rewardPointsUnderFifty;
        }
    }


}
