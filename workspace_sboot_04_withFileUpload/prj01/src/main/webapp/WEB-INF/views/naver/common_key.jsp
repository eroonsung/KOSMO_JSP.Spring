<!-- ********************************************************************************** -->
<!-- JSP 기술의 한 종류인 [Page Directive]를 이용하여 현 JSP 페이지 처리 방식 선언하기 -->
<!-- ********************************************************************************** -->
<!-- 현재 이 JSP 페이지 실행 후 생성되는 문서는 HTML 이고, 이 문서 안의 데이터는 UTF-8 방식으로 인코딩한다라고 설정함 -->
<!-- 현재 이 JSP 페이지는 UTF-8 방식으로 인코딩한다-->
<!-- UTF-8 인코딩 방식은 한글을 포함한 전 세계 모든 문자열을 부호화할 수 있는 방법이다.-->
<!-- 모든 JSP 페이지, 상단에 무조건 들어가는 코드 -->
<!-- UTF-8 인코딩방식을 가지고 있는 text중의 하나인 html 파일로 처리 -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.naver.erp.Info"%>

<!-- ********************************************************************************** -->
<!-- JSP 페이지에서 사용할 [사용자 정의 태그]의 한 종류인 [JSTL의 C코어 태그]를 사용하겠다고 선언 -->
<!-- ********************************************************************************** -->
	<!-- 표현은 태그처럼 하되 기능은 Java -->
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<!--
		--------------------------------------
		JSTL 이란?
		--------------------------------------
			=>자카르타라는 단체에서 만든 [사용자 정의 태그]를 말한다.
			=>자바 문법을 HTML 과 같은 태그 형태로 표현한다.
			=>HTML 과 같은 태그 형태로 표현하면 뒤에서 자바 문법으로 바뀌어 실행된다.
			=>JSTL 사용 장점
				-태그 형태이므로 디자이너 퍼블리셔, 초급 개발자가 접근이 쉽다.
				-큰 에러가 아닌 이상 화면이 멈추지 않는다.
				-null 처리에 대해 관대하다. null에 대해 민감한 반응을 보이지않는다. 
			=>JSTL 은 EL 과 같이 쓰인다.

	-->

<!-- --------------------------------------------- -->
<!-- JSTL이라는 커스텀태그를 사용하여 HttpServletRequest 객체에 키값-데이터 저장하기 -->
<!-- --------------------------------------------- -->
<c:set var="thBgColor" value="lightblue" scope="request"/>

	<%-- 
		위 커스텀 태그와 동일한 기능을 가진 자바 코드
		<% request.setAttribute("thBgColor","lightyellow"); %>
	--%>  
	
<c:set var="bodyBgColor" value="#e7f3f3" scope="request"/>

<c:set var="naverPath" value="<%=Info.naverPath%>" scope="request"/>
<!--<c:set var="naverPath" value="naver/" scope="request"/>-->

<c:set var="boardImgRate1" value="30%" scope="request"/>
<c:set var="boardImgRate1" value="70%" scope="request"/>


<c:set var="cellpadding" value="7" scope="request"/>
<c:set var="cellpadding" value="5" scope="request"/>


