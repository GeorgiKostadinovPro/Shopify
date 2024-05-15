package models.products;

import utilities.DateFormatter;
import utilities.DecimalFormatter;

import java.math.BigDecimal;
import java.time.LocalDate;

public abstract class Product implements models.products.contracts.Product {
    private int id;
    private String name;
    private int quantity;
    private BigDecimal deliveryPrice;
    private LocalDate expirationDate;

    protected Product(
            int _id,
            String _name,
            int _quantity,
            BigDecimal _deliveryPrice,
            LocalDate _expirationDate)
    {
        setId(_id);
        setName(_name);
        setQuantity(_quantity);
        setDeliveryPrice(_deliveryPrice);
        setExpirationDate(_expirationDate);
    }

    public int getId() {
        return this.id;
    }

    private void setId(int _id) {
        if (_id <= 0) {
            throw new IllegalArgumentException("Id must be greater than 0.");
        }

        this.id = _id;
    }

    public String getName() {
        return this.name;
    }

    private void setName(String _name) {
        if (_name == null || _name.isEmpty()) {
            throw new IllegalArgumentException("Name must NOT be empty.");
        }

        this.name = _name;
    }

    public int getQuantity() {
        return this.quantity;
    }

    private void setQuantity(int _quantity) {
        if (_quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0.");
        }

        this.quantity = _quantity;
    }

    public void increaseQuantity(int _quantity) {
        if (_quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0.");
        }

        this.quantity += _quantity;
    }

    public void decreaseQuantity(int _quantity) {
        if (_quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0.");
        }

        if (this.quantity - _quantity < 0) {
            throw new IllegalArgumentException("Amount too big. Cannot reduce the base quantity.");
        }

        this.quantity -= _quantity;
    }

    public BigDecimal getDeliveryPrice() {
        return this.deliveryPrice;
    }

    private void setDeliveryPrice(BigDecimal _deliveryPrice) {
        if (_deliveryPrice == null || _deliveryPrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Delivery price must be greater than zero.");
        }

        this.deliveryPrice = _deliveryPrice;
    }

    public LocalDate getExpirationDate() {
        return this.expirationDate;
    }

    private void setExpirationDate(LocalDate _expirationDate) {
        if (!_expirationDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Expiration date must be before current date.");
        }

        this.expirationDate = _expirationDate;
    }

    public boolean isExpired() {
        LocalDate currentDate = LocalDate.now();
        return currentDate.isAfter(this.expirationDate);
    }

    public abstract BigDecimal calculateTotalPrice();

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("--- Product Information ---\n");
        sb.append("Product ID: " + this.id + "\n");
        sb.append("Name: " + this.name + "\n");
        sb.append("Category: " + this.getClass().getSimpleName() + "\n");
        sb.append("Quantity: " + this.quantity + "\n");
        sb.append("Delivery Price: $" + DecimalFormatter.format(this.deliveryPrice) + "\n");
        sb.append("Expiration Date: " + DateFormatter.format(this.expirationDate) + "\n");

        return sb.toString().trim();
    }
}
