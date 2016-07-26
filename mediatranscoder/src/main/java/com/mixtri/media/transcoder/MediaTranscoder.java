package com.mixtri.media.transcoder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
/**
 * 
 * @author Hitesh
 * This class opens vlc player on the server and transcodes it.
 */

@Path("/")
public class MediaTranscoder {

	@GET
	@Path("/transcode")
	public Response transcodeMedia(@QueryParam("streamId") String streamId){
	
	try{
		
		
		String input=streamId+".ogg";
		String output=streamId+".mp3";
		
		ProcessBuilder builder = new ProcessBuilder(
		"cmd.exe", "/c", "cd \"C:\\Program Files (x86)\\VideoLAN\\VLC\" && "
		+ "vlc --sout #transcode{vcodec=none,acodec=mp3,ab=128,channels=2,samplerate=44100}:"
		+ "std{access=shout,mux=ogg,dst=source:mixtri@52.77.202.27:80/mixtri/"+output+"} "
		+ "http://ec2-52-77-202-27.ap-southeast-1.compute.amazonaws.com/mixtri/"+input);
	    builder.redirectErrorStream(true);
	    
	    Process p = builder.start();
	    
	    Map<String,String> mapSuccess = new HashMap<String,String>(); 
	    
	    mapSuccess.put("success","Successfully Transcoded to .mp3........");
	    	
	    Gson gson = new Gson();
	    String response = gson.toJson(mapSuccess);
      
        if(p.isAlive()){
        
       // return Response.ok(response, MediaType.APPLICATION_JSON).build();
        	return Response.ok().build();
        	
        }
        
        /*BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        while (true) {
            line = r.readLine();
            if (line == null) { break; }
            System.out.println("Hello Logs: "+line);
        }*/
	}catch(IOException exp){
		
		System.out.println("Exception Occured while executing windows command line commands");
		Response.serverError().build();
	}
		
		return Response.ok("Successfully Transcoded to .mp3........", MediaType.APPLICATION_JSON).build();
	}
}
