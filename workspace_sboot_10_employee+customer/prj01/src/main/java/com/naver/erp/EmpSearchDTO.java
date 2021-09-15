package com.naver.erp;

import java.util.List;

public class EmpSearchDTO {
	private String keyword1;
	private List<String> jikup;
	private List<String> dep_name;
	private String min_year;
	private String min_month;
	private String max_year;
	private String max_month;
	
	private String sortHeadName;
	private String sortAscDesc;
	
	private int selectPageNo=1;
	private int rowCntPerPage=10;
	
	//===============================================
	public String getKeyword1() {
		return keyword1;
	}
	public void setKeyword1(String keyword1) {
		this.keyword1 = keyword1;
	}
	public List<String> getJikup() {
		return jikup;
	}
	public void setJikup(List<String> jikup) {
		this.jikup = jikup;
	}
	public List<String> getDep_name() {
		return dep_name;
	}
	public void setDep_name(List<String> dep_name) {
		this.dep_name = dep_name;
	}
	public String getMin_year() {
		return min_year;
	}
	public void setMin_year(String min_year) {
		this.min_year = min_year;
	}
	public String getMin_month() {
		return min_month;
	}
	public void setMin_month(String min_month) {
		this.min_month = min_month;
	}
	public String getMax_year() {
		return max_year;
	}
	public void setMax_year(String max_year) {
		this.max_year = max_year;
	}
	public String getMax_month() {
		return max_month;
	}
	public void setMax_month(String max_month) {
		this.max_month = max_month;
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
