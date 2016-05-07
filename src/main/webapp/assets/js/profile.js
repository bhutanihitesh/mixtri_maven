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
				profilePicPath = 'assets/img/basic/logo1.jpg'
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
			
		
		var firstName = $('#profile-firstName').val();
		var lastName = $('#profile-lastName').val();
		var displayName = $('#profile-displayName').val();
		var location = $('#profile-f_elem_city').val();
		var phoneNumber = $('#profile-contact').val();
		var biography = $('#profile-biography').val();
		var profilePic = $('#image-upload').get(0).files[0];
		var emailId = $.cookie('emailId');
		var currentProfilePicPath;
		
		//This condition is to make sure we are using the current profile pic path if the user has not uploaded the pic and only updated the data.
		if(profilePic == undefined){
			
			currentProfilePicPath = $('#profile-pic')[0].src;
		}
		
		var city;
		var state;
		var country;
		
		var arrLocation = location.split(',');
		
		city = arrLocation[0];
		state = arrLocation[1];
		country = arrLocation[2];
		
		
		var data = new FormData();
		data.append('emailId',emailId);
		data.append('firstName',firstName);
		data.append('lastName',lastName);
		data.append('displayName',displayName);
		data.append('city',city);
		data.append('state',state);
		data.append('country',country);
		data.append('phoneNumber',phoneNumber);
		data.append('biography',biography);
		data.append('profilePic',profilePic);
		data.append('currentProfilePicPath',currentProfilePicPath);
		
		
		
		if(profilePic != undefined && !(profilePic.type =='image/png' || profilePic.type =='image/jpeg' || profilePic.type =='image/gif' || profilePic.type =='image/jpg')){
			$('#error').html('Only images are allowed to be uploaded.');
			$('#error').show();
			$('#error').delay(4000).fadeOut();
			return false;
			
		}
		
		$.ajax({

			type: 'POST',
			url: '/mixtri/rest/profile/updateProfileInfo',
			contentType: false,
			processData: false,
			data: data,

			success: function(result){
				$('#modalProfileUpdateSuccess').modal('show');
			},
			
			error: function(result){
		     window.location.href = "error.jsp";	
			}
		});	      
		
		
	});
	
	
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