package com.naver.erp;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
//URL 주소로 접속하면 호출되는 메소드를 소유한 [컨트롤러 클래스]선언
// @Controller를 붙임으로써 [컨트롤러 클래스]임을 지정한다.
//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
	//로그인에 관련된 URL 주소를 맞이할 메소드 관리
@Controller
public class LoginController {
	
	//==================================================
	//속성 변수 loginDAO 선언하고, LoginDAO라는 인터페이스를 구현한 클래스를 객체화하여 저장
	// 즉, 속성변수 loginDAO에는 LoginDAOImpl 객체의 메위주가 저장되어 있음
	//==================================================
		//@Autowired가 붙은 속성변수에는 인터페이스 자료형을 쓰고
		// 이 인터페이스를 구현한 클래스 하나를 찾아서 객체화하여 저장한다.
		// LoginDAO라는 인터페이스를 구현한 클래스의 이름을 몰라도 관계없음
		// 1개가 존재하기만 하면 된다.
		// 속성변수 옆에 인터페이스가 들어가면 그 인터페이스를 구현한 클래스의 객체가 들어감	
		// 객체와 객체간의 결합도를 약화시킴	
			//=> @Autowired가 붙은 속성변수는 비어있지 않음 : 무조건 객체의 메위주가 들어있음
				//(@Autowired가 없으면 속성변수에 null이 들어있음)
	//==================================================
	//	@Autowired
	//	인터페이스명 속성변수;
	//==================================================
		// 이 인터페이스를 구현한 클래스를 찾아서 객체화한 후 객체의 메위주를 속성변수에 저장한다.
		// 객체의 이름은 무엇이든 상관없다. 인터페이스를 구현한 객체이면 된다.
			// 만약 LoginDAOImpl 객체의 이름을 바꿔도 이 코드는 바꾸지 않아도 됨(유지보수성이 높음)
		// 즉 속성변수에는 null이 저장되어 있지 않다.
		// <주의> 인터페이스를 구현한 객체는 1개여야 한다.
	@Autowired
	private LoginDAO loginDAO; 
		// 속성변수의 역할 : 클래스를 구성하는 동료 속성변수나 동료 메소드가 공용으로 사용할 수 있음
		
	
	//***********************************************
	//가상 주소 /loginForm.do로 접근하면 호출되는 메소드 선언
	//***********************************************
	@RequestMapping( value = "/loginForm.do")
	//메소드 이름은 아무거나 해도 상관 없음
	public ModelAndView loginForm() {
		//System.out.print("짱");
		//return null;
		//----------------------------------------------------
		//[ModelAndView 객체] 생성하기
		//[ModelAndView 객체]에 [호출할 JSP 페이지명]을 저장하기
		//[ModelAndView 객체] 리턴하기
		//----------------------------------------------------
		ModelAndView mav = new ModelAndView();
		// /WEB-INF/views/loginForm.jsp
		mav.setViewName("loginForm.jsp");
		return mav;
			// ModelAndView객체의 메모리 위치 주소 리턴
	};
	
