<!DOCTYPE html>
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js">
<!--<![endif]-->
<head>
<meta charset="utf-8">
<title>Artist Details</title>

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

<!--=================================
JPlayer (Audio Player)
=================================-->

<section id="audio-player">
  <div class="container">
    <div class="rock-player">
      <div class="row">
        <div class="col-lg-7 col-md-7 col-sm-12 col-xs-12">
          <div class="row">
            <div class="col-lg-4 col-md-4 col-sm-4 col-xs-12">
              <div id="player-instance" class="jp-jplayer"></div>
              <div class="controls">
                <div class="jp-prev"></div>
                <div  class="play-pause jp-play"></div>
                <div  class="play-pause jp-pause" style="display:none"></div>
                <div class="jp-next"></div>
              </div>
              <!--controls--> 
            </div>
            <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
              <div class="player-status">
                <h5 class="audio-title">Maroon 5 - Moves Like Jagger ft. Christina Aguilera</h5>
                <div class="audio-timer"><span class="current-time jp-current-time">01:02</span> / <span class="total-time jp-duration">4:05</span></div>
                <div class="audio-progress">
                  <div class="jp-seek-bar">
                    <div class="audio-play-bar jp-play-bar" style="width:20%;"></div>
                  </div>
                  <!--jp-seek-bar--> 
                </div>
                <!--audio-progress--> 
              </div>
              <!--player-status--> 
            </div>
            <!--column--> 
          </div>
          <!--inner-row--> 
        </div>
        <!--column-->
        
        <div class="col-lg-5 col-md-5 col-sm-12 col-xs-12">
          <div class="audio-list">
            <div class="audio-list-icon"></div>
            <div class="jp-playlist"> 
              <!--Add Songs In mp3 formate here-->
              <ul class="hidden playlist-files">
                <li data-title="Maroon 5 - Moves Like Jagger" 
                                    	data-artist="ft. Christina" 
                                        data-mp3="http://www.jplayer.org/audio/mp3/TSP-01-Cro_magnon_man.mp3"></li>
                <li data-title="Coldplay - Princess Of China" 
                                    	data-artist="ft. Rihanna" 
                                        data-mp3="http://www.jplayer.org/audio/mp3/TSP-01-Cro_magnon_man.mp3"></li>
                <li data-title="Lorem Ipsum" 
                                    	data-artist="ft. Rocker" 
                                        data-mp3="http://www.jplayer.org/audio/mp3/TSP-01-Cro_magnon_man.mp3"></li>
                <li data-title="New Fish!!!" 
                                    	data-artist="ft. Jailer" 
                                        data-mp3="http://www.jplayer.org/audio/mp3/TSP-01-Cro_magnon_man.mp3"></li>
                <li data-title="I wanna grow" 
                                    	data-artist="ft. MeDon" 
                                        data-mp3="http://www.jplayer.org/audio/mp3/TSP-01-Cro_magnon_man.mp3"></li>
                <li data-title="do be do" 
                                    	data-artist="ft. scoby" 
                                        data-mp3="http://www.jplayer.org/audio/mp3/TSP-01-Cro_magnon_man.mp3"></li>
                <li data-title="We aint smart" 
                                    	data-artist="ft. SomeFool" 
                                        data-mp3="http://www.jplayer.org/audio/mp3/TSP-01-Cro_magnon_man.mp3"></li>
              </ul>
              <!--Playlist ends-->
              <h5>Audio Playlist</h5>
              <div class="audio-track">
                <ul>
                  <li></li>
                </ul>
              </div>
            </div>
          </div>
        </div>
      </div>
      <!--row--> 
    </div>
  </div>
</section>

