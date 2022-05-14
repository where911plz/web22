<%@ page import="java.util.List" %>
<%@ page import="kr.mjc.khs.web.dao.User" %>
<%
    List<User> userList = (List<User>) request.getAttribute("userList");
%>
<!DOCTYPE html>
<html>
<head>
    <title>게시판</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <base href="<%=request.getContextPath()%>/"/>
</head>
<body>
<%@ include file="/WEB-INF/jsp/model1/top.jsp" %>
<h3>게시판</h3>
</div>
</body>
</html>