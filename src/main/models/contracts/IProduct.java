package models.contracts;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface IProduct {
    int getId();
    String getType();
    String getName();
    int getQuantity();
    void increaseQuantity(int quantity);
    void decreaseQuantity(int quantity);
    BigDecimal getDeliveryPrice();
    LocalDate getExpirationDate();
    BigDecimal calculateFinalPrice();
    boolean isExpired();
}
