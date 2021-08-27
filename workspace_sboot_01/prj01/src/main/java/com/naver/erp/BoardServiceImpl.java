package com.naver.erp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BoardServiceImpl implements BoardService{
	@Autowired
	private BoardDAO boardDAO;

	public int insertBoard(BoardDTO boardDTO) {
		int boardRegCnt = this.boardDAO.insertBoard(boardDTO);
		return boardRegCnt;
	}
	
	public BoardDTO getBoard(int b_no) {
		int updateCnt = this.boardDAO.updateReadcount(b_no);
		if(updateCnt==0) {return null;}
		
		BoardDTO boardDTO = this.boardDAO.getBoard(b_no);
		return boardDTO;
	}
}

