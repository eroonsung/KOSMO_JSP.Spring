package com.naver.erp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
//URL 주소로 접속하면 호출되는 메소드를 소유한 [컨트롤러 클래스]선언
// @Controller를 붙임으로써 [컨트롤러 클래스]임을 지정한다.
//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
	//게시판에 관련된 URL 주소를 맞이할 메소드 관리

@Controller
@RequestMapping(value="/naver")
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
	
	//==================================================
	//속성변수 path 선언하고 Info 인터페이스의 속성변수 naverPath 안의 데이터를 저장하기
	// Info 인터페이스의 속성변수 naverPath 안에는 "naver/"가 저장되어 있다.
	//==================================================	
	private String path = Info.naverPath;
	
	
	//***********************************************
	//@RequestMapping이 붙은 메소드가 호출되기 전에 호출되는 메소드 선언
	//@ModelAttribute가 붙은 메소드는 @RequestMapping이 붙은 메소드가 호출되기 전에 호출되는 메소드이다.
	//@ModelAttribute("키값명")이 붙은 메소드가 리턴하는 데이터는
	//스프링이 HttpServletRequest 객체에 setAttribute( "키값명", 리턴데이터 ) 메소드를 호출하므로
	//@RequestMapping(~)이 붙은 메소드는 호출 후에 이동하는 JSP페이지에서 ${requestScope.키값명}으로 꺼낼 수 있다.
	//***********************************************
	@ModelAttribute("totCnt")
	public int getTotCnt( BoardSearchDTO boardSearchDTO ) {
		int totCnt = this.boardDAO.getBoardListAllCnt(boardSearchDTO);
		return totCnt;
	}

	//***********************************************
	//가상 주소 /boardList.do로 접근하면 호출되는 메소드 선언
	//	@RequestMapping 내부에 method="RequestMethod.POST"가 없으므로
	//	가상주소 /boardList.do로 접근 시 get 또는 post방식 접근 모두 허용한다.
	//***********************************************
	//	메소드 안에서 페이징 처리
	//***********************************************
	//@RequestMapping(value="/boardList.do")
	public ModelAndView getBoardList(
			//+++++++++++++++++++++++++++++++++++++++++++++++++
			// 파라미터값을 저장하고 있는 BoardSearchDTO객체를 받아오는 매개변수 선언
			//+++++++++++++++++++++++++++++++++++++++++++++++++
			BoardSearchDTO boardSearchDTO
			
			//,HttpSession session
		) throws Exception {
		/*
		//----------------------------------------------------
		//로그인 성공 여부를 확인하기
		//----------------------------------------------------
		//HttpSession 객체에 저장된 로그인 아이디를 꺼내기
		String login_id = (String)session.getAttribute("login_id");
		
		// 만약 login_id 변수 안에 null이 저장되어 있으면
		// 즉 만약 로그인에 성공한 적이 없으면
		if(login_id==null) {
			ModelAndView mav = new ModelAndView();
			mav.setViewName("logout.jsp");
			return mav;
		}
		*/
		
		//----------------------------------------------------
		//검색 조건에 맞는 모든 행의 개수
		// 즉 [검색된 게시판 목록의 총 개수] 얻기
		// 총 개수를 먼저 구해야함! 총 개수에 따라 페이징 처리 결과가 달라지기 때문
		//----------------------------------------------------
		int boardListAllCnt = this.boardDAO.getBoardListAllCnt(boardSearchDTO);
		

	
		//반복되는 코드(페이징처리하는 코드)=> 따로 클래스 생성가능
		//----------------------------------------------------
		//[마지막 페이지 번호]
		//[현재 화면에 보여지는 페이지 번호의 최소 페이지 번호]
		//[현재 화면에 보여지는 페이지 번호의 최대 페이지 번호]
		//BoardSearchDTO 객체에 저장된 [선택한 페이지 번호]
		//BoardSearchDTO 객체에 저장된 [한화면에 보여줄 행의 개수]
		//[한화면에 보여줄 페이지 번호의 개수] 구하기
		//----------------------------------------------------
		int last_pageNo = 0;
		int min_pageNo = 0;
		int max_pageNo = 0;
		int selectPageNo = boardSearchDTO.getSelectPageNo();
		int rowCntPerPage = boardSearchDTO.getRowCntPerPage();
		int pageNoCntPerPage = 10;
		
		//----------------------------------------------------
		//만약 검색된 결과물의 개수가 0보다 크면 즉, 검색 결과물이 있으면
		//----------------------------------------------------
		if( boardListAllCnt>0 ) { // boardListAllCnt가 0이면 작동 안함
			// [마지막 페이지 번호] 구하기
			last_pageNo = boardListAllCnt/rowCntPerPage;
				if( boardListAllCnt%rowCntPerPage>0 ) { last_pageNo++; }
			//만약 선택한 페이지 번호가 마지막 페이지 번호보다 크면	
			if(selectPageNo>last_pageNo) {
				//변수에 1 저장하기
				selectPageNo = 1;
				//boardSearchDTO객체의 selectPageNo 속성변수에 1 저장하기
					// -> 오라클 쪽으로 보내기 위해서
				boardSearchDTO.setSelectPageNo(selectPageNo);
			}
			// [현재 화면에 보여지는 페이지 번호의 최소 페이지 번호] 구하기
			min_pageNo = ((selectPageNo-1)/pageNoCntPerPage)*pageNoCntPerPage+1;
			// [현재 화면에 보여지는 페이지 번호의 최대 페이지 번호] 구하기
			max_pageNo =  min_pageNo+pageNoCntPerPage-1;
				if( max_pageNo>last_pageNo ){
					max_pageNo = last_pageNo ;
				}
		}
		
		
		
		//정순번호
		int start_serial_no = (selectPageNo-1)*rowCntPerPage+1;
		//System.out.println("BoardController.getBoardList 메소드 호출 시작");
		//----------------------------------------------------
		//오라클 board테이블 안의 데이터(n행m열)를 검색해서 자바 객체에 저장하기	
		// 즉, [검색조건에 맞는 게시판 목록] 얻기
		// 트랜잭션이 걸리지 않기 때문에 @Service, @Transactional 클래스 만들지 않아도 됨
		//----------------------------------------------------
		List<Map<String,String>> boardList = this.boardDAO.getBoardList(boardSearchDTO);
		
		//----------------------------------------------------
		//[ModelAndView 객체] 생성하기
		//[ModelAndView 객체]에 [호출할 JSP 페이지명]을 저장하기
		//[ModelAndView 객체]에 [게시판 목록 검색 결과] 저장하기
		//[ModelAndView 객체]에 [게시판 목록 검색 결과의 총 개수] 저장하기
		//[ModelAndView 객체]에 [마지막 페이지 번호]를 저장하기
		//[ModelAndView 객체]에 [현재 화면에 보여지는 페이지 번호의 최소 페이지 번호]를 저장하기
		//[ModelAndView 객체]에 [현재 화면에 보여지는 페이지 번호의 최대 페이지 번호]를 저장하기
		//[ModelAndView 객체]에 [선택한 페이지 번호]를 저장하기
		//[ModelAndView 객체]에 [한 화면에 보여줄 행의 개수]를 저장하기
		//[ModelAndView 객체]에 [한 화면에 보여줄 페이지의 개수]를 저장하기
		//----------------------------------------------------
		ModelAndView mav = new ModelAndView();
		//mav.setViewName("boardList.jsp");
		mav.setViewName(path+"boardList.jsp");
		mav.addObject("boardList", boardList);
		mav.addObject("boardListAllCnt", boardListAllCnt);
		
		
		mav.addObject("last_pageNo", last_pageNo);
		mav.addObject("min_pageNo", min_pageNo);
		mav.addObject("max_pageNo", max_pageNo);
		
		mav.addObject("selectPageNo", selectPageNo);
		mav.addObject("rowCntPerPage", rowCntPerPage);
		mav.addObject("pageNoCntPerPage", pageNoCntPerPage);
		
		mav.addObject("start_serial_no", start_serial_no);
		
		
		//System.out.println("BoardController.getBoardList 메소드 호출 완료");
		//----------------------------------------------------
		//[ModelAndView 객체] 리턴하기
		//----------------------------------------------------
		return mav;
	}
	
	
	//***********************************************
	//	위 getBoardList 메소드 안의 내용을
	// Util에 있는 페이징 처리 getPagingNos 메소드 사용해서 작성
	//***********************************************
	@RequestMapping(value="/boardList.do")
	public ModelAndView getBoardList2(
			//+++++++++++++++++++++++++++++++++++++++++++++++++
			// 파라미터값을 저장하고 있는 BoardSearchDTO객체를 받아오는 매개변수 선언
			//+++++++++++++++++++++++++++++++++++++++++++++++++
			BoardSearchDTO boardSearchDTO
			
			//,HttpSession session
		) throws Exception {
		
		//----------------------------------------------------
		//검색 조건에 맞는 모든 행의 개수
		// 즉 [검색된 게시판 목록의 총 개수] 얻기
		// 총 개수를 먼저 구해야함! 총 개수에 따라 페이징 처리 결과가 달라지기 때문
		//----------------------------------------------------
		int boardListAllCnt = this.boardDAO.getBoardListAllCnt(boardSearchDTO);
		
		//----------------------------------------------------
		//페이징처리 관련 번호가 저장된 HashMap<String,Integer> 객체 구하기
		//----------------------------------------------------
		int selectPageNo = boardSearchDTO.getSelectPageNo();
		int rowCntPerPage = boardSearchDTO.getRowCntPerPage();
		int pageNoCntPerPage = 10;
		
		Map<String,Integer> map= Util.getPagingNos(boardListAllCnt
				, pageNoCntPerPage
				, selectPageNo
				, rowCntPerPage);
		
		//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		//HashMap<String,Integer> 객체에 저장된 보정된 선택된 페이지 번호를
		//BoardSearchDTO객체의 setSelectPageNo메소드 호출로 덮어씌우기
		boardSearchDTO.setSelectPageNo(map.get("selectPageNo"));
		
		//정순번호
		int start_serial_no = (selectPageNo-1)*rowCntPerPage+1;
		
		//System.out.println("BoardController.getBoardList 메소드 호출 시작");
		//----------------------------------------------------
		//오라클 board테이블 안의 데이터(n행m열)를 검색해서 자바 객체에 저장하기	
		// 즉, [검색조건에 맞는 게시판 목록] 얻기
		// 트랜잭션이 걸리지 않기 때문에 @Service, @Transactional 클래스 만들지 않아도 됨
		//----------------------------------------------------
		List<Map<String,String>> boardList = this.boardDAO.getBoardList(boardSearchDTO);
		
		//----------------------------------------------------
		//[ModelAndView 객체] 생성하기
		//[ModelAndView 객체]에 [호출할 JSP 페이지명]을 저장하기
		//[ModelAndView 객체]에 [게시판 목록 검색 결과] 저장하기
		//[ModelAndView 객체]에 [게시판 목록 검색 결과의 총 개수] 저장하기
		//[ModelAndView 객체]에 [페이징 처리 관련 번호가 저장된 HashMap<String,Integer> 객체]를 저장하기
		//----------------------------------------------------
		ModelAndView mav = new ModelAndView();
		//mav.setViewName("boardList.jsp");
		mav.setViewName(path+"boardList.jsp");
		mav.addObject("boardList", boardList);
		mav.addObject("boardListAllCnt", boardListAllCnt);
		
		mav.addObject("start_serial_no", start_serial_no);
		
		mav.addObject("pagingNos",map );
		
		//----------------------------------------------------
		//[ModelAndView 객체] 리턴하기
		//----------------------------------------------------
		return mav;
	}
	
	

	//***********************************************
	//가상 주소 /boardRegForm.do로 접근하면 호출되는 메소드 선언
	// 새글쓰기 + 댓글쓰기
	//***********************************************
	@RequestMapping(value="/boardRegForm.do")
	public ModelAndView goBoardRegForm(
			//@RequestParam(value="b_no") int b_no 
				//=> 반드시 b_no라는 파라미터명이 있어야 함
				// 새글쓰기에는 b_no값이 없기 때문에 에러 발생
			//+++++++++++++++++++++++++++++++++++++++++++++++++
			// 파라미터명이 b_no인 파라미터값을 받아오는 매개변수 b_no 선언하기
			//+++++++++++++++++++++++++++++++++++++++++++++++++
			//방법 1
			//HttpServletRequest request
				//=> 조건문 사용해서 사용 가능
			//방법 2
			@RequestParam(
					value="b_no" // 파라미터명 설정
					, required=false // 파라미터명, 값이 안들어와도 용서함
					, defaultValue="0" // 파라미터값이 없으면 파라미터값을 0으로 함
				) int b_no 
				// required=false : 필수 입력 조건을 풀어줌
				// defaultValue="0" : 파라미터 값이 안들어왔을 때 null값이 들어오는 것을 방지
		) { 
		
		/* HttpServletRequest request를 매개변수로 받았을 때
		 	String b_no = request.getParameter("b_no");
		 	if(b_no==null){
		 		// 새글쓰기 코딩
		 	}else if{
		 		//댓글쓰기 코딩
		 	}
		 */
		//----------------------------------------------------
		//[ModelAndView 객체] 생성하기
		//[ModelAndView 객체]에 [호출할 JSP 페이지명]을 저장하기
		//[ModelAndView 객체] 리턴하기
		//----------------------------------------------------
		ModelAndView mav = new ModelAndView();
		mav.setViewName(path+"boardRegForm.jsp");
		return mav;
	}
	
	//***********************************************
	//가상 주소 /boardRegProc.do로 접근하면 호출되는 메소드 선언
	//DB연동 결과물을 json 형태로 바로 클라이언트에게 보냄
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
			value = "/boardRegProc.do"
			, method = RequestMethod.POST
			, produces = "application/json;charset=UTF-8" 
	)
	@ResponseBody
	public Map<String,String> insertBoard( 
		//+++++++++++++++++++++++++++++++++++++++++++++++++
		// 파라미터값을 저장할 [BoardDTO 객체]를 매개변수로 선언
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
		// <input type="file name=img> 입력양식의 파일이 저장된 MultipartFile 객체 저장 매개변수 선언
		// <주의> 업로드된 파일이 없어도 MultipartFile객체는 생성되어 들어온다.
		//+++++++++++++++++++++++++++++++++++++++++++++++++
		, @RequestParam("img") MultipartFile multi
		
		//+++++++++++++++++++++++++++++++++++++++++++++++++
		// Error 객체를 관리하는 BindingResult 객체가 저장되어 들어오는 매개변수 bindingResult 선언
		// BindingResult 객체 => 유효성 검사
		//+++++++++++++++++++++++++++++++++++++++++++++++++
		, BindingResult bindingResult
	) {
		
		//----------------------------------------------------
		//업로드 된 파일의 크기와 확장자 체크하기
		//----------------------------------------------------
		//만약에 업로드된 파일이 있으면
		if(multi.isEmpty()==false) {
			//만약에 업로드된 파일의 크기가 1,000,000 byte(=1,000KB)보다 크면
			if(multi.getSize()>1000000) {
				Map<String,String> map = new HashMap<String, String>();
				map.put("boardRegCnt", "0");
				map.put("msg", "업로드 파일이 1000kb보다 크면 안됩니다.");
				return map; // return이 나오면 메소드 중단, 오른쪽에 값이 있으면 메소드 호출한 쪽으로 던져줌
			}
			
			//만약에 업로드된 파일의 확장자가 이미지 확장자가 아니면
			String fileName=multi.getOriginalFilename();
			
			if(fileName.endsWith(".jpg")==false && fileName.endsWith(".gif")==false && fileName.endsWith(".png")==false) {
				Map<String,String> map = new HashMap<String, String>();
				map.put("boardRegCnt", "0");
				map.put("msg", "이미지 파일이 아닙니다.");
				return map;
			}
		}
		
		//----------------------------------------------------
		//게시판 등록 성공 여부가 저장된 변수 선언->1이 저장되면 성공했다는 의미
		//유효성 체크 에러 메시지를 저장할 수 있는 변수 msg 선언
		//----------------------------------------------------
		int boardRegCnt=0;
		//유효성 체크 에러 메시지를 저장할 변수 msg 선언
		String msg = "";
		
		//예외 처리
		try { 
			//----------------------------------------------------
			//check_BoardDTO 메소드를 호출하여 [유효성 체크]를 하고 경고문자 얻기
			//유효성확인에 실패하면 DB연동을 할 수 없음
				//check_BoardDTO 메소드를 따로 생성하는 이유
					//유효성 체크 -> 댓글달기, 수정 등등 반복적으로 재사용하기 위해
					// 가독성을 위해
			//----------------------------------------------------	
			// check_BoardDTO 메소드를 호출하여 유효성 체크하고 에러메시지 문자 얻기
				// boardDTO, bindingResult의 메모리위치주소를 넣어줌
			msg = check_BoardDTO(boardDTO, bindingResult);
			
			//----------------------------------------------------
			//만약 msg안에 ""가 저장되어 있으면, 즉 유효성 체크를 통과했으면 DB연동 진행
			//----------------------------------------------------
			//if(msg.length()==0){
			if( msg.equals("") ) {
				//----------------------------------------------------
				//[BoardServiceImpl 객체]의 insertBoard 메소드 호출로 
				// 게시판 글 입력하고 [게시판 입력 적용행의 개수] 얻기
				//----------------------------------------------------
					// 결과가 1이면(한 행이 들어가면) 성공
				//boardDTO에 파라미터값이 담겨있음
				boardRegCnt = this.boardService.insertBoard(boardDTO, multi);
				//System.out.println("boardRegCnt => "+ boardRegCnt); // DB연동 성공했는지 확인
				
				//System.out.println("BoardController.insertBoard 메소드 호출 성공"); 				
			}
		}catch(Exception e){
			//위 try 구문에서 에러가 발생하면
			// 아래 코드 실행
			boardRegCnt = -1;
			msg="서버에서 문제 발생! 서버관리자에게 문의 바람";
		}
		//----------------------------------------------------
		//HashMap<String,String>객체 생성하기
		//HashMap<String,String>객체에 게시판 입력 성공 행의 개수 저장하기
		//HashMap<String,String>객체에 유효성 체크시 메시지 저장하기
		//HashMap<String,String>객체 리턴하기
		//----------------------------------------------------
		Map<String,String> map = new HashMap<String,String>();
		map.put("boardRegCnt", boardRegCnt+"");
		map.put("msg", msg); 
		return map;
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
		BoardDTO boardDTO = this.boardService.getBoard(b_no);
			//readcount가 증가하기 때문에 update구문이 있어 트랜잭션이 걸림
		
		//----------------------------------------------------
		//[ModelAndView 객체] 생성하기
		//[ModelAndView 객체]에 [호출할 JSP 페이지명]을 저장하기
		//[ModelAndView 객체]에 DB연동 결과물 담기 
		//[ModelAndView 객체] 리턴하기
		//----------------------------------------------------
		ModelAndView mav = new ModelAndView();
		mav.setViewName(path+"boardContentForm.jsp");
		
		mav.addObject("b_no", b_no);
		mav.addObject("boardDTO", boardDTO);
		return mav;
	}
	
	
	//***********************************************
	//가상 주소 /boardUpDelForm.do로 접근하면 호출되는 메소드 선언
	//***********************************************
	@RequestMapping(value="/boardUpDelForm.do")
	public ModelAndView goBoardUpDelForm(
			//----------------------------------------------------
			//"b_no" 라는 파라미터명의 파라미터값이 저장되는 매개변수 b_no선언
			// 수정 또는 삭제할 게시판 고유 번호가 들어오는매개변수 선언
			//----------------------------------------------------
			@RequestParam(value="b_no") int b_no 
		) {
		
		//----------------------------------------------------
		//boardDAOImpl 객체의 getBoard 메소드 호출로 1개의 게시판글을
		//BoardDTO 객체에 담아서 가져오기
		//----------------------------------------------------
		//=>만약 서비스층을 거치게 만들거면 새로운 메소드를 만들어야 함
			// 왜냐하면 서비스층의 getBoard 메소드는 조회수를 1 증가시키기 때문에
		//BoardDTO boardDTO=this.boardService.getBoard_without_updateReadcount(b_no);
		//=>만약 서비스층을 거치지 않게 만들거면 기존 getBoard 메소드를 사용할 수 있음
		BoardDTO boardDTO = this.boardDAO.getBoard(b_no);
		
		//----------------------------------------------------
		//[ModelAndView 객체] 생성하기
		//[ModelAndView 객체]에 [호출할 JSP 페이지명]을 저장하기
		//[ModelAndView 객체]에 DB연동 결과물 담기 
		//[ModelAndView 객체] 리턴하기
		//----------------------------------------------------
		ModelAndView mav = new ModelAndView();
		mav.setViewName(path+"boardUpDelForm.jsp");
		mav.addObject("boardDTO",boardDTO);
		return mav;
	}
	
	//***********************************************
	//가상 주소 /boardUpDelProc.do로 접근하면 호출되는 메소드 선언
	//***********************************************
	//@RequestMapping이 붙은 메소드의 매개변수에 DTO를 넣으면
	//파라미터명과 BoardDTO의 속성변수명이 같기만 하면 알아서 속성변수안에 파라미터 값을 저장시킴
	@RequestMapping( 
			value = "/boardUpDelProc.do"
			, method = RequestMethod.POST
			, produces = "application/json;charset=UTF-8" 
	)
	@ResponseBody
	public Map<String,String> checkBoardUpDelForm(
			//+++++++++++++++++++++++++++++++++++++++++++++++++
			// 파라미터값을 저장할 [BoardDTO 객체]를 매개변수로 선언
			//+++++++++++++++++++++++++++++++++++++++++++++++++
			BoardDTO boardDTO
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++
			// <input type="file name=img> 입력양식의 파일이 저장된 MultipartFile 객체 저장 매개변수 선언
			// <주의> 업로드된 파일이 없어도 MultipartFile객체는 생성되어 들어온다.
			//+++++++++++++++++++++++++++++++++++++++++++++++++
			, @RequestParam("img") MultipartFile multi
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++
			// "upDel"라는 파라미터명의 파라미터값이 저장된 매개변수 upDel 선언
			//+++++++++++++++++++++++++++++++++++++++++++++++++
			, @RequestParam(value="upDel") String upDel
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++
			// Error 객체를 관리하는 BindingResult 객체가 저장되어 들어오는 매개변수 bindingResult 선언
			// BindingResult 객체 => 유효성 검사
			//+++++++++++++++++++++++++++++++++++++++++++++++++
			, BindingResult bindingResult
		) throws Exception {
		
		//----------------------------------------------------
		//업로드 된 파일의 크기와 확장자 체크하기
		//----------------------------------------------------
		//만약에 업로드된 파일이 있으면
		if(multi.isEmpty()==false) {
			//만약에 업로드된 파일의 크기가 1,000,000 byte(=1,000KB)보다 크면
			if(multi.getSize()>1000000) {
				Map<String,String> map = new HashMap<String, String>();
				map.put("boardRegCnt", "0");
				map.put("msg", "업로드 파일이 1000kb보다 크면 안됩니다.");
				return map; // return이 나오면 메소드 중단, 오른쪽에 값이 있으면 메소드 호출한 쪽으로 던져줌
			}
			
			//만약에 업로드된 파일의 확장자가 이미지 확장자가 아니면
			String fileName=multi.getOriginalFilename();
			
			if(fileName.endsWith(".jpg")==false && fileName.endsWith(".gif")==false && fileName.endsWith(".png")==false) {
				Map<String,String> map = new HashMap<String, String>();
				map.put("boardRegCnt", "0");
				map.put("msg", "이미지 파일이 아닙니다.");
				return map;
			}
		}
		
		
		int boardUpDelCnt =0;
		//유효성 체크 에러 메시지를 저장할 변수 msg 선언
		String msg = "";
		//----------------------------------------------------
		//만약 게시판 수정 모드면 => 유효성 검사 필요
		//수정 실행하고 수정 적용행의 개수 얻기
		//----------------------------------------------------
		if(upDel.equals("up")) {
			//----------------------------------------------------
			//check_BoardDTO 메소드를 호출하여 [유효성 체크]를 하고 경고문자 얻기
			//유효성확인에 실패하면 DB연동을 할 수 없음
			//----------------------------------------------------	
			
				// check_BoardDTO 메소드를 호출하여 유효성 체크하고 에러메시지 문자 얻기
				// boardDTO, bindingResult의 메모리위치주소를 넣어줌
			msg = check_BoardDTO(boardDTO, bindingResult);
			
			//----------------------------------------------------
			//[ModelAndView 객체]에 유효성 체크 에러메시지 저장하기
			//----------------------------------------------------
			
			//만약 msg안에 ""가 저장되어 있으면, 즉 유효성 체크를 통과했으면
			if(msg.equals("")) {
				//수정 DB연동
				//----------------------------------------------------
				//[BoardServiceImpl 객체]의 updateBoard 메소드 호출로 
				// 게시판 글 수정하고 [게시판 수정 적용행의 개수] 얻기
				//----------------------------------------------------
					// 결과가 1이면(한 행이 들어가면) 성공
				//boardDTO에 파라미터값이 담겨있음
				boardUpDelCnt = this.boardService.updateBoard(boardDTO, multi);
				//System.out.println("boardUpdateCnt => "+ boardUpDelCnt); // DB연동 성공했는지 확인
			}
		}
		//----------------------------------------------------
		//만약 게시판 삭제 모드면 => 유효성검사 필요없음
		//----------------------------------------------------
		else if(upDel.equals("del")) {
			//----------------------------------------------------
			//[BoardServiceImpl 객체]의 deleteBoard 메소드 호출로
			//삭제 실행하고 [삭제 적용행의 개수] 얻기
			//----------------------------------------------------
			boardUpDelCnt = this.boardService.deleteBoard(boardDTO);
		}
		
		//*******************************************
		// HashMap<String,String> 객체 생성하기
		// HashMap<String,String> 객체에 게시판 수정.삭제 성공행의 개수 저장하기
		// HashMap<String,String> 객체에 유효성 체크 시 메시지 저장하기
		// HashMap<String,String> 객체 리턴하기
		//*******************************************
		Map<String, String> map = new HashMap<String,String>();
		map.put("boardUpDelCnt", boardUpDelCnt+"");		
		map.put("msg",msg);
		return map;
	}
}


