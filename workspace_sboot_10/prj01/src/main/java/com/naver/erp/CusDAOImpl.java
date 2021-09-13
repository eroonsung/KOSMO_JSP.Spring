package com.naver.erp;


import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CusDAOImpl implements CusDAO{
	@Autowired
	private SqlSessionTemplate sqlSession;

	@Override
	public int getCusListAllCnt(CusSearchDTO cusSearchDTO) {
		int cusListAllCnt = this.sqlSession.selectOne(
				"com.naver.erp.CusDAO.getCusListAllCnt"
				, cusSearchDTO
			);
		
		return cusListAllCnt;
	}

	@Override
	public List<Map<String, String>> getCusList(CusSearchDTO cusSearchDTO) {
		List<Map<String,String>> cusList = this.sqlSession.selectList(
				"com.naver.erp.CusDAO.getCusList" // 실행할 SQL구문의 위치 지정
				,cusSearchDTO // 실행할 SQL 구문에서 사용할 데이터 지정
			);
		
		return cusList;
	}

	@Override
	public List<EmpDTO> getEmpList() {
		List<EmpDTO> empList = this.sqlSession.selectList(
				"com.naver.erp.CusDAO.getEmpList");
		return empList;
	}

	@Override
	public int insertCus(CusDTO cusDTO) {
		int cusRegCnt =this.sqlSession.insert(
				"com.naver.erp.CusDAO.insertCus"
				, cusDTO 
			); 
		
		return cusRegCnt;
	}

	@Override
	public List<String> getAgeDecadeList() {
		List<String> ageDecadeList = this.sqlSession.selectList(
				"com.naver.erp.CusDAO.getAgeDecadeList"
				);
		return ageDecadeList;
		
	}

	@Override
	public CusDTO getCus(int cus_no) {
		CusDTO cusDTO = this.sqlSession.selectOne(
				"com.naver.erp.CusDAO.getCus"
				, cus_no
				);
		return cusDTO;
	}

	@Override
	public int getCusCnt(CusDTO cusDTO) {
		int cusCnt = this.sqlSession.selectOne(
				"com.naver.erp.CusDAO.getCusCnt" // 실행할 SQL 구문의 위치 지정
				, cusDTO	// 실행할 SQL 구문에서 사용할 데이터 지정
				);
		return cusCnt;
	}

	@Override
	public int updateCus(CusDTO cusDTO) {
		int updateCnt = this.sqlSession.update(
				"com.naver.erp.CusDAO.updateCus" // 실행할 SQL 구문의 위치 지정
				, cusDTO	// 실행할 SQL 구문에서 사용할 데이터 지정
				);
		return updateCnt;
	}

	@Override
	public int deleteCus(CusDTO cusDTO) {
		int deleteCnt = this.sqlSession.delete(
				"com.naver.erp.CusDAO.deleteCus" // 실행할 SQL 구문의 위치 지정
				, cusDTO	// 실행할 SQL 구문에서 사용할 데이터 지정
				);
		return deleteCnt;
	}

	

}
