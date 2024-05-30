package models.contracts;

import models.Cart;

import java.math.BigDecimal;

public interface IClient {
    int getId();
    String getName();
    BigDecimal getBudget();
    Cart getCart();
    void reduceBudget(BigDecimal amount);
}
