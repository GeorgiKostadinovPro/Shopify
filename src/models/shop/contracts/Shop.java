package models.shop.contracts;

import models.cashiers.contracts.Cashier;
import models.clients.contracts.Client;
import models.products.contracts.Product;
import models.receipts.Receipt;

import java.math.BigDecimal;

public interface Shop {
    int getId();
    int getTotalReceipts();
    public String getShortInfo();

    BigDecimal calculateTotalCashierSalariesExpenses();
    BigDecimal calculateTotalProductsDeliveryExpenses();
    BigDecimal calculateTotalIncomeFromSoldProducts();
    BigDecimal calculateTotalGeneralIncome();

    void addProduct(Product _product);
    void removeProduct(int _productId);

    void addCashier(String _name, BigDecimal _monthlySalary);
    void removeCashier(int _cashierId);

    void addCheckout();
    void removeCheckout(int _checkoutId);

    void addClient(String _name, BigDecimal _budget);
    void removeClient(int _clientId);

    Receipt processCheckout(Client _client);
}
