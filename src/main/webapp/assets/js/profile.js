$(document).ready(function() {

	$.ajax({

		type: 'GET',
		url: '/mixtri/rest/profile/getProfileInfo',
		dataType: 'json',
		data: {
			emailId: $.cookie('emailId')
		},

		success: function(result){

			var firstName = result.firstName;
			var lastName = result.lastName;
			var displayName = result.displayName;
			var location = result.location;
			var phoneNumber = result.phoneNumber;
			var biography = result.biography;
			var profilePicPath = result.profilePicPath;


			$('#profile-firstName').val(firstName);
			$('#profile-lastName').val(lastName);
			$('#profile-displayName').val(displayName);
			$('#profile-f_elem_city').val(location);
			$('#profile-contact').val(phoneNumber);
			$('#profile-biography').val(biography);

			if(profilePicPath == undefined){
				profilePicPath = 'assets/img/basic/logo_mixtri.png'
			}
			$('#profile-pic').attr('src',profilePicPath);
		},

		error: function(result){
			window.location.href = "error.jsp";	
		}
	});

	$('#btnUploadImage').on('click',function(e){

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
					$('#profile-pic').attr('width','120px');
					$('#profile-pic').attr('height','120px');
					$('#profile-pic').attr('src',e.target.result);
				}

				reader.readAsDataURL(input.files[0]);

			}
		}


	});


	$('#error').hide();
	$('#successProfileUpdate').hide();

	$('#editProfileForm').on('submit',function(e){

		e.preventDefault();

		if($('#profile-displayName').val()==""){

			$('#error').html('Please add a display name to your profile');
			$('#error').show();
			$('#error').delay(4000).fadeOut();
			return false;
		}

		if($('#profile-f_elem_city').val()==""){

			$('#error').html('Please enter your city by typing 3 or more letters.');
			$('#error').show();
			$('#error').delay(4000).fadeOut();
			return false;
		}

		if($('#profile-contact').val()==""){

			$('#error').html('Please update your phone number. Your fans might contact you for bookings.');
			$('#error').show();
			$('#error').delay(6000).fadeOut();
			return false;
		}


		var profilePic = $('#image-upload').get(0).files[0];

		if(profilePic != undefined && !(profilePic.type =='image/png' || profilePic.type =='image/jpeg' || profilePic.type =='image/gif' || profilePic.type =='image/jpg')){
			$('#error').html('Only images are allowed to be uploaded.');
			$('#error').show();
			$('#error').delay(4000).fadeOut();
			return false;

		}

		var folderName = $.cookie('emailId');

		checkIfFolderExists(folderName);

	});



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

	function uploadToGoogleDrive(folderId) {


		var fileData = $('#image-upload').get(0).files[0];

		if(fileData!=undefined){

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

		}else{
			
			saveProfilePicDetails(null,null);
		}

	}


	function callbackAfterUpload(jsonResp,rawResp){

		$.unblockUI();

		var status = ($.parseJSON(rawResp)).gapiRequest.data.status;

		//This means that the file has been uploaded on the google drive
		if(status===200){

			//On Successfully saving the song to google drive save the uploaded song's details on the data with an Ajax Call

			var uploadedFileId = jsonResp.id;
			var uploadPath = 'https://googledrive.com/host/'+jsonResp.id
			var fileName = jsonResp.originalFilename;
			var fileSize = jsonResp.fileSize;
			var googleFileId = jsonResp.id;

			saveProfilePicDetails(uploadPath);

		}else{

			window.location.href = "error.jsp";
		}	
	}


	function saveProfilePicDetails(uploadPath){

		var firstName = $('#profile-firstName').val();
		var lastName = $('#profile-lastName').val();
		var displayName = $('#profile-displayName').val();
		var location = $('#profile-f_elem_city').val();
		var phoneNumber = $('#profile-contact').val();
		var biography = $('#profile-biography').val();
		var profilePic = $('#image-upload').get(0).files[0];
		var emailId = $.cookie('emailId');
		

		var city;
		var state;
		var country;

		var arrLocation = location.split(',');

		city = arrLocation[0];
		state = arrLocation[1];
		country = arrLocation[2];


		$.ajax({

			type: 'POST',
			url: '/mixtri/rest/profile/updateProfileInfo',

			data: {

				emailId:emailId,
				firstName:firstName,
				lastName:lastName,
				displayName:displayName,
				city:city,
				state:state,
				country:country,
				phoneNumber:phoneNumber,
				biography:biography,
				currentProfilePicPath:uploadPath

			},

			dataType: 'json',
			success: function(result){
				$('#modalProfileUpdateSuccess').modal('show');
			},

			error: function(result){
				window.location.href = "error.jsp";	
			}
		});


	}

	$('#btnHomepage').on('click',function(){
		window.location.href = "index.jsp";
	});

	$('#btnMakeMoreChanges').on('click',function(){
		window.location.reload();
	});

	/**
	Count the number of characters keyed in by the user in description field
	 **/
	$("#profile-biography").keyup(function(){
		el = $(this);
		if(el.val().length >= 1000){
			el.val( el.val().substr(0, 1000) );
		} else {
			var chars = 1000-el.val().length;
			$("#maxChars").html('Chars left: '+chars);

		}
	});


});

//This is a rest api call for fetching cities on signup form
jQuery(function () 
		{
	jQuery("#profile-f_elem_city").autocomplete({
		source: function (request, response) {
			jQuery.getJSON(
					"http://gd.geobytes.com/AutoCompleteCity?callback=?&q="+request.term,
					function (data) {
						response(data);
					}
			);
		},
		minLength: 3,
		select: function (event, ui) {
			var selectedObj = ui.item;
			jQuery("#profile-f_elem_city").val(selectedObj.value);
			return false;
		},
		open: function () {
			jQuery(this).removeClass("ui-corner-all").addClass("ui-corner-top");
		},
		close: function () {
			jQuery(this).removeClass("ui-corner-top").addClass("ui-corner-all");
		}
	});
	jQuery("#profile-f_elem_city").autocomplete("option", "delay", 100);
		});