<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@include file="common.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script>
//staff_name gender religion_code school_code skill_code trem
$(document).ready(function(){
	$(".staffListAllCnt").hide();
	$(".searchResult").hide();
	$(".pageNo").hide();
	
	$(".staffInputBtn").click(function(){
		location.replace('/staff_input_form.do');
		})

		$(".rowCntPerPage").change(function(){
			$(".selectPageNo").val("1");
			search();
		})
		
		$(".staffSearch").click(function(){
			search();
		})
		
		$(".staffSearchAll").click(function(){
			searchAll();
		})
		/*
		$(".ageDecade").click(function(){
			search();
		})
		*/
		$(".term").change(function(){
			hireDateTerm()
		})
		
		headerSort("staffList", "staff_name");
		headerSort("staffList", "gender");
		headerSort("staffList", "religion_name");
		headerSort("staffList", "graduate_day");
})

function goStaffUpDelForm(staff_no){

	$("[name=StaffUpDelForm] [name=staff_no]").val(staff_no);
	document.StaffUpDelForm.submit();
}

function search(){
	
	var name= $(".name").val();
	
	if(name==null||name.split(" ").join("")==""){
		name= "";
	}
	$(".name").val( $.trim(name) );


	var min_year = $(".min_year").val();
	var min_month = $(".min_month").val();
	var max_year = $(".max_year").val();
	var max_month = $(".max_month").val();
	
	if(min_year!=""&&min_month!=""&&max_year==""&&max_month==""){
		$(".max_year").val("2021");
		$(".max_month").val("12");
	}
	
	if(max_year!=""&&max_month!=""&&min_year==""&&min_month==""){
		$(".min_year").val("2000");
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

function searchAll(){
	$(".name").val("");
	$(".gender").prop("checked",false);
	$(".religion").prop("selected",false);
	$(".school").prop("checked",false);
	$(".skill").prop("checked",false);
	$(".term").val("");
	$(".sortHeadName").val("");
	$(".sortAscDesc").val("");
	
	searchExe();
}

function searchExe(){

	
	$.ajax({
		url: "/staff_search_form.do"
		, type :"post"
		, data: $("[name=staffSearchForm]").serialize()
		, success: function(responseHtml){

			$(".staffListAllCnt").show();
			$(".searchResult").show();
			$(".pageNo").show();
			
			$(".staffListAllCnt").text(
				 $(responseHtml).find(".staffListAllCnt").text()
				);
			$(".searchResult").html(
					 $(responseHtml).find(".searchResult").html()
					);
			$(".pageNo").html($(responseHtml).find(".pageNo").html())
			
			//===========================
			headerSort("staffList", "staff_name");
			headerSort("staffList", "gender");
			headerSort("staffList", "religion_name");
			headerSort("staffList", "graduate_day");
			headerSortAjax();
			//===========================
		}
		,error: function(){
			alert("서버 접속 실패");
		}
	});
}

function search_with_changePageNo(selectPageNo){
	$("[name=staffSearchForm]").find(".selectPageNo").val(selectPageNo);
	//search 함수 호출하기
	search();
}

	
</script>
</head>
<body onKeydown="if(event.keyCode==13){search();}">
<center>
	<form name="staffSearchForm" method="post" onSubmit="return false">
	<table border=1 cellpadding=5 class="tbcss2">
		<tr><th colspan="6" bgcolor="${thBgColor}"><b>사원 정보 검색</b>
		<tr>
			<th bgcolor="${thBgColor}">이름
			<td><input type="text" name="name" class="name">
			<th bgcolor="${thBgColor}">성별<td>
			<input type="checkbox" name="gender" class="gender" value="남">남
			<input type="checkbox" name="gender" class="gender" value="여">여
			<th bgcolor="${thBgColor}">종교<td>
			<select name="religion">
			<option value="0">
				<c:forEach var="rel" items="${relgionList}">
					<option value="${rel.religion_code}" class="religion">${rel.religion_name}
				</c:forEach>
				</select>
		<tr>
			<th bgcolor="${thBgColor}">학력<td>
			<c:forEach var="school" items="${schoolList}">
				<input type="checkbox" name="school" class="school" value="${school.school_code}">${school.school_name}
			</c:forEach>
			<th bgcolor="${thBgColor}">기술<td colspan="3">
			<c:forEach var="skill" items="${skillList}">
				<input type="checkbox" name="skill" class="skill" value="${skill.skill_code}">${skill.skill_name}
			</c:forEach>
		<tr>
			<th bgcolor="${thBgColor}">졸업일
			<td colspan="5">
				<select name="min_year" class="term min_year">
						<option value="">
						<c:forEach var="i" begin="0" end="${2021-2000}" step="1">
						 <c:set var="yearOption" value="${2021-i}"/>
							<option value="${yearOption}">${yearOption}
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
						<c:forEach var="i" begin="0" end="${2021-2000}" step="1">
						 <c:set var="yearOption" value="${2021-i}"/>
							<option value="${yearOption}">${yearOption}
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
	</table>
			<div style="height:10px"></div>
			<select name="rowCntPerPage" class="rowCntPerPage">
				<option value="10" selected>10
				<option value="15">15
				<option value="20">20
			</select>행 보기
		
		<input type="hidden" name="selectPageNo" class="selectPageNo" value="1">	
		
		<input type="button" value="검색" class="staffSearch">&nbsp;
		<input type="button" value="모두검색" class="staffSearchAll">
		<input type="reset" value="초기화" >
		<input type="button" value="직원등록" class="staffInputBtn">
		
		<input type="hidden" name="sortHeadName" class="sortHeadName" value="">	
		<input type="hidden" name="sortAscDesc" class="sortAscDesc" value="">	
	</form>
	
		<div style="height:5px"></div>
	<div class="staffListAllCnt">검색건수 : 총 ${staffListAllCnt}개</div>
		<div style="height:10px"></div>
		<div class="searchResult">
		<table border=1 class="staffList tbcss2" width = "600px" >
			<tr>
			<th bgcolor="${thBgColor}">번호<th class="staff_name" bgcolor="${thBgColor}">이름<th class="gender" bgcolor="${thBgColor}">성별<th class="religion_name" bgcolor="${thBgColor}">종교<th class="skill_name" bgcolor="${thBgColor}">기술<th class="graduate_day" bgcolor="${thBgColor}">졸업일</th>
			<c:if test="${requestScope.staffList!=null}">
				
					<c:forEach var="staff" items="${requestScope.staffList}" varStatus="loopTagStatus">
						<tr 
							bgColor="${loopTagStatus.index%2==0?'white':'#dcdcdc'}" 
							style="cursor:pointer;"  onclick='goStaffUpDelForm(${staff.staff_no})'>
							
							<td> <!-- 역순번호 출력 -->
							<!-- ${requestScope.boardListAllCnt-board.RNUM+1} -->
							<!-- ${boardListAllCnt-requestScope.start_serial_no+1-loopTagStatus.index} -->
							${staffListAllCnt-pagingNos.rowCntPerPage*(pagingNos.selectPageNo-1)-loopTagStatus.index}
							
							<!-- 정순번호 출력 -->
							<!-- ${requestScope.start_serial_no+loopTagStatus.index} -->
							<!--${rowCntPerPage*(selectPageNo-1)+1+loopTagStatus.index} -->
							
							
							<td> ${staff.staff_name}
							
							<td> ${staff.gender}
							<td> ${staff.religion_name}
							<td> 
								${staff.skill_name}
							<td> ${staff.graduate_day}
						</tr>
					</c:forEach> 
					
				</c:if>
		</table>
	</div>
	<div style="height:5px"></div>
	<div class="pageNo">
			<c:if test="${staffListAllCnt>0}">
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
		
		<form name="StaffUpDelForm" method="post" action="staff_updel_form.do">
			<input type="hidden" name="staff_no">
		</form>
</center>
</body>
</html>