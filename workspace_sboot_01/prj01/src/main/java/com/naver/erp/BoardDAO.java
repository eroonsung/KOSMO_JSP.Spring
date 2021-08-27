package com.naver.erp;

import java.util.List;
import java.util.Map;

public interface BoardDAO {
	
	List<Map<String,String>> getBoardList();
	
	int insertBoard(BoardDTO boardDTO);
	
	int updateReadcount(int b_no);
	
	BoardDTO getBoard(int b_no);
}