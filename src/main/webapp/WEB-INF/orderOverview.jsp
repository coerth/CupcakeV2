<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<t:pagetemplate>
    <jsp:attribute name="header">
        Velkommen hos Olsker Cupcakes!
    </jsp:attribute>

    <jsp:attribute name="footer">

     </jsp:attribute>

    <jsp:body>



        <c:forEach items="${requestScope.orderArrayList}" var="item">
            <table>
                <thead class="orderHeader">
                <tr>
                <th colspan="3">Ordrenummer: ${item.order_id}</th>
                    <th colspan="3">Kundenavn: ${item.customerName}</th>
                </tr>
                </thead>
                <thead>
                <tr>
                    <th>Antal:</th>
                    <th>Bund:</th>
                    <th>Topping:</th>
                    <th>Bundpris:</th>
                    <th>Toppingpris:</th>
                    <th>Total:</th>
                </tr>

                </thead>

                <tbody>
                    <c:forEach items="${item.orderlineArrayList}" var="line">
                <tr>

                        <td> ${line.amount}</td>
                        <td> ${line.b_name}</td>
                        <td> ${line.t_name}</td>
                        <td> ${line.b_price}kr.</td>
                        <td> ${line.t_price}kr.</td>
                        <td> ${line.total}kr.</td>
                </tr>
                    </c:forEach>

                </tbody>
            </table>
        </c:forEach>

<c


    </jsp:body>

</t:pagetemplate>
