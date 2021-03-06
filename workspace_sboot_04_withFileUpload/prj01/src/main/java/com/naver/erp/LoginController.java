package com.naver.erp;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Cookie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
//URL 주소로 접속하면 호출되는 메소드를 소유한 [컨트롤러 클래스]선언
// @Controller를 붙임으로써 [컨트롤러 클래스]임을 지정한다.
//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
	//로그인에 관련된 URL 주소를 맞이할 메소드 관리
@Controller
@RequestMapping(value="/naver")
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
		

	private String path = Info.naverPath;
	
	
	
	//***********************************************
	//가상 주소 /loginForm.do로 접근하면 호출되는 메소드 선언
	//***********************************************
	@RequestMapping( value = "/loginForm.do")
	//메소드 이름은 아무거나 해도 상관 없음
	public ModelAndView loginForm(
		HttpSession session	
		) {
		
		//System.out.print("짱");
		//return null;
		//----------------------------------------------------
		//[ModelAndView 객체] 생성하기
		//[ModelAndView 객체]에 [호출할 JSP 페이지명]을 저장하기
		//[ModelAndView 객체] 리턴하기
		//----------------------------------------------------
		ModelAndView mav = new ModelAndView();
		// /WEB-INF/views/loginForm.jsp
		mav.setViewName(path+"loginForm.jsp");
		return mav;
			// ModelAndView객체의 메모리 위치 주소 리턴
	};
	


	//***********************************************
	//가상 주소 /loginProc3.do로 접근하면 호출되는 메소드 선언
	//DB연동 결과물을 json 형태로 바로 클라이언트에게 보냄
		// ModelAndView 객체를 리턴하지 않음
	//	메소드 앞에 
	//	@RequestMapping(~,~,, produces = "application/json;charset=UTF-8")와
	//	@ResponseBody가 붙으면 리턴하는 데이터가 클라이언트에게 전송된다.
	//------------------------------------------------
	//즉, ModelAndView 객체를 리턴하면 jsp페이지를 호출하고 그 jsp페이지의 실행결과인 
	//		HTML문서가 응답메시지에 저장되어 전송되지만
	//@RequestMapping(~)와 @ResponseBody가 붙으면 리턴하는 데이터가 
	//		JSON형태로 응답메시지에 저장되어 전송된다
	//***********************************************
	@RequestMapping( 
			value = "/loginProc.do"
			, method = RequestMethod.POST
			, produces = "application/json;charset=UTF-8" 
	)
	@ResponseBody
	public int loginProc(
			//----------------------------------------------------
			//"login_id"라는 파라미터명에 해당하는 파라미터값을 꺼내서 매개변수 login_id에 저장하고 들어온다.
			//"pwd"라는 파라미터명에 해당하는 파라미터값을 꺼내서 매개변수 pwd에 저장하고 들어온다.
			//"is_login"라는 파라미터명에 해당하는 파라미터값을 꺼내서 매개변수 is_login에 저장하고 들어온다.
			//		required=false => 파라미터명, 값이 안들어와도 용서함
			// HttpSession 객체의 메위주를 저장하는 매개변수 session 선언하기
			// HttpServletResponse 객체의 메위주를 저장하는 매개변수 response 선언하기
			//----------------------------------------------------
			@RequestParam( value="login_id") String login_id	// default (required = true)
			, @RequestParam( value="pwd") String pwd	
			, @RequestParam( value="is_login", required=false ) String is_login
			, HttpSession session		
			, HttpServletResponse response
	) {
		//System.out.println("LoginController.loginProc 메소드 실행");//-> 실행되지 않으면 매개변수 문제
		//----------------------------------------------------
		//HashMap 객체 생성하기 => 아이디 암호를 하나의 박스에 저장
		// 아이디 암호를 해시맵에 저장하는 이유 : 한곳에 집어넣어야 mapper에 전달할 수 있기 때문(단일화)
			// 쿼리문에 참여하는 데이터는 형식적으로 하나 유형의 데이터여야 함
		//HashMap 객체에 로그인 아이디 저장
		//HashMap 객체에 로그인 암호 저장
		//----------------------------------------------------
		Map<String,String> map = new HashMap<String,String>();
		map.put("login_id", login_id);
		map.put("pwd", pwd);
		
		
		//----------------------------------------------------
		//LoginDAOImpl 객체의 getLogin_idCnt 메소드를 호출하여
		//로그인 아이디와 암호의 전체 개수를 얻기
			// @Service 층을 거치지 않고 @Repository 층으로 바로 가는 이유
			// : 트랜잭션이 필요없기 때문에
		//----------------------------------------------------
		int login_idCnt = this.loginDAO.getLogin_idCnt(map);
		
		
		//----------------------------------------------------
		//만약 login_idCnt변수 안의 데이터가 1이면
		// 즉 만약 입력한 아이디, 암호가 DB에 존재하면
		// 즉 만약 로그인이 성공하면
		//----------------------------------------------------
		if( login_idCnt==1 ) {
			//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
			//HttpSession 객체에 로그인 아이디 저장하기
			//HttpSession 객체에 로그인 아이디를 저장하면 재접속했을때 다시 꺼낼 수 있음
				//HttpSession 객체에 넣는 이유 : 재접속할때마다 로그인 했는지 확인하기 위해서
			//<참고> HttpSession 객체는 접속한 이후에도 제거되지 않고 지정된 기간동안 살아있는 객체이다.
			//<참고> HttpServletRequest, HttpServletResponse 객체는 접속할때 생성되고 응답 이후 삭제되는 객체
			//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
			session.setAttribute("login_id", login_id);
			
			//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
			//매개변수 is_login에 null이 저장되어 있으면(=[아이디, 암호 자동 입력]의사가 없을 경우)
			//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
			if(is_login==null) {
				//==============================================
				//웹서버가 클라이언트가 가지고 있는 쿠키를 지울 수 없기 때문에(제거할 수 없기 때문에)
				//  클라이언트가 가지고 있는 쿠키를 null값으로 덮어씌움
				//	=> 마치 제거한 것과 같은 효과
				//==============================================
					//(쿠키명, 쿠키값, 살아있을 시간, HttpServletResponse 객체)
				
				// 쿠키명 "login_id"에 쿠키값 null로 응답메시지에 쿠키 저장하기
				// 응답메시지에 저장된 쿠키를 클라이언트쪽에 저장, 이미 존재하면 덮어씀
				Util.addCookie("login_id", null, 0, response);
				Util.addCookie("pwd", null, 0, response);
				
				/*Cookie cookie1 = new Cookie("login_id", null);
				cookie1.setMaxAge(0);
				response.addCookie(cookie1);*/
			}
			//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
			//매개변수 is_login에 'yes'가 저장되어 있으면(=[아이디, 암호 자동 입력]의사가 있을 경우)
			//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
			else {
				//==============================================
				//클라이언트가 보낸 아이디, 암호를 응답메시지에 쿠키명과 쿠키값으로 저장하기
				//하나의 쿠키객체에 하나의 쿠키명과 쿠키값을 저장함
				//==============================================
				Util.addCookie("login_id", login_id, 60*60*24, response);
				Util.addCookie("pwd", pwd, 60*60*24, response);
			}
			//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
			//HttpSession 객체에 메시지 저장하기
			//HttpSession 객체에 저장된 데이터는 모든 JSP 페이지에서 ${sessionScope.키값명}으로 꺼내 표현할 수 있다.
			//<참고> sessionScope은 생략 가능함 (먼저 sessionScope을 붙여서 찾은 이후 requestScope를 붙여서 찾음)
			//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
			session.setAttribute("msg", "짱짱");
		}
		
		
		//----------------------------------------------------
		//로그인 아이디와 암호의 존재 개수 리턴하기
		//----------------------------------------------------
		return login_idCnt;
	}		
	
	//***********************************************
	//가상 주소 /logout.do로 접근하면 호출되는 메소드 선언
	//***********************************************
	@RequestMapping( value = "/logout.do")
	public ModelAndView logout(
			HttpSession session
	) {
		//----------------------------------------------------
		//HttpSession 객체에 "login_id"라는 키값으로 저장된 데이터 삭제
		//HttpSession 객체에 로그인 성공 후 저장된 아이디값을 지우기
		// 즉 HttpSession 객체에 저장된 로그인 정보를 지우기
		//----------------------------------------------------
		session.removeAttribute("login_id"); 		
		
		//----------------------------------------------------
		//[ModelAndView 객체] 생성하기
		//[ModelAndView 객체]에 [호출할 JSP 페이지명]을 저장하기
		//[ModelAndView 객체] 리턴하기
		//----------------------------------------------------
		ModelAndView mav = new ModelAndView();
		mav.setViewName(path+"logout.jsp");
		return mav;	
	}
	
	//***********************************************
	//가상 주소 /login_alert.do로 접근하면 호출되는 메소드 선언
	//***********************************************
	@RequestMapping( value = "/login_alert.do")
	public ModelAndView login_alert() {
		
		//----------------------------------------------------
		//[ModelAndView 객체] 생성하기
		//[ModelAndView 객체]에 [호출할 JSP 페이지명]을 저장하기
		//[ModelAndView 객체] 리턴하기
		//----------------------------------------------------
		ModelAndView mav = new ModelAndView();
		mav.setViewName(path+"login_alert.jsp");
		return mav;	
	}
}















