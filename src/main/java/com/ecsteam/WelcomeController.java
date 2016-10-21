package com.ecsteam;


import java.util.Date;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class WelcomeController {
	
	public final static String TEST_COOKIE_NAME = "TEST_COOKIE";

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
	public String index(HttpServletRequest request) {
		
		HttpSession session = request.getSession(false);
				
        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        
        // Prevent browser from making "/favicon.ico" requests
        sb.append("<head><link rel=\"icon\" type=\"image/png\" href=\"data:image/png;base64,iVBORw0KGgo=\"></head>");
        sb.append("<body>");
        
        sb.append("<h1>App info</h1>");
              
        sb.append("Page refresh time: " + new Date());
        sb.append("<br/>spaceName: ");
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

        sb.append("<h1>HTTP Session info</h1>");
        
        if (session==null) {
        	sb.append("No HTTP session exists");
        	sb.append("<br/><br/><a href='/create'>Create HTTP session</a>");
        	sb.append("<br/><br/><a href='/clearCookies'>Clear all cookies (if any exist)</a>");
        } else {
        
			Date createTime = (Date)session.getAttribute("createTime");
			UUID uid = (UUID) session.getAttribute("uid");
			Integer counter = (Integer) session.getAttribute("counter");
			

	        sb.append("uid: ");
	        sb.append(uid);
	
	        sb.append("<br/>createTime: ");
	        sb.append(createTime);

	        sb.append("<br/>counter: ");
	        sb.append(counter);
	        session.setAttribute("counter", (counter!=null)?++counter:0);
	        
        	sb.append("<br/><br/><a href='/remove'>Delete HTTP session</a>");

        }
        
       

        boolean foundTestCookie = false;
        if (request.getCookies() != null) {
	        for (Cookie cookie: request.getCookies()) {
	        	if (cookie.getName().equals(TEST_COOKIE_NAME)) {
	        		foundTestCookie = true;
	        		break;
	        	}
	       
	        }
        }
        
        if (!foundTestCookie) {
      		sb.append("<br/><br/><a href='/createTestCookie'>Create "+TEST_COOKIE_NAME+"</a>");
        }
 
        sb.append("</body></html>");
 
        
        return sb.toString();
        

	}
	
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	@ResponseBody
	public String createSession(HttpSession session) {
		Date createTime = (Date)session.getAttribute("createTime");
		UUID uid = (UUID) session.getAttribute("uid");
        if (uid == null) {
            uid = UUID.randomUUID();
            session.setAttribute("uid", uid);
            createTime = new Date();
            session.setAttribute("createTime", createTime);
            
            session.setAttribute("counter", 0);
        }
        return redirectViaJavascript();
	}
	
	@RequestMapping(value = "/remove", method = RequestMethod.GET)
	@ResponseBody
	public String removeSession(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		Cookie cookie = new Cookie("JSESSIONID","");
		cookie.setValue(null);
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
        return redirectViaJavascript();
	}
	
	@RequestMapping(value = "/clearCookies", method = RequestMethod.GET)
	@ResponseBody
	public String clearCookies(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            cookie.setMaxAge(0);
            cookie.setValue(null);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
        //return new RedirectView("/");
        return redirectViaJavascript();
	}
	
	@RequestMapping(value = "/createTestCookie", method = RequestMethod.GET)
	@ResponseBody
	public String createTestCookie(HttpServletRequest request, HttpServletResponse response) {
		Cookie cookie = new Cookie(TEST_COOKIE_NAME,"MyExampleValue_"+System.currentTimeMillis());
		cookie.setPath("/");
		response.addCookie(cookie);
		return redirectViaJavascript();
	}
	/**
	 * Not able to use return type of "View" and return new RedirectView("/"); because complex testing
	 * through load balancer (e.g., F5) might not have a match between the "Host:" and the real
	 * URL the browser is using.  This can happen when we force the "Host:" value within the LB.  The
	 * safest way is to let the browser do a relative request back to root URI so the host name does
	 * not change.
	 * 
	 * @return
	 */
	private String redirectViaJavascript() {
		return "<html><head><script>window.location.href='/'; </script></head></html>";
	}

	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason="Incorrect request,     check request data")
	public void handleClientErrors(Exception ex) { }
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason="Internal server error")
	public void handleServerErrors(Exception ex) { }

}

