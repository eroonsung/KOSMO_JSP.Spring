package com.naver.erp;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
//URL 주소로 접속하면 호출되는 메소드를 소유한 [컨트롤러 클래스]선언
// @Controller를 붙임으로써 [컨트롤러 클래스]임을 지정한다.
//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
	//게시판에 관련된 URL 주소를 맞이할 메소드 관리

@Controller
public class BoardController {
	
	//==================================================
	//속성 변수 boardService 선언하고, BoardService라는 인터페이스를 구현한 클래스를 찾아
	// 객체를 생성해 객체의 메위주를 저장
	// 즉, 속성변수 boardService에는 BoardServiceImpl 객체의 메위주가 저장되어 있음
	//==================================================
	//@Autowired의 역할
		//속성변수에 붙은 자료형인 [인터페이스]를 구현한 [클래스]를 객체화하여 객체의 메위주를 저장
		//[인터페이스]를 구현한 [클래스]가 1개가 아니면(없거나 2개이상 이면) 에러가 발생한다.
		// 만약 0개를 만들었을 때 에러를 발생시키지 않고 null값을 넣고 싶다면
			// -> @Autowired(required=false)로 선언하면 
			//[인터페이스]를 구현한 [클래스가] 0개여도 에러없이 null이 저장됨
	@Autowired
	private BoardService boardService;
	
	//==================================================
	//속성 변수 boardDAO 선언하고, BoardDAO라는 인터페이스를 구현한 클래스를 찾아
	// 객체를 생성해 객체의 메위주를 저장
	// 즉, 속성변수 boardDAO에는 BoardDAOImpl 객체의 메위주가 저장되어 있음
	//==================================================
	@Autowired
	private BoardDAO boardDAO;
	
	//***********************************************
	//가상 주소 /boardList.do로 접근하면 호출되는 메소드 선언
	//	@RequestMapping 내부에 method="RequestMethod.POST"가 없으므로
	//	가상주소 /boardList.do로 접근 시 get 또는 post방식 접근 모두 허용한다.
	//***********************************************
	@RequestMapping(value="/boardList.do")
	public ModelAndView getBoardList() {
		
		//----------------------------------------------------
		//오라클 board테이블 안의 데이터(n행m열)를 검색해서 자바 객체에 저장하기	
		// 즉, [게시판 목록] 얻기
		// 트랜잭션이 걸리지 않기 때문에 @Service, @Transactional 클래스 만들지 않아도 됨
		//----------------------------------------------------
		List<Map<String,String>> boardList = this.boardDAO.getBoardList();
	
		//----------------------------------------------------
		//[ModelAndView 객체] 생성하기
		//[ModelAndView 객체]에 [호출할 JSP 페이지명]을 저장하기
		//[ModelAndView 객체]에 [게시판 목록 검색 결과] 저장하기
		//[ModelAndView 객체] 리턴하기
		//----------------------------------------------------
		ModelAndView mav = new ModelAndView();
		mav.setViewName("boardList.jsp");
		mav.addObject("boardList", boardList);
		return mav;
	}
		
	
	//***********************************************
	//가상 주소 /boardRegForm.do로 접근하면 호출되는 메소드 선언
	//***********************************************
	@RequestMapping(value="/boardRegForm.do")
	public ModelAndView goBoardRegForm() {
		//----------------------------------------------------
		//[ModelAndView 객체] 생성하기
		//[ModelAndView 객체]에 [호출할 JSP 페이지명]을 저장하기
		//[ModelAndView 객체] 리턴하기
		//----------------------------------------------------
		ModelAndView mav = new ModelAndView();
		mav.setViewName("boardRegForm.jsp");
		return mav;
	}
	
