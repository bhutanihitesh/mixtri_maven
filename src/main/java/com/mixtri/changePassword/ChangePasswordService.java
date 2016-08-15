package com.mixtri.changePassword;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.mixtri.DAO.MixtriDAO;
import com.mixtri.login.UserLoginBean;
import com.mixtri.profile.Profile;

@Path("/")
public class ChangePasswordService {
	
	
	static Logger log = Logger.getLogger(ChangePasswordService
			.class.getName());
	
	private String SENDER_EMAIL_ID="bhutanihitesh@gmail.com";
	private String PASSWORD="fxarapxzwmmlrjrn";
	
	
	
	@Path("validateUser")
	@POST
	public Response validateUser(@FormParam("emailId") String recipientEmail){

		try{
			UserLoginBean userLoginBean = new UserLoginBean();
			userLoginBean.setEmailId(recipientEmail);
			MixtriDAO mixtriDAO = new MixtriDAO();
			userLoginBean = mixtriDAO.retriveLoginInfoDAO(userLoginBean);

			if(!userLoginBean.isUsernameAuthenticated()){

				log.debug("Change Password: User name is not authenticated: "+userLoginBean.isUsernameAuthenticated());
				log.debug("Invalid emailId while changing password.");
				return Response.status(Status.BAD_REQUEST).entity("This account is not listed with us. Please create an account!").build();


			}

		}catch(SQLException sqlExp){
			log.error("SQL Exception Occured: "+sqlExp);
			return Response.serverError().build();

		}
		catch(Exception exp){ 
			log.error("Exception Occured UserLogin: authenticate method: "+exp);
			return Response.serverError().build();
		}
		return Response.ok().build();
	}
	
	
	@Path("sendPasswordLink")
	@POST
	public Response sendChangePasswordLink(@FormParam("emailId") String recipientEmail,@FormParam("baseURL") String baseURL){

		Map<String,String> mapResponse = new HashMap<String, String>();
		String response=null;
		Gson gson = new Gson();
		MixtriDAO mixtriDAO = new MixtriDAO();
		try{
			String emailToken = mixtriDAO.getChangePasswordHashCodeDAO(recipientEmail);
			String changePasswordLink=baseURL+"/mixtri/change-password.jsp?emailId="+recipientEmail+"&emailToken="+emailToken;

			log.debug("Change Password: Link: "+changePasswordLink);
			Boolean emailSent = sendEmail(recipientEmail,changePasswordLink);

			if(emailSent){

				mapResponse.put("changePasswordLink", changePasswordLink);
				response = gson.toJson(mapResponse);
			}

		}catch(SQLException sqlExp){
			log.error("SQL Exception Occured: "+sqlExp);
			return Response.serverError().build();

		}
		catch(Exception exp){ 
			log.error("Exception Occured UserLogin: authenticate method: "+exp);
			return Response.serverError().build();
		}
		return Response.ok(response,MediaType.APPLICATION_JSON).build();	
	}
	

	
	@Path("changePassword")
	@POST
	public Response changePassword(@FormParam("emailId") String recipientEmail,@FormParam("emailToken") String emailToken,
			@FormParam("newPassword") String newPassword,@FormParam("confirmPassword") String confirmPassword){
		
		Response serviceResponse;
		
		boolean validEmailToken = false;
		
		try{
		
		UserLoginBean userLoginBean = new UserLoginBean();
		userLoginBean.setEmailId(recipientEmail);
		MixtriDAO mixtriDAO = new MixtriDAO();
		userLoginBean = mixtriDAO.retriveLoginInfoDAO(userLoginBean);
		
		if(userLoginBean.isUsernameAuthenticated()){
			
			validEmailToken = mixtriDAO.validateEmailTokenDAO(emailToken);
			if(!validEmailToken){
				
				return Response.status(Status.BAD_REQUEST).entity("Either the URL is incorrect or it has expired.").build();
				
			}else{
				
				Profile profile = new Profile();
				serviceResponse=profile.changePassword(recipientEmail, newPassword, confirmPassword);
				
			}
			
			
		}else{
			
			return Response.status(Status.BAD_REQUEST).entity("Either the URL is incorrect or it has expired.").build();
		}
		
		}catch(SQLException sqlExp){
			log.error("SQL Exception Occured: "+sqlExp);
			return Response.serverError().build();
			
		}
		 catch(Exception exp){ 
			 log.error("Exception Occured UserLogin: authenticate method: "+exp);
			 return Response.serverError().build();
		}
		
		return serviceResponse;
	}
	
	public boolean sendEmail(String recipientEmail,String changePasswordLink){
		   
		   
	    Boolean emailSent = true;
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(SENDER_EMAIL_ID, PASSWORD);
			}
		  });

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("bhutanihitesh@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
			InternetAddress.parse(recipientEmail));
			message.setSubject("Change your password");
			message.setText("Hi there,\n\nYou requested to change your password for your mixtri account. Click on the link below or copy "
			+ "and paste it in the browser:\n\n"+changePasswordLink+"\n \nThis link will expire in 24 hrs. Please donnot reply to this email.\n\nRegards,\n"
			+ "Team Mixtri");
			Transport.send(message);

		} catch (MessagingException e) {
			emailSent = false;
			throw new RuntimeException(e);
		}
		
		return emailSent;
	}

}
