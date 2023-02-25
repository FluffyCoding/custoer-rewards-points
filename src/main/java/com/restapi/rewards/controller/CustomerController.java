package com.restapi.rewards.controller;

import com.restapi.rewards.dto.CustomerRequestDto;
import com.restapi.rewards.dto.CustomerResponseDto;
import com.restapi.rewards.dto.CustomerUpdateRequestDto;
import com.restapi.rewards.dto.RewardPointsResponseDto;
import com.restapi.rewards.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Tag(name = "Customer Api")
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/v1/customers/")
public class CustomerController {


    private final CustomerService customerService;

    @Operation(summary = "get all customers with transactions records", description = "This endpoint all customers")
    @GetMapping("/")
    public ResponseEntity<List<CustomerResponseDto>> getAllCustomer() {
        return ResponseEntity.ok(customerService.findAllCustomers());
    }
    @Operation(summary = "save new customer", description = "This endpoint save customer")
    @PostMapping("/")
    public ResponseEntity<CustomerResponseDto> saveNewCustomer(@Valid @RequestBody CustomerRequestDto customerRequestDto) {
        return ResponseEntity.ok(customerService.saveNewCustomer(customerRequestDto));
    }

    @Operation(summary = "update customers record", description = "This endpoint update customer")
    @PatchMapping("/")
    public ResponseEntity<CustomerResponseDto> updateCustomer(@Valid @RequestBody CustomerUpdateRequestDto updateRequestDto) {
        return ResponseEntity.ok(customerService.updateCustomer(updateRequestDto));
    }

    @Operation(summary = "delete customer transactions records", description = "This endpoint delete customers")
    @DeleteMapping("/")
    public ResponseEntity<String> deleteCustomer(@RequestParam(name = "id") Long id) {
        customerService.deleteExistingCustomer(id);
        return new ResponseEntity<>("Customer Entity is Deleted", HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Get Total Reward Points Of Selected Customer By Date", description = "This Endpoint Returns Total Reward Points of Selected Customer")
    @GetMapping("/points")
    public ResponseEntity<RewardPointsResponseDto> collectRewardPoints(@RequestParam Long customerId,
                                                                       @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                                       @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        var customerRewardPoints = customerService.getCustomerRewardPoints(customerId, startDate, endDate);
        return ResponseEntity.ok(customerRewardPoints);
    }

}

