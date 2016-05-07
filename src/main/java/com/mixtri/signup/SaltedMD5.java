package com.mixtri.signup;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

import org.apache.log4j.Logger;

public class SaltedMD5 {
	
	static Logger log = Logger.getLogger(SaltedMD5.class.getName());
	
	
	public String generateSecurePassword(String passwordToHash,UserSignUpBean userSignUpBean) throws NoSuchAlgorithmException, NoSuchProviderException{
		
		String generatedPassword=null;
		String salt = getSalt();
		generatedPassword = getSecurePassword(passwordToHash, salt);
		userSignUpBean.setSalt(salt);
		
		
		return generatedPassword;
	}
	
	public String getSecurePassword(String passwordToHash, String salt)
    {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(salt.getBytes());
            byte[] bytes = md.digest(passwordToHash.getBytes());
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e) {
           e.printStackTrace();
        }
        return generatedPassword;
    }
     
    private static String getSalt() throws NoSuchAlgorithmException, NoSuchProviderException
    {
        //Always use a SecureRandom generator
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
        //Create array for salt
        byte[] salt = new byte[16];
        //Get a random salt
        sr.nextBytes(salt);
        //return salt
        return salt.toString();
    }
}


