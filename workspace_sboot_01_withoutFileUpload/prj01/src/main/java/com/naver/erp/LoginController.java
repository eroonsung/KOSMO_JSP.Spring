package com.naver.erp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
	//===========================================================================
	@Autowired
	private LoginDAO loginDAO;
	
	@RequestMapping(value="loginForm.do")
	public ModelAndView loginForm() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("loginForm.jsp");
		return mav;
	}
	//===========================================================================
	@RequestMapping(value="loginProc.do")
	public ModelAndView loginProc(HttpServletRequest request, HttpSession session) {
		String login_id = request.getParameter("login_id");
		String pwd = request.getParameter("pwd");
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("login_id", login_id);
		map.put("pwd", pwd);
		
		int login_idCnt = loginDAO.getLogin_idCnt(map);
		
		if(login_idCnt==1) {
			session.setAttribute("login_id", login_id);			
		}
		
		ModelAndView mav = new ModelAndView();
		//mav.setViewName("loginProc.jsp");
		mav.setViewName("loginProc2.jsp");
		mav.addObject("idCnt",login_idCnt );
		return mav;
	}
	
	//----------------------------------------------------------------------------
	@RequestMapping( value = "/loginProc2.do")
	public ModelAndView loginProc2(
			@RequestParam( value="login_id") String login_id
			, @RequestParam( value="pwd") String pwd		
			, @RequestParam( value="is_login", required=false ) String is_login
			, HttpSession session		
			, HttpServletResponse response
	) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("login_id", login_id);
		map.put("pwd", pwd);
		
		int login_idCnt = loginDAO.getLogin_idCnt(map);
		
		if( login_idCnt==1 ) {
			session.setAttribute("login_id", login_id);
			
			if(is_login==null) {
				Cookie cookie1 = new Cookie("login_id", null);
				cookie1.setMaxAge(0);
				
				Cookie cookie2 = new Cookie("pwd", null);
				cookie2.setMaxAge(0);
				
				response.addCookie(cookie1);
				response.addCookie(cookie2);
			}else {
				Cookie cookie1 = new Cookie("login_id", login_id);
				cookie1.setMaxAge(60*60*24);
				
				Cookie cookie2 = new Cookie("pwd", pwd);
				cookie2.setMaxAge(60*60*24);
				
				response.addCookie(cookie1);
				response.addCookie(cookie2);
			}
		}
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("loginProc2.jsp");
		mav.addObject("idCnt", login_idCnt); 
		return mav;	
	}	
	
	//----------------------------------------------------------------------------
	@RequestMapping( 
			value = "/loginProc3.do"
			, method = RequestMethod.POST
			, produces = "application/json;charset=UTF-8" )
	@ResponseBody
	public int loginProc3(
			@RequestParam( value="login_id") String login_id
			, @RequestParam( value="pwd") String pwd		
			, @RequestParam( value="is_login", required=false ) String is_login
			, HttpSession session		
			, HttpServletResponse response
	) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("login_id", login_id);
		map.put("pwd", pwd);
		
		int login_idCnt = loginDAO.getLogin_idCnt(map);
		
		if( login_idCnt==1 ) {
			session.setAttribute("login_id", login_id);
			
			if(is_login==null) {
				/*
				Cookie cookie1 = new Cookie("login_id", null);
				cookie1.setMaxAge(0);
				
				Cookie cookie2 = new Cookie("pwd", null);
				cookie2.setMaxAge(0);
				
				response.addCookie(cookie1);
				response.addCookie(cookie2);
				*/
				Util.addCookie("login_id", null, 0, response);
				Util.addCookie("pwd", null, 0, response);
				
			}else {
				/*
				 * Cookie cookie1 = new Cookie("login_id", login_id);
				 * cookie1.setMaxAge(60*60*24);
				 * 
				 * Cookie cookie2 = new Cookie("pwd", pwd); cookie2.setMaxAge(60*60*24);
				 * 
				 * response.addCookie(cookie1); response.addCookie(cookie2);
				 */
				Util.addCookie("login_id", login_id, 60*60*24, response);
				Util.addCookie("pwd", pwd, 60*60*24, response);
			}
		}
		
		return login_idCnt;	
	}	
	//----------------------------------------------------------------------------
	@RequestMapping(value="logout.do")
	public ModelAndView logout(HttpSession session) {
		
		session.removeAttribute("login_id");
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("logout.jsp");
		return mav;
	}
	
	//----------------------------------------------------------------------------
	@RequestMapping(value="login_alert.do")
	public ModelAndView login_alert() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("login_alert.jsp");
		return mav;
	}
}
