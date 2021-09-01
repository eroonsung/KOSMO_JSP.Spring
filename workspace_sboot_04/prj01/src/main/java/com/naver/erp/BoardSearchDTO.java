package com.naver.erp;

public class BoardSearchDTO {
	//---------------------------------------------------
	//[검색 키워드] 저장하는 속성변수 선언
	//현재 [선택된 페이지 번호]를 저장하는 속성변수 선언
	//한 화면에 보여줄 [행의 개수]를 저장하는 속성변수 선언
	//---------------------------------------------------
	private String keyword1;
	
	//에러 방지를 위해 default값을 먼저 주고 시작
	//location.reaplace로 처음 들어올때 아무런 파라미터도 가지고 오지 않기 때문에
	//처음에 아무것도 검색이 되지 않음 그렇기 때문에 default값을 넣어주어야함
	private int selectPageNo=1;
	private int rowCntPerPage=10;

	//---------------------------------------------------
	//getter, setter 메소드 선언
	//---------------------------------------------------
	public String getKeyword1() {
		return keyword1;
	}
	public void setKeyword1(String keyword1) {
		this.keyword1 = keyword1;
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
