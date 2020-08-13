/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uyennlp.order;

import java.io.Serializable;

/**
 *
 * @author HP
 */
public class OrderCreateError implements Serializable {

    private String customerNameIsEmpty;
    private String wrongCouponCode;
    private String usedCoupon;
    private String notEnoughTour;

    public OrderCreateError() {
    }

    public String getCustomerNameIsEmpty() {
        return customerNameIsEmpty;
    }

    public void setCustomerNameIsEmpty(String customerNameIsEmpty) {
        this.customerNameIsEmpty = customerNameIsEmpty;
    }

    public String getWrongCouponCode() {
        return wrongCouponCode;
    }

    public void setWrongCouponCode(String wrongCouponCode) {
        this.wrongCouponCode = wrongCouponCode;
    }

    public String getUsedCoupon() {
        return usedCoupon;
    }

    public void setUsedCoupon(String usedCoupon) {
        this.usedCoupon = usedCoupon;
    }

    public String getNotEnoughTour() {
        return notEnoughTour;
    }

    public void setNotEnoughTour(String notEnoughTour) {
        this.notEnoughTour = notEnoughTour;
    }

}
