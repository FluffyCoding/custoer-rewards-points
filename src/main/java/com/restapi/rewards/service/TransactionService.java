package com.restapi.rewards.service;

import com.restapi.rewards.dto.CustomersTransactionSummryDto;
import com.restapi.rewards.dto.RewardPointsByCustomerDto;
import com.restapi.rewards.dto.TransactionRequestDto;
import com.restapi.rewards.dto.TransactionResponseDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface TransactionService {


    List<TransactionResponseDto> getAllTransaction();

    TransactionResponseDto commitNewTransaction(TransactionRequestDto transaction);

    List<TransactionResponseDto> findTransactionByEmail(String email);

    List<TransactionResponseDto> findTransactionByDateAndCustomerId(RewardPointsByCustomerDto pointsByCustomerDto);

    List<CustomersTransactionSummryDto> transactionSummry(LocalDate startDate, LocalDate endDate);


    Integer rewardPointsCalculator(BigDecimal amount);


}
