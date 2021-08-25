package com.naver.erp;

public interface BoardService {
	//***인터페이스 사용 이유***
	// controller 층에서 속성변수 옆에 인터페이스를 붙이고 @Autowired를 사용하면 클래스의 이름을 바꿔도 작동한다.
	// 정해진 메소드 이름만 가져다 쓸 수 있으므로 작업의 통일성을 유지할 수 있다.
	
	//*************************************************
	//[게시판 글 입력 후 입력 적용 행의 개수]를 리턴하는 메소드 선언
	//*************************************************
	int insertBoard(BoardDTO boardDTO);
		//=> (public) (abstract) int insertBoard (BoardDTO boardDTO);
}
