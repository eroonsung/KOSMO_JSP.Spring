package com.naver.erp;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BoardDAOImpl implements BoardDAO{
	//++++++++++++++++++++++++++++++++++++++++++++++++++
	//속성변수 sqlSession을 선언하고, [SqlSessionTemplate 객체]를 생성해 저장
	//++++++++++++++++++++++++++++++++++++++++++++++++++
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	//==========================================================================
	//***********************************************
	// [게시판 글 입력 후 입력 적용 행의 개수] 리턴하는 메소드 선언
	//***********************************************
	public List<Map<String,String>> getBoardList(){
		System.out.println("		BoardDAOImpl.getBoardList 메소드 시작");
		
		List<Map<String,String>> boardList = this.sqlSession.selectList(
				"com.naver.erp.BoardDAO.getBoardList" 
			);
		
		System.out.println("		BoardDAOImpl.getBoardList 메소드 완료");
		return boardList;
	}
	//==========================================================================
	//***********************************************
	// [검색한 게시판 목록] 리턴하는 메소드 선언
	//***********************************************
	public int insertBoard(BoardDTO boardDTO) {
		System.out.println("		BoardDAOImpl.insertBoard 메소드 시작");
		
		int boardRegCnt = this.sqlSession.insert(
				"com.naver.erp.BoardDAO.insertBoard"
				, boardDTO
				);
				
		System.out.println("		BoardDAOImpl.insertBoard 메소드 완료");
		return boardRegCnt;
	}
	
	//==========================================================================
	//***********************************************
	// [게시판 글 조회수 증가하고 수정행의 개수]를 리턴하는 메소드 선언
	//***********************************************
	public int updateReadcount(int b_no) {
		System.out.println("		BoardDAOImpl.updateReadcount 메소드 시작");
		
		int updateCnt = this.sqlSession.update(
				"com.naver.erp.BoardDAO.updateReadcount"
				, b_no
				);
		
		System.out.println("		BoardDAOImpl.updateReadcount 메소드 완료");
		return updateCnt;
	}
	//***********************************************
	// [1개 게시판 글 정보]를 리턴하는 메소드 선언
	//***********************************************
	public BoardDTO getBoard(int b_no) {
		System.out.println("		BoardDAOImpl.getBoard 메소드 시작");
		
		BoardDTO boardDTO = this.sqlSession.selectOne(
				"com.naver.erp.BoardDAO.getBoard"
				, b_no
				);
				
		System.out.println("		BoardDAOImpl.getBoard 메소드 완료");
		return boardDTO;
	}
	
	//==========================================================================
	//***********************************************
	// [수정할 게시판의 존개 개수]를 리턴하는 메소드 선언
	//***********************************************
	public int getBoardCnt(BoardDTO boardDTO) {
		System.out.println("		BoardDAOImpl.getBoardCnt 메소드 시작");
		
		int boardCnt = this.sqlSession.selectOne(
				"com.naver.erp.BoardDAO.getBoardCnt"
				, boardDTO
				);
		
		System.out.println("		BoardDAOImpl.getBoardCnt 메소드 완료");
		return boardCnt;
	}

	//***********************************************
	// [수정할 게시판의 비밀번호 존재 개수]를 리턴하는 메소드 선언
	//***********************************************
	public int getPwdCnt(BoardDTO boardDTO) {
		System.out.println("		BoardDAOImpl.getPwdCnt 메소드 시작");
		
		int pwdCnt = this.sqlSession.selectOne(
				"com.naver.erp.BoardDAO.getPwdCnt"
				, boardDTO
				);
		
		System.out.println("		BoardDAOImpl.getPwdCnt 메소드 완료");
		return pwdCnt;
	}

	//***********************************************
	// [게시판 수정 명령 후 수정 적용행의 개수]를 리턴하는 메소드 선언
	//***********************************************
	public int updateBoard(BoardDTO boardDTO) {
		System.out.println("		BoardDAOImpl.updateBoard 메소드 시작");
		
		int updateCnt = this.sqlSession.update(
				"com.naver.erp.BoardDAO.updateBoard"
				, boardDTO
				);
		
		System.out.println("		BoardDAOImpl.updateBoard 메소드 완료");
		return updateCnt;
	}

	//==========================================================================
	//***********************************************
	// [삭제할 게시판의 자식글 존재 개수]를 리턴하는 메소드 선언
	//***********************************************
	public int getChildrenCnt(BoardDTO boardDTO) {
		System.out.println("		BoardDAOImpl.getChildrenCnt 메소드 시작");
		
		int childrenCnt = this.sqlSession.selectOne(
				"com.naver.erp.BoardDAO.getChildrenCnt" 
				, boardDTO	
				);
		
		System.out.println("		BoardDAOImpl.getChildrenCnt 메소드 완료");
		return childrenCnt;
	}

	//***********************************************
	// [삭제할 게시판의 이후 글의 출력순서를 1씩 감소]시키는는 메소드 선언
	//***********************************************
	public int downPrintNo(BoardDTO boardDTO) {
		System.out.println("		BoardDAOImpl.downPrintNo 메소드 시작");
		
		int downPrintNo = this.sqlSession.update(
				"com.naver.erp.BoardDAO.downPrintNo"
				, boardDTO
				);
		
		System.out.println("		BoardDAOImpl.downPrintNo 메소드 완료");
		return downPrintNo;
	}
	
	//***********************************************
	// [게시판 삭제 명령 후 삭제 적용행의 개수]를 리턴하는 메소드 선언
	//***********************************************
	public int deleteBoard(BoardDTO boardDTO) {
		System.out.println("		BoardDAOImpl.deleteBoard 메소드 시작");
		
		int deleteCnt = this.sqlSession.delete(
				"com.naver.erp.BoardDAO.deleteBoard"
				, boardDTO
				);
		
		System.out.println("		BoardDAOImpl.deleteBoard 메소드 완료");
		return deleteCnt;
	}	
}
