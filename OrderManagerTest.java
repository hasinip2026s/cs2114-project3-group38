import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;

/** Tests the order manager model without a view or controller. */
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
        assertEquals(1, manager.placeOrder("Alex", "Latte", "Medium").getOrderNumber());
        assertEquals(2, manager.placeOrder("Sam", "Latte", "Medium").getOrderNumber());
        assertEquals(3, manager.placeOrder("Taylor", "Latte", "Medium").getOrderNumber());
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
        assertEquals(1, manager.placeOrder("Alex", "Latte", "Medium").getOrderNumber());
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
        assertEquals(3, manager.placeOrder("Taylor", "Latte", "Medium").getOrderNumber());
    }

    @Test
    public void testEstimatedWaitAndReadyTimes() {
        OrderManager manager = new OrderManager();
        Order coffee = manager.placeOrder("Alex", "Hot Coffee", "Small");
        Order latte = manager.placeOrder("Sam", "Iced Latte", "Medium");
        Order tea = manager.placeOrder("Taylor", "Hot Tea", "Small");
        assertEquals(0.0, manager.getEstimatedWaitMinutes(coffee.getOrderNumber()), 0.001);
        assertEquals(2.5, manager.getEstimatedReadyMinutes(coffee.getOrderNumber()), 0.001);
        assertEquals(2.5, manager.getEstimatedWaitMinutes(latte.getOrderNumber()), 0.001);
        assertEquals(6.5, manager.getEstimatedReadyMinutes(latte.getOrderNumber()), 0.001);
        assertEquals(6.5, manager.getEstimatedWaitMinutes(tea.getOrderNumber()), 0.001);
        assertEquals(8.5, manager.getEstimatedReadyMinutes(tea.getOrderNumber()), 0.001);
    }

    @Test
    public void testEtaUpdatesAfterCompletion() {
        OrderManager manager = new OrderManager();
        manager.placeOrder("Alex", "Coffee", "Small");
        Order second = manager.placeOrder("Sam", "Latte", "Medium");
        manager.completeNextOrder();
        assertEquals(0.0, manager.getEstimatedWaitMinutes(second.getOrderNumber()), 0.001);
        assertEquals(4.0, manager.getEstimatedReadyMinutes(second.getOrderNumber()), 0.001);
    }

    @Test
    public void testEtaUpdatesAfterCancelMiddle() {
        OrderManager manager = new OrderManager();
        manager.placeOrder("Alex", "Coffee", "Small");
        Order second = manager.placeOrder("Sam", "Latte", "Medium");
        Order third = manager.placeOrder("Taylor", "Tea", "Small");
        assertEquals(8.5, manager.getEstimatedReadyMinutes(third.getOrderNumber()), 0.001);
        assertTrue(manager.cancelOrder(second.getOrderNumber()));
        assertEquals(4.5, manager.getEstimatedReadyMinutes(third.getOrderNumber()), 0.001);
    }

    @Test
    public void testEtaInvalidOrMissingOrder() {
        OrderManager manager = new OrderManager();
        manager.placeOrder("Alex", "Coffee", "Small");
        assertEquals(-1.0, manager.getEstimatedWaitMinutes(-1), 0.001);
        assertEquals(-1.0, manager.getEstimatedWaitMinutes(0), 0.001);
        assertEquals(-1.0, manager.getEstimatedWaitMinutes(99), 0.001);
        assertEquals(-1.0, manager.getEstimatedReadyMinutes(-1), 0.001);
        assertEquals(-1.0, manager.getEstimatedReadyMinutes(0), 0.001);
        assertEquals(-1.0, manager.getEstimatedReadyMinutes(99), 0.001);
    }

    @Test
    public void testEtaAfterCancelledOrCompletedOrder() {
        OrderManager manager = new OrderManager();
        Order first = manager.placeOrder("Alex", "Coffee", "Small");
        Order second = manager.placeOrder("Sam", "Latte", "Medium");
        assertTrue(manager.cancelOrder(first.getOrderNumber()));
        assertEquals(-1.0, manager.getEstimatedReadyMinutes(first.getOrderNumber()), 0.001);
        assertSame(second, manager.completeNextOrder());
        assertEquals(-1.0, manager.getEstimatedReadyMinutes(second.getOrderNumber()), 0.001);
        assertEquals(-1.0, manager.getEstimatedWaitMinutes(second.getOrderNumber()), 0.001);
    }

    @Test
    public void testHotIcedOrderAcceptedAndInvalidDoesNotConsumeId() {
        OrderManager manager = new OrderManager();
        assertThrows(IllegalArgumentException.class,
            () -> manager.placeOrder("Alex", "Warm Latte", "Medium"));
        Order order = manager.placeOrder("Alex", "Iced Latte", "Medium");
        assertEquals(1, order.getOrderNumber());
        assertEquals("Iced Latte", order.getDrinkType());
        assertEquals(4.0, manager.getEstimatedReadyMinutes(order.getOrderNumber()), 0.001);
    }
}

