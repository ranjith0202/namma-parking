package com.park.common.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ProductOrderDto {
    private Long id; // optional for create, required for update

    @NotNull(message = "Product ID is required")
    private Long productId;

    @NotBlank(message = "Customer name cannot be blank")
    private String customerName;

    @NotBlank(message = "Customer email is required")
    @Email(message = "Invalid email format")
    private String customerEmail;

    @NotNull(message = "Order quantity is required")
    @Positive(message = "Order quantity must be greater than zero")
    private Integer orderQuantity;

    @NotNull(message = "Order date is required")
    private LocalDateTime orderDate;

    @NotBlank(message = "Order status is required")
    private String status; // PENDING, COMPLETED, CANCELLED

}
