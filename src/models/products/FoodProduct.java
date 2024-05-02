package models.products;

import javax.naming.OperationNotSupportedException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

public class FoodProduct extends Product {
    private BigDecimal markupPercentage;
    private int approachingExpirationDays;
    private BigDecimal approachingExpirationDiscount;

    public FoodProduct(
            int _id,
            String _name,
            BigDecimal _deliveryPrice,
            LocalDate _expirationDate,
            BigDecimal _markupPercentage,
            int _approachingExpirationDays,
            BigDecimal _approachingExpirationDiscount)
    {
        super(_id, _name, _deliveryPrice, _expirationDate);

        setMarkupPercentage(_markupPercentage);
        setApproachingExpirationDays(_approachingExpirationDays);
        setApproachingExpirationDiscount(_approachingExpirationDiscount);
    }

    private void setMarkupPercentage(BigDecimal _markupPercentage) {
        if (_markupPercentage.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Markup percentage cannot be negative.");
        }

        this.markupPercentage = _markupPercentage;
    }

    private void setApproachingExpirationDays(int _approachingExpirationDays) {
        this.approachingExpirationDays = _approachingExpirationDays;
    }

    private void setApproachingExpirationDiscount(BigDecimal _approachingExpirationDiscount) {
        if (_approachingExpirationDiscount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Expiration discount cannot be negative.");
        }

        this.approachingExpirationDiscount = _approachingExpirationDiscount;
    }

    @Override
    public BigDecimal calculateTotalPrice() {
        if (super.isExpired()) {
            throw new IllegalStateException("The product has already expired.");
        }

        LocalDate currentDate = LocalDate.now();

        // deliveryPrice: 50, markUpPer: 5% => totalPrice = 50 * (1 + (5 / 100)) = 50 * 1.05 = 52.5
        // expirationDiscount: 5% => 52.5 * (1 - (5 / 100)) = 52.5 * 0.95 = 49.875
        // totalPrice => 49.875

        BigDecimal valueToMultiplyPrice = BigDecimal.ONE.add(
                this.markupPercentage.divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP)
        );

        if (super.getExpirationDate().minusDays(this.approachingExpirationDays).isBefore(currentDate)) {
            BigDecimal discount = BigDecimal.ONE.subtract(
                    this.approachingExpirationDiscount.divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP)
            );

            return super.getDeliveryPrice().multiply(valueToMultiplyPrice).multiply(discount);
        }

        return super.getDeliveryPrice().multiply(valueToMultiplyPrice);
    }
}
