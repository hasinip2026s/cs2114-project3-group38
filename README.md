# BrewQueue – Coffee Shop Order Manager

CS 2114 · Project 3 · Deliverable 3: First Prototype

**Team:** Ash Satasia, Seongjin Nam, Hasini Pottipati, Liangru Ji

BrewQueue is a console app where customers place drink orders and baristas complete them in the order they were received. Orders are stored in a singly linked queue (FIFO).

## How to Run

### Run the model (no view)

**LinkedQueueTest.java**

1. Open the project in Eclipse.
2. Right-click `LinkedQueueTest.java` and select **Run As → JUnit Test**.
3. The tests check enqueue, dequeue, peek, isEmpty, size, toList, FIFO ordering, and invalid operations. All 15 JUnit tests passed successfully without requiring the View or Controller.

**OrderManagerTest.java**

1. Open the project in Eclipse.
2. Make sure `Order.java`, `LinkedQueue.java`, and `OrderManager.java` are implemented.
3. Right-click `OrderManagerTest.java` and select **Run As → JUnit Test**.
4. The 16 prepared tests cover order placement, FIFO ordering, unique order numbers, invalid inputs, empty queue behavior, and order cancellation.
5. These tests have not yet been verified with the completed Order class.

### Run the thin view

1. Once `ConsoleView.java` and `BrewQueueController.java` are implemented, open the project in Eclipse.
2. Right-click `BrewQueueController.java` and select **Run As → Java Application**, assuming the controller contains the main method.
3. A numbered menu should appear in the console.
4. Select an option to place an order, view the next order, complete an order, display pending orders, or exit.

The thin view and end-to-end execution still need to be verified.

## Reflection

### What changed from the specification?

- **Classes:** LinkedQueue was implemented as specified using a singly linked list with front and rear node references, a size field, and a private Node class. No changes were needed to the original queue design. OrderManager follows the specification by using `LinkedQueue<Order>` to manage pending orders.

- **Method signatures:** All seven specified LinkedQueue methods were implemented without changes. OrderManager includes `placeOrder()`, `viewNextOrder()`, `completeNextOrder()`, `getPendingOrders()`, `getPendingOrderCount()`, and `cancelOrder()`. The count method provides direct access to the number of pending orders, supporting the count requirement in our original test plan.

- **Where validation lives:** LinkedQueue handles invalid operations by throwing `IllegalArgumentException` for null entries and `NoSuchElementException` for dequeue or peek operations on an empty queue. OrderManager checks for null, blank, or overly long customer names and blank drink or size inputs. This keeps basic order validation in the Model and prevents invalid requests from modifying the queue. The Order constructor is expected to validate the permitted drink types and sizes. Controller-level input handling still needs integration testing.

- **Test cases:** Ash created 15 JUnit tests covering queue operations, FIFO ordering, empty queue behavior, invalid inputs, and repeated operations. Additional tests verify that toList returns an independent list and that the generic queue supports different data types. All 15 LinkedQueue tests passed. Seongjin prepared 16 additional JUnit tests for OrderManager, including FIFO behavior, unique order numbers, input validation, empty queue operations, and cancellation. These tests still need to be executed with the completed Order class.

### Did the model really stand alone?

LinkedQueue works independently of ConsoleView and BrewQueueController. All 15 JUnit tests ran successfully without requiring any user interface. No view-related logic was needed in the queue implementation.

OrderManager is also designed to work independently of the View and Controller. It communicates directly with Order and LinkedQueue to manage customer orders. It does not use Scanner, print console messages, or depend on ConsoleView.

The OrderManager tests are intended to verify this separation, but full model integration testing is still pending. No Model/View responsibilities have needed to move across the boundary in the code prepared so far.

### What happened to our unknowns?

In our scope, we listed three things we did not know how to do yet:

- **Cancelling an order from the middle of the queue:** A `cancelOrder()` method has been prepared in OrderManager. It uses dequeue and enqueue operations to remove a matching order while preserving the relative order of all remaining orders. Integration tests are still needed, and the feature is not yet connected to the console interface.

- **Calculating estimated wait times:** Still open. We plan to calculate estimated waiting times using the preparation times of orders ahead in the queue. This will be our main change feature for Deliverable 4.

- **Saving pending orders to a file:** Still open. We have not implemented file persistence and do not plan to include it in the next deliverable.

### What doesn't work yet?

No issues were found in the LinkedQueue implementation during testing. All 15 LinkedQueue JUnit tests passed.

OrderManager and its JUnit tests have been prepared, but integration with the completed Order class has not yet been verified.

The full console workflow still needs testing. The remaining work includes connecting the Model to the Controller and View, testing all five MVP options, and confirming that invalid inputs do not crash the program.

The order cancellation method is not yet accessible through the console interface, and estimated wait times have not been implemented.

## Next Steps

### What is left to finish the MVP

In the order we will do them:

1. **Complete and verify the Model:** Finish Order.java, integrate it with LinkedQueue and OrderManager, and run OrderManagerTest.java. Fix any issues related to order validation, unique order numbers, FIFO ordering, and empty queues.

2. **Connect the Controller and View:** Complete ConsoleView.java and BrewQueueController.java. Connect the menu options to OrderManager so customers can place orders, view the next order, complete orders, and display pending orders.

