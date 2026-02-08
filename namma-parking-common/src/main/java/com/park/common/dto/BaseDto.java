package com.park.common.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class BaseDto {
	private Long id;
	private String createdBy;
	private String modifiedBy;
	private LocalDateTime createdTime;
	private LocalDateTime modifiedTime;
	
	
}
