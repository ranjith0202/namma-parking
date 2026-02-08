package com.park.common.dto;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class PatchUpdateDto {
	private Long id;

    private String name;

    private String description;

    @Positive(message = "Price must be greater than zero")
    private Double price;

    @PositiveOrZero(message = "Quantity cannot be negative")
    private Integer quantity;

    private String category;

    private String unit;

    private Boolean status = true;
}
