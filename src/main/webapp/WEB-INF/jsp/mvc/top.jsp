<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="e" uri="https://www.owasp.org/index.php/OWASP_Java_Encoder_Project" %>
<div style="background-color:#aaffaa; padding:0.5rem;">
    <a href="./">홈</a> |
    <a href="./app/user/userList?count=25&page=1">회원</a> |
    <a href="./app/article/articleList?count=25&page=1">게시판</a> |
    <c:if test="${empty sessionScope.ME}">
        <a href="./app/user/signinForm">로그인</a> |
        <a href="./app/user/signupForm">회원가입</a>
    </c:if>
    <c:if test="${!empty sessionScope.ME}">
        <a href="./app/user/myInfo">${sessionScope.ME.name}님</a> |
        <a href="./app/user/signout">로그아웃</a>
    </c:if>
</div>