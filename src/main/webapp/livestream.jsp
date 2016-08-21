<!DOCTYPE html>
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js">
<!--<![endif]-->
<head>
<meta charset="utf-8">
<title>Livestream</title>

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
<link rel="stylesheet" href="assets/css/jquery-ui.css">
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<!--<link rel="stylesheet" type="text/css" href="assets/css/blue.css">
<link rel="stylesheet" type="text/css" href="assets/css/orange.css">
<link rel="stylesheet" type="text/css" href="assets/css/red.css">
<link rel="stylesheet" type="text/css" href="assets/css/green.css">
<link rel="stylesheet" type="text/css" href="assets/css/purple.css">-->


<!-- <script defer src="assets/js/ajaxify.min.js"></script> -->
</head>
<body>



	<div id="header">
		<%@include file="header.jsp"%>
	</div>

	<div class="pageContentArea">
		<div class="container">
			<div class="djSignUpMsg">
				<div class="text-center font-size-large">
					<h2>Live Stream Setup</h2>
				</div>
				<div class="text-center" style="color: #e62948">
					<h4>Time to rock your fans!</h4>
				</div>
				<!-- End header -->
			</div>
			<!-- Start Step 1 -->
			<form id="event-form" action="" method="post" role="form">
				<div class="form-components">

					<div class="divided-section-header">
						<span class="event-steps">Step 1:</span>&nbsp;<span
							class="step-description">Enter Live Stream Info:</span>
					</div>

					<div class="row">

						<div class="col-xs-12 col-sm-12 col-md-4">
							<!-- <div class="divided-section"> -->
							<div class="form-group">
								<label>Live Stream Name<span style="color: #e62948">*</span></label>
								<input type="text" class="form-control inputLiveStreamInfo"
									id="streamInfo" name="streamInfo" maxlength="35" required />
							</div>
							<div class="form-group">
								<div class="dateTimePicker">
									<label>Date:<span style="color: #e62948">*</span></label> <input
										id="eventDatePicker" type="text"
										class="form-control event-date-time" type="date"
										name="eventDate" value="" required />
								</div>
								<div class="dateTimePicker">
									<label>Event Start Time:<span style="color: #e62948">*</span></label>
									<input id="eventTimePicker" type="time"
										class="form-control event-date-time" name="eventTime" required />

								</div>
								<div style="color: graytext; font-size: small;">MM/DD/YYYY</div>


							</div>
							<div>
								<div>
									<label>Time Zone:<span style="color: #e62948">*</span></label>
									<select id="timeZone" name="selectedTimeZone"
										class="form-control">
										<option value="GMT-12:00">(GMT -12:00) Eniwetok,
											Kwajalein</option>
										<option value="GMT-11:00">(GMT -11:00) Midway Island,
											Samoa</option>
										<option value="GMT-10:00">(GMT -10:00) Hawaii</option>
										<option value="GMT-09:00">(GMT -09:00) Alaska</option>
										<option value="GMT-08:00">(GMT -08:00) Pacific Time
											(US &amp;Canada)</option>
										<option value="GMT-07:00">(GMT -07:00) Mountain Time
											(US &amp; Canada)</option>
										<option value="GMT-06:00">(GMT -06:00) Central Time
											(US &amp; Canada), Mexico City</option>
										<option value="GMT-05:00">(GMT -05:00) Eastern Time
											(US &amp; Canada), Bogota, Lima</option>
										<option value="GMT-04:50">(GMT -04:30) Caracas</option>
										<option value="GMT-04:00">(GMT -04:00) Atlantic Time
											(Canada), La Paz, Santiago</option>
										<option value="GMT-03:50">(GMT -03:30) Newfoundland</option>
										<option value="GMT-03:00">(GMT -03:00) Brazil, Buenos
											Aires, Georgetown</option>
										<option value="GMT-02:00">(GMT -02:00) Mid-Atlantic</option>
										<option value="GMT-01:00">(GMT -01:00 hour) Azores,
											Cape Verde Islands</option>
										<option value="GMT">(GMT) Western Europe Time,
											London, Lisbon, Casablanca, Greenwich</option>
										<option value="GMT+01:00">(GMT +01:00 hour) Brussels,
											Copenhagen, Madrid, Paris</option>
										<option value="GMT+02:00">(GMT +02:00) Kaliningrad,
											South Africa, Cairo</option>
										<option value="GMT+03:00">(GMT +03:00) Baghdad,
											Riyadh, Moscow, St. Petersburg</option>
										<option value="GMT+03:50">(GMT +03:30) Tehran</option>
										<option value="GMT+04:00">(GMT +04:00) Abu Dhabi,
											Muscat, Yerevan, Baku, Tbilisi</option>
										<option value="GMT+04:50">(GMT +04:30) Kabul</option>
										<option value="GMT+05:00">(GMT +05:00) Ekaterinburg,
											Islamabad, Karachi, Tashkent</option>
										<option value="GMT+05:30" selected="selected">(GMT
											+05:30) Mumbai, Kolkata, Chennai, New Delhi</option>
										<option value="GMT+05:75">(GMT +05:45) Kathmandu</option>
										<option value="GMT+06:00">(GMT +06:00) Almaty, Dhaka,
											Colombo</option>
										<option value="GMT+06:50">(GMT +06:30) Yangon, Cocos
											Islands</option>
										<option value="GMT+07:00">(GMT +07:00) Bangkok,
											Hanoi, Jakarta</option>
										<option value="GMT+08:00">(GMT +08:00) Beijing,
											Perth, Singapore, Hong Kong</option>
										<option value="GMT+09:00">(GMT +09:00) Tokyo, Seoul,
											Osaka, Sapporo, Yakutsk</option>
										<option value="GMT+09:50">(GMT +09:30) Adelaide,
											Darwin</option>
										<option value="GMT+10:00">(GMT +10:00) Eastern
											Australia, Guam, Vladivostok</option>
										<option value="GMT+11:00">(GMT +11:00) Magadan,
											Solomon Islands, New Caledonia</option>
										<option value="GMT+12:00">(GMT +12:00) Auckland,
											Wellington, Fiji, Kamchatka</option>
									</select>

								</div>
							</div>

							<!-- End Dropdown -->

							<!-- <div id="streamButton">
								<button id="goLive" type="button" class="btn streamActive"
									onClick="publicRoom()">Go Live!</button>
								<button id="testStream" type="button" class="btn btn-default"
									onClick="privateRoom()">Test Stream</button>
							</div>		
								<div
									style="font-size: small; color: graytext; margin-top: 10px;">*
									Select Test Stream to test your connection before going live</div> -->


							<!-- </div> -->
							<!-- </div> -->
						</div>
						<!-- First Column Finished -->

						<div class="col-xs-12 col-sm-12 col-md-4">
							<div class="form-group">
								<div style="display: inline-flex;">
									<label style="width: 240px;">Event Description:</label>
									<div id="maxChars" style="color: graytext;">Chars Left
										140</div>
								</div>
								<textarea class="form-control" rows="5" id="eventDescription"
									name="eventDescription"
									placeholder="Something catchy to attract fans for your event!!!"></textarea>

							</div>

							<div>
								<div>
									<br> <label>Genre:</label> <select class="form-control"
										id="genre">
										<option value="Open Format">Music Genres - All</option>
										<option value="Bollywood">Bollywood</option>
										<option value="Dubstep">Dubstep</option>
										<option value="Disco">Disco</option>
										<option value="Electronic Dance Music">Electronic
											Dance Music</option>
										<option value="House">House</option>
										<option value="Hip Hop/Rap">Hip Hop/Rap</option>
										<option value="Indi Pop">Indi Pop</option>
										<option value="Instrumental">Instrumental</option>
										<option value="Progressive">Progressive</option>
										<option value="Punjabi">Punjabi</option>
										<option value="RnB">RnB</option>
										<option value="Reggae">Reggae</option>
										<option value="Rock">Rock</option>
										<option value="Retro">Retro</option>
										<option value="Disco">Disco</option>
										<option value="Disco">Trance</option>
									</select>
								</div>
							</div>


						</div>

						<!-- Second Column Finished -->

						<div class="col-xs-12 col-sm-12 col-md-4">
							<div class="form-group">
								<div>
									<label>Image<span style="color: #e62948">*</span></label>
								</div>
								<div class="uploadImage">
									<img id="profile-pic" style="width: 194px; height: 104px;" />
								</div>
								<div class="right">
									<button id="btnUploadImage"
										class="btn btn-default commonButton">Upload Image</button>
									<input id="image-upload" name="eventPic" type="file"
										accept="image/*" />
								</div>

							</div>

							<div class="hashTags">
								<div class="form-group">
									<label style="padding-top: 20px;">#HashTags/Keywords</label> <input
										type="text" class="form-control" id="hashtags" name="hashtags"
										maxLength="40"
										placeholder="#Bollywood Electronic, #English, #Trance etc...">
								</div>
							</div>

						</div>

						<!-- Third Coloumn finished -->

					</div>
					<!-- row finished -->

					<!-- This puts a border and divides a page -->
					<div class="djSignUpMsg"></div>
					<!-- Step 2 starts -->
					<div class="divided-section-header">
						<span class="event-steps">Step 2:</span>&nbsp;<span
							class="step-description">Pick a streaming option:</span>
					</div>
					<!-- Streaming Option Col-1 -->
					<div id="panelrow" class="row">
						<!-- Streaming options column1 -->
						<div class="col-xs-12 col-sm-12 col-md-5">
							<div class="panel-group">
								<div id="panel-icecast"
									class="panel panel-primary pointer-cursor selected">
									<div class="panel-heading">
										<strong>ICECAST: </strong>Best streaming option for live audio
									</div>
									<div class="panel-body">
										<img class="panel-logo"
											src="assets/img/djsignup/icecast_logo.png">
										<p>
											Easiest of all, Icecast lets you stream live audio in minutes
											& can be used directly with <span class="wordMixtri">Traktor</span>
											and <span class="wordMixtri">VirtualDJ</span> or any other
											Icecast compatible DJ software. <a href="#">Learn how ></a>
										<p>
									</div>

								</div>

								<label></label>

								<div id="panel-recorded-mixes"
									class="panel panel-info pointer-cursor">
									<div class="panel-heading">
										<strong>RECORDED MIXES</strong>
									</div>
									<div class="panel-body">
										<img class="panel-logo"
											src="assets/img/djsignup/Music_logo.png"> Wanna sit
										back and relax and yet want your fans to listen to your mixes?
										Just upload a recorded set and stream it live.
									</div>
								</div>
							</div>

						</div>
						<!-- Streaming options column 1 Ends -->

						<!-- Streaming options Column 2 Starts-->

						<div class="col-xs-12 col-sm-12 col-md-5" style="left: 100px">
							<div class="panel-group">

								<div id="panel-directinput"
									class="panel panel-info pointer-cursor">
									<div class="panel-heading">
										<strong>DIRECT INPUT</strong>
									</div>
									<div class="panel-body">
										<img src="assets/img/djsignup/DirectInput_logo.png"
											style="height: 100px; width: 70px; float: left;">
										Directly plugin your mixer's/hardware sound output to your
										computer's microphone input and you are done. <a href="#">Learn
											how ></a>
									</div>

								</div>
								<label></label>
								<div id="panel-flash-media"
									class="panel panel-info pointer-cursor">
									<div class="panel-heading">
										<strong>FLASH MEDIA LIVE ENCODER</strong>
									</div>
									<div class="panel-body">
										<img class="panel-logo"
											src="assets/img/djsignup/flash_Media_logo.png"> Wanna
										get more close to your fans? Now you can stream your live
										video at multiple angles synced up with your audio using flash
										media encoder. <br> <a href="#">Learn how ></a>
									</div>
								</div>
							</div>

						</div>

						<!-- Streaming options Col 2 ends -->
					</div>
					<!--  Streaming Option row Ends -->

					<div id="uploadedMixes">
						<div class="row">
							<div class="col-xs-12 col-sm-12 col-md-5">

								<div id="panel-past-mixes" class="panel panel-default"
									style="margin-top: 10px; margin-left: 10px;">
									<div class="panel-heading">
										<strong>CHOOSE FROM YOUR PREVIOUS RECORDED MIXES</strong>
									</div>
									<div id="uploaded-past-mixes" class="panel-body"></div>

								</div>

							</div>

							<div class="col-xs-12 col-sm-12 col-md-1">
								<h4>OR</h4>
							</div>

							<div class="col-xs-12 col-sm-12 col-md-5">

								<div>
									<h4>Upload A Recorded Mix!</h4>
								</div>
								</br>

								<div class="section-recorded-mixes">
									<div style="color: #e62948;">
										<h4 id="selectedMixName">Selected Mix: None</h4>
									</div>
									<br>
									<h5 class="disk-storage">Storage Space</h5>
									<div class="storage-space">
										<div id="disk-space-bar" class="progress-bar"></div>

									</div>
									<div id="diskSpace" class="disk-space">
										<span id="spanDiskSpace">1024 MB Left</span> <span
											style="float: right;"><a href="uploadmanager.jsp">Need
												More Space? Delete Old Records</a></span>
									</div>
									<!-- <div id="diskStorage" class="disk-storage">1024 MB Left</div>
						<div id="diskStorage" class="disk-storage right">Need More? Delete Old Records!</div> -->



									<div class="form-group">

										<label>Mix Title<span style="color: #e62948">*</span></label>
										<input type="text" class="form-control inputLiveStreamInfo"
											id="mix-title" placeholder="Eg: Non-Stop Bollywood Mix" />
									</div>
									<div>

										<button id="btnUploadMix" class="btn btn-default commonButton">Choose
											File</button>
										<input id="mixUpload" type="file" name="mixUpload"
											accept="audio/*" /> <label>&nbsp;Choose Mix<span
											style="color: #e62948">*</span></label> <label
											style="color: graytext;">Max size 140 MB, only .mp3</label>
										<div id="selectedFileName" style="color: graytext;"></div>
										<br>

										<div id="progress-bar-percent">
											<div id="progress-bar" class="progress-bar">&nbsp;</div>
											<div id="progress-percent" style="color: graytext;"></div>
										</div>

										<div id="maxFileSizeError" class="alert-error"></div>

										<div id="invalid-mp3-file" class="alert-error"></div>

									</div>
									<div>
										<div id="saveSetErrors" style="color: red;"></div>
										<div id="saveSetSuccess" style="color: green;"></div>
									</div>
									<div style="float: left;">
										<button id="btnSaveSet" class="btn btn-default commonButton"
											style="width: 200px;">Upload</button>
									</div>

								</div>

							</div>

						</div>


					</div>


					<!-- Background Themes -->

					<div class="djSignUpMsg"></div>
					<!-- Step 2 starts -->
					<div class="divided-section-header">
						<div>
							<span class="event-steps">Step 3:</span>&nbsp;<span
								class="step-description">Pick your background theme:</span>
						</div>
						<div style="color: #e62948;">
							<h4 id="selectedTheme">Selected Theme: LUMINATOR</h4>
						</div>
					</div>
					<!-- Streaming Option Col-1 -->
					<div id="panelrow" class="row">
						<!-- Streaming options column1 -->
						<div class="col-xs-12 col-sm-12 col-md-5">

							<div class="panel panel-default"
								style="margin-top: 10px; margin-left: 10px;">
								<div class="panel-heading">
									<strong>CHOOSE YOUR BACKGROUND THEME</strong>
								</div>
								<div class="panel-body"
									style="max-height: 300px; overflow-y: scroll;">
									<div id="vidTheme-1" class="videoThemes selectedVid selected">
										<label>LUMINATOR</label>

									</div>
									<div id="vidTheme-2" class="videoThemes">
										<label>PINK VINYL</label>

									</div>
									<div id="vidTheme-3" class="videoThemes">
										<label>BOUNCE GENERATION</label>

									</div>
									<div id="vidTheme-4" class="videoThemes">
										<label>PURPCYCLE</label>

									</div>
									<div id="vidTheme-5" class="videoThemes">
										<label>RATATA</label>

									</div>
									<div id="vidTheme-6" class="videoThemes">
										<label>TOMMOROW LAND</label>
									</div>



								</div>

							</div>

						</div>
						<!-- Background Theme Cols Ends -->

						<!-- Vidoes Start -->
						<div class="col-xs-12 col-sm-12 col-md-6">
							<div id="bgTheme-1" class="bgThemeVideo">
								
								<video id="vidFrame-1" controls muted controls="controls"
									class="vidFrames" preload="metadata">
									<source
										src="https://drive.google.com/uc?export=download&id=0B5rjLGYHECZeOTBBTnFBNUJKeWM"
										type='video/mp4' />
								</video>

							</div>
							<div id="bgTheme-2" class="bgThemeVideo hidden">

								<video id="vidFrame-2" controls muted controls="controls"
									class="vidFrames" preload="metadata">
									<source
										src="https://drive.google.com/uc?export=download&id=0B5rjLGYHECZeNmVkUkJjanJLaEk"
										type='video/mp4' />
								</video>

							</div>

							<div id="bgTheme-3" class="bgThemeVideo hidden">
								<video id="vidFrame-3" controls muted controls="controls"
									class="vidFrames" preload="metadata">
									<source
										src="https://drive.google.com/uc?export=download&id=0B5rjLGYHECZeMmpoU25adzhyeDA"
										type='video/mp4' />
										
										<source src="https://drive.google.com/uc?export=download&id=0B5rjLGYHECZeMmpoU25adzhyeDA" type='video/webm'/>								</video>
							</div>

							<div id="bgTheme-4" class="bgThemeVideo hidden">
								<video id="vidFrame-4" controls muted class="vidFrames"
									preload="metadata">
									<source
										src="https://drive.google.com/uc?export=download&id=0B5rjLGYHECZeUloxT2tSa0FjTm8"
										type='video/mp4' />
								</video>

							</div>

							<div id="bgTheme-5" class="bgThemeVideo hidden">
								<video id="vidFrame-5" controls muted class="vidFrames"
									preload="metadata">
									<source
										src="https://drive.google.com/uc?export=download&id=0B5rjLGYHECZebTgxWnh5RHRSWjA"
										type='video/mp4' />
								</video>
							</div>

							<div id="bgTheme-6" class="bgThemeVideo hidden">
								<video id="vidFrame-6" controls class="vidFrames" muted
									preload="metadata">
									<source
										src="https://drive.google.com/uc?export=download&id=0B5rjLGYHECZealViOGJTVzhSakU"
										type='video/mp4' />
								</video>

							</div>

						</div>

						<!-- Videos Ends -->

					</div>
					<!-- Background theme rows ends -->

					<!-- Validation Errors shown here -->
					<div class="form-group">
						<div class="text-center">
							<div id="messages" style="color: red;"></div>
						</div>
					</div>

					<div class="text-center">
						<button id="btnLiveStream" class="btn btn-default commonButton"
							style="width: 300px;" type="submit">Setup Live Stream</button>
					</div>
				</div>
				<!-- form-component ended -->
			</form>

		</div>


		<!--=================================
	Footer
	=================================-->
		<footer id="footer">
			<%@include file="footer.jsp"%>
		</footer>
		<script type="text/javascript" src="assets/js/jquery.blockUI.js"></script>
		<script src="https://apis.google.com/js/client.js"></script>
		<script type="text/javascript" src="assets/js/livestream.js"></script>
	<script>	
		/*Place Your Google Analytics code here*/
	</script>
	</div>
</body>
</html>