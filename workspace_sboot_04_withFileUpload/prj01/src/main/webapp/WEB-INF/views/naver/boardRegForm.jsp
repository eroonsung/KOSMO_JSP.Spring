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
<%@include file="common.jsp" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<head>
	<script>
		$(document).ready(function(){
			$(".writer").val("사오정")
			$(".subject").val("빨리 취업 하세요.")
			$(".email").val("abc@naver.com")
			$(".content").val("어쩌구 저쩌구")
			$(".pwd").val("1234")
		})
		// **********************************************************
		// [게시판 등록 화면]에 입력된 데이터의 유효성 체크를 자스로 하지 않고
		// 비동기 방식으로 서버에 "/boardRegProc.do"로 접속하는 함수 선언
		// **********************************************************
		function checkBoardRegForm(){
			//--------------------------------------------
			// "정말 등록하시겠습니까?" 라고 물어보기
			//--------------------------------------------
			if(confirm("정말 등록하시겠습니까?")==false) {return;}

			//alert( $("[name=boardRegForm]").serialize())
			//return;
			
			//--------------------------------------------
			//form 태그에 파일업로드 전송 관련 설정하기
			//--------------------------------------------
			document.boardRegForm.enctype="multipart/form-data"; 
			
			//--------------------------------------------
			//현재 화면에서 페이지 이동 없이(=비동기 방식으로)
			//서버쪽 boardRegProc.do로 접속하여 게시판 글쓰기를 하고 
			//글쓰기 성공여부를 알려주기
			//--------------------------------------------
			$.ajax({
				// 서버쪽 호출 URL 주소 지정
				url:"/${naverPath}boardRegProc.do"
				// form 태그 안의 입력양식 데이터 즉, 파라미터값을 보내는 방법 지정 
				, type: "post"
				// 서버로 보낼 파라미터명과 파라미터값을 설정
				
				//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
				// 파일업로드를 위한 설정
				//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
				//-----------------------------------
				// 서버로 보내는 데이터에 파일이 있으므로
				// 전송하는 파라미터명, 파라미터값을 serialize를 하여 Query String으로 변경하지 않게 설정하기.
				// 서버로 보내는 데이터에 파일이 없으면  processData: false 을 생략한다.
				//-----------------------------------
				,processData:false
				//-----------------------------------
				// 서버로 보내는 데이터에 파일이 있으므로
				// 현재 form 태그에 설정된 enctype 속성값을 Content-Type 으로 사용하도록 설정하기.
				// 이게 없으면 현재 form 태그에 설정된 enctype 속성값과 관계없이 무조건 enctype 이 application/x-www-form-urlencoded 로 설정된다.
				// 서버로 보내는 데이터에 파일이 없으면  ,contentType: false  을 생략한다.
				//-----------------------------------
				,contentType:false
				//-----------------------------------
				// 서버로 보내는 데이터 설정하기
				// 서버로 보내는 데이터에 파일이 있으므로 FormData 객체 설정
				//-----------------------------------
				// boardRegForm이름을 가진 태그 중에 첫번째 놈
				, data: new FormData( $("[name=boardRegForm]").get(0) ) 

					//name값과 value값은 문자열만 전송 됨(파일업로드 불가)
					//, data: $("[name=boardRegForm]").serialize()
				//--------------------------------------------
				// 서버의 응답을 성공적으로 받았을 경우 실행할 익명함수 설정
				// 익명함수의 매개변수에는 서버가 보내온 Map<String,String>을 JSON 객체로 바뀌어 들어온다.
				//--------------------------------------------				
				, success: function (json) {
					//--------------------------------------------
					//매개변수로 들어온 JSON 객체에서 게시판 입력 성공행의 개수를 꺼내서 지역변수 boardRegCnt에 저장하기
					//매개변수로 들어온 JSON 객체에서 유효성 체크 결과 문자열을 지역변수 msg에 저장하기
					//--------------------------------------------
					var boardRegCnt = json.boardRegCnt;
						boardRegCnt = parseInt(boardRegCnt,10);
					var msg = json.msg;

					//--------------------------------------------
					//만약 유효성 체크 결과 문자열이 있으면 경고하고 함수 중단하기
					//--------------------------------------------
					if(msg!=null && msg.length>0){
						alert(msg);
						return;
					}
					var b_no = $("[name=boardRegForm] [name=b_no]").val();
					//=================================================
					//name=b_no의 value값이 0일 경우 (새글쓰기일 경우)
					//=================================================
					if(b_no=="0"){
						//--------------------------------------------
						//게시판 글 입력 성공 행의 개수가 1이면 즉 입력이 성공했으면
						//--------------------------------------------
						//if(boardRegCnt=="1"){
						if(boardRegCnt==1){
							alert("새글쓰기 성공");
							location.replace("/${naverPath}boardList.do");
						}
						//--------------------------------------------
						//그렇지 않으면, 즉 입력이 실패했으면
						//--------------------------------------------
						else{
							alert("새글쓰기 실패");
						}
					//=================================================
					//name=b_no의 value값이 0이 아닐 경우 (댓글쓰기일 경우)
					//=================================================
					}else{
						//--------------------------------------------
						//게시판 글 입력 성공 행의 개수가 1이면 즉 입력이 성공했으면
						//--------------------------------------------
						if(boardRegCnt==1){
							alert("댓글쓰기 성공");
							location.replace("/${naverPath}boardList.do");
						}
						//--------------------------------------------
						//그렇지 않으면, 즉 입력이 실패했으면
						//--------------------------------------------
						else{
							alert("댓글쓰기 실패");
						}
					}
				}
				//--------------------------------------------
				// 서버의 응답을 못 받았을 경우 실행할 익명함수 설정
				//--------------------------------------------		
				, error: function(){
					alert("서버 접속 실패");
				}
			});
		}

	</script>

