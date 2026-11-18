import java.util.List;
import java.util.Scanner;

/**
 * View layer: displays menus, messages and orders, and reads user input.
 * It does not store or modify the order queue.
 */
public class ConsoleView {
    private final Scanner scanner;

    public ConsoleView(Scanner scanner) {
        this.scanner = scanner;
    }

    public void showMainMenu() {
        System.out.println();
        System.out.println("=== BrewQueue ===");
        System.out.println("1. Place Order");
        System.out.println("2. View Next Order");
        System.out.println("3. Complete Next Order");
        System.out.println("4. Pending Orders");
        System.out.println("5. Cancel Order");
        System.out.println("6. Check ETA");
        System.out.println("7. Exit");
    }

    public void showChoices(String[] options) {
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }
    }

    /** Returns null when input has ended. */
    public String readLine(String prompt) {
        System.out.print(prompt);
        if (!scanner.hasNextLine()) {
            return null;
        }
        return scanner.nextLine().trim();
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public void showPendingOrders(List<Order> orders) {
        if (orders.isEmpty()) {
            System.out.println("No pending orders.");
            return;
        }
        for (Order order : orders) {
            System.out.println(order);
        }
        System.out.println("Pending orders: " + orders.size());
    }

    /** Displays values calculated by the model through the controller. */
    public void showEstimatedWaitTime(int orderNumber, double wait, double ready) {
        System.out.printf("Order #%d | Wait to start: %.1f min | Ready in: %.1f min%n",
            orderNumber, wait, ready);
    }
}
