<!DOCTYPE html>
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js">
<!--<![endif]-->
<head>
<meta charset="utf-8">
<title>Change Password</title>

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

<!--<link rel="stylesheet" type="text/css" href="assets/css/blue.css">
<link rel="stylesheet" type="text/css" href="assets/css/orange.css">
<link rel="stylesheet" type="text/css" href="assets/css/red.css">
<link rel="stylesheet" type="text/css" href="assets/css/green.css">
<link rel="stylesheet" type="text/css" href="assets/css/purple.css">-->

<!-- <script async src="assets/js/modernizr-2.6.2-respond-1.1.0.min.js"></script>
<script defer src="assets/js/jquery.js"></script>
<script defer src="assets/js/ajaxify.min.js"></script>
 -->
</head>
<body>



	<!--=================================
	Header
	=================================-->
	<div id="header">
		<%@include file="header.jsp"%>
	</div>
	<div class="container" style="padding-bottom: 50px;" align="center">

		<h2 style="color: #e62948;">Reset your password</h2>
		<br>

		<form id="changePasswordForm" role="form" style="margin-bottom: 50px;">
			<div class="form-components">

				<div class="form-group inputBoxes">
					<input type="password" class="form-control inputLiveStreamInfo"
						id="newPassword" name="newPassword"
						placeholder="New Password" minlength=3 required />
				</div>
				<div class="form-group inputBoxes">
					<input type="password" class="form-control inputLiveStreamInfo"
						id="confirmPassword" name="confirmPassword"
						placeholder="Confirm Password" minlength=3 required />
				</div>
			</div>

			<div>
				<button id="btnUpatePassword" class="btn btn-default commonButton"
					style="width: 150px;" type="submit">Update</button>

				<h4 id="successPasswordUpdated" style="color: #e62948;"></h4>
				<div id="error" style="color: red;font-size:larger"></div>
			</div>

		</form>

	</div>
	<footer id="footer">
		<%@include file="footer.jsp"%>
	</footer>
	<script defer src="assets/js/change-password.js"></script>
</body>
</html>