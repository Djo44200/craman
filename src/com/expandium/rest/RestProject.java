package com.expandium.rest;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import org.json.*;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.expandium.beans.Project;
import com.expandium.dal.DALException;
import com.expandium.dal.ProjectDAO;
import com.expandium.dal.TeamDAO;



@Path("/projects")
public class RestProject {
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Project> getListProject() throws DALException {
		// The service returns a list of projects with his team
		ArrayList <Project> projects = new ArrayList<>();
		projects = ProjectDAO.findAll();
		
		
		return projects;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createProject(String addProject) throws IOException, DALException {
		// The service create a project with team, return boolean.
		
		Project project = new Project();
		String projectName;
		String projectNew = null;
		String teamName;
		int teamId;
		int idProject;
		
	
		// Recovery of the JSON object. 
		org.json.JSONObject json = new org.json.JSONObject(addProject);
		
		if (json.getString("Project").equals("CREATE NEW PROJECT")) {
			projectName = json.getString("Project");
			projectNew = json.getString("NewProject");
			teamName = json.getString("Team");
			
		}else {
			projectName = json.getString("Project");
			teamName = json.getString("Team");
			
		}
		
		// Retrieving the id Team for add Project
		teamId = TeamDAO.findByName(teamName);
		

		
		if (projectName.equals("CREATE NEW PROJECT")) {
			
			// New Project
			project.setIdTeam(teamId);
			project.setName(projectNew);
			project.setNameTeam(teamName);
			ProjectDAO.createWholeProject(project);
			
		} else {
			
			// Existing project
			
			// Retrieving the id Project 
			idProject=ProjectDAO.findByName(projectName);
			
			project.setIdProject(idProject);
			project.setIdTeam(teamId);
			project.setName(projectName);
			project.setNameTeam(teamName);
			
			ProjectDAO.createProjectRelations(project);
		}
		
		
		
		return Response.ok().header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD").build();
	}

}
