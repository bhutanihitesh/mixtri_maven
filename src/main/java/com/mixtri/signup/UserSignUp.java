package com.mixtri.signup;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.google.gson.Gson;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.jws.WebService;
import javax.rmi.CORBA.UtilDelegate;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.mixtri.BusinessExceptions.ExceptionHttpStatusResolver;
import com.mixtri.DAO.MixtriDAO;
import com.mixtri.database.ConnectionFactory;
import com.mixtri.signup.UserSignUpBean;
import com.mixtri.utils.MixtriUtils;

@Path("/")
@WebService
public class UserSignUp{
	static Logger log = Logger.getLogger(UserSignUp.class.getName());
	Connection connection;
	PreparedStatement statement;
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/signup")
	public Response createUser(@FormParam("displayName") String displayName, @FormParam("emailId") String emailId,@FormParam("contact") String contact, 
			@FormParam("Signup_password")String Signup_password, @FormParam("confirm_password")String confirm_password,@FormParam("place")String place,
			@FormParam("mixtriSignUp")boolean mixtriSignUp){

		boolean isUserCreated = false;
		UserSignUpBean  userSignUpBean = new UserSignUpBean();

		Map<String,String> serverResponse = new LinkedHashMap<String,String>();
		Gson gson = new Gson();
		String json = null;


		try{
			//Encrypting the password with MD5 and salting.
			//This means if the user is signing up using our system and not social signup then he cannot keep these as nulls	
			if(mixtriSignUp){

				if(displayName==null || emailId==null || contact==null || Signup_password==null || confirm_password==null || place == null){
					serverResponse.put("error", "You cannot have an empty field. Please provide some value");
					json = gson.toJson(serverResponse);
					return Response.ok(json, MediaType.APPLICATION_JSON).build(); 
				}

			}	
			boolean isValidEmail = MixtriUtils.isValidEmailAddress(emailId);
			if(!isValidEmail){
				serverResponse.put("error","Invalid Email Id");
				json = gson.toJson(serverResponse);
				return Response.ok(json, MediaType.APPLICATION_JSON).build();
			}

			boolean isValidPhoneNumber = true;

			if(contact!=null){

				isValidPhoneNumber = MixtriUtils.isValidPhonenumber(contact);
			}
			if(!isValidPhoneNumber){
				serverResponse.put("error", "Invalid phone number format");
				json = gson.toJson(serverResponse);
				return Response.ok(json, MediaType.APPLICATION_JSON).build();
			}

			if(Signup_password!=null && confirm_password!=null && !(Signup_password.equals(confirm_password)) ){
				serverResponse.put("error","Confirm password don't match");
				json = gson.toJson(serverResponse);
				return Response.ok(json, MediaType.APPLICATION_JSON).build();
			}

			SignUpDB signUpDB = new SignUpDB(); 
			
			connection = ConnectionFactory.getInstance().getConnection();
			
			ResultSet rs = signUpDB.accountExists(emailId,connection,statement);
			boolean isAccountExists = false;
			
			while(rs.next()){
				  
				if(rs.getString("EmailId")!=null & rs.getInt("Active")==1){
					
					log.debug("Account already Exists and is active "+rs.getString("EmailId"));
					isAccountExists = true;
					break;
				}	
			}

			//We create an account for the user in our database the very first time he login using social login else just log him in to mixtri if the account is already
			//there.
			Map<String,String> userData;
			if(isAccountExists && !mixtriSignUp){
				userData = signUpDB.getUserData(emailId);
				
				serverResponse.put("displayName",userData.get("displayName"));
				serverResponse.put("emailId",emailId);
				serverResponse.put("profileURLId",userData.get("profileURLId"));
				json = gson.toJson(serverResponse);
				return Response.ok(json,MediaType.APPLICATION_JSON).build();
				
			}else if(isAccountExists){
				
				serverResponse.put("error","Sorry this email id is already registered with us!");
				json = gson.toJson(serverResponse);
				return Response.ok(json, MediaType.APPLICATION_JSON).build();
			}

				String hashedPassword = null;

				if(Signup_password!=null){
					SaltedMD5 saltedMD5 = new SaltedMD5();
					hashedPassword = saltedMD5.generateSecurePassword(Signup_password,userSignUpBean);

				}
				
				userSignUpBean.setDisplayName(displayName);
				userSignUpBean.setEmailId(emailId);
				userSignUpBean.setPassword(hashedPassword);
				userSignUpBean.setContact(contact);
				Date utilDate = new Date();
				java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
				userSignUpBean.setCreateDate(sqlDate);
				String city = null;
				String state = null;
				String country = null;
				
				if(place!=null){
				
				 String arrPlace[] = place.split(",");
				 city = arrPlace[0];
				 state = arrPlace[1];
				 country = arrPlace[2];
				}
				userSignUpBean.setCity(city);
				userSignUpBean.setState(state);
				userSignUpBean.setCountry(country);

				MixtriDAO mixtriDAO = new MixtriDAO();
				userSignUpBean = mixtriDAO.createNewUserDAO(userSignUpBean);
				isUserCreated = userSignUpBean.isUsercreated();	

				if(isUserCreated){
					serverResponse.put("displayName",userSignUpBean.getDisplayName());
					serverResponse.put("emailId",userSignUpBean.getEmailId());
					serverResponse.put("profileURLId",userSignUpBean.getProfileURLId());
					json = gson.toJson(serverResponse);



				}

		}catch(SQLException sqlExp){
			log.error("SQL Exception Occured createUser Method: "+sqlExp.getStackTrace());
			return Response.serverError().build();


		}
		catch(Exception exp){ 
			log.error("Exception Occured UserSignup: CreateUser method: "+exp);
			return Response.serverError().build();
		}finally{
			
				if(connection!=null){
					try {
						connection.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
		}

		return Response.ok(json,MediaType.APPLICATION_JSON).build();
	}	

}
