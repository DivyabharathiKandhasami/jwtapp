package com.usermanagent.jwtapp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Author")
public class AuthRequest {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	public int auth_id;

	@Column(name = "user_name")
	public String userName;
	
	@Column(name = "password")
	public String password;

	public String getUserName() {

		return userName;
	}

	public String getPassword() {
		return password;
	}

}
