package com.ecsteam;


import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WelcomeController {

	@Value("${application.message:Hello World}")
	private String message = "Hello World";

	@Value("${vcap.application.application_uris:not_found}")
	private String myURIs;
	
	

	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseBody
	public String index(HttpSession session) {
		
		UUID uid = (UUID) session.getAttribute("uid");
        if (uid == null) {
            uid = UUID.randomUUID();
        }
        session.setAttribute("uid", uid);
        //return uid.toString();
		
        return "Message: ["+ message+ "] My URIs:"+ myURIs;
		
		
		//return "Hello";
	}
	/*
	
	@RestController
	class HelloRestController {

	    @RequestMapping("/")
	    String uid(HttpSession session) {
	        UUID uid = (UUID) session.getAttribute("uid");
	        if (uid == null) {
	            uid = UUID.randomUUID();
	        }
	        session.setAttribute("uid", uid);
	        return uid.toString();
	    }
	}
	
	*/
	
	@RequestMapping("/foo")
	public String foo(Map<String, Object> model) {
		throw new RuntimeException("Foo");
	}

}

