<!DOCTYPE html>
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js">
<!--<![endif]-->
<head>
<meta charset="utf-8">
<title>Sign Up</title>

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



	<!--=================================
Vegas Slider Images
=================================-->

	<ul class="vegas-slides hidden">
		<li><img data-fade='2000' src="assets/img/BG/bg1.jpg" alt="" /></li>
		<li><img data-fade='2000' src="assets/img/BG/bg2.jpg" alt="" /></li>
		<li><img data-fade='2000' src="assets/img/BG/bg3.jpg" alt="" /></li>
		<li><img data-fade='2000' src="assets/img/BG/bg4.jpg" alt="" /></li>
	</ul>


	<div id="ajaxArea">
		<div class="pageContentArea">
			<!--=================================
      Home
      =================================-->
			<section id="home-slider">
				<div class="container">
					<div class="home-inner">

						<div id="homeSliderNav" class="slider-nav">
							<a id="home-prev" href="#" class="prev fa fa-chevron-left"></a> <a
								id="home-next" href="#" class="next fa fa-chevron-right"></a>
						</div>
						<!--sliderNav-->

						<div id="flex-home" class="flexslider" data-animation="slide"
							data-animationSpeed="1000" data-autoPlay="true"
							data-slideshowSpeed="3000">
							<ul class="slides">
								<li><img src="assets/img/slider/slider.jpg" alt="">
									<div class="flex-caption">
										<h2>Live Djs from around the world</h2>
									</div></li>
								<li><img src="assets/img/slider/slider-1.jpg" alt="">
									<div class="flex-caption">
										<h2>Never ending music festival</h2>
									</div></li>
								<li><img src="assets/img/slider/slider-2.jpg" alt="">
									<div class="flex-caption">
										<h2>24*7 Bollywood, EDM, Trance and much more</h2>
									</div></li>
								<li><img src="assets/img/slider/slider-3.jpg" alt="">
									<div class="flex-caption">
										<h2>Party at your house with the best Djs</h2>
									</div></li>
								<li><img src="assets/img/slider/slider-4.jpg" alt="">
									<div class="flex-caption">
										<h2>Play live showcase your talent</h2>
									</div></li>
							</ul>
						</div>
					</div>
				</div>
			</section>

		</div>
		<!--pageContent-->
	</div>
	<!--ajaxwrap-->


	<section id="djSignUp">
		<div class="container">

			<div class="djSignUpMsg" align="center">

				<h3>

					<span style="color: #e62948; font-size: xx-large;">Mixtri,</span>
					for Djs
				</h3>
				<h4>where India's best Djs Live Stream.</h4>
			</div>

		</div>

		<div class="container">
			<div class="djSignUp">
				<div class="row">
					<div class="col-lg-6 col-md-6 col-sm-6">
						<div class="aboutus">
							<img width=500px height=500px src="assets/img/aboutUs/phone1.jpg"
								alt="dummy">
						</div>
					</div>

					<div id="aboutus" class="col-lg-6 col-md-6 col-sm-6">
						<div class="aboutus">
							<h2 style="color: #e62948;">Get On the Groove Anywhere, Anytime</h2>
							<br> <br>
							<p>India's first 24*7 Live streaming platform for the Djs.</p>
							<p>
								<span class="wordMixtri">Mixtri,</span> is platform where Djs
								from India and around the world can showcase thier talent to
								thier fans anywhere in the world by live streaming high-quality
								audio and video right from their laptops and Dj consoles using
								our robust and easy-to-use technology without any external
								downloads or cords for ....FREE
							</p>

						</div>
						<!--\\latest news-->
					</div>
				</div>
				<div class="row">
					<div id="aboutus" class="col-lg-6 col-md-6 col-sm-6"></div>
					<div id="aboutus" class="col-lg-3 col-md-3 col-sm-3">
						<div class="signUpIcons">


							<ul>
								<li style="margin-bottom: 30px;"><span style="font-size: xx-large;"
									class="fa fa-volume-up liveIcons"></span>
									<h4>Live Audio</h4></li>
								<li><span style="font-size: xx-large;"
									class="fa fa-video-camera liveIcons"></span>
									<h4>Live Video</h4></li>
							</ul>
						</div>
						<div class=row>
							<div class="text-center">
				        		<button id="btnSignup1" class="btn btn-default commonButton" data-toggle="modal" style="width: 300px;">Sign Up</button>
				        </div>
						</div>

					</div>
					<div id="aboutus" class="col-lg-3 col-md-3 col-sm-3">
						<div class="signUpIcons">

							<ul>
								<li style="margin-bottom: 30px;"><span style="font-size: xx-large;"
									class="fa fa-ban liveIcons"></span>
									<h4>No downloads required</h4></li>
								<li><span style="font-size: xx-large;"
									class="fa fa-calendar liveIcons"></span>
									<h4>Setup event and play live</h4></li>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>
	<section id="sectionBroadcastways">
		<div class="container">
			<div class="broadcastways">

				<h2>4 ways to live stream</h2>
				<p>
					<span class="wordMixtri">Mixtri,</span> offers 4 different options
					for the Djs to choose from to broadcast live directly from their
					consoles with no external cords.
				</p>
				<div class="row">

					<div class="col-lg-6 col-md-6 col-sm-6">
						<div class="aboutus">
							<img width=99px; height=99px;
								src="assets/img/djsignup/icecast_logo.png">
						</div>
						<div class="wordMixtri">Icecast</div>
						<div>
							The easiest and the quickest way to broadcast high quality stero
							sound directly from <a href="#" style="text-decoration: none">Traktor</a>,
							<a href="#" style="text-decoration: none;">Virtual Dj</a> or <a
								href="#" style="text-decoration: none">Serato(using B.U.T.T)</a>
							softwares without any need of external cords or downloads.<a
								href="#" style="text-decoration: none"> Learn more</a>
						</div>
					</div>

					<div class="col-lg-6 col-md-6 col-sm-6">
						<div class="aboutus">
							<img width=99px; height=99px;
								src="assets/img/djsignup/flash_Media_logo.png" alt="dummy">
						</div>
						<div class="wordMixtri">Flash Media Live Encoder</div>
						<div>
							Wanna give your fans a more real time experience? Now you can use
							Flash Media Live Encoder to broadcast your video synced with your
							Dj audio output. <a href="#" style="text-decoration: none;">Learn
								more</a>
						</div>
					</div>

				</div>

				<div class="row">
					<div class="col-lg-6 col-md-6 col-sm-6">
						<div class="aboutus">
							<img width=99px; height=99px;
								src="assets/img/djsignup/DirectInput_logo.png">
							<div class="wordMixtri">Direct Input</div>
							<div>
								Wanna broadcast a live show or an audio output from an external
								mixer? Don't worry its fairly simple. Just plug in the output
								from an external device to your computer's microphone input and
								you are live to the world.<a href="#"
									style="text-decoration: none;"> Learn more</a>
							</div>
						</div>

					</div>

					<div class="col-lg-6 col-md-6 col-sm-6">
						<div class="aboutus">
							<img width=99px; height=99px;
								src="assets/img/djsignup/Music_logo.png" alt="dummy">
						</div>

						<div class="wordMixtri">Recorded Mixes</div>
						<div>Wanna chat and get feedback from your fans? Sit back
							and relax by uploading a recorded mix (mp3 format) and play it
							during a live stream.</div>

					</div>
				</div>
			</div>
		</div>
	</section>

	<section id="whyMitri">
		<div class="container">
			<div class="whyMixtri" style="margin-bottom: 30px;">

				<div>
					<h1>Why Mixtri?</h1>
				</div>
				<div>
					<div class="row">
						<div class="features">
							<div class="col-lg-4 col-md-4 col-sm-4">
								<span style="font-size: xx-large;" class="fa fa-dollar liveIcons"></span>
								Fans gets you business
							</div>

							<div class="col-lg-4 col-md-4 col-sm-4">
								<span style="font-size: xx-large;" class="fa fa-mobile liveIcons"></span>
								iOS/Android
							</div>

							<div class="col-lg-4 col-md-4 col-sm-4">
								<span style="font-size: xx-large;" class="fa fa-database liveIcons"></span>
								Storage space
							</div>
						</div>
					</div>

					<div class="row">
						<div class="features">
							<div class="col-lg-4 col-md-4 col-sm-4">
								<span style="font-size: xx-large;" class="fa fa-volume-up liveIcons"></span>
								Free Unlimited Broadcasts
							</div>

							<div class="col-lg-4 col-md-4 col-sm-4">
								<span style="font-size: xx-large;" class="fa fa-video-camera liveIcons"></span>
								Live Video
							</div>

							<div class="col-lg-4 col-md-4 col-sm-4">
								<span style="font-size: xx-large;" class="fa fa-file-image-o liveIcons"></span>
								Brand Promotion
							</div>
						</div>
					</div>

					<div class="row">
						<div class="features">
							<div class="col-lg-4 col-md-4 col-sm-4">
								<span style="font-size: xx-large;" class="fa fa-play-circle liveIcons"></span>
								Audio Recording
							</div>

							<div class="col-lg-4 col-md-4 col-sm-4">
								<span style="font-size: xx-large;" class="fa fa-desktop liveIcons"></span>
								Full Screen Party Mode
							</div>

							<div class="col-lg-4 col-md-4 col-sm-4">
								<span style="font-size: xx-large;" class="fa fa-thumbs-o-up liveIcons"></span>
								Social Connects
							</div>
						</div>
					</div>

				</div>

			</div>

			<div class="text-center">
				<button id="btnSignup2" class="btn btn-default commonButton" data-toggle="modal" style="width: 300px;">Sign Up</button>
			</div>
		</div>

	</section>
	<!--=================================
	Footer
	=================================-->
	<footer id="footer">
		<%@include file="footer.jsp"%>
	</footer>
	<script>/*Place Your Google Analytics code here*/</script>
	
	<script>
	$(document).ready(function(){
		
		if(typeof $.cookie('displayName') != 'undefined'){
			$('#btnSignup1').remove();
			$('#btnSignup2').remove();
		
		}else{
			
			$('#btnSignup1').attr('data-target','#signupbox');
			$('#btnSignup2').attr('data-target','#signupbox');
		}
	});
	
	</script>

</body>
</html>