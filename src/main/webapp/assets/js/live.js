$(document).ready(function() {

	var websocket = new WebSocket("ws://"+ location.hostname+ ":" + location.port + "/mixtri/liveUpdate");
	//websocket.onopen = function(evt) { onOpen(evt) };
	websocket.onmessage = function(evt) { onMessage(evt) };
	websocket.onerror = function(evt) { onError(evt) };
	websocket.onclose = function(evt) {onClose(evt)};

	

	var liveStreamURL = $.cookie('liveStreamURL');	
    var isDj = false;
	
    //Saving the eventId for the user to submit the feedback, end and end event
	var eventId = getQueryVariable('eventId');
	
	var profileURLId = getQueryVariable('profileURLId');
	
	/**
	 * Load the siren first on page load and then play on button click of start live stream or join event.
	 */
	$('#playSiren')[0].load();

	//When the Dj starts to live stream set his cookie to isDj=true. So that in his chat session we can Identify if he is Dj then give him a different
	//color div when he writting anything in the chat box. And when he ends the event just remove the cookie isTrue.
	//This condition checks if the user who is joining the event is same as the logged in user i.e if Dj is himself joining his own event.
	//isDj cookie is being used in messengerWebsocket.js
	//Now we are setting this cookie in livestream.js file when the user is setting up the event.
	
	isDj = $.cookie('isDj');

	//Gets the url params
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

	$('.mixtri').on('click',function(){
		window.location.href = "index.jsp";
	});
	
	
	//Get event info from the database and gets called on document Load

	$.ajax({

		type: 'GET',
		url: '/mixtri/rest/event-info',
		dataType: 'json',
		data: {
			eventId: $.cookie('eventId'),
			profileURLId: profileURLId,
			fanEmailId: $.cookie('emailId')
		},

		success: function(result){

			$('#streamInfo').html(result.streamInfo);

			var seconds = parseInt(result.seconds);
			var eventPicPath = result.eventPicPath;
			var streamingOption = result.streamingOption;
			var bgVideoTheme = result.bgVideoTheme;
			var eventDescription = result.eventDescription;
			var genre = result.genre;
			var attendeeCount = result.attendeeCount;
			var kudosCount = result.kudosCount;
			var fanCount = result.fanCount;
			var isFan = result.isFan;
			var displayName = result.displayName;
			var city = result.city;
			var state = result.state;
			var country = result.country;
			var emailId = result.emailId;
			var contactNumber = result.contactNumber;

			//Show Dj Contact Info
			$('[data-toggle="popover"]').popover({
				html: true
			});
			var btnContactInfo = $('#btnShowDjContactInfo');
			btnContactInfo.attr('data-content','Place: '+city+', '+state+', '+country+'</br>Email: '+emailId+'</br> Phone: '+contactNumber);	

			$('.djName').html(displayName);
			$('#djName').html(displayName);
			$('.eventPic').attr('src',eventPicPath);
			$('#attendeeCount').html(' '+attendeeCount);
			$('#kudosCount').html(' '+kudosCount);
			$('#fanCount').html(' '+fanCount);

			if(eventDescription==''){
				$('#description').remove();
			}else{
				$('#eventDescription').html(eventDescription)
			}

			if(genre!=''){

				$('#genre').html(genre);

			}
			
			//if he is already a Fan show him the user
			if(isFan){
				$('#fan').addClass('fanKudos');
				$('#wordFanUnfan').html('Unfan');
			}


			$('#bgVideoTheme video source').attr('src', bgVideoTheme);

			var minutes = "0" + Math.floor(seconds / 60);
			var secs = "0" + (seconds - minutes * 60);
			var timeLeft = minutes +':' + secs;

			$('#timeLeft').html(timeLeft);
			timeLeft = $('#timeLeft');
			startTimer(seconds, timeLeft);

			if(streamingOption=="panel-icecast" && isDj){
				$('.btnIcecastSettings').removeClass('hidden');
				$('#divIcecastStreaming').removeClass('hidden');
			}	

		},

		error: function(result){

			window.location.href = "error.jsp";
		}


	});	


	//Function called at page load
	var obj = $('#blink');
	var obj1 = $('#isIcecastStreaming');
	textBlink(1000,obj);
	textBlink(1000,obj1);

	var windowHeight = $(window).height();
	var windowWidth = $(window).width();

	//Set the window height and width on page load
	$('#half1').css('width',windowWidth/2);
	$('#half2').css('width',windowWidth/2);
	$('#half1').css('height',windowHeight);
	$('#half2').css('height',windowHeight);

	//This Hides the countdown clock initially
	$('#countdown').hide();
	var countdown =  $("#countdown").countdown360({
		radius      : 60,
		seconds     : 10,
		fontColor   : '#FFFFFF',
		strokeStyle : '#e62948',
		fillStyle   : '#333333',
		fontColor   : '#e62948',
		autostart   : false,
		onComplete  : function () {

			$('#half1').animate({ right : windowWidth},{queue:false,duration:2500});  
			$('#half2').animate({ left : windowWidth},{queue:false,duration:2500});

			window.setTimeout(function(){
				$('.slidingGate').hide();
			}, 2500);

			var timeRemaining = countdown.getTimeRemaining();
			if(timeRemaining<='0'){
				$("#bgVideoTheme video")[0].load();
				$("#bgVideoTheme video")[0].play();
				
				var isDj = $.cookie('isDj');
				
				if(isDj==undefined || !isDj){
					
					//Auto Play the track when the page loads.
					$('#streamAudioPlayer')[0].play();
				}
			}		
		}
	});


	//Show Start Live Streaming button to Dj and Join Button to the user. The Button is same just the text is changed

	//Hide End Event button by default and show it only to the Dj.
	$('#btnEndEvent').hide();

	if(isDj){
		$('#btnJoinEvent').hide();
		$('#btnStartEvent').show();
		$('#btnEndEvent').show();
	}else{
		$('#btnJoinEvent').show();
		$('#btnStartEvent').hide();
	}

	//On button click on join event insert record in attendee table
	$('#btnJoinEvent').attr('disabled','disabled');


	$('#btnJoinEvent').on('click',function(e){

		//On Button click set the URLs for the html Audio Tags and we will figure out if we need to play it or not depending on if the user is a listener or a dj.
		
		var srcOgg = "http://52.77.202.27/mixtri/"+eventId+".ogg";
		var srcMp3 = "http://52.77.202.27/mixtri/"+eventId+".mp3";
		
		$('#srcOgg').attr('src',srcOgg);
		$('#srcMp3').attr('src',srcMp3);
		
		//Auto play of audio is not supported in mobile browsers. To play the audio automatically on page load we are doing a hack where we are loading the audio first when
		//User clicks on JoinEvent button and then when timer finishes it will play the music.
		$('#streamAudioPlayer')[0].load();

		$.ajax({

			type: 'POST',
			url: '/mixtri/rest/event/liveDataUpdate',
			contentType: "application/x-www-form-urlencoded",
			data: {
				eventId: eventId,
				emailId: $.cookie('emailId'),
				attendeeId: $.cookie('attendeeId'),
				type: 'joinEvent'
			},

			success: function(result){

				$.cookie('attendeeId', result.id,{ path: '/'});
				$('#countdown').show(1000);
				countdown.start();

				$('#playSiren')[0].play();
				//playStopTrack("#jPlayerSiren",'https://drive.google.com/uc?export=download&id=0B_jU3ZFb1zpHQktFWHR3OVcxWjA','play');

				var msg = {
						type: "attendeeCount",
						text: result.attendeeCount

				};

				websocket.send(JSON.stringify(msg));



			},
			error: function(result){

				window.location.href = "error.jsp";
			}

		});

	});

	//On Message event handler method for websocket	
	function onMessage(event) {

		var msg = JSON.parse(event.data);

		switch(msg.type) {

		case 'chat':
			displayChatMessage(event);
			break;

		case 'attendeeCount':
			displayAttendeeCount(event);
			break;
			
		case 'kudosCount':
			displayKudosCount(event);
			break;
			
		case 'fanCount':
			displayFanCount(event);
			break;

		}
	}


	function displayAttendeeCount(event){
		var serverMessage =JSON.parse(event.data);
		$('#attendeeCount').html(' '+serverMessage.text);
	}

	$('#btnStartEvent').attr('disabled','disabled'); //Button will be disabled and when counter goes to 0 it will become active

	//Start live event and set status as 'Y' in Database
	$('#btnStartEvent').on('click',function(e){
		e.preventDefault();

		$.ajax({

			type: 'POST',
			url: '/mixtri/rest/event/updateEventStatus',
			contentType: "application/x-www-form-urlencoded",
			data: {
				eventId: eventId,
				isLive: 'Y'
			},

			success: function(result){
				
				transcodeToMp3();
				$('#countdown').show(1000);
				countdown.start();

				$('#playSiren')[0].play();
				//playStopTrack("#jPlayerSiren",'https://drive.google.com/uc?export=download&id=0B_jU3ZFb1zpHQktFWHR3OVcxWjA','play');
				

			},
			error: function(result){

				window.location.href = "error.jsp";
			}

		});

	});

	function transcodeToMp3(){
		
		$.ajax({

			type: 'GET',
			url: 'http://ec2-52-77-202-27.ap-southeast-1.compute.amazonaws.com:8080/mediatranscoder/rest/transcode',
			/*url:'http://localhost:8000/mediatranscoder/rest/transcode',*/
			dataType: 'json',
			data: {
				streamId: eventId,
			},

			success: function(result){
				
				$('#streamAudioPlayer').play();
			},
			error: function(result){
				
				/*This REST Service is returning 200 OK code but error text. Not sure why. So its a hack to play the stream in the error function*/
				$('#streamAudioPlayer').play();
				
				console.log("Cannot Transcode to .mp3: "+liveStreamURL);
				console.log('Errors: '+result);
				
			}

		});
		
	}
	
	function textBlink(options,obj) {

		var options = options;

		setInterval(function() {
			if ($(obj).css("visibility") == "visible") {
				$(obj).css('visibility', 'hidden');
			}
			else {
				$(obj).css('visibility', 'visible');
			}
		}, options);

	}

	//Play Siren Sound on countdown


	function playStopTrack(id,liveStreamURL,action){
		console.log('Testing url'+liveStreamURL);
		$(id).jPlayer({
			ready: function() {
				$(this).jPlayer("setMedia", {
					mp3: liveStreamURL
				}).jPlayer(action);
				var click = document.ontouchstart === undefined ? 'click' : 'touchstart';
				var kickoff = function () {
					$(id).jPlayer(action);

				};

			},
			swfPath: "assets/jPlayer",
			loop: false
		});

	}


	

	function startTimer(duration, display) {
		var timer = duration, minutes, seconds;
		setInterval(function () {
			minutes = parseInt(timer / 60, 10)
			seconds = parseInt(timer % 60, 10);

			minutes = minutes < 10 ? "0" + minutes : minutes;
			seconds = seconds < 10 ? "0" + seconds : seconds;

			display.text(minutes + ":" + seconds);
			timer--;
			if (--timer < 0) {
				timer = 0;
			}

			if(timer == '0'){
				$('#timeLeft').html('Ready To Start!')
				$('#btnStartEvent').removeAttr('disabled');
				$('#btnJoinEvent').removeAttr('disabled');

			}

		}, 1000);
	}


	if(isDj){
		$('.kudo-fan-attendee-count').hide();
	}

	//Likes and Become a fan.
	$('#kudos').on('click',function(){
		var kudos = $('#kudos');

		if(!kudos.hasClass('fanKudos')){
			$('#kudos').addClass('fanKudos');
			
			$.ajax({

				type: 'POST',
				url: '/mixtri/rest/event/liveDataUpdate',
				contentType: "application/x-www-form-urlencoded",
				data: {
					eventId: eventId,
					type: 'kudosCount'
				},

				success: function(result){

					var msg = {
							type: "kudosCount",
							text: result.kudosCount

					};

					websocket.send(JSON.stringify(msg));



				},
				error: function(result){

					window.location.href = "error.jsp";
				}

			});
			
			

			//Wipe off the kudos after 10 sec so that user can give him a kudos again.		  
			setTimeout(function(){
				$("#kudos").removeClass('fanKudos');

			}, 10000);

		}
	});
	
	/*Display Kudos Count to all the clients connected in an event*/
	function displayKudosCount(event){

		var serverMessage =JSON.parse(event.data);
		$('#kudosCount').html(' '+serverMessage.text);

	}

	$('#fan').on('click',function(){
		//If the user is a guest he cannot become a fan of a Dj. Show him the login box
		if(typeof $.cookie('emailId') == 'undefined'){
			$('#loginbox').modal('show');
		}else{
			//If a user is not a fan then let him become Dj's fan
			var fan = $('#fan');
			if(!fan.hasClass('fanKudos')){
			
				$.ajax({

					type: 'POST',
					url: '/mixtri/rest/event/liveDataUpdate',
					contentType: "application/x-www-form-urlencoded",
					data: {
						profileURLId: profileURLId,
						fanEmailId: $.cookie('emailId'),
						type: 'addFan'
					},

					success: function(result){

						var msg = {
								type: "fanCount",
								text: result.fanCount

						};
                        
						$('#fan').addClass('fanKudos');
						$('#wordFanUnfan').html('Unfan');
						websocket.send(JSON.stringify(msg));



					},
					error: function(result){

						window.location.href = "error.jsp";
					}

				});
				
			}else{
				
				$.ajax({

					type: 'POST',
					url: '/mixtri/rest/event/liveDataUpdate',
					contentType: "application/x-www-form-urlencoded",
					data: {
						profileURLId: profileURLId,
						fanEmailId: $.cookie('emailId'),
						type: 'removeFan'
					},

					success: function(result){

						var msg = {
								type: "fanCount",
								text: result.fanCount

						};
                        
						$('#fan').removeClass('fanKudos');
						$('#wordFanUnfan').html('Fan');
						websocket.send(JSON.stringify(msg));



					},
					error: function(result){

						window.location.href = "error.jsp";
					}

				});
				
			}

		}


	});

   function displayFanCount(event){
	   
	   var serverMessage =JSON.parse(event.data);
		$('#fanCount').html(' '+serverMessage.text);
	   
   }
	
	//End live event and set status as 'N' in Database
	$('#btnYes').on('click',function(){

		$.ajax({

			type: 'POST',
			url: '/mixtri/rest/event/updateEventStatus',
			contentType: "application/x-www-form-urlencoded",
			data: {
				eventId: eventId,
				isLive: 'N'
			},

			success: function(result){

				//Remove isDj=true from cookie
				$.removeCookie('isDj', { path: '/' });
				$.removeCookie('eventId', { path: '/' });

				//stop the track currently being played
				//playStopTrack('#jPlayerLiveTrack',liveStreamURL,'pause');
				
				$('#streamAudioPlayer')[0].stop();

				$("#bgVideoTheme video")[0].pause();
				$('#feedbackModal').modal('show');

			},
			error: function(result){

				window.location.href = "error.jsp";
			}

		});


	});

	//Submit Feedback

	$('#btnSubmitFeedback').on('click',function(){

		var feedback = $('#feedbackText').val();
		if(!$.isEmptyObject(feedback)){

			$.ajax({

				type: 'POST',
				url: '/mixtri/rest/event/feedback',
				contentType: "application/x-www-form-urlencoded",
				data: {
					feedback: feedback,
					eventId: eventId

				},

				success: function(result){
					$('#divFeedback').hide();
					$('#btnsFeedback').hide();
					$('#btnReturnHome').show();
					$('#btnReturnHome').removeClass('hidden');
					$('#feedbackSubmitted').html('Thank you for your extremely valuable feedback. This is turly helpful!');
				},
				error: function(result){

					window.location.href = "error.jsp";
				}

			});

		}else{
			$('#validationError').html('Please enter something to submit the feedback!');
			$('#validationError').show();
			$('#validationError').delay(5000).fadeOut();
		}	




	});


	/**
			Count the number of characters keyed in by the user in feedback field
	 **/
	$("#feedbackText").keyup(function(){
		el = $(this);
		if(el.val().length >= 500){
			el.val( el.val().substr(0, 500) );
		} else {
			var chars = 500-el.val().length;
			$("#maxChars").html('Chars left: '+chars);

		}
	});

	$('#btnReturnHome').on('click',function(){
		window.location.href = 'index.jsp';
	});

	$('#btnCancelFeedback').on('click',function(){
		window.location.href = 'index.jsp';
	});


	//If user is not logged in and he tries to become a fan or tries to start a chat then show him a login page.

	$('#msgText').focus(function(){

		if(typeof $.cookie('emailId') === 'undefined'){

			$('#loginbox').modal('show');
		}

	});

	/*************Chat functionality and other live updates websocketing**************/
	var username;
	var chatDiv='<div class="bubble-container"><span class="bubble"><div class="bubble-text"><p>';
	
	
    $('#chatForm').on('submit',function(e){

        e.preventDefault();
		var loggedInUser = $.cookie('displayName');

		if($.isEmptyObject($('#msgText').val())){
			return false;	
		}else{
			var isDj = $.cookie('isDj');

			if(isDj=='true'){
				chatDiv = '<div class="bubble-container"><span class="bubble"><div class="bubble-text dj-chat"><p><i class="fa fa-headphones"></i> '
					$('.bubble-container').removeProp('background-color');	   
			}else{				
				chatDiv = '<div class="bubble-container"><span class="bubble"><div class="bubble-text"><p>';
			}

			// Construct a msg object containing the data the server needs to process the message from the chat client.
			var msg = {
					type: "chat",
					text: loggedInUser + ": " + $('#msgText').val(),
					chatDiv:chatDiv

			};

			websocket.send(JSON.stringify(msg));
		}
    });
	/*function onOpen() {
    		console.log('Connected: ');
			}*/

	function displayChatMessage(evt){

		var msgText = JSON.parse(evt.data);
		var bubble = $(msgText.chatDiv+ msgText.text + '</p></div></span></div>');
		var bubbles = 1;
		var maxBubbles = 8;
		$("#msgText").val("");

		$(".bubble-container:last")
		.after(bubble);

		if (bubbles >= maxBubbles) {
			var first = $(".bubble-container:first")
			.remove();
			bubbles--;
		}

		bubbles++;
		$('.bubble-container').show(250, function showNext() {
			if (!($(this).is(":visible"))) {
				bubbles++;
			}

			$(this).next(".bubble-container")
			.show(250, showNext);

			$("#wrapper").scrollTop(9999999);
		});


	}

	/****************************Chat functionality and other live updates functionality ends***************/
	//if user navigates from the page remove the isDj Cookie, delete the user from the attendee count
	$(window).bind("beforeunload", function() { 

		//$.removeCookie('isDj', { path: '/' });

		$.ajax({

			type: 'POST',
			url: '/mixtri/rest/event/liveDataUpdate',
			contentType: "application/x-www-form-urlencoded",
			data: {
				eventId: eventId,
				attendeeId: $.cookie('attendeeId'),
				type: 'leaveEvent'

			},

			success: function(result){

				$.removeCookie('attendeeId', { path: '/' });
				
				var msg = {
						type: "attendeeCount",
						text: result.attendeeCount

				};
                websocket.send(JSON.stringify(msg));

			},
			error: function(result){
				window.location.href = "error.jsp";
			},

		});
	})

});