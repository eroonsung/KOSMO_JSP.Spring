package com.naver.erp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
//[서비스 클래스]인 [BoardServiceImpl 클래스] 선언
//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
	//[서비스 클래스]에는 @Service와 @Transactional를 붙인다
	//@Service => [서비스 클래스]임을 지정하고 bean 태그로 자동 등록된다.
	//@Transactional => [서비스 클래스]의 메소드 내부에서 일어나는 모든 작업에는 [트랜잭션]이 걸린다.
	// DB연동의 세분화
//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
@Service
@Transactional
public class BoardServiceImpl implements BoardService {
	
	//*************************************************
	// 속성변수 boardDAO 선언하고, [BoardDAO 인터페이스]를 구현한 클래스를 객체화해서 저장한다.
	//*************************************************
		// @Autowired => 속성변수에 붙은 자료형인 [인터페이스]를 구현한 [클래스]를 객체화하여 저장한다.
	@Autowired
	private BoardDAO boardDAO;
	
	//*************************************************
	// [1개 게시판 글 입력 후 입력 적용 행의 개수]를 리턴하는 메소드 선언
	//*************************************************
	public int insertBoard(BoardDTO boardDTO) {
		//-------------------------------------------------
		//만약 엄마 글 번호(b_no)가 1 이상이면 댓글쓰기이므로
		//엄마 글 이후의 게시판 글에 대해 출력순서번호를 1증가 시키기
		//-------------------------------------------------
		if(boardDTO.getB_no()>0) {
			//-------------------------------------------------
			//BoardDAOImpl 객체의 updatePrintNo 메소드를 호출하여 출력순서 번호를 1 증가시키고
			//수정행에 적용 개수를 리턴받는다.
			//게시판 글이 입력되는 부분 이후 글들은 출력 순서번호를 1씩 증가시켜야 한다.
			// 이 값이 0이든 1이든 프로그램에 영향을 미치지 않음(있으면 업데이트 하고 없으면 하지마)
			//-------------------------------------------------
			int updatePrintNoCnt = this.boardDAO.updatePrintNo(boardDTO);
		}
		
		//-------------------------------------------------
		//BoardDAOImpl 객체의 insertBoard 메소드를 호출하여 게시판 글 입력 후 입력 적용 행의 개수 얻기
		//-------------------------------------------------
		int boardRegCnt = this.boardDAO.insertBoard(boardDTO);
		
		//-------------------------------------------------
		//1개 게시판 글 입력 적용 행의 개수 리턴하기
		//-------------------------------------------------
		System.out.println("BoardServiceImpl.insertBoard 메소드 호출 성공"); 
		return boardRegCnt;
	}
	
	//*************************************************
	// [1개 게시판 글]을 리턴하는 메소드 선언
	//*************************************************
	public BoardDTO getBoard(int b_no) {
		//트랜잭션이 걸리는 층 
		// -> SQL 구문이 여러개이면 SQL구문 1개씩 나눠서 DB연동 지시(update, select)
		
		//-------------------------------------------------
		//[BoardDAOImpl 객체]의 updateReadcount 메소드를 호출하여 [조회수를 증가하고]
		// 수정한 행의 개수를 얻는다.
		//-------------------------------------------------
		int updateCnt= this.boardDAO.updateReadcount(b_no);
		if(updateCnt==0) {return null;}
		
		//-------------------------------------------------
		//[BoardDAOImpl 객체]의 getBoard 메소드를 호출하여 [1개 게시판 글]을 얻는다.
		//-------------------------------------------------
		BoardDTO boardDTO = this.boardDAO.getBoard(b_no);
		
		//-------------------------------------------------
		//[1개 게시판 글]이 저장된 BoardDTO 객체 리턴하기
		//-------------------------------------------------
		return boardDTO;
	}
	
