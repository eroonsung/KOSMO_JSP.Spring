package com.naver.erp;

import java.util.List;
import java.util.Map;

public interface CusDAO {

	int getCusListAllCnt(CusSearchDTO cusSearchDTO);

	List<Map<String, String>> getCusList(CusSearchDTO cusSearchDTO);

	List<EmpDTO> getEmpList();

	int insertCus(CusDTO cusDTO);

	List<String> getAgeDecadeList();

	CusDTO getCus(int cus_no);

	int getCusCnt(CusDTO cusDTO);

	int updateCus(CusDTO cusDTO);

	int deleteCus(CusDTO cusDTO);




}
