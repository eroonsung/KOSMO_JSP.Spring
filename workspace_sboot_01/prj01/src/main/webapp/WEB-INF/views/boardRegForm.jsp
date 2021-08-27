<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="/resources/jquery-1.11.0.min.js"></script>
<script>

	$(document).ready(function(){
		$(".writer").val("사오정");
		$(".subject").val("안녕하세요.");
		$(".email").val("a@naver.com");
		$(".content").val("어쩌구 저쩌구");
		$(".pwd").val("1234");
	})
	function checkBoardRegForm(){
		if(confirm("정말 등록하시겠습니까?")==false){return;}

		$.ajax({
			url:"/boardRegProc.do"
			, type: "post"
			, data: $("[name=boardRegForm]").serialize()
			, success: function(responseHtml){
				var msg = $(responseHtml).filter(".msg").text();
				msg = $.trim(msg);

				if(msg!=0 && msg.length>0){
					alert(msg);
					return;
				}
				
				var boardRegCnt = $(responseHtml).filter(".boardRegCnt").text();
				boardRegCnt = $.trim(boardRegCnt);
				boardRegCnt = parseInt(boardRegCnt,10);

				if(boardRegCnt==1){
					alert("새글쓰기 성공");
					location.replace("boardList.do");					
				}else{
					alert("새글쓰기 실패");
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
		<form name="boardRegForm" method="post" action="/boardRegProc.do">
			<table border=1 cellpadding=5>
			<caption><b>[새글쓰기]</b></caption>
				<tr>
					<th bgcolor="lightgray">이름</th>
					<td><input type="text" name="writer" class="writer" size="10" maxlength="10"></td>				
				</tr>
				<tr>
					<th bgcolor="lightgray">제목</th>
					<td><input type="text" name="subject" class="subject" size="40" maxlength="30"></td>				
				</tr>
				<tr>
					<th bgcolor="lightgray">이메일</th>
					<td><input type="text" name="email" class="email" size="40" maxlength="30"></td>				
				</tr>
				<tr>
					<th bgcolor="lightgray">내용</th>
					<td><textarea name="content" class="content" cols="50" rows="20"></textarea></td>				
				</tr>
				<tr>
					<th bgcolor="lightgray">비밀번호</th>
					<td><input type="password" name="pwd" class="pwd" size="10" maxlength="4"></td>				
				</tr>
			</table>
			<div style="height:6px"></div>
			<input type="button" value="저장" onClick="checkBoardRegForm()">
			<input type="reset" value="다시작성" >
			<input type="button" value="목록보기" onClick="location.replace('boardList.do')">
		</form>
	</center>
</body>
</html>