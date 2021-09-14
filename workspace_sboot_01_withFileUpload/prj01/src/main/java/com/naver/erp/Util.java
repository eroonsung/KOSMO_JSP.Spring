package com.naver.erp;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class Util {
	//----------------------------------------------------------------------------
	public static void addCookie(
		String cookieName
		, String cookieVal
		, int cookieAge
		, HttpServletResponse response
	) {
		Cookie cookie = new Cookie(cookieName, cookieVal);
		cookie.setMaxAge(cookieAge);
		response.addCookie(cookie);
	}
	
	//----------------------------------------------------------------------------
	public static boolean isNull(String str) {
		return str==null||str.length()==0;
	}
	
	//----------------------------------------------------------------------------
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

		Map<String, Integer> map = new HashMap<String,Integer>();
		
		map.put("selectPageNo", selectPageNo);
		map.put("rowCntPerPage", rowCntPerPage);
		map.put("min_pageNo", min_pageNo);
		map.put("max_pageNo", max_pageNo);
		map.put("last_pageNo", last_pageNo);
		map.put("pageNoCntPerPage", pageNoCntPerPage);
		
		return map;
	}
}
