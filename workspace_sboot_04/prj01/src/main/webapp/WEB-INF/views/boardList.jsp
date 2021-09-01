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
			// pk값이 필요 없기 때문에 location.replace()방법 사용
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

		// **********************************************************
		// 키워드에 입력한 데이터를 가진 [게시판 목록]을 검색해서 그 결과를 보여주는 함수 선언
		// **********************************************************
		function search(){
			
			//-----------------------------------------
			//입력한 키워드 얻어오기
			//-----------------------------------------
			var keyword1= $(".keyword1").val();
			
			//-----------------------------------------
			//만약 키워드가 비어있거나 공백으로 구성되어 있으면 경고하고 비우고 함수 중단하기
			//-----------------------------------------
			if(keyword1==null||keyword1.split(" ").join("")==""){
				//alert("키워드가 비어있어 검색하지 않습니다.");
				$(".keyword1").val("");
				//$(".keyword1").focus();
				//return;
				//=> 함수 중단하지 않고 DB연동 바로 하게 함
			}
			
			//-----------------------------------------
			//입력한 키워드의 앞 뒤 공백 제거하고 다시 넣어주기
			//-----------------------------------------
			$(".keyword1").val( $.trim(keyword1) );
			
			//-----------------------------------------
			//비동기방식으로 웹서버에 접근해서 키워드를 만족하는
			// 검색결과물을 응답받아 현 화면에 반영하기
			//-----------------------------------------
			searchExe();
		}

		// **********************************************************
		// [모두검색] 버튼을 눌렀을때 전체 결과물 보이는 함수 선언
		// **********************************************************
		function searchAll(){
			$(".keyword1").val("");
			searchExe();
		}

		function searchExe(){
			//-----------------------------------------
			//현재 화면에서 페이지 이동 없이(=비동기방식으로)
			//서버쪽 /boardList.do로 접속하여 키워드를 만족하는
			// 검색결과물을 응답받아 현 화면에 반영하기
			//-----------------------------------------
			$.ajax({
				url: "/boardList.do"
				, type :"post"
				, data: $("[name=boardListForm]").serialize()
				, success: function(responseHtml){
					//-----------------------------------------
					//매개변수 responseHtml로 들어온 검색 결과물 html 소스 문자열에서
					//class=searchResult를 가진 태그 내부의 [검색 결과물 html 소스]를 얻어서
					//아래 현 화면의 html 소스 중에 class=searchResult를 가진 태그 내부에 덮어씌우기
					//-----------------------------------------
					//var html = $(responseHtml).find(".searchResult").html();
					//$(".searchResult").html(html);
					$(".searchResult").html(
						 $(responseHtml).find(".searchResult").html()
						);
					
					//-----------------------------------------
					//매개변수 responseHtml로 들어온 검색 결과물 html 소스 문자열에서
					//class=boardListAllCnt를 가진 태그 내부의 [총 개수 문자열]을 얻어서
					//아래 현 화면의 html 소스 중에 class=boardListAllCnt를 가진 태그 내부에 덮어씌우기
					//-----------------------------------------
					//var text = $(responseHtml).find(".count").text();
					//$(".count").text(text);
					$(".boardListAllCnt").text(
						 $(responseHtml).find(".boardListAllCnt").text()
						);
					
					//-----------------------------------------
					//매개변수 responseHtml로 들어온 검색 결과물 html 소스 문자열에서
					//class=pageNo를 가진 태그 내부의 [검색 결과물 html 소스]를 얻어서
					//아래 현 화면의 html 소스 중에 class=pageNo를 가진 태그 내부에 덮어씌우기
					//-----------------------------------------
					$(".pageNo").html($(responseHtml).find(".pageNo").html())
					
				}
				,error: function(){
					alert("서버 접속 실패");
				}
			});
		}
		
		// **********************************************************
		// 페이지 번호를 클릭하면 호출되는 함수 선언
		// **********************************************************
		function search_with_changePageNo( selectPageNo ){
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
				searchAll();
			})
		})
	</script>

</head>

