package com.shrijee.rentcafe.model;

import java.io.Serializable;
import java.util.List;

public class Rent implements Serializable {

    private int rentId;
    private String rentName;
    private String address;
    private String pincode;
    private String city;
    private String state;
    private int renter;
    private float price;
    private String type;
    private String description;
    private String imageURL;
    private int noOfBedroom;
    private int noOfBathroom;
    private List<String> utilities;
    private int furnished;
    private int parking;
    private int microwave;
    private int hydro;
    private int heat;
    private int water;

    public Rent() {
    }

    public Rent(int rentId, String rentName, String address, String pincode, String city, String state, int renter, float price, String type, String description, String imageURL, int noOfBedroom, int noOfBathroom, List<String> utilities, int furnished, int parking) {
        this.rentId = rentId;
        this.rentName = rentName;
        this.address = address;
        this.pincode = pincode;
        this.city = city;
        this.state = state;
        this.renter = renter;
        this.price = price;
        this.type = type;
        this.description = description;
        this.imageURL = imageURL;
        this.noOfBedroom = noOfBedroom;
        this.noOfBathroom = noOfBathroom;
        this.utilities = utilities;
        this.furnished = furnished;
        this.parking = parking;
    }

    public Rent(String rentName, String address, String pincode, String city, String state, int renter, float price, String type, String description, String imageURL, int noOfBedroom, int noOfBathroom, List<String> utilities, int furnished, int parking) {
        this.rentName = rentName;
        this.address = address;
        this.pincode = pincode;
        this.city = city;
        this.state = state;
        this.renter = renter;
        this.price = price;
        this.type = type;
        this.description = description;
        this.imageURL = imageURL;
        this.noOfBedroom = noOfBedroom;
        this.noOfBathroom = noOfBathroom;
        this.utilities = utilities;
        this.furnished = furnished;
        this.parking = parking;
    }

    public int getMicrowave() {
        return microwave;
    }

    public void setMicrowave(int microwave) {
        this.microwave = microwave;
    }

    public int getHydro() {
        return hydro;
    }

    public void setHydro(int hydro) {
        this.hydro = hydro;
    }

    public int getHeat() {
        return heat;
    }

    public void setHeat(int heat) {
        this.heat = heat;
    }

    public int getWater() {
        return water;
    }

    public void setWater(int water) {
        this.water = water;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getNoOfBedroom() {
        return noOfBedroom;
    }

    public void setNoOfBedroom(int noOfBedroom) {
        this.noOfBedroom = noOfBedroom;
    }

    public int getNoOfBathroom() {
        return noOfBathroom;
    }

    public void setNoOfBathroom(int noOfBathroom) {
        this.noOfBathroom = noOfBathroom;
    }

    public List<String> getUtilities() {
        return utilities;
    }

    public void setUtilities(List<String> utilities) {
        this.utilities = utilities;
    }

    public int getFurnished() {
        return furnished;
    }

    public void setFurnished(int furnished) {
        this.furnished = furnished;
    }

    public int getParking() {
        return parking;
    }

    public void setParking(int parking) {
        this.parking = parking;
    }

    public int getRentId() {
        return rentId;
    }

    public void setRentId(int rentId) {
        this.rentId = rentId;
    }

    public String getRentName() {
        return rentName;
    }

    public void setRentName(String rentName) {
        this.rentName = rentName;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getRenter() {
        return renter;
    }

    public void setRenter(int renter) {
        this.renter = renter;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Rent(int rentId, String rentName, String address, String pincode, String city, String state, int renter, float price, String type, String description) {
        this.rentId = rentId;
        this.address = address;
        this.rentName = rentName;
        this.pincode = pincode;
        this.city = city;
        this.state = state;
        this.renter = renter;
        this.price = price;
        this.type = type;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Rent{" +
                "rentId=" + rentId +
                ", rentName='" + rentName + '\'' +
                ", address='" + address + '\'' +
                ", pincode='" + pincode + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", renter=" + renter +
                ", price=" + price +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", noOfBedroom=" + noOfBedroom +
                ", noOfBathroom=" + noOfBathroom +
                ", utilities=" + utilities +
                ", furnished=" + furnished +
                ", parking=" + parking +
                '}';
    }

}