	//=====파라미터값을 꺼내는 방법 3=====
	//***********************************************
	//가상 주소 /boardRegProc.do로 접근하면 호출되는 메소드 선언
	//***********************************************
		// 새글쓰기 목적을 가지고 있음
	//@RequestMapping이 붙은 메소드의 매개변수에 DTO를 넣으면
	//파라미터명과 BoardDTO의 속성변수명이 같기만 하면 알아서 속성변수안에 파라미터 값을 저장시킴
	@RequestMapping(value="/boardRegProc.do")
	public ModelAndView insertBoard( 
		//+++++++++++++++++++++++++++++++++++++++++++++++++
		// 파라미터값을 저장할 [BoardDTO 객체를 매개변수로 선언
		//+++++++++++++++++++++++++++++++++++++++++++++++++
			// [파라미터명]과 [BoradDTO 객체]의 [속성변수명]이 같으면
			// setter 메소드가 작동되어 [파라미터값]이 [속성변수]에 저장된다.
			// [속성변수명]에 대응하는 [파라미터명]이 없으면 setter 메소드가 작동되지 않는다.
			// [속성변수명]에 대응하는 [파라미터명]이 있는데 파라미터값이 없으면
				// [속성변수]의 자료형에 관계없이 무조건 null값이 저장된다.
				// 이때 [속성변수]의 자료형이 기본형일 경우 null값이 저장될 수 없어 에러가 발생한다.
				// 이런 에러를 피하려면 파라미터값이 기본형이거나 속성변수의 자료형을 String으로 해야한다.
				// 이런 에러가 발생하면 메소드안의 실행구문은 하나도 실행되지 않음에 주의한다.
			// 매개변수로 들어온 [DTO 객체]는 이 메소드가 끝난 후 호출되는 JSP 페이지로 그대로 이동한다.
			// 즉, HttpServletRequest 객체에 boardDTO라는 키값명으로 저장된다.
		BoardDTO boardDTO 
		//+++++++++++++++++++++++++++++++++++++++++++++++++
		// Error 객체를 관리하는 BindingResult 객체가 저장되어 들어오는 매개변수 bindingResult 선언
		// BindingResult 객체 => 유효성 검사
		//+++++++++++++++++++++++++++++++++++++++++++++++++
		, BindingResult bindingResult
	) {
		//----------------------------------------------------
		//[ModelAndView 객체] 생성하기
		//[ModelAndView 객체]에 [호출할 JSP 페이지명]을 저장하기
		//----------------------------------------------------
		ModelAndView mav = new ModelAndView();
		mav.setViewName("boardRegProc.jsp");
		//예외 처리
		try { 
			/*
			System.out.println(boardDTO.getB_no());
			System.out.println(boardDTO.getWriter());
			System.out.println(boardDTO.getSubject());	
			System.out.println(boardDTO.getEmail());
			System.out.println(boardDTO.getContent());
			System.out.println(boardDTO.getPwd());	
			*/
			//----------------------------------------------------
			//check_BoardDTO 메소드를 호출하여 [유효성 체크]를 하고 경고문자 얻기
			//유효성확인에 실패하면 DB연동을 할 수 없음
				//check_BoardDTO 메소드를 따로 생성하는 이유
					//유효성 체크 -> 댓글달기, 수정 등등 반복적으로 재사용하기 위해
					// 가독성을 위해
			//----------------------------------------------------	
			//유효성 체크 에러 메시지를 저장할 변수 msg 선언
			String msg = "";
			// check_BoardDTO 메소드를 호출하여 유효성 체크하고 에러메시지 문자 얻기
				// boardDTO, bindingResult의 메모리위치주소를 넣어줌
			msg = check_BoardDTO(boardDTO, bindingResult);
			
			//만약 msg안에 ""가 저장되어 있으면, 즉 유효성 체크를 통과했으면 DB연동 진행
			//if(msg.length()==0){
			if( msg.equals("") ) {
				//----------------------------------------------------
				//[BoardServiceImpl 객체]의 insertBoard 메소드 호출로 
				// 게시판 글 입력하고 [게시판 입력 적용행의 개수] 얻기
				//----------------------------------------------------
					// 결과가 1이면(한 행이 들어가면) 성공
				//boardDTO에 파라미터값이 담겨있음
				int boardRegCnt = this.boardService.insertBoard(boardDTO);
				System.out.println("boardRegCnt => "+ boardRegCnt); // DB연동 성공했는지 확인

				//----------------------------------------------------
				//[ModelAndView 객체]에 [게시판 입력 적용행의 개수] 저장하기
				//[ModelAndView 객체]에 유효성 체크 에러메시지 저장하기
				//----------------------------------------------------
				mav.addObject("boardRegCnt", boardRegCnt);
				mav.addObject("msg", msg);

				System.out.println("BoardController.insertBoard 메소드 호출 성공"); 
				
			}
			//만약 msg안에 ""가 저장되어 있지 않으면, 즉 유효성 체크를 통과 못했으면
			else {
				mav.addObject("boardRegCnt", 0);
				mav.addObject("msg", msg);
			}
		}catch(Exception e){
			//위 try 구문에서 에러가 발생하면
			// 아래 코드 실행
			mav.addObject("boardRegCnt", -1);
			mav.addObject("msg", "서버에서 문제 발생. 서버 관리자에게 문의 바람");
		}
		//----------------------------------------------------
		//[ModelAndView 객체] 리턴하기
		//----------------------------------------------------
		return mav;
		}
	

