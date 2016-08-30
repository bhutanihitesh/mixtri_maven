<!DOCTYPE html>
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js">
<!--<![endif]-->
<head>
<meta charset="utf-8">
<title>Event</title>

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
<link rel="stylesheet" type="text/css" href="assets/css/main.css">
<link rel="stylesheet" type="text/css" href="assets/css/flipclock.css">

<!--<link rel="stylesheet" type="text/css" href="assets/css/blue.css">
<link rel="stylesheet" type="text/css" href="assets/css/orange.css">
<link rel="stylesheet" type="text/css" href="assets/css/red.css">
<link rel="stylesheet" type="text/css" href="assets/css/green.css">
<link rel="stylesheet" type="text/css" href="assets/css/purple.css">-->

<!-- <script async  src="assets/js/modernizr-2.6.2-respond-1.1.0.min.js"></script>
<script defer  src="assets/js/jquery.js"></script>
<script defer  src="assets/js/ajaxify.min.js"></script>
 -->
 

 
</head>
<body>

<!-- ===============================================
	 Facebook share
    ==================================================== -->


	<div id="fb-root"></div>
<script>(function(d, s, id) {
  var js, fjs = d.getElementsByTagName(s)[0];
  if (d.getElementById(id)) return;
  js = d.createElement(s); js.id = id;
  js.src = "//connect.facebook.net/en_US/sdk.js#xfbml=1&version=v2.5&appId=404108326453693";
  fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));</script>


	<!--=================================
	Header
	=================================-->
	<div id="header">
		<%@include file="header.jsp"%>
	</div>

	<div id="ajaxArea">
		<div class="pageContentArea">
		
		<!-- Icecast Setting Modal -->	
<div class="modal fade" id="modalIcecastSettings" role="dialog">
    <div class="modal-dialog">
    
    <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">Your Icecast Settings!</h4>
        </div>
        <div class="modal-body">
        
        <ul class="nav nav-tabs" id="tabContent">
        	<li class="active"><a href="#Traktor" data-toggle="tab">Traktor</a></li>
        	<li><a href="#VDJ-7" data-toggle="tab">VDJ-7</a></li>
        	<li><a href="#VDJ-8" data-toggle="tab">VDJ-8</a></li>
		</ul>
		
		<div class="tab-content">
        <div class="tab-pane active" id="Traktor">
        
            Tracktor
      </div>
        
        <div class="tab-pane" id="VDJ-7">
        VDJ-7
        </div> 


        <div class="tab-pane" id="VDJ-8">
        VDJ-8
       </div>   
        
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default eventModalButton" data-dismiss="modal" style="border-color: #e62948;">Close</button>
        </div>
      </div>
      
    </div>
  </div>
</div>
		
		<!-- Modal -->
  <div class="modal fade" id="eventSetUpModal" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title" style="color: #e62948;font-size: x-large;font-family: 'Oswald', sans-serif;">Woohoo! Your live streaming event has been setup!</h4>
        </div>
        <div class="modal-body">
        
        <h4>Want your event to be a big hit?</h4><br>
          <ol>
          
          		<li>Set up your event well advance in time. Your event will not start unless you are there to start it. You can join your event 30 mins before the 
          		   start of the event and do your settings.
          		</li><br>
          		<li>Click <a href="#">here</a> to view step-by-step guide of your setting</li><br>
          		<li>Make it big. Share your event on facebook and invite your friends and fans.</li><br>
           </ol>
          
           <button id="btn-fbShare" style="margin-bottom: 10px; margin-left:200px;" type="button" class="btn btn-primary">
					<i class="fa fa-facebook"></i> Facebook
			</button>
			
			<div id="liveStreamURL"></div>
          
        
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default eventModalButton" data-dismiss="modal" style="border-color: #e62948;">Got It!</button>
        </div>
      </div>
      
    </div>
  </div>
  