	//=====파라미터값을 꺼내는 방법 1=====
	//HttpServletRequest request를 매개변수로 받아 메소드 안에서 파라미터값을 꺼냄
	//***********************************************
	//가상 주소 /loginProc.do로 접근하면 호출되는 메소드 선언
	//***********************************************
	@RequestMapping( value = "/loginProc.do")
	//메소드 이름은 아무거나 해도 상관 없음
	public ModelAndView loginProc(
			//클라이언트가 보낸 요청메시지를 관리하는 HttpServletRequest 객체가 매개변수로 들어온다.
			//HttpServletRequest 객체의 메소드를 이용하면 파라미터값을 얻을 수 있다.
				HttpServletRequest request
		) {
		System.out.println("LoginController.loginProc 메소드 호출 시작");
		//----------------------------------------------------
		//클이 보낸 요청 메시지 안의 "id"라는 파라미터명의 파라미터 값 꺼내기
		//클이 보낸 아이디를 꺼내라
		//클이 보낸 요청 메시지 안의 "pwd"라는 파라미터명의 파라미터 값 꺼내기
		//클이 보낸 암호를 꺼내라
		//----------------------------------------------------
		String login_id = request.getParameter( "login_id" );
		//System.out.println("LoginController.loginProc.파라미터명.login_id=>"+login_id); // 콘솔창에 띄워서 확인
		String pwd = request.getParameter( "pwd" );
		//System.out.println("LoginController.loginProc=.파라미터명.pwd>"+pwd);

		//----------------------------------------------------
		//HashMap 객체 생성하기
		//HashMap 객체에 로그인 아이디 저장
		//HashMap 객체에 로그인 암호 저장
		//----------------------------------------------------
		Map<String,String> map = new HashMap<String,String>();
		map.put("login_id", login_id);
		map.put("pwd", pwd);
		
		//----------------------------------------------------
		//LoginDAOImpl 객체의 getLogin_idCnt 메소드를 호출하여
		//로그인 아이디와 암호의 전체 개수를 얻기
		//----------------------------------------------------
		System.out.println("LoginController.loginProc=>"+1);
		int login_idCnt = loginDAO.getLogin_idCnt(map);
		System.out.println("LoginController.loginProc=>"+2);
		
		//----------------------------------------------------
		//[ModelAndView 객체] 생성하기
		//[ModelAndView 객체]에 [호출할 JSP 페이지명]을 저장하기
		//[ModelAndView 객체]에 아이디 암호의 존재 개수를 저장하기, DB연동 결과물 저장
			//ModelAndView 객체에 저장된 DB연동 결과물은 HttpServletRequest 객체에 setAttribute 메소드 호출로 저장된다.
		//[ModelAndView 객체] 리턴하기
		//----------------------------------------------------
		ModelAndView mav = new ModelAndView();
		mav.setViewName("loginProc.jsp");
		// 아이디 암호의 존재 개수가 1이라고 가정함(DB연동의 결과물 저장)
		//mav.addObject("idCnt", 1); // new Integer(1) //기본형 데이터가 기본형 관리 객체로 형변환(Autoboxing)
		mav.addObject("idCnt", login_idCnt); // DB연동 결과물 저장
			//위 addObject 메소드로 저장된 DB연동 결과물은 HttpServletRequest 객체에 setAttribute 메소드 호출로도 저장된다.
		System.out.println("LoginController.loginProc 메소드 호출 완료");
		return mav;
	};
	
	//=====파라미터값을 꺼내는 방법 2=====
	// @RequestParam 어노테이션을 사용해 매개변수로 바로 파라미터값을 받음
	//***********************************************
	//가상 주소 /loginProc2.do로 접근하면 호출되는 메소드 선언
	//***********************************************
	@RequestMapping( value = "/loginProc2.do")
	//메소드 이름은 아무거나 해도 상관 없음
	public ModelAndView loginProc2(
			//----------------------------------------------------
			//"login_id"라는 파라미터명에 해당하는 파라미터값을 꺼내서 매개변수 login_id에 저장하고 들어온다.
			//----------------------------------------------------
			@RequestParam( value="login_id") String login_id
			//----------------------------------------------------
			//"pwd"라는 파라미터명에 해당하는 파라미터값을 꺼내서 매개변수 pwd에 저장하고 들어온다.
			//----------------------------------------------------
			, @RequestParam( value="pwd") String pwd
	) {
		//----------------------------------------------------
		//HashMap 객체 생성하기
		//HashMap 객체에 로그인 아이디 저장
		//HashMap 객체에 로그인 암호 저장
		//----------------------------------------------------
		Map<String,String> map = new HashMap<String,String>();
		map.put("login_id", login_id);
		map.put("pwd", pwd);
		
		//----------------------------------------------------
		//LoginDAOImpl 객체의 getLogin_idCnt 메소드를 호출하여
		//로그인 아이디와 암호의 전체 개수를 얻기
		//----------------------------------------------------
		int login_idCnt = loginDAO.getLogin_idCnt(map);
		
		//----------------------------------------------------
		//[ModelAndView 객체] 생성하기
		//[ModelAndView 객체]에 [호출할 JSP 페이지명]을 저장하기
		//[ModelAndView 객체]에 아이디 암호의 존재 개수를 저장하기, DB연동 결과물 저장
			//ModelAndView 객체에 저장된 DB연동 결과물은 HttpServletRequest 객체에 setAttribute 메소드 호출로 저장된다.
		//[ModelAndView 객체] 리턴하기
		//----------------------------------------------------
		ModelAndView mav = new ModelAndView();
		mav.setViewName("loginProc.jsp");
		// 아이디 암호의 존재 개수가 1이라고 가정함(DB연동의 결과물 저장)
		//mav.addObject("idCnt", 1); // new Integer(1) //기본형 데이터가 기본형 관리 객체로 형변환(Autoboxing)
		mav.addObject("idCnt", login_idCnt); // DB연동 결과물 저장
			//위 addObject 메소드로 저장된 DB연동 결과물은 HttpServletRequest 객체에 setAttribute 메소드 호출로도 저장된다.
		return mav;
		
	}
	
}