<div id="ajaxArea">
    <div class="pageContentArea">
      <!--=================================
      bread crums
      =================================-->
        <section class="breadcrumb">
              
             <div class="container">
                  <div class="row">
                      <div class="col-lg-6 col-md-6 col-sm-6">
                          <h1>artist</h1>
                          <h5>detail of artist</h5>
                      </div>
                      
                      <div class="col-lg-6 col-md-6 col-sm-6">
                          <ul>
                              <li><a href="#">Home</a></li>
                              <li><a href="#">artists</a></li>
                              <li><a href="#">Detail</a></li>
                          </ul>
                      </div>
                  </div>
             </div>
        </section>
      <div class="clearfix"></div>
      
      
      <!--=================================
      videos
      =================================-->
      
      <section id="artists">
          <div class="container">
              <div class="row">
                  <div class="artist-detail">
                      <div class="artist">
                          <div class="col-lg-4 col-md-4 col-sm-4">
                          	<figure class="artistFace">
                              <img src="assets/img/artist/artist-detail.jpg" alt=""/>
                             </figure> 
                          </div>
                           <div class="col-lg-8 col-md-8 col-sm-8">
                              <div class="artist-detail-content">
                                  <h3>enrique iglesias</h3>
                                  <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis sed nibh pharetra, congue lectus at, malesuada augue. Donec tincidunt faucibus lacus. Curabitur vitae euismod lectus. Vestibulum sagittis scelerisque tincidunt. Aliquam sit amet arcu felis. Ut posuere augue vitae velit rhoncus sodales. Vestibulum eleifend accumsan ligula ut ornare. Pellentesque iaculis sed mi dapibus volutpat. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Cras vel nisi a neque euismod egestas id a massa. Maecenas vel est dolor.</p>
                                  <p>Pellentesque cursus sit amet arcu a adipiscing. Curabitur feugiat enim id nibh facilisis tincidunt. Mauris sodales metus sit amet nisi laoreet hendrerit. Pellentesque consequat turpis et metus aliquet, at scelerisque ante bibendum. Duis condimentum quam at quam facilisis, eu sagittis tortor mattis. Aenean consequat iaculis odio id porta. Aenean lacus risus, varius eu orci nec, fringilla suscipit augue. Nunc porttitor lobortis ipsum, ut vestibulum nunc accumsan vel. Vestibulum sit amet tempor libero. Praesent ac arcu vitae lectus fermentum auctor. Pellentesque tristique ut neque non porta. Sed venenatis lacus ac lacus lobortis facilisis id sit amet lectus. Integer id metus sit amet tellus rhoncus sodales eget sit amet nisl.</p>
                                  <p>Suspendisse sed urna condimentum, posuere orci et, mattis velit. Pellentesque in facilisis sem. Quisque nibh dolor, semper ac dictum in, dignissim a nisl. Phasellus vel sapien ut odio dapibus tincidunt. Suspendisse eu aliquam urna, id porttitor risus. Mauris ligula dui, elementum at enim in, blandit hendrerit nunc. Ut facilisis pulvinar lacinia. Curabitur at hendrerit libero. Fusce ornare tellus luctus lectus rutrum tempor. In tellus tellus, facilisis quis suscipit vel, scelerisque in sem.</p>
                              </div><!--//artist-detail-content-->
                              
                              <div class="artist-tracks">
                                  <h1>latest tracks</h1>
                                  <div class="alreadyAdded-warning alert-theme alert">
                                    	<div class="row">
                                        	<div class="col-xs-12 col-sm-8">
                                            	This song is already in the playlist, Whould you like to add it anyway?
                                            </div>
                                            <div class="col-xs-12 col-sm-4 text-right">
                                            	<button class="btn addAgain">Yes</button><button class="btn dontAdd">No</button>
                                            </div>
                                        </div>
                                   </div>     
                                 <div class="track clearfix">
                                      <div class="track_title">hero</div>
                                      <div class="track_release">release date:<span> 2001</span></div>
                                      <div class="track_listen">
                                          <span  data-title="Maroon 5 - Moves Like Jagger" data-artist="ft. Christina" data-mp3="http://www.jplayer.org/audio/mp3/TSP-01-Cro_magnon_man.mp3" title="add to playlist"> <i class="fa fa-play"></i></span>
                                       </div>
                                      <div class="track_buy"><a href="#"><i class="fa fa-shopping-cart"></i>buy it on itunes</a></div>
                                  </div><!--track-->
                                  
                                 <div class="track clearfix">
                                      <div class="track_title">I Like It </div>
                                      <div class="track_release">release date:<span> 2010</span></div>
                                      <div class="track_listen">
                                          <span  data-title="Maroon 5 - Moves Like Jagger" data-artist="ft. Christina" data-mp3="http://www.jplayer.org/audio/mp3/TSP-01-Cro_magnon_man.mp3" title="add to playlist"> <i class="fa fa-play"></i></span>
                                       </div>
                                      <div class="track_buy"><a href="#"><i class="fa fa-shopping-cart"></i>buy it on itunes</a></div>
                                  </div><!--track-->
                                  
                                  
                                  <div class="track clearfix">
                                      <div class="track_title">Dirty Dancer</div>
                                      <div class="track_release">release date:<span> 2011</span></div>
                                      <div class="track_listen">
                                          <span  data-title="Maroon 5 - Moves Like Jagger" data-artist="ft. Christina" data-mp3="http://www.jplayer.org/audio/mp3/TSP-01-Cro_magnon_man.mp3" title="add to playlist"> <i class="fa fa-play"></i></span>
                                       </div>
                                      <div class="track_buy"><a href="#"><i class="fa fa-shopping-cart"></i>buy it on itunes</a></div>
                                  </div><!--track-->
                                  
                                  <div class="track clearfix">
                                      <div class="track_title">Escape</div>
                                      <div class="track_release">release date:<span> 2007</span></div>
                                      <div class="track_listen">
                                         <span  data-title="Maroon 5 - Moves Like Jagger" data-artist="ft. Christina" data-mp3="http://www.jplayer.org/audio/mp3/TSP-01-Cro_magnon_man.mp3" title="add to playlist"> <i class="fa fa-play"></i></span>
                                       </div>
                                      <div class="track_buy"><a href="#"><i class="fa fa-shopping-cart"></i>buy it on itunes</a></div>
                                  </div><!--track-->
                                  
                              </div><!--artist tracks-->
                          </div>
                      </div><!--\\artist-->
                  </div><!--//artist detail-->
              </div><!--row-->
          </div><!--//container-->  
      </section>
	</div><!--pageContent-->
