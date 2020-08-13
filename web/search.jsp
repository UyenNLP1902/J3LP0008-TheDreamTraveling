<%-- 
    Document   : index
    Created on : Jun 15, 2020, 8:20:30 AM
    Author     : SE140355
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search Page</title>
    </head>
    <body>
        <c:set var="account" value="${sessionScope.ACCOUNT}"/>

        <c:if test="${empty account}">
            <form action="loginPage">
                <input type="submit" value="Login" name="btnAction" />
            </form>
            <hr>
        </c:if>

        <c:if test="${not empty account}">
            <c:if test="${not empty account.role}">
                <font color="red">
                Welcome ${account.name}
                </font>

                <br>
                <form action="logout">
                    <input type="submit" value="Logout" name="btnAction" />
                </form>
            </c:if>
            <hr>
            <br>
            <c:if test="${account.role == 1}">
                <a href="insertPage">Insert a new tour</a>
            </c:if>
            <c:if test="${account.role == 2}">
                <a href="cartPage">View cart</a>
            </c:if>

        </c:if>



        <c:set var="placeList" value="${applicationScope.PLACES}"/>
        <jsp:useBean id="now" class="java.util.Date" />
        <fmt:formatDate var="today" value="${now}" pattern="yyyy-MM-dd" />
        <h1>Search Page</h1>
        <form action="search">
            <input type="hidden" name="txtCurrentPage" value="1"/>
            Search to place: <select name="cboSearchPlace">
                <option value="0" ${param.cboSearchPlace == '0' ? 'selected' : ''}>All</option>
                <c:forEach var="place" items="${placeList}">
                    <option value="${place.id}" ${param.cboSearchPlace == place.id ? 'selected' : ''}>${place.destination}</option>
                </c:forEach>
            </select> <br>

            Date: <input type="date" name="txtFromDate" min="${today}" 
                         <c:if test="${not empty param.txtFromDate}">value="${param.txtFromDate}"</c:if>
                             >

                         to <input type="date" name="txtToDate" min="${today}" 
                      <c:if test="${not empty param.txtToDate}">value="${param.txtToDate}"</c:if>
                          > <br>

                      Price: <input type="number" name="txtPriceFrom" min="0" 
                      <c:if test="${empty param.txtPriceFrom}">value="0"</c:if>
                      <c:if test="${not empty param.txtPriceFrom}">value="${param.txtPriceFrom}"</c:if>
                          /> 
                      to <input type="number" name="txtPriceTo" min="0" 
                      <c:if test="${empty param.txtPriceTo}">value="0"</c:if>
                      <c:if test="${not empty param.txtPriceTo}">value="${param.txtPriceTo}"</c:if>
                          /> 
                      <br>
                      <input type="submit" value="Search" name="btnAction" />
            </form><br>

        <c:set var="currentPage" value="${requestScope.currentPage}"/>
        <c:set var="noOfPages" value="${requestScope.noOfPages}"/>
        <c:set var="place" value="${param.cboSearchPlace}"/>
        <c:set var="results" value="${requestScope.RESULTS}"/>

        <c:if test="${not empty results}">
            <table border="1">
                <thead>
                    <tr>
                        <th>Image</th>
                        <th>Brief information</th>
                        <th>Details</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="tour" items="${results}">
                        <tr>
                            <td>
                                <img src="${tour.image}" style="width:250px">
                            </td>
                            <td>
                                <p style="width:750px">
                                    <b>Name:</b> ${tour.name} <br>

                                    <jsp:useBean id="dateValue" class="java.util.Date"/>
                                    <jsp:setProperty name="dateValue" property="time" value="${tour.fromDate.time}"/>
                                    <b>From date:</b> <fmt:formatDate value="${dateValue}" pattern="dd/MM/yyyy"/> <br>

                                    <jsp:useBean id="dateValue2" class="java.util.Date"/>
                                    <jsp:setProperty name="dateValue2" property="time" value="${tour.toDate.time}"/>
                                    <b>To date:</b> <fmt:formatDate value="${dateValue2}" pattern="dd/MM/yyyy"/> <br>
                                    <b>Price:</b> ${tour.price} VND
                                </p>
                            </td>
                            <td>
                                <form action="showInfo">
                                    <input type="hidden" name="txtTourId" value="${tour.id}" />
                                    <input type="submit" value="Show full details" name="btnAction" />
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <c:if test="${currentPage != 1}">
                <c:url var="urlRewritting" value="search">
                    <c:param name="btnAction" value="Search"/>
                    <c:param name="txtCurrentPage" value="${currentPage - 1}"/>
                    <c:param name="cboSearchPlace" value="${place}"/>
                </c:url>
                <a href="${urlRewritting}">Previous</a>
            </c:if>
            Page ${currentPage}/${noOfPages}
            <c:if test="${currentPage < noOfPages}">
                <c:url var="urlRewritting" value="search">
                    <c:param name="btnAction" value="Search"/>
                    <c:param name="txtCurrentPage" value="${currentPage + 1}"/>
                    <c:param name="cboSearchPlace" value="${place}"/>
                </c:url>
                <a href="${urlRewritting}">Next</a>
            </c:if>
        </c:if>


    </body>
</html>
