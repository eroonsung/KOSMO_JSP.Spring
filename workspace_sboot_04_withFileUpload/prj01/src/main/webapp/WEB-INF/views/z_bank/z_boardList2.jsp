<!-- ********************************************************************************** -->
<!-- JSP 기술의 한 종류인 [Page Directive]를 이용하여 현 JSP 페이지 처리 방식 선언하기 -->
<!-- ********************************************************************************** -->
<!-- 현재 이 JSP 페이지 실행 후 생성되는 문서는 HTML 이고, 이 문서 안의 데이터는 UTF-8 방식으로 인코딩한다라고 설정함 -->
<!-- 현재 이 JSP 페이지는 UTF-8 방식으로 인코딩한다-->
<!-- UTF-8 인코딩 방식은 한글을 포함한 전 세계 모든 문자열을 부호화할 수 있는 방법이다.-->
<!-- 모든 JSP 페이지, 상단에 무조건 들어가는 코드 -->
<!-- UTF-8 인코딩방식을 가지고 있는 text중의 하나인 html 파일로 처리 -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

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
		// **********************************************************
		//새글쓰기 버튼을 클릭할 경우 호출되는 함수
		// [게시판 새글쓰기 화면]으로 이동하는 함수 선언
		// **********************************************************
		function goBoardRegForm(){
			//Location 객체의 replace 메소드 호출로
			// [새글쓰기 화면]으로 이동하기
				//-> form태그를 이용한 웹서버 접속방법이 아니므로
				//파라미터값을 가지고 가려면 url주소 뒷부분에 ?파명=파값을 붙여야만 한다.
				//즉 get방식으로 웹서버에 접속하는 방법(보안성이 필요없는 데이터)
			// pk값이 필요 없기 때문에 location.replace()방법 사용
			location.replace('/boardRegForm.do')
		}
		
		// **********************************************************
		//게시판 목록에서 행을 클릭할 경우 호출되는 함수
		// [게시판 상세보기 화면]으로 이동하는 함수 선언
		// 매개변수로 클릭한 행의 pk값 즉 b_no 컬럼값이 전달됨
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
			// form태그를 이용한 웹서버 접속방법 -> 파라미터값을 숨겨서 웹서버에 접속할 수 있음
			//-----------------------------------------
			document.boardContentForm.submit();
		}

		// **********************************************************
		//검색버튼과 페이지 번호를 누르면 호출되는 함수
		// 키워드에 입력한 데이터를 가진 [게시판 목록]을 검색해서 그 결과를 보여주는 함수 선언
		// **********************************************************
		function search(){
			
			//-----------------------------------------
			//입력한 키워드 얻어오기
			//-----------------------------------------
			var keyword1= $(".keyword1").val();
			var keyword2= $(".keyword2").val();
			
			//-----------------------------------------
			//만약 키워드가 비어있거나 공백으로 구성되어 있으면 경고하고 비우고 함수 중단하기
			//-----------------------------------------
			if(keyword1==null||keyword1.split(" ").join("")==""){
				//alert("키워드가 비어있어 검색하지 않습니다.");
				keyword1= "";
				//$(".keyword1").focus();
				//return;
				//=> 함수 중단하지 않고 DB연동 바로 하게 함
			}
			
			if(keyword2==null||keyword2.split(" ").join("")==""){
				//alert("키워드가 비어있어 검색하지 않습니다.");
				keyword2= "";
				//$(".keyword2").focus();
				//return;
				//=> 함수 중단하지 않고 DB연동 바로 하게 함
			}
			
			//-----------------------------------------
			//입력한 키워드의 앞 뒤 공백 제거하고 입력양식에 다시 넣어주기
			//-----------------------------------------
			$(".keyword1").val( $.trim(keyword1) );
			$(".keyword2").val( $.trim(keyword2) );
			
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
			//-----------------------------------------
			//키워드 입력 양식에 "" 넣어주기
			//-----------------------------------------
			$(".keyword1").val("");
			$(".keyword2").val("");
			// andOr는 항상 들어가기 때문에 ""로 풀지 말것!
			
			
			//-----------------------------------------
			//비동기 방식으로 웹서버에 접속하는 searchExe() 함수 호출하기
			//-----------------------------------------
			searchExe();
		}

		function searchExe(){
			//-----------------------------------------
			//현재 화면에서 페이지 이동 없이(=비동기방식으로)
			//서버쪽 /boardList.do로 접속하여 키워드를 만족하는
			// 검색결과물을 응답받아 현 화면에 반영하기
			//-----------------------------------------
			//비동기 방식을 사용하는 이유
				//=> 현재 화면에서 일부분만 바꾸기 위해서
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
										
					//-----------------------------------------
					//changeBgColor() 함수 호출로 짝수행, 홀수행, 헤더에 배경색 주기
					//-----------------------------------------
					changeBgColor();
					//-----------------------------------------
					//reg_date_sort() 함수 호출로 등록일 클릭했을 때 발생하는 일 설정하기
					//-----------------------------------------
					reg_date_sort();
					
					//-----------------------------------------
					//비동기방식으로 다시 불러온 페이지의 등록일 헤더 옆에 "▲","▼" 넣기
					//-----------------------------------------
					// class=sort를 가진 태그의 현재 value값 얻기
					var sort= $(".sort").val();
					
					//만약에 class=sort를 가진 태그의 value값이 "reg_date asc"이면
					//class=reg_date를 가진 태그의 내부 마지막에 ▲추가하기
					if(sort=="reg_date asc"){
						$(".reg_date").append("▲");
					//만약에 class=sort를 가진 태그의 value값이 "reg_date desc"이면
					//class=reg_date를 가진 태그의 내부 마지막에 ▼추가하기
					}else if(sort=="reg_date desc"){
						$(".reg_date").append("▼");
					}
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
			//class=selectPageNo를 가진 입력양식에 클릭한(선택한) 페이지 번호를 value값으로 삽입하기
			//왜? 서버로 보내기 위해서
			$("[name=boardListForm]").find(".selectPageNo").val(selectPageNo);
			//search 함수 호출하기
			search();
		}

		// **********************************************************
		// 헤더 중에 등록일 문자열을 클릭했을 때 일어나는 일을 설정하는 함수 선언
		// **********************************************************
		function reg_date_sort(){
			//-----------------------------------------
			//class=reg_date를 가진 태그에 마우스를 대면 손모양 보이게 하기
			//-----------------------------------------
			$(".reg_date").css("cursor","pointer");
			//-----------------------------------------
			//class=reg_date를 가진 태그를 클릭하면 hidden 태그에 정렬 문자열 삽입하기
			//-----------------------------------------
			$(".reg_date").click(function(){
				// JQuery에서 'this'는 선택자
				// 클릭한 태그를 관리하는 JQuery 객체의 메위주 얻기
				var thisObj = $(this);
				// 클릭한 태그가 끌어안고 있는 문자열 얻기
				var txt = thisObj.text();
				// 문자열에서 앞 뒤 공백 제거하고 다시 얻기
				txt= $.trim(txt);

				//만약 문자열에 "▲"가 있으면 
				if(txt.indexOf("▲")>=0){
					//class=sort를 가진 태그에 value값으로 ""를 삽입하기
					$(".sort").val("");
				//만약 문자열에 "▼"가 있으면 	
				}else if(txt.indexOf("▼")>=0){
					//class=sort를 가진 태그에 value값으로 "reg_date asc"를 삽입하기
					$(".sort").val("reg_date asc");
				//만약 문자열에 "▲","▼"가 없으면 
				//else if(txt.indexOf("▲")<0&&txt.indexOf("▼")<0)	
				}else{
					//class=sort를 가진 태그에 value값으로 "reg_date desc"를 삽입하기
					$(".sort").val("reg_date desc");
					
				}
				search();
			})
		}

		// **********************************************************
		// body태그 안의 내용을 모두 읽어들인 후 실행할 자스 코드 설정하기
		// **********************************************************
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
			
			//-----------------------------------------
			//changeBgColor() 함수 호출로 짝수행, 홀수행, 헤더에 배경색 주기
			//-----------------------------------------
			changeBgColor();
			//-----------------------------------------
			//reg_date_sort() 함수 호출로 등록일 클릭했을 때 발생하는 일 설정하기
			//-----------------------------------------
			reg_date_sort();

		})
	</script>

