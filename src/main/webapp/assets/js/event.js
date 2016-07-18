var clock;	
$(document).ready(function() {
	
	//Setting all the required info on the page
	
	//Disabling the join buttons and activate them just 30 mins before the event starts.
	
	$('#btnJoin1').attr('disabled','disabled');
	$('#btnJoin2').attr('disabled','disabled');
	
	var profileURLId = getQueryVariable('profileURLId');
	var eventId = getQueryVariable('eventId');
	
	$.cookie('eventId',eventId,{ path: '/'});
	
	function getQueryVariable(variable) {
		  var query = decodeURIComponent(window.location.search.substring(1));
		  var vars = query.split("&");
		  for (var i=0;i<vars.length;i++) {
		    var pair = vars[i].split("=");
		    if (pair[0] == variable) {
		      return pair[1];
		    }
		  }
	 }
	
	
	//Show Manage Live Stream links only to the Dj.
	$('#manageLiveStreamLinks').hide();
	
	if(profileURLId==loggedInUserProfileId){
		$('#manageLiveStreamLinks').show();
	}
	
	//Show modal on load only to Dj who has setup the event.
	var loggedInUserProfileId = $.cookie('profileURLId');
	 
	 if(profileURLId==loggedInUserProfileId){
		 var liveStreamURL = $.cookie('liveStreamURL');
		 $('#liveStreamURL').html(liveStreamURL);
		 $('#eventSetUpModal').modal('show');	 
	 }
	
	
	//Get event info from the database
	
	//var streamInfo = $.cookie('streamInfo');
	$.ajax({
					
				 	type: 'GET',
					url: '/mixtri/rest/event-info',
					dataType: 'json',
					data: {
						eventId: $.cookie('eventId'),
						profileURLId: profileURLId
					},
				    
				    success: function(result){
				    	$('#djName').html(result.displayName);
				    	$('#streamInfo').html(result.streamInfo);
				    	$('#listTime').html(" "+result.eventTime+" OWNWARDS");
				    	$('#spanTime').html(" "+result.eventTime+" ownwards");
				    	$.cookie('eventId',result.id,{ path: '/'});
				    	
				    	var eventDate = result.eventDate;
				    	var arrDate = eventDate.split(" ");
				    	var day = arrDate[0];
				    	var month = arrDate[1];
				    	var seconds = parseInt(result.seconds);
				    	var streamingOption = result.streamingOption;
				    	var eventPicPath = result.eventPicPath;
				    	
				    	initializeCountdown(seconds);
				    	
				    	$('#spanDay').html(day);
				    	$('#spanMonth').html(month);
				    	$('#spanDate').html(" "+eventDate);
				    	$('.djName').html(result.displayName);
				    	$('#eventPic').attr('src',eventPicPath);
				    	
				    	
				    	if(streamingOption=="panel-icecast" && profileURLId==loggedInUserProfileId)
				    		$('#btnIcecastSettings').removeClass('hidden');
				    	
				    },
				    
				    error: function(result){
				    	
				    	window.location.href = "error.jsp";
				    }
				    
				    
	});		    
	
	
	 //Setting up clock
	 function initializeCountdown(seconds){
		
		 //var clock;

		clock = $('.event-countdown-clock').FlipClock({
	        clockFace: 'DailyCounter',
	        autoStart: false,
	        callbacks: {
	        	stop: function() {
	        		$('.message').html('The clock has stopped!')
	        	}
	        }
	    });
			    
	    clock.setTime(seconds);
	    clock.setCountdown(true);
	    clock.start();
	   //Enables the Join button 30 mins before the event starts on page load. 
	    if(clock.getTime()!=null && clock.getTime()<=1800){
			  $('#btnJoin1').removeAttr('disabled');
			  $('#btnJoin2').removeAttr('disabled');
	    }	  
	    
	 } 
	 
	 
	 $('#btnJoin1').on('click',function(e){
		 
		 loggedInUserProfileId = $.cookie('profileURLId');
		 
		 if (!window.location.origin) { // Some browsers (mainly IE) does not have this property, so we need to build it manually...
			  window.location.origin = window.location.protocol + '//' + window.location.hostname + (window.location.port ? (':' + window.location.port) : '');
			}
		 
		 var base_url = window.location.origin;
		 var profileURL = base_url+'/mixtri/live.jsp?profileURLId='+profileURLId+'&eventId='+eventId;
		 window.location.href = profileURL; 
		 
	 });


	$('#btnJoin2').on('click',function(e){
     loggedInUserProfileId = $.cookie('profileURLId');
     
	 if (!window.location.origin) { // Some browsers (mainly IE) does not have this property, so we need to build it manually...
		  window.location.origin = window.location.protocol + '//' + window.location.hostname + (window.location.port ? (':' + window.location.port) : '');
	 }
	 var base_url = window.location.origin;
	 var profileURL = base_url+'/mixtri/live.jsp?profileURLId='+profileURLId+'&eventId='+eventId;
	 window.location.href = profileURL; 
	 
	});
	
	/************************ Facebook Share******************************/
	
	$('#share_button').on('click',function(e){
		
		FB.ui(
				{
					method: 'feed',
					name: 'Listen to '+$('#djName')[0].innerText+' Spinning Live Exclusively @ Mixtri',
					link: 'www.mixtri.com',
					picture: 'http://cdn.shopify.com/s/files/1/0277/1157/products/nabi_Headphones_FrontView_1500_v3_e4114922-e90a-420b-b6fe-bf2c7b888f9e_1024x1024.png?v=1423341327',
					message: ''
				});
	});
	//window.location.href
	//https://d85wutc1n854v.cloudfront.net/live/products/600x375/WB0PGGM81.png?v=1.0
	//http://cdn.shopify.com/s/files/1/0277/1157/products/nabi_Headphones_FrontView_1500_v3_e4114922-e90a-420b-b6fe-bf2c7b888f9e_1024x1024.png?v=1423341327
	
	/*********************************************************************/
	 
});