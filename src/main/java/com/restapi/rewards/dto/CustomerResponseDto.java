package com.restapi.rewards.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * A DTO for the {@link com.restapi.rewards.entity.Customer} entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerResponseDto {
    private  Long id;
    private  String firstName;
    private  String lastName;
    private  String email;
    private  List<TransactionResponseDto> transactionList;
}