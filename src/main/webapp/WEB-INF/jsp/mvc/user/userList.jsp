<!DOCTYPE html>
<html>
<head>
    <title>회원목록</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <base href="${pageContext.request.contextPath}/"/>
    <style>
        .grid {display: grid;grid-template-columns: repeat(3, auto);}
        form [type=number] {width: 50px;}
    </style>
</head>
<body>
<%@ include file="/WEB-INF/jsp/top.jsp" %>
<h3><fmt:message key="title.userList"/></h3>
<form name="form1">
    <span>전체 ${totalCount}개, ${totalPage}페이지</span>
    <button id="btnPrev" type="submit"
            <c:if test="${param.page==1}">disabled</c:if>>
        &lt;
    </button>
    <input type="number" name="page" value="${param.page}" min="1" readonly/>
    <button id="btnNext" type="submit" <c:if
            test="${param.page==totalPage}">disabled</c:if>>&gt;
    </button>
    <input type="number" name="count" value="${param.count}" min="25" max="100"
           step="25"/>행씩
    <button type="submit">조회</button>
</form>
<div class="grid">
    <div>번호</div>
    <div>이메일</div>
    <div>이름</div>
    <c:forEach var="user" items="${userList}">
        <div><a href="./app/user/userInfo?userId=${user.userId}">${user.userId}</a>
        </div>
        <div>${user.email}</div>
        <div>${user.name}</div>
    </c:forEach>
</div>
<script>
    document.querySelector("#btnNext").onclick = function () {
        document.querySelector("[name=page]").value++;
    };
    document.querySelector("#btnPrev").onclick = function () {
        document.querySelector("[name=page]").value--;
    };
    document.querySelector("[name=count]").onchange = function () {
        document.querySelector("[name=page]").value = 1;
    };
</script>
</body>
</html>