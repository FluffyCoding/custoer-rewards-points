package com.restapi.rewards.dto;

import com.restapi.rewards.entity.Customer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * A DTO for the {@link Customer} entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequestDto implements Serializable {
    @Size(min = 3, max = 50, message = "first name should be at least 10 and less than 50 character")
    @NotNull
    @Schema(description = "Customer First Name", example = "Faisal")
    private String firstName;
    @Size(min = 3, max = 50, message = "last name should be at least 10 and less than 50 character")
    @NotNull
    @Schema(description = "Customer Last Name", example = "Shahzad")
    private String lastName;
    @Size(max = 50)
    @Email(message = "should be a valid email address")
    @Schema(description = "Customer Email Address", example = "f.shahzad@hotmail.com")
    private String email;
}