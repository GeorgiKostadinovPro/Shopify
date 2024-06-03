import common.messages.ExceptionMessages;
import models.CartItem;
import models.Product;
import models.contracts.IProduct;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CartItemTest {

    private IProduct product;
    private CartItem cartItem;

    @BeforeEach
    void setUp() {
        this.product = new Product(
                1,
                "FoodProduct",
                "Test Product",
                10,
                BigDecimal.valueOf(10.5),
                LocalDate.of(2024, 10,10),
                BigDecimal.TEN,
                10,
                BigDecimal.TEN);


        this.cartItem = new CartItem(1, this.product, 2);
    }

    @AfterEach
    void tearDown() {
        this.product = null;
        this.cartItem = null;
    }

    @Test
    void testConstructor_ValidValues() {
        assertEquals(1, this.cartItem.getId());
        assertEquals(this.product, this.cartItem.getProduct());
        assertEquals(2, this.cartItem.getQuantity());
    }

    @Test
    void testConstructor_InvalidId() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new CartItem(0, this.product, 2);
        });

        assertEquals(ExceptionMessages.INVALID_IDENTIFIER, exception.getMessage());
    }

    @Test
    void testConstructor_NullProduct() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new CartItem(1, null, 2);
        });

        assertEquals(ExceptionMessages.INVALID_PRODUCT, exception.getMessage());
    }

    @Test
    void testConstructor_InvalidQuantity() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new CartItem(1, this.product, 0);
        });
        assertEquals(ExceptionMessages.INVALID_PRODUCT_QUANTITY, exception.getMessage());
    }

    @Test
    void testSetQuantity_Valid() {
        this.cartItem.setQuantity(3);
        assertEquals(3, this.cartItem.getQuantity());
    }

    @Test
    void testSetQuantity_Invalid() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            this.cartItem.setQuantity(0);
        });

        assertEquals(ExceptionMessages.INVALID_PRODUCT_QUANTITY, exception.getMessage());
    }

    @Test
    void testGetItemTotalPrice() {
        BigDecimal expectedTotal = new BigDecimal("23.10");
        assertEquals(expectedTotal, this.cartItem.getItemTotalPrice());
    }

    @Test
    void testToString() {
        String expectedString = "- Test Product (2 x $11.55 = $23.10)";
        assertEquals(expectedString, this.cartItem.toString());
    }
}