<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page import = "java.util.Map" %>
<%@ page import = "java.util.List" %>
<%@ page import = "com.naver.erp.StaffDTO" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="thBgColor" value="#E0EBFF" scope="request"/>

<script src="/resources/jquery-1.11.0.min.js"></script>
<link href="/resources/common.css" rel="stylesheet" type="text/css" >

<script>

function headerSort(tableName, thClass){
	var thObjs = $("."+tableName+" tr:eq(0)").find("."+thClass);
	thObjs.css("cursor", "pointer");
	thObjs.click(function(){
		var thisThObj = $(this);
		var txt = thisThObj.text();
		
		if(txt.indexOf("▲")>=0){
			$(".sortHeadName").val("");
			$(".sortAscDesc").val("");
		}else if(txt.indexOf("▼")>=0){
			$(".sortHeadName").val(thisThObj.attr("class"));
			$(".sortAscDesc").val("asc");
		}else{
			$(".sortHeadName").val(thisThObj.attr("class"));
			$(".sortAscDesc").val("desc");
		}
		searchExe();
	})
	
}

function headerSortGt(tableName, gtTrIdx){
	var thObjs = $("."+tableName+" tr:eq(0)").find("th:gt("+gtTrIdx+")");
	thObjs.css("cursor", "pointer");
	thObjs.click(function(){
		var thisThObj = $(this);
		var txt = thisThObj.text();
		
		if(txt.indexOf("▲")>=0){
			$(".sortHeadName").val("");
			$(".sortAscDesc").val("");
		}else if(txt.indexOf("▼")>=0){
			$(".sortHeadName").val(thisThObj.attr("class"));
			$(".sortAscDesc").val("asc");
		}else{
			$(".sortHeadName").val(thisThObj.attr("class"));
			$(".sortAscDesc").val("desc");
		}
		searchExe();
	})
}

function headerSortAjax(){
	var sortHeadName = $(".sortHeadName").val();
	var sortAscDesc = $(".sortAscDesc").val();
	$("."+sortHeadName).siblings().each(function(){
		var txt = $(this).text();
		txt=$.trim(txt);
		txt = txt.replace("▲","");
		txt = txt.replace("▼","");
		$(this).text(txt);
	})
	if(sortAscDesc=="asc"){
		$("."+sortHeadName).append("▲")
	}else if(sortAscDesc=="desc"){
		$("."+sortHeadName).append("▼")
	}
	
}

</script>