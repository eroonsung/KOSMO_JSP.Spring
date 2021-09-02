<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- ********************************************** -->
<!-- 현재 JSP 페이지에서 사용할 클래스 수입하기 -->
<!-- ********************************************** -->
<%@ page import = "java.util.Map" %>
<%@ page import = "java.util.List" %>

<%@ include file="common.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script>

	// **********************************************************
	// [게시판 글쓰기 화면]으로 이동하는 함수 선언
	// **********************************************************
	function goBoardRegForm(){
		location.replace("boardRegForm.do");
	}
	
	// **********************************************************
	// [게시판 상세보기 화면]으로 이동하는 함수 선언
	// **********************************************************
	function goBoardContentForm(b_no){
		$("[name=boardContentForm] [name=b_no]").val(b_no);
		document.boardContentForm.submit();
	}

	function search(){
		var keyword1 = $(".keyword1").val();
		if(keyword1.split(" ").join("")==""){
			//alert("키워드가 비어있어 검색하지 않습니다.");
			$(".keyword1").val("");
			//$(".keyword1").focus();
			//return;
		}
		$(".keyword1").val($.trim(keyword1));
		searchExe();
	}
	
	function searchAll(){
		$(".keyword1").val("");
		searchExe();
	}

	function searchExe(){
		$.ajax({
			url: "/boardList.do"
			, type: "post"
			, data: $("[name=boardListForm]").serialize()
			, success: function(responseHtml){
				var html = $(responseHtml).find(".searchResult").html();
				$(".searchResult").html(html);
				
				var text = $(responseHtml).find(".boardListAllCnt").text();
				$(".boardListAllCnt").text(text);

				$(".pageNo").html( $(responseHtml).find(".pageNo").html()); 
			}
			, error: function(){
				alert("서버 접속 실패");
			}
		})
	}

	function search_with_changePageNo(selectPageNo){
		$("[name=boardListForm]").find(".selectPageNo").val(selectPageNo);
		search();
	}

	$(document).ready(function(){
		$(".dayList").click(function(){
			search();
		})
		
		$(".rowCntPerPage").change(function(){
			search();
		})
		
		$(".boardSearch").click(function(){
			search();
		})
		
		$(".boardSearchAll").click(function(){
			$(".dayList").prop("checked", false);
			searchAll();
		})
	})
	
</script>
</head>

