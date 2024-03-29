package com.mballem.demoajax;

import org.directwebremoting.spring.DwrSpringServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;

import com.mballem.demoajax.service.SocialMetaTagService;
@ImportResource(locations = "classpath:dwr-spring.xml")
@SpringBootApplication
public class DemoAjaxApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(DemoAjaxApplication.class, args);
	}
	
	@Autowired
	SocialMetaTagService service;
	
	@Override
	public void run(String... args) throws Exception {

//		SocialMetaTag tag = service.getSocialMetatagByUrl("https://www.pichau.com.br/headset-logitech-g933-artemis-spectrum-7-1-surround-usb-wireless-981-000598");
//		System.out.println(tag);

	}
	
	@Bean
	public ServletRegistrationBean<DwrSpringServlet> dwrSpringServlet() {
		DwrSpringServlet dwrServlet = new DwrSpringServlet();
		ServletRegistrationBean<DwrSpringServlet> registrationBean =
				new ServletRegistrationBean<>(dwrServlet, "/dwr/*");
		registrationBean.addInitParameter("debug", "true");
		registrationBean.addInitParameter("activeReverseAjaxEnabled", "true");
		
		return registrationBean;
		
	}
}
