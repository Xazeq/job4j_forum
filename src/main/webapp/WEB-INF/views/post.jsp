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
    <script>
        function reply() {
            $('#replyBtn').attr('hidden', true);
            $('#replyText').attr('hidden', false);
        }
    </script>
    <title>Forum</title>
</head>
<body>
<div class="container mt-3">
    <ul class="nav">
        <li class="nav-item">
            <a class="nav-link" href="<c:url value='/'/>">Главная</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="<c:url value='/create'/>">Добавить тему</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="<c:url value='/logout'/>">${user.username} | Выйти</a>
        </li>
    </ul>
    <div class="row">
        <table class="table table-hover">
            <thead class="thead-dark">
            <tr>
                <th colspan="2" scope="col">${post.name}</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td style="width: 20%">
                    Автор: <c:out value="${post.user.username}"/>
                    <br>
                    <small><c:out value="${post.created.time.toLocaleString()}"/></small>
                </td>
                <td>${post.description}</td>
            </tr>
            <c:forEach items="${post.comments}" var="comment">
                <tr>
                    <td>
                        Автор: <c:out value="${comment.author.username}"/>
                        <br>
                        <small><c:out value="${comment.created.time.toLocaleString()}"/></small>
                    </td>
                    <td>
                            ${comment.text}
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <div class="row">
        <form id="replyBtn">
            <button type="button" class="btn btn-primary" onclick="reply()">Ответить</button>
        </form>
        <form id="replyText" style="width: 100%;" action="<c:url value="/comment/save?id=${post.id}"/>" method="POST" hidden>
            <textarea class="form-control" id="comment" name="text" rows="2"></textarea>
            <br>
            <button type="submit" class="btn btn-primary" >Отправить</button>
        </form>
    </div>
</div>
</body>
</html>
