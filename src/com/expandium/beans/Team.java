package com.expandium.beans;

import java.io.Serializable;

public class Team implements Serializable {
	/**
	 * Class Team
	 */
	
	private int idTeam;
	private String name;
	private int idProject;
	private String nameProject;
	
	
	// Constructor without parameters
	public Team() {
		super();
	}

	// Constructor
	public Team(int idTeam, String name) {
		super();
		this.idTeam = idTeam;
		this.name = name;
	}
	
	
	// Getters/Setters

	public int getIdTeam() {
		return idTeam;
	}

	public void setIdTeam(int idTeam) {
		this.idTeam = idTeam;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
	//Method toString

	public int getIdProject() {
		return idProject;
	}

	public void setIdProject(int idProject) {
		this.idProject = idProject;
	}

	public String getNameProject() {
		return nameProject;
	}

	public void setNameProject(String nameProject) {
		this.nameProject = nameProject;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Team [idTeam=");
		builder.append(idTeam);
		builder.append(", name=");
		builder.append(name);
		builder.append(", idProject=");
		builder.append(idProject);
		builder.append(", nameProject=");
		builder.append(nameProject);
		builder.append("]");
		return builder.toString();
	}

	
	
	
	
	
	

}
