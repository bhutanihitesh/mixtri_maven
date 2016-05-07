package com.mixtri.tracks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.glassfish.jersey.media.multipart.FormDataParam;

import com.google.gson.Gson;
import com.mixtri.DAO.MixtriDAO;

@Path("/")
public class TrackResource {

	private static Logger log = Logger.getLogger(TrackResource.class.getName());
    private static final MixtriDAO mixtridao=new MixtriDAO();
    
    
   
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
    @Path("/tracks")
	public Response getTracks(@QueryParam("page") int page, @QueryParam("filter") String filter,@QueryParam("offset") int offset){
    String result = null;
	try{
		int limitPerPage=10;
		if(page==1){
			offset = 0;	
		}else{
		 offset = page*limitPerPage+1-limitPerPage;
		} 
		
		
		String query = null;
		List<Map<String,Object>> tracks= null;
		
		switch (filter) {
		
		 case "All":
		 query = "SELECT artistImageSrc,popularity,audioTitle,artistDisplayName,audioSrc FROM MIXTRI.archivedmixes WHERE type='archived' "
						+ "LIMIT "+offset+","+limitPerPage;	 
		 tracks= mixtridao.getAllTracksDAO(query);
		 break;
		 
		 case "popular":
			 query = "SELECT artistImageSrc,popularity,audioTitle,artistDisplayName,audioSrc FROM MIXTRI.ARCHIVEDMIXES WHERE type='archived' "
							+ "ORDER BY popularity DESC LIMIT "+offset+","+limitPerPage;	 
			 tracks= mixtridao.getAllTracksDAO(query);
			 break;
		 
		 default:
			 query = "SELECT artistImageSrc,popularity,audioTitle,artistDisplayName,audioSrc FROM MIXTRI.ARCHIVEDMIXES WHERE type='archived' "
							+ "AND genre='"+filter+"' LIMIT "+offset+","+limitPerPage;	 
			 tracks= mixtridao.getAllTracksDAO(query);
			 break;
		 
		}
		
		
	   
	   Gson gson = new Gson(); 
		result = gson.toJson(tracks);
       
	 }catch(Exception exp){
		 
		 log.error(exp.getMessage());
		return Response.serverError().build();
		 
	 }
      
	 return Response.ok(result, MediaType.APPLICATION_JSON).build();
	}
	
}
