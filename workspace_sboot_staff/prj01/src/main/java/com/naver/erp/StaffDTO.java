package com.naver.erp;

import java.util.List;
import java.util.Map;

public class StaffDTO {
	private int staff_no;
	private String staff_name;
	private String jumin_no1;
	private String jumin_no2;
	private int school_code;
	private String school_name;
	private int religion_code;
	private String religion_name;
	private int staff_skill_no;
	private int skill_code;
	private List<String> skill_codeList;
	private String skill_name;
	private String graduate_day_ofYear;
	private String graduate_day_ofMonth;
	private String graduate_day_ofDate;
	
	private String pic;
	
	private String is_del;

	public int getStaff_no() {
		return staff_no;
	}

	public void setStaff_no(int staff_no) {
		this.staff_no = staff_no;
	}

	public String getStaff_name() {
		return staff_name;
	}

	public void setStaff_name(String staff_name) {
		this.staff_name = staff_name;
	}

	public String getJumin_no1() {
		return jumin_no1;
	}

	public void setJumin_no1(String jumin_no1) {
		this.jumin_no1 = jumin_no1;
	}

	public String getJumin_no2() {
		return jumin_no2;
	}

	public void setJumin_no2(String jumin_no2) {
		this.jumin_no2 = jumin_no2;
	}

	public int getSchool_code() {
		return school_code;
	}

	public void setSchool_code(int school_code) {
		this.school_code = school_code;
	}

	public String getSchool_name() {
		return school_name;
	}

	public void setSchool_name(String school_name) {
		this.school_name = school_name;
	}

	public int getReligion_code() {
		return religion_code;
	}

	public void setReligion_code(int religion_code) {
		this.religion_code = religion_code;
	}

	public String getReligion_name() {
		return religion_name;
	}

	public void setReligion_name(String religion_name) {
		this.religion_name = religion_name;
	}

	public int getStaff_skill_no() {
		return staff_skill_no;
	}

	public void setStaff_skill_no(int staff_skill_no) {
		this.staff_skill_no = staff_skill_no;
	}

	public int getSkill_code() {
		return skill_code;
	}

	public void setSkill_code(int skill_code) {
		this.skill_code = skill_code;
	}

	public List<String>  getSkill_codeList() {
		return skill_codeList;
	}

	public void setSkill_codeList(List<String>  skill_codeList) {
		this.skill_codeList = skill_codeList;
	}

	public String getSkill_name() {
		return skill_name;
	}

	public void setSkill_name(String skill_name) {
		this.skill_name = skill_name;
	}

	public String getGraduate_day_ofYear() {
		return graduate_day_ofYear;
	}

	public void setGraduate_day_ofYear(String graduate_day_ofYear) {
		this.graduate_day_ofYear = graduate_day_ofYear;
	}

	public String getGraduate_day_ofMonth() {
		return graduate_day_ofMonth;
	}

	public void setGraduate_day_ofMonth(String graduate_day_ofMonth) {
		this.graduate_day_ofMonth = graduate_day_ofMonth;
	}

	public String getGraduate_day_ofDate() {
		return graduate_day_ofDate;
	}

	public void setGraduate_day_ofDate(String graduate_day_ofDate) {
		this.graduate_day_ofDate = graduate_day_ofDate;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getIs_del() {
		return is_del;
	}

	public void setIs_del(String is_del) {
		this.is_del = is_del;
	}

	
}
