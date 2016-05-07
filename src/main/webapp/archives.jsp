<!DOCTYPE html>
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js">
<!--<![endif]-->
<head>
<meta charset="utf-8">
<title></title>

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
</head>
<body>

	<!--=================================
	Header
	=================================-->
	<header id="header">
		<%@include file="header.jsp"%>
	</header>


	<div class="pageContentArea">
		<!--=================================
      bread crums
      =================================-->
		<section class="breadcrumb">

			<div class="container">
				<div class="row">
					<div class="col-lg-12 col-md-12 col-sm-12">
						<h1>Archived Mixes</h1>
					</div>

				</div>
			</div>
		</section>

		<!-- ======= Rows for Archived Mixes start =======-->



		<div class="container" style="position: relative;">
			<div class="row">

				<div class="col-lg-9 col-md-9 col-sm-9 alltracks">
					<h1>tracks</h1>



					<div class="theHeaders">
					</div>
					<div class="news-feed-btn"></div>
					<!--\\artist col-->
				</div>
				
				<div class="col-lg-3 col-md-3 col-sm-3 djsignup">
							<ul class="sitemap">
								<li><a id="featured" href="#">Featured <span class="fa fa-star"></span></a></li>
								<li><a id="mostPopular" href="#">Most Popular <span
										class="caret"></span></a></li>
								<li><a id="myDjs" href="#">My Djs <span class="fa fa-heart"></span></a></li>
							</ul>

					</div>
				
				
			</div>
			<!--row-->
		</div>
		<!--container-->


	</div>
	<!--=================================
	Footer
	=================================-->
	<footer id="footer">
		<%@include file="footer.jsp"%>
	</footer>
	<script type="text/javascript" src="assets/js/archives.js">	</script>
	<script>/*Place Your Google Analytics code here*/</script>

</body>
</html>