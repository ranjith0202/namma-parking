package com.park.parking.entity;

import java.time.LocalDateTime;

import com.park.parking.util.UserContext;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public class BaseEntity {
	private String createdBy;
	private LocalDateTime createdTime;
	private String modifiedBy;
	private LocalDateTime modifiedTime;
	
	@PrePersist
	public  void onCreate() {
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
