<!DOCTYPE html>
<html>
<head>
    <title>글쓰기</title>
    <meta name="viewport" content="width=device-width", initial-scake="1.0">
    <base href="${pageContext.request.contextPath}/">
    <style>
        form input {
            width: 100%;
        }
        form textarea {
            width: 100%; height: 200px;
        }
    </style>
</head>
<body>
<%@ include file="/WEB-INF/jsp/mvc/top.jsp" %>
<h3>글쓰기</h3>
<form action="./mvc/article/updatearticle" method="post">
    <p><input type="title" name="title" required autofocus/> </p>
    <p><textarea name="content" required></textarea></p>
    <p><button type="submit">등록</button></p>
    </p>
</form>
</body>
</html>