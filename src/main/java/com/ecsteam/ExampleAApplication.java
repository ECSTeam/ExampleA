package com.ecsteam;

import java.util.HashMap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class ExampleAApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(ExampleAApplication.class, args);
	}
	
	
	public static void mainX(String[] args) throws Exception {
		Exception e = new Exception("hello Original");
		
		ObjectMapper mapper = new ObjectMapper();

		String json = mapper.writeValueAsString(e);
		System.out.println("Json:" + json);
		
		HashMap<String,Object> result =
		        new ObjectMapper().readValue(json, HashMap.class);
		
		result.put("message", "hello from Kurt");
		String newJson = mapper.writeValueAsString(result);
		
		//String newJson = json.replaceAll("hello Original", "new message");
		
		Exception newException = mapper.readValue(newJson, Exception.class);
		 
		//throw newException;

		
		//System.out.println("hello");
	}
	
	
//	@Bean
//	CharacterEncodingFilter characterEncodingFilter() {
//	    CharacterEncodingFilter filter = new CharacterEncodingFilter();
//	    filter.setEncoding("UTF-8x");
//	    filter.setForceEncoding(true);
//	    return filter;
//	}
	
//	
//	@Bean
//	public FilterRegistrationBean<CharacterEncodingFilter> filterRegistrationBean() {
//	    FilterRegistrationBean<CharacterEncodingFilter> registrationBean = new FilterRegistrationBean<CharacterEncodingFilter>();
//	    CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
//	    characterEncodingFilter.setForceEncoding(true);
//	    characterEncodingFilter.setEncoding("");
//	    registrationBean.setFilter(characterEncodingFilter);
//	    return registrationBean;
//	}
//	

	
}
