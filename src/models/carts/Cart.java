package models.carts;

import common.exceptions.InsufficientQuantityException;
import common.exceptions.ProductNotExistException;
import models.products.contracts.Product;

import java.math.BigDecimal;
import java.text.DecimalFormat;
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

        setId(_id);
    }

    public int getId() {
        return this.id;
    }

    private void setId(int _id) {
        if (_id <= 0) {
            throw new IllegalArgumentException("Id must be greater than 0.");
        }

        this.id = _id;
    }

    public void addProduct(Product product, int desiredQuantity) {
        int productId = product.getId();

        if (desiredQuantity > product.getQuantity()) {
            throw new InsufficientQuantityException(
                                    "Insufficient quantity of " + product.getName() +
                                    ". Available quantity: " + product.getQuantity() +
                                    ", Required quantity: " + (desiredQuantity - product.getQuantity()));
        }

        CartItem cartItem;

        if (!cartItems.containsKey(productId)) {
            cartItem = new CartItem(productId, this.id, product, desiredQuantity);
            cartItems.put(productId, cartItem);
        } else {
            cartItem = cartItems.get(productId);
            int newQuantity = cartItem.getQuantity() + desiredQuantity;
            cartItem.setQuantity(newQuantity);
        }

        this.totalPrice = this.totalPrice.add(cartItem.getItemPrice());

        product.setQuantity(product.getQuantity() - desiredQuantity);
    }

    public void removeProduct(Product product) {
        int productId = product.getId();

        if (!this.cartItems.containsKey(productId)) {
            throw new ProductNotExistException("Product with such id does NOT exist!");
        }

        CartItem cartItem = cartItems.get(productId);

        cartItems.remove(productId);

        this.totalPrice = this.totalPrice.subtract(cartItem.getItemPrice());

        product.setQuantity(product.getQuantity() + cartItem.getQuantity());
    }

    public Map<Integer, CartItem> getCartItems() {
        return this.cartItems;
    }

    public BigDecimal getTotalPrice() {
        return this.totalPrice;
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#,##0.00");

        StringBuilder sb = new StringBuilder();

        sb.append("Cart Information: \n");

        for (CartItem cartItem : this.cartItems.values()) {
            sb.append(cartItem.toString()).append("\n");
        }

        sb.append("-------------------------\n");
        sb.append("Total Price: ").append(df.format(getTotalPrice())).append("\n");

        return sb.toString().trim();
    }
}
