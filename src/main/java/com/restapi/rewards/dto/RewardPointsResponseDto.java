package com.restapi.rewards.dto;


import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public interface RewardPointsResponseDto {

    @Schema(description = "Customer Name")
    String getName();

    String getEmail();

    BigDecimal getTotalAmountSpent();

    Integer getTotalPointsEarnd();

}