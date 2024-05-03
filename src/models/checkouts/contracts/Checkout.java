package models.checkouts.contracts;

import models.cashiers.contracts.Cashier;
import models.products.contracts.Product;

import java.math.BigDecimal;

public interface Checkout {
    int getId();
    void processPurchase(Cashier cashier, Product product, int quantity, BigDecimal paymentAmount);
}
