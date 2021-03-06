package com.naver.erp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="/naver")
public class BoardController {
	//===========================================================================
	@Autowired
	private BoardDAO boardDAO;
	
	@Autowired
	private BoardService boardService;
	
	private String path = Info.naverPath;
	
	//===========================================================================
	@ModelAttribute("totCnt")
	public int getTotCnt ( BoardSearchDTO boardSearchDTO  ) {
		int totCnt = this.boardDAO.getBoardListAllCnt(boardSearchDTO);
		return totCnt;		
	}
	
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
	public ModelAndView goBoardList(BoardSearchDTO boardSearchDTO ) {
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
			
		if( boardListAllCnt>0 ) { // boardListAllCnt??? 0?????? ?????? ??????
			// [????????? ????????? ??????] ?????????
			last_pageNo = boardListAllCnt/rowCntPerPage;
				if( boardListAllCnt%rowCntPerPage>0 ) { last_pageNo++; }
			//?????? ????????? ????????? ????????? ????????? ????????? ???????????? ??????	
			if(selectPageNo>last_pageNo) {
				//????????? 1 ????????????
				selectPageNo = 1;
				//boardSearchDTO????????? selectPageNo ??????????????? 1 ????????????
					// -> ????????? ????????? ????????? ?????????
				boardSearchDTO.setSelectPageNo(selectPageNo);
			}
			// [?????? ????????? ???????????? ????????? ????????? ?????? ????????? ??????] ?????????
			min_pageNo = ((selectPageNo-1)/pageNoCntPerPage)*pageNoCntPerPage+1;
			// [?????? ????????? ???????????? ????????? ????????? ?????? ????????? ??????] ?????????
			max_pageNo =  min_pageNo+pageNoCntPerPage-1;
				if( max_pageNo>last_pageNo ){
					max_pageNo = last_pageNo ;
				}
		}
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		List<Map<String,String>> boardList = this.boardDAO.getBoardList(boardSearchDTO);
		
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		ModelAndView mav = new ModelAndView();
		mav.setViewName("boardList.jsp");
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
	//Util??? ?????? ????????? ?????? getPagingNos ????????? ??????
	@RequestMapping(value="boardList.do")
	public ModelAndView goBoardList2(BoardSearchDTO boardSearchDTO ) {
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
		mav.setViewName(path+"boardList.jsp");
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
		mav.setViewName(path+"boardRegForm.jsp");
		return mav;
	}
	
	//----------------------------------------------------------------------------
	/*
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
			mav.addObject("msg","???????????? ?????? ??????, ?????? ??????????????? ?????? ??????");
		}		
		return mav;
	}
	*/
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	@RequestMapping( 
			value = "/boardRegProc.do"
			, method = RequestMethod.POST
			, produces = "application/json;charset=UTF-8" )
	@ResponseBody
	public Map<String,String> insertBoard2(
			BoardDTO boardDTO
			, @RequestParam("img") MultipartFile multi
			, BindingResult bindingResult
	) {

		if(multi.isEmpty()==false) {
			if(multi.getSize()>1000000) {
				Map<String,String> map = new HashMap<String, String>();
				map.put("boardRegCnt", "0");
				map.put("msg", "????????? ????????? 1000kb?????? ?????? ????????????.");
				return map; 
			}
			
			//????????? ???????????? ????????? ???????????? ????????? ???????????? ?????????
			String fileName=multi.getOriginalFilename();
			
			if(fileName.endsWith(".jpg")==false && fileName.endsWith(".gif")==false && fileName.endsWith(".png")==false) {
				Map<String,String> map = new HashMap<String, String>();
				map.put("boardRegCnt", "0");
				map.put("msg", "????????? ????????? ????????????.");
				return map;
			}
		}
		
		String msg="";	
		int boardRegCnt=0;
		try {
			
			msg = check_BoardDTO(boardDTO, bindingResult);
			
			if(msg.equals("")) {
				boardRegCnt = this.boardService.insertBoard(boardDTO, multi);
			}
		}catch(Exception e) {
			boardRegCnt=-1;
			msg = "???????????? ?????? ??????, ?????? ??????????????? ?????? ??????";
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
		mav.setViewName(path+"boardContentForm.jsp");
		mav.addObject("boardDTO",boardDTO);
		mav.addObject("b_no",b_no);
		return mav;
	}
	
	//----------------------------------------------------------------------------
	@RequestMapping(value="boardUpDelForm.do")
	public ModelAndView goBoardUpDelForm(@RequestParam(value="b_no") int b_no) {
		BoardDTO boardDTO = this.boardService.getBoard(b_no);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName(path+"boardUpDelForm.jsp");
		mav.addObject("boardDTO",boardDTO);
		return mav;
	}
	
	//----------------------------------------------------------------------------
	/*
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
	*/
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	@RequestMapping( 
			value = "/boardUpDelProc.do"
			, method = RequestMethod.POST
			, produces = "application/json;charset=UTF-8" )
	@ResponseBody
	public Map<String,String> checkBoardUpDelForm2(
			BoardDTO boardDTO, @RequestParam(value="upDel") String upDel
			, @RequestParam("img") MultipartFile multi
			, BindingResult bindingResult
		) throws Exception {
		
		if(multi.isEmpty()==false) {
			if(multi.getSize()>1000000) {
				Map<String,String> map = new HashMap<String, String>();
				map.put("boardRegCnt", "0");
				map.put("msg", "????????? ????????? 1000kb?????? ?????? ????????????.");
				return map; 
			}
			
			//????????? ???????????? ????????? ???????????? ????????? ???????????? ?????????
			String fileName=multi.getOriginalFilename();
			
			if(fileName.endsWith(".jpg")==false && fileName.endsWith(".gif")==false && fileName.endsWith(".png")==false) {
				Map<String,String> map = new HashMap<String, String>();
				map.put("boardRegCnt", "0");
				map.put("msg", "????????? ????????? ????????????.");
				return map;
			}
		}
		
		String msg = "";
		int boardUpDelCnt = 0;
		
		if(upDel.equals("up")) {
			msg = check_BoardDTO(boardDTO, bindingResult);
			
			if(msg.equals("")) {
				boardUpDelCnt = this.boardService.updateBoard(boardDTO, multi);
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
