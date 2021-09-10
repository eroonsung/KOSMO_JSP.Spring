<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="/resources/common.css" rel="stylesheet" type="text/css" >
<script src="/resources/jquery-1.11.0.min.js"></script>
<script>
	$(document).ready(function(){
		$("[name=loginForm] .login").click(function(){
			checkLoginForm();
		})
	});

	function checkLoginForm(){
		var login_id = $(".login_id").val()
		if(login_id.split(" ").join("")==""){
			alert("아이디가 비어 있음. 입력 바람");
			$(".login_id").val("");
			$(".login_id").focus();
			return;	
		}

		var pwd = $(".pwd").val()
		if(pwd.split(" ").join("")==""){
			alert("아이디가 비어 있음. 입력 바람");
			$(".pwd").val("");
			$(".pwd").focus();	
			return;	
		}

		$.ajax({
			url: "/loginProc3.do"
			, type: "post"
			, data: $("[name=loginForm]").serialize()
			/*
			, success: function(responseHtml){
				var idCnt = $(responseHtml).filter(".idCnt").text();
				idCnt=$.trim(idCnt);
				idCnt=parseInt(idCnt,10);
				if(idCnt==1){
					alert("로그인 성공");
					location.replace("/boardList.do");
					}
				else{
					alert("로그인 실패");
					}
				}
			*/
			, success: function(login_idCnt){
				if(login_idCnt==1){
					alert("로그인 성공");
					location.replace("/boardList.do");
					}
				else{
					alert("로그인 실패");
					}
				}
			, error: function(){
				alert("서버 접속 실패");
				}
		})
	}
</script>
</head>
<body>
	<center>
		<form name="loginForm" method="post">
			<table border=1 cellpadding=5 class="tbcss2">
				<caption><b>[로그인]</b></caption>
				<tr>
					<th bgcolor="lightgray" align=center>아이디</th>
					<td>
						<input type="text" name="login_id" class="login_id" size="20" value="${cookie.login_id.value}">
					</td>
				</tr>
				<tr>
					<th bgcolor="lightgray" align=center>암호</th>
					<td>
						<input type="password" name="pwd" class="pwd" size="20" value="${cookie.pwd.value}">
					</td>
				</tr>
			</table>
			<table cellpadding=3>
				<tr align=center>
					<td align=center>
						<input type="button" value="로그인" class="login">
						<input type="checkbox" name="is_login" value="yes" ${empty cookie.login_id.value?'':'checked'}>아이디/암호기억
				</tr>
				<tr align=center>
					<td>
						<!-- <input type="checkbox" name="is_login" value="y"> 아이디,암호 기억 -->
						<span style="cursor:pointer" onClick="location.replace('/memRegForm.do')">[회원가입]</span>
				</tr>
			</table>
		</form>
	</center>
</body>
</html>