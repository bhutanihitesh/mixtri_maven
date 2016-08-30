package com.mixtri.event;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.sql.Timestamp;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.mixtri.DAO.MixtriDAO;
import com.mixtri.utils.MixtriUtils;

@Path("/")
public class Event {
	
	
	/**
	 * This function takes time zone from the client and return server time and time advanced by 10 mins to the client.
	 * @param selectedTimeZone
	 * @return
	 */
	static Logger log = Logger.getLogger(Event.class.getName());
	static String BASE_PATH;
	
	static{
		try{
			Properties prop = new Properties();
			String path = new File("properties/mixtri.properties").getAbsolutePath();
			File file = new File(path);

			InputStream input = null;
			input = new FileInputStream(file);
			// load a properties file
			prop.load(input);
			BASE_PATH = prop.getProperty("BASE_PATH");
		}catch(Exception exp){
			log.error("Error Loading properties files "+exp.getStackTrace());
		}

	}
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/test")
	public Response testRest(){
		
		System.out.println("Here is the first Rest call...");
		return Response.ok("Success Again and Again", MediaType.APPLICATION_JSON).build();
	}
	
	
	/***
	 * While Saving the event we check if the event time and the server time are valid ie user is setting the event in advance. Also, We also check while saving the event
	 * that the same event exists with this name or not. If not then it sends in one more restful service request and saves the event 
	 * @param selectedTimeZone
	 * @param streamInfo
	 * @param profileURLId
	 * @return
	 */
	
