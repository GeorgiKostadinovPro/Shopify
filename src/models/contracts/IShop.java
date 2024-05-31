package models.contracts;

import models.Receipt;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;

public interface IShop {
    int getId();
    public String getShortInfo();
    public Collection<IProduct> getProducts();

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

    Receipt processCheckout(IClient _client);
}
