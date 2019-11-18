package com.expandium.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.expandium.beans.Team;
import com.expandium.beans.User;
import com.expandium.util.DBConnection;

public class TeamDAO {

	/**
	 * teams-related queries
	 */
	private final static String LIST_TEAMS = "SELECT *, name FROM Teams ORDER BY name;";	
	private final static String LIST_TEAMS_PROJECTS = "SELECT Projects_has_Teams.Teams_idTeams,Projects_has_Teams.Teams_name,Projects.idProjects,Projects.name AS 'nameProject' FROM Projects_has_Teams INNER JOIN Projects ON Projects_has_Teams.Projects_idProjects = Projects.idProjects ORDER BY Projects_has_Teams.Teams_name, Projects.name;";
	private final static String SEARCH_ID_TEAM = "select * from Teams where idTeams = ?;";
	private final static String SEARCH_NAME_TEAM = "select * from Teams where name = ?;";
	private final static String INSERT_TEAM = "insert into Teams(name) values (?);";
	
	
	// Search All Teams
	public static ArrayList<Team> findAll() throws DALException {
		
		Connection cnx = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Team> listTeams = new ArrayList<>();

		try {
			// Connection DB
			cnx = DBConnection.connect();
			pstmt = cnx.prepareStatement(LIST_TEAMS);
			rs = pstmt.executeQuery();

			// For each team
			while (rs.next()) {
				Team team = new Team();
				team.setIdTeam(rs.getInt("idTeams"));
				team.setName(rs.getString("name"));
				listTeams.add(team);

			}
		} catch (SQLException e) {

			throw new DALException("Problem - listTeam - TeamDAO - List :"+ listTeams+ " Request: " +pstmt+ e.getMessage());
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

		return listTeams;
	}

	
	
	
	
	
	// Search All Teams and his Projects
		public static ArrayList<Team> findTeamProject() throws DALException {
			Connection cnx = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			ArrayList<Team> listTeams = new ArrayList<>();

			try {
				// Connection DB
				cnx = DBConnection.connect();
				pstmt = cnx.prepareStatement(LIST_TEAMS_PROJECTS);
				rs = pstmt.executeQuery();

				// For each team
				while (rs.next()) {
					Team team = new Team();
					team.setIdTeam(rs.getInt("Teams_IdTeams"));
					team.setName(rs.getString("Teams_name"));
					team.setIdProject(rs.getInt("idProjects"));
					team.setNameProject(rs.getString("nameProject"));
					listTeams.add(team);

				}
			} catch (SQLException e) {

				throw new DALException("Problem - listTeamHisProject - TeamDAO - Request : "+ pstmt+ " " + e.getMessage());
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

			return listTeams;

		}
		
		// Search team by name and return id
				public static int findByName(String teamName) throws DALException {

					Connection cnx = null;
					PreparedStatement pstmt = null;
					ResultSet rs = null;
					int idTeam = 0;

					try {
						// Connection DB
						cnx = DBConnection.connect();
						pstmt = cnx.prepareStatement(SEARCH_NAME_TEAM);
						pstmt.setString(1, teamName);
						rs = pstmt.executeQuery();
						
						if (rs.next()) {
							idTeam = rs.getInt("idTeams");
						}
						
						
					} catch (SQLException e) {
						throw new DALException("Problem - search teams By Name - TeamDAO - Request : "+pstmt+ " " + e.getMessage());
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

					return idTeam;

				}
		

		// Search team by id and return Team Object
		public static Team findById(int id) throws DALException {

			Connection cnx = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			Team team = new Team();

			try {
				// Connection DB
				cnx = DBConnection.connect();
				pstmt = cnx.prepareStatement(SEARCH_ID_TEAM);
				pstmt.setInt(1, id);
				rs = pstmt.executeQuery();

				if (rs.next()) {

					team.setIdTeam(rs.getInt("idTeams"));
					team.setName(rs.getString("name"));
					

				}
			} catch (SQLException e) {
				throw new DALException("Problem - search teams By Id - TeamDAO - Request :"+ pstmt+ " Team: " + team + e.getMessage());
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

			return team;

		}
	
		// Create a team
		public static boolean createTeam(Team team) throws DALException {
			Connection cnx = null;
			boolean result = false;
			PreparedStatement pstmt = null;
			try {
				cnx = DBConnection.connect();
				pstmt = cnx.prepareStatement(INSERT_TEAM, Statement.RETURN_GENERATED_KEYS);
				pstmt.setString(1, team.getName());
				pstmt.executeUpdate();
				result = true;

				try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						team.setIdTeam(generatedKeys.getInt(1));
						result = false;
					} else {
						throw new SQLException("Can not create team, no ID obtained.");
					}
				}

			} catch (SQLException e) {

				throw new DALException("Problem - createTeam - TeamDAO - Request : "+ pstmt + " Team: "+ team + " " + e.getMessage());
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


		
}
