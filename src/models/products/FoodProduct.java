package models.products;

import common.messages.ExceptionMessages;
import utilities.DecimalFormatter;

import java.math.BigDecimal;
import java.time.LocalDate;

public class FoodProduct extends Product {
    private BigDecimal markupPercentage;
    private int approachingExpirationDays;
    private BigDecimal approachingExpirationDiscount;

    public FoodProduct(
            int _id,
            String _name,
            int _quantity,
            BigDecimal _deliveryPrice,
            LocalDate _expirationDate,
            BigDecimal _markupPercentage,
            int _approachingExpirationDays,
            BigDecimal _approachingExpirationDiscount)
    {
        super(_id, _name, _quantity, _deliveryPrice, _expirationDate);

        this.setMarkupPercentage(_markupPercentage);
        this.setApproachingExpirationDays(_approachingExpirationDays);
        this.setApproachingExpirationDiscount(_approachingExpirationDiscount);
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

    @Override
    public BigDecimal calculateFinalPrice() {
        super.calculateFinalPrice();

        BigDecimal valueToMultiplyPrice = BigDecimal.ONE.add(
                this.markupPercentage.divide(BigDecimal.valueOf(100))
        );

        if (this.checkForExpirationDiscount()) {
            BigDecimal discount = BigDecimal.ONE.subtract(
                    this.approachingExpirationDiscount.divide(BigDecimal.valueOf(100))
            );

            return super.getDeliveryPrice().multiply(valueToMultiplyPrice).multiply(discount);
        }

        return super.getDeliveryPrice().multiply(valueToMultiplyPrice);
    }

    private boolean checkForExpirationDiscount() {
        LocalDate currentDate = LocalDate.now();

        return super.getExpirationDate().minusDays(this.approachingExpirationDays).isBefore(currentDate);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(super.toString());
        sb.append("\n --- More Information --- \n");
        sb.append("Markup Percentage: ").append(this.markupPercentage).append("%\n");
        sb.append("Expiration Discount: ").append(this.approachingExpirationDiscount).append("%\n");
        sb.append("Is Discount Applied: ").append(this.checkForExpirationDiscount()).append("\n");
        sb.append("Total Price: $").append(DecimalFormatter.format(this.calculateFinalPrice())).append("\n");

        return sb.toString().trim();
    }
}
