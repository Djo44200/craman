package com.expandium.beans;

import java.io.Serializable;

public class User implements Serializable {
	/**
	 * Class User
	 */
	
	private int idUser;
	private String name;
	private String firstName;
	
	
	// Constructor
	public User(int idUser, String name, String firstName) {
		super();
		this.idUser = idUser;
		this.name = name;
		this.firstName = firstName;
	}

	// Constructor without parameters
	public User() {
		super();
	}
	
	
	// Setters / Getters
	
	
	public int getIdUser() {
		return idUser;
	}
	

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	
	
	
	// Method toString
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("User [idUser=");
		builder.append(idUser);
		builder.append(", name=");
		builder.append(name);
		builder.append(", firstName=");
		builder.append(firstName);
		builder.append("]");
		return builder.toString();
	}

	
	
	
	
	
	
	
	
	

}
