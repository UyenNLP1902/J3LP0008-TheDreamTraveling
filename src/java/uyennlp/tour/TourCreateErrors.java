/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uyennlp.tour;

import java.io.Serializable;

/**
 *
 * @author HP
 */
public class TourCreateErrors implements Serializable {

    private String emptyTourName;
    private String emptyPrice;
    private String wrongPriceFormat;
    private String emptyQuota;
    private String wrongQuotaFormat;
    private String emptyImage;
    private String sameDestination;
    private String invalidFromDate;
    private String invalidToDate;
    private String sameDate;

    public TourCreateErrors() {
    }

    public String getEmptyTourName() {
        return emptyTourName;
    }

    public void setEmptyTourName(String emptyTourName) {
        this.emptyTourName = emptyTourName;
    }

    public String getEmptyPrice() {
        return emptyPrice;
    }

    public void setEmptyPrice(String emptyPrice) {
        this.emptyPrice = emptyPrice;
    }

    public String getWrongPriceFormat() {
        return wrongPriceFormat;
    }

    public void setWrongPriceFormat(String wrongPriceFormat) {
        this.wrongPriceFormat = wrongPriceFormat;
    }

    public String getEmptyQuota() {
        return emptyQuota;
    }

    public void setEmptyQuota(String emptyQuota) {
        this.emptyQuota = emptyQuota;
    }

    public String getWrongQuotaFormat() {
        return wrongQuotaFormat;
    }

    public void setWrongQuotaFormat(String wrongQuotaFormat) {
        this.wrongQuotaFormat = wrongQuotaFormat;
    }

    public String getEmptyImage() {
        return emptyImage;
    }

    public void setEmptyImage(String emptyImage) {
        this.emptyImage = emptyImage;
    }

    public String getSameDestination() {
        return sameDestination;
    }

    public void setSameDestination(String sameDestination) {
        this.sameDestination = sameDestination;
    }

    public String getInvalidFromDate() {
        return invalidFromDate;
    }

    public void setInvalidFromDate(String invalidFromDate) {
        this.invalidFromDate = invalidFromDate;
    }

    public String getInvalidToDate() {
        return invalidToDate;
    }

    public void setInvalidToDate(String invalidToDate) {
        this.invalidToDate = invalidToDate;
    }

    public String getSameDate() {
        return sameDate;
    }

    public void setSameDate(String sameDate) {
        this.sameDate = sameDate;
    }

}
