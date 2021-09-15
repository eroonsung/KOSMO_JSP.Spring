package com.naver.erp;

import java.util.List;
import java.util.Map;

public interface EmpDAO {

	int getEmpListAllCnt(EmpSearchDTO empSearchDTO);

	List<Map<String, String>> getEmpList(EmpSearchDTO empSearchDTO);

	EmpDTO getEmp(int emp_no);

	int updateEmp(EmpDTO empDTO);

	int deleteEmp(EmpDTO empDTO);

	int getEmpCnt(EmpDTO empDTO);

	int insertEmp(EmpDTO empDTO);

	List<String> getJikupList();

	List<String> getDepNameList();

	int getMinY();

	int getMaxY();

	int updateMgrEmpNo(EmpDTO empDTO);

	int updateCusEmpNo(EmpDTO empDTO);

	List<Map<String, String>> getDepNoAndNameList();

	List<EmpDTO> getMgrEmpList();

	String getPic(EmpDTO empDTO);


}
