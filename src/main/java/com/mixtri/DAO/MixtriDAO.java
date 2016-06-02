package com.mixtri.DAO;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.mixtri.event.EventBean;
import com.mixtri.event.EventDB;
import com.mixtri.login.LoginDB;
import com.mixtri.login.UserLoginBean;
import com.mixtri.profile.ProfileDB;
import com.mixtri.signup.SignUpDB;
import com.mixtri.signup.UserSignUpBean;
import com.mixtri.tracks.TrackBean;
import com.mixtri.tracks.TrackDB;
import com.mixtri.uploader.UploaderBean;
import com.mixtri.uploader.UploaderDB;


public class MixtriDAO {
	static Logger log = Logger.getLogger(MixtriDAO.class.getName());

	public UserLoginBean retriveLoginInfoDAO(UserLoginBean userLoginBean) throws SQLException, Exception{

		LoginDB loginDB = new LoginDB();
		
		userLoginBean = loginDB.getLoginInfoDB(userLoginBean);
        
		return userLoginBean;
	}

	public UserSignUpBean setSignUpInfoDAO(UserSignUpBean userSignUpBean) throws SQLException,Exception{
		SignUpDB signUpDB = new SignUpDB();

		int insertedRows;
		insertedRows = signUpDB.createNewUserDB(userSignUpBean);

		//If a valid GUID is return then setUsercreated = true; 		
		if(insertedRows>0){
			userSignUpBean.setUsercreated(true);
			log.debug("User created Successfully");
		}	
		else{

			userSignUpBean.setUsercreated(false);
			log.debug("Error creating user");
		}

		return userSignUpBean;
	}
	
	public List<Map<String,Object>> getAllTracksDAO(String query) throws ClassNotFoundException, SQLException{
		
		//Later trackbeans will be set here from the resultset returned by TrackDB class
		TrackDB trackdb=new TrackDB();
		return trackdb.getAllTracksDB(query);
		
	}
	
	public List<Map<String,String>> getUserPastTracksInfoDAO(String emailId) throws Exception{
		List<Map<String,String>> listUploadedTracks;
		UploaderDB uploaderDB = new UploaderDB();
		log.debug("Get userPastTrackInfo");
		listUploadedTracks = uploaderDB.getPastUploadedTracksDB(emailId);
		
		
		return listUploadedTracks;
	}
	
	public void saveUploadedMixDAO(UploaderBean uploaderBean) throws ClassNotFoundException, SQLException{
		
		log.debug("Inside saveUploadedMixDAO): "+uploaderBean);
		UploaderDB uploaderDB = new UploaderDB();
		uploaderDB.saveUploadedMixDB(uploaderBean);
		
	}
	
