$(document).ready(function() {
	
	//Gives the current timezone of the web user so that we can show him events according to his timezone.
	
	var webUserTimeZone = getTimezoneName();
	
	function getTimezoneName() {
        timezone = jstz.determine()
        return timezone.name();
    }
	
    var pageNumber = 1;
    var limitPerPage = 10;
	$.ajax({
		
		type: 'GET',
		url: '/mixtri/rest/liveUpcomingEvents',
		data: {
				webUserTimeZone: webUserTimeZone,
				page:pageNumber,
				limitPerPage:limitPerPage,
				filter:'All'
		      },
		dataType: 'json',
		
		success: function(result){
			
			buildHTMLForPages(result);
			
	    },
	    error: function(result){
	      window.location.href = "error.jsp";
	    }
		
	});
	
	function buildHTMLForPages(result){
		
		var html='';
		//result.length will always return 10 because we have set limitPerPage to 10
		var listAllEvents = result.allEvents;
		
		var iMax = listAllEvents.length;
		if(iMax<10){
			
			iMax = listAllEvents.length;
		}
		
		for(var i=0;i<iMax;){
			
			html+='<div class="row">';
			for(var iCount=0;iCount<2;iCount++){
			  //Logic to handle even odd number of rows
				if(i==iMax){
					  break;
				  }	
			  var div;	
			  var isLive = listAllEvents[i].isLive;
			  var timeLeft = listAllEvents[i].timeLeft;
				if(isLive == 'Y'){
					div='<li class="Live-Djs-NowLive"><span class="fa fa-music"></span> Now Live!</li>';	
				}else if(parseInt(timeLeft)<=0){
					div='<li class="Live-Djs-DoorOpens">Doors Opened</li>';
				}else if(parseInt(timeLeft)>0){
					
					if(parseInt(timeLeft)>=60){
					  
						var hours = Math.floor( timeLeft / 60);          
					    var minutes = hours % 60;
					    
					    div='<li class="Live-Djs-TimeLeft">starts in next <span id="timeLeft">'+hours+'</span> hours and '+minutes+' mins</li>';
					    
					}else{
						
						div='<li class="Live-Djs-TimeLeft">starts in next <span id="timeLeft">'+parseInt(timeLeft)+'</span> mins</li>';
					 }
				}
			
			  var djName="\""+listAllEvents[i].displayName+"\"";
			  
			  /**
			   * Both eventURL and joinEventURL are same. Jus the way JSON returns the values which contains double quotes we are using two different variables.
			   */
			  var joinEventURL = 'event.jsp?profileURLId='+listAllEvents[i].profileURLId+'&eventId='+listAllEvents[i].id;
			  var eventURL = "\""+'event.jsp?profileURLId='+listAllEvents[i].profileURLId+'&eventId='+listAllEvents[i].id+"\"";
			  var eventLogo="\""+listAllEvents[i].eventPicPath+"\"";
			  var eventDate="\""+listAllEvents[i].eventDate+"\"";
			  var eventTime="\""+listAllEvents[i].eventTime+"\"";
			  var timeZone="\""+listAllEvents[i].timeZone+"\"";
			  
			  html+='<div class="col-lg-5 col-md-5 col-sm-5">';
			  html+='<div class="container-live-dj">';
			  html+='<div class="Live-Djs">';
			  html+='<div class="show-image"><div class="hoverJoin"><span style="margin-left: 6px;" class="fa fa-sign-in"></span><br><span>Join</span></div><a href="'+joinEventURL+'"><img style="width: 310px;height:320px;" src="'+listAllEvents[i].eventPicPath+'" alt="dummy"></a></div>';
			  html+='<div class="Live-Djs-Content">';
			  html+="<div class='Genre'>"+listAllEvents[i].genre+"<span style='float: right'><i onclick='fbShare("+djName+","+eventURL+","+eventLogo+","+eventDate+","+eventTime+","+timeZone+")' class='fa fa-facebook-square' style='color: #268EE7; cursor: pointer;'> Share</i></span></div>";
			  html+='<div class="Dj-name">';
			  html+='<a id='+i+' href="'+joinEventURL+'">'+listAllEvents[i].displayName+'</a>';
			  html+='</div><br>';
			  html+='<div class="stream">';
			  html+='<div>'+listAllEvents[i].streamInfo+'</div>';
			  html+='</div>';
			  html+='<div>';
			  html+='<ul>';
			  html+='<li class="Live-Djs-userIcon"><span class="fa fa-user"></span> '+listAllEvents[i].attendeeCount+'</li>';
			  html+='<li class="Live-Djs-userIcon"><span class="fa fa-beer"></span> Fans '+listAllEvents[i].fanCount+'</li>';
			  html+=div;
			  html+='</ul>';
			  html+='</div>';
			  html+='</div>';
			  html+='</div>';
			  html+='</div>';
			  html+='</div>';
			  i++;
			  
			}
			
			html+='</div>';
			
		}
		
		$('#liveUpcomingEvents').prepend(html);
		
		if(listAllEvents.length==0)
			pageNumber = 0;
		else
			pageNumber =  1;
		
		$('#displayRange').html('Displaying '+pageNumber+'-'+listAllEvents.length+' of '+result.noOfRecords);  
		
		
	}
	
	$('#musicGenre').on('change',function(e){
	   var filter = $('#musicGenre').val();	
		getLiveUpcomingEvents(1,filter);
	});
	
  function getLiveUpcomingEvents(pageNumber,filter){
		
		$.ajax({

			type: 'GET',
			url: '/mixtri/rest/liveUpcomingEvents',
			data: {
				webUserTimeZone: webUserTimeZone,
				page:pageNumber,
				limitPerPage:limitPerPage,
				filter:filter
				
		    },
			dataType: 'json',

			success: function(result){
			  
			  $('#liveUpcomingEvents').empty();
			  $('#displayRange').empty();
			  buildHTMLForPages(result);
				
			}
		});
		
		
		
	}
  /************************ Facebook Share******************************/ 
  
  fbShare = function (djName,joinEventURL,eventPicURL,eventDate,eventTime,timeZone) {
	  var baseURL = document.location.origin+"/"+'mixtri/';
	  var fbShareURL = document.location.origin+"/"+'mixtri/'+joinEventURL;
	  
	  FB.ui(
			  {
				  method: 'feed',
				  name: 'Listen to '+djName+' Spinning Live Exclusively @ Mixtri . Live Streaming starts on '+eventDate+' at '+eventTime+' '+timeZone+'',
				  link: baseURL+joinEventURL,
				  picture: eventPicURL,
				  message: 'Please Listen to me live!'
			  });	
  }
  

});