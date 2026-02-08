package com.park.users.entity;

import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "roles")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserEntity extends BaseEntity{
	
	@Id
	@Column(name ="user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "user_name")
	private String userName;
	
	private String password;
	
	private String email;
	
	/*
	 * @ManyToMany(fetch = FetchType.EAGER)
	 * 
	 * @JoinTable( name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
	 * inverseJoinColumns = @JoinColumn(name = "role_id") )
	 * 
	 * private Set<Roles> roles;
	 * 
	 */
	  @ElementCollection
	    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
	    @Column(name = "role_id")
	    private List<Long> rolesIds;
	
	
}
