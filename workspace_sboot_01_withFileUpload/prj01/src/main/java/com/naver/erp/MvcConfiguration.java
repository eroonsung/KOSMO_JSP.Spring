package com.naver.erp;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfiguration implements WebMvcConfigurer{
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new SessionInterceptor()).excludePathPatterns(
					"/naver/loginForm.do"
					, "/naver/loginProc.do"
					, "/naver/loginProc2.do"
					, "/naver/loginProc3.do"
					, "/naver/logout.do"
					, "/naver/login_alert.do"
					, "/naver/memRegForm.do"
					, "/resources/**"
					
			);
	}
}