	//******************************************************************	
	//******************************************************************
	//게시판 입력 또는 수정 시 게시판 입력 글의 입력양식의 유효성을 검사하고
	//문제가 있으면 경고 문자를 리턴하는 메소드 선언
	//******************************************************************
	//******************************************************************
	private String check_BoardDTO(BoardDTO boardDTO, BindingResult bindingResult) {
		String checkMsg = "";
		//----------------------------------------------------
		//BoardDTO 객체에 저장된 데이터의 유효성 체크할 BoardValidator 객체 생성하기
		//BoardValidator 객체의 validate 메소드를 호출하여 유효성 체크 실행하기
		//----------------------------------------------------
		BoardValidator boardValidator = new BoardValidator();
			// 메소드만 호출 (리턴값이 없음)
		boardValidator.validate(
				boardDTO  // 유효성을 체크할 DTO 객체
				, bindingResult // 유효성 체크 결과를 관리하는 BindingResult 객체
		);
		
		//----------------------------------------------------
		//만약 BindingResult 객체의 hasErrors() 메소드를 호출하여 true값을 얻으면(유효성 체크에 문제 발생)
		//----------------------------------------------------
		if(bindingResult.hasErrors()) {
			// 변수 checkMsg에 BoardValidator 객체에 저장된 경고문구 얻어 저장하기 
			checkMsg = bindingResult.getFieldError().getCode();
		}		
		//----------------------------------------------------
		//checkMsg안의 문자 리턴하기
		//----------------------------------------------------
		return checkMsg;
	}
	
	
	//***********************************************
	//가상 주소 /boardContentForm.do로 접근하면 호출되는 메소드 선언
	//***********************************************
	@RequestMapping(value="/boardContentForm.do")
	public ModelAndView goBoardContentForm(
			@RequestParam(value="b_no") int b_no // "b_no"는 원래 숫자 문자이지만 spring이 알아서 바꿔줌
			) {
		//----------------------------------------------------
		// [BoardServiceImpl 객체]의 getBoard 메소드 호출로 [1개의 게시판글]을 BoardDTO 객체에 담아오기
		//----------------------------------------------------
		//BoardDTO board = this.boardService.getBoard(b_no);
			//readcount가 증가하기 때문에 update구문이 있어 트랜잭션이 걸림
		
		//----------------------------------------------------
		//[ModelAndView 객체] 생성하기
		//[ModelAndView 객체]에 [호출할 JSP 페이지명]을 저장하기
		//[ModelAndView 객체]에 DB연동 결과물 담기 
		//[ModelAndView 객체] 리턴하기
		//----------------------------------------------------
		ModelAndView mav = new ModelAndView();
		mav.setViewName("boardContentForm.jsp");
		mav.addObject("b_no", b_no);
		return mav;
	}
}
