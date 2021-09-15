<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@include file="common.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script>
	$(document).ready(function(){
		$(".cus_name").val("가나다");
		$(".tel_num").val("1234567");
	})

	function checkCusRegForm(){
		if(confirm("정말 등록하시겠습니까?")==false) {return;}
		$.ajax({
			url:"/cusRegProc.do"
			// form 태그 안의 입력양식 데이터 즉, 파라미터값을 보내는 방법 지정 
			, type: "post"
			// 서버로 보낼 파라미터명과 파라미터값을 설정
			, data: $("[name=cusRegForm]").serialize()
			//--------------------------------------------
			// 서버의 응답을 성공적으로 받았을 경우 실행할 익명함수 설정
			// 익명함수의 매개변수에는 서버가 보내온 html 소스가 문자열로 들어온다
			// 즉 응답 메시지 안의 html 소스가 문자열로써 익명함수의 매개변수로 들어온다.
			// 응답 메시지 안의 html 소스는 boardRegProc.jsp의 실행 결과물이다.
			//--------------------------------------------				
			, success: function (responseHtml) {
				//--------------------------------------------	
				//매개변수로 들어온 html 소스에서 class=msg를 가진 태그가 끌어안고 있는 문자 꺼내기
				//즉 에러 메시지 꺼내기 
				// 꺼낸 존재 개수의 앞 뒤 공백 제거하기
				//--------------------------------------------
				var msg = $(responseHtml).filter(".msg").text();
				msg = $.trim(msg);
				if(msg!=null && msg.length>0){
					alert(msg);
					return;
				}
				
				
				var cusRegCnt = $(responseHtml).filter(".cusRegCnt").text();
				cusRegCnt = $.trim(cusRegCnt);
				cusRegCnt = parseInt(cusRegCnt,10);
				
				if(cusRegCnt==1){
					alert("고객등록 성공");
					location.replace("/cusList.do");
				}
				//--------------------------------------------
				//그렇지 않으면, 즉 입력이 실패했으면
				//--------------------------------------------
				else{
					alert("고객등록 실패");
				}
			}
			//--------------------------------------------
			// 서버의 응답을 못 받았을 경우 실행할 익명함수 설정
			//--------------------------------------------		
			, error: function(){
				alert("서버 접속 실패");
			}
		});
	}
	
</script>
</head>
<body>
	<center>
		<form name="cusRegForm" method="post" action="/cusRegForm.do">
			<table border=1>
		 		<caption><b>고객등록</b></caption>	
			
				<tr>
					<th bgcolor="lightgray">고객이름</th>
					<td><input type="text" name="cus_name" class="cus_name" size="10" maxlength=10></td>
				</tr>
				<tr>
					<th bgcolor="lightgray">전화번호</th>
					<td><input type="text" name="tel_num" class="tel_num" size=40 maxlength="30"></td>
				</tr>
				<tr>
					<th bgcolor="lightgray">주민번호</th>
					<td><input type="text" name="jumin_num" class="jumin_num" size=20 maxlength="13"></td>
				</tr>
				
				<tr>
					<th bgcolor="lightgray">담당직원</th>
					<td>
						<select name="emp_no" >
						<option value="0">
							<c:forEach var="list" items="${empList}" varStatus="loopTagStatus">
								<option value="${list.emp_no}">${list.emp_no} - ${list.emp_name}(${list.jikup}) - ${list.dep_name}
							</c:forEach>
						</select>
					</td>
				</tr>
			</table>
			<div style="height:6px"></div>
			<input type="button" value="저장" onClick="checkCusRegForm();">
			<input type="reset" value="다시작성">
			<input type="button" value="목록보기" onclick="location.replace('/cusList.do')">
			
		</form>
	</center>
</body>
</html>