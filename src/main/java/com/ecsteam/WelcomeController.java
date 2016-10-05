package com.ecsteam;


import java.util.Date;
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

	@Value("${vcap.application.space_name:unknown}")
	private String spaceName;

	@Value("${vcap.application.space_id:unknown}")
	private String spaceId;

	@Value("${vcap.application.application_name:unknown}")
	private String applicationName;

	@Value("${vcap.application.application_id:unknown}")
	private String applicationId;

	@Value("${vcap.application.application_uris:unknown}")
	private String appURIs;
	
	@Value("${CF_INSTANCE_ADDR:unknown}")
	private String instanceAddr;
	
	@Value("${CF_INSTANCE_GUID:unknown}")
	private String instanceGuid;

	@Value("${CF_INSTANCE_INDEX:unknown}")
	private String instanceIndex;

	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseBody
	public String index(HttpSession session) {
		
		Date createTime = (Date)session.getAttribute("createTime");
		UUID uid = (UUID) session.getAttribute("uid");
        if (uid == null) {
            uid = UUID.randomUUID();
            session.setAttribute("uid", uid);
            createTime = new Date();
            session.setAttribute("createTime", createTime);
        }
		
        StringBuilder sb = new StringBuilder();
        sb.append("<html><body>");
        
        sb.append("<h1>App info</h1>");
               
        sb.append("spaceName: ");
        sb.append(spaceName);
        
        sb.append("<br/>spaceId: ");
        sb.append(spaceId);

        sb.append("<br/>applicationName: ");
        sb.append(applicationName);

        sb.append("<br/>applicationId: ");
        sb.append(applicationId);
     
        sb.append("<br/>instanceAddr: ");
        sb.append(instanceAddr);

        sb.append("<br/>instanceGuid: ");
        sb.append(instanceGuid);

        sb.append("<br/>instanceIndex: ");
        sb.append(instanceIndex);
        
        sb.append("<br/>App URIs (at the time the app was started): ");
        sb.append(appURIs);
        
        sb.append("<h1>Custom</h1>");
        
        sb.append("Custom Message: ");
        sb.append(message);

        sb.append("<h1>Session info</h1>");
        
        sb.append("uid: ");
        sb.append(uid);

        sb.append("<br/>createTime: ");
        sb.append(createTime);

        sb.append("</body></html>");
 
        
        return sb.toString();
        

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

