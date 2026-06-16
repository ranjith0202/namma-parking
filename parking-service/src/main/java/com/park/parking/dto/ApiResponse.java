package com.park.parking.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
	 private int status;             // HTTP status code
	    private String message;         // Success or error message
	   // private LocalDateTime timestamp; // Response timestamp
	    //private LocalDateTime timestamp = LocalDateTime.now();
	    private T data;                 // Payload (optional)
	    
}
