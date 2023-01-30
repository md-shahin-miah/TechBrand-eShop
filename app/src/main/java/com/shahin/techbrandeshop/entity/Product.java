package com.shahin.techbrandeshop.entity;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.List;

@Entity(tableName = "products")
public class Product implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int productID;
    private String productName;
    private String description;
    private String brand;
    private double price;
    private double discount;
    private boolean isFreeShipping;
    private double rate;
    private int quantityInStock;

    @Embedded(prefix = "product_spec_")
    private Specification specification;

    private List<String> productImages;

    public Product() {
    }

    @Ignore
    public Product(String productName, String description, String brand,
                   double price,
                   double discount, boolean isFreeShipping, double rate, int quantityInStock,
                   Specification specification, List<String> productImages) {
        this.productName = productName;
        this.description = description;
        this.brand = brand;
        this.price = price;
        this.discount = discount;
        this.isFreeShipping = isFreeShipping;
        this.rate = rate;
        this.quantityInStock = quantityInStock;
        this.specification = specification;
        this.productImages = productImages;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public boolean isFreeShipping() {
        return isFreeShipping;
    }

    public void setFreeShipping(boolean freeShipping) {
        isFreeShipping = freeShipping;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public Specification getSpecification() {
        return specification;
    }

    public void setSpecification(Specification specification) {
        this.specification = specification;
    }

    public List<String> getProductImages() {
        return productImages;
    }

    public void setProductImages(List<String> productImages) {
        this.productImages = productImages;
    }

    public String getPriceFormat() {
        return "đ " + new DecimalFormat("#,###").format(getPrice());
    }

    public String getDiscountFormat() {
        double discountPrice = getPrice() + (getPrice() * (getDiscount() / 100));
        return "đ " + new DecimalFormat("#,###").format(discountPrice);
    }
}