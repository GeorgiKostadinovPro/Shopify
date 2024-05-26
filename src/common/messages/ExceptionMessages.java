package common.messages;

public class ExceptionMessages {
    // Common
    public static final String INVALID_IDENTIFIER = "Id must be greater than 0!";
    public static final String INVALID_NAME = "Name must NOT be empty!";

    // Cashier
    public static final String INVALID_MONTHLY_SALARY = "Monthly salary must be greater than 0!";
    public static final String CASHIER_ALREADY_EXISTS = "Cashier with such Id is already presented!";
    public static final String CASHIER_NOT_PRESENTED = "Cashier with such Id does NOT exist!";
    public static final String NO_AVAILABLE_CASHIERS = "Cannot process checkout because there are NO cashiers available!";

    // Checkout
    public static final String INSUFFICIENT_BUDGET = "Your budget is way too low for payment!";
    public static final String CHECKOUT_NOT_PRESENTED = "Checkout with such Id does not exist!";
    public static final String NO_AVAILABLE_CHECKOUTS = "Cannot process checkout because there are NO checkouts available!";

    // Client
    public static final String INVALID_BUDGET = "Budget must be greater than 0!";

    // Cart
    public static final String INVALID_PRODUCT_ID = "Product with such ID does NOT exist!";
    public static final String INSUFFICIENT_QUANTITY
            = "Insufficient quantity of %s. Available quantity: %d. Needed quantity: +%d!";

    // Product & CartItem
    public static final String INVALID_PRODUCT = "Product CANNOT be null!";
    public static final String INVALID_PRODUCT_QUANTITY = "Quantity must be greater than 0!";

    // Product
    public static final String INVALID_DELIVERY_PRICE = "Delivery price must be greater than 0!";
    public static final String INVALID_EXPIRATION_DATE = "Expiration date must be before current date!";
    public static final String INVALID_QUANTITY_AMOUNT_TO_REDUCE = "Amount too big. Cannot reduce the base quantity!";

    public static final String INVALID_MARKUP_PERCENTAGE = "Markup percentage CANNOT be negative!";
    public static final String PRODUCT_ALREADY_EXPIRED = "The product has already expired!";
    public static final String INVALID_APPROACHING_EXPIRATION_DAYS = "Approaching expiration days cannot be negative!";
    public static final String INVALID_EXPIRATION_DISCOUNT = "Expiration discount cannot be negative!";

    public static final String PRODUCT_ALREADY_EXISTS = "Product with such ID or Name already exists!";
}
