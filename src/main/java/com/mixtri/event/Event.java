package com.mixtri.event;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
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
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import com.google.gson.Gson;
import com.mixtri.DAO.MixtriDAO;
import com.mixtri.uploader.Uploader;
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
	
	/***
	 * While Saving the event we check if the event time and the server time are valid ie user is setting the event in advance. Also, We also check while saving the event
	 * that the same event exists with this name or not. If not then it sends in one more restful service request and saves the event 
	 * @param selectedTimeZone
	 * @param streamInfo
	 * @param profileURLId
	 * @return
	 */
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/test")
	public Response testRest(){
		
		return Response.ok("Success Again and Again", MediaType.APPLICATION_JSON).build();
	}
	
	
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
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Response setEventFormData(@FormDataParam("emailId") String emailId,@FormDataParam("displayName") String displayName, @FormDataParam("streamInfo") String streamInfo,
			@FormDataParam("eventDate") String eventDate,@FormDataParam("eventTime") String eventTime, @FormDataParam("selectedTimeZone") String selectedTimeZone,
			@FormDataParam("eventDescription") String eventDescription,@FormDataParam("genre") String genre, @FormDataParam("hastags") String hastags,
			@FormDataParam("streamingOption") String streamingOption,@FormDataParam("eventPic") InputStream fileInputStream,
			@FormDataParam("eventPic") FormDataContentDisposition fileFormDataContentDisposition,@FormDataParam("bgVideoTheme") String bgVideoTheme,
			@FormDataParam("profileURLId") String profileURLId){
		
		String response="";
		try{
		 EventBean eventBean;
		 Uploader fileServiceImpl = new Uploader();
		 
		 //Give path of the pic saved for the event for the logged in user.
		 String eventPicPath = fileServiceImpl.uploadEventPic(fileInputStream, fileFormDataContentDisposition, emailId,false); 
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
		 eventBean = setEventBean(emailId,displayName,streamInfo,eventCreatedUTCTimestamp,selectedTimeZone,eventDescription,genre,hastags
				 				  ,streamingOption,eventPicPath,bgVideoPath,profileURLId);
		 
		 MixtriDAO mixtriDAO = new MixtriDAO();
		 boolean isSaved = mixtriDAO.saveEventInfoDAO(eventBean);
		 
		 HashMap<String,String> eventId = new HashMap<String, String>();
		 if(isSaved){
			 Gson gson = new Gson();
			 eventId.put("id", eventBean.getId());
			 response = gson.toJson(eventId);
		 }
		
		}catch(Exception exp){
			log.error("Exception Occured: "+ exp.getLocalizedMessage());
			return Response.serverError().build();
		}
		 return Response.ok(response).build();
		
	}
	
	public EventBean setEventBean(String emailId,String displayName,String streamInfo,Timestamp eventCreatedUTCTimestamp,String selectedTimeZone,String eventDescription, 
			String genre,String hastags,String streamingOption,String eventPicPath,String bgVideoPath,String profileURLId){
		
		final String id = MixtriUtils.getUUID();
		
		EventBean eventBean = new EventBean();
		eventBean.setId(id);
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
