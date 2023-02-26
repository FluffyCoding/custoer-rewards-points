package com.restapi.rewards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
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
public class TransactionRequestDto {

    @Schema(description = "amount value", example = "250.00")
    @Min(value = 1, message = "Transaction Value should be min 1 ")
    private BigDecimal transactionAmount;

    @NotNull
    @Schema(description = "Customer Id", example = "2")
    private Long customerId;

    @FutureOrPresent
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Schema(description = "Date", example = "2023-01-01")
    private LocalDate transactionDate;

}