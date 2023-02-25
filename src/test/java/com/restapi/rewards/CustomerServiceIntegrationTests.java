package com.restapi.rewards;

import com.restapi.rewards.dto.CustomerRequestDto;
import com.restapi.rewards.dto.CustomerResponseDto;
import com.restapi.rewards.entity.Customer;
import com.restapi.rewards.repository.CustomerRepository;
import com.restapi.rewards.repository.TransactionRepository;
import com.restapi.rewards.service.CustomerService;
import com.restapi.rewards.service.impl.CustomerServiceImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;

import static org.assertj.core.api.BDDAssertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ExtendWith(MockitoExtension.class)
class CustomerServiceIntegrationTests {

    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private CustomerService customerService;
    @Mock
    private ModelMapper mapper;
    Customer customerModel;
    CustomerRequestDto customerRequestDto;
    CustomerResponseDto customerResponseDto;



    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        customerModel = new Customer(1L, "Jamal", "Khan", "jamla@mail.com", Collections.emptyList());
        customerService = new CustomerServiceImplementation(customerRepository, transactionRepository, mapper);
        customerRequestDto = new CustomerRequestDto("Faisal", "Shahzad", "faisal@mail.com");
        customerResponseDto = CustomerResponseDto.builder().id(1L).firstName("Jamal").lastName("Khan").email("jamla@mail.com").build();
    }

    @Test
    void testShouldCreateNewCustomerIfCustomerDateIsValid() {
        given(mapper.map(customerRequestDto, Customer.class)).willReturn(customerModel);
        given(mapper.map(customerModel, CustomerResponseDto.class)).willReturn(customerResponseDto);

        when(customerRepository.save(any(Customer.class))).thenReturn(customerModel);
        var customer = customerService.saveNewCustomer(customerRequestDto);

        then(customer).isNotNull();
        then(customer.getFirstName()).isEqualTo("Jamal");

    }


}
