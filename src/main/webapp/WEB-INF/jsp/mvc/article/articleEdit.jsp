<!DOCTYPE html>
<html>
<head>
    <title>글수정</title>
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
<h3>글수정</h3>
<form action="./app/article/updateArticle" method="post">
    <p><input type="text" name="title"
              value="${e:forHtml(article.title)}" placeholder="제목" required
              autofocus/>
    </p>
    <p><textarea name="content"
                 required>${e:forHtml(article.content)}</textarea></p>
    <p>
        <button type="submit">저장</button>
    </p>
    <input type="hidden" name="articleId" value="${article.articleId}"/>
</form>
</body>
</html>