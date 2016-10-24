package com.mixtri.media.transcoder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import com.jezhumble.javasysmon.JavaSysMon;

import scala.Array;

public class UDKSpawner {

	static Logger log = Logger.getLogger(UDKSpawner.class.getName());

	/**
	 * Mutex that forces only one child process to be spawned at a time. 
	 * 
	 */
	private static final Object spawnProcessMutex = new Object();

	/**
	 * Spawns a new UDK process and sets {@link #uccPid} to it's PID. To work correctly,
	 * the code relies on the fact that no other method in this JVM runs UDK processes and
	 * that no method kills a process unless it acquires lock on spawnProcessMutex.
	 * @param procBuilder
	 * @return 
	 */
	public Map<String,Object> spawnUDK(ProcessBuilder procBuilder) throws IOException {
	    synchronized (spawnProcessMutex){            
	        JavaSysMon monitor = new JavaSysMon();
	        DirectUDKChildProcessVisitor beforeVisitor = new DirectUDKChildProcessVisitor();
	        monitor.visitProcessTree(monitor.currentPid(), beforeVisitor);
	        Set<Integer> alreadySpawnedProcesses = beforeVisitor.getUdkPids();
	        
	        Process process = procBuilder.start();
	        
	        Map<String, Object> hm = new HashMap<String,Object>();
	        hm.put("process", process);

	        DirectUDKChildProcessVisitor afterVisitor = new DirectUDKChildProcessVisitor();
	        monitor.visitProcessTree(monitor.currentPid(), afterVisitor);
	        Set<Integer> newProcesses = afterVisitor.getUdkPids();
	        List<Integer> pids = new ArrayList<Integer>(newProcesses);

	        newProcesses.removeAll(alreadySpawnedProcesses);
	        hm.put("pids", pids);

	        if(newProcesses.isEmpty()){
	            //uccLog.severe("There is no new UKD PID.");
	        	log.info("There is no new PID.");
	        }
	        return hm;
	    }
	}    

	public boolean killUDKByPID(List<String> pids){
	    boolean isSuccess=true;
	  
		try{
			
		for(String pid: pids){
			
			String cmd = "tasklist /fi \"pid eq "+pid;
			
			try {
			    String line;
			    Process p = Runtime.getRuntime().exec(cmd);
			    BufferedReader input =
			            new BufferedReader(new InputStreamReader(p.getInputStream()));
			    while ((line = input.readLine()) != null) {
			    	
			    	//This means the process is not tomcat process and it doesn't kill the server process.
			    	if(!line.contains("javaw.exe") && line.contains(pid)){
			    	 
			    		JavaSysMon monitor = new JavaSysMon();
			    		monitor.killProcessTree(Integer.parseInt(pid), false);
			    		
			    		log.info("Process Killed:" +line);
			    		
			    			
			    	}
			    }
			    input.close();
			} catch (Exception err) {
			    err.printStackTrace();
			}

		}
	  
	  }catch(Exception exp){
		 
		  System.out.println("Error killing process");
		  isSuccess=false;
	  }
		
		return isSuccess;
	}

}
