import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import org.junit.jupiter.api.Test;

/** Tests Order's validation, drink choices, prices and prep estimates. */

public class OrderTest
{
    @Test
    public void testConstructor()
    {
        Order order = new Order(1, "Alex", "Latte", "Medium");
        assertEquals(1, order.getOrderNumber());
        assertEquals("Alex", order.getCustomerName());
        assertEquals("Latte", order.getDrinkType());
        assertEquals("Medium", order.getSize());
    }


    @Test
    public void testConstructorBlankName()
    {
        assertThrows(
            IllegalArgumentException.class,
            () -> new Order(1, "   ", "Latte", "Medium"));
    }


    @Test
    public void testConstructorNullAndLongName()
    {
        assertThrows(
            IllegalArgumentException.class,
            () -> new Order(1, null, "Latte", "Medium"));
        assertThrows(
            IllegalArgumentException.class,
            () -> new Order(
                1,
                "ABCDEFGHIJKLMNOPQRSTUVWXYZABCDE",
                "Latte",
                "Medium"));
    }


    @Test
    public void testConstructorDrinkNotOnMenu()
    {
        assertThrows(
            IllegalArgumentException.class,
            () -> new Order(1, "Alex", "Mocha", "Medium"));
        assertThrows(
            IllegalArgumentException.class,
            () -> new Order(1, "Alex", null, "Medium"));
    }


    @Test
    public void testConstructorSizeNotOnMenu()
    {
        assertThrows(
            IllegalArgumentException.class,
            () -> new Order(1, "Alex", "Latte", "Huge"));
        assertThrows(
            IllegalArgumentException.class,
            () -> new Order(1, "Alex", "Latte", null));
    }


    @Test
    public void testConstructorInvalidOrderNumber()
    {
        assertThrows(
            IllegalArgumentException.class,
            () -> new Order(0, "Alex", "Latte", "Small"));
    }


    @Test
    public void testGetOrderNumber()
    {
        assertEquals(
            1,
            new Order(1, "Alex", "Latte", "Medium").getOrderNumber());
    }


    @Test
    public void testGetOrderNumberUnchanged()
    {
        Order order = new Order(5, "Alex", "Latte", "Medium");
        assertEquals(5, order.getOrderNumber());
        assertEquals(5, order.getOrderNumber());
    }


    @Test
    public void testGetCustomerName()
    {
        assertEquals(
            "Alex",
            new Order(1, "Alex", "Latte", "Medium").getCustomerName());
    }


    @Test
    public void testGetCustomerNameOneCharacter()
    {
        assertEquals(
            "A",
            new Order(1, "A", "Latte", "Medium").getCustomerName());
    }


    @Test
    public void testGetDrinkTypeLatte()
    {
        assertEquals(
            "Latte",
            new Order(1, "Alex", "Latte", "Medium").getDrinkType());
    }


    @Test
    public void testGetDrinkTypeTea()
    {
        assertEquals(
            "Tea",
            new Order(1, "Alex", "Tea", "Medium").getDrinkType());
    }


    @Test
    public void testGetSizeMedium()
    {
        assertEquals(
            "Medium",
            new Order(1, "Alex", "Latte", "Medium").getSize());
    }


    @Test
    public void testGetSizeSmall()
    {
        assertEquals("Small", new Order(1, "Alex", "Latte", "Small").getSize());
    }


    @Test
    public void testGetPrice()
    {
        assertEquals(
            5.00,
            new Order(1, "Alex", "Latte", "Medium").getPrice(),
            0.001);
    }


    @Test
    public void testGetPriceChangesWithSize()
    {
        Order small = new Order(1, "Alex", "Tea", "Small");
        Order large = new Order(2, "Sam", "Tea", "Large");
        assertEquals(3.50, small.getPrice(), 0.001);
        assertEquals(5.00, large.getPrice(), 0.001);
    }


    @Test
    public void testGetPrepMinutes()
    {
        assertEquals(
            4.0,
            new Order(1, "Alex", "Latte", "Medium").getPrepMinutes(),
            0.001);
    }


    @Test
    public void testGetPrepMinutesChangesWithSize()
    {
        Order small = new Order(1, "Alex", "Coffee", "Small");
        Order large = new Order(2, "Sam", "Coffee", "Large");
        assertEquals(2.5, small.getPrepMinutes(), 0.001);
        assertEquals(4.5, large.getPrepMinutes(), 0.001);
    }


    @Test
    public void testGetTimePlaced()
    {
        LocalTime before = LocalTime.now().truncatedTo(ChronoUnit.MINUTES);
        Order order = new Order(1, "Alex", "Latte", "Medium");
        LocalTime after = LocalTime.now().truncatedTo(ChronoUnit.MINUTES);
        LocalTime placed = order.getTimePlaced();

        assertFalse(placed.isBefore(before));
        assertFalse(placed.isAfter(after));
        assertEquals(0, placed.getSecond());
    }


    @Test
    public void testToString()
    {
        Order order = new Order(4, "Alex", "Latte", "Medium");
        String placed = order.getTimePlaced()
            .format(DateTimeFormatter.ofPattern("h:mm a", Locale.US));
        assertEquals(
            "#4 Alex: Medium Latte, $5.00, about 4 min, placed " + placed,
            order.toString());
    }


    @Test
    public void testHotAndIcedDrinks()
    {
        for (String drink : new String[] { "Coffee", "Latte", "Tea" })
        {
            Order hot = new Order(1, "Alex", "Hot " + drink, "Small");
            Order iced = new Order(2, "Sam", "Iced " + drink, "Small");
            assertEquals("Hot " + drink, hot.getDrinkType());
            assertEquals("Iced " + drink, iced.getDrinkType());
            assertTrue(hot.toString().contains("Hot " + drink));
            assertTrue(iced.toString().contains("Iced " + drink));
        }
    }


    @Test
    public void testIcedLattePriceAndPrepMinutes()
    {
        Order order = new Order(1, "Alex", "Iced Latte", "Medium");
        assertEquals(5.0, order.getPrice(), 0.001);
        assertEquals(4.0, order.getPrepMinutes(), 0.001);
    }


    @Test
    public void testInvalidTemperatureOrDrinkCombination()
    {
        assertThrows(
            IllegalArgumentException.class,
            () -> new Order(1, "Alex", "Warm Latte", "Medium"));
        assertThrows(
            IllegalArgumentException.class,
            () -> new Order(1, "Alex", "Iced Mocha", "Medium"));
    }
}