<!-- 객체가 소유하고 있는 메소드를 호출할때는 무조건 대소문자 구분해서 적어줘야함!! keyCode -->
<body onKeydown="if(event.keyCode==13){search();}">
	<center>
		<!-- ******************************************** -->
		<!-- 자바변수 선언하고 검색 화면 구현에 필요한 데이터 저장하기-->
		<!-- ******************************************** -->
		<%
			List<Map<String,String>> boardList = (List<Map<String,String>>)request.getAttribute("boardList");
			int boardListAllCnt = (Integer)request.getAttribute("boardListAllCnt");	
		
			int selectPageNo = (Integer)request.getAttribute("selectPageNo");
			int last_pageNo = (Integer)request.getAttribute("last_pageNo");
			int min_pageNo = (Integer)request.getAttribute("min_pageNo");
			int max_pageNo = (Integer)request.getAttribute("max_pageNo");
			
			int start_serial_no = (Integer)request.getAttribute("start_serial_no");
		
		%>
		<!-- ******************************************** -->
		<!-- [게시판 검색 조건 입력 양식]을 내포한 form태그 선언
		<!-- ******************************************** -->
		<form name="boardListForm" method="post" onSubmit="return false">
			[키워드] : <input type="text" name="keyword1" class="keyword1">
			<!-- <input type="text" name="keyword1" class="keyword1" onKeydown="if(event.keyCode==13){search();}"> -->
			
			<input type="hidden" name="selectPageNo" class="selectPageNo" value="1">			
			<select name="rowCntPerPage" class="rowCntPerPage">
				<option value="10">10
				<option value="15">15
				<option value="20">20
				<option value="25">25
				<option value="30">30
				<!-- <option value="<%=boardListAllCnt%>">모두 -->
			</select>행 보기
			
			<input type="button" value=" 검색 " class="boardSearch" >&nbsp;
			<input type="button" value="모두검색" class="boardSearchAll" >&nbsp;
			
			<!-- hidden 태그는 서버에 데이터를 보낼 때 사용함 -->
			
			<a href = "javascript:goBoardRegForm();">[새글쓰기]</a>	
		</form>
		<div style="height:5px"></div>
		
				<!-- ============================================ -->
		<!-- 페이지 번호 출력 -->
		<!-- ============================================ -->
		<div class="pageNo">
		<%			
			if(boardListAllCnt>0){
				//방법 1 : 이전/다음 => 10 단위로 페이지가 달라짐
				/*
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
				*/
				
				//방법 2 : 이전/다음 => 1단위로 페이지가 달라짐
				/*
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
				*/
				//방법 3 : 처음/이전/다음/끝 다 보이되 클릭 막기
				if(selectPageNo>1){
					out.print("<span style='cursor:pointer;' onClick='search_with_changePageNo(1);'>[처음]</span> ");
					out.print("<span style='cursor:pointer;' onClick='search_with_changePageNo("+(selectPageNo-1)+");'>[이전]</span> ");
					out.print("&nbsp;&nbsp;");
				}else{
					out.print("<span>[처음]</span> ");
					out.print("<span>[이전]</span> ");
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
					out.print("<span>[다음]</span> ");
					out.print("<span>[끝]</span> ");
					out.print("&nbsp;&nbsp;");
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
						
						out.println("<tr style='cursor:pointer;' onClick='goBoardContentForm("+b_no+")'><td>"
						+(serialNo2--)+"<td>"+xxx+subject+"<td>"+writer+"<td>"+readcount+"<td>"+reg_date);
						//serialNo1++
					}	
				}
			%>
			</table>
		</div>
		
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




<!-- 

		페이징 처리
		mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
		공식 => [페이지 당 보여줄 행의 개수], [선택페이지번호], [총개수]로
		        검색 결과물의 [시작행 번호]과 검색 결과물의 [끝행 번호]를 구하는 공식
				단 변수는 아래와 같다.
		------------------------------------------------------------------
					totCnt        => 검색된 총 결과물 개수
					rowCntPerPage => 페이지 당 보여줄 행의 개수
					selectPageNo  => 선택한 페이지 번호
					beginRowNo    => 검색된 총 결과물에서 가져올 범위의 시작행
					endRowNo      => 검색된 총 결과물에서 가져올 범위의 끝행
		mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
		if( totCnt>0){
			int endRowNo = selectPageNo * rowCntPerPage;
			int beginRowNo = endRowNo - rowCntPerPage + 1;
			if( endRowNo>totCnt ){
				endRowNo = totCnt;
			}
		}
		---------------------------------
		if( totCnt>0){
			int beginRowNo = selectPageNo * rowCntPerPage - rowCntPerPage + 1;
			int endRowNo = beginRowNo + rowCntPerPage -1;
			if( endRowNo>totCnt ){
				endRowNo = totCnt;
			}
		}


		mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
		공식 => [총개수], [페이지 당 보여줄 행의 개수],[선택된페이지번호]
		        [한 화면에 보여지는 페이지 번호 개수]로
		
		        [현재 화면에 보여지는 페이지 번호의 최소 번호]과 
		        [현재 화면에 보여지는 페이지 번호의 최대 번호]를 구하는 공식
				단 변수는 아래와 같다.
		------------------------------------------------------------------
					totCnt            => 검색된 총 결과물 개수
					rowCntPerPage => 페이지 당 보여줄 행의 개수
					selectPageNo      => 선택한 페이지 번호
					pageNoCntPerPage  => 페이지 당 보여줄 페이지 번호의 개수
					pageMinNo  => 현재 화면에 보여지는 페이지 번호의 최소 페이지 번호
					pageMaxNo  => 현재 화면에 보여지는 페이지 번호의 최대 페이지 번호
		mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
		if( totCnt>0 ) {
			int last_pageNo = totCnt/rowCntPerPage;
				if( totCnt%rowCntPerPage>0 ) { last_pageNo++; }
			int min_pageNo = ((selectPageNo-1)/pageNoCntPerPage)*pageNoCntPerPage+1;
			int max_pageNo = min_pageNo+pageNoCntPerPage-1;
				if( max_pageNo>last_pageNo ){
					max_pageNo = last_pageNo ;
				}
		}

 -->