package com.naver.erp;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class EmpDAOImpl implements EmpDAO{
	@Autowired
	private SqlSessionTemplate sqlSession;

	
	public int getEmpListAllCnt(EmpSearchDTO empSearchDTO) {				
		int empListAllCnt = this.sqlSession.selectOne(
				"com.naver.erp.EmpDAO.getEmpListAllCnt"
				, empSearchDTO
			);
		
		return empListAllCnt;
	}

	public List<Map<String, String>> getEmpList(EmpSearchDTO empSearchDTO) {
		List<Map<String,String>> empList = this.sqlSession.selectList(
				"com.naver.erp.EmpDAO.getEmpList" // 실행할 SQL구문의 위치 지정
				,empSearchDTO // 실행할 SQL 구문에서 사용할 데이터 지정
			);
		
		return empList;
	}

	public EmpDTO getEmp(int emp_no) {
		EmpDTO empDTO = this.sqlSession.selectOne(
				"com.naver.erp.EmpDAO.getEmp"
				, emp_no
				);
		return empDTO;
	}


	public int updateEmp(EmpDTO empDTO) {
		int updateCnt = this.sqlSession.update(
				"com.naver.erp.EmpDAO.updateEmp" // 실행할 SQL 구문의 위치 지정
				, empDTO	// 실행할 SQL 구문에서 사용할 데이터 지정
				);
		return updateCnt;
	}

	
	public int deleteEmp(EmpDTO empDTO) {
		int deleteCnt = this.sqlSession.delete(
				"com.naver.erp.EmpDAO.deleteEmp" // 실행할 SQL 구문의 위치 지정
				, empDTO	// 실행할 SQL 구문에서 사용할 데이터 지정
				);
		return deleteCnt;
	}

	
	public int getEmpCnt(EmpDTO empDTO) {
		int empCnt = this.sqlSession.selectOne(
				"com.naver.erp.EmpDAO.getEmpCnt" // 실행할 SQL 구문의 위치 지정
				, empDTO	// 실행할 SQL 구문에서 사용할 데이터 지정
				);
		return empCnt;
	}

	public int insertEmp(EmpDTO empDTO) {
		int empRegCnt =this.sqlSession.insert(
				"com.naver.erp.EmpDAO.insertEmp"
				, empDTO 
			); 
			return empRegCnt;
	}

	public List<String> getJikupList() {
		List<String> jikupList = this.sqlSession.selectList(
				"com.naver.erp.EmpDAO.getJikupList"
				);
		return jikupList;
	}

	@Override
	public List<String> getDepNameList() {
		List<String> depNameList = this.sqlSession.selectList(
				"com.naver.erp.EmpDAO.getdepNameList"
				);
		return depNameList;
	}

	@Override
	public int getMinY() {
		int minY = this.sqlSession.selectOne("com.naver.erp.EmpDAO.getMinY");
		return minY;
	}

	@Override
	public int getMaxY() {
		int maxY = this.sqlSession.selectOne("com.naver.erp.EmpDAO.getMaxY");
		return maxY;
	}
	
	@Override
	public int updateMgrEmpNo(EmpDTO empDTO) {
		int updateMgrEmpNoCnt = this.sqlSession.update(
				"com.naver.erp.EmpDAO.updateMgrEmpNo" // 실행할 SQL 구문의 위치 지정
				, empDTO	// 실행할 SQL 구문에서 사용할 데이터 지정
				);
		return updateMgrEmpNoCnt;
	}

	@Override
	public int updateCusEmpNo(EmpDTO empDTO) {
		int updateCusEmpNoCnt = this.sqlSession.update(
				"com.naver.erp.EmpDAO.updateCusEmpNo" // 실행할 SQL 구문의 위치 지정
				, empDTO	// 실행할 SQL 구문에서 사용할 데이터 지정
				);
		return updateCusEmpNoCnt;
	}

	@Override
	public List<Map<String, String>> getDepNoAndNameList() {
		List<Map<String,String>> depNoAndNameList = this.sqlSession.selectList(
				"com.naver.erp.EmpDAO.getDepNoAndNameList");
		return depNoAndNameList;
	}

	@Override
	public List<EmpDTO> getMgrEmpList() {
		List<EmpDTO> MgrEmpList = this.sqlSession.selectList(
				"com.naver.erp.EmpDAO.getMgrEmpList");
		return MgrEmpList;
	}

	@Override
	public String getPic(EmpDTO empDTO) {
		String pic = this.sqlSession.selectOne(
				"com.naver.erp.EmpDAO.getPic"
				, empDTO);
		return pic;
	}

}
