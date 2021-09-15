<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@include file="common.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script>

	function goEmpList(){
		location.replace('/empList.do');
	}
	
	function goCusRegForm(){
		location.replace('/cusRegForm.do');
	}
	
	function goCusUpDelForm(cus_no){
		$("[name=cusUpDelForm] [name=cus_no]").val(cus_no);
		document.cusUpDelForm.submit();
	}

	
	function search(){
		
		var keyword1= $(".keyword1").val();
		
		if(keyword1==null||keyword1.split(" ").join("")==""){
			//alert("키워드가 비어있어 검색하지 않습니다.");
			keyword1= "";
			//$(".keyword1").focus();
			//return;
			//=> 함수 중단하지 않고 DB연동 바로 하게 함
		}
		
		$(".keyword1").val( $.trim(keyword1) );


		searchExe();
	}
	
	function searchAll(){
		$(".keyword1").val("");
		
		searchExe();
	}
	
	function searchExe(){
		$.ajax({
			url: "/cusList.do"
			, type :"post"
			, data: $("[name=cusListForm]").serialize()
			, success: function(responseHtml){
				$(".cusListAllCnt").text(
					 $(responseHtml).find(".cusListAllCnt").text()
					);
				$(".searchResult").html(
						 $(responseHtml).find(".searchResult").html()
						);
				$(".pageNo").html($(responseHtml).find(".pageNo").html())
				
				
				headerSort();
				var sortHeadName = $(".sortHeadName").val();
				var sortAscDesc = $(".sortAscDesc").val();
				$("."+sortHeadName).siblings().each(function(){
					var txt = $(this).text();
					txt=$.trim(txt);
					txt = txt.replace("▲","");
					txt = txt.replace("▼","");
					$(this).text(txt);
				})
				if(sortAscDesc=="asc"){
					$("."+sortHeadName).append("▲")
				}else if(sortAscDesc=="desc"){
					$("."+sortHeadName).append("▼")
				}
			}
			,error: function(){
				alert("서버 접속 실패");
			}
		});
	}
	
	function search_with_changePageNo(selectPageNo){
		$("[name=cusListForm]").find(".selectPageNo").val(selectPageNo);
		//search 함수 호출하기
		search();
	}



	function headerSort(){
		var thObjs = $(".customerList tr").find("th:gt(1)");
		thObjs.css("cursor", "pointer");
		thObjs.click(function(){
			var thisThObj = $(this);
			var txt = thisThObj.text();
			if(txt.indexOf("▲")>=0){
				$(".sortHeadName").val("");
				$(".sortAscDesc").val("");
			}else if(txt.indexOf("▼")>=0){
				$(".sortHeadName").val(thisThObj.attr("class"));
				$(".sortAscDesc").val("asc");
			}else{
				$(".sortHeadName").val(thisThObj.attr("class"));
				$(".sortAscDesc").val("desc");
			}
			search();
		})
		
	}
	

	$(document).ready(function(){
		$(".rowCntPerPage").change(function(){
			$(".selectPageNo").val("1");
			search();
		})
		
		$(".cusSearch").click(function(){
			search();
		})
		
		$(".cusSearchAll").click(function(){
			$(".ageDecade").prop("checked",false);

			$(".sortHeadName").val("");
			$(".sortAscDesc").val("");
			
			searchAll();
		})
		
		$(".ageDecade").click(function(){
			search();
		})
		headerSort();
	})
	
</script>
</head>
<body onKeydown="if(event.keyCode==13){search();}">
	<center>
	<form name="cusListForm" method="post" onSubmit="return false">
		<table border=1 cellpadding=5 >
			<caption><b>[고객검색]</b></caption>
			<tr>
				<td bgColor="lightgray">키워드
				<td> <input type="text" name="keyword1" class="keyword1">
			</tr>
			<tr>
				<td bgColor="lightgray">연령대
				<td> 
				<c:forEach var="list" items="${ageDecadeList}" varStatus="loopTagStatus">
					<input type="checkbox" name="ageDecade" class="ageDecade" value="${list.ageDecade}">${list.ageDecade}0대
				</c:forEach>
			</tr>
			
		</table>
		<div style="height:10px"></div>
			<select name="rowCntPerPage" class="rowCntPerPage">
				<option value="10" selected>10
				<option value="15">15
				<option value="20">20
			</select>행 보기
		
		<input type="hidden" name="selectPageNo" class="selectPageNo" value="1">	
		
		<input type="button" value="검색" class=cusSearch>&nbsp;
		<input type="button" value="모두검색" class="cusSearchAll">
		<a href = "javascript:goCusRegForm();">[고객등록]</a>	
		
		<input type="hidden" name="sortHeadName" class="sortHeadName" value="">	
		<input type="hidden" name="sortAscDesc" class="sortAscDesc" value="">	
	</form>
	<div class="cusListAllCnt">총 ${cusListAllCnt}개</div>

		<div class="pageNo">
			<c:if test="${cusListAllCnt>0}">
				<c:if test="${pagingNos.selectPageNo>1}">
					<span style='cursor:pointer;' onClick='search_with_changePageNo(1);'>[처음]</span> 
					<span style='cursor:pointer;' onClick='search_with_changePageNo(${requestScope.selectPageNo-1});'>[이전]</span>
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
		<div style="height:10px"></div>
		<div class="searchResult">
			<table border=1 class="customerList" width=500px>
				<tr bgColor="gray">
				<th>번호<th>고객번호<th class="cus_name">고객명<th class="tel_num">전화번호<th class="age">나이<th class="emp_name">담당직원명
				
				<c:if test="${requestScope.cusList!=null}">
				
					<c:forEach var="cus" items="${requestScope.cusList}" varStatus="loopTagStatus">
						<tr 
							bgColor="${loopTagStatus.index%2==0?'white':'lightgray'}" 
							style="cursor:pointer;"  onclick='goCusUpDelForm(${cus.cus_no})'>
							
							<td> <!-- 역순번호 출력 -->
							<!-- ${requestScope.boardListAllCnt-board.RNUM+1} -->
							<!-- ${boardListAllCnt-requestScope.start_serial_no+1-loopTagStatus.index} -->
							${cusListAllCnt-pagingNos.rowCntPerPage*(pagingNos.selectPageNo-1)-loopTagStatus.index}
							
							<!-- 정순번호 출력 -->
							<!-- ${requestScope.start_serial_no+loopTagStatus.index} -->
							<!--${rowCntPerPage*(selectPageNo-1)+1+loopTagStatus.index} -->
							
							
							<td> ${cus.cus_no}
							<td> ${cus.cus_name}
							<td> ${cus.tel_num}
							<td> ${cus.age}세
							<td> ${cus.emp_name}
						</tr>
					</c:forEach> 
					
				</c:if>
				
			</table>
		</div>
		<div style="height:10px"></div>
		<a href = "javascript:goEmpList();">[직원검색]</a>
		
		<form name="cusUpDelForm" method="post" action="cusUpDelForm.do">
			<input type="hidden" name="cus_no">
			<!-- <input type="text" name="b_no"> -->	
		</form>
	</center>
</body>
</html>