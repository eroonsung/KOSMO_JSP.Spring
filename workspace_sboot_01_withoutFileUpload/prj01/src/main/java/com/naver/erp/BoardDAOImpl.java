package com.naver.erp;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.mybatis.spring.SqlSessionTemplate;

@Repository
public class BoardDAOImpl implements BoardDAO{
	//--------------------------------------------------------------------------
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	//--------------------------------------------------------------------------
	/*
	public List<Map<String,String>> getBoardList(){
		List<Map<String,String>> boardList = this.sqlSession.selectList(
				"com.naver.erp.BoardDAO.getBoardList"
				);
		return boardList;
	};
	*/
	public List<Map<String,String>> getBoardList(BoardSerachDTO boardSearchDTO){
		List<Map<String,String>> boardList = this.sqlSession.selectList(
				"com.naver.erp.BoardDAO.getBoardList"
				, boardSearchDTO
				);
		return boardList;
	};
	
	public int getBoardListAllCnt(BoardSerachDTO boardSearchDTO) {
		int boardListAllCnt = this.sqlSession.selectOne(
				"com.naver.erp.BoardDAO.getBoardListAllCnt"
				, boardSearchDTO				
				);
		return boardListAllCnt;
	}
	//--------------------------------------------------------------------------
	public int insertBoard(BoardDTO boardDTO) {
		int boardRegCnt = this.sqlSession.insert(
				"com.naver.erp.BoardDAO.insertBoard"
				,boardDTO
			);
		return boardRegCnt;
	}
	
	public int updatePrintNo(BoardDTO boardDTO) {
		int updatePrintNoCnt = this.sqlSession.update(
				"com.naver.erp.BoardDAO.updatePrintNo"
				,boardDTO
			);
		return updatePrintNoCnt;
	}
	
	//--------------------------------------------------------------------------
	public int updateReadcount(int b_no) {
		int updateCnt = this.sqlSession.update(
				"com.naver.erp.BoardDAO.updateCnt"
				,b_no
			);
		return updateCnt;
	}
	
	public BoardDTO getBoard(int b_no) {
		BoardDTO boardDTO = this.sqlSession.selectOne(
				"com.naver.erp.BoardDAO.getBoard"
				,b_no
			);
		return boardDTO;
	}
	
	//--------------------------------------------------------------------------
	public int getBoardCnt(BoardDTO boardDTO) {
		int boardCnt = this.sqlSession.selectOne(
				"com.naver.erp.BoardDAO.getBoardCnt"
				,boardDTO
				);
		return boardCnt;
	}
	
	public int getPwdCnt(BoardDTO boardDTO) {
		int pwdCnt = this.sqlSession.selectOne(
				"com.naver.erp.BoardDAO.getPwdCnt"
				,boardDTO
				);
		return pwdCnt;
	}
	
	public int updateBoard(BoardDTO boardDTO) {
		int updateCnt = this.sqlSession.update(
				"com.naver.erp.BoardDAO.updateBoard"
				,boardDTO
				);
		return updateCnt;
	}
	//--------------------------------------------------------------------------
	public int getChildrenCnt(BoardDTO boardDTO) {
		int childrenCnt = this.sqlSession.selectOne(
				"com.naver.erp.BoardDAO.getChildrenCnt" 
				, boardDTO	
				);
		return childrenCnt;
	}
	
	public int downPrintNo(BoardDTO boardDTO) {
		int downPrintNo = this.sqlSession.update(
				"com.naver.erp.BoardDAO.downPrintNo"
				, boardDTO
				);
		return downPrintNo;
	}
	
	public int deleteBoard(BoardDTO boardDTO) {
		int deleteCnt = this.sqlSession.delete(
				"com.naver.erp.BoardDAO.deleteBoard"
				, boardDTO
				);
		return deleteCnt;
	}
}

