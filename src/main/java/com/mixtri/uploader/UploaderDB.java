package com.mixtri.uploader;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.mixtri.database.ConnectionFactory;
import com.mixtri.event.EventDB;
import com.mixtri.utils.MixtriUtils;

public class UploaderDB {
	private static Logger log = Logger.getLogger(UploaderDB.class.getName());


	Connection connection;
	PreparedStatement statement;

	private Connection getConnection() throws SQLException,ClassNotFoundException 
	{
		Connection con = ConnectionFactory.getInstance().getConnection();
		return con;
	}

	public void saveUploadedMixDB(UploaderBean uploaderBean) throws SQLException, ClassNotFoundException{


		try {

			String query ="INSERT INTO mixtri.archivedmixes(id,emailId,audioTitle,fileSize,audioSrc,dateUploaded,artistDisplayName,type,googleDriveFileId)"
					+ "VALUES(?,?,?,?,?,?,?,?,?)";

			connection = getConnection();

			String displayName = getDisplayName(uploaderBean.getEmailId());

			statement = connection.prepareStatement(query);    
			statement.setString(1, uploaderBean.getId());
			statement.setString(2, uploaderBean.getEmailId());
			statement.setString(3, uploaderBean.getTitle());
			statement.setDouble(4, uploaderBean.getFileSize());
			statement.setString(5, uploaderBean.getFilePath());
			statement.setDate(6,   uploaderBean.getDateUploaded());
			statement.setString(7, displayName);
			statement.setString(8, "upload");
			statement.setString(9, uploaderBean.getGoogleFileId());
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

	public String getDisplayName(String emailId) throws ClassNotFoundException, SQLException{

		String query = "SELECT DISPLAYNAME FROM mixtri.users WHERE EMAILID=?";
		String displayName = null;
		ResultSet rs =null;


		statement = connection.prepareStatement(query);    
		statement.setString(1, emailId);    
		rs = statement.executeQuery();

		while(rs.next()){
			displayName = rs.getString("displayName");
		}


		return displayName;
	}

	public List<Map<String,String>> getPastUploadedTracksDB(String emailId) throws Exception{

		List<Map<String,String>> listUploadedTracks;
		ResultSet rs=null;

		log.debug("Entry: getPastUploadedTracks");
		listUploadedTracks = getTracksInfo(emailId);

		return listUploadedTracks;

	}


	public List<Map<String,String>> getTracksInfo(String emailId) throws Exception{


		String query = "SELECT ID,AUDIOTITLE,audioSrc,googleDriveFileId FROM mixtri.archivedmixes WHERE type='upload' AND EMAILID=? order by dateUploaded asc";
		List<Map<String,String>> listUploadedTracks = new ArrayList<Map<String,String>>();
		ResultSet rs =null;
		try{


			connection = getConnection();
			statement = connection.prepareStatement(query);    
			statement.setString(1,emailId);    
			rs = statement.executeQuery();

			while(rs.next()){

				Map<String,String> mapOrgNames = new HashMap<String,String>();
				String id = rs.getString("id");
				String audioTitle = rs.getString("audioTitle");
				String audioSrc = rs.getString("audioSrc");
				String googleDriveFileId = rs.getString("googleDriveFileId");
				mapOrgNames.put("id",id);
				mapOrgNames.put("audioTitle",audioTitle);
				mapOrgNames.put("audioSrc",audioSrc);
				mapOrgNames.put("googleDriveFileId",googleDriveFileId);
				listUploadedTracks.add(mapOrgNames);
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

		return listUploadedTracks;

	}

	public float getUserDiskSpaceDB(String emailId) throws ClassNotFoundException, SQLException{

		String query = "SELECT SUM(FILESIZE) AS USED_DISK_SPACE FROM mixtri.archivedmixes WHERE EMAILID=?";
		ResultSet rs =null;
		float usedDiskSpace = 0;
		try{

			connection = getConnection();
			statement = connection.prepareStatement(query);    
			statement.setString(1,emailId);    
			rs = statement.executeQuery();

			if(rs.next()){

				usedDiskSpace= rs.getFloat("USED_DISK_SPACE");
				//Space in MB used by the DJ
				usedDiskSpace = usedDiskSpace/1000000;
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


		return usedDiskSpace;

	}

	public void deleteUploadedTrackDB(String uploadedSetId) throws ClassNotFoundException, SQLException{

		String query = "DELETE FROM mixtri.archivedmixes WHERE googleDriveFileId=?";
		
		try{

			connection = getConnection();
			statement = connection.prepareStatement(query);    
			statement.setString(1,uploadedSetId);    
			statement.executeUpdate();

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
		 
	}
	
	public String getTrackPath(String uploadedSetId) throws ClassNotFoundException, SQLException{
		String trackPath=null;
		
		String query = "SELECT audioSrc FROM mixtri.archivedmixes WHERE ID=?";
		ResultSet rs=null;
		try{

			connection = getConnection();
			statement = connection.prepareStatement(query);    
			statement.setString(1,uploadedSetId);    
			rs = statement.executeQuery();
			
			while(rs.next()){
				trackPath = rs.getString("audioSrc");
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
		
		return trackPath;
		
	}
	

}
