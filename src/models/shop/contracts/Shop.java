package models.shop.contracts;

import models.cashiers.contracts.Cashier;
import models.clients.contracts.Client;
import models.receipts.Receipt;

import java.math.BigDecimal;

public interface Shop {
    int getTotalReceipts();

    BigDecimal calculateTotalCashierSalariesCost();
    BigDecimal calculateTotalProductsDeliveryCost();
    BigDecimal calculateTotalIncomeFromSoldProducts();
    BigDecimal calculateTotalGeneralIncome();

    void addCashier(Cashier _cashier);
    void removeCashier(Integer _cashierId);

    void addCheckout();
    void removeCheckout(Integer _checkoutId);

    Receipt processCheckout(Client _client);
}
