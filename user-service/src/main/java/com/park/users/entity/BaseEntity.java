package com.park.users.entity;

import java.time.LocalDateTime;

import com.park.users.util.UserContext;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

@Data
@MappedSuperclass
public class BaseEntity {
	private String createdBy;
	private String modifiedBy;
	private LocalDateTime createdTime;
	private LocalDateTime modifiedTime;
	
	@PrePersist
	public void onCreate() {
		this.createdTime = LocalDateTime.now();
		this.modifiedTime = LocalDateTime.now();
		this.createdBy = UserContext.getUser();
	}
	
	@PreUpdate
	public void onUpdate() {
		this.modifiedTime = LocalDateTime.now();
		this.modifiedBy = UserContext.getUser();
	}
	
}
