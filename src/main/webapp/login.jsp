<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page errorPage="error.jsp" isErrorPage="false" %>

<t:pagetemplate>
    <jsp:attribute name="header">
             Login
    </jsp:attribute>

    <jsp:attribute name="footer">
            Login
    </jsp:attribute>

    <jsp:body>

        <div class="area" >
            <ul class="circles">
                <li></li>
                <li></li>
                <li></li>
                <li></li>
                <li></li>
                <li></li>
                <li></li>
                <li></li>
                <li></li>
                <li></li>
            </ul>
        </div >

        <h3>Har du allerede en bruger?<br>Log på her:</h3>

        <form action="login" method="post">
            <label for="email">Email: </label>
            <input type="text" id="email" name="email"/><br>
            <label for="password">Kodeord: </label>
            <input type="password" id="password" name="password"/><br>
            <input type="submit"  value="Log in"/>
        </form>

        <h3>Ny bruger?<br>Lav din bruger her: </h3>

        <form action="CreateUser" method="post">
            <label for="newEmail">Email: </label>
            <input type="text" id="newEmail" name="newEmail"/><br>
            <label for="newPassword">Kodeord: </label>
            <input type="password" id="newPassword" name="newPassword"/><br>
            <label for ="address">Gade: </label>
            <input type="text" id="address" name="address"/><br>
            <label for="streetNumber">Vej nr:</label>
            <input type="text" id="streetNumber" name="streetNumber"><br>
            <input type="submit"  value="Opret bruger"/>
        </form>



    </jsp:body>
</t:pagetemplate>