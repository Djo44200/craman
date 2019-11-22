package com.expandium.dal;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

import com.expandium.beans.Record;
import com.expandium.beans.User;
import com.expandium.util.DBConnection;

public class RecordDAO {
	
	/**
	 * records-related queries
	 */
	
	private final static String LIST_RECORDS = "SELECT * FROM Records "
			+ "INNER JOIN Projects ON Records.Projects_idProjects = Projects.idProjects ORDER BY Projects.name;";
	private final static String INSERT_RECORD = "insert into Records(quarters,date,timestamp,Users_idUsers,Projects_idProjects, Teams_idTeams)"
			+ " values (?,?,?,?,?,?)ON DUPLICATE KEY UPDATE quarters=?;";
	private final static String DELETE_RECORD = "DELETE FROM Records WHERE date =? AND Users_idUsers=? AND Projects_idProjects=? AND Teams_idTeams=?;";
	private final static String SEARCH_RECORDS_PER_DATES_USER = "SELECT * FROM Records WHERE Users_idUsers=? AND date BETWEEN ? AND ?";
	private final static String SEARCH_QUARTER_BY_DATE = "SELECT date,timestamp, SUM(quarters) AS quarters FROM Records "
			+ "WHERE Users_idUsers=? AND date BETWEEN ? AND ? GROUP BY date, timestamp;";
	private final static String SEARCH_ALL_QUARTER_BY_TRIMESTER = "SELECT SUM(Records.quarters) AS quarters, Projects.name AS nameProject, Projects_has_Teams.Teams_name "
			+ "FROM Records LEFT JOIN Projects ON Records.Projects_idProjects = Projects.idProjects "
			+ "INNER JOIN Projects_has_Teams ON Projects_has_Teams.Projects_idProjects = Projects.idProjects WHERE Records.date BETWEEN ? AND ? "
			+ "GROUP BY Projects.name, Projects_has_Teams.Teams_name;";
	// Search All Records
			public static ArrayList<Record> findAll() throws DALException {
				Connection cnx = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				ArrayList<Record> listRecords = new ArrayList<>();
				

				try {
					// Connection DB
					cnx = DBConnection.connect();
					pstmt = cnx.prepareStatement(LIST_RECORDS);
					rs = pstmt.executeQuery();
					
					// For each project
					while (rs.next()) {
						Record record = new Record();
						record.setIdRecord(rs.getInt("idRecords"));
						record.setQuarter(rs.getInt("quarters"));
						record.setTimestamp(rs.getLong("timestamp"));
						record.setDate(rs.getDate("date"));
						record.setIdUser(rs.getInt("Users_idUsers"));
						record.setIdProject(rs.getInt("Projects_idProjects"));
						record.setIdTeam(rs.getInt("Teams_idTeams"));
						record.setNameProject(rs.getString("Projects.name"));
						listRecords.add(record);
					}
				} catch (SQLException e) {
					
					throw new DALException("Problem - listRecords - RecordDAO - List : "+listRecords+" " + e.getMessage());
				} finally {
					try {
						if (rs != null)
							rs.close();
						if (pstmt != null)
							pstmt.close();
						if (cnx != null)
							cnx.close();
					} catch (SQLException e) {
						throw new DALException("Problem - Closing connection - " + e.getMessage());
					}
				}

				return listRecords;

			}

