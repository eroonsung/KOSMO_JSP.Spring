package com.naver.erp;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfiguration implements WebMvcConfigurer{
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new SessionInterceptor()).excludePathPatterns(
					"/loginForm.do"
					, "/loginProc.do"
					, "/loginProc2.do"
					, "/logout.do"
					, "/login_alert.do"
					, "/memRegForm.do"
					, "/resources/**"
					
			);
	}
}
