import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

/**
 * Tests the controller through scripted console input.
 * Original controller tests attributed to Liangru Ji (D3); D4 scenarios
 * exercise Hot/Iced selection, ETA, and menu 7 Exit.
 */
public class BrewQueueControllerTest {
    @Test
    public void testExit() {
        assertTrue(runWithInput("7\n").contains("Goodbye!"));
    }

    @Test
    public void testInputEndingExits() {
        assertTrue(runWithInput("").contains("Goodbye!"));
    }

    @Test
    public void testInvalidMenuChoicesRetry() {
        String result = runWithInput("abc\n0\n8\n7\n");
        assertTrue(result.contains("Please enter a whole number"));
        assertTrue(result.contains("Goodbye!"));
    }

    @Test
    public void testPlaceOrderUsingNumbers() {
        String result = runWithInput("1\nAlex\n2\n2\n2\n7\n");
        assertTrue(result.contains("Order placed:"));
        assertTrue(result.contains("Alex"));
        assertTrue(result.contains("Iced Latte"));
        assertTrue(result.contains("Medium"));
        assertTrue(result.contains("Ready in: 4.0 min"));
    }

    @Test
    public void testPlaceOrderUsingNames() {
        String result = runWithInput("1\nSam\nTea\nHot\nSmall\n7\n");
        assertTrue(result.contains("Order placed:"));
        assertTrue(result.contains("Sam"));
        assertTrue(result.contains("Hot Tea"));
        assertTrue(result.contains("Small"));
    }

    @Test
    public void testInvalidNameRetries() {
        String longName = "ABCDEFGHIJKLMNOPQRSTUVWXYZABCDE";
        String result = runWithInput(
            "1\n   \n" + longName + "\nAlex\n1\n1\n1\n7\n");
        assertTrue(result.contains("Name must contain 1 to 30"));
        assertTrue(result.contains("Order placed:"));
    }

    @Test
    public void testInvalidDrinkAndSizeRetry() {
        String result = runWithInput(
            "1\nAlex\nSoda\nLatte\nHot\nExtra Large\nMedium\n7\n");
        assertTrue(result.contains("Please choose one of the listed options."));
        assertTrue(result.contains("Order placed:"));
    }

    @Test
    public void testInvalidTemperatureRetries() {
        String result = runWithInput("1\nAlex\nCoffee\nWarm\nIced\nSmall\n7\n");
        assertTrue(result.contains("Please choose one of the listed options."));
        assertTrue(result.contains("Iced Coffee"));
        assertTrue(result.contains("Order placed:"));
    }

    @Test
    public void testViewNextOrderWhenEmpty() {
        assertTrue(runWithInput("2\n7\n").contains("No pending orders."));
    }

    @Test
    public void testViewNextOrder() {
        String result = runWithInput("1\nAlex\nLatte\nHot\nMedium\n2\n7\n");
        assertTrue(result.contains("Next order:"));
        assertTrue(result.contains("Alex"));
    }

    @Test
    public void testCompleteNextOrderWhenEmpty() {
        assertTrue(runWithInput("3\n7\n").contains("No pending orders."));
    }

    @Test
    public void testCompleteNextOrder() {
        String result = runWithInput("1\nAlex\nLatte\nHot\nMedium\n3\n7\n");
        assertTrue(result.contains("Completed order:"));
        assertTrue(result.contains("Alex"));
    }
    @Test
    public void testShowPendingOrders() {
        String result = runWithInput(
            "1\nAlex\nLatte\nHot\nMedium\n"
            + "1\nSam\nTea\nIced\nSmall\n4\n7\n");
        assertTrue(result.contains("Alex"));
        assertTrue(result.contains("Sam"));
        assertTrue(result.contains("Pending orders: 2"));
    }

    @Test
    public void testCancelOrder() {
        String result = runWithInput("1\nAlex\nLatte\nHot\nMedium\n5\n1\n7\n");
        assertTrue(result.contains("Order #1 canceled."));
    }

    @Test
    public void testCancelMissingOrder() {
        assertTrue(runWithInput("5\n99\n7\n").contains("Order #99 was not found."));
    }

    @Test
    public void testCheckEtaAfterTwoOrders() {
        String result = runWithInput(
            "1\nAlex\nCoffee\nHot\nSmall\n"
            + "1\nSam\nLatte\nIced\nMedium\n6\n2\n7\n");
        assertTrue(result.contains("Order #2 | Wait to start: 2.5 min | Ready in: 6.5 min"));
    }

    @Test
    public void testEtaUpdatesAfterCompletingFirstOrder() {
        String result = runWithInput(
            "1\nAlex\nCoffee\nHot\nSmall\n"
            + "1\nSam\nLatte\nIced\nMedium\n3\n6\n2\n7\n");
        assertTrue(result.contains("Order #2 | Wait to start: 0.0 min | Ready in: 4.0 min"));
    }

    @Test
    public void testEtaUnknownOrder() {
        assertTrue(runWithInput("6\n99\n7\n").contains("Order not found."));
    }

    @Test
    public void testInvalidEtaInputRetries() {
        String result = runWithInput("6\nabc\n0\n99\n7\n");
        assertTrue(result.contains("Please enter a whole number"));
        assertTrue(result.contains("Order not found."));
    }

    @Test
    public void testInputEndingDuringOrderDoesNotCrash() {
        String result = runWithInput("1\nAlex\nCoffee\n");
        assertTrue(result.contains("Goodbye!"));
        assertFalse(result.contains("Order placed:"));
    }

    private String runWithInput(String input) {
        PrintStream originalOutput = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        Scanner scanner = new Scanner(input);
        try {
            System.setOut(new PrintStream(output));
            OrderManager manager = new OrderManager();
            ConsoleView view = new ConsoleView(scanner);
            BrewQueueController controller = new BrewQueueController(manager, view);
            controller.run();
        }
        finally {
            System.setOut(originalOutput);
            scanner.close();
        }
        return output.toString();
    }
}

        