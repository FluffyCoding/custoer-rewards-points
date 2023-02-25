package com.restapi.rewards;

import com.restapi.rewards.entity.Customer;
import com.restapi.rewards.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Collections;

import static org.assertj.core.api.BDDAssertions.then;

@DataJpaTest
class CustomerRepositoryTests {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    Customer customer;

    @BeforeEach
    void init() {
        customer = new Customer(null, "Jamal", "Khan", "jamla@mail.com", Collections.emptyList());

    }

    @Test
    void testPersistsNewCustomerAndTestByEmail() {
        var customerSaved = testEntityManager.persistAndFlush(customer);
        then(customerSaved.getEmail()).isNotNull();
        then(customerSaved.getEmail()).isEqualTo("jamla@mail.com");
    }

    @Test
    void testTotalFiveRecordsShouldBeInCustomerTable() {
        // five records are already in test database
        var customers = customerRepository.findAll();
        then(customers.size()).isEqualTo(5);
    }

    @Test
    void testFindCustomerByEmailAddressShouldBeSuccess() {
        testEntityManager.persistAndFlush(customer);
        var customer = customerRepository.findByEmailIgnoreCase("jamla@mail.com").get();
        then(customer.getEmail()).isEqualTo("jamla@mail.com");
        then(customer.getFirstName()).isEqualTo("Jamal");
    }


}