	//*************************************************
	// [1개 게시판 글]을 수정 실행하고 수정 적용행의 개수를 리턴하는 메소드 선언
	//*************************************************
	public int updateBoard (BoardDTO boardDTO) {
		//-------------------------------------------------
		//[BoardDAOImpl 객체]의 getBoardCnt 메소드를 호출하여
		// 수정할 게시판의 존재 개수를 얻는다.
		//-------------------------------------------------
		int boardCnt = this.boardDAO.getBoardCnt(boardDTO);
			// 게시판이 존재하지 않으면 -1을 리턴
		if(boardCnt==0) {return -1;}
		
		//-------------------------------------------------
		//[BoardDAOImpl 객체]의 getPwdCnt 메소드를 호출하여
		// 수정할 게시판의 비밀번호 존재 개수를 얻는다.
			//=> 생략 가능함
		//-------------------------------------------------
		int pwdCnt = this.boardDAO.getPwdCnt(boardDTO);
			// 비밀번호가 일치하지 않으면 -2를 리턴
		if(pwdCnt==0) {return -2;}
		
		//-------------------------------------------------
		//[BoardDAOImpl 객체]의 updateBoard 메소드를 호출하여 
		//게시판 글 수정 후 입력 수정 행의 개수 얻기
		//-------------------------------------------------
		int updateCnt = this.boardDAO.updateBoard(boardDTO);
		//-------------------------------------------------
		//게시판 수정 명령한 후 수정 적용행의 개수를 리턴하기
		//-------------------------------------------------
		return updateCnt;
	}
	
	//*************************************************
	//[1개 게시판 글]을 삭제 실행하고 삭제 적용행의 개수를 리턴하는 메소드 선언
	//*************************************************
	public int deleteBoard(BoardDTO boardDTO){
		//-------------------------------------------------
		//[BoardDAOImpl 객체]의 getBoardCnt 메소드를 호출하여
		// 삭제할 게시판의 존재 개수를 얻는다.
		//-------------------------------------------------
		int boardCnt = this.boardDAO.getBoardCnt(boardDTO);
			// 게시판이 존재하지 않으면 -1을 리턴
		if(boardCnt==0) {return -1;}
		
		//-------------------------------------------------
		//[BoardDAOImpl 객체]의 getPwdCnt 메소드를 호출하여
		// 삭제할 게시판의 비밀번호 존재 개수를 얻는다.
			//=> 생략 가능함
		//-------------------------------------------------
		int pwdCnt = this.boardDAO.getPwdCnt(boardDTO);
			// 비밀번호가 일치하지 않으면 -2를 리턴
		if(pwdCnt==0) {return -2;}
		
		//댓글이 있는 글
		//1.삭제를 못하게 하거나
		//2.삭제를 못하게 하는 대신 제목을 "삭제된 글입니다"+상세보기 금지
		//-------------------------------------------------
		//[BoardDAOImpl 객체]의 getChildrenCnt 메소드를 호출하여
		//삭제할 게시판의 자식글 존재 개수를 얻는다.
		//-------------------------------------------------
		int childrenCnt = this.boardDAO.getChildrenCnt(boardDTO);
		// 자식글이 있으면 -3을 리턴함
		if(childrenCnt>0) {return -3;}
		
		//위에 조건문을 다 넘어 온 뒤 실행됨
		//-------------------------------------------------
		//[BoardDAOImpl 객체]의 downPrintNo 메소드를 호출하여
		//삭제될 게시판 이후 글의 출력순서번호를 1씩 감소시킨 후 수정 적용 행의 개수를 얻는다.
		//-------------------------------------------------
		//댓글이 없는 글을 삭제하게 된다면 내 밑에 있는 글들의 print_no를 업데이트 해야함
		// 이 값이 0이든 1이든 프로그램에 영향을 미치지 않음(있으면 업데이트 하고 없으면 하지마)
		int downPrintNo = this.boardDAO.downPrintNo(boardDTO);
		
		//-------------------------------------------------
		//[BoardDAOImpl 객체]의 deleteBoard 메소드를 호출하여 
		// 게시판 글 삭제 후 삭제 행의 개수 얻기
		//-------------------------------------------------
		int deleteCnt = this.boardDAO.deleteBoard(boardDTO);
		
		return deleteCnt;
	}
}
