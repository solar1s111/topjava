<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="ru">
    <head>
        <title>Edit Meal</title>
    </head>
    <body>
        <h3><a href="index.html">Home</a></h3>
        <hr>
        <div>
        <c:if test="${meal != null}">
            <form action="update" method="post">
        </c:if>
        <c:if test="${meal == null}">
            <form action="insert" method="post">
        </c:if>
        <caption>
            <h2>
                <c:if test="${meal != null}">
                    Edit Meal
                </c:if>
                <c:if test="${meal == null}">
                    Add New Meal
                </c:if>
            </h2>
        </caption>
            <table>
            <tbody>
                <tr>
                    <td width="105" height="35">DateTime:</td>
                    <td><input type="datetime-local" name="date" value="<c:out value='${meal.dateTime}' />"></td>
                </tr>
                <tr>
                    <td height="35">Description:</td>
                    <td><input type="text" name="description" value="<c:out value='${meal.description}' />"></td>
                </tr>
                <tr>
                    <td height="35">Calories:</td>
                    <td><input type="number" name="calories" value="<c:out value='${meal.calories}' />"></td>
                </tr>
                <tr>
                    <td colspan="2">
                        <input type="submit" value="Save" name="Save"> <input type="reset" value="Cancel" name="Cancel">
                    </td>
                </tr>
            </tbody>
            </table>
        </form>
        </div>
    </body>
</html>
