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
        <h3><a href="editMeal.jsp">Add Meal</a></h3>
        <table align="left" border="1">
            <thead>
            <tr>
                <th width="30" height="45">id</th>
                <th>Date</th>
                <th>Description</th>
                <th>Calories</th>
                <th colspan=2 width="120">Action</th>
            </tr>
            </thead>
            <tbody>
                <c:forEach var="meal" items="${meals}">
                    <tr style="color:${meal.excess ? 'red' : 'green'}">
                        <td align="center" style="height: 35px">${meal.id}</td>
                        <td>${meal.date} ${meal.time}</td>
                        <td>${meal.description}</td>
                        <td>${meal.calories}</td>
                        <td align="center"><a href="editMeal.jsp">Update</a></td>
                        <td align="center"><a href="meals?action&id=${meal.id}">Delete</a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </body>
</html>
