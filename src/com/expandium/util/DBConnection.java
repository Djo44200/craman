package com.expandium.util;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DBConnection {
	public static Connection connect()throws SQLException {
		Connection cnx = null;
		InitialContext jndi=null;
		DataSource ds=null;
		//----> Get a reference on the initial context JNDI
		try{
		jndi=new InitialContext();}
		catch(NamingException e){
			throw new SQLException("Initial context access error JNDI");}
		//----> search for the connection pool in the directory
		try{
		ds=(DataSource)jndi.lookup("java:comp/env/project_CRA");}
		catch(NamingException e){
			throw new SQLException("Object not found JNDI:"+e.getMessage());
		}
		//----> get a connection
		try{
			cnx = ds.getConnection();
			return cnx;
		}
		catch(SQLException e){
			throw new SQLException("Can not get a connection:"+e.getMessage());}
		}
}
