package com.mixtri.profile;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.mixtri.database.ConnectionFactory;
import com.mixtri.event.EventDB;
import com.mixtri.signup.UserSignUpBean;

public class ProfileDB {

	static Logger log = Logger.getLogger(ProfileDB.class.getName());
	Connection connection;
	PreparedStatement statement;

	private Connection getConnection() throws SQLException,ClassNotFoundException 
	{
		Connection con = ConnectionFactory.getInstance().getConnection();
		return con;
	}

	public Map<String,Object> getProfileInfoDB(String emailId) throws ClassNotFoundException, SQLException{

		ResultSet rs =null;
		Map<String,Object> profileInfo = new HashMap<String, Object>();
		try{

			String query = "SELECT * FROM MIXTRI.USERS WHERE emailId=?";
			connection = getConnection();
			statement = connection.prepareStatement(query);    
			statement.setString(1, emailId);
			rs = statement.executeQuery();
			String city;
			String state;
			String country;

			while(rs.next())
			{
				profileInfo.put("firstName", rs.getString("firstName"));
				profileInfo.put("lastName", rs.getString("lastName"));
				profileInfo.put("displayName", rs.getString("displayName"));

				city = rs.getString("city");
				state = rs.getString("state");
				country = rs.getString("country");

				String location = city+","+state+","+country;
				profileInfo.put("location",location);
				profileInfo.put("phoneNumber",rs.getString("phoneNumber"));
				profileInfo.put("biography",rs.getString("biography"));
				profileInfo.put("profilePicPath",rs.getString("profilePicPath"));

			}
		}finally
		{
			try {
				if(statement != null)
					statement.close();
				if(connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	
		return profileInfo;
	}

	public void updateProfileDB(UserSignUpBean userSignUpBean) throws SQLException, ClassNotFoundException{

		String firstName = userSignUpBean.getFirstName();
		String lastName = userSignUpBean.getLastName();
		String displayName = userSignUpBean.getDisplayName();
		String phoneNumber = userSignUpBean.getContact();
		String biography = userSignUpBean.getBiography();
		String profilePicPath = userSignUpBean.getProfilePicPath();
		String city = userSignUpBean.getCity();
		String state = userSignUpBean.getState();
		String country = userSignUpBean.getCountry();
		String emailId = userSignUpBean.getEmailId();

		String query = "UPDATE MIXTRI.USERS SET firstName=?,lastName=?,displayName=?,phoneNumber=?,biography=?,profilePicPath=?,city=?,state=?,country=? WHERE emailId=?";

		try{

			connection = getConnection();
			statement = connection.prepareStatement(query);    
			statement.setString(1, firstName);
			statement.setString(2, lastName);
			statement.setString(3, displayName);
			statement.setString(4, phoneNumber);
			statement.setString(5, biography);
			statement.setString(6, profilePicPath);
			statement.setString(7, city);
			statement.setString(8, state);
			statement.setString(9, country);
			statement.setString(10, emailId);
			statement.executeUpdate();
		}finally{

			try {
				if(statement != null)
					statement.close();
				if(connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public void changePasswordDB(UserSignUpBean userSignUpBean) throws ClassNotFoundException, SQLException{

		String emailId = userSignUpBean.getEmailId();
		String password = userSignUpBean.getPassword();
		String salt = userSignUpBean.getSalt();
		String query = "UPDATE MIXTRI.USERS SET password=?,salt=? WHERE emailId=?";

		try{

			connection = getConnection();
			statement = connection.prepareStatement(query);    
			statement.setString(1, password);
			statement.setString(2, salt);
			statement.setString(3, emailId);

			statement.executeUpdate();
		}finally{

			try {
				if(statement != null)
					statement.close();
				if(connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}


	}

	public void deleteAccountDB(String emailId) throws ClassNotFoundException, SQLException{

		try{
			String query = "DELETE FROM MIXTRI.USERS WHERE emailId=?";
			connection = getConnection();
			statement = connection.prepareStatement(query);    
			statement.setString(1, emailId);

			statement.executeUpdate();
		}finally{

			try {
				if(statement != null)
					statement.close();
				if(connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
