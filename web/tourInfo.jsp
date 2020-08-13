<%-- 
    Document   : tourInfo
    Created on : Jun 16, 2020, 2:59:57 PM
    Author     : SE140355
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Tour Info</title>
    </head>
    <body>
        <jsp:useBean id="now" class="java.util.Date" />
        <fmt:formatDate var="today" value="${now}" pattern="dd/MM/yyyy" />
        <c:if test="${empty sessionScope.ACCOUNT}">
            <form action="loginPage">
                <input type="submit" value="Login" name="btnAction" />
            </form>
            <hr>
        </c:if>
        <c:if test="${not empty sessionScope.ACCOUNT.role}">
            <font color="red">
            Welcome ${sessionScope.ACCOUNT.name}
            </font>

            <br>
            <form action="logout">
                <input type="submit" value="Logout" name="btnAction" />
            </form>
            <hr>
        </c:if>
        <c:if test="${sessionScope.ACCOUNT.role == 2}">
            <a href="cartPage">View cart</a><br>
        </c:if>

        <a href="searchPage">Search for more tours</a>

        <h1>Tour Info</h1>
        <c:set var="tour" value="${requestScope.DETAILS}"/>
        <c:set var="placeList" value="${applicationScope.PLACES}"/>
        <img src="${tour.image}" style="width:350px"><br>
        <p>
            <b>Name:</b> ${tour.name}<br>

            <jsp:useBean id="dateValue" class="java.util.Date"/>
            <jsp:setProperty name="dateValue" property="time" value="${tour.fromDate.time}"/>
            <b>From date:</b> <fmt:formatDate var="fromDateText" value="${dateValue}" pattern="dd/MM/yyyy"/> <br>

            <jsp:useBean id="dateValue2" class="java.util.Date"/>
            <jsp:setProperty name="dateValue2" property="time" value="${tour.toDate.time}"/>
            <b>To date:</b> <fmt:formatDate var="toDateText" value="${dateValue2}" pattern="dd/MM/yyyy"/> <br>

            <b>From place: </b>
            <c:forEach var="place" items="${placeList}">
                <c:if test="${tour.fromPlace == place.id}">${place.destination}</c:if>
            </c:forEach><br>

            <b>To place: </b>
            <c:forEach var="place" items="${placeList}">
                <c:if test="${tour.toPlace == place.id}">${place.destination}</c:if>
            </c:forEach><br>

            <b>Price:</b> ${tour.price} VND <br>

            <b>Quota:</b> ${tour.quota} people <br><br>

            <c:if test="${sessionScope.ACCOUNT.role == 2}">
                <c:if test="${fromDateText >= today}">
                    <c:if test="${tour.emptySeat == 0}">
                        <b>Quantity:</b> <font color="red">Out of stock</font>
                    </c:if>

                    <c:if test="${tour.emptySeat > 0}">
                    <form action="addOrder">
                        <b>Quantity:</b> <input type="number" name="txtQuantity" 
                                                min="1" max="${tour.quota}" value="1"
                                                    /> people
                                                <br>

                                                <input type="hidden" name="txtTourId" value="${tour.id}" />
                        <input type="submit" value="Add to cart" name="btnAction" />
                    </form>
                </c:if>
            </c:if>
        </c:if>
    </body>
</html>
