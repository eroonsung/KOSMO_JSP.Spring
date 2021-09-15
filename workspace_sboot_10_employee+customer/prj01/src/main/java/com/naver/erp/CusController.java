package com.naver.erp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CusController {
	
	@Autowired
	private CusDAO cusDAO;
	
	@Autowired
	private CusService cusService;
	
	@RequestMapping(value="/cusList.do")
	public ModelAndView getCusList(CusSearchDTO cusSearchDTO) {

		
		int cusListAllCnt = this.cusDAO.getCusListAllCnt(cusSearchDTO);
		
		int selectPageNo = cusSearchDTO.getSelectPageNo();
		int rowCntPerPage = cusSearchDTO.getRowCntPerPage();
		int pageNoCntPerPage = 10;
		
		int start_serial_no = (selectPageNo-1)*rowCntPerPage+1; 
		
		Map<String,Integer> map = Util.getPagingNos(cusListAllCnt, pageNoCntPerPage, selectPageNo, rowCntPerPage);
		
		List<Map<String,String>> cusList = this.cusDAO.getCusList(cusSearchDTO);
		List<String> ageDecadeList = this.cusDAO.getAgeDecadeList();
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("cusList.jsp");
		mav.addObject("cusListAllCnt", cusListAllCnt);
		mav.addObject("cusList", cusList);
		
		mav.addObject("pagingNos",map);
		
		mav.addObject("start_serial_no", start_serial_no);
		
		mav.addObject("ageDecadeList", ageDecadeList);
		
		return mav;
	}
	
	@RequestMapping(value="/cusRegForm.do")
	public ModelAndView goCusRegForm() {
		List<EmpDTO> empList = this.cusDAO.getEmpList();
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("cusRegForm.jsp");
		mav.addObject("empList", empList);
		return mav;
	}
	
	/*
	@RequestMapping(
		value="/cusRegProc.do"
		, method = RequestMethod.POST
		, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Map<String,String> insertCus(
			CusDTO cusDTO
			, BindingResult bindingResult
	){
		String msg = "";
		int cusRegCnt=0;
		
		try {
			msg = check_cusDTO(cusDTO, bindingResult);
			if(msg.equals("")) {
				cusRegCnt = this.cusService.insertCus(cusDTO);
			}
		}catch (Exception e) {
			cusRegCnt = -1;
			msg="서버에서 문제 발생. 서버 관리자에게 문의 바람";
		}
		System.out.println(cusRegCnt);
		Map<String,String> map = new HashMap<String,String>();
		map.put("cusRegCnt", cusRegCnt+"");
		map.put("msg", msg);
		return map;
	}
	*/
	private String check_cusDTO(CusDTO cusDTO, BindingResult bindingResult) {
		String checkMsg = "";
		//----------------------------------------------------
		//BoardDTO 객체에 저장된 데이터의 유효성 체크할 BoardValidator 객체 생성하기
		//BoardValidator 객체의 validate 메소드를 호출하여 유효성 체크 실행하기
		//----------------------------------------------------
		CusValidator cusValidator = new CusValidator();
			// 메소드만 호출 (리턴값이 없음)
		cusValidator.validate(
				cusDTO  // 유효성을 체크할 DTO 객체
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
	
	@RequestMapping(value="/cusRegProc.do")
	public ModelAndView checkCusRegForm(
			CusDTO cusDTO
			, BindingResult bindingResult
			
		) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("cusRegProc.jsp");
		try { 
			String msg = "";
			msg = check_cusDTO(cusDTO, bindingResult);
			
			mav.addObject("msg", msg);
			
			if( msg.equals("") ) {
				//----------------------------------------------------
				//[BoardServiceImpl 객체]의 insertBoard 메소드 호출로 
				// 게시판 글 입력하고 [게시판 입력 적용행의 개수] 얻기
				//----------------------------------------------------
					// 결과가 1이면(한 행이 들어가면) 성공
				//boardDTO에 파라미터값이 담겨있음
				int cusRegCnt = this.cusService.insertCus(cusDTO);
				//System.out.println("boardRegCnt => "+ boardRegCnt); // DB연동 성공했는지 확인
				
				//----------------------------------------------------
				//[ModelAndView 객체]에 [게시판 입력 적용행의 개수] 저장하기
				//----------------------------------------------------
				mav.addObject("cusRegCnt", cusRegCnt);
				//System.out.println("BoardController.insertBoard 메소드 호출 성공"); 				
			}
			//만약 msg안에 ""가 저장되어 있지 않으면, 즉 유효성 체크를 통과 못했으면
			else {
				mav.addObject("cusRegCnt", 0);
			}
		}catch(Exception e){
			//위 try 구문에서 에러가 발생하면
			// 아래 코드 실행
			mav.addObject("cusRegCnt", -1);
			mav.addObject("msg", "서버에서 문제 발생. 서버 관리자에게 문의 바람");
		}
		//----------------------------------------------------
		//[ModelAndView 객체] 리턴하기
		//----------------------------------------------------
		return mav;
	}
	
	@RequestMapping(value="/cusUpDelForm.do")
	public ModelAndView goCusUpDelForm(
			@RequestParam(value="cus_no") int cus_no
		) {
		CusDTO cusDTO = this.cusDAO.getCus(cus_no);
		List<EmpDTO> empList = this.cusDAO.getEmpList();
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/cusUpDelForm.jsp");
		mav.addObject("cusDTO",cusDTO);
		mav.addObject("empList", empList);
		return mav;
	}
	/*
		@RequestMapping(
			value="/cusUpDelProc.do"
			, method = RequestMethod.POST
			, produces = "application/json;charset=UTF-8")
		@ResponseBody
		public Map<String,String> checkBoardUpDelForm(
				CusDTO cusDTO
				,@RequestParam(value="upDel") String upDel
				, BindingResult bindingResult
		){
		
		int cusUpDelCnt =0;
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
			msg = check_cusDTO(cusDTO, bindingResult);
			
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
				cusUpDelCnt = this.cusService.updateCus(cusDTO);
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
			cusUpDelCnt = this.cusService.deleteCus(cusDTO);
		}
		
		//*******************************************
		// HashMap<String,String> 객체 생성하기
		// HashMap<String,String> 객체에 게시판 수정.삭제 성공행의 개수 저장하기
		// HashMap<String,String> 객체에 유효성 체크 시 메시지 저장하기
		// HashMap<String,String> 객체 리턴하기
		//*******************************************
		Map<String, String> map = new HashMap<String,String>();
		map.put("cusUpDelCnt", cusUpDelCnt+"");		
		map.put("msg",msg);
		return map;
	}
	*/
	@RequestMapping(value="/cusUpDelProc.do")
	public ModelAndView checkCusUpDelForm(
			CusDTO cusDTO
			, @RequestParam(value="upDel") String upDel
			, BindingResult bindingResult
		) {
		ModelAndView mav = new ModelAndView();
		//mav.setViewName("boardUpDelProc.jsp");
		mav.setViewName("cusUpDelProc.jsp");
		
		if(upDel.equals("up")) {
			String msg = "";
			msg = check_cusDTO(cusDTO, bindingResult);
			
			mav.addObject("msg", msg);
			
			if(msg.equals("")) {
				int cusUpDelCnt = this.cusService.updateCus(cusDTO);
				mav.addObject("cusUpDelCnt", cusUpDelCnt);
			}
			else {
				mav.addObject("cusUpDelCnt", 0);
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
			int cusUpDelCnt = this.cusService.deleteCus(cusDTO);
			mav.addObject("cusUpDelCnt", cusUpDelCnt);
		}
		
		//----------------------------------------------------
		//[ModelAndView 객체] 리턴하기
		//----------------------------------------------------
		return mav;
	}
}
