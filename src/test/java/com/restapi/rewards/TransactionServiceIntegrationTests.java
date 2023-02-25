package com.restapi.rewards;

import com.restapi.rewards.dto.TransactionRequestDto;
import com.restapi.rewards.dto.TransactionResponseDto;
import com.restapi.rewards.entity.Customer;
import com.restapi.rewards.entity.Transaction;
import com.restapi.rewards.repository.CustomerRepository;
import com.restapi.rewards.repository.TransactionRepository;
import com.restapi.rewards.service.TransactionService;
import com.restapi.rewards.service.impl.TransactionServiceImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class TransactionServiceIntegrationTests {


    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private TransactionService transactionService;
    @Mock
    private ModelMapper mapper;

    TransactionRequestDto transactionRequestDto;
    TransactionResponseDto transactionResponseDto;
    Transaction transactionModel;

    Customer customerModel;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);

        customerModel = new Customer(1L, "Jamal", "Khan", "jamla@mail.com", Collections.emptyList());
        transactionService = new TransactionServiceImplementation(transactionRepository, mapper, customerRepository);
        transactionModel = Transaction.builder().transactionDate(LocalDate.now()).transactionAmount(new BigDecimal(1078)).pointsAwarded(2006).customer(customerModel).build();
        transactionRequestDto = TransactionRequestDto.builder().transactionDate(LocalDate.now()).transactionAmount(new BigDecimal(1078)).customerId(customerModel.getId()).build();
        transactionResponseDto = TransactionResponseDto.builder().transactionDate(LocalDate.now()).transactionAmount(new BigDecimal(1078)).pointsAwarded(2006).build();
    }

    @Test
    void testShouldCommit_A_NewTransactionIfTransactionDataIsValid() {
        given(mapper.map(transactionRequestDto, Transaction.class)).willReturn(transactionModel);
        given(mapper.map(transactionModel, TransactionResponseDto.class)).willReturn(transactionResponseDto);
        when(customerRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(customerModel));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transactionModel);
        var transaction = transactionService.commitNewTransaction(transactionRequestDto);

        then(transaction).isNotNull();
        then(transaction.getPointsAwarded()).isEqualTo(2006);
    }

    @Test
    void testRewardsPointCalculator() {
        then(transactionService.rewardPointsCalculator(BigDecimal.valueOf(657))).isEqualTo(1164);
    }


}
