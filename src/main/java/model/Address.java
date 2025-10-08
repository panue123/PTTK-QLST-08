package model;

public class Address {
    private int id;
    private Customer customer;
    private String street;
    private String province;
    private String country;

    public Address() {}

    public Address(int id, Customer customer, String street, String province, String country) {
        this.id = id;
        this.customer = customer;
        this.street = street;
        this.province = province;
        this.country = country;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }
    public String getProvince() {
        return province;
    }
    public void setProvince(String province) {
        this.province = province;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
}
