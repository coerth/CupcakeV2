<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<t:pagetemplate>
    <jsp:attribute name="header">
            Din ordre er bekræftet og du kan hente dine lækre cupcakes i butikken om 78 hverdage!
    </jsp:attribute>

    <jsp:attribute name="footer">

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


        Du har nu : ${sessionScope.customer.balance} på din konto!

        <table class="table table-striped">
        <thead>
        <tr>
            <th scope="col">#</th>
            <th scope="col">Antal</th>
            <th scope="col">Bund</th>
            <th scope="col">Topping</th>
            <th scope="col">Pris</th>

        </tr>
        </thead>
        <tbody>
        <c:set var="index" value="0"></c:set>
        <c:set var="fullAmount" scope="session" value="0"></c:set>
        <c:set var="fullCupcakeAmount" value="0"></c:set>
        <c:forEach items="${requestScope.cupcakeOrderArrayList}" var="item" varStatus="loop">
            <tr>
                <th scope="row">${index = index +1}</th>
                <td ${fullCupcakeAmount = fullCupcakeAmount + item.amount} >${item.amount}</td>
                <td>${item.bottom.name} (${item.bottom.price}kr.)</td>
                <td>${item.topping.name} (${item.topping.price}kr.)</td>
                <td ${fullAmount = fullAmount + item.total}>${item.total}kr.</td>
            </tr>

        </c:forEach>



    </jsp:body>

</t:pagetemplate>
