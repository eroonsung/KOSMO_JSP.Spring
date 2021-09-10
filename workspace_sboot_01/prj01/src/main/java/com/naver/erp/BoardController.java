package com.naver.erp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BoardController {
	//===========================================================================
	@Autowired
	private BoardDAO boardDAO;
	
	@Autowired
	private BoardService boardService;
	
	//===========================================================================
	/*
	@RequestMapping(value="boardList.do")
	public ModelAndView goBoardList() {
		
		List<Map<String,String>> boardList = this.boardDAO.getBoardList();
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("boardList.jsp");
		mav.addObject("boardList",boardList);
		return mav;
	}
	*/
	//@RequestMapping(value="boardList.do")
	public ModelAndView goBoardList(BoardSerachDTO boardSearchDTO ) {
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		int boardListAllCnt = this.boardDAO.getBoardListAllCnt(boardSearchDTO);
		
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		int last_pageNo = 0;
		int min_pageNo = 0;
		int max_pageNo = 0;
		int selectPageNo = boardSearchDTO.getSelectPageNo();
		int rowCntPerPage = boardSearchDTO.getRowCntPerPage();
		int pageNoCntPerPage = 10;
		int start_serial_no = (selectPageNo-1)*rowCntPerPage+1;
			
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
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		List<Map<String,String>> boardList = this.boardDAO.getBoardList(boardSearchDTO);
		
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		ModelAndView mav = new ModelAndView();
		//mav.setViewName("boardList.jsp");
		mav.setViewName("boardList2.jsp");
		mav.addObject("boardList",boardList);
		mav.addObject("boardListAllCnt",boardListAllCnt);
		
		mav.addObject("last_pageNo", last_pageNo);
		mav.addObject("min_pageNo", min_pageNo);
		mav.addObject("max_pageNo", max_pageNo);
		
		mav.addObject("selectPageNo", selectPageNo);
		mav.addObject("rowCntPerPage", rowCntPerPage);
		mav.addObject("pageNoCntPerPage", pageNoCntPerPage);
		
		mav.addObject("start_serial_no", start_serial_no);
		
		return mav;
	}
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//Util에 있는 페이징 처리 getPagingNos 메소드 사용
	@RequestMapping(value="boardList.do")
	public ModelAndView goBoardList2(BoardSerachDTO boardSearchDTO ) {
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		int boardListAllCnt = this.boardDAO.getBoardListAllCnt(boardSearchDTO);
		
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		int selectPageNo = boardSearchDTO.getSelectPageNo();
		int rowCntPerPage = boardSearchDTO.getRowCntPerPage();
		int pageNoCntPerPage = 10;
		
		Map<String,Integer> map= Util.getPagingNos(boardListAllCnt
				, pageNoCntPerPage
				, selectPageNo
				, rowCntPerPage);
		
		boardSearchDTO.setSelectPageNo(map.get("selectPageNo"));
		
		int start_serial_no = (selectPageNo-1)*rowCntPerPage+1;
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		List<Map<String,String>> boardList = this.boardDAO.getBoardList(boardSearchDTO);
		
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		ModelAndView mav = new ModelAndView();
		//mav.setViewName("boardList.jsp");
		mav.setViewName("boardList3.jsp");
		mav.addObject("boardList",boardList);
		mav.addObject("boardListAllCnt",boardListAllCnt);
		
		mav.addObject("start_serial_no", start_serial_no);
		
		mav.addObject("pagingNos",map );
		
		return mav;
	}
	//----------------------------------------------------------------------------
	/*
	@RequestMapping(value="boardRegForm.do")
	public ModelAndView goBoardRegForm() {
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("boardRegForm.jsp");
		return mav;
	}
	*/
	@RequestMapping(value="boardRegForm.do")
	public ModelAndView goBoardRegForm( @RequestParam(value="b_no", required=false, defaultValue="0") int b_no) {
		
		ModelAndView mav = new ModelAndView();
		//mav.setViewName("boardRegForm.jsp");
		mav.setViewName("boardRegForm2.jsp");
		return mav;
	}
	
	//----------------------------------------------------------------------------
	//@RequestMapping(value="boardRegProc.do")
	public ModelAndView insertBoard(BoardDTO boardDTO, BindingResult bindingResult) {
				
		ModelAndView mav = new ModelAndView();
		//mav.setViewName("boardRegProc.jsp");
		mav.setViewName("boardRegProc2.jsp");
		try {
			String msg="";
			msg = check_BoardDTO(boardDTO, bindingResult);
			mav.addObject("msg",msg);
			
			if(msg.equals("")) {
				int boardRegCnt = this.boardService.insertBoard(boardDTO);
				mav.addObject("boardRegCnt",boardRegCnt);
			}else {
				mav.addObject("boardRegCnt",0);
			}
		}catch(Exception e) {
			mav.addObject("boardRegCnt",-1);
			mav.addObject("msg","서버에서 문제 발생, 서버 관리자에게 문의 바람");
		}		
		return mav;
	}
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	@RequestMapping( 
			value = "/boardRegProc.do"
			, method = RequestMethod.POST
			, produces = "application/json;charset=UTF-8" )
	@ResponseBody
	public Map<String,String> insertBoard2(BoardDTO boardDTO, BindingResult bindingResult) {
		String msg="";	
		int boardRegCnt=0;
		try {
			
			msg = check_BoardDTO(boardDTO, bindingResult);
			
			if(msg.equals("")) {
				boardRegCnt = this.boardService.insertBoard(boardDTO);
			}
		}catch(Exception e) {
			boardRegCnt=-1;
			msg = "서버에서 문제 발생, 서버 관리자에게 문의 바람";
		}		
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("msg", msg);
		map.put("boardRegCnt", boardRegCnt+"");
		return map;
	}
	
	private String check_BoardDTO(BoardDTO boardDTO, BindingResult bindingResult) {
		String checkMsg="";
		BoardValidator boardValidator = new BoardValidator();
		boardValidator.validate(boardDTO, bindingResult);
		
		if(bindingResult.hasErrors()) {
			checkMsg = bindingResult.getFieldError().getCode();
		}
		return checkMsg;
	}
	//----------------------------------------------------------------------------
	@RequestMapping(value="boardContentForm.do")
	public ModelAndView goBoardContentForm(@RequestParam(value="b_no") int b_no) {
		
		BoardDTO boardDTO = this.boardService.getBoard(b_no);
		
		ModelAndView mav = new ModelAndView();
		//mav.setViewName("boardContentForm.jsp");
		mav.setViewName("boardContentForm2.jsp");
		mav.addObject("boardDTO",boardDTO);
		mav.addObject("b_no",b_no);
		return mav;
	}
	
	//----------------------------------------------------------------------------
	@RequestMapping(value="boardUpDelForm.do")
	public ModelAndView goBoardUpDelForm(@RequestParam(value="b_no") int b_no) {
		BoardDTO boardDTO = this.boardService.getBoard(b_no);
		
		ModelAndView mav = new ModelAndView();
		//mav.setViewName("boardUpDelForm.jsp");
		mav.setViewName("boardUpDelForm2.jsp");
		mav.addObject("boardDTO",boardDTO);
		return mav;
	}
	
	//----------------------------------------------------------------------------
	//@RequestMapping(value="boardUpDelProc.do")
	public ModelAndView checkBoardUpDelForm(
			BoardDTO boardDTO, @RequestParam(value="upDel") String upDel, BindingResult bindingResult
		) {
		
		ModelAndView mav = new ModelAndView();
		//mav.setViewName("boardUpDelProc.jsp");
		mav.setViewName("boardUpDelProc2.jsp");
		
		if(upDel.equals("up")) {
			String msg = "";
			msg = check_BoardDTO(boardDTO, bindingResult);
			mav.addObject("msg", msg);
			
			if(msg.equals("")) {
				int boardUpDelCnt = this.boardService.updateBoard(boardDTO);
				mav.addObject("boardUpDelCnt", boardUpDelCnt);
			}
			else {
				mav.addObject("boardUpDelCnt", 0);
			}
		}
		else if(upDel.equals("del")) {
			int boardUpDelCnt = this.boardService.deleteBoard(boardDTO);
			mav.addObject("boardUpDelCnt", boardUpDelCnt);
		}
		return mav;
	}
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	@RequestMapping( 
			value = "/boardUpDelProc.do"
			, method = RequestMethod.POST
			, produces = "application/json;charset=UTF-8" )
	@ResponseBody
	public Map<String,String> checkBoardUpDelForm2(
			BoardDTO boardDTO, @RequestParam(value="upDel") String upDel, BindingResult bindingResult
		) {
		String msg = "";
		int boardUpDelCnt = 0;
		
		if(upDel.equals("up")) {
			msg = check_BoardDTO(boardDTO, bindingResult);
			
			if(msg.equals("")) {
				boardUpDelCnt = this.boardService.updateBoard(boardDTO);
			}
		}
		else if(upDel.equals("del")) {
			boardUpDelCnt = this.boardService.deleteBoard(boardDTO);
		}
		
		Map<String, String> map = new HashMap<String,String>();
		map.put("msg",msg);
		map.put("boardUpDelCnt", boardUpDelCnt+"");		
		return map;
	}
}
