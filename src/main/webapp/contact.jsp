<!DOCTYPE html>
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js">
<!--<![endif]-->
<head>
<meta charset="utf-8">
<title>WeRock</title>

<!--=================================
Meta tags
=================================-->
<meta name="description" content="">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta name="viewport" content="minimum-scale=1.0, width=device-width, maximum-scale=1, user-scalable=no" />

<!--=================================
Style Sheets
=================================-->
<link href='http://fonts.googleapis.com/css?family=Oswald:400,700,300' rel='stylesheet' type='text/css'>
<link href='http://fonts.googleapis.com/css?family=Roboto:400,400italic,700' rel='stylesheet' type='text/css'>
<link rel="stylesheet" type="text/css" href="assets/css/bootstrap.min.css">

<link rel="stylesheet" href="assets/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="assets/css/flexslider.css">
<link rel="stylesheet" type="text/css" href="assets/css/prettyPhoto.css">
<link rel="stylesheet" type="text/css" href="assets/css/jquery.vegas.css">
<link rel="stylesheet" type="text/css" href="assets/css/jquery.mCustomScrollbar.css">
<link rel="stylesheet" href="assets/css/main.css">

<!--<link rel="stylesheet" type="text/css" href="assets/css/blue.css">
<link rel="stylesheet" type="text/css" href="assets/css/orange.css">
<link rel="stylesheet" type="text/css" href="assets/css/red.css">
<link rel="stylesheet" type="text/css" href="assets/css/green.css">
<link rel="stylesheet" type="text/css" href="assets/css/purple.css">-->

<!-- <script async  src="assets/js/modernizr-2.6.2-respond-1.1.0.min.js"></script>
<script defer  src="assets/js/jquery.js"></script>
<script defer  src="assets/js/ajaxify.min.js"></script>
 --></head>
<body>


<!--=================================
	Header
	=================================-->
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
      videos
      =================================-->
      
      <section id="contact">
          <div class="container">
              <div class="row">
                  <div class="col-lg-9 col-md-9 col-sm-9">
                      <div id="google-map" class="xv-gmap contact-map" data-theme="pink" data-address="kansas city" data-zoomlvl="13" data-maptype="HYBRID"></div>
                      <form id="contactform" action="assets/php/submit.php" method="post">
                          <div class="row">
                              <div class="col-lg-5 col-md-5 col-sm-5">
                                  <h5>Your Name:</h5>
                                  <input type="text" id="name" />
                              </div>
                              <div class="col-lg-5 col-md-5 col-sm-5">
                                  <h5>Your Email:</h5>
                                   <input type="text" name="email" id="email" />
                              </div>
                          </div>
                          <div class="row">
                              <div class="col-lg-12 col-md-12 col-sm-12">
                                  <h5>Your Message:</h5>
                                  <textarea name="message" id="message"></textarea>
                              </div>
                          </div>
                          <button id="submit1" type="submit">Submit</button>
                      </form>
                      <div id="valid-issue" style="display:none;"> Please Provide Valid Information</div>   
                  </div>
                  
                  <div class="col-lg-3 col-md-3 col-sm-3">
                      <h3>Head Office</h3>
                      <p>1814 NE Miami Gardens Drive, 
      Suite #1007 North Miami Beach, 
      FL, 33179</p>
                      <i class="fa fa-mobile-phone"></i>
                      <p>1.800.987.7890</p>
                      <b class=" fa fa-envelope"></b>
                      <p>info@visionmax.com</p>
                      <div class="booking-info">
                          <h3>booking</h3>
                          <i class="fa fa-mobile-phone"></i>
                          <p>1.800.987.7890</p>
                          <b class=" fa fa-envelope"></b>
                          <p>info@visionmax.com</p>
                      </div>
                      <h3>manager</h3>
                      <i class="fa fa-mobile-phone"></i>
                      <p>1.800.987.7890</p>
                      <b class=" fa fa-envelope"></b>
                      <p>info@visionmax.com</p>
                  </div>
              </div><!--row-->
          </div><!--//container-->  
      </section>
	</div><!--pageContent-->
</div><!--ajaxwrap-->
<!--=================================
	Footer
	=================================-->
<footer id="footer">
<%@include file="footer.jsp" %>
</footer>
</body>
</html>
