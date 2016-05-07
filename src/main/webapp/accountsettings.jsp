<!DOCTYPE html>
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js">
<!--<![endif]-->
<head>
<meta charset="utf-8">
<title>Mixtri-Account Settings</title>

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

</head>
<body>



	<div id="header">
		<%@include file="header.jsp"%>
	</div>

	<div id="modalPasswordChangeSuccess" class="modal fade" role="dialog">
			<div class="modal-dialog">

				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h3 class="modal-title">Change Password</h3>
					</div>
					<div class="modal-body" style="font-family: initial; font-size: x-large;">
						<div>Password changed successfully</div>
					</div>
					<div class="modal-footer">
					  <div>
					 		<button id="btnReturnHome" type="button" class="btn btn-default commonButton" 
					 		style="border-width: thin; border-color: #e62948; margin: 0px;" data-dismiss="modal">Ok</button>
					 </div> 	
					</div>
				</div>

			</div>
		</div>
		
		<div id="modalDeleteAccount" class="modal fade" role="dialog">
			<div class="modal-dialog">

				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h3 class="modal-title">Delete Account</h3>
					</div>
					<div class="modal-body" style="font-family: initial; font-size: x-large;">
						<div>Are you sure you want to delete your Mixtri account?</div>
					</div>
					<div class="modal-footer">
					  <div>
					 		<button id="btnDeleteAccountYes" type="button" class="btn btn-default commonButton" 
					 		style="border-width: thin; border-color: #e62948; margin: 0px;" data-dismiss="modal">Yes</button>
					 		<button id="btnDeleteAccountNo" type="button" class="btn btn-default commonButton" 
					 		style="border-width: thin; border-color: #e62948; margin: 0px;" data-dismiss="modal">No</button>
					 </div> 	
					</div>
				</div>

			</div>
		</div>


	<div class="pageContentArea">
		<div class="container">
			<div class="djSignUpMsg">
				<h2 style="text-align: center; font-size: xx-large;">Account Settings</h2>

			</div>
	  <div style="margin-left: 300px;">
	  
	  <div class="djSignUpMsg">
				<h4 style="color:#e62948;">Change password</h4>

			</div>
	  
			<form id="changePasswordForm" role="form" style="margin-bottom: 50px;">
				<div class="form-components">
					<div class="form-group inputBoxes">
						<label class="myProfileLabels">Password </label>
						<input id="changePassword" type="password"
									    class="form-control inputLiveStreamInfo" name="Signup_password"
										placeholder="Password" minlength=3 required /> 
					</div>
					<div class="form-group inputBoxes">
						<label class="myProfileLabels">Re-enter Password </label>
						<input id="re-enterpassword" type="password"
						class="form-control inputLiveStreamInfo" name="confirm_password" 
						placeholder="Confirm Password" minlength=3 required />
					</div>
					
					
				</div>
				
					<div>
						<button id="btnSaveChangePassword"
							class="btn btn-default commonButton" style="width: 150px;"
							type="submit">Save</button>
					</div>
					<div id="error" style="color: red;"></div>


				</form>
				
				<div class="djSignUpMsg">
				<h4 style="color:#e62948;">Delete My Account</h4>
				</div>
				
				<div style="color: #CD0022; border-style:double; text-align:center; font-size: large;">This will permanently remove your Mixtri account and cannot be reverted back</div>
				<button id="btnDeleteAccount"
							class="btn btn-default commonButton" style="width: 150px;"
							type="submit">Delete Account</button>	
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
	
	<script defer src="assets/js/accountsettings.js"></script>
</body>
</html>
