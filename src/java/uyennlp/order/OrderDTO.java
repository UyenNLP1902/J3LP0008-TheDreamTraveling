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
public class OrderDTO implements Serializable {

    private int id;
    private String customerId;
    private String customerName;
    private int total;
    private boolean couponUsed;

    public OrderDTO() {
    }

    public OrderDTO(int id, String customerId, String customerName, int total, boolean couponUsed) {
        this.id = id;
        this.customerId = customerId;
        this.customerName = customerName;
        this.total = total;
        this.couponUsed = couponUsed;
    }

    public OrderDTO(String customerId, String customerName, int total, boolean couponUsed) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.total = total;
        this.couponUsed = couponUsed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public boolean isCouponUsed() {
        return couponUsed;
    }

    public void setCouponUsed(boolean couponUsed) {
        this.couponUsed = couponUsed;
    }

}
