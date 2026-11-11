# BrewQueue – Coffee Shop Order Manager

CS 2114 · Project 3 · Deliverable 3: First Prototype

Team: Ash Satasia, Seongjin Nam, Hasini Pottipati, Liangru Ji

BrewQueue is a console app where customers place drink orders and baristas complete them in the order they were received. Orders are stored in a singly linked queue (FIFO).

## How to Run

### Run the model (no view)

`LinkedQueueTest.java`

1. Open the project in Eclipse.
2. Right-click `LinkedQueueTest.java` and select **Run As → JUnit Test**.
3. The tests check enqueue, dequeue, peek, isEmpty, size, toList, FIFO ordering, and invalid operations. All 15 JUnit tests passed successfully without requiring the View or Controller.

`OrderManagerTest.java` (Seongjin Nam)

1. Open the project in Eclipse.
2. Make sure Order.java, LinkedQueue.java, and OrderManager.java are implemented.
3. Right-click `OrderManagerTest.java` and select **Run As → JUnit Test**.
4. The 16 prepared tests cover order placement, FIFO ordering, unique order numbers, invalid inputs, empty queues, and order cancellation.
5. These tests still need to be executed with the completed Order.java.

### Run the thin view

1. Run [TODO: class with the `main` method, e.g. `BrewQueueController.java`].
2. A numbered menu appears in the console. Enter a number to choose an option.

## Reflection

### What changed from the specification?

[TODO: For each change, say what the spec said, what you built instead, and why.]

- **Classes:** [TODO] LinkedQueue was implemented as specified using a singly linked list with front and rear node references, a size field, and a private Node class. No changes were needed to the original class design.
- **Method signatures:** [TODO] All seven specified LinkedQueue methods were implemented without changes.
- **Where validation lives:** [TODO] LinkedQueue handles its own invalid operations by throwing IllegalArgumentException for null entries and NoSuchElementException when attempting to dequeue or peek at an empty queue.
- **Test cases:** [TODO] Created 15 JUnit tests covering normal operations, FIFO ordering, empty queue behavior, invalid inputs, and repeated operations. Additional tests were added to verify that toList returns an independent list and that the generic queue works with different data types. All 15 tests passed.

**OrderManager (Seongjin Nam):**

- **Classes:** OrderManager was implemented based on our specification using LinkedQueue<Order> to manage pending customer orders. No additional classes were needed for the order management logic.
- **Method signatures:** Implemented placeOrder(), viewNextOrder(), completeNextOrder(), getPendingOrders(), getPendingOrderCount(), and cancelOrder(). The methods follow our original design.
- **Where validation lives:** OrderManager checks for invalid customer names and blank drink or size inputs. Invalid inputs throw IllegalArgumentException before changing the queue or advancing the order number. The Order class is expected to validate the permitted drink types and sizes.
- **Test cases:** Prepared 16 JUnit tests covering order creation, FIFO behavior, unique order numbers, empty queue operations, invalid inputs, and order cancellation. These tests still need to be run after Order.java is completed.

### Did the model really stand alone?

[TODO: Can the model run without `ConsoleView`? Did anything have to move across the model/view line? Does anything in the model still know about the view?]

LinkedQueue works independently of ConsoleView and BrewQueueController. All 15 JUnit tests ran successfully without requiring any user interface. No view-related logic was needed in the queue implementation.

**OrderManager (Seongjin Nam):**

OrderManager was designed to work independently of ConsoleView and BrewQueueController. It uses only Order and LinkedQueue to manage customer orders. No user interface code was added to OrderManager, and its methods can be tested directly using JUnit. No logic needed to be moved across the Model/View boundary in this implementation. Full integration testing is still pending.

### What happened to our unknowns?

In our scope, we listed three things we didn't know how to do yet:

- **Cancelling an order from the middle of the queue:** The cancelOrder() method was implemented in OrderManager using dequeue() and enqueue() to remove a specific order while preserving FIFO order. The method still needs to be tested with the completed Order class.
- **Calculating estimated wait times:** [TODO: figured out / still open]
- **Saving pending orders to a file:** [TODO: figured out / still open]

