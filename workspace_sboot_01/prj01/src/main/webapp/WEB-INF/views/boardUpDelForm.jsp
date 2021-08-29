<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page import = "com.naver.erp.BoardDTO" %>



<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<head>
	<script src="/resources/jquery-1.11.0.min.js"></script>
	<script>
		$(document).ready(function(){
			
		})
		function checkBoardUpDelForm(upDel){
			//alert(upDel);
			//return;
			if(upDel=="up"){
				if(confirm("정말 수정하시겠습니까?")==false){return};
				$("[name='upDel']").val("up");
			}

			else if(upDel=="del"){
				if(confirm("정말 삭제하시겠습니까?")==false){return};
				$("[name='upDel']").val("del");
			}
			
			$.ajax({
				url:"/boardUpDelProc.do"
				, type: "post"
				, data: $("[name=boardUpDelForm]").serialize()
				, success: function (responseHtml) {
					var msg = $(responseHtml).filter(".msg").text();
					msg = $.trim(msg);
					
					var boardUpDelCnt = $(responseHtml).filter(".boardUpDelCnt").text();
					boardUpDelCnt = $.trim(boardUpDelCnt);
					boardUpDelCnt = parseInt(boardUpDelCnt,10);
					//====================================================
					if(upDel=="up"){
						if(msg!=""&& msg.length>0){
							alert(msg);
							return;
						}
						
						if(boardUpDelCnt == -1){
							alert("게시판 글이 삭제되었습니다.");
							location.replace("/boardList.do");
						}else if(boardUpDelCnt == -2){
							alert("암호가 틀립니다.");
							$(".pwd").val("");
						}else if(boardUpDelCnt == 1){
							if(confirm("수정 성공. 목록화면으로 이동하시겠습니까?")){
								location.replace("/boardList.do");
							}else{
								return;
							}
						}else{
							alert("서버 에러 발생! 관리자에게 문의 바람.");
						}
					}
					//====================================================
					else if(upDel=="del"){
						if(boardUpDelCnt == 1){
							alert("삭제성공.");
							location.replace("/boardList.do");
						}else if(boardUpDelCnt == -1){
							alert("게시판 글이 삭제되었습니다.");
							location.replace("/boardList.do");
						}else if(boardUpDelCnt == -2){
							alert("암호가 틀립니다.");
							$(".pwd").val("");
						}else if(boardUpDelCnt == -3){
							alert("댓글이 존재해 삭제가 불가능합니다.");
						}else{
							alert("서버 에러 발생! 관리자에게 문의 바람.");
						}
						
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
	<%
		BoardDTO boardDTO = (BoardDTO)request.getAttribute("boardDTO");
		int b_no = 0;
		if(boardDTO!= null){
			b_no = boardDTO.getB_no();
			String subject = boardDTO.getSubject();
			String writer = boardDTO.getWriter();
			String content = boardDTO.getContent();
			String reg_date = boardDTO.getReg_date();
			int readcount = boardDTO.getReadcount();
			String email = boardDTO.getEmail();
	%>

		<form name="boardUpDelForm">
			<table border=1 cellpadding=5>
			<caption><b>게시판 수정/삭제</b></caption>
				<tr>
					<th bgcolor="lightgray">이름</th>
					<td><input type="text" name="writer" class="writer" size="10" maxlength=10 value="<%=writer%>"></td>
				</tr>
				<tr>
					<th bgcolor="lightgray">제목</th>
					<td><input type="text" name="subject" class="subject" size=40 maxlength="30" value="<%=subject%>"></td>
				</tr>
				<tr>
					<th bgcolor="lightgray">이메일</th>
					<td><input type="text" name="email" class="email" size=40 maxlength="30" value="<%=email%>"></td>
				</tr>
				<tr>
					<th bgcolor="lightgray">내용</th>
					<td><textarea name="content" class="content" cols=50 rows=20><%=content%></textarea></td>
				</tr>
				<tr>
					<th bgcolor="lightgray">비밀번호</th>
					<td><input type="password" name="pwd" class="pwd" size=10 maxlength="4"></td>
				</tr>
			</table>
			<div style="height:6px"></div>
			<input type="button" value="수정" onClick="checkBoardUpDelForm('up');">
			<input type="reset" value="삭제" onClick="checkBoardUpDelForm('del');">
			<input type="button" value="목록보기" onclick="location.replace('/boardList.do');">
			
			<input type="hidden" name="b_no" value=<%=b_no%>>
			<input type="hidden" name="upDel">
		</form>
		<%
			}else{
				out.print("<script>alert('삭제된 글입니다.'); location.replace('/boardList.do')</script>");
			}
		%>
	</center>
</body>

</html>