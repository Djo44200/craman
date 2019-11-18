package com.expandium.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.plaf.ListUI;

import com.expandium.beans.User;
import com.expandium.util.DBConnection;

public class UserDAO {

	/**
	 * user-related queries
	 */

	private final static String LIST_USERS = "select * from Users ORDER BY firstName;";
	private final static String SEARCH_ID_USER = "select * from Users where idUsers = ?;";
	private final static String INSERT_USER = "insert into Users(name, firstName) values (?,?);";

	// Search All User
	public static ArrayList<User> findAll() throws DALException {
		Connection cnx = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<User> listUsers = new ArrayList<>();

		try {
			// Connection DB
			cnx = DBConnection.connect();
			pstmt = cnx.prepareStatement(LIST_USERS);
			rs = pstmt.executeQuery();

			// For each user
			while (rs.next()) {
				User user = new User();
				user.setIdUser(rs.getInt("idUsers"));
				user.setName(rs.getString("name"));
				user.setFirstName(rs.getString("firstName"));
				listUsers.add(user);

			}
		} catch (SQLException e) {

			throw new DALException("Problem - listUser - UserDAO - List : "+listUsers+ " " + e.getMessage());
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

		return listUsers;

	}

	// Search user by id and return User Object
	public static User findById(int id) throws DALException {

		Connection cnx = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		User user = new User();

		try {
			// Connection DB
			cnx = DBConnection.connect();
			pstmt = cnx.prepareStatement(SEARCH_ID_USER);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();

			if (rs.next()) {

				user.setIdUser(rs.getInt("idUsers"));
				user.setName(rs.getString("name"));
				user.setFirstName(rs.getString("firstName"));

			}
		} catch (SQLException e) {
			throw new DALException("Problem - search user - UserDAO - Request : "+pstmt+ " User : "+user+ " " + e.getMessage());
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

		return user;

	}

	// Create a user
	public static boolean createUser(User user) throws DALException {
		Connection cnx = null;
		boolean result = false;
		PreparedStatement pstmt = null;
		try {
			cnx = DBConnection.connect();
			pstmt = cnx.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, user.getName());
			pstmt.setString(2, user.getFirstName());
			pstmt.executeUpdate();
			result = true;

			try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					user.setIdUser(generatedKeys.getInt(1));
					result = false;
				} else {
					throw new SQLException("Can not create user, no ID obtained.");
				}
			}

		} catch (SQLException e) {

			throw new DALException("Problem - createUser - UserDAO - Request : "+ pstmt + e.getMessage());
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