</head>

<!-- ------------------------------------------------------------ -->
<!-- body 태그 선언하기 -->
<!-- 객체가 소유하고 있는 메소드를 호출할때는 무조건 대소문자 구분해서 적어줘야함!! keyCode -->
<!--  body태그에 keyDown 이벤트를 걸면 특정 태그에 가는 포커스 상관없이 무조건 화면에서 키보드를 누르면 -->
<!--  자스코드를 실행하게 할 수 있음 -->
<!-- ------------------------------------------------------------ -->
<body onKeydown="if(event.keyCode==13){search();}">
	<center>
	<div class="logout"></div>
		
		<!-- ******************************************** -->
		<!-- [게시판 검색 조건 입력 양식]을 내포한 form태그 선언
		<!-- ******************************************** -->
		<form name="boardListForm" method="post" onSubmit="return false">
			<!-- ---------------------------------------------- -->
			<!-- 키워드 데이터를 저장하는 text 입력양식 선언-->
			<!-- ---------------------------------------------- -->
			[키워드] : <input type="text" name="keyword1" class="keyword1">
			<select name="andOr">
				<option value="or"> or	
				<option value="and"> and
			</select>
			<input type="text" name="keyword2" class="keyword2">
			<div style="height:5px"></div>
			<!-- <input type="text" name="keyword1" class="keyword1" onKeydown="if(event.keyCode==13){search();}"> -->
			
			<input type="checkbox" name="dayList" class="dayList" value="어제">어제
			<input type="checkbox" name="dayList" class="dayList" value="오늘">오늘
			<input type="checkbox" name="dayList" class="dayList" value="일주일내">일주일내
			<input type="checkbox" name="dayList" class="dayList" value="30일내">30일내
		

			<div style="height:5px"></div>
			<input type="button" value=" 검색 " class="boardSearch" >&nbsp;
			<input type="button" value="모두검색" class="boardSearchAll" >&nbsp;
			
			<!-- hidden 태그는 서버에 데이터를 보낼 때 사용함 -->
			
			<a href = "javascript:goBoardRegForm();">[새글쓰기]</a>	
			
			<!-- 검색화면에 필수적인 아주 중요한 웹서버로 보낼 데이터-->
			<!-- 페이징 처리 관련 데이터 -->
			<!-- ---------------------------------------------- -->
			<!-- 클릭한 페이지번호를 저장할 hidden 입력양식 선언-->
			<!-- ---------------------------------------------- -->
			<input type="hidden" name="selectPageNo" class="selectPageNo" value="1">	
			
			<!-- ---------------------------------------------- -->
			<!-- 정렬기준을 저장할 hidden 입력양식 선언-->
			<!-- ---------------------------------------------- -->
			<input type="hidden" name="sort" class="sort" value="">	
		
		<div style="height:10px"></div>
		
		
		<!-- ============================================ -->
		<!-- 검색된 목록의 총 개수 출력하기 -->
		<!-- ============================================ -->
		<!-- EL을 사용하여 HttpServletRequest 객체에 -->
		<!-- setAttribute 메소드로 저장된 키값 "boardListAllCnt"로 저장된 데이터를 꺼내서 표현하기-->
			<%-- ${requestScope.키값} --%>
		<!-- EL : Expression Language -->
		<!-- <참고> EL은 JSP 페이지에서 사용 가능한 언어 -->
		<!-- 즉 EL은 JSP 기술의 한 종류 -->
		
		<!-- <div class="boardListAllCnt">총 <% // =boardListAllCnt%>개</div> -->
		<!-- <div class="boardListAllCnt">총 ${requestScope.boardListAllCnt}개</div> -->
		<span class="boardListAllCnt">총 ${boardListAllCnt}개</span>
			<!-- ---------------------------------------------- -->
			<!-- 한 화면에 보여줄 검색 결과물 행의 개수 관련 입력양식 선언 -->
			<!-- ---------------------------------------------- -->
			<select name="rowCntPerPage" class="rowCntPerPage">
				<option value="10">10
				<option value="15">15
				<option value="20" selected>20
				<option value="25">25
				<option value="30">30
			</select>행 보기
		</form>
		
		<!-- ============================================ -->
		<!-- 페이지 번호 출력 -->
		<!-- ============================================ -->
		<div class="pageNo">
			<%--
			********************************************************************
			JSTL 이란 커스텀태그 중에 C코어 태그 중에 if 조건문을 사용하여
			만약에 게시판 검색 개수가 0보다 크면
			---------------------------------------------------------------
				C코어 태그 중에 if 조건문 형식
				<c:if test="${EL조건식}">
	
				</c:> 
			********************************************************************
			--%>
			<c:if test="${requestScope.boardListAllCnt>0}">
					<!------------------------------------------------>
					<!--선택한 페이지 번호가 1보다 크면 [처음], [이전] 글씨 보이기. 단 클릭하면 함수 호출하도록 이벤트 걸기-->
					<!------------------------------------------------->
					<c:if test="${requestScope.selectPageNo>1}">
						<span style='cursor:pointer' onClick='search_with_changePageNo(1);'>[처음]</span>
						<span style='cursor:pointer' onClick='search_with_changePageNo(${requestScope.selectPageNo}-1);'>[이전]</span>
					</c:if>
					<!------------------------------------------------>
					<!-- 선택한 페이지 번호가 1면 [처음], [이전] 글씨 보이기. 단 클릭하면 함수 호출하는 이벤트 걸지 말기-->
					<!------------------------------------------------->
					<c:if test="${requestScope.selectPageNo<=1}">
						<span>[처음]</span>
						<span>[이전]</span>
					</c:if>

					<%--********************************************************************
					JSTL 이란 커스텀 태그 중에 C코어 태그 중에 forEach 반복문을 사용하여
					[최소 페이지번호] 부터 [최대 페이지 번호]를 표현하기
					********************************************************************
						----------------------------------------------------------------
						C코어 태그 중에 forEach 반복문 형식1
						----------------------------------------------------------------
							<c:forEach var="반복문안에서사용할지역변수" begin="시작번호" end="끝번호" step="증감정수">
								HTML 코딩
							</c:forEach> 
						----------------------------------------------------------------
						C코어 태그 중에 forEach 반복문 형식2
						----------------------------------------------------------------
							<c:forEach var="반복문안에서사용할지역변수" items="EL로표현되는ArrayList객체" varStatus="LoopTagStatus객체저장변수명">
								HTML 코딩
							</c:forEach> 
					********************************************************************--%>
					<c:forEach  var="no"  begin="${requestScope.min_pageNo}"  end="${requestScope.max_pageNo}" step="1"> 
						<!------------------------------------------------->
						<!-- 만약 출력되는 페이지번호와 선택한 페이지 번호가 일치하면 그냥 번호만 표현하기-->
						<!------------------------------------------------->
						<c:if test="${no==requestScope.selectPageNo}">
							<span><b>${no}</b></span>
						</c:if>
						<!------------------------------------------------->
						<!-- 만약 출력되는 페이지번호와 선택한 페이지 번호가 일치하지 않으면 클릭하면 함수호출하도록 클릭 이벤트 걸기-->
						<!------------------------------------------------->
						<c:if test="${no!=requestScope.selectPageNo}">
							<span style='cursor:pointer' onClick='search_with_changePageNo(${no});'>[${no}]</span>
						</c:if>
					</c:forEach>

					<!----------------------------------------------->
					<!-- 선택한 페이지 번호가 마지막 페이지 번호보다 작으면 [다음][마지막] 문자 표현하기-->
					<!-- 단 클릭하면 함수 호출하도록 클릭 이벤트 걸기-->
					<!------------------------------------------------->
					<c:if test="${requestScope.selectPageNo<requestScope.last_pageNo}">
						<span style='cursor:pointer' onClick='search_with_changePageNo(${requestScope.selectPageNo}+1);'>[다음]</span>
						<span style='cursor:pointer' onClick='search_with_changePageNo(${requestScope.last_pageNo});'>[마지막]</span>
					</c:if>
					<!----------------------------------------------->
					<!-- 선택한 페이지 번호가 마지막 페이지 번호보다 크거나 같으면 [다음][마지막] 문자만 표현하기-->
					<!-- 단 클릭하면 함수 호출하는 이벤트는 걸지 않기-->
					<!------------------------------------------------->
					<c:if test="${requestScope.selectPageNo>=requestScope.last_pageNo}">
						<span>[다음]</span>
						<span>[마지막]</span> 
					</c:if>
			</c:if>
		</div>
		<div style="height:10px"></div>
		
		<!-- ============================================ -->
		<!-- 검색 결과 출력하기 -->
		<!-- ============================================ -->		
		<div class="searchResult">
			<table border=1 class="tbcss0" cellpadding="3" width=500 >
				<tr bgColor="gray">
				<th>번호<th>제목<th>작성자<th>조회수
					<th><span class="reg_date">등록일</span>
				
				<c:if test="${requestScope.boardList!=null}">
				
					<c:forEach var="board" items="${requestScope.boardList}" varStatus="loopTagStatus">
						<%-- <tr bgColor="${loopTagStatus.index%2==0?'white':'lightgray'}" 
							style="cursor:pointer;" onclick='goBoardContentForm(${board.b_no})' > --%>
							<tr	style="cursor:pointer;" onclick='goBoardContentForm(${board.b_no})' >
							<td align="center"> <!-- 역순번호 출력 -->
							<!-- ${requestScope.boardListAllCnt-board.RNUM+1} -->
							<!-- ${boardListAllCnt-requestScope.start_serial_no+1-loopTagStatus.index} -->
							${boardListAllCnt-rowCntPerPage*(selectPageNo-1)-loopTagStatus.index}
							
							<!-- 정순번호 출력 -->
							<!-- ${requestScope.start_serial_no+loopTagStatus.index} -->
							<!--${rowCntPerPage*(selectPageNo-1)+1+loopTagStatus.index} -->
							
							
							<td width="35%"> <!-- ㄴ으로 표현되는 들여쓰기 -->
								<c:if test="${board.print_level>0}">
									<c:forEach begin="1" end="${board.print_level}">
										&nbsp&nbsp&nbsp&nbsp
									</c:forEach>
									ㄴ
								</c:if>
								${board.subject}
								
							<td align="center"> ${board.writer}
							<td align="center"> ${board.readcount}
							<td align="center"> ${board.reg_date}
						</tr>
					</c:forEach> 
					
				</c:if>
				
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