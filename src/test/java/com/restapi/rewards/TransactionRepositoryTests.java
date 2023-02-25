package com.restapi.rewards;

import com.restapi.rewards.entity.Customer;
import com.restapi.rewards.entity.Transaction;
import com.restapi.rewards.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;

import static org.assertj.core.api.BDDAssertions.then;

@DataJpaTest
public class TransactionRepositoryTests {


    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    TestEntityManager testEntityManager;

    Transaction transaction;

    Customer customer;
    @BeforeEach
    void inti(){

        customer = new Customer(3L, "Jamal", "Khan", "jamla@mail.com", Collections.emptyList());

        transaction = Transaction.builder()
                .transactionDate(LocalDate.now())
                .transactionAmount(new BigDecimal(1126))
                .pointsAwarded(2002)
                .customer(customer)
                .build();
    }

    @Test
    void testPersistsNewTransactionAndTestByAmount() {
        var saveTransaction = testEntityManager.persistAndFlush(transaction);
        then(saveTransaction.getTransactionAmount()).isEqualTo(new BigDecimal(1126));
    }

    @Test
    void testCollectPointsFrom_A_SingleCustomerByGivenDates(){
        var responseDto = transactionRepository.collectPointsByCustomerId(3L, LocalDate.of(2023, 1, 1), LocalDate.of(2023, 4, 1));
        then(responseDto).isNotNull();
        then(responseDto.getTotalPointsEarnd()).isEqualTo(4008);
    }


    @Test
    void testGetTransactionSummary_From_AllCustomers_ByDate(){
        var responseDto = transactionRepository.getTransactionsSummaryByDate(LocalDate.of(2023, 1, 1), LocalDate.of(2023, 4, 1));
        then(responseDto).isNotNull();
        then(responseDto.size()).isGreaterThan(0);
        then(responseDto.get(1).getTotalAmoutSpent()).isGreaterThan(new BigDecimal(0));
    }

}
