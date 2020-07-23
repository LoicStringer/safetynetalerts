package com.safetynet.safetynetalerts;

import org.apache.catalina.core.ApplicationContext;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.safetynet.safetynetalerts.dao.PersonDao;
import com.safetynet.safetynetalerts.service.EmergencyService;


@Configuration
public class AppConfig {
	
	@Bean
	public HttpTraceRepository htttpTraceRepository(){
	  return new InMemoryHttpTraceRepository();
	}


}
