<!DOCTYPE html>
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js">
<!--<![endif]-->
<head>
<meta charset="utf-8">
<title>Mixtri-Upload Manager</title>

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
<link rel="stylesheet" href="assets/css/uploadManager.css">

<!--<link rel="stylesheet" type="text/css" href="assets/css/blue.css">
<link rel="stylesheet" type="text/css" href="assets/css/orange.css">
<link rel="stylesheet" type="text/css" href="assets/css/red.css">
<link rel="stylesheet" type="text/css" href="assets/css/green.css">
<link rel="stylesheet" type="text/css" href="assets/css/purple.css">-->

</head>
<body>



	<div id="header">
		<%@include file="header.jsp"%>
	</div>

		<div id="modalDeleteSet" class="modal fade" role="dialog">
			<div class="modal-dialog">

				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h3 class="modal-title">Delete Remixed Set</h3>
					</div>
					<div class="modal-body" style="font-family: initial; font-size: x-large;">
						<div>Are you sure you want to delete this set?</div>
					</div>
					<div class="modal-footer">
					  <div>
					 		<button id="btnDeleteSetYes" type="button" class="btn btn-default commonButton" 
					 		style="border-width: thin; border-color: #e62948; margin: 0px;" data-dismiss="modal">Yes</button>
					 		<button id="btnDeleteSetNo" type="button" class="btn btn-default commonButton" 
					 		style="border-width: thin; border-color: #e62948; margin: 0px;" data-dismiss="modal">No</button>
					 </div> 	
					</div>
				</div>

			</div>
		</div>


	<div class="pageContentArea">
		<div class="container">
			<div class="djSignUpMsg">
				<h2 style="text-align: center; font-size: xx-large;">Upload Manager</h2>

			</div>
	  <div>
	  
	  <table class="table table-striped">
    <thead>
      <tr>
      	<th>Remixed Set</th>
        <th>Mix Title</th>
        <th>Delete</th>
      </tr>
    </thead>
    <tbody id="uploadedTracks">
    </tbody>
  </table>
	  
			
				
				
				
					
	</div>	

		</div>


	</div>
	<!--pageContent-->

	<!--=================================
	Footer
	=================================-->
	<footer id="footer">
		<%@include file="footer.jsp"%>
	</footer>
	 
	 <script src="https://apis.google.com/js/client.js?onload=checkAuth"></script>
	 <script src="assets/js/uploadmanager.js"></script>
	 
</body>
</html>
