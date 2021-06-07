<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="ru">
    <head>
        <title>Meals</title>
    </head>
    <body>
        <h3><a href="index.html">Home</a></h3>
        <hr>
        <h2>Meals</h2>
        <h3><a href="add">Add Meal</a></h3>
        <table align="left" border="1">
            <thead>
            <tr>
                <th height="45">Date</th>
                <th>Description</th>
                <th>Calories</th>
                <th colspan=2 width="120">Action</th>
            </tr>
            </thead>
            <tbody>
                <c:forEach var="meal" items="${meals}">
                    <tr style="color:${meal.excess ? 'red' : 'green'}">
                        <td style="height: 35px">${meal.date} ${meal.time}</td>
                        <td>${meal.description}</td>
                        <td>${meal.calories}</td>
                        <td align="center"><a href="edit?id=<c:out value='${meal.id}' />">Update</a></td>
                        <td align="center"><a href="delete?id=<c:out value='${meal.id}' />">Delete</a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </body>
</html>
