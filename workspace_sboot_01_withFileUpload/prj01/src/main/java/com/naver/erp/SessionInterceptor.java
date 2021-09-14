package com.naver.erp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

public class SessionInterceptor implements HandlerInterceptor{
	@Override
	public boolean preHandle(
			HttpServletRequest request
			, HttpServletResponse response
			, Object handler
	) throws Exception{
		HttpSession session = request.getSession();
		
		String login_id = (String)session.getAttribute("login_id");
		
		if(login_id==null) {
			response.sendRedirect("/login_alert.do");
			return false;
		}else {
			return true;
		}
	}
	
}
