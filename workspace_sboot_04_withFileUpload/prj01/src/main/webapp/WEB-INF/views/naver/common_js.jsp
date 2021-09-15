<!-- ********************************************************************************** -->
<!-- JSP 기술의 한 종류인 [Page Directive]를 이용하여 현 JSP 페이지 처리 방식 선언하기 -->
<!-- ********************************************************************************** -->
<!-- 현재 이 JSP 페이지 실행 후 생성되는 문서는 HTML 이고, 이 문서 안의 데이터는 UTF-8 방식으로 인코딩한다라고 설정함 -->
<!-- 현재 이 JSP 페이지는 UTF-8 방식으로 인코딩한다-->
<!-- UTF-8 인코딩 방식은 한글을 포함한 전 세계 모든 문자열을 부호화할 수 있는 방법이다.-->
<!-- 모든 JSP 페이지, 상단에 무조건 들어가는 코드 -->
<!-- UTF-8 인코딩방식을 가지고 있는 text중의 하나인 html 파일로 처리 -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>


<script>
	$(document).ready(function(){
		$(".logout").prepend(
			"<center><div><span style='cursor:pointer' onclick='location.replace(\"/${naverPath}logout.do\");'>[로그아웃]</span></div></center>"	
		);
		
		$("body").attr("bgcolor", "${bodyBgColor}")
	});
	
	// **********************************************************
	// 테이블 태그 내부의 배경색을 바꾸는 함수
	// **********************************************************
	function changeBgColor(){
		setTrBgcolor(
				//--------------------------------------
				"tbcss0"		//테이블 태그의 클래스값
				//--------------------------------------
				,"gray"			//head tr의 배경색값
				//--------------------------------------
				,"#eef7ff"		//head tr 이후의 홀수 tr 배경색값
				//,"white"
				//--------------------------------------
				,"lightblue"	//head tr 이후의 짝수 tr 배경색값
				//--------------------------------------
				,"lightpink"	//마우스를 댔을 때 배경색값
		);
	}
</script>	



