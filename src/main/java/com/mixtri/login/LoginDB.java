package com.mixtri.login;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

import com.mixtri.login.UserLoginBean;
import com.mixtri.signup.SaltedMD5;
import com.mixtri.database.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LoginDB {

	static Logger log = Logger.getLogger(LoginDB.class.getName());
	Connection connection;
	PreparedStatement statement;

	private static Connection getConnection() 
			throws SQLException, 
			ClassNotFoundException 
	{
		Connection con = ConnectionFactory.
				getInstance().getConnection();
		return con;
	}

	public UserLoginBean getLoginInfoDB(UserLoginBean userLoginBean) throws ClassNotFoundException, SQLException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{

		String query = "SELECT EMAILID,PASSWORD,SALT,DISPLAYNAME,PROFILEURLID FROM MIXTRI.USERS WHERE EMAILID=?";
		ResultSet rs =null;
		try{

			connection = getConnection();
			statement = connection.prepareStatement(query);    
			statement.setString(1, userLoginBean.getEmailId());    
			rs = statement.executeQuery();
			
			while(rs.next()){
				
				String emailIdDB = rs.getString("emailId");
				String passwordDB = rs.getString("password");
				String salt = rs.getString("salt");
				String displayName = rs.getString("displayName");
				String emailIdFromUI = userLoginBean.getEmailId();
				String pwdFromUI = userLoginBean.getPassword();
				String profileURLId = rs.getString("profileURLId");

				SaltedMD5 saltedMD5 = new SaltedMD5();
				
				if(passwordDB==null){
					
					if(emailIdFromUI.equalsIgnoreCase(emailIdDB)){
						userLoginBean.setUsernameAuthenticated(true);
					}else{
						userLoginBean.setUsernameAuthenticated(false);
					}
					
					userLoginBean.setPasswordAuthenticated(false);
					return userLoginBean;
				}
				String hashedPassword = saltedMD5.getSecurePassword(pwdFromUI,salt);

				if(emailIdFromUI.equalsIgnoreCase(emailIdDB)){

					userLoginBean.setUsernameAuthenticated(true);
					log.debug("Username Authenticated: "+emailIdDB);

					if(hashedPassword.equals(passwordDB)){
						userLoginBean.setPasswordAuthenticated(true);
						userLoginBean.setDisplayName(displayName);
						userLoginBean.setProfileURLId(profileURLId);
						log.debug("password authenticated");
					}

				}else{
					userLoginBean.setUsernameAuthenticated(false);
				}
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

		return userLoginBean;

	}

}
