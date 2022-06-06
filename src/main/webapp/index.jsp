<!DOCTYPE html>
<html>
<head>
    <title></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <base href="${pageContext.request.contextPath}/"/>
</head>
<body>
<h3>게시판 애플리케이션</h3>
<ul>
    <li><a href="./model1/user/userList?count=25&page=1">서블릿들을 사용한 Model 1
        애플리케이션</a></li>
    <li><a href="./mvc/user/userList?count=25&page=1">DispatcherServlet을 사용한 MVC
        애플리케이션</a></li>
    <li><a href="./app/user/userList?count=25&page=1">Spring Web MVC를 사용한
        애플리케이션</a></li>
</ul>
</body>
</html>