package com.inventorysystem.auth;

import com.inventorysystem.common.dto.RequestMetaData;
import com.inventorysystem.common.utilities.Utils;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@EnableFeignClients
@EnableDiscoveryClient
@EnableAsync
@SpringBootApplication
@ComponentScan(basePackages = "com.inventorysystem.*")
public class AuthApplication {

	@Autowired
	private Utils commonUtils;

	public static void main(String[] args) {
		SpringApplication.run(AuthApplication.class, args);
	}

	@Bean
	@RequestScope
	public RequestMetaData getRequestDetail() {
		HttpServletRequest request =
			(((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest());
		return commonUtils.getRequestDetail(request);
	}
}