</head>

<body>
	<center>
		<div class="logout"></div>
		<!-- *************************************************** -->
		<!-- [로그인 정보 입력양식]을 내포한 form 태그 선언-->
		<!-- *************************************************** -->
		<form name="boardRegForm" method="post" action="/${naverPath}boardRegProc.do" >
			<table border=1 cellpadding="${cellpadding}" class="tbcss2" width=500>
			<%-- 
				<c:if test="${empty param.b_no}">
					<caption><b>새글쓰기</b></caption>	
				</c:if>
				<c:if test="${!empty param.b_no}">
					<caption><b>댓글쓰기</b></caption>
				</c:if>
			 --%>
			 <c:choose>
			 	<c:when test="${empty param.b_no}">
			 		<caption><b>새글쓰기</b></caption>	
			 	</c:when>
			 	<c:otherwise>
			 		<caption><b>댓글쓰기</b></caption>
			 	</c:otherwise>
			 </c:choose>
			
				<tr>
					<th bgcolor="${thBgColor}">이름</th>
					<td><input type="text" name="writer" class="writer" size="10" maxlength=10></td>
				</tr>
				<tr>
					<th bgcolor="${thBgColor}">제목</th>
					<td><input type="text" name="subject" class="subject" size=40 maxlength="30"></td>
				</tr>
				<tr>
					<th bgcolor="${thBgColor}">이메일</th>
					<td><input type="text" name="email" class="email" size=40 maxlength="30"></td>
				</tr>
				<tr>
					<th bgcolor="${thBgColor}">내용</th>
					<td><textarea name="content" class="content" cols=50 rows=20></textarea></td>
				</tr>
				<tr>
					<th bgcolor="${thBgColor}">이미지</th>
					<td>
					<c:if test= "${!empty requestScope.boardDTO.pic}">
						<img src="resources/img/${requestScope.boardDTO.pic}" width="40%"> 
						<div style="height:3px"></div>
					</c:if>	
					<input type="file" name="img" class="img"></td>
				</tr>
				<tr>
					<th bgcolor="${thBgColor}">비밀번호</th>
					<td><input type="password" name="pwd" class="pwd" size=10 maxlength="4"></td>
				</tr>
			</table>
			<div style="height:6px"></div>
			<input type="button" value="저장" onClick="checkBoardRegForm();">
			<input type="reset" value="다시작성">
			<input type="button" value="목록보기" onclick="location.replace('/${naverPath}boardList.do')">
			
			<c:if test="${empty param.b_no}">
				<input type="hidden" name="b_no" value="0" >
			</c:if>
			<c:if test="${!empty param.b_no}">
				<input type="hidden" name="b_no" value="${param.b_no}" >
			</c:if>
			
		</form>
		[현재 게시판 총 개수 : ${requestScope.totCnt}개]
		<div>${sessionScope.msg}</div>
	</center>
</body>

</html>