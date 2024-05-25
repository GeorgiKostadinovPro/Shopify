package models.clients;

import common.exceptions.InsufficientPaymentException;
import common.messages.ExceptionMessages;
import models.carts.Cart;
import utilities.DecimalFormatter;

import java.math.BigDecimal;

public class Client implements models.clients.contracts.Client {
    private int id;
    private String name;
    private BigDecimal budget;
    private final Cart cart;

    public Client(int _id, String _name, BigDecimal _budget) {
        this.setId(_id);
        this.setName(_name);
        this.setBudget(_budget);

        this.cart = new Cart(this.id);
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

    public String getName() {
        return this.name;
    }

    private void setName(String _name) {
        if (_name == null || _name.isEmpty()) {
            throw new IllegalArgumentException(ExceptionMessages.INVALID_NAME);
        }

        this.name = _name;
    }

    public BigDecimal getBudget() {
        return this.budget;
    }

    private void setBudget(BigDecimal _budget) {
        if (_budget == null || _budget.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(ExceptionMessages.INVALID_BUDGET);
        }

        this.budget = _budget;
    }

    public Cart getCart() {
        return this.cart;
    }

    public void reduceBudget(BigDecimal _amount) {
        if (this.budget.compareTo(_amount) < 0) {
            throw new InsufficientPaymentException(ExceptionMessages.INSUFFICIENT_BUDGET);
        }

        this.budget = this.budget.subtract(_amount);
    }

    @Override
    public String toString() {
        return "Client [id: " + this.id + ", name: " + this.name + ", budget: $" + DecimalFormatter.format(this.budget) + "]";
    }
}
