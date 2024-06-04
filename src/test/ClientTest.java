import common.exceptions.InsufficientPaymentException;
import common.messages.ExceptionMessages;
import models.Client;
import models.Product;
import models.contracts.IProduct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {
    private Client client;
    private IProduct product;

    @BeforeEach
    void setUp() {
        client = new Client(1, "John Doe", new BigDecimal("100.00"));

        product = new Product(
                1,
                "FOOD",
                "Test Product",
                10,
                new BigDecimal("5.00"),
                LocalDate.now().plusDays(10),
                new BigDecimal("20.00"),
                5,
                new BigDecimal("10.00")
        );
    }

    @Test
    void testClientInitialization() {
        assertEquals(1, client.getId());
        assertEquals("John Doe", client.getName());
        assertEquals(new BigDecimal("100.00"), client.getBudget());
        assertNotNull(client.getCart());
        assertTrue(client.getCart().getCartItems().isEmpty());
    }

    @Test
    void testReduceBudgetSuccess() {
        client.reduceBudget(new BigDecimal("20.00"));
        assertEquals(new BigDecimal("80.00"), client.getBudget());
    }

    @Test
    void testReduceBudgetInsufficientFunds() {
        InsufficientPaymentException thrown = assertThrows(InsufficientPaymentException.class, () -> {
            client.reduceBudget(new BigDecimal("120.00"));
        });

        assertEquals(ExceptionMessages.INSUFFICIENT_BUDGET, thrown.getMessage());
    }

    @Test
    void testToString() {
        client.getCart().addProduct(product, 2);

        String expected = "Client: John Doe (ID: 1) has a budget of: $100.00\n" +
                "Cart Information: \n" +
                "- Test Product (2 x $6.00 = $12.00)\n" +
                "-------------------------\n" +
                "Total Price: 12.00";

        assertEquals(expected, client.toString().replace("\r", ""));
    }

    @Test
    void testSetIdInvalid() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            new Client(-1, "John Doe", new BigDecimal("100.00"));
        });

        assertEquals(ExceptionMessages.INVALID_IDENTIFIER, thrown.getMessage());
    }

    @Test
    void testSetNameInvalid() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            new Client(1, "", new BigDecimal("100.00"));
        });

        assertEquals(ExceptionMessages.INVALID_NAME, thrown.getMessage());
    }

    @Test
    void testSetBudgetInvalid() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            new Client(1, "John Doe", new BigDecimal("-1.00"));
        });

        assertEquals(ExceptionMessages.INVALID_BUDGET, thrown.getMessage());
    }
}