<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
            integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
            crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <title>Forum</title>
</head>
<body>
<div class="container mt-3">
    <div class="row">
        <ul class="nav">
            <li class="nav-item">
                <a class="nav-link" href="<c:url value='/create'/>">Добавить тему</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<c:url value='/logout'/>">${user.username} | Выйти</a>
            </li>
        </ul>
    </div>
    <div class="row">
        <table class="table table-hover">
            <thead class="thead-dark">
            <tr>
                <th scope="col">Тема</th>
                <th scope="col" style="width: 10%;">Ответов</th>
                <th scope="col" style="width: 25%">Последний ответ</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${posts}" var="post">
                <tr>
                    <td>
                        <c:if test="${user != null && user.username.equals(post.user.username)}">
                            <a href='<c:url value="/update?id=${post.id}"/>'>
                                <i class="fa fa-edit mr-3"></i>
                            </a>
                        </c:if>
                        <a href='<c:url value="/post?id=${post.id}"/>'><c:out value="${post.name}"/></a>
                        <br>
                        <small>Автор: <c:out value="${post.user.username}"/></small>
                    </td>
                    <td><c:out value="${post.comments.size()}"/></td>
                    <td>
                        <c:if test="${post.comments.size() == 0}">
                            <c:out value="${post.created.time.toLocaleString()}"/>
                            <br>
                            <small>Автор: <c:out value="${post.user.username}"/></small>
                        </c:if>
                        <c:if test="${post.comments.size() > 0}">
                            <c:out value="${post.comments.get(post.comments.size() - 1).created.time.toLocaleString()}"/>
                            <br>
                            <small>Автор: <c:out value="${post.comments.get(post.comments.size() - 1).author.username}"/></small>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
