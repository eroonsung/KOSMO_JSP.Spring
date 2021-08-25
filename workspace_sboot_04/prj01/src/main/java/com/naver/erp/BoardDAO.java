package com.naver.erp;

import java.util.List;
import java.util.Map;

public interface BoardDAO {

	//***********************************************
	//[게시판 글 입력 후 입력 적용 행의 개수] 리턴하는 메소드 선언
	//***********************************************
	int insertBoard( BoardDTO boardDTO );
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++
	// [검색한 게시판 목록] 리턴하는 메소드 선언
	//++++++++++++++++++++++++++++++++++++++++++++++++++
	List<Map<String,String>> getBoardList();
}
