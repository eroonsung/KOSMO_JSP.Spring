<!-- ********************************************************************************** -->
<!-- JSP 기술의 한 종류인 [Page Directive]를 이용하여 현 JSP 페이지 처리 방식 선언하기 -->
<!-- ********************************************************************************** -->
<!-- 현재 이 JSP 페이지 실행 후 생성되는 문서는 HTML 이고, 이 문서 안의 데이터는 UTF-8 방식으로 인코딩한다라고 설정함 -->
<!-- 현재 이 JSP 페이지는 UTF-8 방식으로 인코딩한다-->
<!-- UTF-8 인코딩 방식은 한글을 포함한 전 세계 모든 문자열을 부호화할 수 있는 방법이다.-->
<!-- 모든 JSP 페이지, 상단에 무조건 들어가는 코드 -->
<!-- UTF-8 인코딩방식을 가지고 있는 text중의 하나인 html 파일로 처리 -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>



<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<head>
	<script src="/resources/jquery-1.11.0.min.js"></script>
	<script>
		$(document).ready(function(){
			$(".writer").val("사오정")
			$(".subject").val("빨리 취업 하세요.")
			$(".email").val("abc@naver.com")
			$(".content").val("어쩌구 저쩌구")
			$(".pwd").val("1234")
		})
		// **********************************************************
		// [게시판 등록 화면]에 입력된 데이터의 유효성 체크를 자스로 하지 않고
		// 비동기 방식으로 서버에 "/boardRegProc.do"로 접속하는 함수 선언
		// **********************************************************
		function checkBoardRegForm(){
			//--------------------------------------------
			// "정말 등록하시겠습니까?" 라고 물어보기
			//--------------------------------------------
			if(confirm("정말 등록하시겠습니까?")==false) {return;}

			//alert( $("[name=boardRegForm]").serialize())
			//return;
			
			//--------------------------------------------
			//현재 화면에서 페이지 이동 없이(=비동기 방식으로)
			//서버쪽 boardRegProc.do로 접속하여 게시판 글쓰기를 하고 
			//글쓰기 성공여부를 알려주기
			//--------------------------------------------
			$.ajax({
				// 서버쪽 호출 URL 주소 지정
				url:"/boardRegProc.do"
				// form 태그 안의 입력양식 데이터 즉, 파라미터값을 보내는 방법 지정 
				, type: "post"
				// 서버로 보낼 파라미터명과 파라미터값을 설정
				, data: $("[name=boardRegForm]").serialize()
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
					
					//--------------------------------------------	
					//매개변수로 들어온 html 소스에서 class=boardRegCnt를 가진 태그가 끌어안고 있는 숫자 꺼내기
					//즉 게시판 글 입력 성공 행의 개수 꺼내기
					// 꺼낸 존재 개수의 앞 뒤 공백 제거하기
					//--------------------------------------------
					//alert(responseHtml);
					var boardRegCnt = $(responseHtml).filter(".boardRegCnt").text();
					boardRegCnt = $.trim(boardRegCnt);
					boardRegCnt = parseInt(boardRegCnt,10);

					//방법1
					var b_no = $("[name=boardRegForm] [name=b_no]").val();
					//=================================================
					//name=b_no의 value값이 0일 경우 (새글쓰기일 경우)
					//=================================================
					if(b_no=="0"){
						//--------------------------------------------
						//게시판 글 입력 성공 행의 개수가 1이면 즉 입력이 성공했으면
						//--------------------------------------------
						//if(boardRegCnt=="1"){
						if(boardRegCnt==1){
							alert("새글쓰기 성공");
							location.replace("/boardList.do");
						}
						//--------------------------------------------
						//그렇지 않으면, 즉 입력이 실패했으면
						//--------------------------------------------
						else{
							alert("새글쓰기 실패");
						}
					//=================================================
					//name=b_no의 value값이 0이 아닐 경우 (댓글쓰기일 경우)
					//=================================================
					}else{
						//--------------------------------------------
						//게시판 글 입력 성공 행의 개수가 1이면 즉 입력이 성공했으면
						//--------------------------------------------
						if(boardRegCnt==1){
							alert("댓글쓰기 성공");
							location.replace("/boardList.do");
						}
						//--------------------------------------------
						//그렇지 않으면, 즉 입력이 실패했으면
						//--------------------------------------------
						else{
							alert("댓글쓰기 실패");
						}
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
		<!-- *************************************************** -->
		<!-- [로그인 정보 입력양식]을 내포한 form 태그 선언-->
		<!-- *************************************************** -->
		<form name="boardRegForm" method="post" action="/boardRegProc.do">
			<table border=1 cellpadding=5>
			
			<!-- HttpServletRequset 객체의 b_no가 null이면 새글쓰기 -->
			<% if(request.getParameter("b_no")==null){ %>
			<caption><b>새글쓰기</b></caption>
			
			<!-- HttpServletRequset 객체의 b_no가 null이 아니면 댓글쓰기 -->
			<% }else{ %>
			<caption><b>댓글쓰기</b></caption>
			<% } %>
			
				<tr>
					<th bgcolor="lightgray">이름</th>
					<td><input type="text" name="writer" class="writer" size="10" maxlength=10></td>
				</tr>
				<tr>
					<th bgcolor="lightgray">제목</th>
					<td><input type="text" name="subject" class="subject" size=40 maxlength="30"></td>
				</tr>
				<tr>
					<th bgcolor="lightgray">이메일</th>
					<td><input type="text" name="email" class="email" size=40 maxlength="30"></td>
				</tr>
				<tr>
					<th bgcolor="lightgray">내용</th>
					<td><textarea name="content" class="content" cols=50 rows=20></textarea></td>
				</tr>
				<tr>
					<th bgcolor="lightgray">비밀번호</th>
					<td><input type="password" name="pwd" class="pwd" size=10 maxlength="4"></td>
				</tr>
			</table>
			<div style="height:6px"></div>
			<input type="button" value="저장" onClick="checkBoardRegForm();">
			<input type="reset" value="다시작성">
			<input type="button" value="목록보기" onclick="location.replace('/boardList.do')">
			
			<!-- 만약 파명 b_no의 파값이 null이면(새글쓰기)-->
			<!-- name="b_no"를 가진 hidden 태그의 value에 0입력하기 -->
			<% if(request.getParameter("b_no")==null){ %>
				<input type="hidden" name="b_no" value="0" >
				
			<!-- 만약 파명 b_no의 파값이 null이 아니면(댓글쓰기)-->
			<!-- name="b_no"를 가진 hidden 태그의 value에 현재 화면의 파값 입력하기 -->
			<% }else{ %>
				<input type="hidden" name="b_no" value="<%=request.getParameter("b_no")%>" >
			<% } %>
		</form>
	</center>
</body>

</html>