	@GET
	@Path("/servertime")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getServerTime(@QueryParam("selectedTimeZone") String selectedTimeZone,@QueryParam("streamInfo") String streamInfo,
			@QueryParam("profileURLId") String profileURLId){
		String result="";
		boolean eventExists =false;
		try {		
			Map<String,String> times = new HashMap<String,String>();
			Date date = null;
			Date dateAdvanced = null;
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/YYYY|HH:mm");
			sdf.setTimeZone(TimeZone.getTimeZone(selectedTimeZone));
			Calendar cal = Calendar.getInstance();
			date = cal.getTime();
			log.debug("Date: "+date);
			Calendar calAdvancedTime = Calendar.getInstance();
			calAdvancedTime.add(Calendar.MINUTE, 10);
			dateAdvanced = calAdvancedTime.getTime();
			log.debug("dateAdvanced: "+dateAdvanced);
			String advancedDateTime  = sdf.format(dateAdvanced);
			String serverDateTime =    sdf.format(date);
			
			MixtriDAO mixtriDAO = new MixtriDAO();
			if(streamInfo!=null){
				eventExists = mixtriDAO.getEventInfoDAO(streamInfo,profileURLId);
			}
			
			if(eventExists){
				times.put("error","You already have a live event setup with the name "+streamInfo+" .You can re use this name for your new event once your previous event has"
						+ " ended.");
			}
			
			
			times.put("serverTime", serverDateTime);
			times.put("advancedTime", advancedDateTime);
			
			log.debug("Times map: "+times);
			
			Gson gson = new Gson(); 
			result = gson.toJson(times);

		}catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
			return Response.serverError().build();
		}
		return Response.ok(result, MediaType.APPLICATION_JSON).build();
	}
	
	@POST
	@Path("/event")
	@Produces(MediaType.APPLICATION_JSON)
	public Response setEventDetails(@FormParam("emailId") String emailId,@FormParam("displayName") String displayName, @FormParam("streamInfo") String streamInfo,
			@FormParam("eventDate") String eventDate,@FormParam("eventTime") String eventTime, @FormParam("selectedTimeZone") String selectedTimeZone,
			@FormParam("eventDescription") String eventDescription,@FormParam("genre") String genre, @FormParam("hastags") String hastags,
			@FormParam("streamingOption") String streamingOption,@FormParam("bgVideoTheme") String bgVideoTheme,
			@FormParam("profileURLId") String profileURLId,@FormParam("eventPicPath") String eventPicPath,@FormParam("liveStreamSource") String liveStreamSource){
		
		String response="";
		try{
		 EventBean eventBean;
		 
		 String bgVideoPath = bgVideoTheme;
		 
		 String fromFormat = "MM/dd/yyyy";
		 String toFormat = "yyyy-MM-dd";
		 String formattedDate =  MixtriUtils.convertDateToAnotherFormat(eventDate, fromFormat, toFormat);
		 
		 String formattedEventDateTime = formattedDate+" "+eventTime+":00";
		 String fromTimezone = selectedTimeZone;
		 String toTimezone = "UTC";
		 Timestamp eventCreatedUTCTimestamp = MixtriUtils.convertToTimezone(formattedEventDateTime, fromTimezone,toTimezone);
		 if(genre==null || genre.isEmpty()){
			 genre = "Open Format";
		 }
		 
		 final String uuid = MixtriUtils.getUUID();
		 final String liveStreamURL="http://52.77.202.27/mixtri/"+uuid+".ogg";
		 
		 if(liveStreamSource==null || liveStreamSource.isEmpty()){
			 
			 liveStreamSource="http://52.77.202.27/mixtri/"+uuid+".ogg";
		 }
		 
		 
		 eventBean = setEventBean(uuid,emailId,displayName,streamInfo,eventCreatedUTCTimestamp,selectedTimeZone,eventDescription,genre,hastags
				 				  ,streamingOption,eventPicPath,bgVideoPath,profileURLId,liveStreamURL,liveStreamSource);
		 
		 MixtriDAO mixtriDAO = new MixtriDAO();
		 boolean isSaved = mixtriDAO.saveEventInfoDAO(eventBean);
		 
		 HashMap<String,String> mapEvent = new HashMap<String, String>();
		 if(isSaved){
			 Gson gson = new Gson();
			 mapEvent.put("id", eventBean.getId());
			 mapEvent.put("liveStreamURL", liveStreamURL);
			 response = gson.toJson(mapEvent);
		 }
		
		}catch(Exception exp){
			
			log.error("Exception Occured: "+ exp.getMessage());
			return Response.serverError().build();
		}
		 return Response.ok(response).build();
		
	}
	
	public EventBean setEventBean(String uuid,String emailId,String displayName,String streamInfo,Timestamp eventCreatedUTCTimestamp,String selectedTimeZone,String eventDescription, 
			String genre,String hastags,String streamingOption,String eventPicPath,String bgVideoPath,String profileURLId,String liveStreamURL,String liveStreamSource){
		
		EventBean eventBean = new EventBean();
		eventBean.setId(uuid);
		eventBean.setEmailId(emailId);
		eventBean.setDisplayName(displayName);
		eventBean.setStreamInfo(streamInfo);
		eventBean.setEventCreatedUTCTimestamp(eventCreatedUTCTimestamp);
		eventBean.setTimeZone(selectedTimeZone);
		eventBean.setEventDescription(eventDescription);
		eventBean.setGenre(genre);
		eventBean.setHastags(hastags);
		eventBean.setStreamingOption(streamingOption);
		eventBean.setEventPicPath(eventPicPath);
		eventBean.setBgVideoPath(bgVideoPath);
		eventBean.setAttendeeCount(0);
		eventBean.setIsLive("");
		eventBean.setProfileURLId(profileURLId);
		eventBean.setKudosCount(0);
		eventBean.setLiveStreamURL(liveStreamURL);
		eventBean.setLiveStreamSource(liveStreamSource);		
		return eventBean;
		
	}
	
	/**
	 * When the event is setup this returns the info about the setup event.
	 */
	
	@GET
	@Path("/event-info")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEventInfo(@QueryParam("eventId") String eventId,@QueryParam("profileURLId") String profileURLId,@QueryParam("fanEmailId")String fanEmailId){
		String response;
		try{
			MixtriDAO mixtriDAO = new MixtriDAO();
			Gson gson = new Gson();
			Map<String,Object> liveStreamInfo = new HashMap<String, Object>();
			liveStreamInfo = mixtriDAO.getSetupEventInfoDAO(eventId,profileURLId,fanEmailId);
			liveStreamInfo = getClientDisplaybleData(liveStreamInfo);
			response = gson.toJson(liveStreamInfo);
			
		}catch(Exception exp){
			log.error("Exception Occured in getEventInfo: "+exp.getStackTrace());
			return Response.serverError().build();
		}
		return Response.ok(response,MediaType.APPLICATION_JSON).build();
		
	}
	
	public Map<String,Object> getClientDisplaybleData(Map<String,Object> liveStreamInfo) throws NumberFormatException, ParseException{
		
		String strDate = (String) liveStreamInfo.get("eventDate");
		String strTime = (String) liveStreamInfo.get("eventTime");
		String timeZone= (String)liveStreamInfo.get("timeZone");
		String arrDate[] = strDate.split("-"); //yyyy-mm-dd format
		Long serverTimeInSeconds = MixtriUtils.getServerTimeInSeconds(timeZone); //Current Server time in seconds
		String arrDate1 [] = strDate.split("-");
		String strYear = arrDate1[0];
		String strMonth = arrDate1[1];
		String strDay = arrDate[2];
		String finalEventDate = strMonth+"/"+strDay+"/"+strYear;
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy|HH:mm");
		Date date = sdf.parse(finalEventDate+"|"+strTime);
		Long eventTimeInSeconds = date.getTime()/1000;
		Long countDownSeconds = eventTimeInSeconds - serverTimeInSeconds; // time for which countdown clock needs to be set up
		
		if(countDownSeconds<=0){
			countDownSeconds = (long) 0;
		}
		String seconds = countDownSeconds.toString();
		strDate = new DateFormatSymbols().getMonths()[Integer.parseInt(arrDate[1])-1];
		String eventDay = arrDate[2];
		String eventMonth = strDate.substring(0, 3);
		String eventDate = eventDay+" "+eventMonth;
		
		 long mins = MixtriUtils.getTimeInMins(strTime);
		 strTime = MixtriUtils.formatTime(mins); // Formats time in AM PM format before sending it to client so as to show in AM PM on UI
		
		liveStreamInfo.put("eventDate", eventDate); //return first three letters of the month
		liveStreamInfo.put("eventTime", strTime);
		liveStreamInfo.put("seconds",seconds); //return seconds for the countDownClock
		
		return liveStreamInfo;
		
	}
	
	
	@GET
	@Path("/liveUpcomingEvents")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getLiveUpcomingEvents(@QueryParam("webUserTimeZone") String webUserTimeZone,@QueryParam("page") int page,@QueryParam("limitPerPage") int limitPerPage,
			@QueryParam("filter")String filter){
	
		String response = null;
		try{
			MixtriDAO mixtriDAO = new MixtriDAO();
			Gson gson = new Gson();
			List<Map<String,Object>> getLiveUpcomingEventsList;
			Map<String,Object> allEvents = new HashMap<String, Object>();
			int offset = 0;
			
			if(page==1){
				offset = 0;	
			}else{
			 offset = (page-1)*limitPerPage;
			}
			
			EventBean eventBean = new EventBean();
			getLiveUpcomingEventsList=mixtriDAO.getLiveUpcomingEventsDAO(webUserTimeZone,eventBean,filter,offset,limitPerPage);
			allEvents.put("allEvents",getLiveUpcomingEventsList);
			allEvents.put("noOfRecords", eventBean.getNoOfRecords());
			
			response = gson.toJson(allEvents);
			
		}catch(Exception exp){
			log.error("Error Occured in fetching Live upcoming events info: "+exp);
			System.out.println("Error Occured in fetching Live upcoming events info: "+exp);
			return Response.serverError().build();
		}
		
		
		return Response.ok(response,MediaType.APPLICATION_JSON).build();
	}
	
	@POST
	@Path("/event/feedback")
	public Response saveEventFeedback(@FormParam("feedback") String feedback,@FormParam("eventId") String eventId){
	 try{
		EventBean eventBean = new EventBean();
		eventBean.setFeedback(feedback);
		eventBean.setId(eventId);
		
		MixtriDAO mixtriDAO = new MixtriDAO();
		mixtriDAO.saveEventFeedbackDAO(eventBean);
		
		
		return Response.ok().build();
		
	 }catch(Exception exp){
		 log.error("Error Occured in saveEventFeedback method: "+exp);
		 return Response.serverError().build();
	 }
	}
	
	@POST
	@Path("/event/updateEventStatus")
	public Response updateEventStatus(@FormParam("eventId") String eventId,@FormParam("isLive") String isLive){
	 try{
		
		
		MixtriDAO mixtriDAO = new MixtriDAO();
		mixtriDAO.updateEventStatusDAO(eventId,isLive);
		
		
		return Response.ok().build();
		
	 }catch(Exception exp){
		 log.error("Error Occured in saveEventFeedback method: "+exp);
		 return Response.serverError().build();
	 }
	
	}
	
	/**
	 * If joinEvent is true that means user is joining a new event
	 * If joinEvent is false that mean the page is unloaded and we need to remove the attendee from the event
	 * @param eventId
	 * @param emailId
	 * @param attendeeId
	 * @return
	 */
	@POST
	@Path("/event/liveDataUpdate")
	public Response liveDataUpdate(@FormParam("eventId") String eventId,@FormParam("emailId") String emailId,@FormParam("attendeeId") String attendeeId,
			@FormParam("type") String type,@FormParam("fanEmailId") String fanEmailId,@FormParam("profileURLId") String djProfileURLId){
	 try{
		
		
		MixtriDAO mixtriDAO = new MixtriDAO();
		
		
		Map<String,Object> attendeeInfo = new HashMap<String, Object>();
		
		/*If the user is refreshing the page the value of type will be null so remove the user*/
		if(type==null){
			type = "leaveEvent";
		}
		
		 switch (type) {
		 
		 case "joinEvent":
		 attendeeInfo = mixtriDAO.updateAttendeeCountDAO(eventId,emailId);
		 break;
		 
		 case "leaveEvent":
	     attendeeInfo = mixtriDAO.removeAttendeeFromEventDAO(attendeeId,eventId);
		 break;
		 
		 case "kudosCount":
		 attendeeInfo = mixtriDAO.updateKudosCount(eventId);
	     break;
	     
	     
		 case "addFan":
	     attendeeInfo = mixtriDAO.updateFanCountDAO(fanEmailId,djProfileURLId,"addFan");
	     break;
	     
		 case "removeFan":
		 attendeeInfo = mixtriDAO.updateFanCountDAO(fanEmailId,djProfileURLId,"removeFan");
		 break;
		 
		}
		
		Gson gson = new Gson();
		String response = gson.toJson(attendeeInfo);
		return Response.ok(response,MediaType.APPLICATION_JSON).build();
		
	 }catch(Exception exp){
		 log.error("Error Occured in saveEventFeedback method: "+exp);
		 return Response.serverError().build();
	 }
	
	}

}
