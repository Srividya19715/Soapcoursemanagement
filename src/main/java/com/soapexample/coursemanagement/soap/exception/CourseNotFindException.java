package com.soapexample.coursemanagement.soap.exception;

import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;


@SoapFault(faultCode = FaultCode.CLIENT)
public class CourseNotFindException extends RuntimeException {

	/**   Now the exception is thrown as server error so we have to use the SoapFault annotation to make it a client error
	 * @SoapFault(faultCode = FaultCode.CUSTOM, customFaultCode ="{http://www.example.org/course-details}Course_Not_Found")
	 */
	private static final long serialVersionUID = 6879207698174789247L;
    
	public CourseNotFindException(String message) {
		super(message);
	}
       
}
