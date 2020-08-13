<%-- 
    Document   : cart
    Created on : Jun 17, 2020, 8:35:17 AM
    Author     : SE140355
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Card Page</title>
    </head>
    <body>
        <c:set var="account" value="${sessionScope.ACCOUNT}"/>
        <c:set var="tourList" value="${applicationScope.TOURS}"/>
        <c:if test="${empty account}">
            <form action="loginPage">
                <input type="submit" value="Login" name="btnAction" />
            </form>
            <hr>
        </c:if>
        <h1>View Your Cart</h1>

        <c:set var="cart" value="${sessionScope.CART}"/>
        <c:if test="${empty cart}">
            <h3>No items</h3>
        </c:if>
        <c:if test="${not empty cart.items}">
            <table border="1">
                <thead>
                    <tr>
                        <th>No.</th>
                        <th>Name</th>
                        <th>Price each</th>
                        <th>Quantity</th>
                        <th>Total</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                <form action="manageCart">
                    <c:forEach var="item" items="${cart.items}" varStatus="counter">
                        <tr>
                            <td>
                                ${counter.count}
                            </td>
                            <td>
                                ${item.key.name}
                            </td>
                            <td>
                                ${item.key.price} VND
                            </td>
                            <td>
                                <input type="number" name="txtTourQuantity${item.key.id}" 
                                       value="${item.value}" 
                                       min="1" style="width: 150px;"
                                       />
                            </td>
                            <td>
                                ${item.key.price * item.value} VND
                            </td>
                            <td>
                                <input type="checkbox" name="chkItem" value="${item.key.id}" />
                            </td>
                        </tr>

                    </c:forEach>
                    <tr>
                        <td colspan="4">
                            <b>Total bill:</b>
                        </td>
                        <td>
                            ${sessionScope.TOTAL_BILL} VND
                        </td>
                        <td>
                            <input type="submit" value="Update selected tours" name="btnAction" /><br>
                            <input type="submit" value="Remove selected tours" name="btnAction" />
                        </td>
                    </tr>
                </form>
            </tbody>
        </table>
        <c:set var="errors" value="${requestScope.ERRORS}"/>
        <c:if test="${not empty errors.notEnoughTour}">
            <font color="red">${errors.notEnoughTour}</font>
            <script>
                alert("Tour quantity is out of bound!");
            </script>
            <br>
        </c:if>
        <br>

        <c:set var="customer" value="${sessionScope.CUSTOMER_NAME}"/>

        <form action="checkout" id="checkout">
            Customer* <input type="text" name="txtCustomer" 
                             <c:choose>
                                 <c:when test="${(empty param.txtCustomer) && (not empty customer)}">
                                     value="${customer}"
                                 </c:when>
                                 <c:otherwise>
                                     value="${param.txtCustomer}"
                                 </c:otherwise>
                             </c:choose>
                             /> 
            <c:if test="${not empty errors.customerNameIsEmpty}">
                <font color="red">${errors.customerNameIsEmpty}</font>
            </c:if>
            <br>

            Coupon <input type="text" name="txtCoupon" value="${param.txtCoupon}" /> 
            <c:if test="${not empty errors.wrongCouponCode}">
                <font color="red">${errors.wrongCouponCode}</font>
            </c:if>
            <c:if test="${not empty errors.usedCoupon}">
                <font color="red">${errors.usedCoupon}</font>
            </c:if>
            <br>

            <input type="submit" value="Check Out" name="btnAction"/>
        </form>
    </c:if>

    <br>
    <a href="searchPage">Search for more tours</a>

    <script>
        function checkConfirm() {
            var answer = confirm("Are you sure you want to continue?");
            if (answer == true) {
                return true;
            } else {
                return false;
            }
        }
    </script>
</body>
</html>
