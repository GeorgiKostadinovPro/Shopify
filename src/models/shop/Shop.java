package models.shop;

import common.exceptions.CashierNotExistException;
import common.exceptions.CheckoutNotExistException;
import common.exceptions.ExistingCashierException;
import common.messages.ExceptionMessages;
import models.cashiers.contracts.Cashier;
import models.checkouts.contracts.Checkout;
import models.clients.contracts.Client;
import models.products.contracts.Product;
import models.receipts.Receipt;
import repositories.CashierRepository;
import utilities.DecimalFormatter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Shop implements models.shop.contracts.Shop {
    private String name;

    private final Map<String, Receipt> receipts;
    private final CashierRepository cashiersRepository;
    private final Map<Integer, Checkout> checkouts;
    private final Map<Integer, Product> deliveredProducts;

    private Shop() {
        this.receipts = new HashMap<>();
        this.cashiersRepository = new CashierRepository();
        this.checkouts = new HashMap<>();
        this.deliveredProducts = new HashMap<>();
    }

    public Shop(String _name) {
        this();

        this.setName(_name);
    }

    private void setName(String _name) {
        if (_name == null || _name.isEmpty()) {
            throw new IllegalArgumentException(ExceptionMessages.INVALID_NAME);
        }

        this.name = _name;
    }

    public int getTotalReceipts() {
        return this.receipts.size();
    }

    public BigDecimal calculateTotalCashierSalariesExpenses() {
        return this.cashiersRepository
                .getAll()
                .stream()
                .map(Cashier::getMonthlySalary)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal calculateTotalProductsDeliveryExpenses() {
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

    public BigDecimal calculateTotalGeneralIncome() {
        BigDecimal totalIncomeFromSoldProducts = this.calculateTotalIncomeFromSoldProducts();
        BigDecimal totalShopCostForCashiersAndDelivery = this.calculateTotalCashierSalariesExpenses()
                                                                .add(this.calculateTotalProductsDeliveryExpenses());

        return totalIncomeFromSoldProducts.subtract(totalShopCostForCashiersAndDelivery);
    }

    public void addCashier(Cashier _cashier) {
        if (this.cashiersRepository.getById(_cashier.getId()) != null) {
            throw new ExistingCashierException(ExceptionMessages.CASHIER_ALREADY_EXISTS);
        }

        this.cashiersRepository.add(_cashier);
    }

    public void removeCashier(int _cashierId) {
        if (this.cashiersRepository.getById(_cashierId) == null) {
            throw new CashierNotExistException(ExceptionMessages.CASHIER_NOT_PRESENTED);
        }

        this.cashiersRepository.remove(_cashierId);
    }

    public void addCheckout() {
        int checkoutId = this.checkouts.size() + 1;
        Checkout checkout = new models.checkouts.Checkout(checkoutId);
        this.checkouts.put(checkoutId, checkout);
    }

    public void removeCheckout(int _checkoutId) {
        if (!this.checkouts.containsKey(_checkoutId)) {
            throw new CheckoutNotExistException(ExceptionMessages.CHECKOUT_NOT_PRESENTED);
        }

        this.checkouts.remove(_checkoutId);
    }

    public Receipt processCheckout(Client _client) {
        if (this.checkouts.isEmpty()) {
            throw new UnsupportedOperationException(ExceptionMessages.NO_AVAILABLE_CHECKOUTS);
        }

        if (this.cashiersRepository.getAll().isEmpty()) {
            throw new UnsupportedOperationException(ExceptionMessages.NO_AVAILABLE_CASHIERS);
        }

        Checkout checkout = this.checkouts.values().stream().findFirst().get();
        Cashier cashier = this.cashiersRepository.getAll().stream().findAny().get();

        Receipt receipt = checkout.processPayment(cashier, _client);
        this.receipts.put(receipt.getSerialNumber(), receipt);

        return receipt;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("--- Welcome to %s! ---\n", this.name));

        sb.append("\n");

        sb.append("--- Information ---\n");
        sb.append(String.format("Receipts: %d | Cashiers: %d | Checkouts: %d | Products: %d\n",
                this.receipts.size(), this.cashiersRepository.getAll().size(), this.checkouts.size(), this.deliveredProducts.size()));

        sb.append("\n");

        sb.append("--- Income and Expenses ---\n");
        sb.append(String.format("Total Cashiers Salaries Expenses: $%s\n", DecimalFormatter.format(this.calculateTotalCashierSalariesExpenses())));
        sb.append(String.format("Total Products Delivery Expenses: $%s\n", DecimalFormatter.format(this.calculateTotalProductsDeliveryExpenses())));
        sb.append(String.format("Total Income from Sold Products: $%s\n", DecimalFormatter.format(this.calculateTotalIncomeFromSoldProducts())));
        sb.append(String.format("Total General Income: $%s\n", DecimalFormatter.format(this.calculateTotalGeneralIncome())));

        sb.append("\n");

        sb.append("--- Cashiers Information ---\n");

        if (this.cashiersRepository.getAll().isEmpty()) {
            sb.append("No Available Cashiers!\n");
        } else {
            this.cashiersRepository.getAll().forEach(c -> sb.append(c.toString()));
        }

        sb.append("\n");

        sb.append("--- Products Information ---\n");

        if (this.deliveredProducts.isEmpty()) {
            sb.append("No Available Products!\n");
        } else {
            this.deliveredProducts.values().forEach(e -> sb.append(e.toString()));
        }

        return sb.toString().trim();
    }
}
