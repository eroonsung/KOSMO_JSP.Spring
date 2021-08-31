package com.naver.erp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BoardController {
	
	//***********************************************
	//이 인터페이스를 구현한 클래스를 찾아서 개체화한 후 객체의 메위주를 속성변수에 저장
	//***********************************************
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private BoardDAO boardDAO;
	
	//***********************************************
	//가상 주소 /loginForm.do로 접근하면 호출되는 메소드 선언
	//***********************************************
	@RequestMapping(value="boardList.do")
	public ModelAndView goBoardList(BoardSearchDTO boardSearchDTO) {
		System.out.println("BoardController.goBoardList 메소드 시작");
		List<Map<String,String>> boardList = this.boardDAO.getBoardList(boardSearchDTO);
		System.out.println("BoardController.goBoardList 메소드 완료");
		
		System.out.println("BoardController.getBoardListAllCnt 메소드 시작");
		int boardListAllCnt = this.boardDAO.getBoardListAllCnt(boardSearchDTO);
		System.out.println("BoardController.getBoardListAllCnt 메소드 완료");
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("boardList.jsp");
		mav.addObject("boardList",boardList);
		mav.addObject("boardListAllCnt",boardListAllCnt);
		return mav;
	}
	
	//***********************************************
	//가상 주소 /boardRegForm.do로 접근하면 호출되는 메소드 선언
	//***********************************************
	@RequestMapping(value="boardRegForm.do")
	public ModelAndView goBoardRegForm(
			@RequestParam(value="b_no", required=false, defaultValue="0")int b_no
			) {
		System.out.println("BoardController.goBoardRegForm 메소드 시작");
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("boardRegForm.jsp");
		System.out.println("BoardController.goBoardRegForm 메소드 완료");
		return mav;
	}
	
	//***********************************************
	//가상 주소 /boardRegProc.do로 접근하면 호출되는 메소드 선언
	//***********************************************
	@RequestMapping(value="boardRegProc.do")
	public ModelAndView insertBoard(
			BoardDTO boardDTO
			, BindingResult bindingResult
		) {
		System.out.println("BoardController.insertBoard 메소드 시작");
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("boardRegProc.jsp");
	
		try {
			String msg="";
			msg = check_BoardDTO(boardDTO,bindingResult);
			mav.addObject("msg",msg);
			
			if(msg.equals("")) {
				int boardRegCnt = this.boardService.insertBoard(boardDTO);
				mav.addObject("boardRegCnt", boardRegCnt);
			}else {
				mav.addObject("boardRegCnt", 0);
			}
		}catch(Exception e) {
			mav.addObject("msg", "서버에서 문제 발생. 서버관리자에게 문의 바람");
			mav.addObject("boardRegCnt",-1);
			
		}
		
		System.out.println("BoardController.insertBoard 메소드 완료");
		return mav;
	}
	//----------------------------------------------------
	//게시판 입력 또는 수정 시 게시판 입력 글의 입력양식의 유효성을 검사하고
	//문제가 있으면 경고 문자를 리턴하는 메소드 선언
	//----------------------------------------------------
	private String check_BoardDTO(BoardDTO boardDTO, BindingResult bindingResult) {
		System.out.println(" >>BoardController.check_BoardDTO 메소드 시작");
		String checkMsg="";
		BoardValidator boardValidator = new BoardValidator();
		boardValidator.validate(boardDTO, bindingResult);
		
		if(bindingResult.hasErrors()) {
			checkMsg = bindingResult.getFieldError().getCode();
		}
		System.out.println(" >>BoardController.check_BoardDTO 메소드 완료");
		return checkMsg;
	}
	
	//***********************************************
	//가상 주소 /boardContentForm.do로 접근하면 호출되는 메소드 선언
	//***********************************************
	@RequestMapping(value="boardContentForm.do")
	public ModelAndView goBoardContentForm(
			@RequestParam(value="b_no") int b_no
			) {
		System.out.println("BoardController.goBoardContentForm 메소드 시작");
		
		BoardDTO boardDTO= this.boardService.getBoard(b_no);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("boardContentForm.jsp");
		mav.addObject("boardDTO", boardDTO);
		mav.addObject("b_no",b_no);
		System.out.println("BoardController.goBoardContentForm 메소드 완료");
		return mav;
	}

	//***********************************************
	//가상 주소 /boardUpDelForm.do로 접근하면 호출되는 메소드 선언
	//***********************************************
	@RequestMapping(value="boardUpDelForm.do")
	public ModelAndView goBoardUpDelForm(@RequestParam(value="b_no") int b_no) {
		System.out.println("BoardController.goBoardUpDelForm 메소드 시작");
		BoardDTO boardDTO = this.boardDAO.getBoard(b_no);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("boardUpDelForm.jsp");
		mav.addObject("boardDTO",boardDTO);	
		System.out.println("BoardController.goBoardUpDelForm 메소드 완료");
		return mav;
	}
	
	//***********************************************
	//가상 주소 /boardUpDelProc.do로 접근하면 호출되는 메소드 선언
	//***********************************************
	@RequestMapping(value="boardUpDelProc.do")
	public ModelAndView checkBoardUpDelForm(
			BoardDTO boardDTO
			, @RequestParam(value="upDel") String upDel
			, BindingResult bindingResult
		) {
		System.out.println("BoardController.checkBoardUpDelForm 메소드 시작");
		
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
		System.out.println("BoardController.checkBoardUpDelForm 메소드 완료");
		return mav;
	}
}
