package com.naver.erp;

import java.util.HashMap;
import java.util.Map;

public class BoardPageDTO {

	public Map<Object, Object> getPageData(
			BoardSearchDTO boardSearchDTO, int boardListAllCnt, int pageNoCntPerPage		
	){
		System.out.println(" >>BoardPageDTO.getPageData 메소드 완료");
		Map<Object, Object> pageData = new HashMap<Object,Object>();
		
		int selectPageNo = boardSearchDTO.getSelectPageNo();
		int rowCntPerPage = boardSearchDTO.getRowCntPerPage();
		
		int min_pageNo = 0;
		int max_pageNo = 0;
		int last_pageNo = 0;
		
		if(boardListAllCnt>0) {
			last_pageNo = boardListAllCnt/rowCntPerPage;
				if(boardListAllCnt%rowCntPerPage>0) {
					last_pageNo++;
				}
			if(selectPageNo>last_pageNo) {
				selectPageNo=1;
				boardSearchDTO.setSelectPageNo(selectPageNo);
			}
			min_pageNo = ((selectPageNo-1)/pageNoCntPerPage)*pageNoCntPerPage+1;
			max_pageNo = min_pageNo+pageNoCntPerPage-1;
				if(max_pageNo>last_pageNo) {
					max_pageNo=last_pageNo;
				}
		}
		
		int start_serial_no = (selectPageNo-1)*rowCntPerPage+1;
		
		pageData.put("selectPageNo", selectPageNo);
		pageData.put("rowCntPerPage", rowCntPerPage);
		pageData.put("min_pageNo", min_pageNo);
		pageData.put("max_pageNo", max_pageNo);
		pageData.put("last_pageNo", last_pageNo);
		pageData.put("pageNoCntPerPage", pageNoCntPerPage);
		pageData.put("start_serial_no", start_serial_no);
		
		System.out.println(" >>BoardPageDTO.getPageData 메소드 완료");
		return pageData;
	}
}