			// Create a Record
			public static boolean createRecord(Record record) throws DALException {
				Connection cnx = null;
				boolean result = false;
				PreparedStatement pstmt = null;
				try {
					
					cnx = DBConnection.connect();
					pstmt = cnx.prepareStatement(INSERT_RECORD, Statement.RETURN_GENERATED_KEYS);
					pstmt.setFloat(1, record.getQuarter());
					pstmt.setDate(2, record.getDate(), Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris")));
					pstmt.setLong(3,record.getTimestamp());
					pstmt.setInt(4, record.getIdUser());
					pstmt.setInt(5, record.getIdProject());
					pstmt.setInt(6, record.getIdTeam());
					pstmt.setFloat(7, record.getQuarter());
					pstmt.executeUpdate();
					result = true;

					try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
						if (generatedKeys.next()) {
							record.setIdRecord(generatedKeys.getInt(1));
							result = false;
						} else {
							throw new SQLException("Can not create Record, no ID obtained.");
						}
					}
				} catch (SQLException e) {
					throw new DALException("Problem - createRecord - RecordDAO - Request : "+pstmt+ " - Record : " + record + e.getMessage());
				} finally {
					try {
						if (pstmt != null)
							pstmt.close();
						if (cnx != null)
							cnx.close();
					} catch (SQLException e) {
						throw new DALException("Problem - close Connection - " + e.getMessage());
					}
				}
				return result;
			}
			
			
			
			
			// Delete a record
			public static void deleteRecord(Record record) throws DALException {
				
				Connection cnx = null;
				boolean result = false;
				PreparedStatement pstmt = null;
				try {
					cnx = DBConnection.connect();
					pstmt = cnx.prepareStatement(DELETE_RECORD);
					pstmt.setDate(1, record.getDate(), Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris")));
					pstmt.setInt(2, record.getIdUser());
					pstmt.setInt(3, record.getIdProject());
					pstmt.setInt(4, record.getIdTeam());
					pstmt.executeUpdate();


				} catch (SQLException e) {

					throw new DALException("Problem - deleteRecord - RecordDAO - Resquest : "+pstmt+ " - Record : "+ record + e.getMessage());
				} finally {
					try {
						if (pstmt != null)
							pstmt.close();
						if (cnx != null)
							cnx.close();
					} catch (SQLException e) {
						throw new DALException("Problem - close Connection - " + e.getMessage());
					}
				}
				
				
				
			}
			
			// Search all records by dates and user
			public static ArrayList<Record> findByDates(Date startDate, Date endDate, int idUser) throws DALException {
			
				Connection cnx = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				
				ArrayList<Record> listRecord = new ArrayList<>();
				
				// Connection DB
				try {
					cnx = DBConnection.connect();
					pstmt = cnx.prepareStatement(SEARCH_RECORDS_PER_DATES_USER);
					pstmt.setInt(1, idUser);
					pstmt.setDate(2, startDate);
					pstmt.setDate(3, endDate);
					rs = pstmt.executeQuery();

					while (rs.next()) {
						Record record = new Record();
						record.setIdRecord(rs.getInt("idRecords"));
						record.setQuarter(rs.getFloat("quarters"));
						record.setDate(rs.getDate("date"));
						record.setIdUser(rs.getInt("Users_idUsers"));
						record.setIdProject(rs.getInt("Projects_idProjects"));
						record.setIdTeam(rs.getInt("Teams_idTeams"));
						record.setTimestamp(rs.getLong("timestamp"));
						record.setIdTeam(rs.getInt("Teams_idTeams"));
						listRecord.add(record);
					
						

					}
				} catch (SQLException e) {
					
					throw new DALException("Problem - SearchRecord - RecordDAO - Request : "+ pstmt + e.getMessage());
				}finally {
					try {
						if (rs != null)
							rs.close();
						if (pstmt != null)
							pstmt.close();
						if (cnx != null)
							cnx.close();
					} catch (SQLException e) {
						throw new DALException("Problem - Closing connection - " + e.getMessage());
					}
				}
				
				return listRecord;
			}
	
	
			// Search all quarters by dates and user
			public static ArrayList<Record> findQuartersByDates(Date startDate, Date endDate, int idUser) throws DALException {
			
				Connection cnx = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				ArrayList<Record> listRecord = new ArrayList<>();
				
				// Connection DB
				try {
					cnx = DBConnection.connect();
					pstmt = cnx.prepareStatement(SEARCH_QUARTER_BY_DATE);
					pstmt.setInt(1, idUser);
					pstmt.setDate(2, startDate);
					pstmt.setDate(3, endDate);
					rs = pstmt.executeQuery();

					while (rs.next()) {
						Record record = new Record();
						record.setQuarter(rs.getFloat("quarters"));
						record.setTimestamp(rs.getLong("timestamp"));
						record.setDate(rs.getDate("date"));
						listRecord.add(record);
					
					}
				} catch (SQLException e) {
					
					throw new DALException("Problem - SearchQuarter - RecordDAO - Request : "+pstmt+ " " + e.getMessage());
				}finally {
					try {
						if (rs != null)
							rs.close();
						if (pstmt != null)
							pstmt.close();
						if (cnx != null)
							cnx.close();
					} catch (SQLException e) {
						throw new DALException("Problem - Closing connection - " + e.getMessage());
					}
				}
				
				return listRecord;
			}
			// Search quarters/project one trimester
			public static ArrayList<Record> findQuartersTrimester(Date startDate, Date endDate) throws DALException {
			
				Connection cnx = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				ArrayList<Record> listRecord = new ArrayList<>();
				
				// Connection DB
				try {
					cnx = DBConnection.connect();
					pstmt = cnx.prepareStatement(SEARCH_ALL_QUARTER_BY_TRIMESTER);
					pstmt.setDate(1, startDate);
					pstmt.setDate(2, endDate);
					rs = pstmt.executeQuery();

					while (rs.next()) {
						Record record = new Record();
						record.setQuarter(rs.getFloat("quarters"));
						record.setNameProject(rs.getString("nameProject"));
						record.setNameTeam(rs.getString("Teams_name"));
						listRecord.add(record);
					
					}
				} catch (SQLException e) {
					
					throw new DALException("Problem - SearchTrimester - RecordDAO - Request : "+ pstmt + e.getMessage());
				}finally {
					try {
						if (rs != null)
							rs.close();
						if (pstmt != null)
							pstmt.close();
						if (cnx != null)
							cnx.close();
					} catch (SQLException e) {
						throw new DALException("Problem - Closing connection - " + e.getMessage());
					}
				}
				
				return listRecord;
			}
	
			
}
