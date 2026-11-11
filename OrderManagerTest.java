
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;

/** Tests OrderManager independently of the view and controller. */
public class OrderManagerTest {

    @Test
    public void testNewManagerIsEmpty() {
        OrderManager manager = new OrderManager();
        assertEquals(0, manager.getPendingOrderCount());
        assertTrue(manager.getPendingOrders().isEmpty());
        assertNull(manager.viewNextOrder());
        assertNull(manager.completeNextOrder());
    }

    @Test
    public void testPlaceOrder() {
        OrderManager manager = new OrderManager();
        Order order = manager.placeOrder("Alex", "Latte", "Medium");
        assertEquals(1, order.getOrderNumber());
        assertEquals("Alex", order.getCustomerName());
        assertEquals(1, manager.getPendingOrderCount());
        assertSame(order, manager.viewNextOrder());
    }

    @Test
    public void testUniqueOrderNumbers() {
        OrderManager manager = new OrderManager();
        Order first = manager.placeOrder("Alex", "Latte", "Medium");
        Order second = manager.placeOrder("Sam", "Latte", "Medium");
        Order third = manager.placeOrder("Taylor", "Latte", "Medium");
        assertEquals(1, first.getOrderNumber());
        assertEquals(2, second.getOrderNumber());
        assertEquals(3, third.getOrderNumber());
    }

    @Test
    public void testViewNextDoesNotRemove() {
        OrderManager manager = new OrderManager();
        Order first = manager.placeOrder("Alex", "Latte", "Medium");
        manager.placeOrder("Sam", "Latte", "Medium");
        assertSame(first, manager.viewNextOrder());
        assertSame(first, manager.viewNextOrder());
        assertEquals(2, manager.getPendingOrderCount());
    }

    @Test
    public void testCompleteOrdersFifo() {
        OrderManager manager = new OrderManager();
        Order first = manager.placeOrder("Alex", "Latte", "Medium");
        Order second = manager.placeOrder("Sam", "Latte", "Medium");
        Order third = manager.placeOrder("Taylor", "Latte", "Medium");
        assertSame(first, manager.completeNextOrder());
        assertSame(second, manager.completeNextOrder());
        assertSame(third, manager.completeNextOrder());
        assertNull(manager.completeNextOrder());
        assertEquals(0, manager.getPendingOrderCount());
    }

    @Test
    public void testPendingOrdersInFifoOrder() {
        OrderManager manager = new OrderManager();
        Order first = manager.placeOrder("Alex", "Latte", "Medium");
        Order second = manager.placeOrder("Sam", "Latte", "Medium");
        List<Order> orders = manager.getPendingOrders();
        assertEquals(2, orders.size());
        assertSame(first, orders.get(0));
        assertSame(second, orders.get(1));
    }

    @Test
    public void testPendingListIsCopy() {
        OrderManager manager = new OrderManager();
        manager.placeOrder("Alex", "Latte", "Medium");
        List<Order> copy = manager.getPendingOrders();
        copy.clear();
        assertEquals(1, manager.getPendingOrderCount());
    }

    @Test
    public void testBlankNameDoesNotChangeState() {
        OrderManager manager = new OrderManager();
        assertThrows(IllegalArgumentException.class,
            () -> manager.placeOrder("   ", "Latte", "Medium"));
        assertEquals(0, manager.getPendingOrderCount());
        assertEquals(1,
            manager.placeOrder("Alex", "Latte", "Medium").getOrderNumber());
    }

    @Test
    public void testNullAndLongNamesRejected() {
        OrderManager manager = new OrderManager();
        assertThrows(IllegalArgumentException.class,
            () -> manager.placeOrder(null, "Latte", "Medium"));
        assertThrows(IllegalArgumentException.class,
            () -> manager.placeOrder("ABCDEFGHIJKLMNOPQRSTUVWXYZABCDE", "Latte", "Medium"));
        assertEquals(0, manager.getPendingOrderCount());
    }

    @Test
    public void testBlankDrinkAndSizeRejected() {
        OrderManager manager = new OrderManager();
        assertThrows(IllegalArgumentException.class,
            () -> manager.placeOrder("Alex", " ", "Medium"));
        assertThrows(IllegalArgumentException.class,
            () -> manager.placeOrder("Alex", "Latte", " "));
        assertEquals(0, manager.getPendingOrderCount());
    }

    @Test
    public void testInvalidDrinkAndSizeRejectedByOrder() {
        // These cases require Order's constructor to validate menu choices.
        OrderManager manager = new OrderManager();
        assertThrows(IllegalArgumentException.class,
            () -> manager.placeOrder("Alex", "NotOnMenu", "Medium"));
        assertThrows(IllegalArgumentException.class,
            () -> manager.placeOrder("Alex", "Latte", "XXXL"));
        assertEquals(0, manager.getPendingOrderCount());
        assertEquals(1,
            manager.placeOrder("Alex", "Latte", "Medium").getOrderNumber());
    }

    @Test
    public void testCancelMiddleKeepsOrder() {
        OrderManager manager = new OrderManager();
        Order first = manager.placeOrder("Alex", "Latte", "Medium");
        Order middle = manager.placeOrder("Sam", "Latte", "Medium");
        Order last = manager.placeOrder("Taylor", "Latte", "Medium");
        assertTrue(manager.cancelOrder(middle.getOrderNumber()));
        assertEquals(2, manager.getPendingOrderCount());
        assertSame(first, manager.completeNextOrder());
        assertSame(last, manager.completeNextOrder());
    }

    @Test
    public void testCancelFirstAndLast() {
        OrderManager manager = new OrderManager();
        Order first = manager.placeOrder("Alex", "Latte", "Medium");
        Order second = manager.placeOrder("Sam", "Latte", "Medium");
        Order last = manager.placeOrder("Taylor", "Latte", "Medium");
        assertTrue(manager.cancelOrder(first.getOrderNumber()));
        assertTrue(manager.cancelOrder(last.getOrderNumber()));
        assertSame(second, manager.viewNextOrder());
        assertEquals(1, manager.getPendingOrderCount());
    }

    @Test
    public void testCancelUnknownDoesNotChangeState() {
        OrderManager manager = new OrderManager();
        Order first = manager.placeOrder("Alex", "Latte", "Medium");
        assertFalse(manager.cancelOrder(999));
        assertFalse(manager.cancelOrder(0));
        assertFalse(manager.cancelOrder(-1));
        assertSame(first, manager.viewNextOrder());
        assertEquals(1, manager.getPendingOrderCount());
    }

    @Test
    public void testCancelEmptyQueue() {
        OrderManager manager = new OrderManager();
        assertFalse(manager.cancelOrder(1));
        assertTrue(manager.getPendingOrders().isEmpty());
    }

    @Test
    public void testCancelledNumberIsNotReused() {
        OrderManager manager = new OrderManager();
        Order first = manager.placeOrder("Alex", "Latte", "Medium");
        manager.placeOrder("Sam", "Latte", "Medium");
        assertTrue(manager.cancelOrder(first.getOrderNumber()));
        Order next = manager.placeOrder("Taylor", "Latte", "Medium");
        assertEquals(3, next.getOrderNumber());
    }
}

