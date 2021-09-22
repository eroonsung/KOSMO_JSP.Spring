<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@include file="common.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script>
$(document).ready(function(){
	$(".staff_name").val("사과");
	$(".jumin_no1").val("960606");
	$(".jumin_no2").val("1111111");
	
	$(".staffSearchBtn").click(function(){
		location.replace('/staff_search_form.do');
		})
	$(".staffSaveBtn").click(function(){
		checkStaffRegForm();
		})
})

function checkStaffRegForm(){
	
	document.staffRegForm.enctype="multipart/form-data"; 
	
	if(confirm("정말 등록하시겠습니까?")==false) {return;}

	$.ajax({
		url:"/staff_input_proc.do"
			, type: "post"
			//, data: $("[name=staffRegForm]").serialize()
			, processData:false
			, contentType:false
			, data: new FormData( $("[name=staffRegForm]").get(0) ) 
		, success: function (json) {
			
			var msg = json.msg;
			if(msg!=null && msg.length>0){
				alert(msg);
				return;
			}
			
			var staffRegCnt = json.staffRegCnt;
			if(staffRegCnt==1){
				alert("직원등록 성공");
				location.replace("/staff_search_form.do");
			}
			else{
				alert("직원등록 실패");
			}
		}
		, error: function(){
			alert("서버 접속 실패");
		}
	});
	
};

</script>
</head>
<body onKeydown="if(event.keyCode==13){search();}">
<center>
	<form name=staffRegForm method="post" onSubmit="return false">
	<table border=1 cellpadding=5 class="tbcss2" >
		<tr><th colspan="6" bgcolor="${thBgColor}"><b>사원 정보 등록</b>
		<tr>
			<th bgcolor="${thBgColor}">이름
			<td><input type="text" name="staff_name" class="staff_name" >
			<th bgcolor="${thBgColor}">주민번호
			<td><input type="text" name="jumin_no1" class="jumin_no1" maxlength="6">-
				<input type="password" name = "jumin_no2" class="jumin_no2"maxlength="7">
			<th bgcolor="${thBgColor}">종교<td>
				<select name=religion_code>
				<option value="0">
				<c:forEach var="rel" items="${relgionList}">
					<option value="${rel.religion_code}">${rel.religion_name}
				</c:forEach>
				</select>
		<tr>
			<th bgcolor="${thBgColor}">학력<td>
			<c:forEach var="school" items="${schoolList}">
				<input type="radio" name="school_code" value="${school.school_code}">${school.school_name}
			</c:forEach>
			<th bgcolor="${thBgColor}">기술<td colspan="3">
			<c:forEach var="skill" items="${skillList}">
			<!-- 	<input type="checkbox" name="skill_code" value="${skill.skill_code}">${skill.skill_name}-->
				<input type="checkbox" name="skill_codeList" value="${skill.skill_code}">${skill.skill_name} 
			</c:forEach>
		<tr>
			<th bgcolor="${thBgColor}">졸업일
			<td colspan="5">
				<select name="graduate_day_ofYear" class="graduate_year">
						<option value="">
						<c:forEach var="i" begin="0" end="${2021-2000}" step="1">
						 <c:set var="yearOption" value="${2021-i}"/>
							<option value="${yearOption}">${yearOption}
						</c:forEach>
					</select>년
					
					<select name="graduate_day_ofMonth" class="graduate_month">
						<option value="">
						<option value="01">01
						<option value="02">02
						<option value="03">03
						<option value="04">04
						<option value="05">05
						<option value="06">06
						<option value="07">07
						<option value="08">08
						<option value="09">09
						<option value="10">10
						<option value="11">11
						<option value="12">12
					</select>월
					
					<select name="graduate_day_ofDate" class="graduate_day">
						<option value="">
						<c:forEach var="i" begin="1" end="31" step="1" varStatus = "status">
							<fmt:formatNumber var="no" minIntegerDigits="2" value="${status.count}" type="number"/>
							<option value="${no}">${no}
						</c:forEach>
					</select>일
					
		<tr>
			<th bgcolor="${thBgColor}">사진
			<td colspan="5"><input type="file" name="img" class="img">	
			
	</table>
			<div style="height:10px"></div>
		<input type="button" value="저장" class="staffSaveBtn">&nbsp;
		<input type="reset" value="초기화" >
		<input type="button" value="직원검색" class="staffSearchBtn">
		
</form>
	
</center>
</body>
</html>