</div><!--ajaxwrap-->    
<!--=================================
	Latest blog
	=================================-->
<section id="latest-blog">
  <div class="container">
    <div class="row">
      <h1>latest from the blog</h1>
      <div class="col-lg-4 col-md-4 col-sm-4">
        <article class="blog-post"> <img src="assets/img/blog/1.jpg" alt=""/>
          <div class="hover"> <a href="blog-detail.jsp">
            <h2>Miguel to Muse: the week in music</h2>
            <p>'I use uncertainty as motivation and hopefully Rob uses it as motivation as...</p>
            <ul>
              <li>Admin</li>
              <li>5 hours ago</li>
              <li>2 Comments</li>
            </ul>
            </a> </div>
        </article>
      </div>
      <!--\\blog-post-->
      
      <div class="col-lg-4 col-md-4 col-sm-4">
        <article class="blog-post"> <img src="assets/img/blog/2.jpg" alt=""/>
          <div class="hover"> <a href="blog-detail.jsp">
            <h2>Kenny Chesney kicks off Austin’s country music</h2>
            <p>'I use uncertainty as motivation and hopefully Rob uses it as motivation as...</p>
            <ul>
              <li>Admin</li>
              <li>5 hours ago</li>
              <li>2 Comments</li>
            </ul>
            </a> </div>
        </article>
      </div>
      <!--\\blog-post-->
      
      <div class="col-lg-4 col-md-4 col-sm-4">
        <article class="blog-post"> <img src="assets/img/blog/3.jpg" alt=""/>
          <div class="hover"> <a href="blog-detail.jsp">
            <h2>CHRISTIAN REY - LIVE MUSIC</h2>
            <p>'I use uncertainty as motivation and hopefully Rob uses it as motivation as...</p>
            <ul>
              <li>Admin</li>
              <li>5 hours ago</li>
              <li>2 Comments</li>
            </ul>
            </a> </div>
        </article>
      </div>
      <!--\\blog-post--> 
      
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
