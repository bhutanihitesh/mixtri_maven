package com.mixtri.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.NumberFormatter;

import org.apache.log4j.Logger;

public class MixtriUtils{

	static Logger log = Logger.getLogger(MixtriUtils.class.getName());
	
	
	public static boolean isValidEmailAddress(String email) {
		String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
		Pattern p = Pattern.compile(ePattern);
		Matcher m = p.matcher(email);
		return m.matches();
	}

	public static boolean isValidPhonenumber(String contact){

		Pattern pattern = Pattern.compile("\\+\\d{2}-\\d{10}");

		Matcher matcher = pattern.matcher(contact);
		return matcher.matches();	      
	}


	public static long getTimeInMins(String time){
		String arr[] = time.split(":"); // split it at the colons
		//converting the time to minutes
		long  timeInMinutes = (Long.parseLong(arr[0]))* 60  + Long.parseLong((arr[1])); 
		return timeInMinutes;
	}
	
	public static String formatTime(long min) throws ParseException{
		
		long hours = TimeUnit.MINUTES.toHours(min);
		long remainMinute = min - TimeUnit.HOURS.toMinutes(hours);
		String result = String.format("%02d", hours) + ":" 
		                    + String.format("%02d", remainMinute);
		
		DateFormat f1 = new SimpleDateFormat("HH:mm"); //HH for hour of the day (0 - 23)
		Date d = f1.parse(result);
		DateFormat f2 = new SimpleDateFormat("hh:mm a"); //Converts it to AM PM Format
		 
		
		return f2.format(d);
	}
	
	public static long getServerTimeInSeconds(String timeZone) throws ParseException{
		long seconds;
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy|HH:mm");
		sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		String serverTime = sdf.format(date); //This will get the current time in that time zone.
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy|HH:mm");
		Date date1 = simpleDateFormat.parse(serverTime);
		seconds = date1.getTime()/1000;
		/*long minutes = getTimeInMins(serverTime);
		seconds = minutes*60;*/
		
		return seconds;
		
	}
	
	
	public static boolean isNumeric(String str)  
	{  
		try  
		{  
			Integer integer = Integer.parseInt(str);  
		}  
		catch(NumberFormatException nfe)  
		{  
			return false;  
		}  
		return true;  
	}
	
	public static String replaceLastTwo(String s,String replaceWith) {
        int length = s.length();
        return s.substring(0, length - 2) +replaceWith;
    }
	
	public static String getUUID(){
		String uuid=null;
		uuid = UUID.randomUUID().toString().replaceAll("-", "");
		return uuid;
	}
	
	public static String getDateAsString(Timestamp timestamp){
		String d=null;
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		//d = df.format(date);
		return d;
	}
	
	/**
	 * Converts the date/time from one timezone to another
	 * @param strDate
	 * @param fromTimeZone
	 * @param toTimezone
	 * @return
	 * @throws ParseException
	 */
	public static Timestamp convertToTimezone(String strDate, String fromTimeZone,String toTimezone) throws ParseException{
		
		String format = "yyyy-MM-dd HH:mm:ssz";
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		Date dateStr = formatter.parse(strDate+fromTimeZone);
		formatter.setTimeZone(TimeZone.getTimeZone(toTimezone));
		String formattedDate = formatter.format(dateStr);
		formattedDate = formattedDate.replace(toTimezone,"");
		Timestamp sqlTimestamp = Timestamp.valueOf(formattedDate);
		
		return sqlTimestamp;
	}
	
	public static String convertDateToAnotherFormat(String dateToBeformatted, String fromFormat,String toFormat) throws ParseException{
		
		 DateFormat originalFormat = new SimpleDateFormat(fromFormat);
		 DateFormat targetFormat = new SimpleDateFormat(toFormat);
		 Date date = originalFormat.parse(dateToBeformatted);
		 String formattedDate = targetFormat.format(date);
		
		return formattedDate;
		
	}
	