3. **Test the complete MVP:** Run the application from the main method and verify all five MVP features. Test invalid menu selections, blank customer names, invalid drinks and sizes, and operations on an empty queue. Fix any integration or runtime errors before Deliverable 4.

The LinkedQueue implementation and its tests are complete. The next priority is to verify that OrderManager correctly manages orders through LinkedQueue and maintains FIFO ordering.

### Change feature

**Feature: Estimated Wait Times**

For Deliverable 4, we plan to add estimated waiting times for customer orders.

The program will calculate an order's estimated wait based on the preparation times of the orders ahead of it in the queue. The Controller will retrieve this value from the Model and ask the View to display it to the user.

This is a non-trivial feature because it adds new order-management calculations and makes the results accessible through the Controller and View.

**Prediction of what it will touch:**

| Class | Will it change? | Methods affected |
| --- | --- | --- |
| `Order` | Probably not | Use the existing planned `getPrepMinutes()` method |
| `LinkedQueue` | No | Existing `toList()` method can provide pending orders in FIFO order |
| `OrderManager` | Yes | Add `getEstimatedWaitMinutes(int orderNumber)` to calculate the waiting time |
| `ConsoleView` | Yes | Add `showEstimatedWaitTime()` to display the result |
| `BrewQueueController` | Yes | Update `run()` and add a handler for the wait-time feature |

Our design predicts that the LinkedQueue implementation should not need to change because the feature can use existing queue operations.

We will compare these predictions with the actual changes made during Deliverable 4.

### Stretch goals

- **Committing to:** Estimated Wait Times as our main Deliverable 4 change feature. We will also aim to complete the Order Cancellation feature by connecting the existing model method to the Controller and View, if time permits.
- **Dropping:** Saving pending orders to a file. This feature is outside our current implementation priorities.

### New tests needed

The following tests will be added or completed:

- **OrderManager.placeOrder():** Verify successful order creation, unique order numbers, and correct FIFO insertion.
- **OrderManager.viewNextOrder():** Verify that viewing an order does not remove it and that an empty queue returns null.
- **OrderManager.completeNextOrder():** Verify that orders are completed in FIFO order and empty queues are handled safely.
- **OrderManager.getPendingOrders():** Verify correct order, queue size, and independent list behavior.
- **OrderManager.cancelOrder():** Verify cancellation of the first, middle, and last orders, as well as invalid or nonexistent order numbers.
- **OrderManager.getEstimatedWaitMinutes():** Verify waiting times for the first order, later orders, an empty queue, and an order number that does not exist.
- **Input validation:** Test blank names, invalid drinks, invalid sizes, and non-numeric menu input.
- **Controller integration:** Verify that menu selections call the correct Model methods and return appropriate results.
- **Complete workflow:** Place several orders, view pending orders, complete orders, and exit without crashing.

Additional integration tests should verify that OrderManager maintains FIFO ordering when placing and completing multiple orders. Invalid requests must not modify the queue or incorrectly advance order numbers.

### Who does what

The following assignments follow our Deliverable 2 division of work and remain subject to team confirmation.

| Remaining item | Owner |
| --- | --- |
| Verify LinkedQueue operations and assist with queue-related issues | Ash Satasia |
| Complete OrderManager integration, run OrderManagerTest, and implement the wait-time calculation | Seongjin Nam |
| Finish Order.java, ConsoleView.java, and their tests; add wait-time output | Hasini Pottipati |
| Finish BrewQueueController.java, test the menu workflow, and connect the wait-time feature | Liangru Ji |
| Review integration, complete README, and prepare for the grading session | All team members |

## Attribution

| File | Team member(s) | Contribution |
| --- | --- | --- |
| `Order.java` | Hasini Pottipati | Assigned to implement the Order model, including order details, prices, and preparation times. Implementation status pending confirmation. |
| `LinkedQueue.java` | Ash Satasia | Implemented the generic singly linked queue, including enqueue, dequeue, peek, isEmpty, size, and toList methods. |
| `OrderManager.java` | Seongjin Nam | GenAI-assisted preparation of order placement, FIFO processing, pending order retrieval, input validation, and order cancellation logic. |
| `ConsoleView.java` | Hasini Pottipati | Assigned to implement console input, menu display, and order output. Implementation status pending confirmation. |
| `BrewQueueController.java` | Liangru Ji | Assigned to implement menu handling and communication between Model and View. Implementation status pending confirmation. |
| `LinkedQueueTest.java` | Ash Satasia | Created and ran 15 JUnit tests covering FIFO behavior, queue operations, invalid inputs, and edge cases. All 15 tests passed. |
| `OrderManagerTest.java` | Seongjin Nam | GenAI-assisted preparation of 16 JUnit tests covering order placement, FIFO processing, invalid inputs, empty queues, and cancellation. Test execution pending. |

Other assigned test files should be added to this table when they are created, with their actual contributors and contributions.

### GenAI Note

**Seongjin Nam:** I used ChatGPT to help implement OrderManager.java and draft OrderManagerTest.java based on our Deliverable 2 specification. I asked for assistance with FIFO queue operations, input validation, order cancellation, JUnit testing, and predicting which classes would need changes for the Estimated Wait Times feature. I reviewed the suggested implementation against our MVC design and the methods provided by LinkedQueue. Full integration testing is still pending.
