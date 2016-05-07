package com.mixtri.login;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.jws.WebService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.FormParam;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.mixtri.DAO.MixtriDAO;
import com.mixtri.login.UserLoginBean;


@WebService
@Path("/")
public class Userlogin{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static Logger log = Logger.getLogger(Userlogin.class.getName());
	
@Context private HttpServletRequest request;	
//@Resource	
@POST
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
@Path("/login")
public Response authenticate(@FormParam("emailId") String emailId, @FormParam("password")String password) {
	  
	  UserLoginBean userLoginBean = new UserLoginBean();
	  HttpSession session = request.getSession(true);
	  
	  Map<String,String> serverResponse = new LinkedHashMap<String,String>();
	  Gson gson = new Gson();
	  String json;
	  
	  
	 try{ 
	  userLoginBean.setEmailId(emailId);
	  userLoginBean.setPassword(password);
	  
	  MixtriDAO mixtriDAO = new MixtriDAO();
	  userLoginBean = mixtriDAO.retriveLoginInfoDAO(userLoginBean);
	  
	  if(!userLoginBean.getEmailId().isEmpty() && userLoginBean.isUsernameAuthenticated()){
	  
		  if(!userLoginBean.getPassword().isEmpty() && userLoginBean.isPasswordAuthenticated()){
			  serverResponse.put("displayName", userLoginBean.getDisplayName());
			  serverResponse.put("emailId", userLoginBean.getEmailId());
			  serverResponse.put("profileURLId",userLoginBean.getProfileURLId());
			  
			  session.setAttribute("displayname",serverResponse);
		  }
			  
		  else{
			  serverResponse.put("error", "Thats an incorrect password. Please try again!");
			  log.debug("Invalid password");
			  json = gson.toJson(serverResponse);
			  return Response.ok(json,MediaType.APPLICATION_JSON).build();
			  
			}  
	  }else{
		    serverResponse.put("error", "Hey, you are not listed with us. Please create an account!");
		    log.debug("Invalid emailId");
		    json = gson.toJson(serverResponse);
			return Response.ok(json,MediaType.APPLICATION_JSON).build();
		  	  		  
	  }
	}catch(SQLException sqlExp){
		log.error("SQL Exception Occured: "+sqlExp);
		return Response.serverError().build();
		
	}
	 catch(Exception exp){ 
		 log.error("Exception Occured UserLogin: authenticate method: "+exp);
		 return Response.serverError().build();
	}
	 
	 json = gson.toJson(serverResponse);
	 return Response.ok(json,MediaType.APPLICATION_JSON).build();
 }

} 