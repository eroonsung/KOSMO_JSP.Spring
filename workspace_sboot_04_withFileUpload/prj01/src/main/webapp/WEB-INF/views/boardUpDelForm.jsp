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
<%@include file="common.jsp" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<head>
	<script>
		$(document).ready(function(){
			//$(".writer").val("${requestScope.boardDTO.writer}");
			//자스로 value값을 넣을 때 el로 꺼내온 값은 ""로 감싸야함
			$(".pwd").val("1234");
		})
		// **********************************************************
		// [게시판 등록 화면]에 입력된 데이터의 유효성 체크 함수 선언
		// **********************************************************
		function checkBoardUpDelForm(upDel){
			//alert(upDel);
			//return;
			//-------------------------------------------------------
			//매개변수로 들어온 upDel에 "up"이 저장되어있으면
			// 즉 수정버튼을 눌렀으면 각 입력양식의 유효성 체크하고 수정여부 물어보기
			//-------------------------------------------------------
			if(upDel=="up"){
				//프로그램 진행상황 확인을 위한 테스트용 태그 만들기
				//$(".xxx").remove();
				//$("body").append("<div class=xxx>수정</div>"); 
				if(confirm("정말 수정하시겠습니까?")==false){return};
				//name=upDel hidden 태그 안에 'up'넣기
				$("[name='upDel']").val("up");
			}
			//-------------------------------------------------------
			//매개변수로 들어온 upDel에 "del"이 저장되어있으면
			// 즉 삭제버튼을 눌렀으면 암호 확인하고 삭제 여부를 물어보기
			//-------------------------------------------------------
			else if(upDel=="del"){
				if(confirm("정말 삭제하시겠습니까?")==false){return};
				//name=upDel hidden 태그 안에 'del'넣기
				$("[name='upDel']").val("del");
			}

			//--------------------------------------------
			//form 태그에 파일업로드 전송 관련 설정하기
			//--------------------------------------------
			document.boardUpDelForm.enctype="multipart/form-data"; 
			
			//-------------------------------------------------------
			//현재 화면에서 페이지 이동 없이
			// 서버쪽 "/boardUpDelProc.do"로 접속해서 수정 또는 삭제하기
			//-------------------------------------------------------
			$.ajax({
				// 서버쪽 호출 URL 주소 지정
				url:"/boardUpDelProc.do"
				// form 태그 안의 입력양식 데이터 즉, 파라미터값을 보내는 방법 지정 
				, type: "post"
				// 웹서버로 보낼 파라미터명과 파라미터값을 설정
				//, data: $("[name=boardUpDelForm]").serialize()
				
				//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
				// 파일업로드를 위한 설정
				//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
				//-----------------------------------
				// 서버로 보내는 데이터에 파일이 있으므로
				// 전송하는 파라미터명, 파라미터값을 serialize를 하여 Query String으로 변경하지 않게 설정하기.
				// 서버로 보내는 데이터에 파일이 없으면  processData: false 을 생략한다.
				//-----------------------------------
				, processData:false
				//-----------------------------------
				// 서버로 보내는 데이터에 파일이 있으므로
				// 현재 form 태그에 설정된 enctype 속성값을 Content-Type 으로 사용하도록 설정하기.
				// 이게 없으면 현재 form 태그에 설정된 enctype 속성값과 관계없이 무조건 enctype 이 application/x-www-form-urlencoded 로 설정된다.
				// 서버로 보내는 데이터에 파일이 없으면  ,contentType: false  을 생략한다.
				//-----------------------------------
				, contentType:false
				//-----------------------------------
				// 서버로 보내는 데이터 설정하기
				// 서버로 보내는 데이터에 파일이 있으므로 FormData 객체 설정
				//-----------------------------------
				// boardRegForm이름을 가진 태그 중에 첫번째 놈
				, data: new FormData( $("[name=boardUpDelForm]").get(0) ) 
				//--------------------------------------------
				// 서버의 응답을 성공적으로 받았을 경우 실행할 익명함수 설정
				// 익명함수의 매개변수에는 서버가 보내온 html 소스가 문자열로 들어온다
				// 즉 응답 메시지 안의 html 소스가 문자열로써 익명함수의 매개변수로 들어온다.
				// 응답 메시지 안의 html 소스는 boardRegProc.jsp의 실행 결과물이다.
				//--------------------------------------------				
				, success: function (json) {
					//-----------------------------------------
					//JSON객체에서 유효성 체크 문자열 꺼내기
					//JSON객체에서 수정/삭제 성공 행의 개수 꺼내기
					//-----------------------------------------
					var boardUpDelCnt = json.boardUpDelCnt;
						boardUpDelCnt = parseInt(boardUpDelCnt,10);
					
					var msg = json.msg;
	
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
				//--------------------------------------------
				// 서버의 응답을 못 받았을 경우 실행할 익명함수 설정
				//--------------------------------------------		
				, error: function(){
					alert("서버 접속 실패");
				}
			})
		}

	</script>

</head>

<body>
	<center>
	<div class="logout"></div>
		<c:if test="${!empty boardDTO}">
		<!-- *************************************************** -->
		<!-- [로그인 정보 입력양식]을 내포한 form 태그 선언-->
		<!-- *************************************************** -->
		<form name="boardUpDelForm">
			<table border=1 cellpadding=5 class="tbcss2">
			<caption><b>게시판 수정/삭제</b></caption>
				<tr>
					<th bgcolor="${thBgColor}">이름</th>
					<td><input type="text" name="writer" class="writer" size="10" maxlength=10 value="${boardDTO.writer}"></td>
				</tr>
				<tr>
					<th bgcolor="${thBgColor}">제목</th>
					<td><input type="text" name="subject" class="subject" size=40 maxlength="30" value="${boardDTO.subject}"></td>
				</tr>
				<tr>
					<th bgcolor="${thBgColor}">이메일</th>
					<td><input type="text" name="email" class="email" size=40 maxlength="30" value="${boardDTO.email}"></td>
				</tr>
				<tr>
					<th bgcolor="${thBgColor}">내용</th>
					<td><textarea name="content" class="content" cols=50 rows=20>${boardDTO.content}</textarea></td>
				</tr>
				<tr>
					<th bgcolor="${thBgColor}">이미지</th>
					<td>
						<input type="file" name="img" class="img">
						<c:if test= "${!empty requestScope.boardDTO.pic}">
							<div style="height:3pt"></div>
							<img src="resources/img/${requestScope.boardDTO.pic}" width="30%"> 	
						</c:if>	
						<input type="checkbox" name="is_del" class="is_del" value="yes">삭제
					</td>
				</tr>
				<tr>
					<th bgcolor="${thBgColor}">비밀번호</th>
					<td><input type="password" name="pwd" class="pwd" size=10 maxlength="4"></td>
				</tr>
			</table>
			<div style="height:6px"></div>
			<input type="button" value="수정" onClick="checkBoardUpDelForm('up');">
			<input type="reset" value="삭제" onClick="checkBoardUpDelForm('del');">
			<input type="button" value="목록보기" onclick="location.replace('/boardList.do');">
			
			<!-- *************************************************** -->
			<!-- 어떤 게시글인지 확인하기 위해 b_no(PK값)을 가져옴 -->
			<!-- *************************************************** -->
			<input type="hidden" name="b_no" value="${boardDTO.b_no}">
			<!-- *************************************************** -->
			<!-- 수정인지 삭제인지 알 수 있는 태그 추가 -->
			<!-- *************************************************** -->
			<input type="hidden" name="upDel">
		</form>
		</c:if>
		<c:if test="${empty boardDTO}">
			<script>alert('삭제된 글입니다.'); location.replace('/boardList.do')</script>
		</c:if>
	</center>
</body>

</html>