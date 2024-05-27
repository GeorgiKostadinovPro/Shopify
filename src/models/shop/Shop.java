package models.shop;

import common.exceptions.*;
import common.messages.ExceptionMessages;
import models.cashiers.contracts.Cashier;
import models.checkouts.contracts.Checkout;
import models.clients.contracts.Client;
import models.products.contracts.Product;
import models.receipts.Receipt;
import repositories.CashierRepository;
import repositories.CheckoutRepository;
import repositories.ClientRepository;
import repositories.ProductRepository;
import utilities.DecimalFormatter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Shop implements models.shop.contracts.Shop {
    private int id;
    private String name;

    private final Map<String, Receipt> receipts;

    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;
    private final CashierRepository cashierRepository;
    private final CheckoutRepository checkoutRepository;

    private Shop() {
        this.receipts = new HashMap<>();

        this.clientRepository = new ClientRepository();
        this.productRepository = new ProductRepository();
        this.cashierRepository = new CashierRepository();
        this.checkoutRepository = new CheckoutRepository();
    }

    public Shop(int _id, String _name) {
        this();

        this.setId(_id);
        this.setName(_name);
    }

    public int getId() {
        return this.id;
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

    public int getTotalReceipts() {
        return this.receipts.size();
    }

    public BigDecimal calculateTotalCashierSalariesExpenses() {
        return this.cashierRepository
                .getAll()
                .stream()
                .map(Cashier::getMonthlySalary)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal calculateTotalProductsDeliveryExpenses() {
        return this.productRepository
                .getAll()
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

    public void addProduct(Product _product) {
        if (this.productRepository.getById(_product.getId()) != null
                || this.productRepository.getAll().stream().anyMatch(p -> p.getName().equals(_product.getName()))) {
            throw new ExistingProductException(ExceptionMessages.PRODUCT_ALREADY_EXISTS);
        }

        this.productRepository.add(_product);
    }

    public void removeProduct(int _productId) {
        if (this.productRepository.getById(_productId) == null) {
            throw new ProductNotExistException(ExceptionMessages.INVALID_PRODUCT_ID);
        }

        this.checkoutRepository.remove(_productId);
    }

    public void addCashier(String _name, BigDecimal _monthlySalary) {
        int cashierId = this.cashierRepository.getAll().size() + 1;
        Cashier cashier =  new models.cashiers.Cashier(cashierId, _name, _monthlySalary);
        this.cashierRepository.add(cashier);
    }

    public void removeCashier(int _cashierId) {
        if (this.cashierRepository.getById(_cashierId) == null) {
            throw new CashierNotExistException(ExceptionMessages.CASHIER_NOT_PRESENTED);
        }

        this.cashierRepository.remove(_cashierId);
    }

    public void addCheckout() {
        int checkoutId = this.checkoutRepository.getAll().size() + 1;
        Checkout checkout = new models.checkouts.Checkout(checkoutId);
        this.checkoutRepository.add(checkout);
    }

    public void removeCheckout(int _checkoutId) {
        if (this.checkoutRepository.getById(_checkoutId) == null) {
            throw new CheckoutNotExistException(ExceptionMessages.CHECKOUT_NOT_PRESENTED);
        }

        this.checkoutRepository.remove(_checkoutId);
    }

    public void addClient(String _name, BigDecimal _budget) {
        int clientId = this.clientRepository.getAll().size() + 1;
        Client client = new models.clients.Client(clientId, _name, _budget);
        this.clientRepository.add(client);
    }

    public void removeClient(int _clientId) {
        if (this.clientRepository.getById(_clientId) == null) {
            throw new ClientNotExistException(ExceptionMessages.CLIENT_NOT_PRESENTED);
        }

        this.clientRepository.remove(_clientId);
    }

    public Receipt processCheckout(Client _client) {
        Checkout checkout = this.checkoutRepository
                .getAll().stream().findFirst().orElse(null);

        if (checkout == null) {
            throw new UnsupportedOperationException(ExceptionMessages.NO_AVAILABLE_CHECKOUTS);
        }

        Cashier cashier = this.cashierRepository
                .getAll().stream().findAny().orElse(null);

        if (cashier == null) {
            throw new UnsupportedOperationException(ExceptionMessages.NO_AVAILABLE_CASHIERS);
        }

        Receipt receipt = checkout.processPayment(cashier, _client);
        this.receipts.put(receipt.getSerialNumber(), receipt);

        return receipt;
    }

    public String getShortInfo() {
        return String.format("ID: %d, Name: %s", this.id, this.name);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("--- Welcome to %s! ---\n", this.name));

        sb.append("\n");

        sb.append("--- Information ---\n");
        sb.append(String.format("Clients: %d, Products: %d | Cashiers: %d, | Checkouts: %d | Receipts: %d\n",
                this.clientRepository.getAll().size(),
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

        sb.append("--- Clients Information ---\n");

        if (this.clientRepository.getAll().isEmpty()) {
            sb.append("No Available Clients!\n");
        } else {
            this.clientRepository.getAll().forEach(c -> sb.append(c.toString()).append("\n"));
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
}
