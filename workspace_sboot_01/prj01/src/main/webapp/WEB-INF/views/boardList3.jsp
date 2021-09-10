<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


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

		var keyword2 = $(".keyword2").val();
		if(keyword2==null||keyword2.split(" ").join("")==""){
			//alert("키워드가 비어있어 검색하지 않습니다.");
			$(".keyword2").val("");
			//$(".keyword1").focus();
			//return;
		}
		$(".keyword2").val($.trim(keyword2));
		searchExe();
	}

	function searchAll(){
		$(".keyword1").val("");
		$(".keyword2").val("");
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
				changeBgColor();
				reg_date_sort();

				var sort = $(".sort").val();

				if(sort=="reg_date asc"){
					$(".reg_date").append("▲")
				}else if(sort=="reg_date desc"){
					$(".reg_date").append("▼")
				}
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

	function reg_date_sort(){
		$(".reg_date").css("cursor","pointer");
		$(".reg_date").click(function(){
			var thisObj = $(this);
			var txt = thisObj.text();
			
			if(txt.indexOf("▲")>=0){
				$(".sort").val("");
			}else if(txt.indexOf("▼")>=0){
				$(".sort").val("reg_date asc");
			}else{
				$(".sort").val("reg_date desc");
			}
					
			search();
		});
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
		
		changeBgColor();
		reg_date_sort();
	})
		
</script>
</head>
<body onKeydown="if(event.keyCode==13){search();}">
	<div class="logout"></div>
	<center>
	<form name="boardListForm" method="post" onSubmit="return false">
		[키워드] : <input type="text" name="keyword1" class="keyword1">
		<select name="andOr">
			<option value="and">and
			<option value="or">or
		</select>
		<input type="text" name="keyword2" class="keyword2">
		<div style="height:5px"></div>
		
		<input type="checkbox" name="dayList" class="dayList" value="어제">어제
		<input type="checkbox" name="dayList" class="dayList" value="오늘">오늘
		<input type="checkbox" name="dayList" class="dayList" value="일주일내">일주일내
		<input type="checkbox" name="dayList" class="dayList" value="30일내">30일내
		<div style="height:5px"></div>
		
		<select name="rowCntPerPage" class="rowCntPerPage">
			<option value="10">10
			<option value="15">15
			<option value="20" selected>20
			<option value="25">25
			<option value="30">30
		</select>행 보기
		
		<input type="hidden" name="selectPageNo" class="selectPageNo" value="1">
		<input type="hidden" name="sort" class="sort" value="">
		
		<input type="button" value=" 검색 " class="boardSearch">
		<input type="button" value="모두검색" class="boardSearchAll">
	
	</form>
		<a href="javascript:goBoardRegForm()">[새글쓰기]</a>
		<div style="height:5px"></div>
		
		<div class="pageNo">
			<c:if test="${requestScope.boardListAllCnt>0}">
				<c:if test="${pagingNos.selectPageNo>1}">
					<span style='cursor:pointer;' onClick='search_with_changePageNo(1);'>[처음]</span> 
					<span style='cursor:pointer;' onClick='search_with_changePageNo(${pagingNos.selectPageNo-1});'>[이전]</span>
				</c:if>
				<c:if test="${pagingNos.selectPageNo<=1}">
					<span>[처음]</span> 
					<span>[이전]</span> 
				</c:if>
				
				<c:forEach var="no" begin="${pagingNos.min_pageNo}" end="${pagingNos.max_pageNo}" step="1">
					<c:if test="${no==pagingNos.selectPageNo}">
						<span><b>${no}</b></span>
					</c:if>
					<c:if test="${no!=pagingNos.selectPageNo}">
						<span style='cursor:pointer;' onClick='search_with_changePageNo(${no});'>[${no}]</span>
					</c:if>
				</c:forEach>
				
				<c:if test="${pagingNos.selectPageNo<pagingNos.last_pageNo}">
					<span style='cursor:pointer;' onClick='search_with_changePageNo(${pagingNos.selectPageNo+1});'>[다음]</span> 
					<span style='cursor:pointer;' onClick='search_with_changePageNo(${pagingNos.last_pageNo});'>[끝]</span>
				</c:if>
				<c:if test="${pagingNos.selectPageNo>=pagingNos.last_pageNo}">
					<span>[다음]</span> 
					<span>[끝]</span> 
				</c:if>
			</c:if>
		</div>
		
		
		<div class="boardListAllCnt">총  ${requestScope.boardListAllCnt}개</div>
		
		<div class="searchResult">
		<table border=1  class="tbcss0">
			<tr><th>번호<th>제목<th>작성자<th>조회수
			<th><span class="reg_date">등록일</span>
			<c:if test="${requestScope.boardList!=null}">
				<c:forEach var="board" items="${requestScope.boardList}" varStatus="loopTagStatus">
					<tr style='cursor:pointer;' onClick='goBoardContentForm(${board.b_no})'> 
					<td>${boardListAllCnt-pagingNos.rowCntPerPage*(pagingNos.selectPageNo-1)-loopTagStatus.index}
					<%-- ${requestScope.start_serial_no+loopTagStatus.index} --%>
					<td>
					<c:if test="${board.print_level>0}">
						<c:forEach begin="1" end="${board.print_level}">
							&nbsp&nbsp&nbsp&nbsp
						</c:forEach>
						ㄴ
					</c:if>
					${board.subject}
					<td>${board.writer}
					<td>${board.readcount}
					<td>${board.reg_date}
				</c:forEach>
			</c:if>
		</table>
		</div>
		
		<form name="boardContentForm" method="post" action="boardContentForm.do">
			<input type="hidden" name="b_no">
		</form>
	</center>
</body>
</html>