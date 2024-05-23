package models.shop;

import common.exceptions.CashierNotExistException;
import common.exceptions.ExistingCashierException;
import models.cashiers.contracts.Cashier;
import models.products.contracts.Product;
import models.receipts.Receipt;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Shop implements models.shop.contracts.Shop {
    private String name;

    private final Map<String, Receipt> receipts;
    private final Map<Integer, Cashier> cashiers;
    private final Map<Integer, Product> deliveredProducts;

    private Shop() {
        this.receipts = new HashMap<>();
        this.cashiers = new HashMap<>();
        this.deliveredProducts = new HashMap<>();
    }

    public Shop(String _name) {
        this();

        this.setName(_name);
    }

    private void setName(String _name) {
        if (_name == null || _name.isEmpty()) {
            throw new IllegalArgumentException("Name must NOT be empty.");
        }

        this.name = _name;
    }

    public BigDecimal calculateTotalCashierSalariesCost() {
        return this.cashiers
                .values()
                .stream()
                .map(Cashier::getMonthlySalary)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal calculateTotalProductsDeliveryCost() {
        return this.deliveredProducts
                .values()
                .stream()
                .map(Product::getDeliveryPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal calculateTotalIncomeFromSoldProducts() {
        return this.receipts
                .values()
                .stream()
                .map(Receipt::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal totalGeneralIncome() {
        BigDecimal totalIncomeFromSoldProducts = this.calculateTotalIncomeFromSoldProducts();
        BigDecimal totalShopCostForCashiersAndDelivery = this.calculateTotalCashierSalariesCost()
                                                                .add(this.calculateTotalProductsDeliveryCost());

        return totalIncomeFromSoldProducts.subtract(totalShopCostForCashiersAndDelivery);
    }

    public void addCashier(Cashier _cashier) {
        int cashierId = _cashier.getId();

        if (this.cashiers.containsKey(cashierId)) {
            throw new ExistingCashierException("Cashier with such Id is already presented!");
        }

        this.cashiers.put(cashierId, _cashier);
    }

    public void removeCashier(Integer _cashierId) {
        if (!this.cashiers.containsKey(_cashierId)) {
            throw new CashierNotExistException("Cashier with such Id does NOT exist!");
        }

        this.cashiers.remove(_cashierId);
    }
}
