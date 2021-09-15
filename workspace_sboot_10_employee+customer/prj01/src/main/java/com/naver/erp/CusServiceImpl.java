package com.naver.erp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CusServiceImpl implements CusService{

	@Autowired
	private CusDAO cusDAO;
	
	@Override
	public int insertCus(CusDTO cusDTO) {
		int cusRegCnt = this.cusDAO.insertCus(cusDTO);
		return cusRegCnt;
	}

	@Override
	public int updateCus(CusDTO cusDTO) {
		int cusCnt = this.cusDAO.getCusCnt(cusDTO);
		// 게시판이 존재하지 않으면 -1을 리턴
		if(cusCnt==0) {return -1;}
		
		int updateCnt = this.cusDAO.updateCus(cusDTO);
		return updateCnt;
	}

	@Override
	public int deleteCus(CusDTO cusDTO) {
		int insertCusDelCnt = this.cusDAO.insertCusDel(cusDTO);
		return insertCusDelCnt;
	}

	
}
