package com.mixtri.uploader;

import java.io.File;


import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.log4j.Logger;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import com.google.gson.Gson;
import com.mixtri.DAO.MixtriDAO;
import com.mixtri.utils.MixtriUtils;

import org.apache.commons.io.IOUtils;

@Path("/")
public class Uploader{

	static Logger log = Logger.getLogger(Uploader.class.getName());
	static String UPLOAD_FILE_SERVER;
	static Properties prop;
	String PARENT_FOLDER_ID="0B_jU3ZFb1zpHQmFIMDNzc2dBRHM";

	static{
		try{
			prop = new Properties();
			String path = new File("src/main/webapp/properties/mixtri.properties").getAbsolutePath();
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
	@Path("/download")
	@Produces("application/zip")
	public Response downloadFile() {
		String responseOk;
		Gson gson = new Gson();
		try{ 
			// set file (and path) to be download
			File file = new File("D:/Demo/download/Microbiology.zip");

			ResponseBuilder responseBuilder = Response.ok((Object) file);
			responseBuilder.header("Content-Disposition", "attachment; filename=\"MyJerseyZipFile.zip\"");

			responseOk = gson.toJson("File downloaded successfully");
			return Response.ok(responseOk, MediaType.APPLICATION_JSON).build();
		}catch(Exception exp){

			log.error("Exception Occured while downoloading file: downloadFile method: "+exp);
			Response.Status httpStatus = Response.Status.INTERNAL_SERVER_ERROR;
			return Response.status(httpStatus).build() ;

		}

	}

	public String uploadEventPic(
			@FormDataParam("eventPic") InputStream fileInputStream,
			@FormDataParam("eventPic") FormDataContentDisposition fileFormDataContentDisposition,
			@FormDataParam("emailId") String emailId,boolean isProfilePic) {
		String fileName = "";
		String uploadPath="";
		try{

			uploadPath = UPLOAD_FILE_SERVER+prop.getProperty("EVENT_PICS")+emailId+"/";
			boolean directoryCreated = createUserDirectory(uploadPath);
			if(directoryCreated){

				fileName = fileFormDataContentDisposition.getFileName();
				final String uuid = MixtriUtils.getUUID();
				log.debug("Saving event pic: "+fileName+" as "+uuid);

				if(isProfilePic){
					fileName = "ProfilePic-"+uuid+".jpg";
				}else{
					fileName = uuid+".jpg";
				}
				byte[] bytes = IOUtils.toByteArray(fileInputStream);
				writeToFileServer(bytes, fileName,uploadPath);

			}
		}catch(Exception exp){
			log.error("Exception Occured: "+exp);
			return uploadPath+fileName;
		}
		return uploadPath+fileName;
	}
	
	@POST
	@Path("/saveSongDetails")
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveSongDetails(@FormParam("uuid") String uuid,@FormParam("emailId") String emailId, @FormParam("mixTitle") String mixTitle,
			@FormParam("uploadPath") String uploadPath,@FormParam("fileName") String fileName,@FormParam("fileSize") float fileSize,@FormParam("googleFileId") String googleFileId){
		
		String response=null;
		
		try {
		
		Gson gson = new Gson();
		
		Map<String,String> messages = new HashMap<String,String>();
		
		//float fileSizeMB = Float.parseFloat(String.format("%.2f", fileSize/1000000));
		UploaderBean uploaderBean = setUploaderBean(emailId,fileSize,uuid,mixTitle,uploadPath,googleFileId);
		MixtriDAO mixtriDAO = new MixtriDAO();
		mixtriDAO.saveUploadedMixDAO(uploaderBean);
		messages.put("success",fileName+" Uploaded Successfully!");
		messages.put("path", uploadPath);
		response = gson.toJson(messages);
		
		}catch(Exception exp){

			log.error("Exception Occured while uploading file: uploadFile method: "+exp);
			Response.Status httpStatus = Response.Status.INTERNAL_SERVER_ERROR;
			return Response.status(httpStatus).build() ;
		} 
		
		return Response.ok(response, MediaType.APPLICATION_JSON).build();
	}

	

	public UploaderBean setUploaderBean(String emailId,double fileSizeMB,String id,String title,String filePath,String googleFileId){

		UploaderBean uploaderBean = new UploaderBean();
		uploaderBean.setFileSize(fileSizeMB);
		uploaderBean.setEmailId(emailId);
		uploaderBean.setId(id);
		uploaderBean.setTitle(title);
		uploaderBean.setFilePath(filePath);
		Date utilDate = new Date();
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		uploaderBean.setDateUploaded(sqlDate);
		uploaderBean.setGoogleFileId(googleFileId);


		return uploaderBean;
	}

	/**
	 * write input stream to file server
	 * @param inputStream
	 * @param fileName
	 * @throws IOException
	 */
	private double writeToFileServer(byte[] bytes, String fileName, String UPLOAD_FILE_SERVER) throws IOException {

		OutputStream outputStream = null;
		String qualifiedUploadFilePath = UPLOAD_FILE_SERVER + fileName;
		double fileSize = 0.0;
		try {
			outputStream = new FileOutputStream(new File(qualifiedUploadFilePath));
			outputStream.write(bytes);
			outputStream.flush();

			File file = new File(qualifiedUploadFilePath);
			fileSize = file.length();
		}
		catch (IOException ioe) {
			ioe.printStackTrace();            
		}
		finally{
			//release resource, if any
			outputStream.close();
		}

		return fileSize;
	}

	/**
	 * This method return the free diskspace left for a user and gets called on liveStream.jsp document load
	 * @param emailId
	 * @return
	 */

	@GET
	@Path("/diskspace")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDiskSpace(@QueryParam("emailId") String emailId){

		String spaceLeft="";
		Gson gson = new Gson();
		MixtriDAO mixtriDAO = new MixtriDAO();
		try{
			
			float userDiskSpace = mixtriDAO.getUsedDiskSpaceDAO(emailId);
			int oneGB = 1024;
			double diskSpaceLeft = oneGB - userDiskSpace;
			spaceLeft = gson.toJson(Float.parseFloat(String.format("%.2f", diskSpaceLeft)) ); //rounds off to first two decimals

			return Response.ok(spaceLeft, MediaType.APPLICATION_JSON).build();

		}catch(Exception exp){
			
			log.error("Exception Occured while fetching disk space for the user:"+exp);
			Response.Status httpStatus = Response.Status.INTERNAL_SERVER_ERROR;
			return Response.status(httpStatus).build() ;
		} 
	}

	public boolean createUserDirectory(String uploadPath) throws IOException{

		File files = new File(uploadPath);
		if (!files.exists()) {
			if (files.mkdirs()) {
				log.debug("User Directory Created: "+uploadPath);
				return true;
			} else {
				log.error("failed to create user Direcory: "+uploadPath);
				throw new IOException();

			}
		}

		return true;
	}

	/**
	 * This function gets the past uploaded mixes for a user and gets called on liveStream.jsp document load
	 * @param emailId
	 * @return
	 */

	@GET
	@Path("/pastmixes")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPastMixes(@QueryParam("emailId") String emailId){

		String pastMixesOrgNames="";
		Gson gson = new Gson();
		List<Map<String,String>> listUploadedTracks;
		Map<String,Object> mapUploadedTracks = new HashMap<String, Object>();
		try{

				MixtriDAO mixtriDAO = new MixtriDAO();
				log.debug("Getting past track info for the user: "+emailId);
				listUploadedTracks = mixtriDAO.getUserPastTracksInfoDAO(emailId);
				mapUploadedTracks.put("listUploadedTracks", listUploadedTracks);
				pastMixesOrgNames = gson.toJson(mapUploadedTracks);
				
		}catch(Exception exp){

			log.error("Exception Occured while retriving past mixes fro the user: "+exp);
			return Response.serverError().build();
		}

		return Response.ok(pastMixesOrgNames, MediaType.APPLICATION_JSON).build();

	}

	@POST
	@Path("/deleteUploadedTrack")
	public Response deleteUploadedTrack(@FormParam("uploadedSetId") String uploadedSetId){
		try{
			MixtriDAO mixtriDAO = new MixtriDAO();
			/**
			 * We are first Deleting the record from the DB because if we would have attempted the delete of the media file first and it was successful but there is an 
			 * exception while deletion of record from the DB and this will show up on the UI as dead link. The other way round successful deletion from the DB but not physical
			 * delete of the file would only cause the file to sit there in the hard drive consuming space but would not cause a dead link. 
			 * 
			 */
			mixtriDAO.deleteUploadedTrackdDAO(uploadedSetId);

		}catch(Exception exp){
			log.error("Error Occured while deleting a set: "+exp);
			return Response.serverError().build();
		}
		return Response.ok().build();
	}

}
