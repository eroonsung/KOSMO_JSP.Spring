<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@include file="common.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script>
	$(document).ready(function(){
		$(".emp_name").val("가나다");
		$(".dep_no").val("40");
		$(".jikup").val("주임");
		$(".salary").val("1234");
		$(".phone").val("01010041004");
	
	})

	function checkEmpRegForm(){
		if(confirm("정말 등록하시겠습니까?")==false) {return;}

		document.empRegForm.enctype="multipart/form-data";
			
		$.ajax({
			url:"/empRegProc.do"
			// form 태그 안의 입력양식 데이터 즉, 파라미터값을 보내는 방법 지정 
			, type: "post"
			// 서버로 보낼 파라미터명과 파라미터값을 설정
			//, data: $("[name=empRegForm]").serialize()
			
			, processData:false
			, contentType: false
			, data: new FormData( $("[name=empRegForm]").get(0) )
			
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
				
				
				var empRegCnt = $(responseHtml).filter(".empRegCnt").text();
				empRegCnt = $.trim(empRegCnt);
				empRegCnt = parseInt(empRegCnt,10);
				
				if(empRegCnt==1){
					alert("직원등록 성공");
					location.replace("/empList.do");
				}
				//--------------------------------------------
				//그렇지 않으면, 즉 입력이 실패했으면
				//--------------------------------------------
				else{
					alert("직원등록 실패");
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
		<form name="empRegForm" method="post" action="/empRegForm.do">
			<table border=1>
		 		<caption><b>직원등록</b></caption>	
			
				<tr>
					<th bgcolor="lightgray">직원이름</th>
					<td><input type="text" name="emp_name" class="emp_name" size="10" maxlength=10></td>
				</tr>
				<tr>
					<th bgcolor="lightgray">직원사진</th>
					<td><input type="file" name="img" class="img"></td>
				</tr>
				<tr>
					<th bgcolor="lightgray">부서</th>
					<td>
						<select name="dep_no" >
							<c:forEach var="list" items="${depNoAndNameList}" varStatus="loopTagStatus">
								<option value="${list.dep_no}">${list.dep_name}
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<th bgcolor="lightgray">직급</th>
					<td>
						<select name="jikup" >
							<c:forEach var="list" items="${jikupList}" varStatus="loopTagStatus">
								<option value="${list.jikup}">${list.jikup}
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<th bgcolor="lightgray">연봉</th>
					<td><input type="text" name="salary" class="salary" size=40 maxlength="30"></td>
				</tr>
				<tr>
					<th bgcolor="lightgray">주민번호</th>
					<td><input type="text" name="jumin_num" class="jumin_num" size=20 maxlength="13"></td>
				</tr>
				<tr>
					<th bgcolor="lightgray">핸드폰번호</th>
					<td><input type="text" name="phone" class="phone" size=40 maxlength="30"></td>
				</tr>
				<tr>
					<th bgcolor="lightgray">직속상관</th>
					<td>
						<select name="mgr_emp_no" >
						<option value="0">
							<c:forEach var="list" items="${MgrEmpList}" varStatus="loopTagStatus">
								<option value="${list.emp_no}">${list.emp_no} - ${list.emp_name}(${list.jikup}) - ${list.dep_name}
							</c:forEach>
						</select>
					</td>
				</tr>
			</table>
			<div style="height:6px"></div>
			<input type="button" value="저장" onClick="checkEmpRegForm();">
			<input type="reset" value="다시작성">
			<input type="button" value="목록보기" onclick="location.replace('/empList.do')">
			
		</form>
	</center>
</body>
</html>