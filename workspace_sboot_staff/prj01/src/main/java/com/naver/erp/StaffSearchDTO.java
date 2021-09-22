package com.naver.erp;

import java.util.List;

public class StaffSearchDTO {
	private String name;
	private List<String> gender;
	private int religion;
	private List<Integer> school;
	private List<Integer> skill;
	private String min_year;
	private String min_month;
	private String max_year;
	private String max_month;
	
	private String sortHeadName;
	private String sortAscDesc;
	
	private int selectPageNo=1;
	private int rowCntPerPage=10;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getGender() {
		return gender;
	}
	public void setGender(List<String> gender) {
		this.gender = gender;
	}
	public int getReligion() {
		return religion;
	}
	public void setReligion(int religion) {
		this.religion = religion;
	}
	public List<Integer> getSchool() {
		return school;
	}
	public void setSchool(List<Integer> school) {
		this.school = school;
	}
	public List<Integer> getSkill() {
		return skill;
	}
	public void setSkill(List<Integer> skill) {
		this.skill = skill;
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
