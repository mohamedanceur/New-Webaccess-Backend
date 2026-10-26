package com.Atos.Webaccess.security.entities;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import com.Atos.Webaccess.User.Entities.User;

@Entity
@Table(name = "auth_tokens")
public class AuthToken {

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id" , nullable = false, unique = true)
	private String id;
	
	@Column(name = "last_acces_time", nullable =false)
	private LocalDateTime lastAccesTime;
	
	@ManyToOne
	@JoinColumn(name ="user_id", referencedColumnName = "id")
	private User user;
	
	public AuthToken() {
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public LocalDateTime getLastAccesTime() {
		return lastAccesTime;
	}

	public void setLastAccesTime(LocalDateTime lastAccesTime) {
		this.lastAccesTime = lastAccesTime;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}
