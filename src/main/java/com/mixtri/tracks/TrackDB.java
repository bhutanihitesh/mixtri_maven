package com.mixtri.tracks;

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



/* This class will interact with the database */

public class TrackDB {

	private static Logger log = Logger.getLogger(TrackDB.class.getName());
	
	Connection connection;
	PreparedStatement statement;

	private Connection getConnection() throws SQLException,ClassNotFoundException 
	{
		Connection con = ConnectionFactory.getInstance().getConnection();
		return con;
	}
	
	public List<Map<String,Object>> getAllTracksDB(String query) throws ClassNotFoundException, SQLException{
		
		List<Map<String,Object>> listArchivedTracks = new ArrayList<Map<String,Object>>();
		
		ResultSet rs =null;
		try{
			connection = getConnection();
			statement = connection.prepareStatement(query);
			rs = statement.executeQuery();

			while(rs.next()){
				
				Map<String,Object> trackMap = new HashMap<String, Object>();
				
				trackMap.put("artistImageSrc", rs.getString("artistImageSrc"));
				trackMap.put("popularity", rs.getInt("popularity"));
				trackMap.put("audioTitle", rs.getString("audioTitle"));
				trackMap.put("artist",rs.getString("artistDisplayName"));
				trackMap.put("audioSrc",rs.getObject("audioSrc"));
				
				listArchivedTracks.add(trackMap);

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

		return listArchivedTracks;
	}
	
	
}
