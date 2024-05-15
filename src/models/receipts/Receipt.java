package models.receipts;

import models.cashiers.contracts.Cashier;
import models.carts.CartItem;
import utilities.DateFormatter;
import utilities.DecimalFormatter;

import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

public class Receipt {
    private final String serialNumber;
    private final BigDecimal totalPrice;
    private final LocalDateTime issuanceDateTime;
    private final Cashier cashier;
    private final List<CartItem> cartItems;

    public Receipt(Cashier _cashier, List<CartItem> _cartItems, BigDecimal _totalPrice) {
        this.serialNumber = this.generateSerialNumber();

        this.cashier = _cashier;
        this.cartItems = _cartItems;
        this.totalPrice = _totalPrice;
        this.issuanceDateTime = LocalDateTime.now();
    }

    private String generateSerialNumber() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        int length = 10;

        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }

        return sb.toString().trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Receipt ").append(this.serialNumber).append("\n");
        sb.append("Issued by: ").append(this.cashier.getName()).append("\n");
        sb.append("Issuance Date: ").append(DateFormatter.formatLocalDateTime(this.issuanceDateTime)).append("\n");
        sb.append("Items:\n");

        for (CartItem item : this.cartItems) {
            sb.append(item.toString()).append("\n");
        }

        sb.append("------------------------------\n");
        sb.append("Total Price: ").append(DecimalFormatter.format(this.totalPrice)).append("\n");
        return sb.toString();
    }
}
