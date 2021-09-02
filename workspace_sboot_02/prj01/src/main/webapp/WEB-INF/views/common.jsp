<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script src="/resources/jquery-1.11.0.min.js"></script>

<script>
	$(document).ready(function(){
		$(".logout").prepend(
				"<center><div><span style='cursor:pointer' onclick='location.replace(\"/loginForm.do\");'>"
				+"[로그아웃]</span></div></center>"
		);
	})
</script>