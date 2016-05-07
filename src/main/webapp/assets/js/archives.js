(function($){
	
			$(document).ready(function(){
				var pageNumber = 1;
				getArchivedMixes('All',pageNumber);
		   
			function getArchivedMixes(filter,page){		
			
			   var previousPage=0;	
			   $.ajax({
					type:"GET",
					url:"rest/tracks",
					data: {filter:filter,
						   page:page
						   },
					dataType:'json'
					
				}).done(function(data){
					console.log(data);
					buildHtmlForTracks(data);
					var limitPerPage = 10;
					var numberOfPages = getNumberOfPages(data,limitPerPage);
					buildHtmlForPages(1,numberOfPages);
					bindPageClick();
					bindSideMenuClick();
					//JPlayer Bind function here
					bindJPlayer(data.length);
				}).fail(function(error){
					console.log("Error");
					console.log(error);
				});
				
			 }
			
		});//Document finishes
			
			
		
		//This function counts the number of tracks returned by the server and then calculates the number of pages to be displayed accordingly.	
		function getNumberOfPages(a,limitPerPage){
			var total = 0;
			var i;
			

			for (i in a) {
			    if (a.hasOwnProperty(i)) {
			    	total++;
			    }
			}
			
			var last = Math.floor(total/limitPerPage);
			
			if((total%limitPerPage) >= 0)
				last++;
			
			return last;
		}	
			
			var bindSideMenuClick=function(){
			
				$('.djsignup').on('click','a',function(e){
					e.preventDefault();
					var elementId = $(this).get(0).id;
					
					if(elementId=='featured'){
						
						$('.theHeaders').empty();
						getFilteredTracks(1,'All',elementId);
						
						
					}
					
					if(elementId=='mostPopular'){
					  $('.theHeaders').empty();	
					  getFilteredTracks(1,'popular',elementId);
					}
				     
					$('.activePage').removeClass('activePage');
					$('#1').addClass('activePage');
				});
				
				
			
			}
			
			var buildHtmlForTracks=function(tracks){
				
				var html='';
				html+='<div class="archivedTracks">';
				html+='<div class="col-lg-3 col-md-3 col-sm-3">Artist</div>';
				html+='<div class="col-lg-3 col-md-3 col-sm-3">Listen</div>';
				html+='<div class="col-lg-3 col-md-3 col-sm-3">Fan popularity <span id="caretId"></span></div>';
				html+='<div class="col-lg-3 col-md-3 col-sm-3">Title</div>';
				html+='</div>';
				
				for(var i=0;i<tracks.length;i++){
					
					html+='<section id= audio-player'+(i+1)+' class="archive-audio-player">';
					html+='<div id=player-instance'+(i+1)+' class="jp-jplayer">'+'</div>';
					html+='<div class="container-Dj-tracks">';
					html+='<div class="row">';
					html+='<div class="Dj-tracks">';
					html+='<div class="col-lg-3 col-md-3 col-sm-3"><img src='+tracks[i].artistImageSrc+'></div>';
					html+='<div class="rock-player-controls col-lg-3 col-md-3 col-sm-3"><div class="jp-play"></div><div class="jp-pause" style="display: none"></div></div>';
					html+='<div class="track_popularity col-lg-3 col-md-3 col-sm-3">'+buildPopularityHtml(tracks[i])+'</div></div>';
					html+='<div class="player-status col-lg-3 col-md-3 col-sm-3">';
					html+='<h5 class="audio-title">'+tracks[i].audioTitle+'</br><span style="font-size: smaller;color: #6a6a6a;">(by '+tracks[i].artist+') </span><span class="activeColor"></span></h5>';
					html+='<div class="audio-timer"><span class="current-time jp-current-time">00:00</span> / <span class="total-time jp-duration">'+tracks[i].totalTime+'</span></div></div>';
					html+='<div class="audio-progress"><div class="jp-seek-bar"><div class="audio-play-bar jp-play-bar" style="width: 20%;"></div></div></div>';
					html+='<div class="audio-list"><div class="jp-playlist"><ul class="mixes" id="mixes"><li data-title='+tracks[i].audioTitle+' data-artist='+tracks[i].artist+' data-mp3='+tracks[i].audioSrc+'></li>'
					html+='</div></div></div></div></section>';
                    
				
				}
				
				$('.theHeaders').append(html);
				
				
			}
			
			var buildPopularityHtml=function(track){
		
				var html='<ul>';
				var popCount=track.popularity;
				for(var i=0;i<10;i++){
					if(i<popCount)
						html+='<li class="active"></li>';
					else
						html+='<li class="inactive"></li>';
	
					
				}
				html+='</ul>';
				
				return html;
			}
			
			var bindJPlayer=function(numOfPlayers){
			
			 for(var i=1;i<=numOfPlayers;i++){
				
				 if($('#audio-player'+i).length!=0 && !($('#audio-player'+i).hasClass('jsExecuted'))){	
						$('#audio-player'+i).addClass('jsExecuted');
						$("#player-instance"+i).jPlayer({
							cssSelectorAncestor: "#audio-player"+i,
						});

						var archivedMix = [],
						$playlist_audio=$('#mixes li'),
						playlist_items_length= $playlist_audio.length;


						var  new_playlist_item = {};
						new_playlist_item['title'] = $playlist_audio.attr('data-title');
						new_playlist_item['artist'] = $playlist_audio.attr('data-artist');
						new_playlist_item['mp3'] = $playlist_audio.attr('data-mp3');
						console.log($playlist_audio.attr('data-mp3'));
						archivedMix.push(new_playlist_item);


						werock = new jPlayerPlaylist({
							jPlayer: "#player-instance"+i,
							enableRemoveControls:true,
							cssSelectorAncestor: "#audio-player"+i
						},archivedMix , {
							swfPath: "assets/jPlayer/jquery.jplayer.swf",
							supplied: "mp3",
							displayTime: 'fast',
							addTime: 'fast',
						});

						$("#player-instance"+i).bind($.jPlayer.event.play, function (event) {});		
				 }
			 }
				
		 }
			
		 var first=0;
		 var last=0;
		 
		 var buildHtmlForPages=function(firstPage,lastPage){
			
			  first=firstPage;
			  last=lastPage;
			 var html='';
			 
			 html+='<ul class="pages"><li><a href="#" id="previous"><b class="fa fa-angle-left"></b></a></li>';
			 for(var i=first;i<=last;i++){
				 
					if(i==1)				 
						html+='<li><a class="activePage" id='+i+' href="#">'+i+'</a></li>';
					else
						html+='<li><a href="#" id='+i+'>'+i+'</a></li>';
						
			 }
			 html+='<li><a href="#" id="next"><b class="fa fa-angle-right"></b></a></li></ul>';
			 
			 $('.news-feed-btn').append(html);
		 }
		 
		 var bindPageClick=function(){
			 
			 $('.news-feed-btn').on('click','a',function(e){
				 
				 e.preventDefault();
				 
				 $('.theHeaders').empty();
				
				 var page=$(this).attr('id');
				 
				 if(page === 'next'){
					 
					 var prevPage=$('.activePage').attr('id');
					 page=parseInt(prevPage)+1;
					 
					 if(page > last)
						 page=last;
					 
					 $('.pages li > a').removeClass('activePage');
					 $('#'+page).addClass('activePage');
					 
				 }else if (page ==='previous'){
					 
					 var prevPage=$('.activePage').attr('id');
					 page=parseInt(prevPage)-1;
					 
					 if(page < first)
						 page=first;
					 
					 $('.pages li > a').removeClass('activePage');
					 $('#'+page).addClass('activePage');
						 
				}else{
					
					 $('.pages li > a').removeClass('activePage');
					 $('#'+page).addClass('activePage');
					 
				 }
				 
				console.log(page);
				
				var sideElementId; 
				if($('.activeSideElement').length>0){
					sideElementId = $('.activeSideElement').get(0).id;
				}
				var filterValue;
				if(sideElementId=='mostPopular'){
					filterValue = 'popular';
				}else{
					
					filterValue = 'All';
				}
				
				getFilteredTracks(page,filterValue,sideElementId);
				 
			 });
		 }
			
		 function getFilteredTracks(page,filter,filterElementId){
			 $.ajax({
					type:"GET",
					url:"rest/tracks",
					dataType:'json',
					data:{"page":page,
						  "filter":filter
						  }
					
				}).done(function(data){
					console.log(data);
					buildHtmlForTracks(data);
					
					var alreadySelectedFilter = $('.activeSideElement');
					$(alreadySelectedFilter).removeClass('activeSideElement');
					$(alreadySelectedFilter).css('color','white');
					
					if(filterElementId=='featured'){
					  $('.activeColor').addClass('fa fa-star');
					  $('#featured').addClass('activeSideElement');
					  $('#featured').css('color','#e62948');
					}
					
					if(filterElementId=='mostPopular'){
						  $('#caretId').addClass('caret');
						  $('#mostPopular').addClass('activeSideElement');
						  $('#mostPopular').css('color','#e62948');
					}
					
					
					//JPlayer Bind function here
					bindJPlayer(data.length);
				}).fail(function(error){
					console.log("Error");
					console.log(error);
				});
		 }

		
	})(jQuery);