<%-- 
    Document   : insertTour
    Created on : Jun 15, 2020, 8:28:00 PM
    Author     : SE140355
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Insert Page</title>
    </head>
    <body>
        <jsp:useBean id="now" class="java.util.Date" />
        <fmt:formatDate var="today" value="${now}" pattern="yyyy-MM-dd" />
        <c:set var="placeList" value="${applicationScope.PLACES}"/>
        <c:set var="errors" value="${requestScope.ERRORS}"/>
        <h1>Insert New Tour</h1>
        <form action="insert" method="POST">
            Tour name: <input type="text" name="txtTourName" value="${param.txtTourName}" /> 
            <c:if test="${not empty errors.emptyTourName}">
                <font color="red">
                ${errors.emptyTourName}
                </font>
            </c:if>
            <br>

            From date: <input type="date" name="txtFromDate" min="${today}"
                              <c:if test="${not empty param.txtFromDate}">value=${param.txtFromDate}</c:if>
                              <c:if test="${empty param.txtFromDate}">value="${today}"</c:if>
                                  >
            <c:if test="${not empty errors.invalidFromDate}">
                <font color="red">
                ${errors.invalidFromDate}
                </font>
            </c:if>
            <br>

            To date: <input type="date" name="txtToDate" min="${today}" 
                            <c:if test="${not empty param.txtToDate}">value=${param.txtToDate}</c:if>
                            <c:if test="${empty param.txtToDate}">value="${today}"</c:if>
                                >
            <c:if test="${not empty errors.invalidToDate}">
                <font color="red">
                ${errors.invalidToDate}
                </font>
            </c:if>
            <c:if test="${not empty errors.sameDate}">
                <font color="red">
                ${errors.sameDate}
                </font>
            </c:if>
            <br>

            From place: <select name="cboFromPlace">
                <c:forEach var="place" items="${placeList}">
                    <option value="${place.id}" ${param.cboFromPlace == place.id ? 'selected' : ''}>${place.destination}</option>
                </c:forEach>
            </select> 
            <br>

            To place: <select name="cboToPlace">
                <c:forEach var="place" items="${placeList}">
                    <option value="${place.id}" ${param.cboToPlace == place.id ? 'selected' : ''}>${place.destination}</option>
                </c:forEach>
            </select> 
            <c:if test="${not empty errors.sameDestination}">
                <font color="red">
                ${errors.sameDestination}
                </font>
            </c:if>
            <br>

            Price: <input type="number" name="txtPrice" value="${param.txtPrice}" min="1"/> 
            <c:if test="${not empty errors.emptyPrice}">
                <font color="red">
                ${errors.emptyPrice}
                </font>
            </c:if>
            <c:if test="${not empty errors.wrongPriceFormat}">
                <font color="red">
                ${errors.wrongPriceFormat}
                </font>
            </c:if>    
            <br>

            Quota: <input type="number" name="txtQuota" value="${param.txtQuota}" min="1"/> 
            <c:if test="${not empty errors.emptyQuota}">
                <font color="red">
                ${errors.emptyQuota}
                </font>
            </c:if>
            <c:if test="${not empty errors.wrongQuotaFormat}">
                <font color="red">
                ${errors.wrongQuotaFormat}
                </font>
            </c:if>
            <br>

            Image link: <input type="text" name="txtImageLink" value="${param.txtImageLink}" /> 
            <c:if test="${not empty errors.emptyImage}">
                <font color="red">
                ${errors.emptyImage}
                </font>
            </c:if>
            <br>

            Status: <select name="cboStatus">
                <option value="1" ${param.cboStatus == '1' ? 'selected' : ''}>Active</option>
                <option value="0" ${param.cboStatus == '0' ? 'selected' : ''}>Inactive</option>
            </select>
            <br><br>
            <input type="submit" value="Insert" name="btnAction" />
        </form>
        <br>
        <a href="searchPage">Return to search page</a>
    </body>
</html>
