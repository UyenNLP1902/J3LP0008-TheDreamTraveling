/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uyennlp.orderdetails;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import uyennlp.tour.TourDTO;

/**
 *
 * @author HP
 */
public class OrderObject implements Serializable {

    private Map<TourDTO, Integer> items;

    public Map<TourDTO, Integer> getItems() {
        return items;
    }

    public int getTotalPrice() {
        int total = 0;
        if (this.items != null) {
            for (TourDTO tour : items.keySet()) {
                total += (tour.getPrice() * this.items.get(tour));
            }
        }
        return total;
    }

    private TourDTO searchForTourInfo(int id) {
        for (TourDTO tour : items.keySet()) {
            if (tour.getId() == id) {
                return tour;
            }
        }
        return null;
    }

    public void addTourToCart(TourDTO tour, int quantity) {
        if (this.items == null) {
            this.items = new HashMap<>();
        }

        TourDTO dto = searchForTourInfo(tour.getId());
        if (dto == null) {
            this.items.put(tour, quantity);
        } else {
            int total = this.items.get(dto);
            this.items.put(dto, total + quantity);
        }
    }

    public void UpdateTourFromCart(int id, int quantity) {
        if (this.items == null) {
            this.items = new HashMap<>();
        }

        TourDTO dto = searchForTourInfo(id);
        if (dto != null) {
            this.items.put(dto, quantity);
        }
    }

    public void removeTourFromCart(int id) {
        if (this.items == null) {
            return;
        }
        TourDTO dto = searchForTourInfo(id);
        if (dto != null) {
            this.items.remove(dto);

            if (this.items.isEmpty()) {
                this.items = null;
            }
        }
    }

    public void clearCart() {
        if (this.items != null) {
            items = null;
        }
    }
}
