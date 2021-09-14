<!-- ********************************************************************************** -->
<!-- JSP 기술의 한 종류인 [Page Directive]를 이용하여 현 JSP 페이지 처리 방식 선언하기 -->
<!-- ********************************************************************************** -->
<!-- 현재 이 JSP 페이지 실행 후 생성되는 문서는 HTML 이고, 이 문서 안의 데이터는 UTF-8 방식으로 인코딩한다라고 설정함 -->
<!-- 현재 이 JSP 페이지는 UTF-8 방식으로 인코딩한다-->
<!-- UTF-8 인코딩 방식은 한글을 포함한 전 세계 모든 문자열을 부호화할 수 있는 방법이다.-->
<!-- 모든 JSP 페이지, 상단에 무조건 들어가는 코드 -->
<!-- UTF-8 인코딩방식을 가지고 있는 text중의 하나인 html 파일로 처리 -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!-- ****************************************** -->
<!-- 현재 JSP 페이지에서 사용할 클래스 수입하기 -->
<!-- ****************************************** -->
<%@ page import = "com.naver.erp.BoardDTO" %>

<!-- ******************************************** -->
<!-- JSP 기술의 한 종류인 [Include Directive]를 이용하여
	common.jsp 파일 내의 소스를 삽입하기 -->
<!-- ******************************************** -->
<%//@include file="common.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<head>
	<script>
		$(document).ready(function(){
	
			})

		//--------------------------------------------
		//게시판 수정/삭제 화면으로 이동하는 함수 선언
		//--------------------------------------------
		function goBoardUpDelForm(){
			//-----------------------------------------
			//name=boardUpDelForm 을 가진 form 태그 내부의 action값의 URL주소로 서버에 접속하기
			// 즉, 상세보기 화면으로 이동하기
			//-----------------------------------------
			document.boardUpDelForm.submit();
		}

		//--------------------------------------------
		//게시판 댓글쓰기 화면으로 이동하는 함수 선언
		//--------------------------------------------
		function goBoardRegForm(){
			//현재화면에서 다른화면 열기
			//-----------------------------------------
			//1. location.replace("/다른화면URL주소")
				//location.replace("/boardRegForm.do?b_no="+b_no);
				// => 데이터(입력양식 name/value)를 가지고 이동하기 어려움
				//해결방법: location.replace("/xxx.do?파라미터명=파라미터값&...") 
					// -> get방식:보안성이 떨어짐
			//-----------------------------------------
			//2. document.Form태그name값.submit();
				//document.boardRegForm.submit();
				// => Form태그 안에 있는 모든 입력양식을 post방식으로 보냄
				
		//--------------------------------------------
		//name=boardRegForm을 가진 form태그의 action값을 URL로 서버에 접속하라
		//--------------------------------------------
			document.boardRegForm.submit();
		}
		
	</script>

</head>

<body>
	<center>

		<% 
			BoardDTO boardDTO = (BoardDTO)request.getAttribute("boardDTO");
			int b_no =0;
			if(boardDTO!= null){
				b_no = boardDTO.getB_no();
				String subject = boardDTO.getSubject();
				String writer = boardDTO.getWriter();
				String content = boardDTO.getContent();
				String reg_date = boardDTO.getReg_date();
				int readcount = boardDTO.getReadcount();
				String email = boardDTO.getEmail();
		%>
		<b>[글 상세 보기]</b>
		
		<table border=1>
			<tr align=center>
				<th bgcolor="lightgray">글번호</th>
				<td><% out.print(b_no); %></td> <!-- </%=b_no%> -->
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
				<th bgcolor="lightgray">이매일</th>
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
		<div class="logout"></div>
		<%
			}else{
				out.print("<script>alert('삭제된 글입니다.'); location.replace('/boardList.do')</script>");
			}
		%>
		
		<!-- ********************************************************** -->
		<!-- [게시판 수정/삭제 화면]으로 이동하는 form태그 선언] -->
		<!-- ********************************************************** -->
		<form name="boardUpDelForm" method="post" action="/boardUpDelForm.do">
			<input type="hidden" name="b_no" value="<%=b_no%>">
			<!--<input type="text" name="b_no" value="<%=b_no%>">-->
		</form>
		
		<!-- ********************************************************** -->
		<!-- [게시판 댓글쓰기 화면]으로 이동하는 form태그 선언] -->
		<!-- ********************************************************** -->
		<form name="boardRegForm" method="post" action="boardRegForm.do">
			<input type="hidden" name="b_no" value="<%=b_no%>">
		</form>
		
		
	</center>
</body>

</html>