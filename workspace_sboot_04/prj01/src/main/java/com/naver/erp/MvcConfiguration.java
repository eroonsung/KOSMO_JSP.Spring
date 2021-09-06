package com.naver.erp;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
//개발자가 직접 만든 SessionInterceptor 클래스를 [인터셉터]로 등록하기 위한 MvcConfiguration 클래스 선언하기
// 일종의 설정을 위한 클래스
//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
@Configuration
public class MvcConfiguration implements WebMvcConfigurer{
	//xml 파일에서 설정해야 할 것(레거시 프로젝트)들을 자바 클래스 내에서 설정함
	
	//***********************************************
	// SessionInterceptor 객체를 인터셉터로 등록하는 코딩이 내포된 
	// addInterceptors 메서드를 오버라이딩한다
	//***********************************************
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//---------------------------------------------------
		// InterceptorRegistry 객체의 addInterceptor메소드를 호출하여 SessionInterceptor 객체를 [인터셉터로 등록]하고
		// excludePathPatterns 메소드를 호출하여 [예외되는 URL 주소 패턴]을 등록한다.
		//		SessionInterceptor객체가 먼저 가로채도록 함
		//---------------------------------------------------
		registry.addInterceptor(new SessionInterceptor()).excludePathPatterns(
				"/loginForm.do"
				,"/loginProc.do"
				,"/loginProc2.do"
				,"/logout.do"
				,"/memRegForm.do"
				,"/logout.do"
				,"/login_alert.do"
				,"/resources/**"
		);
	}
}
