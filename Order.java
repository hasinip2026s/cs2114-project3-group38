import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

/**
 * Represents a customer's immutable order.
 * Both legacy drink names (e.g. "Latte") and temperature-qualified names
 * (e.g. "Iced Latte") are supported to preserve earlier functionality.
 * Each order records the time it was placed.
 */

public class Order {
    private static final String[] DRINKS = {
        "Coffee", "Latte", "Tea",
        "Hot Coffee", "Iced Coffee",
        "Hot Latte", "Iced Latte",
        "Hot Tea", "Iced Tea"
    };

    private static final double[] DRINK_PRICES = {
        3.75, 4.00, 3.50, 3.75, 3.75, 4.00, 4.00, 3.50, 3.50
    };

    private static final double[] DRINK_PREP_MINUTES = {
        2.5, 3.0, 2.0, 2.5, 2.5, 3.0, 3.0, 2.0, 2.0
    };

    private static final String[] SIZES = {"Small", "Medium", "Large"};
    private static final double[] SIZE_PRICES = {0.00, 1.00, 1.50};
    private static final double[] SIZE_PREP_MINUTES = {0.0, 1.0, 2.0};

    // shows the time like "9:14 AM"
    private static final DateTimeFormatter TIME_FORMAT =
        DateTimeFormatter.ofPattern("h:mm a", Locale.US);

    private final int orderNumber;
    private final String customerName;
    private final String drinkType;
    private final String size;
    private final LocalTime timePlaced;

    public Order(int orderNumber, String customerName, String drinkType, String size) {
        if (orderNumber <= 0) {
            throw new IllegalArgumentException("Order number must be positive.");
        }
        if (customerName == null || customerName.trim().isEmpty()
            || customerName.trim().length() > 30) {
            throw new IllegalArgumentException("Customer name must be 1-30 characters.");
        }
        if (indexOf(DRINKS, drinkType) == -1) {
            throw new IllegalArgumentException("Drink is not on the menu.");
        }
        if (indexOf(SIZES, size) == -1) {
            throw new IllegalArgumentException("Size must be Small, Medium, or Large.");
        }

        this.orderNumber = orderNumber;
        this.customerName = customerName.trim();
        this.drinkType = drinkType;
        this.size = size;
        // seconds aren't shown, so only the hour and minute are kept
        this.timePlaced = LocalTime.now().truncatedTo(ChronoUnit.MINUTES);
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getDrinkType() {
        return drinkType;
    }

    public String getSize() {
        return size;
    }

    public LocalTime getTimePlaced() {
        return timePlaced;
    }

    public double getPrice() {
        return DRINK_PRICES[indexOf(DRINKS, drinkType)]
            + SIZE_PRICES[indexOf(SIZES, size)];
    }

    public double getPrepMinutes() {
        return DRINK_PREP_MINUTES[indexOf(DRINKS, drinkType)]
            + SIZE_PREP_MINUTES[indexOf(SIZES, size)];
    }

    @Override
    public String toString() {
        double minutes = getPrepMinutes();
        String prepTime = (minutes == Math.floor(minutes))
            ? String.format("%d", (int) minutes)
            : String.format("%.1f", minutes);
        return String.format("#%d %s: %s %s, $%.2f, about %s min, placed %s",
            orderNumber, customerName, size, drinkType, getPrice(), prepTime,
            timePlaced.format(TIME_FORMAT));
    }

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
