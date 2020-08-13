/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uyennlp.tour;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author HP
 */
public class TourDTO implements Serializable {

    private int id;
    private String name;
    private Timestamp fromDate;
    private Timestamp toDate;
    private Timestamp importDate;
    private int fromPlace;
    private int toPlace;
    private int price;
    private int quota;
    private int emptySeat;
    private String image;
    private boolean status;

    public TourDTO() {
    }

    public TourDTO(int id, String name, Timestamp fromDate, Timestamp toDate, int fromPlace, int toPlace, int price, int quota, int emptySeat, String image) {
        this.id = id;
        this.name = name;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.fromPlace = fromPlace;
        this.toPlace = toPlace;
        this.price = price;
        this.quota = quota;
        this.emptySeat = emptySeat;
        this.image = image;
    }

    public TourDTO(int id, String name, Timestamp fromDate, Timestamp toDate, Timestamp importDate, int fromPlace, int toPlace, int price, int quota, int emptySeat, String image, boolean status) {
        this.id = id;
        this.name = name;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.importDate = importDate;
        this.fromPlace = fromPlace;
        this.toPlace = toPlace;
        this.price = price;
        this.quota = quota;
        this.emptySeat = emptySeat;
        this.image = image;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getFromDate() {
        return fromDate;
    }

    public void setFromDate(Timestamp fromDate) {
        this.fromDate = fromDate;
    }

    public Timestamp getToDate() {
        return toDate;
    }

    public void setToDate(Timestamp toDate) {
        this.toDate = toDate;
    }

    public Timestamp getImportDate() {
        return importDate;
    }

    public void setImportDate(Timestamp importDate) {
        this.importDate = importDate;
    }

    public int getFromPlace() {
        return fromPlace;
    }

    public void setFromPlace(int fromPlace) {
        this.fromPlace = fromPlace;
    }

    public int getToPlace() {
        return toPlace;
    }

    public void setToPlace(int toPlace) {
        this.toPlace = toPlace;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuota() {
        return quota;
    }

    public void setQuota(int quota) {
        this.quota = quota;
    }

    public int getEmptySeat() {
        return emptySeat;
    }

    public void setEmptySeat(int emptySeat) {
        this.emptySeat = emptySeat;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}
