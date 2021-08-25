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
		//BoardDAOImpl 객체의 insertBoard 메소드를 호출하여 게시판 글 입력 후 입력 적용 행의 개수 얻기
		//-------------------------------------------------
		int boardRegCnt = this.boardDAO.insertBoard(boardDTO);
		
		//-------------------------------------------------
		//1개 게시판 글 입력 적용 행의 개수 리턴하기
		//-------------------------------------------------
		System.out.println("BoardServiceImpl.insertBoard 메소드 호출 성공"); 
		return boardRegCnt;
	}
}
