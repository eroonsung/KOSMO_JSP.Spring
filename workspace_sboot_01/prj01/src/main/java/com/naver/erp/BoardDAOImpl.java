package com.naver.erp;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.mybatis.spring.SqlSessionTemplate;

@Repository
public class BoardDAOImpl implements BoardDAO{
	@Autowired
	private SqlSessionTemplate sqlSession;

	public List<Map<String,String>> getBoardList(){
		List<Map<String,String>> boardList = this.sqlSession.selectList(
				"com.naver.erp.BoardDAO.getBoardList"
				);
		return boardList;
	};
	
	public int insertBoard(BoardDTO boardDTO) {
		int boardRegCnt = this.sqlSession.insert(
				"com.naver.erp.BoardDAO.insertBoard"
				,boardDTO
			);
		return boardRegCnt;
	}
	
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
}

