package com.naver.erp;

import java.util.ArrayList;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class StaffController {
	@Autowired
	private StaffDAO staffDAO;
	
	@Autowired
	private StaffService staffService;
	
	@RequestMapping(value="/staff_search_form.do")
	public ModelAndView getSearchForm( StaffSearchDTO staffSearchDTO ) {
		
		int staffListAllCnt = this.staffDAO.getStaffListAllCnt(staffSearchDTO);
		
		int selectPageNo = staffSearchDTO.getSelectPageNo();
		int rowCntPerPage = staffSearchDTO.getRowCntPerPage();
		int pageNoCntPerPage = 10;
		int start_serial_no = (selectPageNo-1)*rowCntPerPage+1; 
		
		Map<String,Integer> map = Util.getPagingNos(staffListAllCnt, pageNoCntPerPage, selectPageNo, rowCntPerPage);
		
		staffSearchDTO.setSelectPageNo(map.get("selectPageNo"));
		
		List<Map<String,String>> staffList = this.staffDAO.getStaffList(staffSearchDTO);
		
		List<StaffDTO> relgionList = this.staffDAO.getReligionList();
		List<StaffDTO> schoolList = this.staffDAO.getSchoolList();
		List<Map<String,String>> skillList = this.staffDAO.getSkillList();
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("staff_search_form.jsp");
		mav.addObject("staffListAllCnt", staffListAllCnt);
		mav.addObject("pagingNos",map);
		mav.addObject("start_serial_no", start_serial_no);
		
		mav.addObject("staffList", staffList);
		
		mav.addObject("relgionList", relgionList);
		mav.addObject("schoolList", schoolList);
		mav.addObject("skillList", skillList);
		return mav;
	}
	
	
	//****************************************************************************
	@RequestMapping(value="/staff_input_form.do")
	public ModelAndView goStaffRegForm() {
		
		List<StaffDTO> relgionList = this.staffDAO.getReligionList();
		List<StaffDTO> schoolList = this.staffDAO.getSchoolList();
		List<Map<String,String>> skillList = this.staffDAO.getSkillList();
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("staff_input_form.jsp");
		mav.addObject("relgionList", relgionList);
		mav.addObject("schoolList", schoolList);
		mav.addObject("skillList", skillList);
		return mav;
	}
	
	@RequestMapping( 
			value = "/staff_input_proc.do"
			, method = RequestMethod.POST
			, produces = "application/json;charset=UTF-8" 
	)
	@ResponseBody
	public Map<String,String> insertStaff( 
		StaffDTO staffDTO 
		, @RequestParam("img") MultipartFile multi
		, BindingResult bindingResult
	) {
		
	
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
		
		int staffRegCnt=0;
		//유효성 체크 에러 메시지를 저장할 변수 msg 선언
		String msg = "";
		
		//예외 처리
		try { 
			msg = check_StaffDTO(staffDTO, bindingResult);
			
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
				staffRegCnt = this.staffService.insertStaff(staffDTO , multi);
				//System.out.println("boardRegCnt => "+ boardRegCnt); // DB연동 성공했는지 확인
				
				//System.out.println("BoardController.insertBoard 메소드 호출 성공"); 				
			}
		}catch(Exception e){
			//위 try 구문에서 에러가 발생하면
			// 아래 코드 실행
			staffRegCnt = -1;
			msg="서버에서 문제 발생! 서버관리자에게 문의 바람";
		}
		//----------------------------------------------------
		//HashMap<String,String>객체 생성하기
		//HashMap<String,String>객체에 게시판 입력 성공 행의 개수 저장하기
		//HashMap<String,String>객체에 유효성 체크시 메시지 저장하기
		//HashMap<String,String>객체 리턴하기
		//----------------------------------------------------
		Map<String,String> map = new HashMap<String,String>();
		map.put("staffRegCnt", staffRegCnt+"");
		map.put("msg", msg); 
		return map;
	}
	
	private String check_StaffDTO(StaffDTO staffDTO, BindingResult bindingResult) {
		String checkMsg = "";
		//----------------------------------------------------
		//BoardDTO 객체에 저장된 데이터의 유효성 체크할 BoardValidator 객체 생성하기
		//BoardValidator 객체의 validate 메소드를 호출하여 유효성 체크 실행하기
		//----------------------------------------------------
		StaffValidator staffValidator = new StaffValidator();
			// 메소드만 호출 (리턴값이 없음)
		staffValidator.validate(
				staffDTO  // 유효성을 체크할 DTO 객체
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
	
	//******************************************************************
	//staff_updel_form
	@RequestMapping(value="/staff_updel_form.do")
	public ModelAndView goStaffUpDelForm(
			@RequestParam(value="staff_no") int staff_no	
			
		) {
		
		StaffDTO staffDTO = this.staffDAO.getStaffDTO(staff_no);
		List<StaffDTO> staffSkillList = this.staffDAO.getStaffSkillDTO(staff_no);
	
		List<StaffDTO> relgionList = this.staffDAO.getReligionList();
		List<StaffDTO> schoolList = this.staffDAO.getSchoolList();
		List<Map<String,String>> skillList = this.staffDAO.getSkillList();
		
		List<Map<String,String>> xxxSkillList = this.staffDAO.getXxxSkillList(staff_no);
		
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("staff_updel_form.jsp");
		mav.addObject("relgionList", relgionList);
		mav.addObject("schoolList", schoolList);
		mav.addObject("skillList", skillList);
		mav.addObject("staffDTO",staffDTO);
		mav.addObject("staffSkillList",staffSkillList);
		mav.addObject("xxxSkillList",xxxSkillList);
		return mav;
	}
	
	@RequestMapping( 
			value = "/staff_updel_proc.do"
			, method = RequestMethod.POST
			, produces = "application/json;charset=UTF-8" 
	)
	@ResponseBody
	public Map<String,String> checkStaffUpDelForm(
			StaffDTO staffDTO
			, @RequestParam(value="upDel") String upDel
			, @RequestParam("img") MultipartFile multi
			, BindingResult bindingResult
		) throws Exception {
		System.out.println("StaffController.checkStaffUpDelForm 시작");
		
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
		
		int staffUpDelCnt =0;
		//유효성 체크 에러 메시지를 저장할 변수 msg 선언
		String msg = "";
		//----------------------------------------------------
		//만약 게시판 수정 모드면 => 유효성 검사 필요
		//수정 실행하고 수정 적용행의 개수 얻기
		//----------------------------------------------------
		if(upDel.equals("up")) {
			msg = check_StaffDTO(staffDTO, bindingResult);
			
			if(msg.equals("")) {
				//수정 DB연동
				staffUpDelCnt = this.staffService.updateStaff(staffDTO , multi);
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
			staffUpDelCnt = this.staffService.deleteStaff(staffDTO);
		}
		
		//*******************************************
		// HashMap<String,String> 객체 생성하기
		// HashMap<String,String> 객체에 게시판 수정.삭제 성공행의 개수 저장하기
		// HashMap<String,String> 객체에 유효성 체크 시 메시지 저장하기
		// HashMap<String,String> 객체 리턴하기
		//*******************************************
		Map<String, String> map = new HashMap<String,String>();
		map.put("staffUpDelCnt", staffUpDelCnt+"");		
		map.put("msg",msg);
		System.out.println("StaffController.checkStaffUpDelForm 완료");
		return map;
	}
}

