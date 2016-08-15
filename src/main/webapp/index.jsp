<!DOCTYPE html>
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js">
<!--<![endif]-->
<head>
<meta charset="utf-8">
<title>Home</title>

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


<!-- <script defer src="assets/js/ajaxify.min.js"></script> -->
<link rel="stylesheet" href="http://ajax.aspnetcdn.com/ajax/jquery.ui/1.10.3/themes/flick/jquery-ui.css" />
<style type="text/css">
.ui-menu .ui-menu-item a,.ui-menu .ui-menu-item a.ui-state-hover, .ui-menu .ui-menu-item a.ui-state-active {
	font-weight: normal;
	margin: -1px;
	text-align:left;
	font-size:14px;
	}
.ui-autocomplete-loading { background: white url("/images/ui-anim_basic_16x16.gif") right center no-repeat; }
</style>

</head>
<body>

<!-- <script>
  window.fbAsyncInit = function() {
    FB.init({
      appId      : '404108326453693',
      xfbml      : true,
      version    : 'v2.7'
    });
  };

  (function(d, s, id){
     var js, fjs = d.getElementsByTagName(s)[0];
     if (d.getElementById(id)) {return;}
     js = d.createElement(s); js.id = id;
     js.src = "//connect.facebook.net/en_US/sdk.js";
     fjs.parentNode.insertBefore(js, fjs);
   }(document, 'script', 'facebook-jssdk'));
</script> -->

	
<div id="header">
<%@include file="header.jsp" %>
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
										<h2>Live Djs from India and around the world</h2>
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
			<!--=================================
      Upcoming events
      =================================-->

			<section id="upcomingEvents">

				<div class="container">
					<div class="container-all-live-djs">
						<h1>Live Djs</h1>
						<div id="liveUpcomingEvents" style="min-height: 500px;"></div>
						<div>

								<div class="djsignup">

									<ul class="sitemap">
										<li><a href="signup.jsp">Are you a Dj? <span
												class="fa fa-headphones"></span></a></li>
										<li>Go Mobile <a href="#"><span class="fa fa-apple"></span></a>
											<a href="#"><span class="fa fa-android"></span></a></li>
										<li>Follow Mixtri  @<a href="#"><span
												class="fa fa-facebook"></span></a><a href="#"> <span
												class="fa fa-twitter"></span></a></li>
									</ul>
									
									<div class="form-group" style="margin-top: 50px;">
  										<select class="form-control" id="musicGenre">
  											<option value="All">Music Genres - All</option>
										    <option value="Bollywood">Bollywood</option>
										    <option value="Dubstep">Dubstep</option>
										    <option value="Disco">Disco</option>
										    <option value="Electronic Dance Music">Electronic Dance Music</option>
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
					</div>
					<div>
					<ul class="sitemap">
						<li><a href="allevents.jsp"><span class="fa fa-th"></span>
								See All</a></li>
					</ul>
					<span id="displayRange" class="displayRange"></span>
					</div>
					
					<!-- <div id="shareButton">Happy Diwali</div> -->

				</div>
				<!-- Container -->
			</section>
		</div>
		<!--pageContent-->
	</div>
	<!--ajaxwrap-->
	<!--=================================
	Footer
	=================================-->
	<footer id="footer">
<%@include file="footer.jsp" %>
</footer>
<script type="text/javascript" src="assets/js/jstz.min.js"></script>
<script type="text/javascript" src="assets/js/index.js"></script>
</body>
</html>
