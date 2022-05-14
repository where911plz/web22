<%--
  Created by IntelliJ IDEA.
  User: royhs
  Date: 2022-05-14
  Time: 오전 4:32
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<html>
<head>
    <title>게시글</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <base href="${pageContext.request.contextPath}/"/>
</head>
<body>
<%@ include file="/WEB-INF/jsp/mvc/top.jsp" %>
<h3>게시글</h3>
<p><div>${article.title}</div></p>
<div>
    <a href="./mvc/user/userInfo?userId=${article.userId}">${article.name}</a> | ${article.cdate}
</div>

<p><div>${article.content}</div></p>
</body>
</html>
