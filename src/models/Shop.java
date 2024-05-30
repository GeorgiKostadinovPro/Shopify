package models;

import common.exceptions.*;
import common.messages.ExceptionMessages;
import models.contracts.*;
import repositories.CashierRepository;
import repositories.CheckoutRepository;
import repositories.ProductRepository;
import utilities.DecimalFormatter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Shop implements IShop {
    private int id;
    private String name;

    private final Map<String, Receipt> receipts;

    private final ProductRepository productRepository;
    private final CashierRepository cashierRepository;
    private final CheckoutRepository checkoutRepository;

    private Shop() {
        this.receipts = new HashMap<>();

        this.productRepository = new ProductRepository();
        this.cashierRepository = new CashierRepository();
        this.checkoutRepository = new CheckoutRepository();
    }

    public Shop(int _id, String _name) {
        this();

        this.setId(_id);
        this.setName(_name);
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public BigDecimal calculateTotalCashierSalariesExpenses() {
        return this.cashierRepository
                .getAll()
                .stream()
                .map(ICashier::getMonthlySalary)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal calculateTotalProductsDeliveryExpenses() {
        return this.productRepository
                .getAll()
                .stream()
                .map(IProduct::getDeliveryPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal calculateTotalIncomeFromSoldProducts() {
        return this.receipts
                .values()
                .stream()
                .map(Receipt::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal calculateTotalGeneralIncome() {
        BigDecimal totalIncomeFromSoldProducts = this.calculateTotalIncomeFromSoldProducts();
        BigDecimal totalShopCostForCashiersAndDelivery = this.calculateTotalCashierSalariesExpenses()
                                                                .add(this.calculateTotalProductsDeliveryExpenses());

        return totalIncomeFromSoldProducts.subtract(totalShopCostForCashiersAndDelivery);
    }

    @Override
    public void addProduct(
            String _type,
            String _name,
            int _quantity,
            BigDecimal _deliveryPrice,
            LocalDate _expirationDate,
            BigDecimal _markupPercentage,
            int _approachingExpirationDays,
            BigDecimal _approachingExpirationDiscount
    ) {

        if (this.productRepository.getAll().stream().anyMatch(p -> p.getName().equals(_name))) {
            throw new ExistingProductException(ExceptionMessages.PRODUCT_ALREADY_EXISTS);
        }

        int productId = this.productRepository.getAll().size() + 1;

        IProduct product = new Product(
                productId,
                _type,
                _name,
                _quantity,
                _deliveryPrice,
                _expirationDate,
                _markupPercentage,
                _approachingExpirationDays,
                _approachingExpirationDiscount);

        this.productRepository.add(product);
    }

    @Override
    public void removeProduct(int _productId) {
        if (this.productRepository.getById(_productId) == null) {
            throw new ProductNotExistException(ExceptionMessages.INVALID_PRODUCT_ID);
        }

        this.productRepository.remove(_productId);
    }

    @Override
    public void addCashier(String _name, BigDecimal _monthlySalary) {
        int cashierId = this.cashierRepository.getAll().size() + 1;
        ICashier cashier =  new Cashier(cashierId, _name, _monthlySalary);
        this.cashierRepository.add(cashier);
    }

    @Override
    public void removeCashier(int _cashierId) {
        if (this.cashierRepository.getById(_cashierId) == null) {
            throw new CashierNotExistException(ExceptionMessages.CASHIER_NOT_PRESENTED);
        }

        this.cashierRepository.remove(_cashierId);
    }

    @Override
    public void addCheckout() {
        int checkoutId = this.checkoutRepository.getAll().size() + 1;
        ICheckout checkout = new Checkout(checkoutId);
        this.checkoutRepository.add(checkout);
    }

    @Override
    public void removeCheckout(int _checkoutId) {
        if (this.checkoutRepository.getById(_checkoutId) == null) {
            throw new CheckoutNotExistException(ExceptionMessages.CHECKOUT_NOT_PRESENTED);
        }

        this.checkoutRepository.remove(_checkoutId);
    }

    @Override
    public Receipt processCheckout(IClient _client) {
        ICheckout checkout = this.checkoutRepository
                .getAll().stream().findFirst().orElse(null);

        if (checkout == null) {
            throw new UnsupportedOperationException(ExceptionMessages.NO_AVAILABLE_CHECKOUTS);
        }

        ICashier cashier = this.cashierRepository
                .getAll().stream().findAny().orElse(null);

        if (cashier == null) {
            throw new UnsupportedOperationException(ExceptionMessages.NO_AVAILABLE_CASHIERS);
        }

        Receipt receipt = checkout.processPayment(cashier, _client);
        this.receipts.put(receipt.getSerialNumber(), receipt);

        return receipt;
    }

    @Override
    public String getShortInfo() {
        return String.format("ID: %d, Name: %s", this.id, this.name);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("--- Welcome to %s! ---\n", this.name));

        sb.append("\n");

        sb.append("--- Information ---\n");
        sb.append(String.format("Products: %d | Cashiers: %d | Checkouts: %d | Receipts: %d\n",
                this.productRepository.getAll().size(),
                this.cashierRepository.getAll().size(),
                this.checkoutRepository.getAll().size(),
                this.receipts.size()));

        sb.append("\n");

        sb.append("--- Income and Expenses ---\n");
        sb.append(String.format("Total Cashiers Salaries Expenses: $%s\n", DecimalFormatter.format(this.calculateTotalCashierSalariesExpenses())));
        sb.append(String.format("Total Products Delivery Expenses: $%s\n", DecimalFormatter.format(this.calculateTotalProductsDeliveryExpenses())));
        sb.append(String.format("Total Income from Sold Products: $%s\n", DecimalFormatter.format(this.calculateTotalIncomeFromSoldProducts())));
        sb.append(String.format("Total General Income: $%s\n", DecimalFormatter.format(this.calculateTotalGeneralIncome())));

        sb.append("\n");

        sb.append("--- Cashiers Information ---\n");

        if (this.cashierRepository.getAll().isEmpty()) {
            sb.append("No Available Cashiers!\n");
        } else {
            this.cashierRepository.getAll().forEach(c -> sb.append(c.toString()));
        }

        sb.append("\n");

        sb.append("--- Products Information ---\n");

        if (this.productRepository.getAll().isEmpty()) {
            sb.append("No Available Products!");
        } else {
            this.productRepository.getAll().forEach(e -> sb.append(e.toString()).append("\n\n"));
        }

        return sb.toString().trim();
    }

    private void setId(int _id) {
        if (_id <= 0) {
            throw new IllegalArgumentException(ExceptionMessages.INVALID_IDENTIFIER);
        }

        this.id = _id;
    }

    private void setName(String _name) {
        if (_name == null || _name.isEmpty()) {
            throw new IllegalArgumentException(ExceptionMessages.INVALID_NAME);
        }

        this.name = _name;
    }
}
