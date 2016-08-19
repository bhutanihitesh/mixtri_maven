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
		
		Connection connection = getConnection();
		
		int insertedRows=0;

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
			  
			  
			 ResultSet rs1 = accountExists(emailId,connection,null);
			 boolean inactiveAccountExists = false;
			 
			 while(rs1.next()){
				  
				 //This condition tell if while creating an account, the account already exists and is inactive then activate the inactive account and update the info
					if(rs1.getString("EmailId")!=null & rs1.getInt("Active")==0){
						inactiveAccountExists = true;
						
						//If the account is getting reinstated then use the old profileURLID
						profileURLId = rs1.getString("profileURLId");
						break;
					}	
			 }
			 
			 
			 if(inactiveAccountExists){
				 
				 insertedRows = activateInactiveUser(uuid,emailId,password,salt,displayName,createDate,profileURLId,contact,city,state,country,showContactInfo);
				 
			 }else{
				 
				 insertedRows = createNewUser(uuid,emailId,password,salt,displayName,createDate,profileURLId,contact,city,state,country,showContactInfo);
			 }
		
		return insertedRows;
	}
	
	public int createNewUser(String uuid,String emailId,String password,String salt,String displayName,Date createDate,String profileURLId,String contact,String city,
							String state,String country,String showContactInfo) throws ClassNotFoundException, SQLException{
		
		int insertedRows=0;
		
		try{
			
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
	
	public int activateInactiveUser(String uuid,String emailId,String password,String salt,String displayName,Date createDate,String profileURLId,String contact,String city,
			String state,String country,String showContactInfo) throws ClassNotFoundException, SQLException{
		
		int insertedRows=0;
		try{
		Connection connection = getConnection();
		PreparedStatement statement;
		
		String query="Update mixtri.users set id=?,password=?,salt=?,displayName=?,createDate=?,phoneNumber=?,city=?,state=?,country=?,showContactInfo=?,active=1 where emailId=?";
		
		statement = connection.prepareStatement(query);
		
		  statement.setString(1, uuid);
		  statement.setString(2, password);
		  statement.setString(3, salt);
		  statement.setString(4, displayName);
		  statement.setDate(5, createDate);
		  statement.setString(6, contact);
		  statement.setString(7, city);
		  statement.setString(8, state);
		  statement.setString(9, country);
		  statement.setString(10, showContactInfo);
		  statement.setString(11, emailId);
		  
		  insertedRows = statement.executeUpdate();
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
	
	public ResultSet accountExists(String emailId, Connection connection, PreparedStatement statement) throws ClassNotFoundException, SQLException{
		 
			ResultSet rs = null;
			
		
			statement = connection.prepareStatement("SELECT EMAILID,Active,profileURLId FROM mixtri.users WHERE EMAILID=?");    
			statement.setString(1, emailId);    
			rs = statement.executeQuery();
			return rs;
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
