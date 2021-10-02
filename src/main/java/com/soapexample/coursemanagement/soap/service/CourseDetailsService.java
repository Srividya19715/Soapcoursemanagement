package com.soapexample.coursemanagement.soap.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

import com.soapexample.coursemanagement.soap.bean.Courses;

@Component
public class CourseDetailsService {
	public enum Status{
		SUCCESS,FAILURE;
	}
	private static List<Courses> courses = new ArrayList<>();
	static {
		Courses course1 = new Courses(1,"Spring","10 steps");
		courses.add(course1);
		Courses course2 = new Courses(2,"Spring MVC","10 Examples");
		courses.add(course2);
		Courses course3 = new Courses(3,"Spring Boot","101 steps");
		courses.add(course3);
		Courses course4 = new Courses(4,"Maven","Popular course");
		courses.add(course4);
	}
	//For course 1
	public Courses findById(int id) {
		for(Courses course:courses) {
			if(course.getId()==id)
				return course;
		}
		return null;
	}
	
	//Finding all courses
	public List<Courses> findAll(){
		return courses;
	}
	
	//Deleting By Id
	public Status DeleteById(int id) {
		Iterator<Courses> iterator = courses.iterator();
		while(iterator.hasNext()) {
			Courses course = iterator.next(); 
			if(course.getId()==id)
				iterator.remove();  // Iterator will remove matching element
				return Status.SUCCESS;
		}
		return Status.FAILURE;
	}
}
