package models.contracts;

import models.Receipt;

public interface Checkout {
    int getId();
    Receipt processPayment(Cashier cashier, Client client);
}
