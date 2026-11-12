/**
 * Represents one customer's drink order in BrewQueue.
 * An order's information does not change after it is created.
 */

public class Order {

    private static final String[] DRINKS = { "Coffee", "Latte", "Tea" };
    private static final double[] DRINK_PRICES = { 3.75, 4.00, 3.50 };
    private static final double[] DRINK_PREP_MINUTES = { 2.5, 3.0, 2.0 };

    private static final String[] SIZES = { "Small", "Medium", "Large" };
    private static final double[] SIZE_PRICES = { 0.00, 1.00, 1.50 };
    private static final double[] SIZE_PREP_MINUTES = { 0.0, 1.0, 2.0 };

    private final int orderNumber;
    private final String customerName;
    private final String drinkType;
    private final String size;


    /**
     * Creates an order with the given information.
     *
     * @param orderNumber  the unique number for this order
     * @param customerName the customer's name
     * @param drinkType    the drink, which must be on the menu
     * @param size         the size, which must be Small, Medium, or Large
     * @throws IllegalArgumentException if the name is blank, or the drink
     *                                  or size is not on the menu
     */
    public Order(int orderNumber, String customerName, String drinkType,
        String size) {
        if (customerName == null || customerName.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "Customer name cannot be blank.");
        }
        if (indexOf(DRINKS, drinkType) == -1) {
            throw new IllegalArgumentException(
                "Drink is not on the menu.");
        }
        if (indexOf(SIZES, size) == -1) {
            throw new IllegalArgumentException(
                "Size must be Small, Medium, or Large.");
        }

        this.orderNumber = orderNumber;
        this.customerName = customerName;
        this.drinkType = drinkType;
        this.size = size;
    }


    /**
     * Returns the order number.
     *
     * @return the order number
     */
    public int getOrderNumber() {
        return this.orderNumber;
    }


    /**
     * Returns the customer's name.
     *
     * @return the customer's name
     */
    public String getCustomerName() {
        return this.customerName;
    }


    /**
     * Returns the drink.
     *
     * @return the drink type
     */
    public String getDrinkType() {
        return this.drinkType;
    }


    /**
     * Returns the size.
     *
     * @return the drink size
     */
    public String getSize() {
        return this.size;
    }


    /**
     * Returns the price for this drink and size.
     *
     * @return the price in dollars
     */
    public double getPrice() {
        return DRINK_PRICES[indexOf(DRINKS, this.drinkType)]
            + SIZE_PRICES[indexOf(SIZES, this.size)];
    }


    /**
     * Returns the prep time for this drink and size.
     *
     * @return the prep time in minutes
     */
    public double getPrepMinutes() {
        return DRINK_PREP_MINUTES[indexOf(DRINKS, this.drinkType)]
            + SIZE_PREP_MINUTES[indexOf(SIZES, this.size)];
    }


    /**
     * Returns the order on one line,
     * e.g. "#4 Alex: Medium Latte, $5.00, about 4 min".
     *
     * @return the order as a String
     */
    @Override
    public String toString() {
        double minutes = this.getPrepMinutes();
        String prepTime;
        if (minutes == Math.floor(minutes)) {
            prepTime = String.format("%d", (int)minutes);
        }
        else {
            prepTime = String.format("%.1f", minutes);
        }
        return String.format("#%d %s: %s %s, $%.2f, about %s min",
            this.orderNumber, this.customerName, this.size,
            this.drinkType, this.getPrice(), prepTime);
    }


    /**
     * Finds where a value is in a list of menu options.
     *
     * @param options the menu options
     * @param value   the value to find
     * @return the index of the value, or -1 if it is not an option
     */
    private static int indexOf(String[] options, String value) {
        if (value == null) {
            return -1;
        }
        for (int i = 0; i < options.length; i++) {
            if (options[i].equals(value)) {
                return i;
            }
        }
        return -1;
    }
}