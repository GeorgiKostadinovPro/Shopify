package models.products;

import common.messages.ExceptionMessages;
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
        this.setId(_id);
        this.setName(_name);
        this.setQuantity(_quantity);
        this.setDeliveryPrice(_deliveryPrice);
        this.setExpirationDate(_expirationDate);
    }

    public int getId() {
        return this.id;
    }

    private void setId(int _id) {
        if (_id <= 0) {
            throw new IllegalArgumentException(ExceptionMessages.INVALID_IDENTIFIER);
        }

        this.id = _id;
    }

    public String getName() {
        return this.name;
    }

    private void setName(String _name) {
        if (_name == null || _name.isEmpty()) {
            throw new IllegalArgumentException(ExceptionMessages.INVALID_NAME);
        }

        this.name = _name;
    }

    public int getQuantity() {
        return this.quantity;
    }

    private void setQuantity(int _quantity) {
        if (_quantity <= 0) {
            throw new IllegalArgumentException(ExceptionMessages.INVALID_PRODUCT_QUANTITY);
        }

        this.quantity = _quantity;
    }

    public void increaseQuantity(int _quantity) {
        if (_quantity <= 0) {
            throw new IllegalArgumentException(ExceptionMessages.INVALID_PRODUCT_QUANTITY);
        }

        this.quantity += _quantity;
    }

    public void decreaseQuantity(int _quantity) {
        if (_quantity <= 0) {
            throw new IllegalArgumentException(ExceptionMessages.INVALID_PRODUCT_QUANTITY);
        }

        if (this.quantity - _quantity < 0) {
            throw new IllegalArgumentException(ExceptionMessages.INVALID_QUANTITY_AMOUNT_TO_REDUCE);
        }

        this.quantity -= _quantity;
    }

    public BigDecimal getDeliveryPrice() {
        return this.deliveryPrice;
    }

    /*This is the price when we deliver the product in the shop*/
    private void setDeliveryPrice(BigDecimal _deliveryPrice) {
        if (_deliveryPrice == null || _deliveryPrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(ExceptionMessages.INVALID_DELIVERY_PRICE);
        }

        this.deliveryPrice = _deliveryPrice;
    }

    public LocalDate getExpirationDate() {
        return this.expirationDate;
    }

    private void setExpirationDate(LocalDate _expirationDate) {
        if (!_expirationDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException(ExceptionMessages.INVALID_EXPIRATION_DATE);
        }

        this.expirationDate = _expirationDate;
    }

    public boolean isExpired() {
        LocalDate currentDate = LocalDate.now();
        return currentDate.isAfter(this.expirationDate);
    }

    //This is the price when clients buy the product.
    // Virtual Method. Descendants have different logic.
    public BigDecimal calculateFinalPrice()
    {
        if (this.isExpired()) {
            throw new IllegalStateException(ExceptionMessages.PRODUCT_ALREADY_EXPIRED);
        }

        return BigDecimal.ZERO;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Product ID: ").append(this.id).append("\n");
        sb.append("Name: ").append(this.name).append("\n");
        sb.append("Category: ").append(this.getClass().getSimpleName()).append("\n");
        sb.append("Quantity: ").append(this.quantity).append("\n");
        sb.append("Delivery Price: $").append(DecimalFormatter.format(this.deliveryPrice)).append("\n");
        sb.append("Expiration Date: ").append(DateFormatter.formatLocalDate(this.expirationDate)).append("\n");

        return sb.toString().trim();
    }
}
