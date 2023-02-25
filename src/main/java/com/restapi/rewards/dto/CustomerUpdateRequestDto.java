package com.restapi.rewards.dto;

import com.restapi.rewards.entity.Customer;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * A DTO for the {@link Customer} entity
 */
@Data
public class CustomerUpdateRequestDto implements Serializable {

    @NotNull
    private Long id;
    @Size(min = 10, max = 50, message = "first name should be at least 10 and less than 50 character")
    @NotNull
    private String firstName;
    @Size(min = 10, max = 50, message = "last name should be at least 10 and less than 50 character")
    @NotNull
    private String lastName;
}