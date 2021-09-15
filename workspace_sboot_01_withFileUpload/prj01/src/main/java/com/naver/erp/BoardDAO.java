package com.naver.erp;

import java.util.List;
import java.util.Map;

public interface BoardDAO {
	//--------------------------------------------------------------------------
	// List<Map<String,String>> getBoardList();
	List<Map<String,String>> getBoardList(BoardSearchDTO boardSearchDTO);
	
	int getBoardListAllCnt(BoardSearchDTO boardSearchDTO);
	
	//--------------------------------------------------------------------------
	int insertBoard(BoardDTO boardDTO);
	
	int updatePrintNo(BoardDTO boardDTO);
	
	//--------------------------------------------------------------------------
	int updateReadcount(int b_no);
	
	BoardDTO getBoard(int b_no);
	
	//--------------------------------------------------------------------------
	int getBoardCnt(BoardDTO boardDTO);
	
	int getPwdCnt(BoardDTO boardDTO);
	
	int updateBoard(BoardDTO boardDTO);
	
	//--------------------------------------------------------------------------
	int getChildrenCnt(BoardDTO boardDTO);
	
	int downPrintNo(BoardDTO boardDTO);
	
	int deleteBoard(BoardDTO boardDTO);

	//--------------------------------------------------------------------------
	String getPic(BoardDTO boardDTO);
}