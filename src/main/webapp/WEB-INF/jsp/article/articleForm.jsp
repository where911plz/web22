<!DOCTYPE html>
<html>
<head>
    <title>글쓰기</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <base href="${pageContext.request.contextPath}/"/>
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
<%@ include file="/WEB-INF/jsp/top.jsp" %>
<h3>글쓰기</h3>
<form action="./app/article/addArticle" method="post">
    <p><input type="text" name="title" placeholder="제목" required autofocus/>
    </p>
    <p><textarea name="content" required></textarea></p>
    <p>
        <button type="submit">등록</button>
    </p>
</form>
</body>
</html>