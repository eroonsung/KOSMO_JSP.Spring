package com.naver.erp;

import org.springframework.web.multipart.MultipartFile;

public interface BoardService {
	//***인터페이스 사용 이유***
	// controller 층에서 속성변수 옆에 인터페이스를 붙이고 @Autowired를 사용하면 클래스의 이름을 바꿔도 작동한다.
	// 정해진 메소드 이름만 가져다 쓸 수 있으므로 작업의 통일성을 유지할 수 있다.
	
	//*************************************************
	//[게시판 글 입력 후 입력 적용 행의 개수]를 리턴하는 메소드 선언
	//*************************************************
	int insertBoard(BoardDTO boardDTO, MultipartFile multi) throws Exception;
		//=> (public) (abstract) int insertBoard (BoardDTO boardDTO);
	

	//*************************************************
	//[1개 게시판 글]을 리턴하는 메소드 선언
	//*************************************************
	BoardDTO getBoard(int b_no);
	
	
	//*************************************************
	//[1개 게시판 글]을 수정 실행하고 수정 적용행의 개수를 리턴하는 메소드 선언
	//*************************************************
	int updateBoard(BoardDTO boardDTO, MultipartFile multi) throws Exception;
	

	//*************************************************
	//[1개 게시판 글]을 삭제 실행하고 삭제 적용행의 개수를 리턴하는 메소드 선언
	//*************************************************
	int deleteBoard(BoardDTO boardDTO);
}
