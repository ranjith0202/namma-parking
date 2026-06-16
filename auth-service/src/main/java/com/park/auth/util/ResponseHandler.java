package com.park.auth.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.park.auth.dto.ApiResponse;

public class ResponseHandler {
	/**
     * Generate a success response
     * @param message - success message
     * @param data - payload
     * @param status - HTTP status
     */
    public static <T> ResponseEntity<ApiResponse<T>> success(
            String message, T data, HttpStatus status) {

        ApiResponse<T> response = new ApiResponse<>(
                status.value(),
                message,
                data
        );
        return new ResponseEntity<>(response, status);
    }

    /**
     * Generate an error response
     * @param message - error message
     * @param status - HTTP status
     */
    public static <T> ResponseEntity<ApiResponse<T>> error(
            String message, HttpStatus status) {

        ApiResponse<T> response = new ApiResponse<>(
                status.value(),
                message,
                null
        );
        return new ResponseEntity<>(response, status);
    }
}