/*
 * 	//=====파라미터값을 꺼내는 방법 1=====
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
				// HttpSession 객체의 메위주를 저장하는 매개변수 session 선언하기
				, HttpSession session
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
		//만약 login_idCnt변수 안의 데이터가 1이면
		// 즉 만약 입력한 아이디, 암호가 DB에 존재하면
		// 즉 만약 로그인이 성공하면
		//----------------------------------------------------
		if( login_idCnt==1 ) {
			//HttpSession 객체에 로그인 아이디 저장하기
			//HttpSession 객체에 로그인 아이디를 저장하면 재접속했을때 다시 꺼낼 수 있음
			//<참고> HttpSession 객체는 접속한 이후에도 제거되지 않고 지정된 기간동안 살아있는 객체이다.
			//<참고> HttpServletRequest, HttpServletResponse 객체는 접속할때 생성되고 응답 이후 삭제되는 객체
			session.setAttribute("login_id", login_id);
		}
		
		//----------------------------------------------------
		//[ModelAndView 객체] 생성하기
		//[ModelAndView 객체]에 [호출할 JSP 페이지명]을 저장하기
		//[ModelAndView 객체]에 아이디 암호의 존재 개수를 저장하기, DB연동 결과물 저장
			//ModelAndView 객체에 저장된 DB연동 결과물은 HttpServletRequest 객체에 setAttribute 메소드 호출로 저장된다.
		//[ModelAndView 객체] 리턴하기
		//----------------------------------------------------
		ModelAndView mav = new ModelAndView();
		//mav.setViewName("loginProc.jsp");
		mav.setViewName("loginProc2.jsp");
		// 아이디 암호의 존재 개수가 1이라고 가정함(DB연동의 결과물 저장)
		//mav.addObject("idCnt", 1); // new Integer(1) //기본형 데이터가 기본형 관리 객체로 형변환(Autoboxing)
		mav.addObject("login_idCnt", login_idCnt); // DB연동 결과물 저장
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
			//----------------------------------------------------
			//"is_login"라는 파라미터명에 해당하는 파라미터값을 꺼내서 매개변수 is_login에 저장하고 들어온다.
			// required=false => 파라미터명, 값이 안들어와도 용서함
			//----------------------------------------------------			
			, @RequestParam( value="is_login", required=false ) String is_login
			//----------------------------------------------------
			// HttpSession 객체의 메위주를 저장하는 매개변수 session 선언하기
			//----------------------------------------------------
			, HttpSession session
			//----------------------------------------------------
			// HttpServletResponse 객체의 메위주를 저장하는 매개변수 response 선언하기
			//----------------------------------------------------			
			, HttpServletResponse response
	) {
		//----------------------------------------------------
		//HashMap 객체 생성하기 => 아이디 암호를 하나의 박스에 저장
		// 아이디 암호를 해시맵에 저장하는 이유 : 한곳에 집어넣어야 mapper에 전달할 수 있기 때문(단일화)
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
		//만약 login_idCnt변수 안의 데이터가 1이면
		// 즉 만약 입력한 아이디, 암호가 DB에 존재하면
		// 즉 만약 로그인이 성공하면
		//----------------------------------------------------
		if( login_idCnt==1 ) {
			//HttpSession 객체에 로그인 아이디 저장하기
			//HttpSession 객체에 로그인 아이디를 저장하면 재접속했을때 다시 꺼낼 수 있음
			//<참고> HttpSession 객체는 접속한 이후에도 제거되지 않고 지정된 기간동안 살아있는 객체이다.
			//<참고> HttpServletRequest, HttpServletResponse 객체는 접속할때 생성되고 응답 이후 삭제되는 객체
			session.setAttribute("login_id", login_id);
			
			//System.out.println("is_login=>"+is_login);
			
			//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
			//매개변수 is_login에 null이 저장되어 있으면(=[아이디, 암호 자동 입력]의사가 없을 경우)
			//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
			if(is_login==null) {
				//==============================================
				//웹서버가 클라이언트가 가지고 있는 쿠키를 지울 수 없기 때문에(제거할 수 없기 때문에)
				//  클라이언트가 가지고 있는 쿠키를 null값으로 덮어씌움
				//	=> 마치 제거한 것과 같은 효과
				//==============================================
				
				// Coockie 객체를 생성하고 쿠키명-쿠키값을 [ "login_id"-null ]로 하기
				Cookie cookie1 = new Cookie("login_id", null);
				// Cookie 객체에 저장된 쿠키의 수명은 0으로 하기
				cookie1.setMaxAge(0);
				
				// Coockie 객체를 생성하고 쿠키명-쿠키값을 [ "pwd"-null ]로 하기
				Cookie cookie2 = new Cookie("pwd", null);
				// Cookie 객체에 저장된 쿠키의 수명은 0으로 하기
				cookie2.setMaxAge(0);
				
				//Cookie객체가 소유한 쿠키를 응답메시지에 저장하기
				// 결국 Cookie 객체가 소유한 쿠키명-쿠키값이 응답메시지에 저장되는 것
				// 응답메시지에 저장된 쿠키는 클라이언트 쪽으로 전송되어 클라이언트쪽에 저장된다.
				response.addCookie(cookie1);
				response.addCookie(cookie2);
				
				Util.addCookie("login_id", null, 0, response);
				Util.addCookie("pwd", null, 0, response);
				
				
			}
			//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
			//매개변수 is_login에 'yes'가 저장되어 있으면(=[아이디, 암호 자동 입력]의사가 있을 경우)
			//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
			else {
				//==============================================
				//클라이언트가 보낸 아이디, 암호를 응답메시지에 쿠키명과 쿠키값으로 저장하기
				//하나의 쿠키객체에 하나의 쿠키명과 쿠키값을 저장함
				//==============================================
				
				//Cookie객체를 생성하고 쿠키명-쿠키값을 ["login_id"-"입력아이디"]로 하기
				Cookie cookie1 = new Cookie("login_id", login_id);
				//Cookie 객체에 저장된 쿠키의 수명은 60*60*24(하루)로 하기
				cookie1.setMaxAge(60*60*24);
				
				//Cookie객체를 생성하고 쿠키명-쿠키값을 ["pwd"-"입력암호"]로 하기
				Cookie cookie2 = new Cookie("pwd", pwd);
				//Cookie 객체에 저장된 쿠키의 수명은 60*60*24(하루)로 하기
				cookie2.setMaxAge(60*60*24);
				
				//Cookie객체가 소유한 쿠키를 응답메시지에 저장하기
				response.addCookie(cookie1);
				response.addCookie(cookie2);
				
				Util.addCookie("login_id", login_id, 60*60*24, response);
				Util.addCookie("pwd", pwd, 60*60*24, response);
			}
		}
		
		//----------------------------------------------------
		//[ModelAndView 객체] 생성하기
		//[ModelAndView 객체]에 [호출할 JSP 페이지명]을 저장하기
		//[ModelAndView 객체]에 아이디 암호의 존재 개수를 저장하기, DB연동 결과물 저장
			//ModelAndView 객체에 저장된 DB연동 결과물은 HttpServletRequest 객체에 setAttribute 메소드 호출로 저장된다.
		//[ModelAndView 객체] 리턴하기
		//----------------------------------------------------
		ModelAndView mav = new ModelAndView();
		//mav.setViewName("loginProc.jsp");
		mav.setViewName("loginProc2.jsp");
		// 아이디 암호의 존재 개수가 1이라고 가정함(DB연동의 결과물 저장)
		//mav.addObject("idCnt", 1); // new Integer(1) //기본형 데이터가 기본형 관리 객체로 형변환(Autoboxing)
		mav.addObject("login_idCnt", login_idCnt); // DB연동 결과물 저장
			//위 addObject 메소드로 저장된 DB연동 결과물은 HttpServletRequest 객체에 setAttribute 메소드 호출로도 저장된다.
		return mav;	
	}	
	
 * 
 * */
