<!DOCTYPE html>
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js">
<!--<![endif]-->
<head>
<meta charset="utf-8">
<title>About Us</title>

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
 -->
</head>
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
      Home
      =================================-->
      <section id="home-slider">
        <div class="container">
          <div class="home-inner">

            <div id="homeSliderNav" class="slider-nav"> 
              <a id="home-prev" href="#" class="prev fa fa-chevron-left"></a>
              <a id="home-next" href="#" class="next fa fa-chevron-right"></a>
            </div><!--sliderNav-->
            
            <div id="flex-home" class="flexslider" data-animation="slide" data-animationSpeed="1000" data-autoPlay="true" data-slideshowSpeed="3000">
              <ul class="slides">
                <li> <img src="assets/img/slider/slider.jpg" alt="">
                  <div class="flex-caption">
                    <h2>Live Djs from India around the world</h2>
                  </div>
                </li>
                <li> <img src="assets/img/slider/slider-1.jpg" alt="" >
                  <div class="flex-caption">
                    <h2>Never ending music festival</h2>
                  </div>
                </li>
                <li> <img src="assets/img/slider/slider-2.jpg" alt="" >
                  <div class="flex-caption">
                    <h2>24*7 Bollywood, EDM, Trance and much more</h2>
                  </div>
                </li>
                <li> <img src="assets/img/slider/slider-3.jpg" alt="" >
                  <div class="flex-caption">
                    <h2>Party at your house</h2>
                  </div>
                </li>
                <li> <img src="assets/img/slider/slider-4.jpg" alt="" >
                  <div class="flex-caption">
                    <h2>Play live showcase your talent</h2>
                  </div>
                </li>
              </ul>
            </div>
          </div>
        </div>
      </section>

    </div><!--pageContent-->
</div><!--ajaxwrap-->

<!-- Content -->
	<section id="aboutus">
		<div class="container">
			<div class="our-aim" align="center">
				<h4>
				   
				    Mixtri is a 24*7 live music festival where Djs from India and
					around the world spin live for their fans.
					
				</h4>
			</div>
		<!--============== About us row starts ================== -->	
			<div class="row">
				<div class="col-lg-6 col-md-6 col-sm-6">
					<div class="aboutus">
						<img width= 500px height= 500px src="assets/img/aboutUs/phone1.jpg" alt="dummy">
					</div>
				</div>	
			
                    <div id="aboutus" class="col-lg-6 col-md-6 col-sm-6">
						<div class="aboutus">
							<h1>About Mixtri</h1><br><br>
							<p>
							    India's first 24*7 Live streaming platform for the Djs.
							</p>
							<p>
							   Mitri is platform where Djs from India and around the world can showcase thier 
							   talent to thier fans anywhere in the world by live streaming high-quality audio
							   and video right from their laptops and Dj consoles using our robust and 
							   easy-to-use technology without any external downloads or cords ....FREE.
							</p>
							<p>
							    Founded in 2015, Mixtri aims to bring the best Djs closer to thier fans. Just plug
							    in your device to speakers get on your party shoes and starting jumping to the 
							    beats of the best Djs in the world.
							    
							</p>
							<p>
							    <font color="#e22847" size="5">
							      <i>
							        YOU NEVER KNOW A WORLD FAMOUS DJ IS PLAYING LIVE AT YOUR HOUSE PARTY.
							     </i> 
							    </font>
							</p>
						</div>
						<!--\\latest news-->
					</div>

				</div>
				<!-- ========================About us row Ends===================== -->
                <!-- ======================= From the Djs========================== -->
            <h1>From the DJs</h1>
            <div class="aboutus-container">
			<div class="row">
				<div class="col-lg-6 col-md-6 col-sm-6">
					<div class="aboutus">
						<img src="assets/img/news/1.jpg" alt="dummy">
						<div class="Dj-name">Dj Lemon</div>
						<p>" Mixtri is a unique platform to connect to my fans all over the world. This is a very unique concept of performing live all over the world and 
						   throwing your impression."</p>
					</div>
				</div>
				<!--\\latest news-->
				<div class="col-lg-6 col-md-6 col-sm-6">
					<div class="aboutus">
						<img src="assets/img/news/1.jpg" alt="dummy">
						<div class="Dj-name">Dj Molly</div>
						<p>" Mixtri is a unique platform to connect to my fans all over the world. This is a very unique concept of performing live all over the world and 
						   throwing your impression."</p>
					</div>
				</div>
			</div>
			<!--\\row ends-->
			<div class="row">
				<div class="col-lg-6 col-md-6 col-sm-6">
					<div class="aboutus">
						<img src="assets/img/news/1.jpg" alt="dummy">
						<div class="Dj-name">Dj Aqeel</div>
						<p>" Mixtri is a unique platform to connect to my fans all over the world. This is a very unique concept of performing live all over the world and 
						   throwing your impression."</p>
					</div>
				</div>
				<!--\\latest news-->
				<div class="col-lg-6 col-md-6 col-sm-6">
					<div class="aboutus">
						<img src="assets/img/news/1.jpg" alt="dummy">
						<div class="Dj-name">Dj Suketu</div>
						<p>" Mixtri is a unique platform to connect to my fans all over the world. This is a very unique concept of performing live all over the world and 
						   throwing your impression."</p>
					</div>
				</div>
			</div>
				<div class="row">	
				<div class="col-lg-6 col-md-6 col-sm-6">
					<div class="aboutus">
						<img src="assets/img/news/1.jpg" alt="dummy">
						<div class="Dj-name">Tiesto</div>
						<p>" Mixtri is a unique platform to connect to my fans all over the world. This is a very unique concept of performing live all over the world and 
						   throwing your impression."</p>
					</div>
				</div>
				<!--\\latest news-->
				<div class="col-lg-6 col-md-6 col-sm-6">
					<div class="aboutus">
						<img src="assets/img/news/1.jpg" alt="dummy">
						<div class="Dj-name">Dj Paddy</div>
						<p>" Mixtri is a unique platform to connect to my fans all over the world. This is a very unique concept of performing live all over the world and 
						   throwing your impression."</p>
					</div>
				</div>
				</div>
				</div>

	

		</div>
	</section>
	<!--=================================
	Footer
	=================================-->
<footer id="footer">
<%@include file="footer.jsp" %>
</footer>
<script>/*Place Your Google Analytics code here*/</script>

</body>
</html>