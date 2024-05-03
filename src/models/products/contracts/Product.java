package models.products.contracts;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface Product {
    int getId();
    String getName();
    BigDecimal getDeliveryPrice();
    LocalDate getExpirationDate();
    BigDecimal calculateTotalPrice();
    boolean isExpired();
}
