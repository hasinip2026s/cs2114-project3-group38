import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** Tests ConsoleView using scripted input and captured output. */

public class ConsoleViewTest {

    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream output;


    @BeforeEach
    public void setUp() {
        this.output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(this.output));
    }


    @AfterEach
    public void tearDown() {
        System.setOut(this.originalOut);
    }


    /**
     * Creates a view that reads the given text as user input.
     */
    private ConsoleView makeView(String input) {
        return new ConsoleView(new Scanner(input));
    }


    @Test
    public void testShowMainMenu() {
        this.makeView("").showMainMenu();
        String printed = this.output.toString();
        assertTrue(printed.contains("1. Place Order"));
        assertTrue(printed.contains("2. View Next Order"));
        assertTrue(printed.contains("3. Complete Next Order"));
        assertTrue(printed.contains("4. Pending Orders"));
        assertTrue(printed.contains("5. Cancel Order"));
        assertTrue(printed.contains("6. Exit"));
    }


    @Test
    public void testShowChoices() {
        this.makeView("").showChoices(new String[] { "Latte", "Tea" });
        String printed = this.output.toString();
        assertTrue(printed.contains("1. Latte"));
        assertTrue(printed.contains("2. Tea"));
    }


    @Test
    public void testReadLineTrims() {
        ConsoleView view = this.makeView("   Alex   \n");
        assertEquals("Alex", view.readLine("Name: "));
        assertTrue(this.output.toString().contains("Name: "));
    }


    @Test
    public void testReadLineInputEnded() {
        ConsoleView view = this.makeView("");
        assertNull(view.readLine("Name: "));
    }


    @Test
    public void testShowMessage() {
        this.makeView("").showMessage("Order #1 for Alex is ready!");
        assertTrue(this.output.toString()
            .contains("Order #1 for Alex is ready!"));
    }


    @Test
    public void testShowPendingOrders() {
        List<Order> orders = new ArrayList<>();
        orders.add(new Order(1, "Alex", "Latte", "Medium"));
        orders.add(new Order(2, "Sam", "Tea", "Small"));
        this.makeView("").showPendingOrders(orders);
        String printed = this.output.toString();
        assertTrue(printed.contains("#1 Alex"));
        assertTrue(printed.contains("#2 Sam"));
        assertTrue(printed.contains("Pending orders: 2"));
    }


    @Test
    public void testShowPendingOrdersEmpty() {
        this.makeView("").showPendingOrders(new ArrayList<>());
        assertTrue(this.output.toString().contains("No pending orders."));
    }
}