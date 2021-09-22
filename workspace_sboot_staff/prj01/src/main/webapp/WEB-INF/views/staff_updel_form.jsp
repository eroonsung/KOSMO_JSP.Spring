<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@include file="common.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script>
$(document).ready(function(){
	$(".staffSearchBtn").click(function(){
		location.replace('/staff_search_form.do');
		})
	
})

	function checkStaffUpDelForm(upDel){
		if(upDel=="up"){
			if(confirm("정말 수정하시겠습니까?")==false){return};
			$("[name='upDel']").val("up");
		}
		//-------------------------------------------------------
		//매개변수로 들어온 upDel에 "del"이 저장되어있으면
		// 즉 삭제버튼을 눌렀으면 암호 확인하고 삭제 여부를 물어보기
		//-------------------------------------------------------
		else if(upDel=="del"){
			if(confirm("정말 삭제하시겠습니까?")==false){return};
			$("[name='upDel']").val("del");
		}

		document.staffUpDelForm.enctype="multipart/form-data"; 

		$.ajax({
			// 서버쪽 호출 URL 주소 지정
			url:"/staff_updel_proc.do"
			// form 태그 안의 입력양식 데이터 즉, 파라미터값을 보내는 방법 지정 
			, type: "post"
			// 웹서버로 보낼 파라미터명과 파라미터값을 설정
			//, data: $("[name=empUpDelForm]").serialize()
			
			//, data: $("[name=staffUpDelForm]").serialize()
			, processData:false
			, contentType:false
			, data: new FormData( $("[name=staffUpDelForm]").get(0) ) 
			//--------------------------------------------
			// 서버의 응답을 성공적으로 받았을 경우 실행할 익명함수 설정
			// 익명함수의 매개변수에는 서버가 보내온 html 소스가 문자열로 들어온다
			// 즉 응답 메시지 안의 html 소스가 문자열로써 익명함수의 매개변수로 들어온다.
			// 응답 메시지 안의 html 소스는 boardRegProc.jsp의 실행 결과물이다.
			//--------------------------------------------				
			, success: function (json) {
				var msg =json.msg;
				msg = $.trim(msg);
				
				var staffUpDelCnt = json.staffUpDelCnt;
				staffUpDelCnt = $.trim(staffUpDelCnt);
				staffUpDelCnt = parseInt(staffUpDelCnt,10);
				//====================================================
				if(upDel=="up"){
					if(msg!=""&& msg.length>0){
						alert(msg);
						return;
					}
					if(staffUpDelCnt == -1){
						alert("삭제된 직원입니다.");
						location.replace("/staff_search_form.do");
					}else if(staffUpDelCnt == 1){
						if(confirm("수정 성공. 목록화면으로 이동하시겠습니까?")){
							location.replace("/staff_search_form.do");
						}else{
							document.staffUpDelForm.submit();
						}
					}else{
						alert("서버 에러 발생! 관리자에게 문의 바람.");
					}
				}
				//====================================================
				else if(upDel=="del"){
					if(staffUpDelCnt == 1){
						alert("삭제성공.");
						location.replace("/staff_search_form.do");
					}else if(staffUpDelCnt == -1){
						alert("삭제된 직원입니다.");
						location.replace("/staff_search_form.do");
					}else{
						alert("서버 에러 발생! 관리자에게 문의 바람.");
					}
					
				}
			}
			//--------------------------------------------
			// 서버의 응답을 못 받았을 경우 실행할 익명함수 설정
			//--------------------------------------------		
			, error: function(){
				alert("서버 접속 실패");
			}
		})
	}


