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
	function goBoardRegForm(){
		location.replace("/boardRegForm.do");
	}

	function goBoardContentForm(b_no){
		$("[name=boardContentForm] [name=b_no]").val(b_no);
		document.boardContentForm.submit();
	}
		
</script>
</head>
<body>
	<center>
		<a href="javascript:goBoardRegForm()">[새글쓰기]</a>
		<table border=1>
			<tr><th>번호<th>제목<th>작성자<th>조회수<th>등록일
			<%
			List<Map<String,String>> boardList = (List<Map<String,String>>)request.getAttribute("boardList");
			if(boardList!=null){
				int totCnt = boardList.size();
				
				for(int i = 0; i<boardList.size(); i++){
					Map<String,String> map = boardList.get(i);
					
					String b_no = map.get("b_no");
					String subject = map.get("subject");
					String writer = map.get("writer");
					String readcount = map.get("readcount");
					String reg_date = map.get("reg_date");
					String print_level = map.get("print_level");
					int print_level_int = Integer.parseInt(print_level,10);
					
					String xxx="";
					for(int j=0; j<print_level_int; j++){
						xxx=xxx+"&nbsp&nbsp&nbsp&nbsp";
					}
					if(print_level_int>0){xxx=xxx+"ㄴ";}
					
					out.println("<tr style='cursor:pointer;' onClick='goBoardContentForm("+b_no+")'><td>"+(totCnt--)
							+"<td>"+xxx+subject+"<td>"+writer+"<td>"+readcount+"<td>"+reg_date);
				
				}
			}
			%>
		</table>
		
		<form name="boardContentForm" method="post" action="boardContentForm.do">
			<input type="hidden" name="b_no">
		</form>
	</center>
</body>
</html>