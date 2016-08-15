<!DOCTYPE html>
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js">
<!--<![endif]-->
<head>
<meta charset="utf-8">
<title>All Events</title>

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
 -->
<!--  <script defer src="assets/js/ajaxify.min.js"></script>-->

</head>
<body>



	<!--=================================
	Header
	=================================-->
	<div id="header">
		<%@include file="header.jsp"%>
	</div>

	<div class="pageContentArea">
		<!--=================================
      bread crums
      =================================-->
		<section class="breadcrumb">

			<div class="container">
				<div class="row">
					<div class="col-lg-6 col-md-6 col-sm-6">
						<h1>latest events</h1>
						<h5>list of upcoming and live events</h5>
					</div>

				</div>
			</div>
		</section>

		<section id="upcomingEvents">

			<div class="container">
				<div class="container-all-live-djs">
					<h1>Live and Upcoming Djs</h1>

					<div id="liveUpcomingEvents" style="min-height: 500px;"></div>

					<div class="djsignup">

						<ul class="sitemap">
							<li><a id="nowLive" href="#"><span class="fa fa-chevron-left">
										Now Live!</span></a></li>
							<li><a id="mostPopular" href="#"><span class="fa fa-beer"> Most
										Popular </span></a></li>
							<li><a href="#"><span class="fa fa-heart"></span> My Djs
							</a></li>
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
				<div class="news-feed-btn"></div>
				<!--Pages-->
				
				<div>
					<span id="displayRange" class="displayRange"></span>
				</div>

			</div>
			<!-- Col artists ends -->
			
			
			
		</section>
	</div>

	<!-- Container -->

	<div class="clearfix"></div>

	<!--=================================
	Footer
	=================================-->
	<footer id="footer">
		<%@include file="footer.jsp"%>
	</footer>
	<script type="text/javascript" src="assets/js/jstz.min.js"></script>
	<script type="text/javascript" src="assets/js/allevents.js"></script>
</body>
</html>
