<!DOCTYPE html>
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js">
<!--<![endif]-->
<head>
<meta charset="utf-8">
<title>Live</title>

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
<link rel="stylesheet" href="assets/css/live.css">
<link rel="stylesheet" href="assets/css/glowfont.css">
<link rel="stylesheet" href="assets/css/main.css">
<link rel="stylesheet" type="text/css" media="screen"
	href="assets/css/messenger.css">
<link rel="stylesheet"
	href="http://ajax.aspnetcdn.com/ajax/jquery.ui/1.10.3/themes/flick/jquery-ui.css" />
<!-- This z-index is for City Pick Up rest service -->
<style>
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

.ui-front {
	z-index: 9999;
}
</style>
<!-- Resource style -->
<!--<link rel="stylesheet" type="text/css" href="assets/css/blue.css">
<link rel="stylesheet" type="text/css" href="assets/css/orange.css">
<link rel="stylesheet" type="text/css" href="assets/css/red.css">
<link rel="stylesheet" type="text/css" href="assets/css/green.css">
<link rel="stylesheet" type="text/css" href="assets/css/purple.css">-->
</head>
<body>
	<div class="pageContentArea">

		<!--  ==============================================
		  
		  Login-SignUp form Start
		    
		=================================================== -->


		<div class="modal fade" id="loginbox"
			style="position: fixed; align: center;" tabindex="-1" role="dialog"
			aria-labelledby="loginboxLabel">
			<div id="logintab"
				class="mainbox modal-dialog col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
				<div class="panel panel-info">
					<div class="panel-heading">

						<button type="button" class="close" data-dismiss="modal">&times;</button>

						<div class="panel-title">
							Log in <a href="#signuptab"
								onclick="$('#loginbox').modal('hide'); $('#signupbox').modal('show');"
								style="color: greenyellow; float: right; font-size: medium; border: none; background: none;">
								Sign Up </a>
						</div>
					</div>

					<div class="panel-body">

						<div style="display: none" id="login-alert"
							class="alert alert-danger col-sm-12"></div>

						<form id="loginform" style="text-align: center; margin-top: 20px;"
							class="form-horizontal" role="form">

							<div class="form-group signUpFormAlignment">
								<div class="input-group marginSignup">
									<span class="input-group-addon"><i
										class="fa fa-envelope"></i></span>
									<div>
										<input id="emailId" type="email" class="form-control"
											name="emailId" placeholder="Email" autocomplete="email"
											style="border-bottom-right-radius: 5px; border-top-right-radius: 5px; width: 80%"
											required />
									</div>
								</div>
							</div>

							<div class="form-group signUpFormAlignment">
								<div class="input-group marginSignup">
									<span class="input-group-addon"><i class="fa fa-lock"></i></span>
									<div>
										<input id="password" type="password" class="form-control"
											style="border-bottom-right-radius: 5px; border-top-right-radius: 5px; width: 80%;"
											name="password" placeholder="password" required minlength=3
											maxlength=15 />
									</div>
								</div>
							</div>

							<div class="form-group">
								<div>
									<button type="submit" id="btnlogin"
										class="btn btn-default commonButton buttonSize"
										style="width: 141px; padding-right: 20px; border-width: thin; border-color: #e62948">Login</button>

								</div>
							</div>

							<div>
								<a href="#">Forgot password?</a>
							</div>




							<div style="border-top: 1px solid #999; padding-top: 30px"
								class="form-group">

								<div>
									<button id="btn-fbsignup" style="margin-bottom: 10px;"
										type="button" class="btn btn-primary">
										<i class="icon-facebook"></i>Log in with Facebook
									</button>
								</div>

								<div>
									By signing in, you accept our <a href="#">Terms of Use</a> and
									<a href="aboutus.jsp">Privacy Policy</a>
								</div>

							</div>

						</form>

					</div>



				</div>
			</div>
		</div>

		<!-- End Login Modal -->

		<!-- Start Signup Modal -->

		<div class="modal fade" style="position: fixed;" id="signupbox"
			tabindex="-1" role="dialog" aria-labelledby="signupboxLabel">

			<div id="signuptab"
				class="mainbox modal-dialog col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
				<div class="panel panel-info">
					<div class="panel-heading">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<div class="panel-title">
							Time to rock your fans! <a href="#signuptab"
								onclick="$('#signupbox').modal('hide'); $('#loginbox').modal('show');"
								style="color: greenyellow; float: right; font-size: medium; border: none; background: none;">
								Log in </a>

						</div>
						<!-- <div style="float: right; font-size: 85%; position: relative; top: -10px;">
						 <button id="signinlink"
							onclick="$('#signupbox').modal('hide'); $('#loginbox').modal('show');"
							style="color: greenyellow; font-size: medium; border: none; background: none;">Log in</button>
							
					</div> -->
					</div>
					<div class="modal-body">
						<form id="signupform" class="form-horizontal" role="form">

							<div id="signupalert" style="display: none"
								class="alert alert-danger">
								<p>Error:</p>
								<span></span>
							</div>

							<div class="form-group signUpFormAlignment">
								<div class="input-group marginSignup">
									<span class="input-group-addon"><i class="fa fa-user"></i></span>
									<div class="col-md-9" style="padding-left: 0px;">
										<input type="text" id="displayName" class="form-control"
											style="border-bottom-right-radius: 5px; border-top-right-radius: 5px;"
											name="name" placeholder="Dj's Display Name..." required
											minLength=3 maxlength=20 />
									</div>
								</div>
							</div>

							<div class="form-group signUpFormAlignment">
								<div class="input-group marginSignup">
									<span class="input-group-addon"><i class="fa fa-user"></i></span>
									<div class="col-md-9" style="padding-left: 0px;">
										<input id="emailId" class="form-control roundedCorners"
											name="email"
											style="border-bottom-right-radius: 5px; border-top-right-radius: 5px;"
											placeholder="Email Address" type="email" required />
									</div>
								</div>
							</div>

							<div class="form-group signUpFormAlignment">
								<div class="input-group marginSignup">
									<span class="input-group-addon"><i
										class="fa fa-mobile-phone"></i></span>
									<div class="col-md-9" style="padding-left: 0px;">
										<input id="contact" type="tel" pattern='[\+]\d{2}[\-]\d{10}'
											class="form-control roundedCorners" name="contact"
											style="border-bottom-right-radius: 5px; border-top-right-radius: 5px;"
											placeholder="Fans can contact you for bookings"
											data-toggle="tooltip"
											title="Please enter a valid phone number (Eg Format: +91-9999999999)"
											required />

									</div>
								</div>
								<label style="margin-left: 80px; font-weight: normal;">Please
									enter in this format: +91-9999999999</label>
							</div>
							<div class="form-group signUpFormAlignment">
								<div class="input-group marginSignup">
									<span class="input-group-addon"><i class="fa fa-home"></i></span>
									<div class="col-md-9" style="padding-left: 0px;">
										<input class="form-control ff_elem" type="text"
											style="border-bottom-right-radius: 5px; border-top-right-radius: 5px;"
											placeholder="Your City: Type aleast first 3 letters"
											title="Please enter your city" name="ff_nm_from[]" value=""
											id="f_elem_city" required />
									</div>
								</div>
							</div>

							<div class="form-group signUpFormAlignment">
								<div class="input-group marginSignup">
									<span class="input-group-addon"><i class="fa fa-lock"></i></span>
									<div class="col-md-11" style="padding-left: 0px;">
										<input id="Signup_password" type="password"
											style="border-bottom-right-radius: 5px; border-top-right-radius: 5px;"
											class="form-control roundedCorners" name="Signup_password"
											placeholder="Password" minlength=3 required />
									</div>


									<span class="input-group-addon"><i class="fa fa-lock"></i></span>
									<div class="col-md-11" style="padding-left: 0px;">
										<input id="confirm_password" type="password"
											style="border-bottom-right-radius: 5px; border-top-right-radius: 5px;"
											class="form-control roundedCorners" name="confirm_password"
											placeholder="Confirm Password" minlength=3 required />
									</div>
								</div>
							</div>

							<div class="form-group"
								style="padding-top: 0px; text-align: center;">
								<div>
									<button type="submit" id="btn-signup"
										class="btn btn-default commonButton buttonSize"
										style="width: 141px; border-width: thin; border-color: #e62948">Create
										account</button>
								</div>
							</div>
							<div>
								<div style="border-top: 1px solid #999; padding-top: 10px"
									class="form-group">

									<div class="col-md-offset-2 col-md-9"
										style="padding-left: 0px; padding-right: 0px;">
										<button id="btn-fbsignup" style="margin-bottom: 10px;"
											type="button" class="btn btn-primary">
											<i class="icon-facebook"></i>Sign Up with Facebook
										</button>
										<button id="btn-fbsignup" style="margin-bottom: 10px;"
											type="button" class="btn btn-danger">
											<i class="icon-facebook"></i>Sign Up with Google
										</button>
									</div>
								</div>

								<div class="text-center" style="padding-top: 0px;">
									By signing in, you accept our <a href="#">Terms of Use</a> and
									<a href="aboutus.jsp">Privacy Policy</a>
								</div>
							</div>


						</form>
					</div>
				</div>


			</div>

		</div>
		<!--  End Signup Modal -->

		<!-- =================================================
		
		Login signup form End
		
		====================================================== -->


		<div id="feedbackModal" class="modal fade" role="dialog">
			<div class="modal-dialog">

				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h3 class="modal-title">How was your experience with Mixtri?</h3>
					</div>
					<div class="modal-body feedbackModalClass">
						<div id="divFeedback">
							<div>We take feedbacks very seriously. Your valuable
								feedback helps us improve the experience!</div>
							<form id="feeback" class="form-horizontal" role="form">
								<div class="form-group">
									<div id="maxChars" style="color: graytext; font-size: small;">Chars
										Left 500</div>
									<textarea id="feedbackText" class="form-control" rows="5"
										id="comment" style="width: 90%;" required></textarea>
								</div>
								<div id="validationError" style="color: red;"></div>

							</form>
						</div>
						<div id="feedbackSubmitted"></div>
					</div>
					<div class="modal-footer">
						<div id="btnsFeedback">
							<button id="btnCancelFeedback" type="button"
								class="btn btn-default commonButton"
								style="border-width: thin; border-color: #e62948"
								data-dismiss="modal">Return to homepage</button>
							<button id="btnSubmitFeedback" type="button"
								class="btn btn-default commonButton"
								style="border-width: thin; border-color: #e62948">Leave
								Feedback</button>
						</div>
						<div>
							<button id="btnReturnHome" type="button"
								class="btn btn-default commonButton hidden"
								style="border-width: thin; border-color: #e62948"
								data-dismiss="modal">Return to homepage</button>
						</div>
					</div>

				</div>

			</div>
		</div>

		<div id="endEvent" class="modal fade" role="dialog">
			<div class="modal-dialog">

				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h3 class="modal-title">End Event!</h3>
					</div>
					<div class="modal-body"
						style="font-family: initial; font-size: x-large;">
						<div>Are you sure you want to end this live event?</div>
					</div>
					<div class="modal-footer">
						<div>
							<button id="btnYes" type="button"
								class="btn btn-default commonButton buttonSize"
								style="border-width: thin; border-color: #e62948"
								data-dismiss="modal">Yes</button>
							<button id="btnNo" type="button"
								class="btn btn-default commonButton buttonSize"
								style="border-width: thin; border-color: #e62948"
								data-dismiss="modal">No</button>
						</div>
					</div>
				</div>

			</div>
		</div>

		<!-- Icecast Setting Modal -->
		<div class="modal fade" id="modalIcecastSettings" role="dialog">
			<div class="modal-dialog">

				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h3 class="modal-title">Your Icecast Settings!</h3>
					</div>
					<div class="modal-body">

						<ul class="nav nav-tabs" id="tabContent">
							<li class="active"><a href="#Traktor" data-toggle="tab">Traktor</a></li>
							<li><a href="#VDJ-7" data-toggle="tab">VDJ-7</a></li>
							<li><a href="#VDJ-8" data-toggle="tab">VDJ-8</a></li>
						</ul>

						<div class="tab-content">
							<div class="tab-pane active" id="Traktor">Tracktor</div>

							<div class="tab-pane" id="VDJ-7">VDJ-7</div>


							<div class="tab-pane" id="VDJ-8">VDJ-8</div>

						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default eventModalButton"
								style="border-width: thin; border-color: #e62948"
								data-dismiss="modal">Close</button>
						</div>
					</div>

				</div>
			</div>
		</div>
		<!-- .cd-section -->
		<div class="row slidingGate">
			<div id="half1" class="col-xs-12 col-sm-12 col-md-6 cd-half-block1">
				<div class="clubMixtri eventEntry" style="margin-top: 150px;">
					<p>TIME TO GET IN THE MIX!</p>
				</div>
				<!-- <div class="eventEntry">TO</div>
				<div class="eventEntry">GET IN THE</div>
				<div class="eventEntry">MIX!</div> -->
			</div>

			<div id="half2" class="col-xs-12 col-sm-12 col-md-6 cd-half-block2">
				<p class="clubMixtri">CLUB MIXTRI</p>
				<!-- Countdown Clock -->
				<div id="countdown"></div>
				<!-- jPlayer For Siren -->
				<div id="jPlayerSiren"></div>
				<div id="jPlayerLiveTrack"></div>
				<div class="djName enterClub" style="margin-top: 0px;"></div>


				<div class="enterClub">
					<div>
						<span style="color: graytext">Door Opens in: </span><span
							id="timeLeft"></span>
					</div>
					<button id="btnJoinEvent" class="btn btn-default commonButton">
						<span class="fa fa-sign-in"></span><span id="btnText">Join
							Event</span>
					</button>
					<button id="btnStartEvent" class="btn btn-default commonButton">
						<span class="fa fa-sign-in"></span>Start Live Streaming
					</button>

				</div>

				<div class="text-center">
					<button id="btnIcecastSettings" type="button"
						class="btn btn-default commonButton btnIcecastSettings hidden"
						data-toggle="modal" data-target="#modalIcecastSettings"
						style="width: 400px;">View your icecast settings before
						streaming live!</button>

				</div>

			</div>
		</div>

		<div class="row eventInfoRow">
			<div class="col-xs-12 col-sm-12 col-md-3">
				<div>
					<p class="clubMixtri">CLUB</p>
				</div>
				<div>
					<div id="blink" class="checkStreamingConnection">Now Live!</div>
					<br> <span id="divIcecastStreaming" class="hidden">Icecast
						Streaming? <span id="isIcecastStreaming"
						class="checkStreamingConnection">Yes</span>
					</span>
				</div>
				<br>
				<div>
					Live Stream Info: <span id="streamInfo" class="step-description"></span>
				</div>
				<div id="description">
					Description: <span id="eventDescription" class="step-description"></span>
				</div>
				<div>
					Genre: <span id="genre" class="step-description">Open Format</span>
				</div>
				<!-- <h4>
							Uploaded Mix Name: <span id="uploadedMixName" class="step-description">Podcast -10</span>
				</h4> -->

				<br>
				<div style="max-width: 250px; margin-bottom: 30px;">

					<a href="#" id="btnShowDjContactInfo"
						style="text-align: center; color: #e62948; font-size: larger;"
						data-toggle="popover" title="Dj's Contact Info"
						data-placement="top"> Liked <span id="djName"></span>'s music?
						See contact Info for bookings!
					</a>
				</div>

				<div style="display: margin-bottom: 20px;">
					<button id="btnEndEvent" style="size: 100px;" data-toggle="modal"
						data-target="#endEvent" class="btn btn-default commonButton">End
						Event!</button>

				</div>

				<!-- <button id="btnIcecastSettings" type="button" class="btn btn-default commonButton btnIcecastSettings hidden"
					 data-toggle="modal" data-target="#modalIcecastSettings">View your icecast settings!</button> -->

				<a href="#" class="btnIcecastSettings hidden"
					style="color: #e62948; font-size: large;" data-toggle="modal"
					data-target="#modalIcecastSettings">View your icecast settings
					>></a>

			</div>
			<div class="col-xs-12 col-sm-12 col-md-5">

				<div id="bgVideoTheme">
					<video class="bgTheme" autoplay muted loop>
						<source src="" type="video/mp4">
					</video>
				</div>

				<div class="avatar">
					<div class="text-center">
						<img id="djAvatar" src="assets/img/events/DjAvatar.gif">
					</div>
				</div>

				<div>
					<img class="eventPic" src="" alt="" />
				</div>

				<div class="djName djNameAlign"></div>

			</div>
			<div class="col-xs-12 col-sm-12 col-md-4">


				<p class="clubMixtri mixtri pointer-cursor">MIXTRI</p>

				<audio id="streamAudioPlayer" preload="auto">
					<source id="srcOgg" src="" type="audio/ogg">
					<source id="srcMp3" src="" type="audio/mpeg">
				</audio>
				
				<audio id="playSiren" src="https://drive.google.com/uc?export=download&id=0B_jU3ZFb1zpHQktFWHR3OVcxWjA" preload="auto">
				
				</audio>

				<div class="blink">
					<div>
						<span class="attendeeFanKudoCount"><i class="fa fa-user"
							style="color: #e62948"> Attendees</i> <span id="attendeeCount">
								0</span></span>
					</div>
					<div>
						<span class="attendeeFanKudoCount"><i
							class="fa fa-thumbs-up fanKudos"> Kudos</i><span id="kudosCount"></span></span>
					</div>
					<div>
						<span class="attendeeFanKudoCount"><i
							class="fa fa-beer fanKudos"> Fans</i><span id="fanCount"></span></span>
					</div>
				</div>
				<div class="kudo-fan-attendee-count">
					<div>
						Kudos <i id="kudos" class="fa fa-thumbs-up pointer-cursor"
							style="font-size: xx-large;"></i> <span
							style="margin-left: 20px;"><span id="wordFanUnfan">Fan</span>
							<i id="fan" class="fa fa-beer pointer-cursor"
							style="font-size: xx-large;"></i></span>
					</div>

				</div>



				<div class="chatComponents">
					<div id="contents">
						<div id="wrapper">
							<div class="bubble-container" style="display: inline;"></div>
						</div>
					</div>

					<div id="form" align="center">
						<form class="form" id="chatForm">
							<input id="msgText" class="chatTextBox" type="text" /> <input
								type="submit" class="button" value="Send" />
						</form>
					</div>
				</div>

			</div>
		</div>
		<div class="row" class="crowd">
			<div class="col-xs-12 col-sm-12 col-md-3">

				<div class="col-xs-12 col-sm-12 col-md-2"></div>
				<div class="col-xs-12 col-sm-12 col-md-2"></div>

				<div class="col-xs-12 col-sm-12 col-md-4"></div>

			</div>
		</div>
	</div>
	<!-- row class crowd -->

	<script type="text/javascript"
		src="assets/js/modernizr-2.6.2-respond-1.1.0.min.js"></script>
	<!-- Modernizr -->
	<script type="text/javascript" src="assets/js/jquery-1.11.1.min.js"></script>
	<script defer src="assets/js/bootstrap.min.js"></script>
	<script defer src="assets/js/jquery.cookie.js"></script>
	<script type="text/javascript"
		src="assets/js/jquery.countdown360.min.js"></script>
	<script defer src="assets/jPlayer/jquery.jplayer.min.js"></script>
	<script type="text/javascript" src="assets/js/live.js">	</script>
	<!-- <script defer src="assets/js/messengerWebsocket.js"></script> -->
	<script defer src="assets/js/jquery-ui.js"></script>
	<script defer src="assets/js/main.js"></script>
</body>
</html>