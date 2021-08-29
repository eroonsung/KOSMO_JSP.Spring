package com.naver.erp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BoardController {
	//===========================================================================
	@Autowired
	private BoardDAO boardDAO;
	
	@Autowired
	private BoardService boardService;
	
	//===========================================================================
	@RequestMapping(value="boardList.do")
	public ModelAndView goBoardList() {
		
		List<Map<String,String>> boardList = this.boardDAO.getBoardList();
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("boardList.jsp");
		mav.addObject("boardList",boardList);
		return mav;
	}
	//----------------------------------------------------------------------------
	@RequestMapping(value="boardRegForm.do")
	public ModelAndView goBoardRegForm() {
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("boardRegForm.jsp");
		return mav;
	}
	//----------------------------------------------------------------------------
	@RequestMapping(value="boardRegProc.do")
	public ModelAndView insertBoard(BoardDTO boardDTO, BindingResult bindingResult) {
				
		ModelAndView mav = new ModelAndView();
		mav.setViewName("boardRegProc.jsp");
		try {
			String msg="";
			msg = check_BoardDTO(boardDTO, bindingResult);
			mav.addObject("msg",msg);
			
			if(msg.equals("")) {
				int boardRegCnt = this.boardService.insertBoard(boardDTO);
				System.out.println("boardRegCnt=>"+boardRegCnt);
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
		mav.setViewName("boardContentForm.jsp");
		mav.addObject("boardDTO",boardDTO);
		mav.addObject("b_no",b_no);
		return mav;
	}
	
	//----------------------------------------------------------------------------
	@RequestMapping(value="boardUpDelForm.do")
	public ModelAndView goBoardUpDelForm(@RequestParam(value="b_no") int b_no) {
		BoardDTO boardDTO = this.boardService.getBoard(b_no);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("boardUpDelForm.jsp");
		mav.addObject("boardDTO",boardDTO);
		return mav;
	}
	
	//----------------------------------------------------------------------------
	@RequestMapping(value="boardUpDelProc.do")
	public ModelAndView checkBoardUpDelForm(
			BoardDTO boardDTO, @RequestParam(value="upDel") String upDel, BindingResult bindingResult
		) {
		System.out.println("checkBoardUpDelForm 호출시작");
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("boardUpDelProc.jsp");
		
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
}
