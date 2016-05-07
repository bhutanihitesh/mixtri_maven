$(document).ready(function() {
	
	//Global Variable
	var uploadedSetId;
	
	$.ajax({
		url: '/mixtri/rest/pastmixes',
		type: 'GET',
		data: {
			emailId: $.cookie("emailId")
		},
		dataType: 'json',
		
		success: function(result){
			
			buildUploadManagerTracksTable(result);
			
	    },
	    error: function(result){
	      window.location.href = "error.jsp";
	    }
		
	});
	
	
	function buildUploadManagerTracksTable(result){
		var html=''
		var listUploadedTracks = result.listUploadedTracks;	
		for(var iCount=0;iCount<listUploadedTracks.length;iCount++){
			
		 html+='<tr><td><audio controls preload="metadata"><source src="'+result.path+listUploadedTracks[iCount].id+".mp3"+'" type="audio/mpeg"></audio></td>';
		 html+='<td>'+listUploadedTracks[iCount].audioTitle+'</td>';
		 html+='<td><i class="fa fa-trash-o deleteSet" id="'+listUploadedTracks[iCount].id+'"></i></td>'
	     html+='</tr>';
		}
		
		$('#uploadedTracks').append(html);
	
		//Binding on click function with the deleteSet class
		$('.deleteSet').on('click',function(e){
			
			//Save the id of the track which user has selected to be deleted in a global varible
			uploadedSetId = this.id;
			$('#modalDeleteSet').modal('show');
		});
	}
	
	$('#btnDeleteSetYes').on('click',function(e){
		
		$.ajax({
			url: '/mixtri/rest/deleteUploadedTrack',
			type: 'POST',
			data: {
				uploadedSetId: uploadedSetId
			},
			success: function(result){
				
				window.location.reload();
				
		    },
		    error: function(result){
		      window.location.href = "error.jsp";
		    }
			
		});
		
		
	});
	
	
});