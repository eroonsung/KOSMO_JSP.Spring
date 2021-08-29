<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<span class=boardUpDelCnt>
		<% out.print((int)request.getAttribute("boardUpDelCnt")); %>
	</span>
	
	<span class=msg>
		<% out.print((String)request.getAttribute("msg")); %>
	</span>
</body>
</html>