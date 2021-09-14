<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page import="com.naver.erp.Info"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script src="/resources/common.js"></script>

<script src="/resources/jquery-1.11.0.min.js"></script>

<link href="/resources/common.css" rel="stylesheet" type="text/css" >

<c:set var="naverPath" value="<%=Info.naverPath %>" scope="request"/>

<script>
	$(document).ready(function(){
		$(".logout").prepend(
			"<center><div><span style='cursor:pointer' onclick='location.replace(\"/${naverPath}logout.do\");'>[로그아웃]</span></div></center>"	
		);
	});
	
	function changeBgColor(){
		setTrBgcolor(
				//--------------------------------------
				"tbcss0"		//테이블 태그의 클래스값
				//--------------------------------------
				,"#787878"			//head tr의 배경색값
				//--------------------------------------
				,"white"		//head tr 이후의 홀수 tr 배경색값
				//--------------------------------------
				,"#DCDCDC"	//head tr 이후의 짝수 tr 배경색값
				//--------------------------------------
				,"lightblue"	//마우스를 댔을 때 배경색값
		);
	}
</script>	