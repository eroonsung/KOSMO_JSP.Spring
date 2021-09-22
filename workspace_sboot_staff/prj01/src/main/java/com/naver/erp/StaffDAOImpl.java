package com.naver.erp;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class StaffDAOImpl implements StaffDAO{
	@Autowired
	private SqlSessionTemplate sqlSession;

	@Override
	public int getStaffListAllCnt(StaffSearchDTO staffSearchDTO) {
		int staffListAllCnt = this.sqlSession.selectOne(
				"com.naver.erp.StaffDAO.getStaffListAllCnt"
				, staffSearchDTO
				);
		return staffListAllCnt;
	}
	
	@Override
	public List<StaffDTO> getReligionList() {
		List<StaffDTO> religionList = this.sqlSession.selectList(
				"com.naver.erp.StaffDAO.getReligionList");
		return religionList;
	}

	@Override
	public List<StaffDTO> getSchoolList() {
		List<StaffDTO> schoolList = this.sqlSession.selectList(
				"com.naver.erp.StaffDAO.getSchoolList");
		return schoolList;
	}

	@Override
	public List<Map<String, String>> getSkillList() {
		 List<Map<String, String>> skillList = this.sqlSession.selectList(
				 "com.naver.erp.StaffDAO.getSkillList");
		return skillList;
	}
	
	@Override
	public List<Map<String, String>> getXxxSkillList(int staff_no) {
		List<Map<String, String>> xxxSkillList = this.sqlSession.selectList(
				 "com.naver.erp.StaffDAO.getXxxSkillList"
				,staff_no);
		return xxxSkillList;
	}

	@Override
	public List<Map<String, String>> getStaffList(StaffSearchDTO staffSearchDTO) {
		List<Map<String, String>> staffList = this.sqlSession.selectList(
				 "com.naver.erp.StaffDAO.getStaffList"
				,staffSearchDTO
				);
		return staffList;
	}
	
	
	@Override
	public StaffDTO getStaffDTO(int staff_no) {
		StaffDTO staffDTO = this.sqlSession.selectOne(
				 "com.naver.erp.StaffDAO.getStaffDTO"
				,staff_no
				);
		return staffDTO;
	}


	@Override
	public List<StaffDTO> getStaffSkillDTO(int staff_no) {
		List<StaffDTO> staffSkillList = this.sqlSession.selectList(
				"com.naver.erp.StaffDAO.getStaffSkillDTO"
				,staff_no
				);
		return staffSkillList;
	}

	@Override
	public int insertStaff(StaffDTO staffDTO) {
		System.out.println("StaffDAO.insertStaff 시작");
		int insertStaffCnt= this.sqlSession.insert(
				"com.naver.erp.StaffDAO.insertStaff"
				, staffDTO
				);
		System.out.println("StaffDAO.insertStaff 완료");
		System.out.println("insertStaffCnt : "+insertStaffCnt);
		return insertStaffCnt;
	}

	@Override
	public int insertStaffSkill(StaffDTO staffDTO) {
		System.out.println("StaffDAO.insertStaffSkill 시작");
		int insertStaffSkillCnt = this.sqlSession.insert(
				"com.naver.erp.StaffDAO.insertStaffSkill"
				,staffDTO
			);
		System.out.println("StaffDAO.insertStaffSkill 완료");
		System.out.println("insertStaffSkillCnt : "+insertStaffSkillCnt);
		return insertStaffSkillCnt;
	}

	
	
	@Override
	public int getStaffCnt(StaffDTO staffDTO) {
		int boardCnt = this.sqlSession.selectOne(
				"com.naver.erp.StaffDAO.getStaffCnt" // 실행할 SQL 구문의 위치 지정
				, staffDTO	// 실행할 SQL 구문에서 사용할 데이터 지정
				);
		return boardCnt;
	}

	@Override
	public String getPic(StaffDTO staffDTO) {
		String pic = this.sqlSession.selectOne(
				"com.naver.erp.StaffDAO.getPic"
				, staffDTO);
		return pic;
	}

	@Override
	public int updateStaff(StaffDTO staffDTO) {
		System.out.println("StaffDAO.updateStaff 시작");
		int updateCnt = this.sqlSession.update(
				"com.naver.erp.StaffDAO.updateStaff" // 실행할 SQL 구문의 위치 지정
				, staffDTO	// 실행할 SQL 구문에서 사용할 데이터 지정
				);
		System.out.println("updateCnt : "+updateCnt);
		System.out.println("StaffDAO.updateStaff 완료");
		return updateCnt;
	}

	@Override
	public int deleteStaff(StaffDTO staffDTO) {
		int deleteCnt = this.sqlSession.delete(
				"com.naver.erp.StaffDAO.deleteStaff" // 실행할 SQL 구문의 위치 지정
				, staffDTO	// 실행할 SQL 구문에서 사용할 데이터 지정
				);
		return deleteCnt;
	}

	@Override
	public int updateSkill(StaffDTO staffDTO) {
		System.out.println("StaffDAO.updateSkill 시작");
		int updateSkillCnt = this.sqlSession.update(
				"com.naver.erp.StaffDAO.updateSkill" // 실행할 SQL 구문의 위치 지정
				, staffDTO	// 실행할 SQL 구문에서 사용할 데이터 지정
				);
		System.out.println("StaffDAO.updateSkill 완료");
		return updateSkillCnt;
	}

	@Override
	public int deleteSkill(StaffDTO staffDTO) {
		System.out.println("StaffDAO.deleteSkill 시작");
		int deleteSkillCnt = this.sqlSession.delete(
				"com.naver.erp.StaffDAO.deleteSkill" // 실행할 SQL 구문의 위치 지정
				, staffDTO	// 실행할 SQL 구문에서 사용할 데이터 지정
				);
		System.out.println("StaffDAO.deleteSkill 완료");
		return deleteSkillCnt;
	}

	@Override
	public int updateStaffSkill(StaffDTO staffDTO) {
		System.out.println("StaffDAO.updateStaffSkill 시작");
		int updateStaffSkillCnt = this.sqlSession.insert(
				"com.naver.erp.StaffDAO.updateStaffSkill"
				,staffDTO
			);
		System.out.println("StaffDAO.updateStaffSkill 완료");
		return updateStaffSkillCnt;
	}


	

	

	
	
}
