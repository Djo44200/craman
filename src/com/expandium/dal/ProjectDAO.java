package com.expandium.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.expandium.beans.Project;
import com.expandium.beans.Team;
import com.expandium.util.DBConnection;

public class ProjectDAO {
	
	/**
	 * projects-related queries
	 */
	private final static String SEARCH_BY_NAME = "SELECT * FROM Projects WHERE name=?";
	private final static String LIST_PROJECTS = "SELECT * FROM Projects JOIN Projects_has_Teams ON Projects.idProjects = Projects_has_Teams.Projects_idProjects ORDER BY Projects.name ;";
	private final static String INSERT_PROJECT1 = "INSERT INTO Projects(name) VALUES (?);";
	private final static String INSERT_PROJECT2 = "INSERT INTO Projects_has_Teams VALUES(?,?,?);";
	
	
	// Search All Projects
		public static ArrayList<Project> findAll() throws DALException {
			Connection cnx = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			ArrayList<Project> listProjects = new ArrayList<>();
			

			try {
				// Connection DB
				cnx = DBConnection.connect();
				pstmt = cnx.prepareStatement(LIST_PROJECTS);
				rs = pstmt.executeQuery();
				
				// For each project
				while (rs.next()) {
					Project project = new Project();
					project.setIdProject(rs.getInt("idProjects"));
					project.setName(rs.getString("name"));
					project.setIdTeam(rs.getInt("Teams_idTeams"));
					project.setNameTeam(rs.getString("Teams_name"));
					listProjects.add(project);

				}
			} catch (SQLException e) {

				throw new DALException("Problem - listProjects - ProjectDAO - listProjects : "+listProjects+" "+ e.getMessage());
			} finally {
				try {

					if (pstmt != null)
						pstmt.close();
					if (cnx != null)
						cnx.close();
				} catch (SQLException e) {
					throw new DALException("Problem - Closing connection - listProjects - ProjectDAO " + e.getMessage());
				}
			}

			return listProjects;

		}
		
		// Search project by id and return Project Object
				public static int findByName(String name) throws DALException {

					Connection cnx = null;
					PreparedStatement pstmt = null;
					ResultSet rs = null;
					int idProject = 0;

					try {
						// Connection DB
						cnx = DBConnection.connect();
						pstmt = cnx.prepareStatement(SEARCH_BY_NAME);
						pstmt.setString(1, name);
						rs = pstmt.executeQuery();
						if (rs.next()) {
							idProject = rs.getInt("idProjects");
						}
					} catch (SQLException e) {
						throw new DALException("Problem - search project By name - ProjectDAO - Request : "+pstmt+ " " + e.getMessage());
					} finally {
						try {
							if (pstmt != null)
								pstmt.close();
							if (cnx != null)
								cnx.close();
						} catch (SQLException e) {
							throw new DALException("Problem - Closing connection - " + e.getMessage());
						}

					}

					return idProject;

				}

	
	// Create a project with a team relation
		public static boolean createWholeProject(Project project) throws DALException {
			Connection cnx = null;
			boolean result = false;
			PreparedStatement pstmt = null;
			try {
				// Insert just Project
				cnx = DBConnection.connect();
				pstmt = cnx.prepareStatement(INSERT_PROJECT1, Statement.RETURN_GENERATED_KEYS);
				pstmt.setString(1, project.getName());
				pstmt.executeUpdate();
				

				try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						project.setIdProject(generatedKeys.getInt(1));
						result = false;
					} else {
						throw new SQLException("Can not create project, no ID obtained. ");
					}
				}
				// Adding the relationship between the project and the team
				pstmt = cnx.prepareStatement(INSERT_PROJECT2);
				pstmt.setInt(1, project.getIdProject());
				pstmt.setInt(2, project.getIdTeam());
				pstmt.setString(3, project.getNameTeam());
				pstmt.executeUpdate();
				result = true;

			} catch (SQLException e) {

				throw new DALException("Problem - createWholeProject - ProjectDAO - Request : "+pstmt+ " " + e.getMessage());
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

		public static boolean createProjectRelations(Project project) throws DALException {
			Connection cnx = null;
			boolean result = false;
			PreparedStatement pstmt = null;
			try {
				// Adding the relationship between the project and the team
				cnx = DBConnection.connect();
				pstmt = cnx.prepareStatement(INSERT_PROJECT2);
				pstmt.setInt(1, project.getIdProject());
				pstmt.setInt(2, project.getIdTeam());
				pstmt.setString(3, project.getNameTeam());
				pstmt.executeUpdate();
				result = true;
			} catch (SQLException e) {
				throw new DALException("Problem - createProjectRelations - ProjectDAO - Request : " +pstmt+" "+ e.getMessage());
			}finally {
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

}
