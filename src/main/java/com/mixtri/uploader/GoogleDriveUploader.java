/*package com.mixtri.uploader;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.InputStreamContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive.Files.Get;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.*;
import com.google.api.services.drive.Drive;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.ParentReference;
import com.google.gson.Gson;

*//****
 * 
 * @author Hitesh
 * We are not using this class but we have just kept this class for reference purposes how to upload to google drive through java
 *
 *//*

public class GoogleDriveUploader {
    *//** Application name. *//*
    private static final String APPLICATION_NAME =
        "Drive API Java Quickstart";

    *//** Directory to store user credentials for this application. *//*
    private static final java.io.File DATA_STORE_DIR = new java.io.File(
        System.getProperty("user.home"), ".credentials/drive-java-quickstart.json");

    *//** Global instance of the {@link FileDataStoreFactory}. *//*
    private static FileDataStoreFactory DATA_STORE_FACTORY;

    *//** Global instance of the JSON factory. *//*
    private static final JsonFactory JSON_FACTORY =
        JacksonFactory.getDefaultInstance();

    *//** Global instance of the HTTP transport. *//*
    private static HttpTransport HTTP_TRANSPORT;

    *//** Global instance of the scopes required by this quickstart.
     *
     * If modifying these scopes, delete your previously saved credentials
     * at ~/.credentials/drive-java-quickstart.json
     *//*
    private static final List<String> SCOPES =
        Arrays.asList(DriveScopes.DRIVE_FILE);
    
    private static final String CLIENT_ID = "991788540840-sss9n7hiiups027ck5e8m5vf047bpuii.apps.googleusercontent.com";

    private static final String CLIENT_SECRET="CtT9D8YEfnYF37aUzDDV3nCb";
    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    
    public static String getGoogleToken() throws IOException{
    	
    	String refreshToken="1/NdU_ZiIarLt-7XPzwCOKQqa9c0HIR88lTAHRqn6-o4U";
    	
    	GoogleCredential credential = createCredentialWithRefreshToken(
    	        HTTP_TRANSPORT, JSON_FACTORY, new TokenResponse().setRefreshToken(refreshToken));
    	
    	credential.refreshToken();
    	String accessToken = credential.getAccessToken();
    	System.out.println(accessToken);
    	return accessToken;
    }
    
    
    public static GoogleCredential createCredentialWithRefreshToken(HttpTransport transport, 
            JsonFactory jsonFactory, TokenResponse tokenResponse) {
        return new GoogleCredential.Builder().setTransport(transport)
            .setJsonFactory(jsonFactory)
            .setClientSecrets(CLIENT_ID, CLIENT_SECRET)
            .build()
            .setFromTokenResponse(tokenResponse);
    }
    
    *//**
     * Creates an authorized Credential object.
     * @return an authorized Credential object.
     * @throws IOException
     *//*
    public static Credential authorize() throws IOException {
        // Load client secrets.
    	
    	InputStream in = 
    		      new FileInputStream("C:/Users/Hitesh/git/mixtri_maven/src/main/webapp/properties/client_secret_mixtri_server_app.json");
    	
        GoogleClientSecrets clientSecrets =
            GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(DATA_STORE_FACTORY)
                .setAccessType("offline")
                .build();
       
        Credential credential = new AuthorizationCodeInstalledApp(
            flow, new LocalServerReceiver()).authorize("user");
        
       // credential.refreshToken();
        String token = credential.getAccessToken();
        
        System.out.println("Token: "+token);
        
        System.out.println(
                "Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
        return credential;
    }
    
   
    *//**
     * Build and return an authorized Drive client service.
     * @return an authorized Drive client service
     * @throws IOException
     *//*
    public static Drive getDriveService() throws IOException {
        Credential credential = authorize();
        return new Drive.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }
    
    
    *//**
     * Insert new file.
     *
     * @param service Drive API service instance.
     * @param title Title of the file to insert, including the extension.
     * @param description Description of the file to insert.
     * @param parentId Optional parent folder's ID.
     * @param mimeType MIME type of the file to insert.
     * @param filename Filename of the file to insert.
     * @return Inserted file metadata if successful, {@code null} otherwise.
     *//*
   
    
    protected static File uploadToGoogleDrive(Drive service, String fileName, String description,
            String parentId, String mimeType, byte[] inputStreamBytes) {
          // File's metadata.
          File body = new File();
          body.setTitle(fileName);
          body.setDescription(description);
          body.setMimeType(mimeType);

          // Set the parent folder.
          if (parentId != null && parentId.length() > 0) {
            body.setParents(
                Arrays.asList(new ParentReference().setId(parentId)));
          }

          try {
        	  
        	File  file = service.files().insert(body, new InputStreamContent(mimeType,new ByteArrayInputStream(inputStreamBytes))).execute();

        	// Uncomment the following line to print the File ID.
            System.out.println("File ID: " + file.getId());

            return file;
          } catch (IOException e) {
            System.out.println("An error occured: " + e);
            return null;
          }
        }

    *//**
     * 
     * @param service: Google Drive Service
     * @param parentId: id of the parent folder already created in mixtri.live@gmail.com google account. The name of parent folder is upload
     * @return
     * @throws IOException
     *//*
    
    public static String createGoogleDriveFolder(Drive service,String parentId,String folderToBeCreated) throws IOException{
    	
    	File fileMetadata = new File();
    	fileMetadata.setTitle(folderToBeCreated);
    	fileMetadata.setMimeType("application/vnd.google-apps.folder");
    	
    	if (parentId != null && parentId.length() > 0) {
    		fileMetadata.setParents(
                Arrays.asList(new ParentReference().setId(parentId)));
          }

    	File folder = service.files().insert(fileMetadata)
    	        .setFields("id")
    	        .execute();
    	System.out.println("Folder ID: " + folder.getId());
    	
    	
    	return folder.getId();
    }
   
    
    protected static File insertFile(Drive service, String title, String description,
            String parentId, String mimeType, String filename) {
          // File's metadata.
          File body = new File();
          body.setTitle(title);
          body.setDescription(description);
          body.setMimeType(mimeType);

          // Set the parent folder.
          if (parentId != null && parentId.length() > 0) {
            body.setParents(
                Arrays.asList(new ParentReference().setId(parentId)));
          }

          // File's content.
          java.io.File fileContent = new java.io.File(filename);
          FileContent mediaContent = new FileContent(mimeType, fileContent);
          try {
        	  
        	//File  file = service.files().insert(body, new InputStreamContent(mimeType,new ByteArrayInputStream(filename))).execute();
        	  
            File file = service.files().insert(body, mediaContent).execute();

            // Uncomment the following line to print the File ID.
            // System.out.println("File ID: " + file.getId());

            return file;
          } catch (IOException e) {
            System.out.println("An error occured: " + e);
            return null;
          }
        }
    
    public static void main(String[] args) throws IOException {
        // Build a new authorized API client service.
        Drive service = getDriveService();
        
        System.out.println("Hello....");
        
        String folderToBeCreated ="Invoices";
     //   String folderId = createGoogleDriveFolder(service,"0B_jU3ZFb1zpHQmFIMDNzc2dBRHM",folderToBeCreated);
        
       // Files.List request = service.files().list().setQ("mimeType='application/vnd.google-apps.folder' and trashed=false");
        Object file1 = service.files().list().get("Invoices");
        
        Get googleGet =  service.files().get("djheeths@gmail.com");
        
        
        //insertFile(service, "Capital Cities - Safe and Sound.mp3", "SF", folderId, "audio/mp3", "C:/Users/Hitesh/Desktop/Songs/Capital Cities - Safe and Sound.mp3");

        FileList result = service.files().list().setMaxResults(10).execute();
        List<File> files = result.getItems();
        if (files == null || files.size() == 0) {
            System.out.println("No files found.");
        } else {
            System.out.println("Files:");
            for (File file : files) {
                System.out.printf("%s (%s)\n", file.getTitle(), file.getId());
            }
        }
       
    }
    
    public static void main(String[] args) throws IOException {
    	getGoogleToken();
    	//authorize();   	
    	
        // Build a new authorized API client service.
        Drive service = getDriveService();
        
       System.out.println("Hello....");
        
        String folderToBeCreated ="Invoices";
        String folderId = createGoogleDriveFolder(service,"0B_jU3ZFb1zpHQmFIMDNzc2dBRHM",folderToBeCreated);
        
        insertFile(service, "Capital Cities - Safe and Sound.mp3", "SF", folderId, "audio/mp3", "C:/Users/Hitesh/Desktop/Songs/Capital Cities - Safe and Sound.mp3");

        FileList result = service.files().list().setMaxResults(10).execute();
        List<File> files = result.getItems();
        if (files == null || files.size() == 0) {
            System.out.println("No files found.");
        } else {
            System.out.println("Files:");
            for (File file : files) {
                System.out.printf("%s (%s)\n", file.getTitle(), file.getId());
            }
        }
       
    }

}
*/