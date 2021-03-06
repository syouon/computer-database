package com.excilys.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The Class Authority. Represents access rights for a User.
 * 
 * @see com.excilys.model.User
 */
@Entity
@Table(name = "authorities")
public class Authority implements Serializable {

	private static final long serialVersionUID = 8772536767806788663L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	/** Access rigths all seperated by a comma */
	@Column(name = "authority")
	private String role;

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
