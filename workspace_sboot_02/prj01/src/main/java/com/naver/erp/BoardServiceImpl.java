package com.naver.erp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BoardServiceImpl implements BoardService{
	//++++++++++++++++++++++++++++++++++++++++++++++++++
	//속성변수 boardDAO을 선언하고, [BoardDAO 객체]를 생성해 저장
	//++++++++++++++++++++++++++++++++++++++++++++++++++
	@Autowired
	private BoardDAO boardDAO;
	
	//*************************************************
	// [1개 게시판 글 입력 후 입력 적용 행의 개수]를 리턴하는 메소드 선언
	//*************************************************
	public int insertBoard(BoardDTO boardDTO) {
		System.out.println("	BoardServiceImpl.insertBoard 메소드 시작");
		int boardRegCnt = this.boardDAO.insertBoard(boardDTO);
		
		System.out.println("	BoardServiceImpl.insertBoard 메소드 완료");
		return boardRegCnt;
	}

	//*************************************************
	// [1개 게시판 글]을 리턴하는 메소드 선언
	//*************************************************
	public BoardDTO getBoard(int b_no) {
		System.out.println("	BoardServiceImpl.getBoard 메소드 시작");
		int updateCnt = this.boardDAO.updateReadcount(b_no);
		if(updateCnt==0) {return null;}		
		
		BoardDTO boardDTO = this.boardDAO.getBoard(b_no);
		System.out.println("	BoardServiceImpl.getBoard 메소드 완료");
		return boardDTO;
	}

	//*************************************************
	// [1개 게시판 글]을 수정 실행하고 수정 적용행의 개수를 리턴하는 메소드 선언
	//*************************************************
	public int updateBoard(BoardDTO boardDTO) {
		System.out.println("	BoardServiceImpl.updateBoard 메소드 시작");
		int boardCnt = this.boardDAO.getBoardCnt(boardDTO);
		if(boardCnt==0) {return -1;}
		
		int pwdCnt = this.boardDAO.getPwdCnt(boardDTO);
		if(pwdCnt==0) {return -2;}
		
		int updateCnt = this.boardDAO.updateBoard(boardDTO);
		
		System.out.println("	BoardServiceImpl.updateBoard 메소드 완료");
		return updateCnt;
	}

	//*************************************************
	//[1개 게시판 글]을 삭제 실행하고 삭제 적용행의 개수를 리턴하는 메소드 선언
	//*************************************************
	public int deleteBoard(BoardDTO boardDTO) {
		System.out.println("	BoardServiceImpl.deleteBoard 메소드 시작");
		
		int boardCnt = this.boardDAO.getBoardCnt(boardDTO);
		if(boardCnt==0) {return -1;}
		
		int pwdCnt = this.boardDAO.getPwdCnt(boardDTO);
		if(pwdCnt==0) {return -2;}
		
		int childrenCnt = this.boardDAO.getChildrenCnt(boardDTO);
		if(childrenCnt>0) {return -3;}
		
		int downPrintNo = this.boardDAO.downPrintNo(boardDTO);
		
		int deleteCnt = this.boardDAO.deleteBoard(boardDTO);
		System.out.println("	BoardServiceImpl.deleteBoard 메소드 완료");
		return deleteCnt;
	}
}
