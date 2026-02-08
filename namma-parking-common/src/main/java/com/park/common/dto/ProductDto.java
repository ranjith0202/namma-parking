package com.park.common.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class ProductDto extends BaseDto{
	private Long id;

    @NotBlank(message = "Product name cannot be empty")
    private String name;

    private String description;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than zero")
    private Double price;

    @NotNull(message = "Available Quantity is required")
    @PositiveOrZero(message = "Available Quantity cannot be negative")
    private Integer availableQuantity;

    private String category;

    @NotBlank(message = "Unit cannot be empty")
    private String unit;

    private Boolean status = true;
}
