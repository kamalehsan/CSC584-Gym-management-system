package com.gsm.model;

import java.sql.Date;

public class Member {
    private int id;
    private String fullname;
    private Date dob;
    private String gender;
    private String contactNumber;
    private String email;
    private String address;
    private String country;
    private String postcode;
    private String occupation;
    private int membershipTypeId;
    private Date expiryDate; // Add this field

    // Constructor
    public Member(int id, String fullname, Date dob, String gender, String contactNumber, String email, String address, String country, String postcode, String occupation, int membershipTypeId, Date expiryDate) {
        this.id = id;
        this.fullname = fullname;
        this.dob = dob;
        this.gender = gender;
        this.contactNumber = contactNumber;
        this.email = email;
        this.address = address;
        this.country = country;
        this.postcode = postcode;
        this.occupation = occupation;
        this.membershipTypeId = membershipTypeId;
        this.expiryDate = expiryDate;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public int getMembershipTypeId() {
        return membershipTypeId;
    }

    public void setMembershipTypeId(int membershipTypeId) {
        this.membershipTypeId = membershipTypeId;
    }

    public Date getExpiryDate() {
        return expiryDate; // Getter for expiryDate
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate; // Setter for expiryDate
    }

    // Add other methods if needed
}
