package model;

import java.util.ArrayList;
import java.util.List;

public class OrderDetail {
    private int id;
    private OnlineOrder order;
    private Product product;
    private float price;
    private int quantity;

    public OrderDetail(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public OnlineOrder getOrder() {
        return order;
    }

    public void setOrder(OnlineOrder order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}