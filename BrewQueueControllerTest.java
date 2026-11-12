import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

// -------------------------------------------------------------------------
/**
 * Tests BrewQueueController with scripted console input.
 *
 * @author Liangru Ji
 * @version 2026.11.11
 */
public class BrewQueueControllerTest {

    // ~ Fields ................................................................

    // ~ Constructors ..........................................................

    // ~ Public Methods ........................................................

    // ----------------------------------------------------------
    /**
     * Tests selecting Exit from the menu.
     */
    @Test
    public void testExit() {
        String result = runWithInput("6\n");
        assertTrue(result.contains("Goodbye!"));
    }


    // ----------------------------------------------------------
    /**
     * Tests ending the input stream.
     */
    @Test
    public void testInputEndingExits() {
        String result = runWithInput("");
        assertTrue(result.contains("Goodbye!"));
    }


    // ----------------------------------------------------------
    /**
     * Tests retrying invalid menu choices.
     */
    @Test
    public void testInvalidMenuChoicesRetry() {
        String result = runWithInput("abc\n0\n7\n6\n");
        assertTrue(result.contains("Please enter a whole number"));
        assertTrue(result.contains("Goodbye!"));
    }


    // ----------------------------------------------------------
    /**
     * Tests placing an order using numbered choices.
     */
    @Test
    public void testPlaceOrderUsingNumbers() {
        String result = runWithInput("1\nAlex\n2\n2\n6\n");
        assertTrue(result.contains("Order placed:"));
        assertTrue(result.contains("Alex"));
        assertTrue(result.contains("Latte"));
        assertTrue(result.contains("Medium"));
    }


    // ----------------------------------------------------------
    /**
     * Tests placing an order using option names.
     */
    @Test
    public void testPlaceOrderUsingNames() {
        String result = runWithInput("1\nSam\nTea\nSmall\n6\n");
        assertTrue(result.contains("Order placed:"));
        assertTrue(result.contains("Sam"));
        assertTrue(result.contains("Tea"));
        assertTrue(result.contains("Small"));
    }


    // ----------------------------------------------------------
    /**
     * Tests retrying invalid customer names.
     */
    @Test
    public void testInvalidNameRetries() {
        String longName = "ABCDEFGHIJKLMNOPQRSTUVWXYZABCDE";
        String result = runWithInput(
            "1\n   \n" + longName + "\nAlex\n1\n1\n6\n");
        assertTrue(result.contains("Name must contain 1 to 30"));
        assertTrue(result.contains("Order placed:"));
    }


    // ----------------------------------------------------------
    /**
     * Tests retrying invalid drink and size choices.
     */
    @Test
    public void testInvalidDrinkAndSizeRetry() {
        String result = runWithInput(
            "1\nAlex\nSoda\nLatte\nExtra Large\nMedium\n6\n");
        assertTrue(
            result.contains("Please choose one of the listed options."));
        assertTrue(result.contains("Order placed:"));
    }


    // ----------------------------------------------------------
    /**
     * Tests viewing the next order when the queue is empty.
     */
    @Test
    public void testViewNextOrderWhenEmpty() {
        String result = runWithInput("2\n6\n");
        assertTrue(result.contains("No pending orders."));
    }


    // ----------------------------------------------------------
    /**
     * Tests viewing the next pending order.
     */
    @Test
    public void testViewNextOrder() {
        String result = runWithInput(
            "1\nAlex\nLatte\nMedium\n2\n6\n");
        assertTrue(result.contains("Next order:"));
        assertTrue(result.contains("Alex"));
    }


    // ----------------------------------------------------------
    /**
     * Tests completing an order when the queue is empty.
     */
    @Test
    public void testCompleteNextOrderWhenEmpty() {
        String result = runWithInput("3\n6\n");
        assertTrue(result.contains("No pending orders."));
    }


    // ----------------------------------------------------------
    /**
     * Tests completing the next pending order.
     */
    @Test
    public void testCompleteNextOrder() {
        String result = runWithInput(
            "1\nAlex\nLatte\nMedium\n3\n6\n");
        assertTrue(result.contains("Completed order:"));
        assertTrue(result.contains("Alex"));
    }


    // ----------------------------------------------------------
    /**
     * Tests displaying all pending orders.
     */
    @Test
    public void testShowPendingOrders() {
        String result = runWithInput(
            "1\nAlex\nLatte\nMedium\n"
                + "1\nSam\nTea\nSmall\n4\n6\n");
        assertTrue(result.contains("Alex"));
        assertTrue(result.contains("Sam"));
    }


    // ----------------------------------------------------------
    /**
     * Tests canceling an existing order.
     */
    @Test
    public void testCancelOrder() {
        String result = runWithInput(
            "1\nAlex\nLatte\nMedium\n5\n1\n6\n");
        assertTrue(result.contains("Order #1 canceled."));
    }


    // ----------------------------------------------------------
    /**
     * Tests canceling an order that is not pending.
     */
    @Test
    public void testCancelMissingOrder() {
        String result = runWithInput("5\n99\n6\n");
        assertTrue(result.contains("Order #99 was not found."));
    }


    // ~ Private Methods .......................................................

    // ----------------------------------------------------------
    /**
     * Runs the controller with scripted input and returns its output.
     *
     * @param input
     *            The lines supplied to the controller.
     * @return The text printed while the controller runs.
     */
    private String runWithInput(String input) {
        PrintStream originalOutput = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        Scanner scanner = new Scanner(input);

        try {
            System.setOut(new PrintStream(output));
            OrderManager manager = new OrderManager();
            ConsoleView view = new ConsoleView(scanner);
            BrewQueueController controller =
                new BrewQueueController(manager, view);
            controller.run();
        }
        finally {
            System.setOut(originalOutput);
            scanner.close();
        }

        return output.toString();
    }
}
