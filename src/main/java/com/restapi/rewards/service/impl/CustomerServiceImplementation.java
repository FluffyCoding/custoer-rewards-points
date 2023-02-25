package com.restapi.rewards.service.impl;

import com.restapi.rewards.dto.CustomerRequestDto;
import com.restapi.rewards.dto.CustomerResponseDto;
import com.restapi.rewards.dto.CustomerUpdateRequestDto;
import com.restapi.rewards.dto.RewardPointsResponseDto;
import com.restapi.rewards.entity.Customer;
import com.restapi.rewards.exception.EmailExistException;
import com.restapi.rewards.repository.CustomerRepository;
import com.restapi.rewards.repository.TransactionRepository;
import com.restapi.rewards.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class CustomerServiceImplementation implements CustomerService {

    private final CustomerRepository customerRepository;
    private final TransactionRepository transactionRepository;
    private final ModelMapper customerMapper;

    @Override
    public List<CustomerResponseDto> findAllCustomers() {
        return customerRepository.findAll()
                .stream().map(customer -> customerMapper.map(customer, CustomerResponseDto.class)).toList();
    }

    @Override
    public CustomerResponseDto findCustomer(Long id) {
        return customerMapper.map(customerRepository.findById(id), CustomerResponseDto.class);
    }

    @Override
    public CustomerResponseDto saveNewCustomer(CustomerRequestDto requestDto) {

        customerRepository.findByEmailIgnoreCase(requestDto.getEmail()).ifPresent(
                c -> {
                    log.error("Cannot Register New Customer. email {} already exists", c);
                    throw new EmailExistException("Email already exist");
                });
        return customerMapper.map(customerRepository.save(customerMapper.map(requestDto, Customer.class)), CustomerResponseDto.class);
    }

    @Override
    public CustomerResponseDto updateCustomer(CustomerUpdateRequestDto customerDto) {
        Customer customer = customerMapper.map(customerDto, Customer.class);
        return customerMapper.map(customerRepository.save(customer), CustomerResponseDto.class);
    }

    @Override
    public void deleteExistingCustomer(Long id) {
        customerRepository.deleteById(id);
    }


    @Override
    @Transactional
    public RewardPointsResponseDto getCustomerRewardPoints(Long customerId, LocalDate startDate, LocalDate endDate) {
        return transactionRepository.collectPointsByCustomerId(customerId, startDate, endDate);

    }
}
