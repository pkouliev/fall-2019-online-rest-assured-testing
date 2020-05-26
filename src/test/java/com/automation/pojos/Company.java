package com.automation.pojos;

/**
 * "company": {
 * *         "companyId": 11783,
 * *         "companyName": "Cybertek",
 * *         "title": "SDET",
 * *         "startDate": "02/01/2015",
 * *         "address": {
 * *             "addressId": 11803,
 * *             "street": "123 Main Street",
 * *             "city": "New York",
 * *             "state": "NY",
 * *             "zipCode": 10001
 * *         }
 */
public class Company {
    private int companyId;
    private String companyName;
    private String title;
    private String startDate;
    private Address address; //Company has Address, does not extend Address

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Company{" +
                "companyId=" + companyId +
                ", companyName='" + companyName + '\'' +
                ", title='" + title + '\'' +
                ", startDate='" + startDate + '\'' +
                ", address=" + address +
                '}';
    }
}