	public static Map<String,String> getDateAndGMTId(String zoneId){
		
		Map<String,String> webUserDateTimezone = new HashMap<String,String>();
		
		//LocalDateTime curTime = LocalDateTime.now();
		//String gmtZoneId = "GMT" + ZoneId.of(zoneId).getRules().getOffset(curTime);
		
		TimeZone tz1 = TimeZone.getTimeZone(zoneId);
		String gmtZoneId = displayTimeZone(tz1);
		TimeZone tz = TimeZone.getTimeZone(gmtZoneId);
		Calendar c = Calendar.getInstance(tz);
		String webUserCurrentDate = c.get(Calendar.YEAR)+"-"+String.format("%02d",(c.get(Calendar.MONTH)+1))+"-"+String.format("%02d",c.get(Calendar.DAY_OF_MONTH));
		String webUserCurrentTime = String.format("%02d",c.get(Calendar.HOUR_OF_DAY))+":"+String.format("%02d",c.get(Calendar.MINUTE))+":"+String.format("%02d",c.get(Calendar.SECOND));
		
		webUserDateTimezone.put("webUserCurrentDate", webUserCurrentDate);
		webUserDateTimezone.put("gmtZoneId", gmtZoneId);
		webUserDateTimezone.put("webUserCurrentTime",webUserCurrentTime);
		
		return webUserDateTimezone;

	}
	
	public static String addToString(String source, char separator, String toBeInserted) {
        int index = source.lastIndexOf(separator); 
        if(index >= 0&& index<source.length())
    return source.substring(0, index+1) + toBeInserted + source.substring(index+1);
        
        return null;
        
    }
	
	public static void deleteUserHomeDirectory(String path){
		
		File directory = new File(path);
		 
    	//make sure directory exists
    	if(!directory.exists()){
 
           log.info("Directory does not exist.");
    	}else{
 
           try{
        	   
               delete(directory);
        	
           }catch(IOException e){
               e.printStackTrace();
           }
        }
 
    	log.debug("Directory Deleted Successfully at path:  "+path);
	}
	
	
	public static void delete(File file)
	    	throws IOException{
	 
	    	if(file.isDirectory()){
	 
	    		//directory is empty, then delete it
	    		if(file.list().length==0){
	    			
	    		   file.delete();
	    		   log.info("Directory is deleted : " 
	                                                 + file.getAbsolutePath());
	    			
	    		}else{
	    			
	    		   //list all the directory contents
	        	   String files[] = file.list();
	     
	        	   for (String temp : files) {
	        	      //construct the file structure
	        	      File fileDelete = new File(file, temp);
	        		 
	        	      //recursive delete
	        	     delete(fileDelete);
	        	   }
	        		
	        	   //check the directory again, if empty then delete it
	        	   if(file.list().length==0){
	           	     file.delete();
	        	     log.debug("Directory is deleted : " 
	                                                  + file.getAbsolutePath());
	        	   }
	    		}
	    		
	    	}else{
	    		//if file, then delete it
	    		file.delete();
	    		log.debug("File is deleted : " + file.getAbsolutePath());
	    	}
	    }
	
	private static String displayTimeZone(TimeZone tz) {

		long hours = TimeUnit.MILLISECONDS.toHours(tz.getRawOffset());
		long minutes = TimeUnit.MILLISECONDS.toMinutes(tz.getRawOffset()) 
                                  - TimeUnit.HOURS.toMinutes(hours);
		
		// avoid -4:-30 issue
		minutes = Math.abs(minutes);

		String result = "";
		NumberFormat formatter = new DecimalFormat("00");
		
		if (hours > 0) {
		
			String strHrs = formatter.format(hours);
			String strMins= formatter.format(minutes);
			result = "GMT+"+strHrs+":"+strMins;
		} else {
			
			
			String strHrs = formatter.format(hours);
			String strMins= formatter.format(minutes);
			result = "GMT"+strHrs+":"+strMins;
		}

		return result;

	}

	public static void main(String[] args) {
		
		try{


			ProcessBuilder builder = new ProcessBuilder(
					"cmd.exe", "/c", "cd \"C:\\Program Files (x86)\\VideoLAN\\VLC\" && vlc --sout #transcode{vcodec=none,acodec=mp3,ab=128,channels=2,samplerate=44100}:std{access=shout,mux=ogg,dst=source:mixtri@52.77.202.27:80/mixtri/mystream.mp3} http://ec2-52-77-202-27.ap-southeast-1.compute.amazonaws.com/mixtri/hitesh.ogg");
		    builder.redirectErrorStream(true);
	        Process p = builder.start();
	        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
	        String line;
	        while (true) {
	            line = r.readLine();
	            if (line == null) { break; }
	            System.out.println(line);
	        }
		}catch(IOException exp){
			System.out.println("Exception Occured while executing windows command line commands");
		}
	}
}