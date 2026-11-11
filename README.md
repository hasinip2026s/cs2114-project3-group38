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

### Did the model really stand alone?

[TODO: Can the model run without `ConsoleView`? Did anything have to move across the model/view line? Does anything in the model still know about the view?]

LinkedQueue works independently of ConsoleView and BrewQueueController. All 15 JUnit tests ran successfully without requiring any user interface. No view-related logic was needed in the queue implementation.

### What happened to our unknowns?

In our scope, we listed three things we didn't know how to do yet:

- **Cancelling an order from the middle of the queue:** [TODO: figured out / still open]
- **Calculating estimated wait times:** [TODO: figured out / still open]
- **Saving pending orders to a file:** [TODO: figured out / still open]

### What doesn't work yet?

[TODO: MVP features, bad-input cases, or tests that are missing or failing.]

No issues were found in the LinkedQueue implementation during testing. All 15 JUnit tests passed. Integration with OrderManager still needs to be verified separately.

## Next Steps

### What is left to finish the MVP

In the order we will do them:

1. [TODO]
2. [TODO]
3. [TODO]

The LinkedQueue implementation and its tests are complete. The next step is to verify that OrderManager correctly uses the queue to place, view, and complete orders while maintaining FIFO ordering.

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

### Who does what

| Remaining item | Owner |
| --- | --- |
| [TODO] | [TODO] |
| [TODO] | [TODO] |
| [TODO] | [TODO] |

## Attribution

| File | Team member(s) | Contribution |
| --- | --- | --- |
| `Order.java` | [TODO] | [TODO: wrote, revised, reviewed, tested, GenAI-assisted, …] |
| `LinkedQueue.java` | Ash Satasia | Implemented the generic singly linked queue, including enqueue, dequeue, peek, isEmpty, size, and toList methods. |
| `OrderManager.java` | [TODO] | [TODO] |
| `ConsoleView.java` | [TODO] | [TODO] |
| `BrewQueueController.java` | [TODO] | [TODO] |
| `LinkedQueueTest.java` | Ash Satasia | Created and ran 15 JUnit tests covering FIFO behavior, queue operations, invalid inputs, and edge cases. All 15 tests passed. |

### GenAI Note

[TODO: How each person used GenAI (what they asked and what they changed), or state that it wasn't used.]
