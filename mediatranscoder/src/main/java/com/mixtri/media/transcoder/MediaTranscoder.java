package com.mixtri.media.transcoder;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.mixtri.media.transcoder.UDKSpawner;

/**
 * 
 * @author Hitesh
 * This class opens vlc player on the server and transcodes it.
 */

@Path("/")
public class MediaTranscoder implements ContainerRequestFilter  {

	static Logger log = Logger.getLogger(MediaTranscoder.class.getName());
	
	List<String> allowedOrigins = Arrays.asList("http://localhost:8080","http://localhost:8000","http://www.mixtri.com");
	String hostOrigin;
	@Override
	public void filter(ContainerRequestContext requestCtx) throws IOException {
		
		hostOrigin = requestCtx.getHeaderString("Origin");
		
		log.info("hostOrigin is: "+hostOrigin);
		log.info("allowed Origins: "+allowedOrigins);
		
		/**
		 * If the host origin is other than then allowed origins then abort the request
		 */
		
        if (!allowedOrigins.contains(hostOrigin)) {
            log.info( "Its an unauthorized request" );

            // Just send a OK signal back to the browser
            requestCtx.abortWith( Response.status( Response.Status.UNAUTHORIZED).build() );

		
	   }
	}      
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/test")
	public Response testRest(){
		
		System.out.println("Here is the first Rest call...");
		return Response.ok("Success Again and Again", MediaType.APPLICATION_JSON).build();
	}
	
	@POST
	@Path("transcode")
	@Produces(MediaType.APPLICATION_JSON)
	public Response transcodeMedia(@FormParam("streamId") String streamId,@FormParam("streamingOption") String streamingOption,
								   @FormParam("liveStreamSource") String liveStreamSource){
	
		Process p=null;
		String mapResponse=null;
	try{
		
		String output=streamId+".mp3";
		
		ProcessBuilder builder=null;
		
		if(streamingOption.equalsIgnoreCase("panel-icecast")){
			/**This Option transocde .ogg stream to .mp3 and stream it on icecast server** This is used when dj is playing live using client tool like Virtual Dj or any
			 * other software/
			 */
					builder = new ProcessBuilder(
					"cmd.exe", "/c", "cd \"C:\\Program Files (x86)\\VideoLAN\\VLC\" && "
					+ "vlc --sout #transcode{vcodec=none,acodec=mp3,ab=128,channels=2,samplerate=44100}:"
					+ "std{access=shout,mux=ogg,dst=source:mixtri@52.77.202.27:80/mixtri/"+output+"} "
					+  liveStreamSource);
					
		}else if(streamingOption.equalsIgnoreCase("panel-recorded-mixes")){
			/**
			 * This options just live streams a newtwork stream from google drive  to icecast without transcoding.
			 */
			
			builder = new ProcessBuilder(
					"cmd.exe", "/c", "cd \"C:\\Program Files (x86)\\VideoLAN\\VLC\" && "
					+ "vlc --sout=#std{access=shout,mux=ogg,dst=source:mixtri@52.77.202.27:80/mixtri/"+output+"} "+"\""+liveStreamSource+"\"");
					
					
		}
		
		UDKSpawner udkSpawner = new UDKSpawner();
	    Map<String,Object> hashMap =udkSpawner.spawnUDK(builder);
	  
	    p = (Process)hashMap.get("process");
	    
	    //Removing Process Object because Gson throws error. It can either convert Map<String,String> or Map<String,List> to json
	    hashMap.remove("process");
	    
        if(p.isAlive()){
        	
        	Gson gson = new Gson();
        	mapResponse = gson.toJson(hashMap);
        	
        }
       
	}catch(IOException exp){
		
		System.out.println("Exception Occured while executing windows command line commands");
		Response.serverError().build();
	}
		
		return Response.ok(mapResponse,MediaType.APPLICATION_JSON).header("Access-Control-Allow-Origin",hostOrigin).build();	
	}
	
	@POST
	@Path("kill")
	
	public Response killTask(@FormParam("processIds") String processIds){
		
		List<String> pids = Arrays.asList(processIds.split("\\s*,\\s*"));
	    
		UDKSpawner udk = new UDKSpawner();
		
		udk.killUDKByPID(pids);
		return Response.ok().header("Access-Control-Allow-Origin",hostOrigin).build();
		
	}
	   

}
