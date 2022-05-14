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
<div>게시물번호</div>
<div>${article.articleId}</div>
<div>제목</div>
<div>${article.title}</div>
<div>내용</div>
<div>${article.context}</div>
<div>이름</div>
<div>${article.name}</div>
<div>작성일</div>
<div>${article.cdate}</div>
</body>
</html>
