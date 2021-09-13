package com.naver.erp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EmpServiceImpl implements EmpService{
	@Autowired
	private EmpDAO empDAO;

	public int updateEmp(EmpDTO empDTO) {
		int empCnt = this.empDAO.getEmpCnt(empDTO);
		// 게시판이 존재하지 않으면 -1을 리턴
		if(empCnt==0) {return -1;}
		
		int updateCnt = this.empDAO.updateEmp(empDTO);
		return updateCnt;
	}
	
	public int deleteEmp(EmpDTO empDTO) {
		int empCnt = this.empDAO.getEmpCnt(empDTO);
		if(empCnt==0) {return -1;}
		
		int updateMgrEmpNoCnt = this.empDAO.updateMgrEmpNo(empDTO);
		int updateCusEmpNoCnt = this.empDAO.updateCusEmpNo(empDTO);
		int deleteCnt = this.empDAO.deleteEmp(empDTO);
		return deleteCnt;
	}

	public int insertEmp(EmpDTO empDTO) {
		int empRegCnt = this.empDAO.insertEmp(empDTO);
		
		return empRegCnt;
	}
	

	
}
