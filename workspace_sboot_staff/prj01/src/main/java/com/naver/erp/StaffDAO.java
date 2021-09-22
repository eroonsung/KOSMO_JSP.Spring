package com.naver.erp;

import java.util.List;
import java.util.Map;

public interface StaffDAO {

	int getStaffListAllCnt(StaffSearchDTO staffSearchDTO);

	List<StaffDTO> getReligionList();

	List<StaffDTO> getSchoolList();

	List<Map<String,String>> getSkillList();

	List<Map<String, String>> getStaffList(StaffSearchDTO staffSearchDTO);

	StaffDTO getStaffDTO(int staff_no);

	List<StaffDTO> getStaffSkillDTO(int staff_no);

	List<Map<String, String>> getXxxSkillList(int staff_no);

	int insertStaff(StaffDTO staffDTO);

	int insertStaffSkill(StaffDTO staffDTO);

	int getStaffCnt(StaffDTO staffDTO);

	String getPic(StaffDTO staffDTO);

	int updateStaff(StaffDTO staffDTO);

	int deleteStaff(StaffDTO staffDTO);

	int updateSkill(StaffDTO staffDTO);

	int deleteSkill(StaffDTO staffDTO);

	int updateStaffSkill(StaffDTO staffDTO);





	

	


}
