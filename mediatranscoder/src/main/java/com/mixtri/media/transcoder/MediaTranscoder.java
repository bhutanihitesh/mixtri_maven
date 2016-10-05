package com.mixtri.media.transcoder;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.message.internal.StringBuilderUtils;

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
	
	List<String> allowedOrigins = Arrays.asList("http://localhost:8000","http://www.mixtri.com");
	
	@Override
	public void filter(ContainerRequestContext requestCtx) throws IOException {
		
		String hostOrigin = requestCtx.getHeaderString("origin");
		
		/**
		 * If the host origin is other than then allowed origins then abort the request
		 */
		
        if (!allowedOrigins.contains(hostOrigin)) {
            log.info( "HTTP Method (OPTIONS) - Detected!" );

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
        	
        	System.out.println("Here is the response: "+mapResponse);
        	
        }
       
	}catch(IOException exp){
		
		System.out.println("Exception Occured while executing windows command line commands");
		Response.serverError().build();
	}
		
		return Response.ok(mapResponse,MediaType.APPLICATION_JSON).header("Access-Control-Allow-Origin", "http://localhost:8000").build();
		//http://www.mixtri.com
	}
	
	@POST
	@Path("kill")
	@Produces(MediaType.APPLICATION_JSON)
	public Response killTask(@FormParam("processIds") String processIds){
		
		List<String> pids = Arrays.asList(processIds.split("\\s*,\\s*"));
	    
		UDKSpawner udk = new UDKSpawner();
		
		boolean isSccuess = udk.killUDKByPID(pids);
		
		return Response.ok(isSccuess,MediaType.APPLICATION_JSON).header("Access-Control-Allow-Origin", "http://localhost:8000").build();
		
	}
	
	 public static void main(String[] args) throws IOException{
		 
		 int processId=0;
		 
		 if(args.length>0){
		   processId = Integer.parseInt(args[0]);
		 }
		 startProcess(processId);

	    }
	 
	 
	 public static void startProcess(int processId) throws IOException{
		 ProcessBuilder builder = new ProcessBuilder(
					"cmd.exe", "/c", "cd \"C:\\Program Files (x86)\\VideoLAN\\VLC\" && vlc --sout #transcode{vcodec=none,acodec=mp3,ab=128,channels=2,samplerate=44100}:std{access=shout,mux=ogg,dst=source:mixtri@52.77.202.27:80/mixtri/mystream.mp3} http://ec2-52-77-202-27.ap-southeast-1.compute.amazonaws.com/mixtri/hitesh.ogg");
		    builder.redirectErrorStream(true);
		    UDKSpawner udkSpawner = new UDKSpawner();
		    Map<String,Object> map =udkSpawner.spawnUDK(builder);
		    
	 }

	
	    
	    

}
