package com.expandium.beans;

import java.io.Serializable;

public class Project implements Serializable {
	/**
	 * Class Project
	 */
	
	
	private int idProject;
	private String name;
	private int idTeam;
	private String nameTeam;
	
	
	// Constructor
	public Project(int idProject, String name) {
		super();
		this.idProject = idProject;
		this.name = name;
	}

	// Constructor without parameters
	public Project() {
		super();
	}
	
	
	
	
	
	public int getIdProject() {
		return idProject;
	}

	public void setIdProject(int idProject) {
		this.idProject = idProject;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

	public int getIdTeam() {
		return idTeam;
	}

	public void setIdTeam(int i) {
		this.idTeam = i;
	}

	public String getNameTeam() {
		return nameTeam;
	}

	public void setNameTeam(String nameTeam) {
		this.nameTeam = nameTeam;
	}



	// Method toString
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Project [idProject=");
		builder.append(idProject);
		builder.append(", name=");
		builder.append(name);
		builder.append(", idTeam=");
		builder.append(idTeam);
		builder.append(", nameTeam=");
		builder.append(nameTeam);
		builder.append("]");
		return builder.toString();
	}
	
	
	
	
	
	
	
}
