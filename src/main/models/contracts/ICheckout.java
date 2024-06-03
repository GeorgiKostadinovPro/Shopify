package models.contracts;

import models.Receipt;

public interface ICheckout {
    int getId();
    Receipt processPayment(ICashier cashier, IClient client);
}
