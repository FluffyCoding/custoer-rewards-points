package com.restapi.rewards.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A DTO for the {@link com.restapi.rewards.entity.Transaction} entity
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TransactionResponseDto {
    private Long id;
    private BigDecimal transactionAmount;
    private Integer pointsAwarded;
    private LocalDate transactionDate;
}