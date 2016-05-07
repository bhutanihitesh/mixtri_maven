<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8" />
    <title>Sample Testing page</title>
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
 <%-- <div id="header">
<%@include file="header.jsp" %>
</div>  --%>  
 
 <form id="loginform1" class="form-horizontal" role="form">
<p><b>Please enter</b> your city here to see it work. <input class="form-control ff_elem" type="text" name="ff_nm_from[]" value="" id="f_elem_city"/>
</form>
    
<script type="text/javascript">
 
jQuery(function () 
 {
	 jQuery("#f_elem_city").autocomplete({
		source: function (request, response) {
		 jQuery.getJSON(
			"http://gd.geobytes.com/AutoCompleteCity?callback=?&q="+request.term,
			function (data) {
			 response(data);
			}
		 );
		},
		minLength: 3,
		select: function (event, ui) {
		 var selectedObj = ui.item;
		 jQuery("#f_elem_city").val(selectedObj.value);
		getcitydetails(selectedObj.value);
		 return false;
		},
		open: function () {
		 jQuery(this).removeClass("ui-corner-all").addClass("ui-corner-top");
		},
		close: function () {
		 jQuery(this).removeClass("ui-corner-top").addClass("ui-corner-all");
		}
	 });
	 jQuery("#f_elem_city").autocomplete("option", "delay", 100);
	});
</script>

</body>
</html>