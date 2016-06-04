package com.mixtri.profile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;


import org.glassfish.jersey.media.multipart.FormDataParam;

import com.google.gson.Gson;
import com.mixtri.DAO.MixtriDAO;
import com.mixtri.signup.SaltedMD5;
import com.mixtri.signup.UserSignUpBean;
import com.mixtri.uploader.Uploader;
import com.mixtri.utils.MixtriUtils;


@Path("profile")
public class Profile {

	static Logger log = Logger.getLogger(Profile.class.getName());
	
	static String UPLOAD_FILE_SERVER;
	static Properties prop;

	static{
		try{
			prop = new Properties();
			String path = new File("properties/mixtri.properties").getAbsolutePath();
			File file = new File(path);

			InputStream input = null;
			input = new FileInputStream(file);
			// load a properties file
			prop.load(input);
			UPLOAD_FILE_SERVER = prop.getProperty("BASE_PATH");
		}catch(Exception exp){
			log.error("Error Loading properties files "+exp.getStackTrace());
		}

	}

	@GET
	@Path("getProfileInfo")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProfileInfo(@QueryParam("emailId") String emailId){
		Map<String,Object> profileInfo;
		Gson gson = new Gson();
		try{

			MixtriDAO mixtriDAO = new MixtriDAO();
			profileInfo = mixtriDAO.getProfileInfoDAO(emailId);

		}catch(Exception exp){
			log.error("Exception Occured in getProfileInfo: "+exp);
			return Response.serverError().build();
		}

		String result = gson.toJson(profileInfo);

		return Response.ok(result,MediaType.APPLICATION_JSON).build();		
	}

	@POST
	@Path("/updateProfileInfo")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateProfileInfo(@FormParam("emailId") String emailId,@FormParam("firstName") String firstName,@FormParam("lastName") String lastName
			,@FormParam("displayName") String displayName,@FormParam("city") String city,@FormParam("state") String state,@FormParam("country") String country,
			@FormParam("phoneNumber") String phoneNumber,@FormParam("biography") String biography,@FormParam("currentProfilePicPath") String currentProfilePicPath,
			@FormParam("fileName") String fileName){

		try{	

			if(currentProfilePicPath.isEmpty())
				currentProfilePicPath = null;
			
			UserSignUpBean userSignUpBean = new UserSignUpBean();

			userSignUpBean.setEmailId(emailId);
			userSignUpBean.setFirstName(firstName);
			userSignUpBean.setLastName(lastName);
			userSignUpBean.setDisplayName(displayName);
			userSignUpBean.setContact(phoneNumber);
			userSignUpBean.setBiography(biography);
			userSignUpBean.setProfilePicPath(currentProfilePicPath);

			if(city.equalsIgnoreCase("undefined")){
				userSignUpBean.setCity(null);
			}else{
				userSignUpBean.setCity(city);
			}

			if(state.equalsIgnoreCase("undefined")){
				userSignUpBean.setState("");
			}else{
				userSignUpBean.setState(state);
			}


			if(country.equalsIgnoreCase("undefined")){
				userSignUpBean.setCountry("");
			}else{
				userSignUpBean.setCountry(country);
			}

			MixtriDAO mixtriDAO = new MixtriDAO();
			mixtriDAO.updateProfileDAO(userSignUpBean);

		}catch(Exception exp){
			log.error("Error occured while updating profile data: "+exp);
			return Response.serverError().build();
		}

		Gson gson = new Gson();
		String response = gson.toJson("Success");
		//return Response.ok().build();
		return Response.ok(response, MediaType.APPLICATION_JSON).build();
	}

	@POST
	@Path("changePassword")
	public Response changePassword(@FormParam("emailId")String emailId,@FormParam("changePassword") String changePassword,@FormParam("re_enterPassword") String re_enterPassword){

		try{
			if(!changePassword.equals(re_enterPassword)){
				return Response.status(Status.BAD_REQUEST).entity("Passwords donnot match").build();
			}else{

				SaltedMD5 saltedMD5 = new SaltedMD5();
				UserSignUpBean userSignUpBean = new UserSignUpBean();
				String hashedPassword = saltedMD5.generateSecurePassword(changePassword,userSignUpBean);
				userSignUpBean.setEmailId(emailId);
				userSignUpBean.setPassword(hashedPassword);

				MixtriDAO mixtriDAO = new MixtriDAO();
				mixtriDAO.changePasswordDAO(userSignUpBean);
			}

		}catch(Exception exp){
			log.error("Exception occured while changing the password "+exp);
			return Response.serverError().build();
		}
		return Response.ok().build();
	}

	@POST
	@Path("deleteAccount")
	public Response deleteAccount(@FormParam("emailId") String emailId){
		try{
			MixtriDAO mixtriDAO = new MixtriDAO();
			mixtriDAO.deleteAccountDAO(emailId);
			String picsPath = UPLOAD_FILE_SERVER+prop.getProperty("EVENT_PICS")+emailId+"/";
			String mediaFilesPath = UPLOAD_FILE_SERVER+"audio"+"/"+emailId+"/";
			MixtriUtils.deleteUserHomeDirectory(picsPath);
			MixtriUtils.deleteUserHomeDirectory(mediaFilesPath);
		}catch(Exception exp){
			log.error("Exception Occured while deleting account: "+exp);
			Response.serverError().build();
		}
		return Response.ok().build();
	}

}
