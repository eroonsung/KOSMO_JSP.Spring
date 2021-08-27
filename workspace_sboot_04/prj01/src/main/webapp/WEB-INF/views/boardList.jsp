<!-- ********************************************************************************** -->
<!-- JSP 기술의 한 종류인 [Page Directive]를 이용하여 현 JSP 페이지 처리 방식 선언하기 -->
<!-- ********************************************************************************** -->
<!-- 현재 이 JSP 페이지 실행 후 생성되는 문서는 HTML 이고, 이 문서 안의 데이터는 UTF-8 방식으로 인코딩한다라고 설정함 -->
<!-- 현재 이 JSP 페이지는 UTF-8 방식으로 인코딩한다-->
<!-- UTF-8 인코딩 방식은 한글을 포함한 전 세계 모든 문자열을 부호화할 수 있는 방법이다.-->
<!-- 모든 JSP 페이지, 상단에 무조건 들어가는 코드 -->
<!-- UTF-8 인코딩방식을 가지고 있는 text중의 하나인 html 파일로 처리 -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!-- ********************************************** -->
<!-- 현재 JSP 페이지에서 사용할 클래스 수입하기 -->
<!-- ********************************************** -->
<%@ page import = "java.util.Map" %>
<%@ page import = "java.util.List" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<head>
	<script src="/resources/jquery-1.11.0.min.js"></script>
	<script>
		// **********************************************************
		// [게시판 글쓰기 화면]으로 이동하는 함수 선언
		// **********************************************************
		function goBoardRegForm(){
			location.replace('/boardRegForm.do')
		}
		// **********************************************************
		// [게시판 상세보기 화면]으로 이동하는 함수 선언
		// **********************************************************
		function goBoardContentForm(b_no){
			//-----------------------------------------
			//name=boardContentForm 을 가진 form 태그 내부의 name=b_no를 가진 입력양식(hidden 태그)에
			// 클릭한 행의 게시판 번호 저장하기
			//-----------------------------------------
			$("[name=boardContentForm] [name=b_no]").val(b_no);
			//-----------------------------------------
			//name=boardContentForm 을 가진 form 태그 내부의 action값의 URL주소로 서버에 접속하기
			// 즉, 상세보기 화면으로 이동하기
			//-----------------------------------------
			document.boardContentForm.submit();
		}
		
	</script>

</head>

<body>
	<center>
		<a href = "javascript:goBoardRegForm();">[새글쓰기]</a>
		
		<table border=1>
		<tr><th>번호<th>제목<th>작성자<th>조회수<th>등록일
		<%
			List<Map<String,String>> boardList = (List<Map<String,String>>)request.getAttribute("boardList");
			if(boardList!=null){
				int totCnt = boardList.size();
				for(int i=0; i<boardList.size(); i++){
					
					Map<String,String> map = boardList.get(i); // 한 행의 해시맵
				
					// 컬럼명 또는 알리아스가 해시맵 객체의 키값으로 들어감
					// 오라클 컬럼명 대문자가 원칙
					String b_no = map.get("b_no");
					
					String subject = map.get("subject");
					String writer = map.get("writer");
					String readcount = map.get("readcount");
					String reg_date = map.get("reg_date");
					
					String print_level = map.get("print_level");
					int print_level_int = Integer.parseInt(print_level,10);
					
					String xxx = "";
					for(int j=0; j<print_level_int; j++){
						xxx = xxx+"&nbsp&nbsp&nbsp&nbsp";
					}
					if(print_level_int>0){xxx =xxx+"ㄴ";}
					
					out.println("<tr style='cursor:pointer;' onClick='goBoardContentForm("+b_no+")'><td>"+(totCnt--)+"<td>"+xxx+subject
							+"<td>"+writer+"<td>"+readcount+"<td>"+reg_date);
				}	
			}
		%>
		
		</table>
		<!-- ******************************************** -->
		<!-- 게시판 상세보기 화면으로 이동하는 form 태그 선언하기 -->
		<!-- 페이지 이동 -->
		<!-- ******************************************** -->
		<form name="boardContentForm" method="post" action="boardContentForm.do">
			<input type="hidden" name="b_no">
			<!-- <input type="text" name="b_no"> -->
			
		</form>
	</center>
</body>

</html>