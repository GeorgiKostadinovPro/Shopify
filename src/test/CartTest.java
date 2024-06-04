import common.exceptions.InsufficientQuantityException;
import common.exceptions.ProductNotExistException;
import common.messages.ExceptionMessages;
import models.Cart;
import models.Product;
import models.contracts.IProduct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CartTest {
    private Cart cart;
    private IProduct product;

    @BeforeEach
    void setUp() {
        cart = new Cart(1);
        product = new Product(
                1,
                "FoodProduct",
                "Test Product",
                10,
                new BigDecimal("5.00"),
                LocalDate.of(2024, 10, 10),
                new BigDecimal("20.00"),
                5,
                new BigDecimal("10.00")
        );
    }

    @Test
    void testAddProductSuccess() {
        cart.addProduct(product, 2);

        assertEquals(1, cart.getCartItems().size());
        assertEquals(new BigDecimal("12.0000"), cart.getTotalPrice());
        assertEquals(8, product.getQuantity());
    }

    @Test
    void testAddProductInsufficientQuantity() {
        InsufficientQuantityException thrown = assertThrows(InsufficientQuantityException.class, () -> {
            cart.addProduct(product, 11);
        });

        assertEquals(String.format(ExceptionMessages.INSUFFICIENT_QUANTITY, "Test Product", 10, 1), thrown.getMessage());
    }

    @Test
    void testRemoveProductSuccess() {
        cart.addProduct(product, 2);

        cart.removeProduct(product);

        assertEquals(0, cart.getCartItems().size());
        assertEquals(new BigDecimal("0.0000"), cart.getTotalPrice());
        assertEquals(10, product.getQuantity());
    }

    @Test
    void testRemoveProductNotExist() {
        ProductNotExistException thrown = assertThrows(ProductNotExistException.class, () -> {
            cart.removeProduct(product);
        });

        assertEquals(ExceptionMessages.INVALID_PRODUCT_ID, thrown.getMessage());
    }

    @Test
    void testClearCart() {
        cart.addProduct(product, 2);
        cart.clearCart();

        assertEquals(0, cart.getCartItems().size());
        assertEquals(BigDecimal.ZERO, cart.getTotalPrice());
    }

    @Test
    void testToStringEmptyCart() {
        String expected = "Cart Information: \nNo cart items found.";
        assertEquals(expected, cart.toString());
    }

    @Test
    void testToStringWithItems() {
        cart.addProduct(product, 2);

        String expected = "Cart Information: \n" +
                "- Test Product (2 x $6.00 = $12.00)\n" +
                "-------------------------\n" +
                "Total Price: 12.00";

        assertEquals(expected, cart.toString().replace("\r", ""));
    }
}