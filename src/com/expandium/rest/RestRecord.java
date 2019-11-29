package com.expandium.rest;

import java.sql.Date;
import java.util.ArrayList;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.expandium.beans.Record;
import com.expandium.dal.DALException;
import com.expandium.dal.RecordDAO;


@Path("/records")
public class RestRecord {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Record> getListRecords() throws DALException {
		// The service returns a list of records with his projects
		ArrayList <Record> records = new ArrayList<>();
		records = RecordDAO.findAll();
		
		return records;
	}
	
	@GET
	@Path("/{startDate}/{endDate}/{idUser}")
	@Produces(MediaType.APPLICATION_JSON)
	
	public ArrayList<Record> getListRecordsPerDates(@PathParam("startDate") Date startDate,@PathParam("endDate") Date endDate,@PathParam("idUser") int idUser ) throws DALException {
		// The service returns a list of records with his projects
		
		ArrayList <Record> records = new ArrayList<>();
		records = RecordDAO.findByDates(startDate,endDate,idUser);
	
		return records;
	}
	
	@GET
	@Path("/quarters/{startDate}/{endDate}/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	
	public ArrayList<Record> getListQuartersPerDates(@PathParam("startDate") Date startDate,@PathParam("endDate") Date endDate,@PathParam("id") int idUser ) throws DALException {
		// The service returns a list of quarters with user
		
		ArrayList <Record> records = new ArrayList<>();
		records = RecordDAO.findQuartersByDates(startDate,endDate,idUser);
		return records;
	}
	
	@GET
	@Path("/trimester/{startDate}/{endDate}")
	@Produces(MediaType.APPLICATION_JSON)
	
	public ArrayList<Record> getListQuartersTrimersters(@PathParam("startDate") Date startDate,@PathParam("endDate") Date endDate ) throws DALException {
		// The service returns a list of quarters with projects and teams
	
		ArrayList <Record> records = new ArrayList<>();
		records = RecordDAO.findQuartersTrimester(startDate, endDate);
		return records;
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createRecord(Record record, @HeaderParam("Access-Control-Request-Headers") @Context HttpServletResponse servlerResponse) throws DALException {
		// The service create a record, return a record id.
	
		RecordDAO.createRecord(record);
		
		servlerResponse.addHeader("Allow-Control-Allow-Methods", "POST,GET,OPTIONS");
        servlerResponse.addHeader("Access-Control-Allow-Credentials", "true");
        servlerResponse.addHeader("Access-Control-Allow-Origin", "*");
        servlerResponse.addHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With");
        servlerResponse.addHeader("Access-Control-Max-Age", "60");
    	return Response.status(Status.NO_CONTENT).header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD").build();
    	}
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteMaRessource(Record record,@HeaderParam("Access-Control-Request-Headers") @Context HttpServletResponse servlerResponse) throws DALException {
		// This service delete a record id quarter = 0
		RecordDAO.deleteRecord(record);
		servlerResponse.addHeader("Allow-Control-Allow-Methods", "POST,GET,OPTIONS,DELETE");
        servlerResponse.addHeader("Access-Control-Allow-Credentials", "true");
        servlerResponse.addHeader("Access-Control-Allow-Origin", "*");
        servlerResponse.addHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With");
        servlerResponse.addHeader("Access-Control-Max-Age", "60");
    	return Response.status(Status.NO_CONTENT).header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD").build();	}
	
	

}