	/**
	 * Saves the info for an event
	 * @param eventBean
	 * @return
	 * @throws IOException
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public boolean saveEventInfoDAO(EventBean eventBean) throws IOException, ClassNotFoundException, SQLException{
		int insertedRows;
		boolean isSaved = false;
		log.debug("Inside saveEventInfoDAO: "+eventBean);
		
		EventDB eventDB = new EventDB();
		insertedRows = eventDB.saveEventInfoDB(eventBean);
		
		if(insertedRows>0){
			isSaved = true;
		}else{
			
			isSaved = false;
		}
		
		return isSaved;
		
	}
	/**
	 * Get info of the event for the user
	 * @param streamInfo
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public boolean getEventInfoDAO(String streamInfo,String profileURLId) throws ClassNotFoundException, SQLException{
		
		log.debug("Inside getEventInfoDAO");
		
		boolean eventExists=false;
		EventDB eventDB = new EventDB();
		eventExists = eventDB.getEventInfoDB(streamInfo,profileURLId);
		return eventExists;
	}
	
	public Map<String,Object> getSetupEventInfoDAO(String eventId,String profileURLId,String fanEmailId) throws ClassNotFoundException, SQLException, ParseException{
		Map<String,Object> eventInfo = new HashMap<String, Object>();
		
		EventDB eventDB = new EventDB();
		eventInfo = eventDB.getSetupEventInfoDB(eventId,profileURLId,fanEmailId);
		
		return eventInfo;
	}
	
	/**
	 * This function calculates the diskspace of the user and return it to the main method which in turn return it to the client. 
	 * @param emailId
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public float getUsedDiskSpaceDAO(String emailId) throws ClassNotFoundException, SQLException{
		float usedDiskSpace=0;
		UploaderDB uploaderDB = new UploaderDB();
		usedDiskSpace = uploaderDB.getUserDiskSpaceDB(emailId);
		return usedDiskSpace;
	}
	
	public List<Map<String,Object>> getLiveUpcomingEventsDAO(String webUserTimeZone,EventBean eventBean,String filter,int offset,int limitPerPage) throws ClassNotFoundException, SQLException, ParseException{
		List<Map<String,Object>> liveUpcomingEvents;
		EventDB eventDB = new EventDB();
		liveUpcomingEvents = eventDB.getLiveUpcomingEventsDB(webUserTimeZone,eventBean,filter, offset, limitPerPage);
		
		return liveUpcomingEvents;
		
	}
	
	public void saveEventFeedbackDAO(EventBean eventBean) throws ClassNotFoundException, SQLException{
		
		EventDB eventDB = new EventDB();
		eventDB.saveEventFeedbackDB(eventBean);
	}
	
	public void updateEventStatusDAO(String eventId,String isLive) throws ClassNotFoundException, SQLException{
		EventDB eventDB = new EventDB();
		eventDB.updateEventStatusDB(eventId,isLive);
	}
	
	public Map<String,Object> updateAttendeeCountDAO(String eventId,String emailId) throws ClassNotFoundException, SQLException{
		EventDB eventDB = new EventDB();
		Map<String,Object> attendeeInfo = new HashMap<String, Object>();
		attendeeInfo = eventDB.updateAttendeeCountDB(eventId,emailId);
		
		return attendeeInfo;
	}
	
	public Map<String,Object> removeAttendeeFromEventDAO(String attendeeId,String eventId) throws ClassNotFoundException, SQLException{
		Map<String,Object> attendeeCount;
		EventDB eventDB = new EventDB();
		attendeeCount = eventDB.removeAttendeeFromEventDB(attendeeId,eventId);
		
		return attendeeCount;
		
	}
	
	public Map<String,Object> updateKudosCount(String eventId) throws ClassNotFoundException, SQLException{
		Map<String,Object> kudosCount;
		EventDB eventDB = new EventDB();
		kudosCount = eventDB.updateKudosCountDB(eventId);
		
		return kudosCount;
		
	}
	
	public Map<String,Object> updateFanCountDAO(String fanEmailId,String djProfileURLId,String type) throws ClassNotFoundException, SQLException{
		Map<String,Object> fanCount;
		EventDB eventDB = new EventDB();
		fanCount = eventDB.updateFanCountDB(fanEmailId,djProfileURLId,type);
		
		return fanCount;
		
	}
	
	public  Map<String, Object> getProfileInfoDAO(String emailId) throws ClassNotFoundException, SQLException{
		
		Map<String,Object> profileInfo;
		ProfileDB profileDB = new ProfileDB();
		profileInfo = profileDB.getProfileInfoDB(emailId);
		return profileInfo;
	}
	
	public void updateProfileDAO(UserSignUpBean userSignUpBean) throws ClassNotFoundException, SQLException{
		
		ProfileDB profileDB = new ProfileDB();
		
		profileDB.updateProfileDB(userSignUpBean);
		
	}
	
	public void changePasswordDAO(UserSignUpBean userSignUpBean) throws ClassNotFoundException, SQLException{
		ProfileDB profileDB = new ProfileDB();
		profileDB.changePasswordDB(userSignUpBean);
	}

	public void deleteAccountDAO(String emailId) throws ClassNotFoundException, SQLException{
		
		ProfileDB profileDB = new ProfileDB();
		profileDB.deleteAccountDB(emailId);
		
	}
	
	public void deleteUploadedTrackdDAO(String uploadedSetId) throws ClassNotFoundException, SQLException{
		
		UploaderDB uploaderDB = new UploaderDB();
		uploaderDB.deleteUploadedTrackDB(uploadedSetId);
		
	}
}
