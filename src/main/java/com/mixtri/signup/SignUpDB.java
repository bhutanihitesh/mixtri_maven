package com.mixtri.signup;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.mixtri.database.ConnectionFactory;
import com.mixtri.utils.MixtriUtils;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

public class SignUpDB {

	static Logger log = Logger.getLogger(SignUpDB.class.getName());
	Connection connection;
	PreparedStatement statement;

	private Connection getConnection() throws SQLException,ClassNotFoundException 
	{
		Connection con = ConnectionFactory.getInstance().getConnection();
		return con;
	}
	
	public int createNewUserDB(UserSignUpBean userSignUpBean) throws IOException, ClassNotFoundException, SQLException{
		
		int insertedRows=0;
		try{

			String displayName =  userSignUpBean.getDisplayName();
			String emailId = userSignUpBean.getEmailId();
			String contact = userSignUpBean.getContact();
			String password = userSignUpBean.getPassword();
			String salt = userSignUpBean.getSalt();
			Date createDate = userSignUpBean.getCreateDate();
			String city = null;
			String state=null;
			String country=null;
			
			if(userSignUpBean.getCity()!=null){
			 city = userSignUpBean.getCity().trim();
			}
			
			if(userSignUpBean.getState()!=null){
				state = userSignUpBean.getState().trim();
			}
			
			if(userSignUpBean.getCountry()!=null){
				country = userSignUpBean.getCountry().trim();
			}
			
			String showContactInfo = null;
			boolean profileNameExists=true;
			String profileURLId = displayName.replaceAll(" ","-");
			//This conditions continously checks DB to see if the same profile url is associated with the other Dj or not and then create unique profile url for every user
			while(profileNameExists){
				
				profileNameExists=profileNameExists(profileURLId);
				
				if(profileNameExists){
					profileURLId = createProfileURL(profileURLId);
				} 
			}
			  String uuid = MixtriUtils.getUUID();
			  userSignUpBean.setProfileURLId(profileURLId);
              
			  String query ="INSERT INTO mixtri.users(id,emailId,password,salt,displayName,createDate,profileURLId,phoneNumber,city,state,country,showContactInfo)"
			  		+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
			  
			  connection = getConnection();
			  statement = connection.prepareStatement(query);    
			  statement.setString(1, uuid);
			  statement.setString(2, emailId);
			  statement.setString(3, password);
			  statement.setString(4, salt);
			  statement.setString(5, displayName);
			  statement.setDate(6, createDate);
			  statement.setString(7, profileURLId);
			  statement.setString(8, contact);
			  statement.setString(9, city);
			  statement.setString(10, state);
			  statement.setString(11, country);
			  statement.setString(12, showContactInfo);
			  insertedRows = statement.executeUpdate();
			
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
		return insertedRows;
	}
	
	public String createProfileURL(String profileId){
		
		String lastTwoChars = profileId.substring(profileId.length() -2, profileId.length());
		//This means last two chars are number so increment
		boolean hasnumber = MixtriUtils.isNumeric(lastTwoChars);
		
		if(hasnumber){
			Integer number = Integer.parseInt(lastTwoChars);
			number++;
			
			String replaceWith = String.format("%02d", number);
			//Replace the last chars which are number with the inremental number
			profileId = MixtriUtils.replaceLastTwo(profileId,replaceWith);
		}else{
			//If last two chars are not number then simply add 01
			profileId =  profileId+"01";
		}
		
		return profileId;
	}
	
	public boolean accountExists(String emailId) throws ClassNotFoundException, SQLException{
		
		boolean emailExists = false;
		
		try{
			
			connection = getConnection();
			statement = connection.prepareStatement("SELECT EMAILID FROM mixtri.users WHERE EMAILID=?");    
			statement.setString(1, emailId);    
			ResultSet resultSet = statement.executeQuery();
			
			if(resultSet.next()){
				emailExists = true;
			}
			
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
		
		return emailExists;
		
	}
	
	public boolean profileNameExists(String profileURLName) throws ClassNotFoundException, SQLException{
		boolean profileURLNameExists=false;
		
		try{
			connection = getConnection();
			statement = connection.prepareStatement("SELECT PROFILEURLID FROM mixtri.users WHERE PROFILEURLID=?");    
			statement.setString(1, profileURLName);    
			ResultSet resultSet = statement.executeQuery();
			
			if(resultSet.next()){
				profileURLNameExists = true;
			}
			
			
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
		
		return profileURLNameExists;
	}
	
	public Map<String,String> getUserData(String emailId) throws ClassNotFoundException, SQLException{
		
		
		Map<String,String> userData = new HashMap<String, String>();
		try{
		connection = getConnection();
		statement = connection.prepareStatement("SELECT profileURLId,displayName FROM mixtri.users WHERE EMAILID=?");    
		statement.setString(1, emailId);    
		ResultSet rs = statement.executeQuery();
		
		while(rs.next()){
			userData.put("profileURLId", rs.getString("profileURLId"));
			userData.put("displayName", rs.getString("displayName"));
		}
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
		return userData;
	}
	

}
