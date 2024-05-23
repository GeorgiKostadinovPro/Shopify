package models.shop.contracts;

import models.cashiers.contracts.Cashier;

import java.math.BigDecimal;

public interface Shop {
    public BigDecimal calculateTotalCashierSalariesCost();
    public BigDecimal calculateTotalProductsDeliveryCost();
    public BigDecimal calculateTotalIncomeFromSoldProducts();
    public BigDecimal totalGeneralIncome();

    public void addCashier(Cashier _cashier);
    public void removeCashier(Integer _cashierId);
}
