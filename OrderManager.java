import java.util.List;

/**
 * Model layer: manages the FIFO order queue without depending on the view.
 * ETA is based on the sum of pending orders' estimated preparation times.
 * It is not a wall-clock countdown.
 */
public class OrderManager {
    private final LinkedQueue<Order> pendingOrders;
    private int nextOrderNumber;

    public OrderManager() {
        pendingOrders = new LinkedQueue<>();
        nextOrderNumber = 1;
    }

    /** Invalid orders do not change the queue or consume an order number. */
    public Order placeOrder(String customerName, String drinkType, String size) {
        if (customerName == null || customerName.trim().isEmpty()
            || customerName.trim().length() > 30) {
            throw new IllegalArgumentException("Customer name must be 1-30 characters.");
        }
        if (drinkType == null || drinkType.trim().isEmpty()
            || size == null || size.trim().isEmpty()) {
            throw new IllegalArgumentException("Drink and size cannot be blank.");
        }

        Order order = new Order(nextOrderNumber, customerName.trim(),
            drinkType.trim(), size.trim());
        pendingOrders.enqueue(order);
        nextOrderNumber++;
        return order;
    }

    public Order viewNextOrder() {
        return pendingOrders.isEmpty() ? null : pendingOrders.peek();
    }

    public Order completeNextOrder() {
        return pendingOrders.isEmpty() ? null : pendingOrders.dequeue();
    }

    public List<Order> getPendingOrders() {
        return pendingOrders.toList();
    }

    public int getPendingOrderCount() {
        return pendingOrders.size();
    }

    /**
     * Cancels an order without changing the relative order of remaining items.
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

    /**
     * Returns the time until this pending order can start being prepared.
     * Returns -1 if the order is not pending or its number is invalid.
     */
    public double getEstimatedWaitMinutes(int orderNumber) {
        if (orderNumber <= 0) {
            return -1;
        }
        double wait = 0.0;
        for (Order order : pendingOrders.toList()) {
            if (order.getOrderNumber() == orderNumber) {
                return wait;
            }
            wait += order.getPrepMinutes();
        }
        return -1;
    }

    /**
     * Returns the total time until this pending order is ready, including
     * this order's preparation time. Returns -1 if it is not pending.
     */
    public double getEstimatedReadyMinutes(int orderNumber) {
        double elapsed = 0.0;
        if (orderNumber <= 0) {
            return -1;
        }
        for (Order order : pendingOrders.toList()) {
            elapsed += order.getPrepMinutes();
            if (order.getOrderNumber() == orderNumber) {
                return elapsed;
            }
        }
        return -1;
    }
}
