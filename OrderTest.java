import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/** Tests Order independently of the view and controller. */
public class OrderTest {

    @Test
    public void testConstructor() {
        Order order = new Order(1, "Alex", "Latte", "Medium");
        assertEquals(1, order.getOrderNumber());
        assertEquals("Alex", order.getCustomerName());
        assertEquals("Latte", order.getDrinkType());
        assertEquals("Medium", order.getSize());
    }


    @Test
    public void testConstructorBlankName() {
        assertThrows(IllegalArgumentException.class,
            () -> new Order(1, "   ", "Latte", "Medium"));
    }


    @Test
    public void testConstructorDrinkNotOnMenu() {
        assertThrows(IllegalArgumentException.class,
            () -> new Order(1, "Alex", "Mocha", "Medium"));
    }


    @Test
    public void testConstructorSizeNotOnMenu() {
        assertThrows(IllegalArgumentException.class,
            () -> new Order(1, "Alex", "Latte", "Huge"));
    }


    @Test
    public void testGetOrderNumber() {
        Order order = new Order(1, "Alex", "Latte", "Medium");
        assertEquals(1, order.getOrderNumber());
    }


    @Test
    public void testGetOrderNumberUnchanged() {
        Order order = new Order(5, "Alex", "Latte", "Medium");
        assertEquals(5, order.getOrderNumber());
        assertEquals(5, order.getOrderNumber());
    }


    @Test
    public void testGetCustomerName() {
        Order order = new Order(1, "Alex", "Latte", "Medium");
        assertEquals("Alex", order.getCustomerName());
    }


    @Test
    public void testGetCustomerNameOneCharacter() {
        Order order = new Order(1, "A", "Latte", "Medium");
        assertEquals("A", order.getCustomerName());
    }


    @Test
    public void testGetDrinkTypeLatte() {
        Order order = new Order(1, "Alex", "Latte", "Medium");
        assertEquals("Latte", order.getDrinkType());
    }


    @Test
    public void testGetDrinkTypeTea() {
        Order order = new Order(1, "Alex", "Tea", "Medium");
        assertEquals("Tea", order.getDrinkType());
    }


    @Test
    public void testGetSizeMedium() {
        Order order = new Order(1, "Alex", "Latte", "Medium");
        assertEquals("Medium", order.getSize());
    }


    @Test
    public void testGetSizeSmall() {
        Order order = new Order(1, "Alex", "Latte", "Small");
        assertEquals("Small", order.getSize());
    }


    @Test
    public void testGetPrice() {
        Order order = new Order(1, "Alex", "Latte", "Medium");
        assertEquals(5.00, order.getPrice(), 0.001);
    }


    @Test
    public void testGetPriceChangesWithSize() {
        Order small = new Order(1, "Alex", "Tea", "Small");
        Order large = new Order(2, "Sam", "Tea", "Large");
        assertEquals(3.50, small.getPrice(), 0.001);
        assertEquals(5.00, large.getPrice(), 0.001);
    }


    @Test
    public void testGetPrepMinutes() {
        Order order = new Order(1, "Alex", "Latte", "Medium");
        assertEquals(4.0, order.getPrepMinutes(), 0.001);
    }


    @Test
    public void testGetPrepMinutesChangesWithSize() {
        Order small = new Order(1, "Alex", "Coffee", "Small");
        Order large = new Order(2, "Sam", "Coffee", "Large");
        assertEquals(2.5, small.getPrepMinutes(), 0.001);
        assertEquals(4.5, large.getPrepMinutes(), 0.001);
    }


    @Test
    public void testToString() {
        Order order = new Order(4, "Alex", "Latte", "Medium");
        assertEquals("#4 Alex: Medium Latte, $5.00, about 4 min",
            order.toString());
    }
}