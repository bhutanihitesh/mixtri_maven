$(document).ready(function() {
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
				window.location.href  = "index.jsp";
				
			},

			error: function(result){
			
				window.location.href = "error.jsp";
			}
		});
		
	});

});