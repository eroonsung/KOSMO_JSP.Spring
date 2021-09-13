<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@include file="common.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script>
	$(document).ready(function(){
	})
	
	function checkEmpUpDelForm(upDel){
		if(upDel=="up"){
			//프로그램 진행상황 확인을 위한 테스트용 태그 만들기
			//$(".xxx").remove();
			//$("body").append("<div class=xxx>수정</div>"); 
			if(confirm("정말 수정하시겠습니까?")==false){return};
			//name=upDel hidden 태그 안에 'up'넣기
			$("[name='upDel']").val("up");
		}
		//-------------------------------------------------------
		//매개변수로 들어온 upDel에 "del"이 저장되어있으면
		// 즉 삭제버튼을 눌렀으면 암호 확인하고 삭제 여부를 물어보기
		//-------------------------------------------------------
		else if(upDel=="del"){
			if(confirm("정말 삭제하시겠습니까?")==false){return};
			//name=upDel hidden 태그 안에 'del'넣기
			$("[name='upDel']").val("del");
		}
		
		//-------------------------------------------------------
		//현재 화면에서 페이지 이동 없이
		// 서버쪽 "/boardUpDelProc.do"로 접속해서 수정 또는 삭제하기
		//-------------------------------------------------------
		$.ajax({
			// 서버쪽 호출 URL 주소 지정
			url:"/empUpDelProc.do"
			// form 태그 안의 입력양식 데이터 즉, 파라미터값을 보내는 방법 지정 
			, type: "post"
			// 웹서버로 보낼 파라미터명과 파라미터값을 설정
			, data: $("[name=empUpDelForm]").serialize()
			//--------------------------------------------
			// 서버의 응답을 성공적으로 받았을 경우 실행할 익명함수 설정
			// 익명함수의 매개변수에는 서버가 보내온 html 소스가 문자열로 들어온다
			// 즉 응답 메시지 안의 html 소스가 문자열로써 익명함수의 매개변수로 들어온다.
			// 응답 메시지 안의 html 소스는 boardRegProc.jsp의 실행 결과물이다.
			//--------------------------------------------				
			, success: function (responseHtml) {
				var msg = $(responseHtml).filter(".msg").text();
				msg = $.trim(msg);
				
				var empUpDelCnt = $(responseHtml).filter(".empUpDelCnt").text();
				empUpDelCnt = $.trim(empUpDelCnt);
				empUpDelCnt = parseInt(empUpDelCnt,10);
				//====================================================
				if(upDel=="up"){
					if(msg!=""&& msg.length>0){
						alert(msg);
						return;
					}
					if(empUpDelCnt == -1){
						alert("삭제된 직원입니다.");
						location.replace("/empList.do");
					}else if(empUpDelCnt == 1){
						if(confirm("수정 성공. 목록화면으로 이동하시겠습니까?")){
							location.replace("/empList.do");
						}else{
							return;
						}
					}else{
						alert("서버 에러 발생! 관리자에게 문의 바람.");
					}
				}
				//====================================================
				else if(upDel=="del"){
					if(empUpDelCnt == 1){
						alert("삭제성공.");
						location.replace("/empList.do");
					}else if(empUpDelCnt == -1){
						alert("삭제된 직원입니다.");
						location.replace("/empList.do");
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
<body>
	<center>
	<c:if test="${!empty empDTO}">
		<form name="empUpDelForm" method="post" action="/empUpDelForm.do">
			<table border=1>
		 		<caption><b>직원 수정/삭제</b></caption>	
			
				<tr>
					<th bgcolor="lightgray">직원이름</th>
					<td><input type="text" name="emp_name" class="emp_name" size="10" maxlength=10 value="${empDTO.emp_name}"></td>
				</tr>
				<tr>
					<th bgcolor="lightgray">부서</th>
					<td>
						<select name="dep_no">
							<c:forEach var="list" items="${depNoAndNameList}" varStatus="loopTagStatus">
								<c:if test="${empDTO.dep_no==list.dep_no}">
									<option value="${list.dep_no}" selected>${list.dep_name}
								</c:if>
								<c:if test="${empDTO.dep_no!=list.dep_no}">
									<option value="${list.dep_no}">${list.dep_name}
								</c:if>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<th bgcolor="lightgray">직급</th>
					<td>
						<select name="jikup" >
							<c:forEach var="list" items="${jikupList}" varStatus="loopTagStatus">
								<c:if test="${empDTO.jikup==list.jikup}">
									<option value="${list.jikup}" selected>${list.jikup}
								</c:if>
								<c:if test="${empDTO.jikup!=list.jikup}">
									<option value="${list.jikup}">${list.jikup}
								</c:if>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<th bgcolor="lightgray">연봉</th>
					<td><input type="text" name="salary" class="salary" size=40 maxlength="30" value="${empDTO.salary}"></td>
				</tr>
				<tr>
					<th bgcolor="lightgray">주민번호</th>
					<td><input type="text" name="jumin_num" class="jumin_num" size=40 maxlength="30" value="${empDTO.jumin_num}"></td>
				</tr>
				<tr>
					<th bgcolor="lightgray">핸드폰번호</th>
					<td><input type="text" name="phone" class="phone" size=40 maxlength="30" value="${empDTO.phone}"></td>
				</tr>
				<tr>
					<th bgcolor="lightgray">직속상관번호</th>
						<td>
						<select name="mgr_emp_no" >
						<option value="">
							<c:forEach var="list" items="${MgrEmpList}" varStatus="loopTagStatus">
								<c:if test="${empDTO.mgr_emp_no==list.emp_no}">
									<option value="${list.emp_no}" selected>${list.emp_no} - ${list.emp_name} - ${list.dep_name}(${list.jikup})
								</c:if>
								<c:if test="${empDTO.mgr_emp_no!=list.emp_no}">
									<option value="${list.emp_no}">${list.emp_no} - ${list.emp_name}(${list.jikup}) - ${list.dep_name}
								</c:if>
							</c:forEach>
						</select>
					</td>
				</tr>
			</table>
			<div style="height:6px"></div>
			<input type="button" value="수정" onClick="checkEmpUpDelForm('up');">
			<input type="reset" value="삭제" onClick="checkEmpUpDelForm('del');">
			<input type="button" value="목록보기" onclick="location.replace('/empList.do');">
			
			<input type="hidden" name="emp_no" value="${empDTO.emp_no}">
			<input type="hidden" name="upDel">
		</form>
	</c:if>
	<c:if test="${empty empDTO}">
		<script>alert('삭제된 직원입니다.'); location.replace('/empList.do')</script>
	</c:if>
	</center>
</body>
</html>