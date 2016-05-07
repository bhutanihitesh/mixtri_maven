package com.mixtri.BusinessExceptions;

import java.util.List;

import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;

/**
 * @author Hitesh Bhutani
 *
 */
public class BusinessException extends Exception{
	static Logger log = Logger.getLogger(BusinessException.class.getName());
    
	private static final long serialVersionUID = 1L;
 
    private String message;
 
    public BusinessException(String message) {
        super();
        this.message = message;
    }
    
    public String getMessages() {
        return message;
    }
}
