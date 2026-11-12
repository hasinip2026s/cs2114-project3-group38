import java.util.List;
import java.util.Scanner;

/**
 * Displays BrewQueue's menu, messages, and orders in the console,
 * and reads what the user types. It never changes the model.
 */

public class ConsoleView {

    private final Scanner scanner;


    /**
     * Creates the view, reading from the given Scanner.
     *
     * @param scanner where user input is read from
     */
    public ConsoleView(Scanner scanner) {
        this.scanner = scanner;
    }


    /**
     * Prints the six menu options.
     */
    public void showMainMenu() {
        System.out.println();
        System.out.println("=== BrewQueue ===");
        System.out.println("1. Place Order");
        System.out.println("2. View Next Order");
        System.out.println("3. Complete Next Order");
        System.out.println("4. Pending Orders");
        System.out.println("5. Cancel Order");
        System.out.println("6. Exit");
    }


    /**
     * Prints the options, numbered from 1.
     *
     * @param options the options to print
     */
    public void showChoices(String[] options) {
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }
    }


    /**
     * Prints the prompt and returns the answer with extra spaces trimmed.
     *
     * @param prompt the text to show before reading
     * @return the trimmed answer, or null if input has ended
     */
    public String readLine(String prompt) {
        System.out.print(prompt);
        if (!this.scanner.hasNextLine()) {
            return null;
        }
        return this.scanner.nextLine().trim();
    }


    /**
     * Prints the message.
     *
     * @param message the message to print
     */
    public void showMessage(String message) {
        System.out.println(message);
    }


    /**
     * Prints each waiting order and the count.
     *
     * @param orders the pending orders, front to rear
     */
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
}
