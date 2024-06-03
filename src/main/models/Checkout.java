package models;

import common.exceptions.InsufficientPaymentException;

import common.messages.ExceptionMessages;
import models.contracts.ICashier;
import models.contracts.IClient;
import models.contracts.ICheckout;

import java.math.BigDecimal;
import java.util.List;

public class Checkout implements ICheckout {
    private int id;

    public Checkout(int _id) {
        this.setId(_id);
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public Receipt processPayment(ICashier cashier, IClient client) {
        Cart cart = client.getCart();

        BigDecimal totalPrice = cart.getTotalPrice();

        if (client.getBudget().compareTo(totalPrice) < 0) {
            throw new InsufficientPaymentException(ExceptionMessages.INSUFFICIENT_BUDGET);
        }

        client.reduceBudget(totalPrice);
        List<CartItem> items = List.copyOf(cart.getCartItems().values());
        cart.clearCart();

        return new Receipt(cashier, items, totalPrice);
    }

    private void setId(int _id) {
        if (_id <= 0) {
            throw new IllegalArgumentException(ExceptionMessages.INVALID_IDENTIFIER);
        }

        this.id = _id;
    }
}
