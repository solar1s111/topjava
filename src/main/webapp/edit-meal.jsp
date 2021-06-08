<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="ru">
    <head>
        <title>Edit Meal</title>
    </head>
    <body>
        <h3><a href="index.html">Home</a></h3>
        <hr>
        <form action="${meal == null ? 'insert' : 'update'}" method="post">
        <h2>
           ${meal == null ? 'Add Meal' : 'Edit Meal'}
        </h2>
        <table>
        <tbody>
            <c:if test="${meal != null}">
                <input type="hidden" name="id" value="${meal.id}" />
            </c:if>
            <tr>
                <td width="105" height="35">DateTime:</td>
                <td><input type="datetime-local" name="date" value="${meal.dateTime}"></td>
            </tr>
            <tr>
                <td height="35">Description:</td>
                <td><input type="text" name="description" value="${meal.description}"></td>
            </tr>
            <tr>
                <td height="35">Calories:</td>
                <td><input type="number" name="calories" value="${meal.calories}"></td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="submit" value="Save" name="Save"> <input type="reset" value="Cancel" name="Cancel">
                </td>
            </tr>
        </tbody>
        </table>
        </form>
    </body>
</html>
