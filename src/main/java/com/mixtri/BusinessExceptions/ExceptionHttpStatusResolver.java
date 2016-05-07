package com.mixtri.BusinessExceptions;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.log4j.Logger;

/**
 * @author Hitesh Bhutani
 *
 */
@Provider
public class ExceptionHttpStatusResolver implements
ExceptionMapper {
	static Logger log = Logger.getLogger(ExceptionHttpStatusResolver.class.getName());
	
	public Response toResponse(Exception exception) {
		Response.Status httpStatus = Response.Status.INTERNAL_SERVER_ERROR;

		return Response.status(httpStatus).build() ;
		/*if (exception instanceof BusinessException)
			httpStatus = Response.Status.BAD_REQUEST;

		return Response.status(httpStatus).entity(exception.getMessage())
				.build();*/
	}

	@Override
	public Response toResponse(Throwable arg0) {
		// TODO Auto-generated method stub
		return null;
	}
}