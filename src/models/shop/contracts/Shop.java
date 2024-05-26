package models.shop.contracts;

import models.cashiers.contracts.Cashier;
import models.clients.contracts.Client;
import models.receipts.Receipt;

import java.math.BigDecimal;

public interface Shop {
    int getId();
    int getTotalReceipts();

    BigDecimal calculateTotalCashierSalariesExpenses();
    BigDecimal calculateTotalProductsDeliveryExpenses();
    BigDecimal calculateTotalIncomeFromSoldProducts();
    BigDecimal calculateTotalGeneralIncome();

    void addCashier(Cashier _cashier);
    void removeCashier(int _cashierId);

    void addCheckout();
    void removeCheckout(int _checkoutId);

    Receipt processCheckout(Client _client);
}