### What doesn't work yet?

[TODO: MVP features, bad-input cases, or tests that are missing or failing.]

No issues were found in the LinkedQueue implementation during testing. All 15 JUnit tests passed. Integration with OrderManager still needs to be verified separately.

**OrderManager (Seongjin Nam):**

OrderManager.java and OrderManagerTest.java have been prepared, but the tests have not been executed yet because Order.java is not complete. Integration with the Controller and View also needs to be verified. The main order management methods are implemented, but the complete MVP workflow has not yet been tested.

## Next Steps

### What is left to finish the MVP

In the order we will do them:

1. [TODO]
2. [TODO]
3. [TODO]

The LinkedQueue implementation and its tests are complete. The next step is to verify that OrderManager correctly uses the queue to place, view, and complete orders while maintaining FIFO ordering.

**OrderManager (Seongjin Nam):**

Once Order.java is completed, I will run OrderManagerTest.java and fix any failing tests. I will verify that orders are added and completed in FIFO order, invalid inputs do not modify the queue, and order numbers remain unique. I will also help verify the integration between OrderManager and BrewQueueController.

### Change feature

**Feature:** [TODO: one non-trivial feature not in the MVP, e.g. Order Cancellation or Estimated Wait Times]

**Prediction of what it will touch:**

| Class | Will it change? | Methods affected |
| --- | --- | --- |
| `Order` | [TODO] | [TODO] |
| `LinkedQueue` | [TODO] | [TODO] |
| `OrderManager` | [TODO] | [TODO] |
| `ConsoleView` | [TODO] | [TODO] |
| `BrewQueueController` | [TODO] | [TODO] |

### Stretch goals

- **Committing to:** [TODO]
- **Dropping:** [TODO]

### New tests needed

[TODO: methods and bad-input cases the new work must cover.]

Additional integration tests should verify that OrderManager maintains FIFO ordering when placing and completing multiple orders. Tests should also check that invalid order requests do not modify the queue and that repeated operations maintain the correct queue size and order.

**OrderManager (Seongjin Nam):**

Additional testing will verify that placeOrder() rejects invalid input without changing the queue or order number, viewNextOrder() does not remove an order, and completeNextOrder() processes orders in FIFO order. Tests for cancelOrder() will verify cancellation from the front, middle, and rear of the queue, as well as invalid order numbers. Integration tests will also be needed after the Controller and View are connected.

### Who does what

| Remaining item | Owner |
| --- | --- |
| [TODO] | [TODO] |
| [TODO] | [TODO] |
| [TODO] | [TODO] |
| Verify OrderManager integration and run OrderManagerTest | Seongjin Nam |

## Attribution

| File | Team member(s) | Contribution |
| --- | --- | --- |
| `Order.java` | [TODO] | [TODO: wrote, revised, reviewed, tested, GenAI-assisted, …] |
| `LinkedQueue.java` | Ash Satasia | Implemented the generic singly linked queue, including enqueue, dequeue, peek, isEmpty, size, and toList methods. |
| `OrderManager.java` | Seongjin Nam | GenAI-assisted implementation of order placement, FIFO processing, pending order retrieval, input validation, and order cancellation. |
| `ConsoleView.java` | [TODO] | [TODO] |
| `BrewQueueController.java` | [TODO] | [TODO] |
| `LinkedQueueTest.java` | Ash Satasia | Created and ran 15 JUnit tests covering FIFO behavior, queue operations, invalid inputs, and edge cases. All 15 tests passed. |
| `OrderManagerTest.java` | Seongjin Nam | GenAI-assisted preparation of 16 JUnit tests covering order placement, FIFO ordering, invalid inputs, empty queues, and cancellation. Testing is pending. |

### GenAI Note

**Seongjin Nam:** I used ChatGPT to help implement OrderManager.java and draft OrderManagerTest.java based on our Deliverable 2 specification. I asked ChatGPT to help with FIFO queue operations, input validation, order cancellation, and JUnit test cases. I reviewed the suggested code against our MVC design and project requirements. The tests still need to be executed after Order.java is completed.

[TODO: Other team members should add how they used GenAI, or state that they did not use it.]