</div>
			<section id="latest-events">
				<div class="container">
					<div class="row">
						<div class="col-lg-4 col-md-4 col-sm-5">
							<div class="event-feed latest">
								<img id="eventPic" src="" alt="">
								<div class="date">
									<span class="day" id="spanDay"></span> <span class="month" id="spanMonth"></span>
								</div>
								<h5><span class="djName"></span> Spinning live</h5>
								<ul>
									<li><b class=" fa fa-location-arrow"></b>AT CLUB MIXTRI</li>
									<li><b class=" fa fa-clock-o"></b><span id="listTime"></span></li>

								</ul>
							</div>
							<!--\\event-feed latest-->

						</div>
						<!--//col-->
						<div class="col-lg-4 col-md-4 col-sm-4">
							<h1>Event Information</h1>
						</div>
						<div class="col-lg-4 col-md-4 col-sm-4">
							<div class="text-right">
								<button id="btnJoin1" type="submit" class="btn btn-default commonButton"
									style="width: 200px;">
									<span class="fa fa-sign-in"></span> Join
								</button>
							</div>
						</div>
						<div class="col-lg-8 col-md-8 col-sm-8">
							<div class="event-info">
								<h5 class="text-right">Entry Opens 30 mins before the event starts!</h5>
								<h4 id="djName"></h4>
								<div id="streamInfo"></div>
								<div>
									<span style="color: graytext;">Entry starts</span> | 
									   <b class="fa fa-calendar" style="color: #e62948"></b>
									    <span  id="spanDate" style="color: graytext;">
									    </span> | 
										<span style="color: graytext;"><b class=" fa fa-clock-o" style="color: #e62948"></b>
											<span id="spanTime"></span> 
										</span> | 
										<span style="color: graytext;"><b class=" fa fa-location-arrow" style="color: #e62948"> </b> 
										At Club Mixtri 
										</span>


								</div><br>
								<div>
								
									<!-- <b id="share_button" class="fa fa-facebook-square" style="color: #375a7f; font-size:x-large; cursor: pointer;"></b> -->
									<i id="share_button" class="fa fa-facebook-square" style="color: #268EE7; font-size:x-large; cursor: pointer;"></i>
									Get Popular! Share your live event on facebook and showcase your talent to the world. 
								</div>

							</div>
							<!--//event-info-->

							<div class="row">
								<div class="col-lg-12 col-md-12 col-sm-12">
									<div id="djName">
										<h3>TIME LEFT FOR <span class="djName" style=" text-transform: uppercase;"></span> TO SPIN LIVE @CLUB MIXTRI</h3>
									</div>
									<br>
									<div class="event-countdown-clock" style="margin: 2em;"></div><br>
									<div>
										<h5>
											Get your booze ready and spin your head with <span class="djName" style="color: #e62948;"></span> 
											live <span style="color: #e62948">@Club Mixtri</span> , where party never ends!
										</h5>

									</div><br>

								</div>
							</div>
							<!-- event-countdown clock ends -->

						</div>

						<!--//col-->
						<!-- <div class="col-lg-4 col-md-4 col-sm-4">
						</div>
						<div class="col-lg-8 col-md-8 col-sm-8">
							<h5>Live Stream Manager</h5>
						</div> -->

					</div>
					<!--row-->
					<div class="row">
						<div class="col-lg-4 col-md-4 col-sm-4">
						<button id="btnIcecastSettings" class="btn btn-default commonButton hidden" data-toggle="modal" data-target="#modalIcecastSettings" style="width: 400px;">
							 View your Icecast Setting!
						</button>
						</div>
						<div class="col-lg-4 col-md-4 col-sm-4">
							<div class="text-center">
						<button id="btnJoin2" type="submit" class="btn btn-default commonButton" style="width: 200px;">
							<span class="fa fa-sign-in"></span> Join
						</button>
					</div>
						</div>
						
						<div id="manageLiveStreamLinks" class="col-lg-3 col-md-3 col-sm-3">
							<h4>Live Stream Manager</h4>
							<div class="underline-border"></div>
						<div class="eventPageManageLinks">
							<ul>
								<li><a href="#" class="eventPageManageLinks">Edit your live stream</a></li>
								<li><a href="#" class="eventPageManageLinks">Cancel your live stream</a></li>
								<li><a href="#" class="eventPageManageLinks">Complete your profile</a></li>
							</ul>
						  </div>
						</div>
						
					</div>


				</div>
				<!--//container-->
			</section>
		</div>
		<!--pageContent-->
	
	<!--ajaxwrap-->

	<!--=================================
	Footer
	=================================-->
	<footer id="footer">
		<%@include file="footer.jsp"%>
	</footer>
	<script>
	/*Place Your Google Analytics code here*/
</script>
	<!-- <script src="assets/js/jquery-1.11.1.min.js"></script> -->
	<script defer="defer" type="text/javascript" src="assets/js/flipclock.min.js"></script>
	<script defer="defer" type="text/javascript" src="assets/js/event.js"> </script>

</body>
</html>
