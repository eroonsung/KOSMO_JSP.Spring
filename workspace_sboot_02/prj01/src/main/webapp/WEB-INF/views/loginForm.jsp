<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="/resources/jquery-1.11.0.min.js"></script>
<script>
	$(document).ready(function(){
		$(".login").click(function(){
			checkLoginForm();
		})
	})

	function checkLoginForm(){
		var login_id =$(".login_id").val();
		if(login_id.split(" ").join("")==""){
			alert("아이디가 비어있음. 입력바람");
			$(".login_id").val("");
			$(".login_id").focus();
			return;
		}
		var pwd =$(".pwd").val();
		if(pwd.split(" ").join("")==""){
			alert("암호가 비어있음. 입력바람");
			$(".pwd").val("");
			$(".pwd").focus();
			return;
		}
		//--------------------------------------------
		// 현재 화면에서 페이지 이동 없이(=비동기 방식으로)
		// 서버쪽 loginProc.do로 접속하여 아이디, 암호의 존재 개수를 얻기
		//--------------------------------------------
		$.ajax({
			url: "loginProc.do"
			, type: "post"
			, data: $("[name=loginForm]").serialize()
			, success: function (responseHtml) {
				var idCnt = $(responseHtml).filter(".login_idCnt").text();
				idCnt = $.trim(idCnt);
				idCnt = parseInt(idCnt,10);

				if(idCnt==1){
					alert("로그인 성공")		
					location.replace("/boardList.do")
				}
				else{
					alert("로그인 실패")
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
		<!-- *************************************************** -->
		<!-- [로그인 정보 입력양식]을 내포한 form 태그 선언-->
		<!-- *************************************************** -->
		<form name="loginForm" method="post">
			<table border=1 cellpadding=5>
				<caption><b>[로그인]</b></caption>
				<tr>
					<th bgcolor="lightgray" align=center>아이디</th>
					<td>
						<input type="text" name="login_id" class="login_id" size="20">
					</td>
				</tr>
				<tr>
					<th bgcolor="lightgray" align=center>암호</th>
					<td>
						<input type="password" name="pwd" class="pwd" size="20">
					</td>
				</tr>
			</table>
			<table cellpadding=3>
				<tr align=center>
					<td align=center><input type="button" value="로그인" class="login">
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