package models;

import common.exceptions.InsufficientQuantityException;
import common.exceptions.ProductNotExistException;
import common.messages.ExceptionMessages;
import models.contracts.IProduct;
import utilities.DecimalFormatter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Cart {
    private int id;
    private BigDecimal totalPrice;
    private final Map<Integer, CartItem> cartItems;

    private Cart() {
        this.totalPrice = BigDecimal.ZERO;

        this.cartItems = new HashMap<>();
    }

    public Cart(int _id) {
        this();

        this.setId(_id);
    }

    public int getId() {
        return this.id;
    }

    public void clearCart() {
        this.cartItems.clear();
        this.totalPrice = BigDecimal.ZERO;
    }

    public Map<Integer, CartItem> getCartItems() {
        return this.cartItems;
    }

    public BigDecimal getTotalPrice() {
        return this.totalPrice;
    }

    public void addProduct(IProduct product, int desiredQuantity) {
        int productId = product.getId();

        if (desiredQuantity > product.getQuantity()) {
            throw new InsufficientQuantityException(
                    String.format(ExceptionMessages.INSUFFICIENT_QUANTITY,
                            product.getName(), product.getQuantity(), (desiredQuantity - product.getQuantity())));
        }

        product.decreaseQuantity(desiredQuantity);

        CartItem cartItem;

        if (!this.cartItems.containsKey(productId)) {
            cartItem = new CartItem(productId, product, desiredQuantity);
            this.cartItems.put(productId, cartItem);
        } else {
            cartItem = this.cartItems.get(productId);
            int newQuantity = cartItem.getQuantity() + desiredQuantity;
            cartItem.setQuantity(newQuantity);
        }

        this.totalPrice = this.totalPrice.add(cartItem.getItemTotalPrice());
    }

    public void removeProduct(IProduct product) {
        int productId = product.getId();

        if (!this.cartItems.containsKey(productId)) {
            throw new ProductNotExistException(ExceptionMessages.INVALID_PRODUCT_ID);
        }

        CartItem cartItem = this.cartItems.get(productId);

        this.cartItems.remove(productId);

        this.totalPrice = this.totalPrice.subtract(cartItem.getItemTotalPrice());

        product.increaseQuantity(cartItem.getQuantity());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Cart Information: \n");

        if (this.cartItems.isEmpty()) {
            sb.append("No cart items found.\n");
        } else {
            for (CartItem cartItem : this.cartItems.values()) {
                sb.append(cartItem.toString()).append("\n");
            }

            sb.append("-------------------------\n");
            sb.append("Total Price: ").append(DecimalFormatter.format(this.getTotalPrice())).append("\n");
        }

        return sb.toString().trim();
    }

    private void setId(int _id) {
        if (_id <= 0) {
            throw new IllegalArgumentException(ExceptionMessages.INVALID_IDENTIFIER);
        }

        this.id = _id;
    }
}