</script>
</head>
<body onKeydown="if(event.keyCode==13){search();}">
<center>
<c:if test="${!empty staffDTO}">
	<form name=staffUpDelForm method="post" onSubmit="return false">
	<table border=1 cellpadding=5 class="tbcss2" >
		<tr><th colspan="6" bgcolor="${thBgColor}"><b>사원 정보 수정</b>
		<tr>
			<th bgcolor="${thBgColor}">이름
			<td><input type="text" name="staff_name" class="staff_name" value="${staffDTO.staff_name}">
			<th bgcolor="${thBgColor}">주민번호
			<td><input type="text" name="jumin_no1" class="jumin_no1" value="${staffDTO.jumin_no1}" maxlength="6">-
				<input type="password" name = "jumin_no2" class="jumin_no2" value="${staffDTO.jumin_no2}" maxlength="7">
			<th bgcolor="${thBgColor}">종교<td>
				<select name=religion_code>
				<c:forEach var="rel" items="${relgionList}">
				<c:if test="${staffDTO.religion_code==rel.religion_code}">
					<option value="${rel.religion_code}" selected>${rel.religion_name}</option>
				</c:if>
				<c:if test="${staffDTO.religion_code!=rel.religion_code}">
					<option value="${rel.religion_code}">${rel.religion_name}</option>
				</c:if>
				</c:forEach>
				</select>
		<tr>
			<th bgcolor="${thBgColor}">학력<td>
			<c:forEach var="school" items="${schoolList}">
				<c:if test="${staffDTO.school_code==school.school_code}">
					<input type="radio" name="school_code" value="${school.school_code}" checked>${school.school_name}
				</c:if>
				<c:if test="${staffDTO.school_code!=school.school_code}">
					<input type="radio" name="school_code" value="${school.school_code}">${school.school_name}
				</c:if>
			</c:forEach>
			<th bgcolor="${thBgColor}">기술<td colspan="3">
			<c:forEach var="skill" items="${skillList}" >
			<c:forEach var="staffSkill" items="${staffSkillList}" varStatus="status">
					<c:if test="${skill.skill_code==staffSkill.skill_code }">
						<input type="checkbox" name="skill_codeList" value="${skill.skill_code}" checked>${skill.skill_name}
					</c:if>
				</c:forEach>
				<c:forEach var="xxxSkill" items="${xxxSkillList}" varStatus="status">
					<c:if test="${skill.skill_code==xxxSkill.skill_code }">
						<input type="checkbox" name="skill_codeList" value="${skill.skill_code}" >${skill.skill_name}
					</c:if>
				</c:forEach>
			</c:forEach>
			
			
		<tr>
			<th bgcolor="${thBgColor}">졸업일
			<td colspan="5">
				<select name="graduate_day_ofYear" class="graduate_day_ofYear">
						<option value="">
						<c:forEach var="i" begin="0" end="${2021-2000}" step="1">
						 <c:set var="yearOption" value="${2021-i}"/>
							<c:if test="${staffDTO.graduate_day_ofYear==yearOption}">
								<option value="${yearOption}" selected>${yearOption}
							</c:if>
							<c:if test="${staffDTO.graduate_day_ofYear!=school.yearOption}">
								<option value="${yearOption}">${yearOption}
							</c:if>
						</c:forEach>
					</select>년
					
					<select name="graduate_day_ofMonth" class="graduate_day_ofMonth">
						<option value="">
						<option value="01" ${staffDTO.graduate_day_ofMonth=='01'?'selected':''}>01
						<option value="02" ${staffDTO.graduate_day_ofMonth=='02'?'selected':''}>02
						<option value="03" ${staffDTO.graduate_day_ofMonth=='03'?'selected':''}>03
						<option value="04" ${staffDTO.graduate_day_ofMonth=='04'?'selected':''}>04
						<option value="05" ${staffDTO.graduate_day_ofMonth=='05'?'selected':''}>05
						<option value="06" ${staffDTO.graduate_day_ofMonth=='06'?'selected':''}>06
						<option value="07" ${staffDTO.graduate_day_ofMonth=='07'?'selected':''}>07
						<option value="08" ${staffDTO.graduate_day_ofMonth=='08'?'selected':''}>08
						<option value="09" ${staffDTO.graduate_day_ofMonth=='09'?'selected':''}>09
						<option value="10" ${staffDTO.graduate_day_ofMonth=='10'?'selected':''}>10
						<option value="11" ${staffDTO.graduate_day_ofMonth=='11'?'selected':''}>11
						<option value="12" ${staffDTO.graduate_day_ofMonth=='12'?'selected':''}>12
					</select>월
					
					<select name="graduate_day_ofDate" class="graduate_day_ofDate">
						<c:forEach var="i" begin="1" end="31" step="1" varStatus = "status">
							<fmt:formatNumber var="no" minIntegerDigits="2" value="${status.count}" type="number"/>
							<option value="${no}" ${staffDTO.graduate_day_ofDate==no?'selected':''}>${no}
						</c:forEach>
					</select>일
					
		<tr>
			<th bgcolor="${thBgColor}">사진
			<td colspan="5"><input type="file" name="img" class="img">	
							<c:if test="${!empty staffDTO.pic}">
							<div style="height:3pt"></div>
							<img src="resources/img/${staffDTO.pic}" width="30%">
							<input type="checkbox" name="is_del" class="is_del" value="yes">삭제
						</c:if>	
		
	</table>
			<div style="height:10px"></div>
			<input type="button" value="수정" onClick="checkStaffUpDelForm('up');">
			<input type="reset" value="삭제" onClick="checkStaffUpDelForm('del');">
		<input type="button" value="직원검색" class="staffSearchBtn">
		
		<input type="hidden" name="staff_no" value="${staffDTO.staff_no}">
		<input type="hidden" name="upDel">
		
</form>
</c:if>
	<c:if test="${empty staffDTO}">
		<script>alert('삭제된 직원입니다.'); location.replace('/staff_search_form.do')</script>
	</c:if>
</center>
</body>
</html>