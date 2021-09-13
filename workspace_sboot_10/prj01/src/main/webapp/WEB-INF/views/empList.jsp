<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@include file="common.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script>

	function goEmpRegForm(){
		location.replace('/empRegForm.do');
	}

	function goCusList(){
		location.replace('/cusList.do');
	}
	
	function goEmpUpDelForm(emp_no){
		$("[name=empUpDelForm] [name=emp_no]").val(emp_no);
		document.empUpDelForm.submit();
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

		var min_year = $(".min_year").val();
		var min_month = $(".min_month").val();
		var max_year = $(".max_year").val();
		var max_month = $(".max_month").val();
		
		if(min_year!=""&&min_month!=""&&max_year==""&&max_month==""){
			$(".max_year").val(${maxY});
			$(".max_month").val("12");
		}
		
		if(max_year!=""&&max_month!=""&&min_year==""&&min_month==""){
			$(".min_year").val(${minY});
			$(".min_month").val("01");
		}
		
		if(min_year!=""&&min_month!=""&&max_year!=""&&max_month!=""){
			if(min_year>max_year){
				alert("최소 날짜가 최대 날짜보다 큽니다.");
				var max_year = $(".max_year").val("");
				var max_month = $(".max_month").val("");
			}else if(min_year==max_year){
				if(min_month>max_month){
					alert("최소 날짜가 최대 날짜보다 큽니다.");
					var max_month = $(".max_month").val("");
				}
			}
		}
		searchExe();
	}
	
	function searchAll(){
		$(".keyword1").val("");
		
		searchExe();
	}
	
	function searchExe(){
		$.ajax({
			url: "/empList.do"
			, type :"post"
			, data: $("[name=empListForm]").serialize()
			, success: function(responseHtml){
				$(".empListAllCnt").text(
					 $(responseHtml).find(".empListAllCnt").text()
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
		$("[name=empListForm]").find(".selectPageNo").val(selectPageNo);
		//search 함수 호출하기
		search();
	}

	function hireDateTerm(){
		var min_year = $(".min_year").val();
		var min_month = $(".min_month").val();
		var max_year = $(".max_year").val();
		var max_month = $(".max_month").val();

		
		if(min_year!=""){
			if(min_month==""){
				$(".min_month").val("01");
			}
		}else{ 
			$(".min_month").val("");
		}

		if(max_year!=""){
			if(max_month==""){
				$(".max_month").val("12");
			}
		}else{  
			$(".max_month").val("");
		}

	}

	function headerSort(){
		var thObjs = $(".employeeList tr").find("th:gt(1)");
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
		
		$(".empSearch").click(function(){
			search();
		})
		
		$(".empSearchAll").click(function(){
			$(".jikup").prop("checked",false);
			$(".term").val("");
			$(".dep_name").prop("checked",false);

			$(".sortHeadName").val("");
			$(".sortAscDesc").val("");
			
			searchAll();
		})
		
		$(".jikup").click(function(){
			search();
		})
		
		$(".dep_name").click(function(){
			search();
		})
		
		$(".term").change(function(){
			hireDateTerm()
		})
		headerSort();
		/*
		var text = $(".emp_no").text();
		text = $.trim(text)+"▲";
		$(".emp_no").text(text);
		*/
	})
	
</script>
</head>
<body onKeydown="if(event.keyCode==13){search();}">
	<center>
	<form name="empListForm" method="post" onSubmit="return false">
		<table border=1 cellpadding=5 >
			<caption><b>[직원검색]</b></caption>
			<tr>
				<td bgColor="lightgray">키워드
				<td> <input type="text" name="keyword1" class="keyword1">
			</tr>
			<tr>
				<td bgColor="lightgray">직급
				<td>
				<c:forEach var="list" items="${jikupList}" varStatus="loopTagStatus">
					<input type="checkbox" name="jikup" class="jikup" value="${list.jikup}">${list.jikup}
				</c:forEach>
				<!-- 
					<input type="checkbox" name="jikup" class="jikup" value="사장">사장
					<input type="checkbox" name="jikup" class="jikup" value="부장">부장
					<input type="checkbox" name="jikup" class="jikup" value="과장">과장
					<input type="checkbox" name="jikup" class="jikup" value="대리">대리
					<input type="checkbox" name="jikup" class="jikup" value="주임">주임
					<input type="checkbox" name="jikup" class="jikup" value="사원">사원
					 -->
			</tr>
			<tr>
				<td bgColor="lightgray">부서명
				<td>
				<c:forEach var="list" items="${depNameList}" varStatus="loopTagStatus">
					<input type="checkbox" name="dep_name" class="dep_name" value="${list.dep_name}">${list.dep_name}
				</c:forEach>
			</tr>
			<tr>
				<td bgColor="lightgray">입사일
				<td>
					<select name="min_year" class="term min_year">
						<option value="">
						<c:forEach var="i" begin="${minY}" end="${maxY}" step="1">
							<option value="${i}">${i}
						</c:forEach>
					</select>년
					
					<select name="min_month" class="term min_month">
						<option value="">
						<option value="01">01
						<option value="02">02
						<option value="03">03
						<option value="04">04
						<option value="05">05
						<option value="06">06
						<option value="07">07
						<option value="08">08
						<option value="09">09
						<option value="10">10
						<option value="11">11
						<option value="12">12
					</select>월 ~ 
					
					<select name="max_year" class="term max_year">
						<option value="">
						<c:forEach var="i" begin="${minY}" end="${maxY}" step="1">
							<option value="${i}">${i}
						</c:forEach>
					</select>년
					
					<select name="max_month" class="term max_month">
						<option value="">
						<option value="01">01
						<option value="02">02
						<option value="03">03
						<option value="04">04
						<option value="05">05
						<option value="06">06
						<option value="07">07
						<option value="08">08
						<option value="09">09
						<option value="10">10
						<option value="11">11
						<option value="12">12
					</select>월
			</tr>			
		</table>
		<div style="height:10px"></div>
			<select name="rowCntPerPage" class="rowCntPerPage">
				<option value="10" selected>10
				<option value="15">15
				<option value="20">20
			</select>행 보기
		
		<input type="hidden" name="selectPageNo" class="selectPageNo" value="1">	
		
		<input type="button" value="검색" class=empSearch>&nbsp;
		<input type="button" value="모두검색" class="empSearchAll">
		<a href = "javascript:goEmpRegForm();">[직원등록]</a>	
		
		<input type="hidden" name="sortHeadName" class="sortHeadName" value="">	
		<input type="hidden" name="sortAscDesc" class="sortAscDesc" value="">	
	</form>
	<div class="empListAllCnt">총 ${empListAllCnt}개</div>

		<div class="pageNo">
			<c:if test="${empListAllCnt>0}">
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
		<div style="height:10px"></div>
		<div class="searchResult">
			<table border=1 class="employeeList" width = "500px">
				<tr bgColor="gray">
				<th>번호<th class="emp_no">직원번호
				<th class="emp_name">직원명
				<th class="jikup">직급
				<th class="hire_date">입사일
				<th class="dep_name">소속부서명
				
				<c:if test="${requestScope.empList!=null}">
				
					<c:forEach var="emp" items="${requestScope.empList}" varStatus="loopTagStatus">
						<tr 
							bgColor="${loopTagStatus.index%2==0?'white':'lightgray'}" 
							style="cursor:pointer;"  onclick='goEmpUpDelForm(${emp.emp_no})'>
							
							<td> <!-- 역순번호 출력 -->
							<!-- ${requestScope.boardListAllCnt-board.RNUM+1} -->
							<!-- ${boardListAllCnt-requestScope.start_serial_no+1-loopTagStatus.index} -->
							${empListAllCnt-rowCntPerPage*(selectPageNo-1)-loopTagStatus.index}
							
							<!-- 정순번호 출력 -->
							<!-- ${requestScope.start_serial_no+loopTagStatus.index} -->
							<!--${rowCntPerPage*(selectPageNo-1)+1+loopTagStatus.index} -->
							
							
							<td> ${emp.emp_no}
							<td> ${emp.emp_name}
							<td> ${emp.jikup}
							<td> ${emp.hire_date}
							<td> ${emp.dep_name}
						</tr>
					</c:forEach> 
					
				</c:if>
				
			</table>
		</div>
		<div style="height:10px"></div>
		<a href = "javascript:goCusList();">[고객검색]</a>
		<form name="empUpDelForm" method="post" action="empUpDelForm.do">
			<input type="hidden" name="emp_no">
			<!-- <input type="text" name="b_no"> -->	
		</form>
	</center>
</body>
</html>