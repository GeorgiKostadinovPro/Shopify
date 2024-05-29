package models.contracts;

import models.Cart;

import java.math.BigDecimal;

public interface Client {
    int getId();
    String getName();
    BigDecimal getBudget();
    Cart getCart();
    void reduceBudget(BigDecimal amount);
}
