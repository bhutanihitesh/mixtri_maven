<!DOCTYPE html>
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js">
<!--<![endif]-->
<head>
<meta charset="utf-8">
<title>Mixtri-My Profile</title>

<!--=================================
Meta tags
=================================-->
<meta name="description" content="">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta name="viewport"
	content="minimum-scale=1.0, width=device-width, maximum-scale=1, user-scalable=no" />

<!--=================================
Style Sheets
=================================-->
<link href='http://fonts.googleapis.com/css?family=Oswald:400,700,300'
	rel='stylesheet' type='text/css'>
<link
	href='http://fonts.googleapis.com/css?family=Roboto:400,400italic,700'
	rel='stylesheet' type='text/css'>
<link rel="stylesheet" type="text/css"
	href="assets/css/bootstrap.min.css">
<link rel="stylesheet" href="assets/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="assets/css/flexslider.css">
<link rel="stylesheet" type="text/css" href="assets/css/prettyPhoto.css">
<link rel="stylesheet" type="text/css"
	href="assets/css/jquery.vegas.css">
<link rel="stylesheet" type="text/css"
	href="assets/css/jquery.mCustomScrollbar.css">
<link rel="stylesheet" href="assets/css/main.css">
<link rel="stylesheet" href="assets/css/profile.css">

<!--<link rel="stylesheet" type="text/css" href="assets/css/blue.css">
<link rel="stylesheet" type="text/css" href="assets/css/orange.css">
<link rel="stylesheet" type="text/css" href="assets/css/red.css">
<link rel="stylesheet" type="text/css" href="assets/css/green.css">
<link rel="stylesheet" type="text/css" href="assets/css/purple.css">-->


<!-- <script defer src="assets/js/ajaxify.min.js"></script> -->
<link rel="stylesheet"
	href="http://ajax.aspnetcdn.com/ajax/jquery.ui/1.10.3/themes/flick/jquery-ui.css" />
<style type="text/css">
.ui-menu .ui-menu-item a, .ui-menu .ui-menu-item a.ui-state-hover,
	.ui-menu .ui-menu-item a.ui-state-active {
	font-weight: normal;
	margin: -1px;
	text-align: left;
	font-size: 14px;
}

.ui-autocomplete-loading {
	background: white url("/images/ui-anim_basic_16x16.gif") right center
		no-repeat;
}
</style>


<script type="text/javascript">

//Your Client ID can be retrieved from your project in the Google
// Developer Console, https://console.developers.google.com
var CLIENT_ID = '676570655548-ou0hs4elvqvqh4m1gv8os4s186us4v5l.apps.googleusercontent.com';

//Changed scope from drive.metadata.readonly to drive.file
//var SCOPES = ['https://www.googleapis.com/auth/drive.metadata.readonly'];

 var SCOPES = ['https://www.googleapis.com/auth/drive.file'];

/**
 * Check if current user has authorized this application.
 */
  
function checkAuth() {
    
  gapi.auth.authorize(
    {
      'client_id': CLIENT_ID,
      'scope': SCOPES.join(' '),
      'immediate': true
    },handleAuthResult);
  
  //loadDriveApi();
 
}

 function handleAuthResult(authResult) {
    
    if (authResult && !authResult.error) {
      loadDriveApi();
    } else {
     
    	console.log('Not authorized by google server'+authResult.error);
    }
  }

/**
 * Initiate auth flow in response to user clicking authorize button.
 *
 * @param {Event} event Button click event.
 */
function handleAuthClick(event) {
  gapi.auth.authorize(
    {client_id: CLIENT_ID, scope: SCOPES, immediate: false},
    handleAuthResult);
  return false;
}

/**
 * Load Drive API client library.
 */
function loadDriveApi() {
 gapi.client.load('drive', 'v2');
}

</script>

