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

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.mixtri.DAO.MixtriDAO;
import com.mixtri.login.UserLoginBean;

@Path("/")
public class ChangePasswordService {
	
	
	static Logger log = Logger.getLogger(ChangePasswordService
			.class.getName());
	
	private String SENDER_EMAIL_ID="bhutanihitesh@gmail.com";
	private String PASSWORD="fxarapxzwmmlrjrn";
	
	
	@Path("/changePasswordLink")
	@POST
	public Response changePassword(@FormParam("emailId") String recipientEmail,@FormParam("baseURL") String baseURL){
		
		Gson gson = new Gson();
		Map<String,String> mapResponse = new HashMap<String, String>();
		String response;
		try{
		UserLoginBean userLoginBean = new UserLoginBean();
		userLoginBean.setEmailId(recipientEmail);
		MixtriDAO mixtriDAO = new MixtriDAO();
		userLoginBean = mixtriDAO.retriveLoginInfoDAO(userLoginBean);
		
		if(userLoginBean.isUsernameAuthenticated()){
			
			String emailToken = mixtriDAO.getChangePasswordHashCodeDAO(recipientEmail);
			String changePasswordLink=baseURL+"/mixtri/changePasswordPage.jsp?emailId="+recipientEmail+"&emailToken="+emailToken;
			Boolean emailSent = sendEmail(recipientEmail,changePasswordLink);
			
			if(emailSent){
				
				mapResponse.put("changePasswordLink", changePasswordLink);
				response = gson.toJson(mapResponse);
			}
			
		}else{
			
			mapResponse.put("error", "This account is not listed with us. Please create an account!");
		    log.debug("Invalid emailId while changing password.");
		    response = gson.toJson(mapResponse);	
		}
		
		response = gson.toJson(mapResponse);
		
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
			//message.setContent("<p>Congratulation Darling..... For your first Job... :-*</p>", "text/html" );
			message.setText("Hi there,\nYou requested to change your password for your mixtri account. Click on the link below or copy "
					+ "and paste it in the browser:\n\n"+changePasswordLink+"\n \n This link will expire in 24 hrs. Please donnot reply to this email.\n\nRegards,\n"
					+ "Team Mixtri");
			Transport.send(message);

		} catch (MessagingException e) {
			emailSent = false;
			throw new RuntimeException(e);
		}
		
		return emailSent;
	}

}
