<%-- 
    Document   : confirm
    Created on : Jun 20, 2020, 11:10:37 AM
    Author     : SE140355
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Confirm Page</title>
    </head>
    <body>
        <h1>Please confirm before progressing</h1>

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
                        <th>Quantity</th>
                        <th>Price</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="item" items="${cart.items}" varStatus="counter">
                        <tr>
                            <td>
                                ${counter.count}
                            </td>
                            <td>
                                ${item.key.name}
                            </td>
                            <td>
                                ${item.value}
                            </td>
                            <td>
                                ${item.key.price} VND
                            </td>
                        </tr>
                    </c:forEach>
                    <tr>
                        <td colspan="2">
                            <b>Subtotal: </b>
                        </td>
                        <td colspan="2">
                            ${sessionScope.TOTAL_BILL} VND
                        </td>
                    </tr>
                </tbody>
            </table>
        </c:if>
        <br>
        <c:set var="coupon" value="${requestScope.COUPON}"/>
        <c:set var="total" value="${requestScope.TOTAL}"/>
        <c:set var="customer" value="${requestScope.TOTAL}"/>
        
        <b>Customer name:</b> ${sessionScope.CUSTOMER_NAME}<br>
        <c:if test="${not empty coupon}">
            <b>Coupon: </b> ${coupon.id}<br>
            <b>Discount: </b> ${coupon.discount}%<br>
            <b>Total: </b> ${total} VND<br>
        </c:if>
        <c:if test="${empty coupon}">
            <b>Coupon: </b> none<br>
            <b>Discount: </b> none<br>
            <b>Total: </b> ${total} VND<br>
        </c:if>
        <form action="proceedPayment">
            <input type="hidden" name="txtCoupon" value="${coupon.id}" />
            <input type="hidden" name="txtCustomerName" value="${sessionScope.CUSTOMER_NAME}" />
            <input type="hidden" name="txtTotal" value="${total}" />
            <input type="submit" value="Proceed" name="btnAction" 
                   onclick="{
                               return checkConfirm();
                           }"/>
        </form>
        <br><br>
        <a href="cartPage">Return to cart</a>

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
