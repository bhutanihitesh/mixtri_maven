//Gloabal Variables

$(document).ready(function() {

	//Go Live and Test Stream buttons

	$('#testStream').click(function(e){
		e.preventDefault();
		$('#goLive').removeClass('btn streamActive');
		$('#goLive').addClass('btn btn-default');
		$('#testStream').removeClass('btn btn-default');
		$('#testStream').addClass('btn streamActive');
	});


	$('#goLive').click(function(e){
		e.preventDefault();
		$('#goLive').removeClass('btn btn-default');
		$('#goLive').addClass('btn streamActive');
		$('#testStream').removeClass('btn streamActive');
		$('#testStream').addClass('btn btn-default');
	});


	//Upload Image on button click
	$('#btnUploadImage').click(function(e) {
		e.preventDefault();

		//clicks file upload input on click of the button.
		$("#image-upload").click();

		$("#image-upload").on('change', function() {
			readURL(this);
		});

		//readURL Function called

		var readURL = function(input) {
			if (input.files && input.files[0]) {
				var reader = new FileReader();

				reader.onload = function(e) {
					$('#profile-pic').attr('width','194px');
					$('#profile-pic').attr('height','104px');
					$('#profile-pic').attr('src',e.target.result);
				}

				reader.readAsDataURL(input.files[0]);

			}
		}

	});


	/**
			  This function show the name of the file selected by the user on the UI
	 **/
	$("#mixUpload").on('change',function(e){
		e.preventDefault();
		//var file = $('input[name="uploadFile"').get(0).files[0];
		var file = $('input[name="mixUpload"').get(0).files[0];
		//var file = $('#mixUpload').get(0).files[0];
		$('#selectedFileName').html('Selected File: '+file.name);

	});


	//Select Streaming option panels as buttons

	$('.pointer-cursor').on('click',function(e){

		var alreadySelectedPanel = $('.selected');
		$(alreadySelectedPanel).removeClass('panel panel-primary pointer-cursor selected');
		$(alreadySelectedPanel).addClass('panel panel-info pointer-cursor');

		//this is the current object user clicked on
		$(this).removeClass('panel panel-info pointer-cursor');
		$(this).addClass('panel panel-primary pointer-cursor selected');

		if(this.id=='panel-recorded-mixes'){
			$('#uploadedMixes').slideDown('slow');
		}else{

			$('#uploadedMixes').slideUp('slow');
		}

	});



	//This codes selects the newly clicked div when user clicks on a new track and unselects any previously selected div.
	//Delegate event to the parent on dynamically generated divs.
	$('#uploaded-past-mixes').on('click','.audioControls',function(){
		var alreadySelectedMix = $('.pastMix');

		if(alreadySelectedMix.length>0){
			alreadySelectedMix.removeClass('pastMix selected');
			alreadySelectedMix.addClass('audioControls');
		}

		$(this).removeClass('audioControls');
		$(this).addClass('pastMix selected');

		var selectedMixName = $(this).context.innerText;

		$('#selectedMixName').html("Selected Mix: "+selectedMixName);

	});


	//This function starts all the selected video by 1 sec ahead and not from 0 secs
	document.getElementById('vidFrame-6').addEventListener('loadedmetadata', function() {
		this.currentTime = 6;
	}, false);

	//Background Theme Videos
	$('.videoThemes').on('click',function(e){

		var alreadySelectedMix = $('.selectedVid');

		if(alreadySelectedMix.length>0){
			alreadySelectedMix.removeClass('selectedVid selected');
			alreadySelectedMix.addClass('videoThemes');
			//Stops the already selected video

			var divId = alreadySelectedMix.get(0).id;
			var strArr = [];
			strArr = divId.split("-"); //get id id of the selected div.
			var vidFrame = "#vidFrame-"+strArr[1];
			$(vidFrame).get(0).pause(); //get the corresponding video id and pause it if the other is selected by the user.
		}
		var vidThemeId = this.id;
		$(this).removeClass('videoThemes');
		$(this).addClass('selectedVid selected');

		//Substringing the number from the selected theme id and then displaying the corresponding Video
		var strArr = [];
		strArr = vidThemeId.split("-");
		//Add the hidden class to the already selected video
		$('.bgThemeVideo').addClass('hidden');
		$('#bgTheme-'+strArr[1]).removeClass('hidden');
		$('#selectedTheme').html("Selected Theme: "+$(this).context.innerText);


	});


	$('#timeZone').on('change',function(){
		getServerDateTime();
	});


	/**
		  		This method is called on document.ready to get server date and time and add current time zone seleted time + 10 extra mins
	 **/

	function getServerDateTime(){

		var selectedTimeZone = $("#timeZone option:selected").val();

		$.ajax({

			type: 'GET',
			url: '/mixtri/rest/servertime',
			dataType: 'json',
			data: {
				selectedTimeZone: selectedTimeZone
			},

			success: function(result){
				var dateTime = result;
				var arrDateTime = dateTime.advancedTime.split("|");
				var arrServerTime = arrDateTime[1].split(":");

				$('#eventTimePicker').val(arrDateTime[1]);
				$("#eventDatePicker" ).datepicker({ minDate: -1}); //This disables the past dates in calendar
				$("#eventDatePicker").datepicker('setDate',arrDateTime[0]);

			},

			error: function(result){

			}
		})	
	}


	//Live Stream Setup Form Validations:
	//Image Validation

	$('#btnLiveStream').on('click',function(e){

		e.preventDefault();

		var profileImage = $('#profile-pic');
		var formSubmit = 'true';

		if($('#streamInfo').val()==""){

			$('#messages').html('ERROR: Please enter live stream name');
			$('#messages').show();
			$('#messages').delay(5000).fadeOut();
			return false;
		}

		if($('#profile-pic').attr('src')==null){
			$('#messages').html('ERROR: Please upload a catchy image for your event for your fans to see. This will attract you fans to your event!');
			$('#messages').show();
			$('#messages').delay(10000).fadeOut();
			return false;

		}

		if($('#eventDatePicker').val()==""){

			$('#messages').html('ERROR: Please enter a date for your event');
			$('#messages').show();
			$('#messages').delay(5000).fadeOut();
			return false;
		}


		if($('#eventTimePicker').val()==""){

			$('#messages').html('ERROR: Please enter time for your event');
			$('#messages').show();
			$('#messages').delay(5000).fadeOut();
			return false;
		}

		//On Submit of the form we need to fetch the latest time of the server use it for 5 mins validation
		var selectedTimeZone = $("#timeZone option:selected").val();
		var streamInfo = $('#streamInfo').val();
		$.ajax({

			type: 'GET',
			url: '/mixtri/rest/servertime',
			dataType: 'json',
			data: {
				selectedTimeZone: selectedTimeZone,
				streamInfo: streamInfo,
				profileURLId: $.cookie('profileURLId')

			},

			success: function(result){

				var dateTime = result;
				var arrDateTime = dateTime.serverTime.split("|");
				var streamingOption = $('.panel.panel-primary.pointer-cursor.selected').get(0).id;

				var currentSystemTime = getTimeInMins(arrDateTime[1]); //Sends server time to this function in the fomrat HH:MM and gets back the mins 
				var eventSetupTime = getTimeInMins($('#eventTimePicker').val());
				var isPastDate = compareWithSystemDate(arrDateTime[0]);
				var eventDate = $("#eventDatePicker").val();
				var serverDate = arrDateTime[0];
				var d1 = Date.parse(eventDate);
				var d2 = Date.parse(serverDate);

				//This condition checks If the Event Setup time is atleast 5 mins more than the system on today's date. If it is future date then it won't check this condition. 
				if(isPastDate){

					$('#messages').html("ERROR: Please select today's date or a future date for your event!");
					$('#messages').show();
					$('#messages').delay(6000).fadeOut();
					formSubmit = false;
					return false;

				}else if(d1==d2){

					if((eventSetupTime - currentSystemTime)<5 ){

						$('#messages').html('ERROR: Please set your event time atleast 5 mins in advance. This gives time for you to prepare!');
						$('#messages').show();
						$('#messages').delay(10000).fadeOut();
						formSubmit = false;
						return false;
					} 

				}

				//This error is returned from the server and checks if the user has already used the same live stream setup with the same name or not 
				if(result.error!=null){
					$('#messages').html(result.error);
					$('#messages').show();
					$('#messages').delay(10000).fadeOut();
					formSubmit = false;
					return false;
				}


				//This condition checks if the user has choosen an option of streaming through past recorded mixes and if he has not selected any previous mix
				//Then throw him a validation error.
				if(streamingOption=='panel-recorded-mixes'){
					var alreadySelectedMix = $('.pastMix');

					if(alreadySelectedMix.length == 0){
						$('#messages').html('Please select either from your previous recorded mixes or upload a new mix!');
						$('#messages').show();
						$('#messages').delay(6000).fadeOut();
						formSubmit = false;
						return false;
					}

				}

			},

			complete: function(){

				//We are manually submitting the form because we want to see if the value returned from server is correct and 5 mins validation satisfies or not.
				if(formSubmit == 'true'){
					submitEventForm();
					$('#btnLiveStream').attr('disabled','disabled');

				}

			},
			error: function(result){
				window.location.href = "error.jsp";
			} 



		})



		function submitEventForm(){

			var selectedVid = $('.selectedVid').get(0).id;

			var strArr = [];
			strArr = selectedVid.split("-"); //get id id of the selected div.
			var vidFrame = "#vidFrame-"+strArr[1];

			var emailId = $.cookie('emailId');
			var displayName = $.cookie('displayName');
			var streamInfo = $('#streamInfo').val();
			var eventDate = $("#eventDatePicker" ).val();
			var eventTime = $('#eventTimePicker').val();
			var selectedTimeZone = $("#timeZone option:selected").val();
			var eventDescription = $('#eventDescription').val();
			var genre = $('#genre').val();
			var hastags = $('#hashtags').val();
			var streamingOption = $('.panel.panel-primary.pointer-cursor.selected').get(0).id;
			var bgVideoTheme = $(vidFrame).get(0).currentSrc;
			var eventPic = $('#image-upload').get(0).files[0];
			var profileURLId = $.cookie('profileURLId');



			var data = new FormData();
			data.append('emailId', emailId);
			data.append('displayName', displayName);
			data.append('streamInfo', streamInfo);
			data.append('eventDate', eventDate);
			data.append('eventTime', eventTime);
			data.append('selectedTimeZone', selectedTimeZone);
			data.append('eventDescription', eventDescription);
			data.append('genre', genre);
			data.append('hastags', hastags);
			data.append('streamingOption', streamingOption);
			data.append('eventPic', eventPic);
			data.append('bgVideoTheme',bgVideoTheme);
			data.append('profileURLId',profileURLId);

			var url = "/mixtri/rest/event";

			$.ajax({
				url: url,
				type: 'POST',
				contentType: false,
				processData: false,
				data: data,

				success: function (result) {

					$.cookie('eventId', result.id,{ path: '/'});
					var profileURLId = $.cookie('profileURLId');
					window.location.href = 'event.jsp?profileURLId='+profileURLId+'&eventId='+result.id;

				},
				error: function(result){

					window.location.href = "error.jsp";

				}

			});

		}


		/***
	    	 		This function checks if the user has setup the event on the same day or not then only it checks for 5 mins validation.
	    	 		Else, it could be possible that user has setup on the next day and but time is less than the system time. So in that case we
	    	 		need take system date in accountibilty and then do the validation.

		 ***/	
		function compareWithSystemDate(serverDate){

			var eventDate = $("#eventDatePicker").val();

			var d1 = Date.parse(eventDate);
			var d2 = Date.parse(serverDate);
			if (d1 < d2) {
				return true;
			}else{
				return false;
			}

		}

		function getTimeInMins(time){
			var a = time.split(':'); // split it at the colons
			//converting the current time to minutes
			var timeInMinutes = (+a[0]) * 60  + (+a[1]); 
			return timeInMinutes;
		}

	}); /*End Event form submit Validations on Setup Live Stream button click*/


	//Upload Recorded Set Valdiation and Save Set
	$('#btnSaveSet').on('click',function(e){
		e.preventDefault();
		//Clear any validation errors if any before showing new ones after user has corrected the info.
		$('#saveSetErrors').empty();

		if($('#mix-title').val()==""){
			$('#saveSetSuccess').empty();
			$('#saveSetErrors').html('ERROR: Please give a title for your mix!');
			$('#saveSetErrors').show();
			$('#saveSetErrors').delay(5000).fadeOut();
			return false;

		}

		if($('#selectedFileName').is(':empty')){
			$('#saveSetSuccess').empty();
			$('#saveSetErrors').html('ERROR: Please choose a file to be uploaded!');
			$('#saveSetErrors').show();
			$('#saveSetErrors').delay(5000).fadeOut();
			return false;

		}
		
		var file = $('#mixUpload').get(0).files[0];
		var mixTitle = $('#mix-title').val();

		if(file.size>140000000){

			$('#maxFileSizeError').html('Please upload file less than 140 MB.');
			$('#maxFileSizeError').show();
			$('#maxFileSizeError').delay(4000).fadeOut();
			return false;

		}

		if(file.type=='audio/mp3' || file.type=='audio/mpeg'){

		}else{

			$('#invalid-mp3-file').html('Please upload only mp3 files.');
			$('#invalid-mp3-file').show();
			$('#invalid-mp3-file').delay(4000).fadeOut();

			return false;

		}

		var folderName =  $.cookie("emailId");
		checkIfFolderExists(folderName);
	});

	//0B_jU3ZFb1zpHQmFIMDNzc2dBRHM This is the id of the parent folder(upload) in google drive in which all the sub folders will be created.
	
	function createFolder(folderName) {

		var request = gapi.client.request({
			'path': '/drive/v2/files/',
			'method': 'POST',
			'headers': {
				'Content-Type': 'application/json',
			},
			'body':{
				"title" : folderName,
				"mimeType" : "application/vnd.google-apps.folder",
				'parents': [{
					"kind": "drive#fileLink",
					"id": "0B_jU3ZFb1zpHQmFIMDNzc2dBRHM"
					
				}]
			}
		});

		request.execute(function(resp) { 
			
			//Upload File to Google Drive after creating the folder
			var folderId = resp.id;
			uploadToGoogleDrive(folderId);
		});
	}

	function generateUUID() {
		var d = new Date().getTime();
		var uuid = 'xxxxxxxxxxxx4xxxyxxxxxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
			var r = (d + Math.random()*16)%16 | 0;
			d = Math.floor(d/16);
			return (c=='x' ? r : (r&0x3|0x8)).toString(16);
		});
		return uuid;
	};
	

	function checkIfFolderExists(folderName) {
		
        var request = gapi.client.drive.files.list({
        	
        	'q':"mimeType = 'application/vnd.google-apps.folder' and title='"+folderName+"'"
        	
        	
          });
        
        request.execute(function(resp) {
        	
        	//This means the folder exists
        	if(resp.items.length>0){
        		
        		//Get the id and upload file in that
        		var folderId = resp.items[0].id;
        		uploadToGoogleDrive(folderId);
        		
        	}else{
        		
        		createFolder(folderName);
        	}
        	

        });
	}
	
	
	function uploadToGoogleDrive(folderId) {

		
			var fileData = $('#mixUpload').get(0).files[0];
			
			$.blockUI({ message: '<h5><img src="assets/img/icons/busy.gif" /> Do a little dance your music is being uploaded...</h5>' });

			const boundary = '-------314159265358979323846';
			const delimiter = "\r\n--" + boundary + "\r\n";
			const close_delim = "\r\n--" + boundary + "--";

			var reader = new FileReader();
			reader.readAsBinaryString(fileData);
			reader.onload = function(e) {
				var contentType = fileData.type || 'application/octet-stream';
				var metadata = {
						'title': fileData.name,
						'mimeType': contentType,
						'parents': [{
							"kind": "drive#fileLink",
							"id": folderId
							
						}]
				};

				var base64Data = btoa(reader.result);
				var multipartRequestBody =
					delimiter +
					'Content-Type: application/json\r\n\r\n' +
					JSON.stringify(metadata) +
					delimiter +
					'Content-Type: ' + contentType + '\r\n' +
					'Content-Transfer-Encoding: base64\r\n' +
					'\r\n' +
					base64Data +
					close_delim;

				var request = gapi.client.request({
					'path': '/upload/drive/v2/files',
					'method': 'POST',
					'params': {'uploadType': 'multipart'},
					'headers': {
						'Content-Type': 'multipart/mixed; boundary="' + boundary + '"'
					},
					'body': multipartRequestBody});

				if (!callbackAfterUpload) {
					callbackAfterUpload = function() {
					};
				}

				//Should be returned by the request
				
				request.execute(function(jsonResp,rawResp){
					
					callbackAfterUpload(jsonResp,rawResp);
					
				});
			}
	}


	function callbackAfterUpload(jsonResp,rawResp){

		$.unblockUI();
		
		var status = ($.parseJSON(rawResp)).gapiRequest.data.status;
		
		//This means that the file has been uploaded on the google drive
		if(status===200){
			//Generates a random track id
			var uuid = generateUUID();

			//On Successfully saving the song to google drive save the uploaded song's details on the data with an Ajax Call
			
			var mixTitle = $('#mix-title').val();
			var uploadedFileId = jsonResp.id;
			var uploadPath = 'https://googledrive.com/host/'+jsonResp.id
			var fileName = jsonResp.originalFilename;
			var fileSize = jsonResp.fileSize;
			
			saveSongDetails(uuid,mixTitle,uploadPath,fileName,fileSize);

		}else{

			window.location.href = "error.jsp";
		}	
	}


	function saveSongDetails(uuid,mixTitle,uploadPath,fileName,fileSize){
		
		$.ajax({
			url: '/mixtri/rest/saveSongDetails',
			type: 'POST',
			data: {
				uuid: uuid,
				emailId: $.cookie("emailId"),
				mixTitle: mixTitle,
				uploadPath: uploadPath,
				fileName:fileName,
				fileSize:fileSize

			},
			dataType: 'json',

			success: function (data) {
				
				//These are the global variables containing id/path for the recently uploaded track.
				var uploadedTrackPath = data.path;
				
				var alreadySelectedMix = $('.pastMix');
				var mixTitle = $('#mix-title').val();

				if(alreadySelectedMix.length>0){
					alreadySelectedMix.removeClass('pastMix selected');
					alreadySelectedMix.addClass('audioControls');
				}

				pastUploadedTracks(uuid,mixTitle,uploadedTrackPath,"pastMix selected","Selected Mix: "+mixTitle);

				//On Success Increase the disk-space bar size
				getDiskSpace();

				//Clear the title text field and selectedFileName div after the successful upload of file to prevent user to upload same file again accidently.
				//If we clear selectedFileName div he will have to choose the file again.
				//On complete of request clear all validation errors.
				$('#mix-title').val("");
				$('#selectedFileName').empty();
				$('#saveSetSuccess').empty();
				$('#saveSetErrors').empty();
				$('#maxFileSizeError').empty();
				$('#invalid-mp3-file').html();
				

			},

			error: function(data){
				window.location.href = "error.jsp";
			},


		});
		
	}

	//On Document Load Remove any selected mixes
	//Value of argument 'selectedTitle' passed to this function will be 'Selected Mix: None' when this function gets called on load
	//But this will have a title value when the user uploads the mix
	function pastUploadedTracks(uploadedTrackId,trackName,uploadedTrackPath,divClass,selectedTitle){

		var html='';
		html+='<div id="pastMix-'+uploadedTrackId+'" class="'+divClass+'">';
		html+='<div>'+trackName+'</div>';
		html+='<audio controls preload="metadata">';
		html+='<source src="'+uploadedTrackPath+'" type="audio/mpeg">';
		html+='</audio>';
		html+='</div>';
		$('#uploaded-past-mixes').prepend(html);
		$('#selectedMixName').html(selectedTitle);
	}

	//On btnUploadMix click prevent its default behavior
	$('#btnUploadMix').on('click',function(e){
		e.preventDefault();
		$('#mixUpload').click();
	});


	/**
			This function gets called on document.ready ie on load and then fetches the diskspace for the user by checking the folder side value.
	 **/
	function getDiskSpace(){	
		$.ajax({
			url: '/mixtri/rest/diskspace',
			type: 'GET',
			data: {
				emailId: $.cookie("emailId")
			},

			success: function (data, textStatus, jqXHR) {
				$('#spanDiskSpace').html(data+" MB Left");
				var oneGB = 1024;
				var percentSpaceUsed = (oneGB - data)/1024*100;
				$('#disk-space-bar').width(percentSpaceUsed+'%');

			},

			error: function(data){
				window.location.href = "error.jsp";
			},


		});

	}

	/**
			This method gets called on document.ready ie on laod of the DOM. This function gets the saved/uploaded mixes from a given user. 
	 **/


	function getPastMixes(){

		$.ajax({
			url: '/mixtri/rest/pastmixes',
			type: 'GET',
			data: {
				emailId: $.cookie("emailId")
			},

			success: function (data, textStatus, jqXHR) {

				var listUploadedTracks = data.listUploadedTracks;
				var uploadedPath = data.audioSrc;

				if(listUploadedTracks){

					for(var iCount=0;iCount<listUploadedTracks.length;iCount++){
						pastUploadedTracks(listUploadedTracks[iCount].id,listUploadedTracks[iCount].audioTitle,listUploadedTracks[iCount].audioSrc,'audioControls','Selected Mix: None');
					}

				}

			},

			error: function(data){
				window.location.href = "error.jsp";
			},


		});

	}

	/**
			Count the number of characters keyed in by the user in description field
	 **/
	$("#eventDescription").keyup(function(){
		el = $(this);
		if(el.val().length >= 140){
			el.val( el.val().substr(0, 140) );
		} else {
			var chars = 140-el.val().length;
			$("#maxChars").html('Chars left: '+chars);

		}
	});



	/**These methods gets called on DOM Ready
	 **/
	getServerDateTime();
	getPastMixes();
	getDiskSpace();

});
