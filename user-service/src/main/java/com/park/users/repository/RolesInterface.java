package com.park.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.park.users.entity.Roles;



public interface RolesInterface extends JpaRepository<Roles, Long> {

}
