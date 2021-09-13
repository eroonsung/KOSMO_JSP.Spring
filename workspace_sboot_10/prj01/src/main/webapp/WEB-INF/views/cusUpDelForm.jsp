<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@include file="common.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script>
	$(document).ready(function(){
	})
	
	function checkCusUpDelForm(upDel){
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
			url:"/cusUpDelProc.do"
			// form 태그 안의 입력양식 데이터 즉, 파라미터값을 보내는 방법 지정 
			, type: "post"
			// 웹서버로 보낼 파라미터명과 파라미터값을 설정
			, data: $("[name=cusUpDelForm]").serialize()
			//--------------------------------------------
			// 서버의 응답을 성공적으로 받았을 경우 실행할 익명함수 설정
			// 익명함수의 매개변수에는 서버가 보내온 html 소스가 문자열로 들어온다
			// 즉 응답 메시지 안의 html 소스가 문자열로써 익명함수의 매개변수로 들어온다.
			// 응답 메시지 안의 html 소스는 boardRegProc.jsp의 실행 결과물이다.
			//--------------------------------------------				
			, success: function (responseHtml) {
				var msg = $(responseHtml).filter(".msg").text();
				msg = $.trim(msg);
				
				var cusUpDelCnt = $(responseHtml).filter(".cusUpDelCnt").text();
				cusUpDelCnt = $.trim(cusUpDelCnt);
				cusUpDelCnt = parseInt(cusUpDelCnt,10);
				//====================================================
				if(upDel=="up"){
					if(msg!=""&& msg.length>0){
						alert(msg);
						return;
					}
					if(cusUpDelCnt == -1){
						alert("삭제된 고객입니다.");
						location.replace("/cusList.do");
					}else if(cusUpDelCnt == 1){
						if(confirm("수정 성공. 목록화면으로 이동하시겠습니까?")){
							location.replace("/cusList.do");
						}else{
							return;
						}
					}else{
						alert("서버 에러 발생! 관리자에게 문의 바람.");
					}
				}
				//====================================================
				else if(upDel=="del"){
					if(cusUpDelCnt == 1){
						alert("삭제성공.");
						location.replace("/cusList.do");
					}else if(cusUpDelCnt == -1){
						alert("삭제된 고객입니다.");
						location.replace("/cusList.do");
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
	<c:if test="${!empty cusDTO}">
		<form name="cusUpDelForm" method="post" action="/cusUpDelForm.do">
			<table border=1>
		 		<caption><b>고객 수정/삭제</b></caption>	
			
				<tr>
					<th bgcolor="lightgray">직원이름</th>
					<td><input type="text" name="cus_name" class="cus_name" size="10" maxlength=10 value="${cusDTO.cus_name}"></td>
				</tr>
				<tr>
					<th bgcolor="lightgray">전화번호</th>
					<td><input type="text" name="tel_num" class="tel_num" size=40 maxlength="30" value="${cusDTO.tel_num}"></td>
				</tr>
				<tr>
					<th bgcolor="lightgray">주민번호</th>
					<td><input type="text" name="jumin_num" class="jumin_num" size=20 maxlength="13" value="${cusDTO.jumin_num}"></td>
				</tr>
				
				<tr>
					<th bgcolor="lightgray">담당직원</th>
					<td>
						<select name="emp_no">
							<c:forEach var="list" items="${empList}" varStatus="loopTagStatus">
								<c:if test="${cusDTO.emp_no==list.emp_no}">
									<option value="${list.emp_no}" selected>${list.emp_no} - ${list.emp_name}(${list.jikup}) - ${list.dep_name}
								</c:if>
								<c:if test="${cusDTO.emp_no!=list.emp_no}">
									<option value="${list.emp_no}">${list.emp_no} - ${list.emp_name}(${list.jikup}) - ${list.dep_name}
								</c:if>
							</c:forEach>
						</select>
					</td>
				</tr>
			</table>
			<div style="height:6px"></div>
			<input type="button" value="수정" onClick="checkCusUpDelForm('up');">
			<input type="reset" value="삭제" onClick="checkCusUpDelForm('del');">
			<input type="button" value="목록보기" onclick="location.replace('/cusList.do');">
			
			<input type="hidden" name="cus_no" value="${cusDTO.cus_no}">
			<input type="hidden" name="upDel">
		</form>
	</c:if>
	<c:if test="${empty cusDTO}">
		<script>alert('삭제된 직원입니다.'); location.replace('/cusList.do')</script>
	</c:if>
	</center>
</body>
</html>