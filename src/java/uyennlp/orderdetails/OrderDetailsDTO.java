/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uyennlp.orderdetails;

import java.io.Serializable;

/**
 *
 * @author HP
 */
public class OrderDetailsDTO implements Serializable {

    private int id;
    private int orderId;
    private int tourId;
    private int quantity;

    public OrderDetailsDTO() {
    }

    public OrderDetailsDTO(int orderId, int tourId, int quantity) {
        this.orderId = orderId;
        this.tourId = tourId;
        this.quantity = quantity;
    }

    public OrderDetailsDTO(int id, int orderId, int tourId, int quantity) {
        this.id = id;
        this.orderId = orderId;
        this.tourId = tourId;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getTourId() {
        return tourId;
    }

    public void setTourId(int tourId) {
        this.tourId = tourId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