</head>
<body>



	<div id="header">
		<%@include file="header.jsp"%>
	</div>

	<div id="modalProfileUpdateSuccess" class="modal fade" role="dialog">
			<div class="modal-dialog">

				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h3 class="modal-title">Success</h3>
					</div>
					<div class="modal-body">
					 	<div>Your profile is updated successufully</div>
					<div id="feedbackSubmitted"></div>	 
					</div>
					<div class="modal-footer">
					  <div id="btnsFeedback">
						<button id="btnHomepage" type="button" class="btn btn-default commonButton" style="border-width: thin; border-color: #e62948; margin: 0px;">Return to homepage</button>
						<button id="btnMakeMoreChanges" type="button" class="btn btn-default commonButton" style="border-width: thin; border-color: #e62948; margin: 0px;" data-dismiss="modal">Make more changes</button>
					 </div>
					</div>
					
				</div>

			</div>
		</div>


	<div class="pageContentArea">
		<div class="container">
			<div class="djSignUpMsg">
				<h2 style="text-align: center; font-size: xx-large;">Edit Profile</h2>

			</div>
	  <div style="margin-left: 300px;">
	  
	  <div class="djSignUpMsg">
				<h4 style="color:#e62948;">Personal Info</h4>

			</div>
	  
			<form id="editProfileForm" role="form" style="margin-bottom: 50px;">
				<div class="form-components">
					<div class="form-group inputBoxes">
						<label class="myProfileLabels">First Name </label> <input type="text"
							class="form-control inputLiveStreamInfo" id="profile-firstName"
							name="firstName" maxlength="20" />
					</div>
					<div class="form-group inputBoxes">
						<label class="myProfileLabels">Last Name </label> <input type="text"
							class="form-control inputLiveStreamInfo" id="profile-lastName"
							name="lastName" maxlength="20" />
					</div>
					<div class="form-group inputBoxes">
						<label class="myProfileLabels">Display Name <span style="color: #e62948">*</span></label><input type="text"
							class="form-control inputLiveStreamInfo" id="profile-displayName"
							name="displayName" maxlength="20" required />
					</div>
					<div class="form-group inputBoxes">
						<label class="myProfileLabels">City <span style="color: #e62948">*</span></label><input class="form-control ff_elem inputLiveStreamInfo"
							type="text"
							style="border-bottom-right-radius: 5px; border-top-right-radius: 5px;"
							placeholder="Your City: Type aleast first 3 letters"
							title="Please enter your city" name="ff_nm_from[]" value=""
							id="profile-f_elem_city" required />
					</div>
					<div class="form-group inputBoxes">
						<label class="myProfileLabels">Phone number <span style="color: #e62948">*</span></label><input id="profile-contact" type="tel"
							pattern='[\+]\d{2}[\-]\d{10}' class="form-control roundedCorners inputLiveStreamInfo"
							name="contact"
							style="border-bottom-right-radius: 5px; border-top-right-radius: 5px;"
							placeholder="Fans can contact you for bookings"
							data-toggle="tooltip"
							title="Please enter a valid phone number (Eg Format: +91-9999999999)"
							required />
					</div>
					<label style="margin-left: 150px; font-weight: normal; color: gray;">Please enter in this format: +91-9999999999</label>
					<div class="form-group inputBoxes">
						<label class="myProfileLabels">Tell us something about yourself </label>
						<textarea class="form-control inputLiveStreamInfo" id="profile-biography" rows="5" cols="8" maxlength="1000"></textarea>
					</div>
					<div id="maxChars" style="color: graytext; margin-left: 350px;">Chars Left 1000</div>

				</div>
				
				<div class="djSignUpMsg">
				<h4 style="color:#e62948;">Profile Picture</h4>

			</div>

					<div>
						<div class="uploadProfilePic">
							<img id="profile-pic" style="width: 120px; height: 120px;" />
						</div>
						<div style="margin-bottom: 80px;">
							<button id="btnUploadImage" class="btn btn-default commonButton"
								style="margin-top: 0px; margin-bottom: 10px;">Upload
								Image</button>
							<input id="image-upload" name="profilePic" type="file"
								accept="image/*" />
							<div style="color: graytext; font-size: small; margin: 10px;">Photos
								should not contain any offensive material or nudity. We reserve
								the right to delete your account.</div>
								
								<div id="error" style="color: red;"></div>
								
						</div>
					</div>
					<div>
						<button id="btnSaveProfileInfo"
							class="btn btn-default commonButton" style="width: 150px;"
							type="submit">Save</button>
							
							<h4 id="successProfileUpdate" style="color: #e62948;">Profile updated successfully</h4>
					</div>


				</form>

			</div>	

		</div>


	</div>
	<!--pageContent-->

	<!--=================================
	Footer
	=================================-->
	<footer id="footer">
		<%@include file="footer.jsp"%>
	</footer>
	
	<script src="https://apis.google.com/js/client.js?onload=checkAuth"></script>
	<script type="text/javascript" src="assets/js/jquery.blockUI.js"></script>
	<script defer src="assets/js/profile.js"></script>
</body>
</html>
