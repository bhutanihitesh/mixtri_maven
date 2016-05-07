package com.mixtri.event;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.mixtri.database.ConnectionFactory;
import com.mixtri.utils.MixtriUtils;
import com.mysql.jdbc.Statement;

public class EventDB {

	static Logger log = Logger.getLogger(EventDB.class.getName());
	Connection connection;
	PreparedStatement statement;

	private Connection getConnection() throws SQLException,ClassNotFoundException 
	{
		Connection con = ConnectionFactory.getInstance().getConnection();
		return con;
	}

	public int saveEventInfoDB(EventBean eventBean) throws IOException, ClassNotFoundException, SQLException{

		log.debug("Inside saveEventInfoDB: "+eventBean);

		int insertedRows;
		try{

			String query ="INSERT INTO MIXTRI.EVENTS(id,displayName,streamInfo,eventCreatedUTCTimeStamp,timeZone,eventDescription,genre,hashtags,streamingOption,"
					+ "eventPicPath,bgVideoPath,isLive,profileURLId,emailId,kudosCount)"
					+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

			String eventDescription = eventBean.getEventDescription().isEmpty() ? null : eventBean.getEventDescription();
			String hastags = eventBean.getHastags().isEmpty() ? null : eventBean.getHastags();
			String isLive = eventBean.getIsLive().isEmpty() ? null : eventBean.getIsLive();

			connection = getConnection();
			statement = connection.prepareStatement(query);    
			statement.setString(1, eventBean.getId());
			statement.setString(2, eventBean.getDisplayName());
			statement.setString(3, eventBean.getStreamInfo());
			statement.setTimestamp(4, eventBean.getEventCreatedUTCTimestamp());
			statement.setString(5, eventBean.getTimeZone());
			statement.setString(6,eventDescription);
			statement.setString(7, eventBean.getGenre());
			statement.setString(8, hastags);
			statement.setString(9, eventBean.getStreamingOption());
			statement.setString(10, eventBean.getEventPicPath());
			statement.setString(11, eventBean.getBgVideoPath());
			statement.setString(12, isLive);
			statement.setString(13, eventBean.getProfileURLId());
			statement.setString(14, eventBean.getEmailId());
			statement.setInt(15, eventBean.getKudosCount());
			insertedRows = statement.executeUpdate();

		}finally
		{
			try {
				if(statement != null)
					statement.close();
				if(connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return insertedRows;
	}

	public Map<String,Object> getSetupEventInfoDB(String eventId,String profileURLId,String fanEmailId) throws ClassNotFoundException, SQLException, ParseException{

		ResultSet rs =null;
		Map<String,Object> liveStreamInfo = new HashMap<String, Object>();
		try{

			String query = "SELECT * FROM MIXTRI.EVENTS WHERE ID=?";
			connection = getConnection();
			statement = connection.prepareStatement(query);    
			statement.setString(1, eventId);
			rs = statement.executeQuery();

			while(rs.next())
			{

				log.debug("Record found: ID: "+eventId+" profileURLId: "+profileURLId);
				Timestamp timestampDB = rs.getTimestamp("eventCreatedUTCTimestamp");
				String eventSetupTimeStampInUTC = timestampDB.toString();
				eventSetupTimeStampInUTC = eventSetupTimeStampInUTC.replace(".0",""); 
				String fromTimezone="UTC";
				String toTimezone= rs.getString("timeZone");
				Timestamp timestamp = MixtriUtils.convertToTimezone(eventSetupTimeStampInUTC, fromTimezone, toTimezone);

				String eventSetupTimestamp = timestamp.toString();
				String eventDate = MixtriUtils.convertDateToAnotherFormat(eventSetupTimestamp, "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd");
				String eventTime = MixtriUtils.convertDateToAnotherFormat(eventSetupTimestamp, "yyyy-MM-dd HH:mm:ss","HH:mm");

				Map<String,String> contactInfo = getDjContactInfo(profileURLId);

				String djEmailId = getDjEmailId(profileURLId);
				int fanCount = getFanCount(djEmailId);

				boolean isFan = isFan(fanEmailId,djEmailId);

				liveStreamInfo.put("id",rs.getString("id"));
				liveStreamInfo.put("streamInfo",rs.getString("streamInfo"));
				liveStreamInfo.put("eventDate",eventDate);
				liveStreamInfo.put("eventTime",eventTime);
				liveStreamInfo.put("timeZone",rs.getString("timeZone"));
				liveStreamInfo.put("streamingOption",rs.getString("streamingOption"));
				liveStreamInfo.put("eventDescription",rs.getString("eventDescription"));
				liveStreamInfo.put("genre",rs.getString("genre"));
				liveStreamInfo.put("eventPicPath",rs.getString("eventPicPath"));
				liveStreamInfo.put("bgVideoTheme",rs.getString("bgVideoPath"));
				liveStreamInfo.put("isLive",rs.getString("isLive"));
				liveStreamInfo.put("attendeeCount",getAttendeeCount(eventId));
				liveStreamInfo.put("displayName",rs.getString("displayName"));
				liveStreamInfo.put("kudosCount",rs.getString("kudosCount"));
				liveStreamInfo.put("fanCount",fanCount);
				liveStreamInfo.put("isFan",isFan);
				liveStreamInfo.put("city",contactInfo.get("city"));
				liveStreamInfo.put("state",contactInfo.get("state"));
				liveStreamInfo.put("country",contactInfo.get("country"));
				liveStreamInfo.put("emailId",contactInfo.get("emailId"));
				liveStreamInfo.put("contactNumber",contactInfo.get("contactNumber"));

			}
		}finally
		{
			try {
				if(statement != null)
					statement.close();
				if(connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return liveStreamInfo;
	}

	/**
	 * This method will return the live event or future event which is setup.
	 * Combination of streamInfo, profileId and isLive will always be unique.
	 * This Function checks the following:
	 * 1. While Dj setup a new event we need to check if the event he has setup is with unique name and is not live
	 * @param streamInfo
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public boolean getEventInfoDB(String streamInfo,String profileURLId) throws ClassNotFoundException, SQLException{

		//If the record is found with same name. Then check 2 conditions:
		//1. If it is empty that means it has been setup but still not live 2. If isLive value is 'Y' that means event with same name is already live by this Dj

		String query = "SELECT * FROM MIXTRI.EVENTS WHERE (ISLIVE IS NULL OR ISLIVE='Y') AND STREAMINFO=? AND PROFILEURLID=?";
		ResultSet rs =null;
		boolean eventExists = false;
		try{
			connection = getConnection();
			statement = connection.prepareStatement(query);    
			statement.setString(1, streamInfo);
			statement.setString(2, profileURLId);
			rs = statement.executeQuery();

			if(rs.next()){
				eventExists = true;
			}





		}finally
		{
			try {
				if(statement != null)
					statement.close();
				if(connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	

		return eventExists;


	}

	/**
	 * This function returnt the contact info of the Dj
	 * @param profileURLId
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public Map<String,String> getDjContactInfo(String profileURLId) throws ClassNotFoundException, SQLException{

		Map<String,String> contactInfo = new HashMap<String, String>();
		String query = "SELECT city,state,country,emailId,phoneNumber FROM MIXTRI.users WHERE profileURLId=?";
		ResultSet rs =null;
		try{
			connection = getConnection();
			statement = connection.prepareStatement(query);    
			statement.setString(1, profileURLId);
			rs = statement.executeQuery();

			while(rs.next()){

				contactInfo.put("city", rs.getString("city"));
				contactInfo.put("state", rs.getString("state"));
				contactInfo.put("country", rs.getString("country"));
				contactInfo.put("emailId",rs.getString("emailId"));
				contactInfo.put("contactNumber",rs.getString("phoneNumber"));

			}

		}finally{

			try {
				if(statement != null)
					statement.close();
				if(connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	

		return contactInfo;

	}

	/**
	 * This method gives the list of live and upcoming events
	 * @return List of all upcoming and live events
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws ParseException 
	 */
	public List<Map<String,Object>> getLiveUpcomingEventsDB(String webUserTimeZoneId,EventBean eventBean,String filter,int offset,int limitPerPage) throws ClassNotFoundException, SQLException, ParseException{

		List<Map<String,Object>> listAllLiveUpcomingEvents = new ArrayList<Map<String,Object>>();
		Boolean isGenreFilter = false;
		String query;
		switch (filter) {
		
		//Query only those events which are live and which are going to be live within next 24 hrs for that user in his timezone.
		 case "All":
		 query = "SELECT SQL_CALC_FOUND_ROWS *,TIMESTAMPDIFF(MINUTE,?,eventCreatedUTCTimestamp) AS TIMELEFT from MIXTRI.events where "
			+ "( (TIMESTAMPDIFF(MINUTE,?,eventCreatedUTCTimestamp) < '1440' AND ISLIVE IS NULL) OR ISLIVE='Y') ORDER BY ISLIVE DESC,TIMELEFT ASC LIMIT "+offset+","+limitPerPage;	 
		 break;
		 
		 case "live":
			 query = "SELECT SQL_CALC_FOUND_ROWS *,TIMESTAMPDIFF(MINUTE,?,eventCreatedUTCTimestamp) AS TIMELEFT from MIXTRI.EVENTS where "
						+ "TIMESTAMPDIFF(MINUTE,?,eventCreatedUTCTimestamp) < '1440' AND ISLIVE='Y' LIMIT "+offset+","+limitPerPage;	 
			 break;
			 
		 case "mostPopular":
			 query = "SELECT SQL_CALC_FOUND_ROWS mEvents.*,TIMESTAMPDIFF(MINUTE,?,eventCreatedUTCTimestamp) AS TIMELEFT from mixtri.events mEvents "
			 		+ "LEFT JOIN (SELECT djEmailid,count(*) AS NumberOfFans from mixtri.fans group by djEmailId order by count(*) desc) AS tempFans "
			 		+ "ON tempFans.djEmailid = mEvents.emailId WHERE ( (TIMESTAMPDIFF(MINUTE,?,eventCreatedUTCTimestamp) < '1440' AND ISLIVE IS NULL) OR "
			 		+ "ISLIVE='Y') order by tempFans.NumberOfFans desc LIMIT "+offset+","+limitPerPage;	 
			 break;
		 
		 default:
		 query = "SELECT SQL_CALC_FOUND_ROWS *,TIMESTAMPDIFF(MINUTE,?,eventCreatedUTCTimestamp) AS TIMELEFT from MIXTRI.EVENTS where "
			+ "( ((TIMESTAMPDIFF(MINUTE,?,eventCreatedUTCTimestamp) < '1440' AND ISLIVE IS NULL) OR ISLIVE='Y') AND genre=?) ORDER BY ISLIVE DESC,TIMELEFT ASC LIMIT "+offset+","+limitPerPage;
		 isGenreFilter = true;
		}

		ResultSet rs =null;
		int noOfRecords = 0;
		try{

			//Convert webuser timezone as GMT+XX:XX from the zone id eg. "Asia/Calcutta"
			Map<String,String> webUserDateAndGMTId = MixtriUtils.getDateAndGMTId(webUserTimeZoneId);

			//Covert the webuser timezone to standard UTC Timezone and then query Database for event which are live or going to be live in his time zone in next 24hrs 
			String fromTimezone=webUserDateAndGMTId.get("gmtZoneId");
			String toTimezone="UTC";
			String localDateOfWebuser = webUserDateAndGMTId.get("webUserCurrentDate"); 
			String webUserCurrentTime = webUserDateAndGMTId.get("webUserCurrentTime");
			//Convert it from webuserTimeZone Time to UTC Time because we are doing a query and it needs to be converted to UTC Timezone
			Timestamp timestamp = MixtriUtils.convertToTimezone(localDateOfWebuser+" "+webUserCurrentTime, fromTimezone, toTimezone); 

			Connection connection = getConnection();
			PreparedStatement statement = connection.prepareStatement(query);   
			statement.setTimestamp(1,timestamp);
			statement.setTimestamp(2,timestamp);
			
			if(isGenreFilter){
			
			  statement.setString(3,filter);
			}
			rs = statement.executeQuery();

			String toWebUserTimeZone = fromTimezone;

			while(rs.next()){

				Timestamp timestampDB = rs.getTimestamp("eventCreatedUTCTimestamp"); //Gets the Event Setup Timestamp in UTC from DB
				String eventSetupTimeStampInUTC = timestampDB.toString(); //Converts it to string
				String fromUTCTimeZone ="UTC";

				eventSetupTimeStampInUTC = eventSetupTimeStampInUTC.replace(".0",""); //removes the .0 ie milliseconds from the end
				eventSetupTimeStampInUTC = MixtriUtils.convertToTimezone(eventSetupTimeStampInUTC, fromUTCTimeZone, toWebUserTimeZone).toString();

				String eventDate = MixtriUtils.convertDateToAnotherFormat(eventSetupTimeStampInUTC, "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd");
				String eventTime = MixtriUtils.convertDateToAnotherFormat(eventSetupTimeStampInUTC, "yyyy-MM-dd HH:mm:ss","HH:mm");

				//For Every row returned by the resultset we create a new map object. Otherwise data gets overwritten.
				Map<String,Object> upcomingLiveEvents = new HashMap<String, Object>();

				int attendeeCount = getAttendeeCount(rs.getString("id"));
				String djEmailId = getDjEmailId(rs.getString("profileURLId") );
				int fanCount = getFanCount(djEmailId);
				String eventDescription = rs.getString("eventDescription") == null ? "" : rs.getString("eventDescription");
				String isLive = rs.getString("isLive") == null ? "" : rs.getString("isLive");
				upcomingLiveEvents.put("id",rs.getString("id"));
				upcomingLiveEvents.put("genre",rs.getString("genre"));
				upcomingLiveEvents.put("displayName",rs.getString("displayName"));
				upcomingLiveEvents.put("streamInfo",rs.getString("streamInfo"));
				upcomingLiveEvents.put("eventDate",eventDate);
				upcomingLiveEvents.put("eventTime",eventTime);
				upcomingLiveEvents.put("timeZone",rs.getString("timeZone"));
				upcomingLiveEvents.put("eventDescription",eventDescription);
				upcomingLiveEvents.put("eventPicPath",rs.getString("eventPicPath"));
				upcomingLiveEvents.put("isLive",isLive);
				upcomingLiveEvents.put("attendeeCount",attendeeCount);
				upcomingLiveEvents.put("fanCount",fanCount);
				upcomingLiveEvents.put("profileURLId",rs.getString("profileURLId"));
				upcomingLiveEvents.put("timeLeft", rs.getString("timeLeft"));
				listAllLiveUpcomingEvents.add(upcomingLiveEvents);

			}
			rs.close();
			
			rs = statement.executeQuery("SELECT FOUND_ROWS()");
            if(rs.next()){
            	noOfRecords = rs.getInt(1);
            	eventBean.setNoOfRecords(noOfRecords);
            } 
            
		}finally{

			try {
				if(statement != null)
					statement.close();
				if(connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return listAllLiveUpcomingEvents;
	}

	public void saveEventFeedbackDB(EventBean eventBean) throws ClassNotFoundException, SQLException{

		String query = "UPDATE MIXTRI.EVENTS SET FEEDBACK=? WHERE ID=?";

		try{
			String feedback = eventBean.getFeedback();
			String eventId = eventBean.getId();

			connection = getConnection();
			statement = connection.prepareStatement(query);    
			statement.setString(1, feedback);
			statement.setString(2, eventId);
			statement.executeUpdate();
		}finally{

			try {
				if(statement != null)
					statement.close();
				if(connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public void updateEventStatusDB(String eventId,String isLive) throws ClassNotFoundException, SQLException{

		String query = "UPDATE MIXTRI.EVENTS SET isLive=? WHERE ID=?";

		try{

			connection = getConnection();
			statement = connection.prepareStatement(query);    
			statement.setString(1, isLive);
			statement.setString(2, eventId);
			statement.executeUpdate();
		}finally{

			try {
				if(statement != null)
					statement.close();
				if(connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}


	public Map<String,Object> updateAttendeeCountDB(String eventId,String emailId) throws ClassNotFoundException, SQLException{

		String query = "INSERT INTO MIXTRI.ATTENDEES(id,eventId,attendeeEmailId) VALUES(?,?,?)";
		Map<String,Object> attendeeInfo = new HashMap<String, Object>();
		Map<String,String> mapAttendeeInfo = new HashMap<String, String>();
		int insertedRows;
		try{

			String id = MixtriUtils.getUUID();

			connection = getConnection();
			statement = connection.prepareStatement(query);    
			statement.setString(1, id);
			statement.setString(2, eventId);
			statement.setString(3, emailId);
			insertedRows = statement.executeUpdate();

			if(insertedRows>0){

				attendeeInfo.put("id", id);
				mapAttendeeInfo = getAttendeeInfo(emailId);
				int attendeeCount = getAttendeeCount(eventId);
				attendeeInfo.put("displayName", mapAttendeeInfo.get("displayName"));
				attendeeInfo.put("attendeeCount",attendeeCount);

			}

		}finally{

			try {
				if(statement != null)
					statement.close();
				if(connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return attendeeInfo;	
	}

	public int getAttendeeCount(String eventId) throws ClassNotFoundException, SQLException{
		int attendeeCount = 0;

		String query = "SELECT COUNT(*) AS ATTENDEE_COUNT FROM MIXTRI.attendees WHERE eventId=?";
		ResultSet rs =null;
		try{
			connection = getConnection();
			statement = connection.prepareStatement(query);    
			statement.setString(1, eventId);
			rs = statement.executeQuery();

			while(rs.next()){

				attendeeCount = rs.getInt("ATTENDEE_COUNT");


			}

		}finally{

			try {
				if(statement != null)
					statement.close();
				if(connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return attendeeCount;


	}

	public Map<String,String> getAttendeeInfo(String emailId) throws ClassNotFoundException, SQLException{
		Map<String,String> attendeeInfo = new HashMap<String, String>();

		String query = "SELECT displayName FROM MIXTRI.users WHERE emailId=?";
		ResultSet rs =null;
		try{
			connection = getConnection();
			statement = connection.prepareStatement(query);    
			statement.setString(1, emailId);
			rs = statement.executeQuery();

			while(rs.next()){

				attendeeInfo.put("displayName", rs.getString("displayName"));


			}

		}finally{

			try {
				if(statement != null)
					statement.close();
				if(connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return attendeeInfo;
	}

	public Map<String,Object> removeAttendeeFromEventDB(String attendeeId,String eventId) throws ClassNotFoundException, SQLException{

		int attendeeCount;
		Map<String,Object> attendeeCountMap = new HashMap<String, Object>();
		String query = "DELETE FROM MIXTRI.ATTENDEES WHERE ID=?";
		try{

			connection = getConnection();
			statement = connection.prepareStatement(query);    
			statement.setString(1, attendeeId);

			statement.executeUpdate();

			attendeeCount = getAttendeeCount(eventId);
			attendeeCountMap.put("attendeeCount", attendeeCount);

		}finally{

			try {
				if(statement != null)
					statement.close();
				if(connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return attendeeCountMap;
	}

	public Map<String,Object> updateKudosCountDB(String eventId) throws ClassNotFoundException, SQLException{

		int kudosCount;
		Map<String,Object> kudosCountMap = new HashMap<String, Object>();
		String query = "UPDATE MIXTRI.EVENTS SET KUDOSCOUNT=KUDOSCOUNT+1 WHERE ID=?";
		try{

			connection = getConnection();
			statement = connection.prepareStatement(query);    
			statement.setString(1,eventId);

			statement.executeUpdate();

			kudosCount = getKudosCount(eventId);
			kudosCountMap.put("kudosCount", kudosCount);

		}finally{

			try {
				if(statement != null)
					statement.close();
				if(connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return kudosCountMap;
	}


	public int getKudosCount(String eventId) throws ClassNotFoundException, SQLException{
		int kudosCount = 0;

		String query = "SELECT KUDOSCOUNT FROM MIXTRI.EVENTS WHERE ID=?";
		ResultSet rs =null;
		try{
			connection = getConnection();
			statement = connection.prepareStatement(query);    
			statement.setString(1, eventId);
			rs = statement.executeQuery();

			while(rs.next()){
				kudosCount = rs.getInt("KUDOSCOUNT");
			}

		}finally{

			try {
				if(statement != null)
					statement.close();
				if(connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return kudosCount;


	}


	public Map<String,Object> updateFanCountDB(String fanEmailId,String djProfileURLId,String type) throws ClassNotFoundException, SQLException{

		int fanCount;
		Map<String,Object> fanCountMap = new HashMap<String, Object>();

		String query=null;
		try{
			String djEmailId = getDjEmailId(djProfileURLId);
			connection = getConnection(); 
			if(type.equalsIgnoreCase("addFan")){
				query = "INSERT INTO MIXTRI.FANS(id,fanEmailId,djEmailId) values(?,?,?)";

				statement = connection.prepareStatement(query);
				statement.setString(1,MixtriUtils.getUUID());
				statement.setString(2,fanEmailId);
				statement.setString(3,djEmailId);

			}else if(type.equalsIgnoreCase("removeFan")){
				query = "DELETE FROM MIXTRI.FANS WHERE fanEmailId=? AND djEmailId=?";
				statement = connection.prepareStatement(query);
				statement.setString(1,fanEmailId);
				statement.setString(2,djEmailId);
			}

			statement.executeUpdate();

			fanCount = getFanCount(djEmailId);
			fanCountMap.put("fanCount", fanCount);

		}finally{

			try {
				if(statement != null)
					statement.close();
				if(connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return fanCountMap;
	}

	public int getFanCount(String djEmailId) throws ClassNotFoundException, SQLException{
		int fanCount = 0;

		String query = "SELECT COUNT(*) AS totalFans FROM MIXTRI.fans WHERE djEmailId=?";
		ResultSet rs =null;
		try{
			connection = getConnection();
			statement = connection.prepareStatement(query);    
			statement.setString(1, djEmailId);
			rs = statement.executeQuery();

			while(rs.next()){
				fanCount = rs.getInt("totalFans");
			}

		}finally{

			try {
				if(statement != null)
					statement.close();
				if(connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return fanCount;


	}

	public String getDjEmailId(String djProfileURLId) throws ClassNotFoundException, SQLException{
		String djEmailId=null;
		String query = "SELECT emailId FROM MIXTRI.users WHERE profileURLId=?";

		ResultSet rs =null;
		try{
			connection = getConnection();
			statement = connection.prepareStatement(query);    
			statement.setString(1, djProfileURLId);
			rs = statement.executeQuery();

			while(rs.next()){
				djEmailId = rs.getString("emailId");
			}

		}finally{

			try {
				if(statement != null)
					statement.close();
				if(connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}


		return djEmailId;
	}


	public boolean isFan(String fanEmailId,String djEmailId) throws ClassNotFoundException, SQLException{

		boolean isFan = false;

		String query = "SELECT id FROM MIXTRI.FANS WHERE fanEmailId=? AND djEmailId=?";
		ResultSet rs =null;
		try{
			connection = getConnection();
			statement = connection.prepareStatement(query);    
			statement.setString(1, fanEmailId);
			statement.setString(2, djEmailId);
			rs = statement.executeQuery();

			if(rs.next()){
				isFan = true;
			}

		}finally{

			try {
				if(statement != null)
					statement.close();
				if(connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return isFan;


	}

}
