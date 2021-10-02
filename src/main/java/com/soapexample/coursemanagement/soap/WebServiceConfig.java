package com.soapexample.coursemanagement.soap;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

//Enabling web services
@EnableWs
//Spring configuration
@Configuration // Message dispatcher servlet receives the url which should be directed to
				// server
public class WebServiceConfig {
	// MESSAGEDISPTACHERSERVLET
	// APPLICATIONCONTEXT
	// URL-->/ws/courses.wsdl

	@Bean
	public ServletRegistrationBean messageDispatcherservlet(ApplicationContext context) {
		MessageDispatcherServlet messageDispatcherservlet = new MessageDispatcherServlet();
		messageDispatcherservlet.setApplicationContext(context);
		messageDispatcherservlet.setTransformWsdlLocations(true);
		return new ServletRegistrationBean(messageDispatcherservlet, "/ws/*");
	}

	// Spring web services automatically generates wsdl

	@Bean(name = "courses") //the bean name giving here is url automatically generated that's why courses.wsdl
	public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema coursesSchema) {
		DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
		// PortType-Courseport
		definition.setPortTypeName("CoursePort");
		// namespace - http://www.example.org/course-details
		definition.setTargetNamespace("http://www.example.org/course-details");
		// setting ws
		definition.setLocationUri("/ws");
		definition.setSchema(coursesSchema);
        return definition;
	}
	
	@Bean
	public XsdSchema coursesSchema() {
		return new SimpleXsdSchema(new ClassPathResource("course-details.xsd"));
	}

}
