package com.naver.erp;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

	@Autowired
	private LoginDAO loginDAO;
	
	@RequestMapping(value="loginForm.do")
	public ModelAndView loginForm() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("loginForm.jsp");
		return mav;
	}
	
	@RequestMapping(value="loginProc.do")
	public ModelAndView loginProc(HttpServletRequest request) {
		String login_id = request.getParameter("login_id");
		String pwd = request.getParameter("pwd");
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("login_id", login_id);
		map.put("pwd", pwd);
		
		int login_idCnt = loginDAO.getLogin_idCnt(map);
		
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("loginProc.jsp");
		mav.addObject("idCnt",login_idCnt );
		return mav;
	}
}
