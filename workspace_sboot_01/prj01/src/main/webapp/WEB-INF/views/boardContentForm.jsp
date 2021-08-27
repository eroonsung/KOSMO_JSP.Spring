<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="com.naver.erp.BoardDTO" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="/resources/jquery-1.11.0.min.js"></script>
<script>


</script>
</head>
<body>
	<center>
		<%
		BoardDTO boardDTO = (BoardDTO)request.getAttribute("boardDTO");
		int b_no=0;
		if(boardDTO!=null){
			b_no = boardDTO.getB_no();
			String subject = boardDTO.getSubject();
			String writer = boardDTO.getWriter();
			String content = boardDTO.getContent();
			String reg_date = boardDTO.getReg_date();
			int readcount = boardDTO.getReadcount();
			String email = boardDTO.getEmail();
		
		%>
		<b>글 상세보기</b>
		<table border=1>
			<tr align=center>
				<th bgcolor="lightgray">글 번호</th>
				<td><%=b_no%></td>
				<th bgcolor="lightgray">조회수</th>
				<td><% out.print(readcount); %></td>
			</tr>
			<tr align=center>
				<th bgcolor="lightgray">작성자</th>
				<td><% out.print(writer); %></td>
				<th bgcolor="lightgray">작성일</th>
				<td><% out.print(reg_date); %></td>
			</tr>
			<tr align=center>
				<th bgcolor="lightgray">이메일</th>
				<td colspan=3><% out.print(email); %></td>
			</tr>
			<tr align=center>
				<th bgcolor="lightgray">글제목</th>
				<td colspan=3><% out.print(subject); %></td>
			</tr>
			<tr align=center>
				<th bgcolor="lightgray">글내용</th>
				<td colspan=3>
					<textarea name="content" rows="13" cols="45" style="border:0" readonly><% out.print(content); %></textarea>
				</td>
			</tr>
		</table><br>
		<input type="button" value="댓글쓰기" onClick="goBoardRegForm();">&nbsp;
		<input type="button" value="수정/삭제" onClick="goBoardUpDelForm();">&nbsp;
		<input type="button" value="전체 글보기" onclick="location.replace('/boardList.do');">
		<%
		}else{
			out.print("<script>alert('삭제된 글입니다'); location.replace('boardList.do');</script>");
		} %>
	</center>
	
	<form name="boardUpDelForm" method="post" action="/boardUpDelForm.do">
		<input type="hidden" name="b_no" value=<%=b_no%>>
	</form>
</body>
</html>