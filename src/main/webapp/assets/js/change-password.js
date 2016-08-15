$(document).ready(function() {


	$('#changePasswordForm').on('submit',function(e){

		e.preventDefault();
		validatePassword();

		submitChangePasswordForm();
	});

	
	function validatePassword(){

		var newPassword = document.getElementById("newPassword").value;
		var confirm_password = document.getElementById("confirmPassword").value;

		if(newPassword != confirm_password) {
			$('#error').html("Passwords don't match");
			$('#error').show();
			$('#error').delay(3000).fadeOut();
			return false;
		}else{
			
			submitChangePasswordForm();
		}

	}
	
	function submitChangePasswordForm(){
		
		$('#error').html('');
		
		var emailToken=getQueryVariable('emailToken');
		var emailId=getQueryVariable('emailId');
		var newPassword=$('#newPassword').val();
		var confirmPassword=$('#confirmPassword').val();
		
		$.ajax({

			type: 'POST',
			url: '/mixtri/rest/changePassword',
			
			data: {
				emailId: emailId,
				emailToken: emailToken,
				newPassword: newPassword,
				confirmPassword:confirmPassword
				
			},

			success: function(result){
				
				var successMsg = result.success;
				
				$('#successPasswordUpdated').html(successMsg);
				$('#changePasswordForm')[0].reset();
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
	}

	
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




});