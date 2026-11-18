import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** Tests console view output and scripted input. */
public class ConsoleViewTest {
    private PrintStream originalOut;
    private ByteArrayOutputStream output;

    @BeforeEach
    public void setUp() {
        originalOut = System.out;
        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
    }

    private ConsoleView makeView(String input) {
        return new ConsoleView(new Scanner(input));
    }

    @Test
    public void testShowMainMenu() {
        makeView("").showMainMenu();
        String printed = output.toString();
        assertTrue(printed.contains("1. Place Order"));
        assertTrue(printed.contains("2. View Next Order"));
        assertTrue(printed.contains("3. Complete Next Order"));
        assertTrue(printed.contains("4. Pending Orders"));
        assertTrue(printed.contains("5. Cancel Order"));
        assertTrue(printed.contains("6. Check ETA"));
        assertTrue(printed.contains("7. Exit"));
    }

    @Test
    public void testShowChoices() {
        makeView("").showChoices(new String[] {"Latte", "Tea"});
        assertTrue(output.toString().contains("1. Latte"));
        assertTrue(output.toString().contains("2. Tea"));
    }

    @Test
    public void testReadLineTrims() {
        ConsoleView view = makeView("   Alex   \n");
        assertEquals("Alex", view.readLine("Name: "));
        assertTrue(output.toString().contains("Name: "));
    }

    @Test
    public void testReadLineInputEnded() {
        assertNull(makeView("").readLine("Name: "));
    }

    @Test
    public void testShowMessage() {
        makeView("").showMessage("Order #1 for Alex is ready!");
        assertTrue(output.toString().contains("Order #1 for Alex is ready!"));
    }

    @Test
    public void testShowPendingOrders() {
        List<Order> orders = new ArrayList<>();
        orders.add(new Order(1, "Alex", "Latte", "Medium"));
        orders.add(new Order(2, "Sam", "Tea", "Small"));
        makeView("").showPendingOrders(orders);
        String printed = output.toString();
        assertTrue(printed.contains("#1 Alex"));
        assertTrue(printed.contains("#2 Sam"));
        assertTrue(printed.contains("Pending orders: 2"));
    }

    @Test
    public void testShowPendingOrdersEmpty() {
        makeView("").showPendingOrders(new ArrayList<>());
        assertTrue(output.toString().contains("No pending orders."));
    }

    @Test
    public void testShowEstimatedWaitTime() {
        makeView("").showEstimatedWaitTime(2, 2.5, 6.5);
        assertTrue(output.toString().contains("Order #2"));
        assertTrue(output.toString().contains("Wait to start: 2.5 min"));
        assertTrue(output.toString().contains("Ready in: 6.5 min"));
    }
}
