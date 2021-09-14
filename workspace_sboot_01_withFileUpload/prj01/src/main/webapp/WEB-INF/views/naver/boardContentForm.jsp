<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="com.naver.erp.BoardDTO" %>

<%@ include file="common.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<script>
	function goBoardRegForm(){
			document.boardRegForm.submit();
		}
	function goBoardUpDelForm(){
			document.boardUpDelForm.submit();
		}
	

</script>
</head>
<body>
	<div class="logout"></div>
	<center>
		<b>글 상세보기</b>
		<c:if test="${!empty requestScope.boardDTO}">
		<table border=1 cellpadding=5 class="tbcss2">
			<tr align=center>
				<th bgcolor="lightgray">글 번호</th>
				<td>${boardDTO.b_no}</td>
				<th bgcolor="lightgray">조회수</th>
				<td>${boardDTO.readcount}</td>
			</tr>
			<tr align=center>
				<th bgcolor="lightgray">작성자</th>
				<td>${boardDTO.writer}</td>
				<th bgcolor="lightgray">작성일</th>
				<td>${boardDTO.reg_date}</td>
			</tr>
			<tr align=center>
				<th bgcolor="lightgray">이메일</th>
				<td colspan=3>${boardDTO.email}</td>
			</tr>
			<tr align=center>
				<th bgcolor="lightgray">글제목</th>
				<td colspan=3>${boardDTO.subject}</td>
			</tr>
			<tr align=center>
				<th bgcolor="lightgray">글내용</th>
				<td colspan=3>
					<textarea name="content" rows="13" cols="45" style="border:0" 
					readonly>${boardDTO.content}</textarea>
				</td>
			</tr>
			<tr>
					<th bgcolor="lightgray">이미지</th>
					<td colspan=3>  
					<c:if test= "${!empty boardDTO.pic}">
						<img src="/resources/img/${boardDTO.pic}" width="50%"> 
					</c:if>	
					</td>
			</tr>
		</table><br>
		<input type="button" value="댓글쓰기" onClick="goBoardRegForm();">&nbsp;
		<input type="button" value="수정/삭제" onClick="goBoardUpDelForm();">&nbsp;
		<input type="button" value="전체 글보기" onclick="location.replace('/${naverPath}boardList.do');">
		</c:if>
		
		<c:if test="${empty requestScope.boardDTO}">
			<script>alert('삭제된 글입니다.'); location.replace('/${naverPath}boardList.do')</script>
		</c:if>
	</center>
	
	<form name="boardUpDelForm" method="post" action="/${naverPath}boardUpDelForm.do">
		<input type="hidden" name="b_no" value="${boardDTO.b_no}">
	</form>
	
	<form name="boardRegForm" method="post" action="/${naverPath}boardRegForm.do">
		<input type="hidden" name="b_no" value="${boardDTO.b_no}">
	</form>
</body>
</html>