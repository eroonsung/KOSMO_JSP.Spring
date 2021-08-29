package com.naver.erp;

public interface BoardService {

	//*************************************************
	int insertBoard(BoardDTO boardDTO);

	//*************************************************
	BoardDTO getBoard(int b_no);
	
	//*************************************************
	int updateBoard(BoardDTO boardDTO);
	
	//*************************************************
	int deleteBoard(BoardDTO boardDTO);

}
