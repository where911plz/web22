<!DOCTYPE html>
<html>
<head>
    <title>게시글</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <base href="${pageContext.request.contextPath}/"/>
    <style>
        .title {font-weight: bold;}
    </style>
</head>
<body>
<%@ include file="/WEB-INF/jsp/mvc/top.jsp" %>
<h3>게시글</h3>
<p><a href="${sessionScope.CURRENT_ARTICLE_LIST}">목록</a>
    <c:if test="${sessionScope.ME.userId==article.userId}"> |
        <a href="./mvc/article/articleEdit?articleId=${article.articleId}">수정</a> |
        <a id="btnDel"
           href="./mvc/article/deleteArticle?articleId=${article.articleId}">삭제</a>
    </c:if>
</p>
<div>
    <p>${article.articleId} | <span class="title">${article.title}</span></p>
    <p><a href="./mvc/user/userInfo?userId=${article.userId}">${article.name}</a>
        | 등록일시 ${article.cdate} | 수정일시 ${article.udate}</p>
    <p>${article.contentHtml}</p>
</div>
<script>
    document.querySelector("#btnDel").onclick = function () {
        return confirm("삭제하시겠습니까?");
    };
</script>
</body>
</html>