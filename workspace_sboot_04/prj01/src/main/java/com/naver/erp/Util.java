package com.naver.erp;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;


//MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
//유틸 관련 메소드(자주 사용되는 기능)를 가진 클래스 선언
//MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
public class Util {
	
	//****************************************************
	//응답메시지에 쿠키명-쿠키값을 저장하는 메소드
	//****************************************************
	public static void addCookie(
			String cookieName
			, String cookieVal
			, int cookieAge
			, HttpServletResponse response
	) {
		//Cookie 객체를 생성하고 쿠키명-쿠키값을 저장하기
		Cookie cookie = new Cookie(cookieName, cookieVal);
		
		//Cookie 객체에 저장된 쿠키의 수명을 주기
		cookie.setMaxAge(cookieAge);
		
		//Cookie객체가 소유한 쿠키를 응답메시지에 저장하기
		// 결국 Cookie 객체가 소유한 쿠키명-쿠키값이 응답메시지에 저장되는 것
		// 응답메시지에 저장된 쿠키는 클라이언트 쪽으로 전송되어 클라이언트쪽에 저장된다.
		response.addCookie(cookie);
	}
	
	
	//****************************************************
	//String형 변수 안에 null값 또는 ""가 있으면 true를 리턴하는 메소드
	//****************************************************
	public static boolean isNull(String str) {
		//null이면 true가 리턴됨
		return str==null||str.length()==0;
		//return str==null||str.equals("");
	}
	
	//****************************************************
	//페이징 처리 관련 데이터를 구하고 Map<String,Integer> 객체에 저장해서
	// Map<String,Integer> 객체를 리턴하는 메소드 선언
	//****************************************************
	public static Map<String,Integer> getPagingNos(
			int totCnt
			, int pageNoCntPerPage
			, int selectPageNo
			, int rowCntPerPage
	){
		
	
		int min_pageNo = 0;
		int max_pageNo = 0;
		int last_pageNo = 0;
		
		if(totCnt>0) {
			last_pageNo = totCnt/rowCntPerPage;
				if(totCnt%rowCntPerPage>0) {
					last_pageNo++;
				}
				if(selectPageNo>last_pageNo) {
					selectPageNo = 1;
					
				}
			min_pageNo = ((selectPageNo-1)/pageNoCntPerPage)*pageNoCntPerPage+1;
			max_pageNo = min_pageNo+pageNoCntPerPage-1;
				if(max_pageNo>last_pageNo) {
					max_pageNo = last_pageNo;
				}
		}
		int start_serial_no = (selectPageNo-1)*rowCntPerPage+1;
		
		Map<String, Integer> map = new HashMap<String,Integer>();
		
		map.put("selectPageNo", selectPageNo);
		map.put("rowCntPerPage", rowCntPerPage);
		map.put("min_pageNo", min_pageNo);
		map.put("max_pageNo", max_pageNo);
		map.put("last_pageNo", last_pageNo);
		map.put("pageNoCntPerPage", pageNoCntPerPage);
		map.put("start_serial_no", start_serial_no);
		return map;
	}
}

