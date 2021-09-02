<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>

<%@ include file="common.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<script>
	function goBoardRegForm(){
		location.replace("/boardRegForm.do");
	}

	function goBoardContentForm(b_no){
		$("[name=boardContentForm] [name=b_no]").val(b_no);
		document.boardContentForm.submit();
	}

	function search(){
		var keyword1 = $(".keyword1").val();
		if(keyword1==null||keyword1.split(" ").join("")==""){
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
		$(".rowCntPerPage").change(function(){
			$(".selectPageNo").val("1");
			search();
		})
		
		$(".boardSearch").click(function(){
			search();
		})

		$(".boardSearchAll").click(function(){
			$(".dayList").prop("checked",false);
			searchAll();
		})
		
		$(".dayList").click(function(){
			search();
		})
		
		
	})
		
</script>
</head>
<body onKeydown="if(event.keyCode==13){search();}">
	<div class="logout"></div>
	<center>
	<%
	List<Map<String,String>> boardList = (List<Map<String,String>>)request.getAttribute("boardList");
	int boardListAllCnt = (Integer)request.getAttribute("boardListAllCnt");
	
	int selectPageNo = (Integer)request.getAttribute("selectPageNo");
	int min_pageNo = (Integer)request.getAttribute("min_pageNo");
	int max_pageNo = (Integer)request.getAttribute("max_pageNo");
	int last_pageNo = (Integer)request.getAttribute("last_pageNo");
	int start_serial_no = (Integer)request.getAttribute("start_serial_no");
	
	%>
	<form name="boardListForm" method="post" onSubmit="return false">
		[키워드] : <input type="text" name="keyword1" class="keyword1">
		
		<input type="checkbox" name="dayList" class="dayList" value="그제">그제
		<input type="checkbox" name="dayList" class="dayList" value="어제">어제
		<input type="checkbox" name="dayList" class="dayList" value="오늘">오늘
		<input type="checkbox" name="dayList" class="dayList" value="일주일내">일주일내
		<div style="height:5px"></div>
		
		<input type="hidden" name="selectPageNo" class="selectPageNo" value="1">
		<select name="rowCntPerPage" class="rowCntPerPage">
			<option value="10">10
			<option value="15">15
			<option value="20" selected>20
			<option value="25">25
			<option value="30">30
		</select>행 보기
		
		<input type="button" value=" 검색 " class="boardSearch">
		<input type="button" value="모두검색" class="boardSearchAll">
	
	</form>
		<a href="javascript:goBoardRegForm()">[새글쓰기]</a>
		<div style="height:5px"></div>
		
		<div class="pageNo">
			<% 
			/*
			//방법 1 => 이전/다음 클릭했을때 10단위로 페이지가 달라짐
			if(boardListAllCnt>0){
				out.print("<span style='cursor:pointer;' onClick='search_with_changePageNo(1);'>[처음]</span> ");
				
				if(min_pageNo>1){
					out.print("<span style='cursor:pointer;' onClick='search_with_changePageNo("+(min_pageNo-1)+");'>[이전]</span> ");
				}
				
				for(int i=min_pageNo; i<=max_pageNo; i++ ){
					if(i==selectPageNo){
						out.print("<span><b>"+i+"</b></span>&nbsp;&nbsp;");	
					}else{
						out.print("<span style='cursor:pointer;' onClick='search_with_changePageNo("+i+");'>["+i+"]</span> ");
					}
							
				}
				
				if(last_pageNo>max_pageNo){
					out.print("<span style='cursor:pointer;' onClick='search_with_changePageNo("+(max_pageNo+1)+");'>[다음]</span> ");
				}
				
				out.print("<span style='cursor:pointer;' onClick='search_with_changePageNo("+last_pageNo+");'>[끝]</span> ");
			}
			*/
			/*
			//방법 2 => 이전/다음 클릭했을 때 1단위로 페이지가 달라짐
			if(boardListAllCnt>0){
				if(selectPageNo>1){
					out.print("<span style='cursor:pointer;' onClick='search_with_changePageNo(1);'>[처음]</span> ");
					out.print("<span style='cursor:pointer;' onClick='search_with_changePageNo("+(selectPageNo-1)+");'>[이전]</span> ");
					out.print("&nbsp;&nbsp;");
				}
	
				for(int i=min_pageNo; i<=max_pageNo; i++ ){
					if(i==selectPageNo){
						out.print("<span><b>"+i+"</b></span>&nbsp;&nbsp;");	
					}else{
						out.print("<span style='cursor:pointer;' onClick='search_with_changePageNo("+i+");'>["+i+"]</span> ");
					}
							
				}
				
				if(selectPageNo<last_pageNo){
					out.print("&nbsp;&nbsp;");
					out.print("<span style='cursor:pointer;' onClick='search_with_changePageNo("+(selectPageNo+1)+");'>[다음]</span> ");
					out.print("<span style='cursor:pointer;' onClick='search_with_changePageNo("+last_pageNo+");'>[끝]</span> ");
				}
			}
			*/
			//방법 3 => 처음/이전/다음/끝을 다 보이게 하되 클릭 막기
			/*
			if(boardListAllCnt>0){
				if(selectPageNo>1){
					out.print("<span style='cursor:pointer;' onClick='search_with_changePageNo(1);'>[처음]</span> ");
					out.print("<span style='cursor:pointer;' onClick='search_with_changePageNo("+(selectPageNo-1)+");'>[이전]</span> ");
					out.print("&nbsp;&nbsp;");
				}else{
					out.print("<span>[처음]</span>");
					out.print("<span>[이전]</span>");
					out.print("&nbsp;&nbsp;");
				}
	
				for(int i=min_pageNo; i<=max_pageNo; i++ ){
					if(i==selectPageNo){
						out.print("<span><b>"+i+"</b></span>&nbsp;&nbsp;");	
					}else{
						out.print("<span style='cursor:pointer;' onClick='search_with_changePageNo("+i+");'>["+i+"]</span> ");
					}
							
				}
				
				if(selectPageNo<last_pageNo){
					out.print("&nbsp;&nbsp;");
					out.print("<span style='cursor:pointer;' onClick='search_with_changePageNo("+(selectPageNo+1)+");'>[다음]</span> ");
					out.print("<span style='cursor:pointer;' onClick='search_with_changePageNo("+last_pageNo+");'>[끝]</span> ");
				}else{
					out.print("&nbsp;&nbsp;");
					out.print("<span>[다음]</span>");
					out.print("<span>[끝]</span>");
				}
			}
			*/
			// 방법 4 => 종합
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
				int serialNo1 = start_serial_no; // 정순번호
				int serialNo2 = boardListAllCnt-start_serial_no+1; // 역순번호
				
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
					
					out.println("<tr style='cursor:pointer;' onClick='goBoardContentForm("+b_no+")'><td>"
					+(serialNo2--)+"<td>"+xxx+subject+"<td>"+writer+"<td>"+readcount+"<td>"+reg_date);
					//serialNo1++
				}
			}
			%>
		</table>
		</div>
		
		<form name="boardContentForm" method="post" action="boardContentForm.do">
			<input type="hidden" name="b_no">
		</form>
	</center>
</body>
</html>