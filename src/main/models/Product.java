package models;

import common.messages.ExceptionMessages;
import models.contracts.IProduct;
import models.enums.ProductType;
import utilities.DateFormatter;
import utilities.DecimalFormatter;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Product implements IProduct {
    private int id;
    private ProductType type;
    private String name;
    private int quantity;
    private BigDecimal deliveryPrice;
    private LocalDate expirationDate;
    private BigDecimal markupPercentage;
    private int approachingExpirationDays;
    private BigDecimal approachingExpirationDiscount;

    public Product(
            int _id,
            String type,
            String _name,
            int _quantity,
            BigDecimal _deliveryPrice,
            LocalDate _expirationDate,
            BigDecimal _markupPercentage,
            int _approachingExpirationDays,
            BigDecimal _approachingExpirationDiscount)
    {
        this.setId(_id);
        this.setType(type);
        this.setName(_name);
        this.setQuantity(_quantity);
        this.setDeliveryPrice(_deliveryPrice);
        this.setExpirationDate(_expirationDate);
        this.setMarkupPercentage(_markupPercentage);
        this.setApproachingExpirationDays(_approachingExpirationDays);
        this.setApproachingExpirationDiscount(_approachingExpirationDiscount);
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getType() {
        return this.type.toString();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getQuantity() {
        return this.quantity;
    }

    @Override
    public BigDecimal getDeliveryPrice() {
        return this.deliveryPrice;
    }

    @Override
    public void increaseQuantity(int _quantity) {
        if (_quantity <= 0) {
            throw new IllegalArgumentException(ExceptionMessages.INVALID_PRODUCT_QUANTITY);
        }

        this.quantity += _quantity;
    }

    @Override
    public void decreaseQuantity(int _quantity) {
        if (_quantity <= 0) {
            throw new IllegalArgumentException(ExceptionMessages.INVALID_PRODUCT_QUANTITY);
        }

        if (this.quantity - _quantity < 0) {
            throw new IllegalArgumentException(ExceptionMessages.INVALID_QUANTITY_AMOUNT_TO_REDUCE);
        }

        this.quantity -= _quantity;
    }

    @Override
    public LocalDate getExpirationDate() {
        return this.expirationDate;
    }

    @Override
    public boolean isExpired() {
        LocalDate currentDate = LocalDate.now();
        return currentDate.isAfter(this.expirationDate);
    }

    @Override
    public BigDecimal calculateFinalPrice()
    {
        if (this.isExpired()) {
            throw new IllegalStateException(ExceptionMessages.PRODUCT_ALREADY_EXPIRED);
        }

        BigDecimal valueToMultiplyPrice = BigDecimal.ONE.add(
                this.markupPercentage.divide(BigDecimal.valueOf(100))
        );

        if (this.checkForExpirationDiscount()) {
            BigDecimal discount = BigDecimal.ONE.subtract(
                    this.approachingExpirationDiscount.divide(BigDecimal.valueOf(100))
            );

            return this.getDeliveryPrice().multiply(valueToMultiplyPrice).multiply(discount);
        }

        return this.getDeliveryPrice().multiply(valueToMultiplyPrice);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Product ID: ").append(this.id).append("\n");
        sb.append("Name: ").append(this.name).append("\n");
        sb.append("Category: ").append(this.getClass().getSimpleName()).append("\n");
        sb.append("Quantity: ").append(this.quantity).append("\n");
        sb.append("Delivery Price: $").append(DecimalFormatter.format(this.deliveryPrice)).append("\n");
        sb.append("Expiration Date: ").append(DateFormatter.formatLocalDate(this.expirationDate)).append("\n");

        sb.append(" --- More Information --- ").append("\n");
        sb.append("Markup Percentage: ").append(this.markupPercentage).append("%\n");
        sb.append("Expiration Discount: ").append(this.approachingExpirationDiscount).append("%\n");
        sb.append("Is Discount Applied: ").append(this.checkForExpirationDiscount()).append("\n");
        sb.append("Total Price: $").append(DecimalFormatter.format(this.calculateFinalPrice())).append("\n");

        return sb.toString().trim();
    }

    private void setId(int _id) {
        if (_id <= 0) {
            throw new IllegalArgumentException(ExceptionMessages.INVALID_IDENTIFIER);
        }

        this.id = _id;
    }

    private void setType(String _type) {
        if (_type == null || _type.isEmpty()) {
            throw new IllegalArgumentException(ExceptionMessages.EMPTY_PRODUCT_TYPE);
        }

        try {
            this.type = ProductType.valueOf(_type);
        } catch (RuntimeException e) {
            throw new IllegalArgumentException(ExceptionMessages.INVALID_PRODUCT_TYPE);
        }
    }

    private void setName(String _name) {
        if (_name == null || _name.isEmpty()) {
            throw new IllegalArgumentException(ExceptionMessages.INVALID_NAME);
        }

        this.name = _name;
    }

    private void setQuantity(int _quantity) {
        if (_quantity <= 0) {
            throw new IllegalArgumentException(ExceptionMessages.INVALID_PRODUCT_QUANTITY);
        }

        this.quantity = _quantity;
    }

    private void setDeliveryPrice(BigDecimal _deliveryPrice) {
        if (_deliveryPrice == null || _deliveryPrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(ExceptionMessages.INVALID_DELIVERY_PRICE);
        }

        this.deliveryPrice = _deliveryPrice;
    }

    private void setExpirationDate(LocalDate _expirationDate) {
        if (!_expirationDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException(ExceptionMessages.INVALID_EXPIRATION_DATE);
        }

        this.expirationDate = _expirationDate;
    }

    private void setMarkupPercentage(BigDecimal _markupPercentage) {
        if (_markupPercentage.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(ExceptionMessages.INVALID_MARKUP_PERCENTAGE);
        }

        this.markupPercentage = _markupPercentage;
    }

    private void setApproachingExpirationDays(int _approachingExpirationDays) {
        if (_approachingExpirationDays < 0) {
            throw new IllegalArgumentException(ExceptionMessages.INVALID_APPROACHING_EXPIRATION_DAYS);
        }

        this.approachingExpirationDays = _approachingExpirationDays;
    }

    private void setApproachingExpirationDiscount(BigDecimal _approachingExpirationDiscount) {
        if (_approachingExpirationDiscount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(ExceptionMessages.INVALID_EXPIRATION_DISCOUNT);
        }

        this.approachingExpirationDiscount = _approachingExpirationDiscount;
    }

    private boolean checkForExpirationDiscount() {
        LocalDate currentDate = LocalDate.now();

        return this.getExpirationDate().minusDays(this.approachingExpirationDays).isBefore(currentDate);
    }
}
