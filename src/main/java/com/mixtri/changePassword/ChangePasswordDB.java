package com.mixtri.changePassword;

import com.mixtri.database.ConnectionFactory;
import com.mixtri.event.EventDB;
import com.mixtri.utils.MixtriUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import javax.mail.*;
import javax.mail.internet.*;

import org.apache.log4j.Logger;

public class ChangePasswordDB {

	
	static Logger log = Logger.getLogger(ChangePasswordDB.class.getName());
	Connection connection;
	PreparedStatement statement;

	private Connection getConnection() throws SQLException,ClassNotFoundException 
	{
		Connection con = ConnectionFactory.getInstance().getConnection();
		return con;
	}
	
	public String getChangePasswordHashCode(String recipientEmailId) throws SQLException, ClassNotFoundException{
		String hashCode;
		
		try{
		
		log.debug("Inside getChangePasswordHashCode");
			
		hashCode = MixtriUtils.getUUID();
		
		log.debug("Generated email Token: "+hashCode);
		
		ResultSet rs =null;
		boolean emailTokenExists=false;
		connection = getConnection();
		
		String sql="select id from mixtri.changepassword where emailId=?";
		
		statement = connection.prepareStatement(sql);    
		statement.setString(1, recipientEmailId);
		rs = statement.executeQuery();
		
		log.debug("Select query executed.");
		
		while(rs.next()){
			emailTokenExists = true;
			
			log.debug("emailTokenExists: "+emailTokenExists);
		}
		
		if(emailTokenExists){
			
			String queryUpdateToken = "update mixtri.changepassword set id=?,changePasswordTS=current_timestamp where emailId=?";
			
			statement = connection.prepareStatement(queryUpdateToken);    
			statement.setString(1, hashCode);
			statement.setString(2, recipientEmailId);
			statement.executeUpdate();
			
			log.debug("Updated existing email Token for emailId: "+recipientEmailId);
			
		}else{
			
			String queryInsertToken = "insert into mixtri.changepassword(id,emailId,changePasswordTS) values(?,?,current_timestamp)";
			
			statement = connection.prepareStatement(queryInsertToken);    
			statement.setString(1, hashCode);
			statement.setString(2, recipientEmailId);
			statement.executeUpdate();
	
			log.debug("Email Token Doesn't exists. Inserting a new one ");
			
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
		return hashCode;
	}
	
	public boolean validateEmailTokenDB(String emailToken) throws SQLException, ClassNotFoundException{
		
		boolean isValidToken = false;
		
		try{
		
		ResultSet rs =null;
		connection = getConnection();
		
		String sql="select id from mixtri.changepassword where id=?";
		
		statement = connection.prepareStatement(sql);    
		statement.setString(1, emailToken);
		rs = statement.executeQuery();
		
		log.debug("validateEmailTokenDB: Select query executed.");
		
		while(rs.next()){
			isValidToken = true;
			log.debug("emailTokenExists: "+isValidToken);
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
		
		return isValidToken;
		
	}
}
