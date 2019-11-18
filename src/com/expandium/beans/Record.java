package com.expandium.beans;

import java.io.Serializable;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.TimeZone;

public class Record implements Serializable {
	
	/**
	 * Class Record
	 */
	// renommer quarter => quart de journée
	private int idRecord;
	private float quarter;
	private int idUser;
	private Date date;
	private int idProject;
	private String nameProject;
	private String nameTeam;
	private long timestamp;
	private int idTeam;
	
	// Constructor
	
	public Record(int idRecord, float quarter, int idUser, Date date, int idProject, String nameProject, String nameTeam, long timestamp, int idTeam) {
		super();
		this.idRecord = idRecord;
		this.quarter = quarter;
		this.idUser = idUser;
		this.date = date;
		this.idProject = idProject;
		this.nameProject = nameProject;
		this.nameTeam = nameTeam;
		this.timestamp = timestamp;
		this.idTeam = idTeam;
	}
	
	
	// Constructor without parameters
	public Record() {
		super();
	}


	// Getters and Setters
	public int getIdRecord() {
		return idRecord;
	}
	public void setIdRecord(int idRecord) {
		this.idRecord = idRecord;
	}
	public float getQuarter() {
		return quarter;
	}
	public void setQuarter(float quarter) {
		this.quarter = quarter;
	}
	
	public Date getDate() {
	
		java.sql.Date sqlDate = new java.sql.Date(this.getTimestamp());
		return sqlDate;
	}
	public void setDate(Date date) {
		this.date = date;
	}


	public int getIdUser() {
		return idUser;
	}


	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}


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

	public long getTimestamp() {
		return timestamp;
	}


	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public int getIdTeam() {
		return idTeam;
	}


	public void setIdTeam(int idTeam) {
		this.idTeam = idTeam;
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
		builder.append("Record [idRecord=");
		builder.append(idRecord);
		builder.append(", quarter=");
		builder.append(quarter);
		builder.append(", idUser=");
		builder.append(idUser);
		builder.append(", date=");
		builder.append(date);
		builder.append(", idProject=");
		builder.append(idProject);
		builder.append(", nameProject=");
		builder.append(nameProject);
		builder.append(", nameTeam=");
		builder.append(nameTeam);
		builder.append(", timestamp=");
		builder.append(timestamp);
		builder.append(", idTeam=");
		builder.append(idTeam);
		builder.append("]");
		return builder.toString();
	}
	
	
	
	
	

}
