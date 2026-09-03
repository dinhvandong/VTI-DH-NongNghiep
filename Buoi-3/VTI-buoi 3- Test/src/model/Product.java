package model;

import java.util.Objects;

/**
 * Lớp đại diện cho một sản phẩm trong hệ thống.
 */
public class Product {
    private int id;
    private String name;
    private double price;
    private String category;
    private int quantity;

    public Product() {
    }

    public Product(int id, String name, double price, String category, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.quantity = quantity;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("Product [ID=%d, Name='%s', Price=%,.2f, Category='%s', Quantity=%d]",
                id, name, price, category, quantity);
    }

    public String toTableRow() {
        return String.format("| %-6d | %-25s | %-15s | %12.2f | %8d |",
                id, name, category, price, quantity);
    }
}
