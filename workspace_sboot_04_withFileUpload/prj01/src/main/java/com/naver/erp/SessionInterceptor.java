package com.naver.erp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
//import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.HandlerInterceptor;

//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
//URL 접속 시 [@Controller 가 붙은 클래스의 @RequestMapping이 붙은 메소드]가 호출되기 전 또는 후에
//실행될 메소드를 소유한 [SessionInterceptor 클래스] 선언 
//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
	//---------------------------------------------------------------
	// [@Controller 가 붙은 클래스의 @RequestMapping 가 붙은 메소드] 호출 전 또는 후에 
	// 실행될 메소드를 소유한 클래스가 될 자격 조건
	//---------------------------------------------------------------
	//		<1> 스프링이 제공하는 [HandlerInterceptor 인터페이스]를 구현한다.
	//		<2> @RequestMapping 가 붙은 메소드 호출 전에 실행할 코딩은 
	//				HandlerInterceptor 인터페이스의 preHandle 메소드를 재정의 하면서 삽입한다.
	//		<3> @RequestMapping 가 붙은 메소드 호출 후에 실행할 코딩은 
	//				HandlerInterceptor 인터페이스의 postHandle 메소드를 재정의 하면서 삽입한다.
	//---------------------------------------------------------------
		//---------------------------------------------------------------
		// [HandlerInterceptor 인터페이스 구현 객체]의 메소드 종류와 호출 시점
		//---------------------------------------------------------------
		// preHandle()*****  :  @RequestMapping 가 붙은 메소드 호출 전에 호출되는 메소드. 
		//                       true 를 리턴하면  @RequestMapping 가 붙은 메소드를 호출하고
		//                       false 를 리턴하면  @RequestMapping 가 붙은 메소드를 호출하지 않는다.
		// postHandle()      : @RequestMapping 가 붙은 메소드 호출 후에 호출되는 메소드. 
		// afterCompletion() : @RequestMapping 가 붙은 메소드 호출 후 JSP 파일이 호출된 후에 호출되는 메소드
//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm

public class SessionInterceptor implements HandlerInterceptor{			

	@Override
	public boolean preHandle(
		HttpServletRequest request 
		, HttpServletResponse response 
		, Object handler
	) throws Exception {
		//--------------------------------------------------
		//HttpSession 객체 얻기
		//HttpServletRequest 객체의 getSession() 메소드를 호출하면 HttpSession 객체를 얻을 수 있다.
		//--------------------------------------------------
		HttpSession session = request.getSession();   
		//--------------------------------------------------
		//HttpSession 객체에서 키값이 login_id로 저장된 데이터 꺼내기
		//즉 로그인 정보 꺼내기
		//--------------------------------------------------
		String login_id = (String)session.getAttribute("login_id");	
		
		//--------------------------------------------------
		//만약 login_id 변수 안에 null이 저장되어 있으면
		//즉 만약 로그인에 성공한 적이 없으면
		//--------------------------------------------------
		if(login_id==null) {
			//String croot  = request.getContextPath();  
			//--------------------------------------------------
			//클라이언트가 loginForm.do로 재접속하라고 설정하기
			//응답 메시지를 받은 클라이언트는 이 URL 주소로 강제 재접속을 한다.
			//로그인 되지 않았을 경우 이동할 url 주소
			//--------------------------------------------------
			response.sendRedirect("/login_alert.do");
			//--------------------------------------------------
			//false 값을 리턴하기
			//false 값을 리턴하면 @RequestMapping이 붙은 메소드는 호출되지 않는다.
			//--------------------------------------------------
			return false; //@Controller 안으로 들어가지 않음
		}
		//--------------------------------------------------
		//만약 login_id 변수 안에 null이 저장되어 있지 않으면
		//즉 만약 로그인에 성공한 적이 있으면
		// else if(login_id != null)
		//--------------------------------------------------
		else { 
			//--------------------------------------------------
			//true 값을 리턴하기
			//true 값을 리턴하면 @RequestMapping이 붙은 메소드가 호출된다.
			//--------------------------------------------------
			return true; //@Controller 안으로 들어감
		}
	}
	
}
