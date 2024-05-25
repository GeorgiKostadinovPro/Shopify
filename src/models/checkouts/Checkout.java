package models.checkouts;

import common.exceptions.InsufficientPaymentException;

import common.messages.ExceptionMessages;
import models.carts.Cart;
import models.carts.CartItem;
import models.cashiers.contracts.Cashier;
import models.clients.contracts.Client;
import models.receipts.Receipt;

import java.math.BigDecimal;
import java.util.List;

public class Checkout implements models.checkouts.contracts.Checkout {
    private int id;

    public Checkout(int _id) {
        this.setId(_id);
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

    public Receipt processPayment(Cashier cashier, Client client) {
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
}
