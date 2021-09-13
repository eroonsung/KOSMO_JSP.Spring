package com.naver.erp;

import java.util.List;

public class CusSearchDTO {
	private String keyword1;
	
	private List<String> ageDecade;
	
	private String sortHeadName;
	private String sortAscDesc;
	
	private int selectPageNo=1;
	private int rowCntPerPage=10;
	
	//==========================================
	public String getKeyword1() {
		return keyword1;
	}
	public void setKeyword1(String keyword1) {
		this.keyword1 = keyword1;
	}
	public List<String> getAgeDecade() {
		return ageDecade;
	}
	public void setAgeDecade(List<String> ageDecade) {
		this.ageDecade = ageDecade;
	}
	public String getSortHeadName() {
		return sortHeadName;
	}
	public void setSortHeadName(String sortHeadName) {
		this.sortHeadName = sortHeadName;
	}
	public String getSortAscDesc() {
		return sortAscDesc;
	}
	public void setSortAscDesc(String sortAscDesc) {
		this.sortAscDesc = sortAscDesc;
	}
	public int getSelectPageNo() {
		return selectPageNo;
	}
	public void setSelectPageNo(int selectPageNo) {
		this.selectPageNo = selectPageNo;
	}
	public int getRowCntPerPage() {
		return rowCntPerPage;
	}
	public void setRowCntPerPage(int rowCntPerPage) {
		this.rowCntPerPage = rowCntPerPage;
	}
	

	


}