/*
 * 	@RequestMapping(value="/boardRegProc.do")
	public ModelAndView insertBoard( 
		//+++++++++++++++++++++++++++++++++++++++++++++++++
		// 파라미터값을 저장할 [BoardDTO 객체]를 매개변수로 선언
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
		//mav.setViewName("boardRegProc.jsp");
		mav.setViewName("boardRegProc2.jsp");
		//예외 처리
		try { 
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
			
			//----------------------------------------------------
			//[ModelAndView 객체]에 유효성 체크 에러메시지 저장하기
			//----------------------------------------------------
			mav.addObject("msg", msg);
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
				//System.out.println("boardRegCnt => "+ boardRegCnt); // DB연동 성공했는지 확인
				
				//----------------------------------------------------
				//[ModelAndView 객체]에 [게시판 입력 적용행의 개수] 저장하기
				//----------------------------------------------------
				mav.addObject("boardRegCnt", boardRegCnt);
				//System.out.println("BoardController.insertBoard 메소드 호출 성공"); 				
			}
			//만약 msg안에 ""가 저장되어 있지 않으면, 즉 유효성 체크를 통과 못했으면
			else {
				mav.addObject("boardRegCnt", 0);
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

	//@RequestMapping이 붙은 메소드의 매개변수에 DTO를 넣으면
	//파라미터명과 BoardDTO의 속성변수명이 같기만 하면 알아서 속성변수안에 파라미터 값을 저장시킴
	@RequestMapping(value="/boardUpDelProc.do")
	public ModelAndView checkBoardUpDelForm(
			//+++++++++++++++++++++++++++++++++++++++++++++++++
			// 파라미터값을 저장할 [BoardDTO 객체]를 매개변수로 선언
			//+++++++++++++++++++++++++++++++++++++++++++++++++
			BoardDTO boardDTO
			//+++++++++++++++++++++++++++++++++++++++++++++++++
			// "upDel"라는 파라미터명의 파라미터값이 저장된 매개변수 upDel 선언
			//+++++++++++++++++++++++++++++++++++++++++++++++++
			, @RequestParam(value="upDel") String upDel
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
		//mav.setViewName("boardUpDelProc.jsp");
		mav.setViewName("boardUpDelProc2.jsp");
		
		//----------------------------------------------------
		//만약 게시판 수정 모드면 => 유효성 검사 필요
		//수정 실행하고 수정 적용행의 개수 얻기
		//----------------------------------------------------
		if(upDel.equals("up")) {
			//----------------------------------------------------
			//check_BoardDTO 메소드를 호출하여 [유효성 체크]를 하고 경고문자 얻기
			//유효성확인에 실패하면 DB연동을 할 수 없음
			//----------------------------------------------------	
			//유효성 체크 에러 메시지를 저장할 변수 msg 선언
			String msg = "";
			// check_BoardDTO 메소드를 호출하여 유효성 체크하고 에러메시지 문자 얻기
				// boardDTO, bindingResult의 메모리위치주소를 넣어줌
			msg = check_BoardDTO(boardDTO, bindingResult);
			
			//----------------------------------------------------
			//[ModelAndView 객체]에 유효성 체크 에러메시지 저장하기
			//----------------------------------------------------
			mav.addObject("msg", msg);
			
			//만약 msg안에 ""가 저장되어 있으면, 즉 유효성 체크를 통과했으면
			if(msg.equals("")) {
				//수정 DB연동
				//----------------------------------------------------
				//[BoardServiceImpl 객체]의 updateBoard 메소드 호출로 
				// 게시판 글 수정하고 [게시판 수정 적용행의 개수] 얻기
				//----------------------------------------------------
					// 결과가 1이면(한 행이 들어가면) 성공
				//boardDTO에 파라미터값이 담겨있음
				int boardUpDelCnt = this.boardService.updateBoard(boardDTO);
				//System.out.println("boardUpdateCnt => "+ boardUpDelCnt); // DB연동 성공했는지 확인
				//----------------------------------------------------
				//[ModelAndView 객체]에 [게시판 수정 적용행의 개수] 저장하기
				//----------------------------------------------------
				mav.addObject("boardUpDelCnt", boardUpDelCnt);
			}
			//만약 msg 안에 ""가 저장되어 있지 않으면, 즉 유효성 체크를 통과하지 못했으면
			else {
				mav.addObject("boardUpDelCnt", 0);
			}
		}
		//----------------------------------------------------
		//만약 게시판 삭제 모드면 => 유효성검사 필요없음
		//----------------------------------------------------
		else if(upDel.equals("del")) {
			//----------------------------------------------------
			//[BoardServiceImpl 객체]의 deleteBoard 메소드 호출로
			//삭제 실행하고 [삭제 적용행의 개수] 얻기
			//----------------------------------------------------
			int boardUpDelCnt = this.boardService.deleteBoard(boardDTO);
			mav.addObject("boardUpDelCnt", boardUpDelCnt);
		}
		
		//----------------------------------------------------
		//[ModelAndView 객체] 리턴하기
		//----------------------------------------------------
		return mav;
	}
*/
