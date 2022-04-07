<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page errorPage="error.jsp" isErrorPage="false" %>

<t:pagetemplate>
    <jsp:attribute name="header">
        Velkommen hos Olsker Cupcakes!
    </jsp:attribute>

    <jsp:attribute name="footer">

     </jsp:attribute>

    <jsp:body>


        <h3> ${requestScope.msg}</h3>

        <form action="AddToCart" method="post">
            <label for="bottomID">Vælg din bund:</label>
            <select name="bottomID" id="bottomID">
                <c:forEach items="${applicationScope.bottomArrayList}" var="item">

                <option value="${item.bottomID}">${item.name} (${item.price}kr.)</option>
                </c:forEach>

            </select>
            <br><br>





            <label for="toppingID">Vælg din topping:</label>
            <select name="toppingID" id="toppingID">
                <c:forEach items="${applicationScope.toppingArrayList}" var="item">

                    <option value="${item.toppingID}">${item.name} (${item.price}kr.)</option>
                </c:forEach>

            </select>
            <br><br>

            <label for="amount">Vælg antal af den valgte cupcake (0-100):</label>

            <input type="number" id="amount" name="amount"
                   min="0" max="100" required>

            <input type="submit" value="Læg i kurv">

        </form>



<%--


            <c:set var="index" value="-1"> </c:set>
        <c:forEach items="${applicationScope.bottomArrayList}" var="item" varStatus="loop" >


        <div class="card">
            <div class="card-body">

                <h5 class="card-title">${item.name}</h5>
                <h6 class="card-subtitle mb-2 text-muted">${item.price} kr</h6>
                <button type="button" onclick="chosenItem = ${item.bottomID}" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModalCenter">
                    Vælg!</button>
            </div>
        </div>

        </c:forEach>


            <!-- Modal -->

            <div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="exampleModalLongTitle">${item.name}</h5>
                            <button type="button" class="close" data-bs-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <form action="AddToCart" method="post">

                            <c:forEach items="${applicationScope.toppingArrayList}" var="topping" >

                            <div class="button">

                                <button type="submit" class="btn btn-primary" id="payment">${item.name} </button>

                                <input type="button" class="btn btn-primary" value="${topping.name} ${topping.topping_id}" />
                                <input type="hidden" name="toppingID" value="${topping.topping_id}">
                                <input type="hidden" name="bottomID">
                                <script>
                                    document.getElementById("bottomID").value = chosenItem
                                </script>

                            </div>





                                </c:forEach>

                        </div>
                        <div class="modal-footer">
                            <button type="submit" class="btn btn-primary" data-bs-dismiss="modal">Læg i kurv</button>
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Luk</button>
                        </div>
                    </div>
                </div>
            </div>


        </form>
--%>


    </jsp:body>

</t:pagetemplate>