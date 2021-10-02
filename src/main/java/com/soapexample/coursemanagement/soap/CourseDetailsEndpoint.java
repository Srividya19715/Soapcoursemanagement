package com.soapexample.coursemanagement.soap;

import java.util.List;

import org.example.course_details.CourseDetails;
import org.example.course_details.DeleteCourseDetailsRequest;
import org.example.course_details.DeleteCourseDetailsResponse;
import org.example.course_details.GetAllCourseDetailsRequest;
import org.example.course_details.GetAllCourseDetailsResponse;
import org.example.course_details.GetCourseDetailsRequest;
import org.example.course_details.GetCourseDetailsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.soapexample.coursemanagement.soap.bean.Courses;

import com.soapexample.coursemanagement.soap.exception.CourseNotFindException;
import com.soapexample.coursemanagement.soap.service.CourseDetailsService;
import com.soapexample.coursemanagement.soap.service.CourseDetailsService.Status;

// --> http://www.example.org/course-details/ [namespace used in xsd file]
//  --> GetCourseDetailsRequest [If request comes with this link the following method will be executed]

@Endpoint
public class CourseDetailsEndpoint {
	// [To process the request whenever the link loads we use payloadroot
	// annotation]
	@Autowired
	CourseDetailsService service;

	@PayloadRoot(namespace = "http://www.example.org/course-details", localPart = "GetCourseDetailsRequest")
	@ResponsePayload
	public GetCourseDetailsResponse processCourseDetailsRequest(@RequestPayload GetCourseDetailsRequest request) {

		Courses course = service.findById(request.getId());
		if(course==null) {
			 throw new RuntimeException("Invalid Course: " +request.getId());
		}
		return mapCourseDetails(course);

	}

	private GetCourseDetailsResponse mapCourseDetails(Courses course) {
		GetCourseDetailsResponse response = new GetCourseDetailsResponse();
		CourseDetails coursedetails = mapCourse(course);
		response.setCourseDetails(coursedetails);
		return response;
	}
   
	private GetAllCourseDetailsResponse mapAllCourseDetails(List<Courses> courses) {
		GetAllCourseDetailsResponse response = new GetAllCourseDetailsResponse();
		for(Courses cour: courses) {
			CourseDetails mapCourse = mapCourse(cour);
			response.getCourseDetails().add(mapCourse);
		}
		
		return response;
	}
	private CourseDetails mapCourse(Courses course) {
		CourseDetails coursedetails = new CourseDetails();
		coursedetails.setId(course.getId());
		coursedetails.setName(course.getName());
		coursedetails.setDescription(course.getDescription());
		return coursedetails;
	}

	@PayloadRoot(namespace = "http://www.example.org/course-details", localPart = "GetAllCourseDetailsRequest")
	@ResponsePayload
	public GetAllCourseDetailsResponse processAllCourseDetailsRequest(@RequestPayload GetAllCourseDetailsRequest request) {

		List<Courses> course = service.findAll();
		return mapAllCourseDetails(course);

	}
	
	@PayloadRoot(namespace = "http://www.example.org/course-details", localPart = "DeleteCourseDetailsRequest")
	@ResponsePayload
	public DeleteCourseDetailsResponse processAllCourseDetailsRequest(@RequestPayload DeleteCourseDetailsRequest request) {

		Status status = service.DeleteById(request.getId());
		DeleteCourseDetailsResponse response = new DeleteCourseDetailsResponse();
		response.setStatus(mapStatus(status));
		return response;
		
	}

	private org.example.course_details.Status mapStatus(Status status) {
		if(status==Status.FAILURE) {
			return org.example.course_details.Status.FAILURE;
		}
		return org.example.course_details.Status.SUCCESS;
	}

	
}
