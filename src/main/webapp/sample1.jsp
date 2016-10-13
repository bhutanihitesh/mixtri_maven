<!-- 
/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */
-->


<!-- <!DOCTYPE html>
<html>
<body>

<audio controls>
  <source src="http://ec2-52-77-202-27.ap-southeast-1.compute.amazonaws.com/mixtri/ddded585a2f24aa1be99f681fa858494.ogg" type="audio/ogg">
  <source src="http://ec2-52-77-202-27.ap-southeast-1.compute.amazonaws.com/mixtri/ddded585a2f24aa1be99f681fa858494.ogg" type="audio/mpeg">
Your browser does not support the audio element.
</audio>

</body>
</html> -->


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Mixtri-Test-Page</title>
        
        <script type="text/javascript">
        
     
      
        function transcode(){
        	
        	console.log("Calling transcode");
        	
        	$.ajax({

    			type: 'POST',
    			
    		    /* url: 'http://ec2-52-77-202-27.ap-southeast-1.compute.amazonaws.com:8080/mediatranscoder/rest/transcode', */
    			url: 'http://localhost:8000/mediatranscoder/rest/transcode',
    			dataType: 'json',
    			data: {
    				streamId: 'hitesh',
    				streamingOption:'panel-recorded-mixes',
    				liveStreamSource:'hitesh'
    			},

    			success: function(result){
    				
    				console.log(result.pids);
    				//$('#streamAudioPlayer').play();
    				
    				console.log(result.pids);
    				var oggSrc = 'http://52.77.202.27/mixtri/c57934f21a854481baf37a258aedbd0e.ogg';
    				var mp3Src = 'http://52.77.202.27/mixtri/c57934f21a854481baf37a258aedbd0e.mp3';
    				
    				$('#srcOgg').attr('src',oggSrc);
    				$('#srcMp3').attr('src',mp3Src);
    				
    				$('#streamAudioPlayer')[0].load();
    				$('#streamAudioPlayer')[0].play();
					
    			},
    			error: function(result){
    				
    				
    				console.log("Cannot Transcode to .mp3: ");
    				console.log('Errors: '+result);
    				
    			}

    		});
        	
        }
        
        
        function killProcess(){
        	
        	var processIds='13040,12396,6332';
        	
        	$.ajax({
        		
		 type: 'POST',
		 	
    		    /* url: 'http://ec2-52-77-202-27.ap-southeast-1.compute.amazonaws.com:8080/mediatranscoder/rest/kill', */
    			url: 'http://localhost:8080/mediatranscoder/rest/kill',
    			dataType: 'json',
    			data: {
    				processIds:processIds
    			},
    			
				success: function(result){
    				
    				console.log(result.pids);
    				//$('#streamAudioPlayer').play();
					
    			},
    			
				error: function(result){
    				
    				
    				console.log("Cannot Kill the process: "+result);
    				console.log('Errors: '+result);
    				
    			}
        		
        	});
        	
        }
           
        function loadLiveStream(){
        	
        	var oggSrc = 'http://52.77.202.27/mixtri/c57934f21a854481baf37a258aedbd0e.ogg';
			var mp3Src = 'http://52.77.202.27/mixtri/c57934f21a854481baf37a258aedbd0e.mp3';
			
			$('#srcOgg').attr('src',oggSrc);
			$('#srcMp3').attr('src',mp3Src);
			
			$('#streamAudioPlayer')[0].load();
        	
        }
    
        function playLiveStream(){
        	
        	$('#streamAudioPlayer')[0].play();
        }
       
        </script>
        
    </head>
    <body>
    
  <button onclick="loadLiveStream()">Load Live Stream</button>
  <button onclick="playLiveStream()">Play Live Stream</button>
  <button onclick="transcode()">Transcode</button>
  <button onclick="killProcess()">killProcess</button>  
<audio id="streamAudioPlayer" preload="auto">
  <source id="srcOgg" src="" type="audio/ogg">
  <source id="srcMp3" src="" type="audio/mpeg">
Your browser does not support the audio element.
</audio>

<!-- http://52.77.202.27/mixtri/f61e3a3377b74fb7a006caa89673a413.mp3 -->
    
<!-- <audio controls oncanplaythrough="this.play();">
  <source src="http://ec2-52-77-202-27.ap-southeast-1.compute.amazonaws.com/mixtri/c57934f21a854481baf37a258aedbd0e.ogg" type="audio/ogg">
  <source src="http://ec2-52-77-202-27.ap-southeast-1.compute.amazonaws.com/mixtri/c57934f21a854481baf37a258aedbd0e.mp3" type="audio/mpeg">
Your browser does not support the audio element.
</audio> -->
    
    <!-- <div id="jPlayerLiveTrack">Jplayer Mixer</div>
    
    <audio controls preload="none" src="assets/audio/Meenal_Performance.mp3" type="audio/mpeg"></audio>
    <audio id="id" controls preload="none" src="assets/audio/Meenal_Performance.ogg" type="audio/ogg"></audio> -->
    
    
   <script type="text/javascript" src="assets/js/jquery-1.11.1.min.js"></script>
    </body>
</html>