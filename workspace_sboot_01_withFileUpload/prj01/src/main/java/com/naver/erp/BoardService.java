package com.naver.erp;

import org.springframework.web.multipart.MultipartFile;

public interface BoardService {
	//----------------------------------------------------------------------------
	int insertBoard(BoardDTO boardDTO, MultipartFile multi) throws Exception;
	
	//----------------------------------------------------------------------------
	BoardDTO getBoard(int b_no);
	
	//----------------------------------------------------------------------------
	int updateBoard(BoardDTO boardDTO, MultipartFile multi) throws Exception;

	//----------------------------------------------------------------------------
	int deleteBoard(BoardDTO boardDTO);
	
}