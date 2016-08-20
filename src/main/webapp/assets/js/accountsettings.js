$(document).ready(function() {
	
	var EMAIL_ID = $.cookie('emailId');
	
	$('#error').hide();
	$('#changePasswordForm').on('submit',function(e){
		e.preventDefault();
		var changePassword = $('#changePassword').val();
		var re_enterPassword = $('#re-enterpassword').val();

		if(changePassword!=re_enterPassword){
			$('#error').html('Passwords donnot match');
			$('#error').show();
			$('#error').delay(4000).fadeOut();
			return false;
		}


		$.ajax({

			type: 'POST',
			url: '/mixtri/rest/profile/changePassword',
			contentType: "application/x-www-form-urlencoded",
			data: {
				emailId : $.cookie('emailId'),
				changePassword: changePassword,
				re_enterPassword: re_enterPassword
			},

			success: function(result){

				$('#modalPasswordChangeSuccess').modal('show');
			},

			error: function(result){
				if(result.status == 400){

					$('#error').html(result.responseText);
					$('#error').show();
					$('#error').delay(4000).fadeOut();
					return false; 
				}else{	
					window.location.href = "error.jsp";
				}
			}
		});

	});

	$('#btnReturnHome').on('click',function(){
		window.location.href = 'index.jsp';
	});
	
	$('#btnDeleteAccount').on('click',function(){
		$('#modalDeleteAccount').modal('show');
	});
	
	$('#btnDeleteAccountYes').on('click',function(){
		
		
		$.ajax({

			type: 'POST',
			url: '/mixtri/rest/profile/deleteAccount',
			contentType: "application/x-www-form-urlencoded",
			data: {
				emailId : $.cookie('emailId'),
			},

			success: function(result){
				
				var cookies = $.cookie();
				//Remove all the set cookies
				for(var cookie in cookies) {
					
				   $.removeCookie(cookie, { path: '/' });
				}
				
				$("#welcomeUser").addClass('hidden');
				$('#loginUser').show();
				
				getAccessToken();
				
			},

			error: function(result){
			
				window.location.href = "error.jsp";
			}
		});
		
	});
	
	
	
	function getAccessToken() {

		$.ajax({

			type: 'GET',
			url: '/mixtri/rest/profile/getToken',
			success: function(result){

				//Setting access token in global variable.
				ACCESS_TOKEN = result.accessToken;
				setAccessToken();
				
			},

			error: function(result){

				console.log('Error recieving access token '+result);
				window.location.href = "error.jsp";	
			}

		});


	}

	
	/** 
	 * Set access token retrieved on server side. This replaces client side explicit
	 * authentication through authentication dialog.
	 */
	function setAccessToken() { 
	
	 gapi.auth.setToken({
		    access_token: ACCESS_TOKEN
		});	
	 
		gapi.client.load('drive', 'v2', checkIfFolderExists);
	}
	
	 
	
	function checkIfFolderExists() {
		
		var request = gapi.client.drive.files.list({

			'q':"mimeType = 'application/vnd.google-apps.folder' and title='"+EMAIL_ID+"'"


		});

		request.execute(function(resp) {

			//This means the folder exists
			if(resp.items.length>0){

				//Get the id and upload file in that
				var folderId = resp.items[0].id;
				deleteFromGoogleDrive(folderId);

			}else{
				
				window.location.href = 'index.jsp';
			}


		});
	}
	
	
	/**
	 * Permanently delete a file, skipping the trash.
	 *
	 * @param {String} fileId ID of the file to delete.
	 */
	
	function deleteFromGoogleDrive(fileId) {
		
		var request = gapi.client.drive.files.delete({
			'fileId': fileId
		});

		request.execute(function(resp) {

			if(resp.code!=404){

				console.log('Delete SuccessFul'); 

			}else{

				console.log('Not Successful: '+resp.message);
			}
			
			window.location.href = 'index.jsp';

		});
	}
	

});