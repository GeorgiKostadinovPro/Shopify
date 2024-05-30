package models.contracts;

import models.Receipt;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface Shop {
    int getId();
    int getTotalReceipts();
    public String getShortInfo();

    BigDecimal calculateTotalCashierSalariesExpenses();
    BigDecimal calculateTotalProductsDeliveryExpenses();
    BigDecimal calculateTotalIncomeFromSoldProducts();
    BigDecimal calculateTotalGeneralIncome();

    void addProduct(String type,
                    String _name,
                    int _quantity,
                    BigDecimal _deliveryPrice,
                    LocalDate _expirationDate,
                    BigDecimal _markupPercentage,
                    int _approachingExpirationDays,
                    BigDecimal _approachingExpirationDiscount);
    void removeProduct(int _productId);

    void addCashier(String _name, BigDecimal _monthlySalary);
    void removeCashier(int _cashierId);

    void addCheckout();
    void removeCheckout(int _checkoutId);

    void addClient(String _name, BigDecimal _budget);
    void removeClient(int _clientId);

    Receipt processCheckout(int _clientId);
}
