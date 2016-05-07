var limitPerPage = 10;
$(document).ready(function() {

//Gives the current timezone of the web user so that we can show him events according to his timezone.
	
	var webUserTimeZone = getTimezoneName();
	
	function getTimezoneName() {
        timezone = jstz.determine()
        return timezone.name();
    } 
	
	showliveUpcomingEvents(1,'All');
    
  function showliveUpcomingEvents(pageNumber,filter){  
	
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
			
			buildHTMLForEvents(result,pageNumber);
			
			var numberOfPages = getNumberOfPages(result.noOfRecords);
			buildHtmlForPages(1,numberOfPages);
			bindPageClick();
			bindSideMenuClick();
			


		},
		error: function(listAllEvents){
			window.location.href = "error.jsp";
		}

	});
 }
  
  function buildHTMLForEvents(result,pageNumber){
	  
	  var html='';
		$('#liveUpcomingEvents').empty();
		//result.length will always return 10 because we have set limitPerPage to 20
		var listAllEvents = result.allEvents;
		
		var iMax = listAllEvents.length;
		if(iMax<20){
			
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

						div='<li class="Live-Djs-TimeLeft">in next <span id="timeLeft">'+parseInt(timeLeft)/60+'</span> hours</li>';
					}else{

						div='<li class="Live-Djs-TimeLeft">in next <span id="timeLeft">'+parseInt(timeLeft)+'</span> mins</li>';
					}
				}	
				var joinEventURL = 'event.jsp?profileURLId='+listAllEvents[i].profileURLId+'&eventId='+listAllEvents[i].id;
				html+='<div class="col-lg-5 col-md-5 col-sm-5">';
				html+='<div class="container-live-dj">';
				html+='<div class="Live-Djs">';
				html+='<div class="show-image"><div class="hoverJoin"><span style="margin-left: 6px;" class="fa fa-sign-in"></span><br><span>Join</span></div><a href="'+joinEventURL+'"><img style="width: 310px;height:320px;" src="'+listAllEvents[i].eventPicPath+'" alt="dummy"></a></div>';
				html+='<div class="Live-Djs-Content">';
				html+='<div class="Genre">'+listAllEvents[i].genre+'<span style="float: right"><i onclick=fbShare("'+listAllEvents[i].displayName+'","'+joinEventURL+'","'+listAllEvents[i].eventPicPath+'") class="fa fa-facebook-square" style="color: #268EE7; cursor: pointer;"> Share</i></span></div>';
				html+='<div class="Dj-name">';
				html+='<a href="'+joinEventURL+'">'+listAllEvents[i].displayName+'</a>';
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

		$('#liveUpcomingEvents').append(html);
		
		var currentStartingCount = (pageNumber-1)*limitPerPage+1;
		var recordCount = limitPerPage*pageNumber;
		var totalRecords = result.noOfRecords;
		
		if(recordCount>totalRecords){
		
			recordCount = totalRecords;
		}
		if(totalRecords==0){
			currentStartingCount=0;
		}
		
		$('#displayRange').html('Displaying '+currentStartingCount+'-'+recordCount+' of '+totalRecords);
  }
	
	var bindPageClick=function(){
		 
		 $('.news-feed-btn').on('click','a',function(e){
			 
			 e.preventDefault();
			
			 var page=$(this).attr('id');
			 
			 if(page === 'next'){
				 
				 var prevPage=$('.activePage').attr('id');
				 page=parseInt(prevPage)+1;
				 
				 if(page > last)
					 page=last;
				 
				 $('.pages li > a').removeClass('activePage');
				 $('#'+page).addClass('activePage');
				 
			 }else if (page ==='previous'){
				 
				 var prevPage=$('.activePage').attr('id');
				 page=parseInt(prevPage)-1;
				 
				 if(page < first)
					 page=first;
				 
				 $('.pages li > a').removeClass('activePage');
				 $('#'+page).addClass('activePage');
					 
			}else{
				
				 $('.pages li > a').removeClass('activePage');
				 $('#'+page).addClass('activePage');
				 
			 }
			 
			console.log(page);
			
			var sideElementId; 
			if($('.activeSideElement').length>0){
				sideElementId = $('.activeSideElement').get(0).id;
			}
			var filterValue;
			if(sideElementId=='mostPopular'){
				filterValue = 'mostPopular';
			}else if(sideElementId=='nowLive'){
				
				filterValue = 'live';
			}else{
				filterValue = 'All';
			}
			
			
			getLiveUpcomingEvents(page,filterValue);
			 
		 });
	 }
	
	
	var bindSideMenuClick=function(){
		
		$('.djsignup').on('click','a',function(e){
			e.preventDefault();
			var elementId = $(this).get(0).id;
			
			if(elementId=='nowLive'){
				
				$('#liveUpcomingEvents').empty();
				getLiveUpcomingEvents(1,'live');
				
				
			}
			
			if(elementId=='mostPopular'){
			  $('#liveUpcomingEvents').empty();
			  getLiveUpcomingEvents(1,'mostPopular');
			}
		     
			$('.activePage').removeClass('activePage');
			$('#1').addClass('activePage');
			
			//On click of the side Menu set drop down value to Music Genre-All
			$('#musicGenre').val('All');
			
		});
		
		
	
	}
	
	
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
			    
				if(!($('.activePage')[0]==undefined)){
				  var currentActivePageId = $('.activePage')[0].id;
				}
				$('.news-feed-btn').empty();
				buildHTMLForEvents(result,pageNumber);
				
				var numberOfPages = getNumberOfPages(result.noOfRecords);
				buildHtmlForPages(1,numberOfPages);
				
				if(parseInt(currentActivePageId)>1){
				 $('#1').removeClass('activePage');
				}
				
				$('#'+currentActivePageId).addClass('activePage');
				
				var alreadySelectedFilter = $('.activeSideElement');
				$(alreadySelectedFilter).removeClass('activeSideElement');
				$(alreadySelectedFilter).css('color','white');
				
				if(filter=='live'){
				  $('#nowLive').addClass('activeSideElement');
				  $('#nowLive').css('color','#e62948');
				}
				
				if(filter=='mostPopular'){
					  $('#mostPopular').addClass('activeSideElement');
					  $('#mostPopular').css('color','#e62948');
				}

			},
			error: function(listAllEvents){
				window.location.href = "error.jsp";
			}

		});
		
		
		
	}
	
	 var first=0;
	 var last=0;
	 
	 var buildHtmlForPages=function(firstPage,lastPage){
		
		  first=firstPage;
		  last=lastPage;
		 var html='';
		 
		 html+='<ul class="pages"><li><a href="#" id="previous"><b class="fa fa-angle-left"></b></a></li>';
		 for(var i=first;i<=last;i++){
			 
				if(i==1)				 
					html+='<li><a class="activePage" id='+i+' href="#">'+i+'</a></li>';
				else
					html+='<li><a href="#" id='+i+'>'+i+'</a></li>';
					
		 }
		 html+='<li><a href="#" id="next"><b class="fa fa-angle-right"></b></a></li></ul>';
		 
		 $('.news-feed-btn').append(html);
	 }
	 
	//This function counts the number of tracks returned by the server and then calculates the number of pages to be displayed accordingly.	
		function getNumberOfPages(totalNoOfRecords){
			var i;
			var last = Math.floor(totalNoOfRecords/limitPerPage);
			
			if((totalNoOfRecords%limitPerPage) > 0)
				last++;
			
			return last;
		}
		
		$('#musicGenre').on('change',function(e){
			   var filter = $('#musicGenre').val();	
				getLiveUpcomingEvents(1,filter);
			});
		
		
		 /************************ Facebook Share******************************/ 
		  
		  fbShare = function (djName,joinEventURL,eventPicURL) {
			  var baseURL = document.location.origin+"/"+'mixtri/';
			  console.log(baseURL+joinEventURL);
			  console.log(baseURL+eventPicURL);
			  var fbShareURL = document.location.origin+"/"+'mixtri/'+joinEventURL;
			  
			  FB.ui(
					  {
						  method: 'feed',
						  name: 'Listen to '+djName+' Spinning Live Exclusively @ Mixtri',
						  link: 'www.mixtri.com',
						  picture: 'http://cdn.shopify.com/s/files/1/0277/1157/products/nabi_Headphones_FrontView_1500_v3_e4114922-e90a-420b-b6fe-bf2c7b888f9e_1024x1024.png?v=1423341327',
						  message: ''
					  });	
		  }
		

});