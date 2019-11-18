package com.expandium.rest;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.expandium.beans.Team;
import com.expandium.beans.User;
import com.expandium.dal.DALException;
import com.expandium.dal.TeamDAO;
import com.expandium.dal.UserDAO;


@Path("/teams")
public class RestTeam {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Team> getListTeams() throws DALException {
		// The service returns a list of teams
		ArrayList <Team> teams = new ArrayList<>();
		teams = TeamDAO.findAll();
		
		
		return teams;
	}
	
	@GET
	@Path("/teamsProject")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Team> getListTeamsandProject() throws DALException {
		// The service returns a list of teams
		ArrayList <Team> teams = new ArrayList<>();
		teams = TeamDAO.findTeamProject();
		
		
		return teams;
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserById(@PathParam("id") int id) throws DALException {
		// The service search a team by id
		Team team = new Team();
		team = TeamDAO.findById(id);
	return Response.ok(team).build();
	}
	
	
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createTeam(Team team) throws DALException {
		// The service create a team, return a team id.
		
		boolean result = TeamDAO.createTeam(team);
		
		
		return Response.status(Status.CREATED).build();
	}
	

}
