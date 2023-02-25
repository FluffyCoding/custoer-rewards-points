package com.restapi.rewards.service;

import com.restapi.rewards.dto.CustomerRequestDto;
import com.restapi.rewards.dto.CustomerResponseDto;
import com.restapi.rewards.dto.CustomerUpdateRequestDto;
import com.restapi.rewards.dto.RewardPointsResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface CustomerService {

    List<CustomerResponseDto> findAllCustomers();

    CustomerResponseDto findCustomer(Long id);

    CustomerResponseDto saveNewCustomer(CustomerRequestDto customer);

    CustomerResponseDto updateCustomer(CustomerUpdateRequestDto customer);

    void deleteExistingCustomer(Long id);

    RewardPointsResponseDto getCustomerRewardPoints(Long customerId, LocalDate startDate, LocalDate endDate);



}
