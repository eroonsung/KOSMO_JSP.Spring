package com.naver.erp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BoardServiceImpl implements BoardService{
	//----------------------------------------------------------------------------
	@Autowired
	private BoardDAO boardDAO;

	//----------------------------------------------------------------------------
	/*
	public int insertBoard(BoardDTO boardDTO) {
		int boardRegCnt = this.boardDAO.insertBoard(boardDTO);
		return boardRegCnt;
	}
	*/
	public int insertBoard(BoardDTO boardDTO) {
		if(boardDTO.getB_no()>0) {
			int updatePrintNoCnt = this.boardDAO.updatePrintNo(boardDTO);
		}
		
		int boardRegCnt = this.boardDAO.insertBoard(boardDTO);
		return boardRegCnt;
	}
	
	
	//----------------------------------------------------------------------------
	public BoardDTO getBoard(int b_no) {
		int updateCnt = this.boardDAO.updateReadcount(b_no);
		if(updateCnt==0) {return null;}
		
		BoardDTO boardDTO = this.boardDAO.getBoard(b_no);
		return boardDTO;
	}
	
	//----------------------------------------------------------------------------
	public int updateBoard(BoardDTO boardDTO) {
		int boardCnt = this.boardDAO.getBoardCnt(boardDTO);
		if(boardCnt == 1) {return -1;}
		
		int pwdCnt = this.boardDAO.getPwdCnt(boardDTO);
		if(pwdCnt == 1) {return -2;}
		
		int updateCnt = this.boardDAO.updateBoard(boardDTO);
		
		return updateCnt;
	}
	//----------------------------------------------------------------------------
	public int deleteBoard(BoardDTO boardDTO) {
		int boardCnt = this.boardDAO.getBoardCnt(boardDTO);
		if(boardCnt==0) {return -1;}
		
		int pwdCnt = this.boardDAO.getPwdCnt(boardDTO);
		if(pwdCnt==0) {return -2;}
		
		int childrenCnt = this.boardDAO.getChildrenCnt(boardDTO);
		if(childrenCnt>0) {return -3;}
		
		int downPrintNo = this.boardDAO.downPrintNo(boardDTO);
		
		int deleteCnt = this.boardDAO.deleteBoard(boardDTO);
		
		return deleteCnt;
		
	}
}

