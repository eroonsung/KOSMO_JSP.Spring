package com.naver.erp;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
	
	//***********************************************
	//이 인터페이스를 구현한 클래스를 찾아서 개체화한 후 객체의 메위주를 속성변수에 저장
	//***********************************************
	@Autowired
	private LoginDAO loginDAO;
	
	//***********************************************
	//가상 주소 /loginForm.do로 접근하면 호출되는 메소드 선언
	//***********************************************
	@RequestMapping(value="loginForm.do")
	public ModelAndView loginForm() {
		System.out.println("loginController.loginForm 메소드 시작");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("loginForm.jsp");
		System.out.println("loginController.loginForm 메소드 완료");
		return mav;
	}
	//***********************************************
	//가상 주소 /loginProc.do로 접근하면 호출되는 메소드 선언
	//***********************************************
	@RequestMapping(value="loginProc.do")
	public ModelAndView loginProc(
		 @RequestParam(value="login_id") String login_id
		 ,  @RequestParam(value="pwd") String pwd
		) {
		System.out.println("loginController.loginProc 메소드 시작");
		Map<String,String> id_pwd_map = new HashMap<String,String>();
		id_pwd_map.put("login_id", login_id);
		id_pwd_map.put("pwd", pwd);
		
		int login_idCnt = this.loginDAO.getLogin_idCnt(id_pwd_map);		
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("loginProc.jsp");
		mav.addObject("login_idCnt", login_idCnt);
		System.out.println("loginController.loginProc 메소드 완료");
		return mav;
	}
}
