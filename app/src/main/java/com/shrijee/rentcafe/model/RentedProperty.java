package com.shrijee.rentcafe.model;


import java.util.Date;

public class RentedProperty {
    private int rentedPropertyId;
    private int propertyId;
    private int renterId;
    private int renteeId;
    private int duration;
    private Date startingDate;
    private int activeFlag;
    private Rent rent;

    public Rent getRent() {
        return rent;
    }

    public void setRent(Rent rent) {
        this.rent = rent;
    }

    public int getRentedPropertyId() {
        return rentedPropertyId;
    }

    public void setRentedPropertyId(int rentedPropertyId) {
        this.rentedPropertyId = rentedPropertyId;
    }

    public int getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }

    public int getRenterId() {
        return renterId;
    }

    public void setRenterId(int renterId) {
        this.renterId = renterId;
    }

    public int getRenteeId() {
        return renteeId;
    }

    public void setRenteeId(int renteeId) {
        this.renteeId = renteeId;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Date getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(Date startingDate) {
        this.startingDate = startingDate;
    }

    public int getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(int activeFlag) {
        this.activeFlag = activeFlag;
    }

    public RentedProperty() {
    }

    public RentedProperty(int propertyId, int renterId, int renteeId, int duration, Date startingDate, int activeFlag) {
        this.propertyId = propertyId;
        this.renterId = renterId;
        this.renteeId = renteeId;
        this.duration = duration;
        this.startingDate = startingDate;
        this.activeFlag = activeFlag;
    }

    public RentedProperty(int rentedPropertyId, int propertyId, int renterId, int renteeId, int duration, Date startingDate, int activeFlag) {
        this.rentedPropertyId = rentedPropertyId;
        this.propertyId = propertyId;
        this.renterId = renterId;
        this.renteeId = renteeId;
        this.duration = duration;
        this.startingDate = startingDate;
        this.activeFlag = activeFlag;
    }
}
