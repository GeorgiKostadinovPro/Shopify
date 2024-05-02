package models.products;

import java.math.BigDecimal;
import java.time.LocalDate;

public abstract class Product implements models.products.contracts.Product {
    private int id;
    private String name;
    private BigDecimal deliveryPrice;
    private LocalDate expirationDate;

    protected Product(
            int _id,
            String _name,
            BigDecimal _deliveryPrice,
            LocalDate _expirationDate)
    {
        setId(_id);
        setName(_name);
        setDeliveryPrice(_deliveryPrice);
        setExpirationDate(_expirationDate);
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public BigDecimal getDeliveryPrice() {
        return this.deliveryPrice;
    }

    public LocalDate getExpirationDate() {
        return this.expirationDate;
    }

    private void setId(int _id) {
        if (_id <= 0) {
            throw new IllegalArgumentException("Id must be greater than 0.");
        }

        this.id = _id;
    }

    private void setName(String _name) {
        if (_name.isEmpty()) {
            throw new IllegalArgumentException("Name must NOT be empty.");
        }

        this.name = _name;
    }

    private void setDeliveryPrice(BigDecimal _deliveryPrice) {
        if (_deliveryPrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Unit delivery price must be greater than zero.");
        }

        this.deliveryPrice = _deliveryPrice;
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
}
