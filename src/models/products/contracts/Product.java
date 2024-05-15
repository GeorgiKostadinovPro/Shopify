package models.products.contracts;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface Product {
    int getId();
    String getName();
    int getQuantity();
    void increaseQuantity(int quantity);
    void decreaseQuantity(int quantity);
    BigDecimal getDeliveryPrice();
    LocalDate getExpirationDate();
    BigDecimal calculateFinalPrice();
    boolean isExpired();
}