<body onKeyDown="if(event.keyCode==13){search();}">
	<center>
	<div class="logout"></div>
	<%
	List<Map<String,String>> boardList = (List<Map<String,String>>)request.getAttribute("boardList");
	int boardListAllCnt = (Integer)request.getAttribute("boardListAllCnt");
	
	int selectPageNo = (Integer)request.getAttribute("selectPageNo");
	int rowCntPerPage = (Integer)request.getAttribute("rowCntPerPage");
	int min_pageNo = (Integer)request.getAttribute("min_pageNo");
	int max_pageNo = (Integer)request.getAttribute("max_pageNo");
	int last_pageNo = (Integer)request.getAttribute("last_pageNo");
	int pageNoCntPerPage = (Integer)request.getAttribute("pageNoCntPerPage");

	int start_serial_no = (Integer)request.getAttribute("start_serial_no");
	%>
		<form name="boardListForm" method="post" onSubmit="return false">
			[키워드] : <input type="text" name="keyword1" class="keyword1">
			
			<input type="checkbox" name="dayList" class="dayList" value="어제">어제
			<input type="checkbox" name="dayList" class="dayList" value="오늘">오늘
			<input type="checkbox" name="dayList" class="dayList" value="일주일내">일주일내
			<input type="checkbox" name="dayList" class="dayList" value="30일내">30일내
			
			<div style="height:5px"></div>
			
			<input type="hidden" name="selectPageNo" class="selectPageNo" value="1">
			<select name="rowCntPerPage" class="rowCntPerPage" onChange="search();">
				<option value="10">10
				<option value="15">15
				<option value="20" selected>20
				<option value="25">25
				<option value="30">30
			</select>행 보기
			
			<input type="button" value=" 검색 " class="boardSearch">
			<input type="button" value="모두검색" class="boardSearchAll">
					
		</form>
	
		<a href = "javascript:goBoardRegForm();">[새글쓰기]</a>
		
		<div class = "pageNo">
		<%
		if(boardListAllCnt>0){
			if(selectPageNo>1){
				out.print("<span style='cursor:pointer;' onClick='search_with_changePageNo(1);'>[처음]</span> ");
				if(min_pageNo>1){
					out.print("<span style='cursor:pointer;' onClick='search_with_changePageNo("+(min_pageNo-1)+");'>[<<]</span> ");
				}else{
					out.print("<span>[<<]</span> ");
				}
				out.print("<span style='cursor:pointer;' onClick='search_with_changePageNo("+(selectPageNo-1)+");'>[이전]</span> ");
			}else{
				out.print("<span>[처음]</span> ");
				out.print("<span>[<<]</span> ");
				out.print("<span>[이전]</span> ");
			}

			for(int i=min_pageNo; i<=max_pageNo; i++ ){
				if(i==selectPageNo){
					out.print("<span><b>"+i+"</b></span> ");	
				}else{
					out.print("<span style='cursor:pointer;' onClick='search_with_changePageNo("+i+");'>["+i+"]</span> ");
				}
						
			}
			
			if(selectPageNo<last_pageNo){
				out.print(" <span style='cursor:pointer;' onClick='search_with_changePageNo("+(selectPageNo+1)+");'>[다음]</span>");
				if(last_pageNo>max_pageNo){
				out.print(" <span style='cursor:pointer;' onClick='search_with_changePageNo("+(max_pageNo+1)+");'>[>>]</span>");
				}else{
					out.print(" <span>[>>]</span>");
				}
				out.print(" <span style='cursor:pointer;' onClick='search_with_changePageNo("+last_pageNo+");'>[끝]</span>");
			}else{
				out.print(" <span>[다음]</span>");
				out.print(" <span>[>>]</span>");
				out.print(" <span>[끝]</span>");
			}
		}
		%>
		</div>
		
		<div class="boardListAllCnt">총 <%=boardListAllCnt%>개</div>
		
		<div class="searchResult">
			<table border=1>
			<tr><th>번호<th>제목<th>작성자<th>조회수<th>등록일
			<%
				if(boardList!=null){
				int serialNo_asc = start_serial_no;
				int serialNo_desc = boardListAllCnt-start_serial_no+1;
				
				for(int i=0; i<boardList.size(); i++){
					Map<String,String> map= boardList.get(i);
					
				String b_no = map.get("b_no");
				
				String subject = map.get("subject");
				String writer = map.get("writer");
				String readcount = map.get("readcount");
				String reg_date = map.get("reg_date");
				
				String print_level = map.get("print_level");
				int print_level_int = Integer.parseInt(print_level,10);
				
				String blank= "";
				for(int j=0; j<print_level_int; j++){
					blank=blank+"&nbsp&nbsp&nbsp&nbsp";
				}
				if(print_level_int>0){blank=blank+"ㄴ";}
				
				out.println("<tr style='cursor:pointer;' onClick='goBoardContentForm("+b_no+")'><td>"
					+(serialNo_desc--)+"<td>"+blank+subject+"<td>"+writer+"<td>"+readcount+"<td>"+reg_date);
					//serialNo_asc++
				}
			}		
			%>
			</table>
		</div>
		<!-- ******************************************** -->
		<!-- 게시판 상세보기 화면으로 이동하는 form 태그 선언하기 -->
		<!-- ******************************************** -->
		<form name="boardContentForm" method="post" action="boardContentForm.do">
			<input type="hidden" name="b_no">
		</form>
	</center>

</body>
</html>