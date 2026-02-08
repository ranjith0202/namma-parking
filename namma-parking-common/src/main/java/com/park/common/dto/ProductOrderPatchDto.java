package com.park.common.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ProductOrderPatchDto {
    private Long id; // optional for create, required for update

    private Long productId;

    private String customerName;

    @Email(message = "Invalid email format")
    private String customerEmail;

    @Positive(message = "Quantity must be greater than zero")
    private Integer quantity;

    private LocalDateTime orderDate;

    private String status; // PENDING, COMPLETED, CANCELLED

}
