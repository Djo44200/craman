package com.expandium.rest;


import java.net.URI;
import java.util.ArrayList;


import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.expandium.beans.User;
import com.expandium.dal.DALException;
import com.expandium.dal.UserDAO;


@Path("/users")
public class RestUser {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<User> getListUsers() throws DALException {
		// The service returns a list of users
		ArrayList <User> users = new ArrayList<>();
		users = UserDAO.findAll();
		return users;
	}
	
	 
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserById(@PathParam("id") int id) throws DALException {
		// The service search a user by id
		User user = new User();
		user = UserDAO.findById(id);
	return Response.ok(user).build();
	}
	
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createUser(String addUser) throws DALException {
		// The service create a user, return a user id.
		
		User user= new User();
		String userName;
		String userFirstName;
		
		// Recovery of the JSON object. 
		org.json.JSONObject json = new org.json.JSONObject(addUser);
		userName = json.getString("userName");
		userFirstName = json.getString("userFirstName");
		user.setName(userName);
		user.setFirstName(userFirstName);
		
		UserDAO.createUser(user);
		
		
		return Response.status(Status.CREATED).build();
	}

}
