package com.restapi.rewards.dto;

import java.math.BigDecimal;

public interface CustomersTransactionSummryDto {

    String getCustomerName();

    Integer getTotalPointsEarned();

    BigDecimal getTotalAmoutSpent();

    int gettoTalOrdersPlaced();

}
