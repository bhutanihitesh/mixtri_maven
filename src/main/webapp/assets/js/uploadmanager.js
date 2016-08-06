$(document).ready(function() {
	
	//Global Variable
	var globalUploadedSetId;
	
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
			
		 html+='<tr><td><audio controls preload="metadata"><source src="'+listUploadedTracks[iCount].audioSrc+'" type="audio/mpeg"></audio></td>';
		 html+='<td>'+listUploadedTracks[iCount].audioTitle+'</td>';
		 html+='<td><i class="fa fa-trash-o deleteSet" id="'+listUploadedTracks[iCount].googleDriveFileId+'"></i></td>'
	     html+='</tr>';
		}
		
		$('#uploadedTracks').append(html);
	
		//Binding on click function with the deleteSet class
		$('.deleteSet').on('click',function(e){
			
			//Save the id of the track which user has selected to be deleted in a global varible
			globalUploadedSetId = this.id;
			$('#modalDeleteSet').modal('show');
		});
	}
	
	
 $('#btnDeleteSetYes').on('click',function(e){
		
		$.ajax({
			url: '/mixtri/rest/deleteUploadedTrack',
			type: 'POST',
			data: {
				uploadedSetId: globalUploadedSetId
			},
			success: function(result){
				
				getAccessToken();
				
				
		    },
		    error: function(result){
		      window.location.href = "error.jsp";
		    }
			
		});
		
		
	});
	
	
	function getAccessToken() {

		$.ajax({

			type: 'GET',
			url: '/mixtri/rest/profile/getToken',
			success: function(result){

				//Setting access token in global variable.
				ACCESS_TOKEN = result.accessToken;
				setAccessToken();
				
			},

			error: function(result){

				console.log('Error recieving access token '+result);
				window.location.href = "error.jsp";	
			}

		});


	}

	
	/** 
	 * Set access token retrieved on server side. This replaces client side explicit
	 * authentication through authentication dialog.
	 */
	function setAccessToken() { 
	
	 gapi.auth.setToken({
		    access_token: ACCESS_TOKEN
		});	
	 
		gapi.client.load('drive', 'v2', onDriveClientLoaded);
	}
	
	 
	/** After the drive api has loaded.. */
	function onDriveClientLoaded() {
		
		deleteFile();
	}
	
	/**
	 * Permanently delete a file, skipping the trash.
	 *
	 * @param {String} fileId ID of the file to delete.
	 */
	function deleteFile() {
		
	  var request = gapi.client.drive.files.delete({
	    'fileId': globalUploadedSetId
	  });
	  
	  request.execute(function(resp) {
		  
		  if(resp.code!=404){
			  
			  console.log('Delete SuccessFul'); 
			  
			  window.location.reload();
			  
		  }else{
			 
			  console.log('Not Successful: '+resp.message);
		  }
		  
	  });
	}
	
	
});