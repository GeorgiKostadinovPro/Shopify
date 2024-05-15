package models.checkouts.contracts;

import models.cashiers.contracts.Cashier;
import models.clients.contracts.Client;
import models.receipts.Receipt;

public interface Checkout {
    int getId();
    Receipt processPayment(Cashier cashier, Client client);
}
