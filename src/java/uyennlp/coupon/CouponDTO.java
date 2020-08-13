/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uyennlp.coupon;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author HP
 */
public class CouponDTO implements Serializable {

    private String id;
    private double discount;
    private Timestamp expiredDate;

    public CouponDTO() {
    }

    public CouponDTO(String id, double discount, Timestamp expiredDate) {
        this.id = id;
        this.discount = discount;
        this.expiredDate = expiredDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public Timestamp getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Timestamp expiredDate) {
        this.expiredDate = expiredDate;
    }

}
