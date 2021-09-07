<!-- ********************************************************************************** -->
<!-- JSP 기술의 한 종류인 [Page Directive]를 이용하여 현 JSP 페이지 처리 방식 선언하기 -->
<!-- ********************************************************************************** -->
<!-- 현재 이 JSP 페이지 실행 후 생성되는 문서는 HTML 이고, 이 문서 안의 데이터는 UTF-8 방식으로 인코딩한다라고 설정함 -->
<!-- 현재 이 JSP 페이지는 UTF-8 방식으로 인코딩한다-->
<!-- UTF-8 인코딩 방식은 한글을 포함한 전 세계 모든 문자열을 부호화할 수 있는 방법이다.-->
<!-- 모든 JSP 페이지, 상단에 무조건 들어가는 코드 -->
<!-- UTF-8 인코딩방식을 가지고 있는 text중의 하나인 html 파일로 처리 -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@include file="common.jsp" %>

	<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	<html>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

	<head>
		<script src="/resources/jquery-1.11.0.min.js"></script>
		<script>

			// **********************************************************
			// body태그 안의 소스를 모두 실행한 후에 실행할 자스 코드 설정
			// **********************************************************
			$(document).ready(function () {
				//--------------------------------------------
				//name=loginForm을 가진 태그 안의 class=login 가진 태그에
				// click이벤트 발생 시 실행할 코드 설정하기
				//--------------------------------------------
				$("[name=loginForm] .login").click(function () {
					// 아이디와 암호의 유효성 체크하는 checkLoginForm()함수 호출하기
					checkLoginForm();
				})
				$(".login_id").val("abc");
				$(".pwd").val("123");
			});

			// **********************************************************
			// 로그인 정보의 유효성을 체크하고 비동기 방식으로 서버와 통신하여
			// 로그인 정보와 암호의 존재 여부에 따른 자스 코드 실행하기
			// **********************************************************
			function checkLoginForm() {
				//alert("졸려");
				//입력된 [아이디]를 가져와 변수에 저장
				var login_id = $(".login_id").val();
				//아이디를 입력하지 않았거나 공백으로 이루어져 있으며
				//아이디 입력란을 비우고 경고하고 함수 중단
				if (login_id.split(" ").join("") == "") {
					alert("아이디가 비어 있음. 입력 바람");
					$(".login_id").val("");
					$(".login_id").focus();
					return;
				}

				//입력된 [암호]를 가져와 변수에 저장
				var pwd = $(".pwd").val();
				//암호를 입력하지 않았거나 공백으로 이루어져 있으며
				//암호 입력란을 비우고 경고하고 함수 중단
				if (pwd.split(" ").join("") == "") {
					alert("암호가 비어 있음. 입력 바람");
					$(".pwd").val("");
					$(".pwd").focus();
					return;
				}
				
				//--------------------------------------------
				// 현재 화면에서 페이지 이동 없이(=비동기 방식으로)
				// 서버쪽 loginProc.do로 접속하여 아이디, 암호의 존재 개수를 얻기
				//--------------------------------------------
				//alert($("[name=loginForm]").serialize())
				//return;
				$.ajax(
					{
						// 서버쪽 호출 URL 주소 지정
						url: "/loginProc.do"
						// form 태그 안의 입력양식 데이터 즉, 파라미터값을 보내는 방법 지정 
						, type: "post"
						// 서버로 보낼 파라미터명과 파라미터값을 설정
						// 즉, 입력한 아이디와 암호 지정
						, data: $("[name=loginForm]").serialize() //"id=" +id "&pwd=" +pwd
						// 위 설정은 아래처럼도 가능
						//{'id':id,'pwd':pwd} //내가 보내고 싶은 것만 보낼때
						//--------------------------------------------
						// 서버의 응답을 성공적으로 받았을 경우 실행할 익명함수 설정
						// 익명함수의 매개변수에는 서버가 보내온 html 소스가 문자열로 들어온다
						// 즉 응답 메시지 안의 html 소스가 문자열로써 익명함수의 매개변수로 들어온다.
						// 응답 메시지 안의 html 소스는 loginProc.jsp의 실행 결과물이다.
						//--------------------------------------------
						, success: function (responseHtml) {
							//--------------------------------------------	
							//매개변수로 들어온 html 소스에서 class=idCnt를 가진 태그가 끌어안고 있는 숫자 꺼내기
							//즉 아이디, 암호의 존재 개수 꺼내기
							// 꺼낸 존재 개수의 앞 뒤 공백 제거하기
							//--------------------------------------------
								//alert(responseHtml);
								var idCnt = $(responseHtml).filter(".idCnt").text();
								idCnt = $.trim(idCnt);
								idCnt = parseInt(idCnt,10);
								//--------------------------------------------
								//만약 아이디, 암호의 존재 개수가 1이면, 즉 존재하면 
								//--------------------------------------------
								//if(idCnt=="1"){
								if(idCnt==1){
									alert("로그인 성공")		
									location.replace("/boardList.do")
								}
								//--------------------------------------------
								//그렇지 않으면, 즉 아이디 암호가 존재하지 않으면
								//--------------------------------------------
								else{
									alert("로그인 실패")
								}
						}
						//--------------------------------------------
						// 서버의 응답을 못 받았을 경우 실행할 익명함수 설증
						//--------------------------------------------
						, error: function () {
							alert("서버 접속 실패");
						}
					}
				);
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
		<!-- 테스트 용 -->
		<!--  
			<a href="localhost:8081/boardList.do">localhost:8081/boardList.do</a><br>
			<a href="localhost:8081/boardRegForm">localhost:8081/boardRegForm</a><br>
			<a href="localhost:8081/boardContentForm.do">localhost:8081/boardContentForm.do</a><br>
			<a href="localhost:8081/boardUpDelForm.do">localhost:8081/boardUpDelForm.do</a><br> 
		-->

	</body>

	</html>