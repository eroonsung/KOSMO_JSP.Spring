package com.naver.erp;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class EmpController {
	@Autowired
	private EmpDAO empDAO;
	
	@Autowired
	private EmpService empService;
	
	@RequestMapping(value="/empList.do")
	public ModelAndView getEmpList( EmpSearchDTO empSearchDTO ) {
		
		int empListAllCnt = this.empDAO.getEmpListAllCnt(empSearchDTO);
		
		int selectPageNo = empSearchDTO.getSelectPageNo();
		int rowCntPerPage = empSearchDTO.getRowCntPerPage();
		int pageNoCntPerPage = 10;
		
		int start_serial_no = (selectPageNo-1)*rowCntPerPage+1; 
		
		Map<String,Integer> map = Util.getPagingNos(empListAllCnt, pageNoCntPerPage, selectPageNo, rowCntPerPage);
		
		List<Map<String,String>> empList = this.empDAO.getEmpList(empSearchDTO);
		
		List<String> jikupList = this.empDAO.getJikupList();
		
		List<String> depNameList = this.empDAO.getDepNameList();
		
		int minY = this.empDAO.getMinY();
		int maxY = this.empDAO.getMaxY();
		
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("empList.jsp");
		mav.addObject("empListAllCnt", empListAllCnt);
		
		mav.addObject("empList", empList);
		
		mav.addObject("pagingNos",map);
		
		mav.addObject("start_serial_no", start_serial_no);
		mav.addObject("jikupList", jikupList);
		mav.addObject("depNameList", depNameList);
		
		mav.addObject("minY", minY);
		mav.addObject("maxY", maxY);
		return mav;
	}
	
	
	@RequestMapping(value="/empRegForm.do")
	public ModelAndView goEmpRegForm() {
		
		List<Map<String,String>> depNoAndNameList = this.empDAO.getDepNoAndNameList();
		List<String> jikupList = this.empDAO.getJikupList();
		List<EmpDTO> MgrEmpList = this.empDAO.getMgrEmpList();
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("empRegForm.jsp");
		mav.addObject("depNoAndNameList", depNoAndNameList);
		mav.addObject("jikupList", jikupList);
		mav.addObject("MgrEmpList", MgrEmpList);
		return mav;
	}

	@RequestMapping(value="/empRegProc.do")
	public ModelAndView checkEmpRegForm(
			EmpDTO empDTO
			, BindingResult bindingResult
			
		) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("empRegProc.jsp");
		try { 
			String msg = "";
			msg = check_EmpDTO(empDTO, bindingResult);
			
			mav.addObject("msg", msg);
			
			if( msg.equals("") ) {
				//----------------------------------------------------
				//[BoardServiceImpl 객체]의 insertBoard 메소드 호출로 
				// 게시판 글 입력하고 [게시판 입력 적용행의 개수] 얻기
				//----------------------------------------------------
					// 결과가 1이면(한 행이 들어가면) 성공
				//boardDTO에 파라미터값이 담겨있음
				int empRegCnt = this.empService.insertEmp(empDTO);
				//System.out.println("boardRegCnt => "+ boardRegCnt); // DB연동 성공했는지 확인
				
				//----------------------------------------------------
				//[ModelAndView 객체]에 [게시판 입력 적용행의 개수] 저장하기
				//----------------------------------------------------
				mav.addObject("empRegCnt", empRegCnt);
				//System.out.println("BoardController.insertBoard 메소드 호출 성공"); 				
			}
			//만약 msg안에 ""가 저장되어 있지 않으면, 즉 유효성 체크를 통과 못했으면
			else {
				mav.addObject("empRegCnt", 0);
			}
		}catch(Exception e){
			//위 try 구문에서 에러가 발생하면
			// 아래 코드 실행
			mav.addObject("empRegCnt", -1);
			mav.addObject("msg", "서버에서 문제 발생. 서버 관리자에게 문의 바람");
		}
		//----------------------------------------------------
		//[ModelAndView 객체] 리턴하기
		//----------------------------------------------------
		return mav;
	}
	
	@RequestMapping(value="/empUpDelForm.do")
	public ModelAndView goEmpUpDelForm(
			@RequestParam(value="emp_no") int emp_no
		) {
		EmpDTO empDTO = this.empDAO.getEmp(emp_no);
		
		List<Map<String,String>> depNoAndNameList = this.empDAO.getDepNoAndNameList();
		List<String> jikupList = this.empDAO.getJikupList();
		List<EmpDTO> MgrEmpList = this.empDAO.getMgrEmpList();
		
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("empUpDelForm.jsp");
		mav.addObject("empDTO",empDTO);
		mav.addObject("depNoAndNameList", depNoAndNameList);
		mav.addObject("jikupList", jikupList);
		mav.addObject("MgrEmpList", MgrEmpList);
		return mav;
	}
	
	@RequestMapping(value="/empUpDelProc.do")
	public ModelAndView checkEmpUpDelForm(
			EmpDTO empDTO
			, @RequestParam(value="upDel") String upDel
			, BindingResult bindingResult
		) {
		ModelAndView mav = new ModelAndView();
		//mav.setViewName("boardUpDelProc.jsp");
		mav.setViewName("empUpDelProc.jsp");
		
		if(upDel.equals("up")) {
			String msg = "";
			msg = check_EmpDTO(empDTO, bindingResult);
			
			mav.addObject("msg", msg);
			
			if(msg.equals("")) {
				int empUpDelCnt = this.empService.updateEmp(empDTO);
				mav.addObject("empUpDelCnt", empUpDelCnt);
			}
			else {
				mav.addObject("empUpDelCnt", 0);
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
			int empUpDelCnt = this.empService.deleteEmp(empDTO);
			mav.addObject("empUpDelCnt", empUpDelCnt);
		}
		
		//----------------------------------------------------
		//[ModelAndView 객체] 리턴하기
		//----------------------------------------------------
		return mav;
	}
	
	private String check_EmpDTO(EmpDTO empDTO, BindingResult bindingResult) {
		String checkMsg = "";
		//----------------------------------------------------
		//BoardDTO 객체에 저장된 데이터의 유효성 체크할 BoardValidator 객체 생성하기
		//BoardValidator 객체의 validate 메소드를 호출하여 유효성 체크 실행하기
		//----------------------------------------------------
		EmpValidator empValidator = new EmpValidator();
			// 메소드만 호출 (리턴값이 없음)
		empValidator.validate(
				empDTO  // 유효성을 체크할 DTO 객체
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
}
