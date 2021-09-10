package com.naver.erp;

import java.util.List;

public class BoardSerachDTO {
	private String keyword1;
	private String keyword2;
	private String andOr;
	
	private int selectPageNo=1;
	private int rowCntPerPage=20;
	
	private List<String> dayList;
	
	private String sort;

	public String getKeyword1() {
		return keyword1;
	}

	public void setKeyword1(String keyword1) {
		this.keyword1 = keyword1;
	}

	public String getKeyword2() {
		return keyword2;
	}

	public void setKeyword2(String keyword2) {
		this.keyword2 = keyword2;
	}

	public String getAndOr() {
		return andOr;
	}

	public void setAndOr(String andOr) {
		this.andOr = andOr;
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

	public List<String> getDayList() {
		return dayList;
	}

	public void setDayList(List<String> dayList) {
		this.dayList = dayList;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}



}
