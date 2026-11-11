
import java.util.List;

/**
 * Manages customer orders using a FIFO LinkedQueue.
 * This model class does not depend on the console view or controller.
 */
public class OrderManager {

    private final LinkedQueue<Order> pendingOrders;
    private int nextOrderNumber;

    /** Creates an empty order queue. Order numbers start at 1. */
    public OrderManager() {
        pendingOrders = new LinkedQueue<>();
        nextOrderNumber = 1;
    }

    /**
     * Creates and enqueues a new order.
     * Invalid orders do not change the queue or use an order number.
     * The Order constructor checks whether the drink and size are on the menu.
     */
    public Order placeOrder(String customerName, String drinkType, String size) {
        if (customerName == null || customerName.trim().isEmpty()
            || customerName.trim().length() > 30) {
            throw new IllegalArgumentException("Customer name must be 1-30 characters.");
        }
        if (drinkType == null || drinkType.trim().isEmpty()
            || size == null || size.trim().isEmpty()) {
            throw new IllegalArgumentException("Drink and size cannot be blank.");
        }

        // Construct before changing the queue or nextOrderNumber.
        Order order = new Order(nextOrderNumber,
            customerName.trim(), drinkType.trim(), size.trim());
        pendingOrders.enqueue(order);
        nextOrderNumber++;
        return order;
    }

    /** Returns the next order without removing it, or null if empty. */
    public Order viewNextOrder() {
        if (pendingOrders.isEmpty()) {
            return null;
        }
        return pendingOrders.peek();
    }

    /** Removes and returns the next order, or null if empty. */
    public Order completeNextOrder() {
        if (pendingOrders.isEmpty()) {
            return null;
        }
        return pendingOrders.dequeue();
    }

    /** Returns a separate list of pending orders in FIFO order. */
    public List<Order> getPendingOrders() {
        return pendingOrders.toList();
    }

    /** Returns the number of orders waiting in line. */
    public int getPendingOrderCount() {
        return pendingOrders.size();
    }

    /**
     * Removes an order by its number without changing the order of others.
     * Returns false if the order number is invalid or not in the queue.
     * Uses only public queue methods, keeping the linked nodes private.
     */
    public boolean cancelOrder(int orderNumber) {
        if (orderNumber <= 0) {
            return false;
        }

        int originalSize = pendingOrders.size();
        boolean removed = false;

        for (int i = 0; i < originalSize; i++) {
            Order current = pendingOrders.dequeue();
            if (!removed && current.getOrderNumber() == orderNumber) {
                removed = true;
            }
            else {
                pendingOrders.enqueue(current);
            }
        }
        return removed;
    }
}
