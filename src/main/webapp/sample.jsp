<html>
  <head>
    <script type="text/javascript">
      // Your Client ID can be retrieved from your project in the Google
      // Developer Console, https://console.developers.google.com
      var CLIENT_ID = '676570655548-ou0hs4elvqvqh4m1gv8os4s186us4v5l.apps.googleusercontent.com';

      //Changed scope from drive.metadata.readonly to drive.file
      //var SCOPES = ['https://www.googleapis.com/auth/drive.metadata.readonly'];
      
       var SCOPES = ['https://www.googleapis.com/auth/drive.file'];

      /**
       * Check if current user has authorized this application.
       */
      function checkAuth() {
          
        gapi.auth.authorize(
          {
            'client_id': CLIENT_ID,
            'scope': SCOPES.join(' '),
            'immediate': true
          }, handleAuthResult);
       
      }

      /**
       * Handle response from authorization server.
       *
       * @param {Object} authResult Authorization result.
       */
      function handleAuthResult(authResult) {
        var authorizeDiv = document.getElementById('authorize-div');
        if (authResult && !authResult.error) {
          // Hide auth UI, then load client library.
          authorizeDiv.style.display = 'none';
          loadDriveApi();
        } else {
          // Show auth UI, allowing the user to initiate authorization by
          // clicking authorize button.
          authorizeDiv.style.display = 'inline';
        }
      }

      /**
       * Initiate auth flow in response to user clicking authorize button.
       *
       * @param {Event} event Button click event.
       */
      function handleAuthClick(event) {
        gapi.auth.authorize(
          {client_id: CLIENT_ID, scope: SCOPES, immediate: false},
          handleAuthResult);
        return false;
      }

      /**
       * Load Drive API client library.
       */
      function loadDriveApi() {
       gapi.client.load('drive', 'v3', insertFile);
        //gapi.client.load('drive','v3',myCustomInsert);
        //gapi.load('drive-share', init);
      }

      /**
       * Print files.
       */
      function listFiles() {
        var request = gapi.client.drive.files.list({
            'pageSize': 10,
            'fields': "nextPageToken, files(id, name)"
          });

          request.execute(function(resp) {
            appendPre('Files:');
            var files = resp.files;
            if (files && files.length > 0) {
              for (var i = 0; i < files.length; i++) {
                var file = files[i];
                appendPre(file.name + ' (' + file.id + ')');
              }
            } else {
              appendPre('No files found.');
            }
          });
      }

      /**
       * Append a pre element to the body containing the given message
       * as its text node.
       *
       * @param {string} message Text to be placed in pre element.
       */
      function appendPre(message) {
        var pre = document.getElementById('output');
        var textContent = document.createTextNode(message + '\n');
        pre.appendChild(textContent);
      }
       
       
       /**
        * Start the file upload.
        *
        * @param {Object} evt Arguments from the file selector.
        */
       function uploadFile(evt) {
     	alert('Hello');
         gapi.client.load('drive', 'v2', function() {
           insertFile();
         });
       }
        
        function myCustomInsert(){
        	
        	var fileName = 'MyDemo.txt';
            var contentType = 'application/json';
        	var delimiter=',';
        	var close_delim=';';
        	const boundary = '-------314159265358979323846264';
        	
        	var metadata = {
        			  'title': fileName,
        			  'mimeType': contentType,
        			  'parents':[{"id":"0B_jU3ZFb1zpHa0hpQ3JYRlBEVGc"}]// It is one of my folder's id.
        			};

        			var base64Data = btoa(JSON.stringify(metadata));
        			var multipartRequestBody =
        			    delimiter +
        			    'Content-Type: application/json\r\n\r\n' +
        			    JSON.stringify(metadata) +
        			    delimiter +
        			    'Content-Type: ' + contentType + '\r\n' +
        			    'Content-Transfer-Encoding: base64\r\n' +
        			    '\r\n' +
        			    base64Data +
        			    close_delim;

        			var request = gapi.client.request({
        			    'path': '/upload/drive/v2/files',
        			    'method': 'POST',
        			    'params': {'uploadType': 'multipart'},
        			    'headers': {
        			      'Content-Type': 'multipart/mixed; boundary="' + boundary + '"'
        			    },
        			    'body': multipartRequestBody});

        			request.execute(function(arg) {
        		           console.log(arg);
        		         });
        	
        }
        
        

       /**
        * Insert new file.
        */
       function insertFile() {
         const boundary = '-------314159265358979323846264';
         const delimiter = "\r\n--" + boundary + "\r\n";
         const close_delim = "\r\n--" + boundary + "--";
         var appState = {
           number: 12,
           text: 'hello'
         };
         var fileName = 'Meri_File_2.txt';
         var contentType = 'application/json';
         var metadata = {
           'title': fileName,
           'mimeType': contentType,
           'parents':[{"id":"0B_jU3ZFb1zpHa0hpQ3JYRlBEVGc"}]// It is one of my folder's id.
         };
         var base64Data = btoa(JSON.stringify(appState));
         var multipartRequestBody =
             delimiter +
             'Content-Type: application/json\r\n\r\n' +
             JSON.stringify(metadata) +
             delimiter +
             'Content-Type: ' + contentType + '\r\n' +
             'Content-Transfer-Encoding: base64\r\n' +
             '\r\n' +
             base64Data +
             close_delim;
         var request = gapi.client.request({
             'path': '/upload/drive/v2/files',
             'method': 'POST',
             'params': {'uploadType': 'multipart'},
             'headers': {
               'Content-Type': 'multipart/mixed; boundary="' + boundary + '"'
             },
             'body': multipartRequestBody});
         request.execute(function(arg) {
           console.log(arg);
         });
       }
       
       
      
      
           init = function() {
               s = new gapi.drive.share.ShareClient();
               s.setItemIds(['0B_jU3ZFb1zpHa0hpQ3JYRlBEVGc']);
           }
           /* window.onload = function() {
               gapi.load('drive-share', init);
           } */
      
    </script>
    <script src="https://apis.google.com/js/client.js?onload=checkAuth">
    </script>
  </head>
  <body>
  
  <button onclick="s.showSettingsDialog()">Share</button>
    <div id="authorize-div" style="display: none">
      <span>Authorize access to Drive API</span>
      Button for the user to click to initiate auth sequence
      <button id="authorize-button" onclick="handleAuthClick(event)">
        Authorize
      </button>
    </div>
    <pre id="output"></pre>
    <h1>Print this...</h1>
      <input type="file" id="filePicker"/>
  </body>
</html>