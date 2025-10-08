package model;

import java.time.LocalDateTime;
import java.util.List;

public class OnlineOrder {
    private int id;
    private Customer customer;
    private Address address;
    private String status;
    private float totalPrice;
    private LocalDateTime orderDate;

    public OnlineOrder() {}

    public OnlineOrder(int id, Customer customer, Address address, String status, float totalPrice, LocalDateTime orderDate) {
        this.customer = customer;
        this.address = address;
        this.status = status;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public LocalDateTime getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }
}
