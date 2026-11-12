
# BrewQueue – Coffee Shop Order Manager

CS 2114 · Project 3

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
2. Make sure `Order.java`, `LinkedQueue.java`, and `OrderManager.java` are implemented.
3. Right-click `OrderManagerTest.java` and select **Run As → JUnit Test**.
4. The 16 prepared tests cover order placement, FIFO ordering, unique order numbers, invalid inputs, empty queues, and order cancellation.
5. These tests still need to be executed after `Order.java` is completed.
`OrderTest.java` (Hasini Pottipati)
 
1. Open the project in Eclipse.
2. Right-click `OrderTest.java` and select **Run As → JUnit Test**.
3. The 17 tests check the constructor, all getters, prices, prep times, `toString()`, and invalid names, drinks, and sizes. Order does not need any other class to run.
### Run the thin view
 
1. Run [TODO: class with the `main` method, e.g. `BrewQueueController.java`].
2. A numbered menu appears in the console. Enter a number to choose an option.
`ConsoleViewTest.java` (Hasini Pottipati)
 
1. Right-click `ConsoleViewTest.java` and select **Run As → JUnit Test**.
2. The 7 tests use scripted Scanner input and captured console output to check the menu, numbered choices, trimmed input, input ending, messages, and the pending orders list (including an empty list).
## Reflection
 
### What changed from the specification?
 
[TODO: For each change, say what the spec said, what you built instead, and why.]
 
- **Classes:** [TODO] LinkedQueue was implemented as specified using a singly linked list with front and rear node references, a size field, and a private Node class. No changes were needed to the original class design.
- **Method signatures:** [TODO] All seven specified LinkedQueue methods were implemented without changes.
- **Where validation lives:** [TODO] LinkedQueue handles its own invalid operations by throwing IllegalArgumentException for null entries and NoSuchElementException when attempting to dequeue or peek at an empty queue.
- **Test cases:** [TODO] Created 15 JUnit tests covering normal operations, FIFO ordering, empty queue behavior, invalid inputs, and repeated operations. Additional tests were added to verify that toList returns an independent list and that the generic queue works with different data types. All 15 tests passed.
**OrderManager (Seongjin Nam):**
 
- **Classes:** OrderManager was implemented based on our specification using `LinkedQueue<Order>` to manage pending customer orders. No additional classes were needed for the order management logic.
- **Method signatures:** Implemented `placeOrder()`, `viewNextOrder()`, `completeNextOrder()`, `getPendingOrders()`, `getPendingOrderCount()`, and `cancelOrder()`. The methods follow our original design.
- **Where validation lives:** OrderManager checks for invalid customer names and blank drink or size inputs. Invalid inputs throw IllegalArgumentException before changing the queue or advancing the order number. The Order class is expected to validate permitted drink types and sizes.
- **Test cases:** Prepared 16 JUnit tests covering order creation, FIFO behavior, unique order numbers, empty queue operations, invalid inputs, and order cancellation. These tests still need to be run after Order.java is completed.
**BrewQueueController (Liangru Ji):**
 
- **Classes:** BrewQueueController was implemented to connect OrderManager
  and ConsoleView. No additional controller classes were needed.
- **Method signatures:** Implemented the constructor, `run()`, and `main()`,
  along with private helper methods for placing, viewing, completing, and
  cancelling orders.
- **Where validation lives:** The Controller checks menu numbers, customer
  names, drink choices, and size choices. Invalid input displays an error
  message and asks the user to try again.
- **Test cases:** Prepared JUnit tests using scripted Scanner input to test
  placing, completing, and cancelling orders, invalid menu choices, blank
  names, and input ending. Full testing is pending until Order and
  ConsoleView are complete.
**Order (Hasini Pottipati):**
 
- **Classes:** Order was implemented as specified, with four fields (order number, customer name, drink type, size) that do not change after the order is created.
- **Method signatures:** All eight specified methods were implemented without changes: the constructor, `getOrderNumber()`, `getCustomerName()`, `getDrinkType()`, `getSize()`, `getPrice()`, `getPrepMinutes()`, and `toString()`.
- **Prices and prep times:** The spec listed `getPrice()` and `getPrepMinutes()` but only gave one example ("Medium Latte, $4.50, about 4 min"). I set a base price and prep time for each drink and added extra cost and time for Medium and Large, matching that example. These values are stored inside Order, so no other class needs to know them.
- **Where validation lives:** As the spec said, the Order constructor throws IllegalArgumentException for a blank name or a drink or size not on the menu. Drink and size names must match the menu exactly (for example, "Latte" and "Medium").
- **Test cases:** Created 17 JUnit tests covering every Order row in our test plan, plus tests for invalid drinks and sizes, prices, prep times, and `toString()`.
**ConsoleView (Hasini Pottipati):**
 
- **Classes:** ConsoleView was implemented as specified. It only prints output and reads input; it never accesses the LinkedQueue or changes order data.
- **Method signatures:** All six specified methods were implemented without changes: the constructor, `showMainMenu()`, `showChoices()`, `readLine()`, `showMessage()`, and `showPendingOrders()`.
- **Where validation lives:** ConsoleView does not validate input, which matches our spec. `readLine()` trims extra spaces and returns null if input has ended. The Controller decides whether the input is valid.
- **Test cases:** Our test plan did not list ConsoleView tests, but the division of work assigned ConsoleViewTest. Created 7 JUnit tests using scripted input and captured output, including the empty pending list case.
### Did the model really stand alone?
 
[TODO: Can the model run without `ConsoleView`? Did anything have to move across the model/view line? Does anything in the model still know about the view?]
 
LinkedQueue works independently of ConsoleView and BrewQueueController. All 15 JUnit tests ran successfully without requiring any user interface. No view-related logic was needed in the queue implementation.
 
**OrderManager (Seongjin Nam):**
 
OrderManager was designed to work independently of ConsoleView and BrewQueueController. It uses only Order and LinkedQueue to manage customer orders. No user interface code was added to OrderManager, and its methods can be tested directly using JUnit. No logic needed to be moved across the Model/View boundary in this implementation. Full integration testing is still pending.
 
**Order (Hasini Pottipati):**
 
Order does not use any other class in the project. It has no printing or input code, and OrderTest creates Order objects directly without a view or controller.
 
**ConsoleView (Hasini Pottipati):**
 
The dependency only goes one way: ConsoleView uses Order's `toString()` to display orders, but no model class refers to ConsoleView. Nothing had to be moved across the model/view line.
 
### What happened to our unknowns?
 
In our scope, we listed three things we didn't know how to do yet:
 
- **Cancelling an order from the middle of the queue:** The `cancelOrder()` method was implemented in OrderManager using dequeue() and enqueue() to remove a specific order while preserving FIFO order. The method still needs to be tested with the completed Order class.
- **Calculating estimated wait times:** Still open. We plan to implement this feature in Deliverable 4 using the preparation times of pending orders.
- **Saving pending orders to a file:** Still open. File persistence has not been implemented.
**Order (Hasini Pottipati):** In our scope, we said we might give each drink a basic preparation time for estimated wait times. Order now has `getPrepMinutes()`, which gives a prep time based on the drink and size, so that part is figured out. The wait time calculation itself is still open.
 
### What doesn't work yet?
 
[TODO: MVP features, bad-input cases, or tests that are missing or failing.]
 
No issues were found in the LinkedQueue implementation during testing. All 15 JUnit tests passed. Integration with OrderManager still needs to be verified separately.
 
**OrderManager (Seongjin Nam):**
 
OrderManager.java and OrderManagerTest.java have been prepared, but the tests have not been executed yet because Order.java is not complete. Integration with the Controller and View also needs to be verified. The main order management methods are implemented, but the complete MVP workflow has not yet been tested.
 
**Order and ConsoleView (Hasini Pottipati):**
 
Order.java, ConsoleView.java, OrderTest.java, and ConsoleViewTest.java are written but still need to be run in Eclipse to confirm all tests pass. ConsoleView has not yet been tested together with BrewQueueController.
 
## Next Steps
 
### What is left to finish the MVP
 
In the order we will do them:
 
1. [TODO]
2. [TODO]
3. [TODO]
The LinkedQueue implementation and its tests are complete. The next step is to verify that OrderManager correctly uses the queue to place, view, and complete orders while maintaining FIFO ordering.
 
**OrderManager (Seongjin Nam):**
 
Once Order.java is completed, I will run OrderManagerTest.java and fix any failing tests. I will verify that orders are added and completed in FIFO order, invalid inputs do not modify the queue, and order numbers remain unique. I will also help verify the integration between OrderManager and BrewQueueController.
 
**Order and ConsoleView (Hasini Pottipati):**
 
I will run OrderTest.java and ConsoleViewTest.java and fix any failing tests. Then I will help check that ConsoleView works correctly with BrewQueueController on the full MVP path.
 
### Change feature
 
**Feature: Estimated Wait Times**
 
For Deliverable 4, we plan to add estimated waiting times for customer orders. The program will calculate an order's estimated wait based on the preparation times of the orders ahead of it in the queue.
 
This is a non-trivial feature because it adds new calculation logic to the Model and allows users to access the estimated wait through the Controller and View.
 
**Prediction of what it will touch:**
 
| Class | Will it change? | Methods affected |
| --- | --- | --- |
| `Order` | No, if the planned method is implemented | Use existing `getPrepMinutes()` |
| `LinkedQueue` | No | Use existing `toList()` |
| `OrderManager` | Yes | Add `getEstimatedWaitMinutes(int orderNumber)` |
| `ConsoleView` | Yes | Add `showEstimatedWaitTime()` |
| `BrewQueueController` | Yes | Update `run()` and add a handler for estimated wait times |
 
We expect LinkedQueue to remain unchanged because the current `toList()` method provides the pending orders in FIFO order. OrderManager will calculate the estimated waiting time by adding the preparation times of orders ahead of the selected order.
 
The Controller will request this information from OrderManager, and ConsoleView will display the result. This keeps the queue implementation independent of the user interface.
 
**Order (Hasini Pottipati):** `getPrepMinutes()` is now implemented in Order, so Order should not need to change for this feature.
 
### Stretch goals
 
- **Committing to:** Estimated Wait Times as our main change feature for Deliverable 4.
- **Dropping:** Saving pending orders to a file.
- **Already implemented in the Model, pending testing and integration:** Order Cancellation.
- **Already implemented in Order (Hasini Pottipati):** Prices. `getPrice()` returns a price based on the drink and size, and each order's price appears in `toString()`.

### New tests needed
 
[TODO: methods and bad-input cases the new work must cover.]
 
Additional integration tests should verify that OrderManager maintains FIFO ordering when placing and completing multiple orders. Tests should also check that invalid order requests do not modify the queue and that repeated operations maintain the correct queue size and order.
 
**OrderManager (Seongjin Nam):**
 
Additional testing will verify that `placeOrder()` rejects invalid inputs without changing the queue or order number, `viewNextOrder()` does not remove an order, and `completeNextOrder()` processes orders in FIFO order.
 
Tests for `cancelOrder()` will verify cancellation from the front, middle, and rear of the queue, as well as invalid order numbers.
 
For the estimated wait time feature, new tests will verify the waiting time for the first order, multiple pending orders, an empty queue, and an order number that does not exist.
 
Integration tests will also be needed after the Controller and View are connected.
 
**ConsoleView (Hasini Pottipati):**
 
New ConsoleViewTest tests will check that `showEstimatedWaitTime()` prints the wait time correctly.
 
### Who does what
 
| Remaining item | Owner |
| --- | --- |
| Run OrderTest and ConsoleViewTest and fix any failures | Hasini Pottipati |
| Add `showEstimatedWaitTime()` to ConsoleView and test it | Hasini Pottipati |
| Implement and test BrewQueueController | Liangru Ji |
| Verify OrderManager integration and run OrderManagerTest | Seongjin Nam |
| Implement estimated wait time calculation in OrderManager | Seongjin Nam |
 
## Attribution
 
| File | Team member(s) | Contribution |
| --- | --- | --- |
| `Order.java` | Hasini Pottipati | GenAI-assisted implementation of the order fields, getters, menu validation, prices, prep times, and `toString()`; reviewed against our specification. |
| `LinkedQueue.java` | Ash Satasia | Implemented the generic singly linked queue, including enqueue, dequeue, peek, isEmpty, size, and toList methods. |
| `OrderManager.java` | Seongjin Nam | GenAI-assisted implementation of order placement, FIFO processing, pending order retrieval, input validation, and order cancellation. |
| `ConsoleView.java` | Hasini Pottipati | GenAI-assisted implementation of the menu, numbered choices, input reading, messages, and pending order display; reviewed against our specification. |
| `BrewQueueController.java` | Liangru Ji | GenAI-assisted code review, initial drafting, and development of a basic framework for the Controller implementation. |
| `BrewQueueControllerTest.java` | Liangru Ji | GenAI-assisted preparation of JUnit tests using scripted input for menu choices, order placement, completion, cancellation, and invalid input. |
| `LinkedQueueTest.java` | Ash Satasia | Created and ran 15 JUnit tests covering FIFO behavior, queue operations, invalid inputs, and edge cases. All 15 tests passed. |
| `OrderManagerTest.java` | Seongjin Nam | GenAI-assisted preparation of 16 JUnit tests covering order placement, FIFO ordering, invalid inputs, empty queues, and cancellation. Testing is pending. |
| `OrderTest.java` | Hasini Pottipati | GenAI-assisted preparation of 17 JUnit tests from our Order test plan, plus tests for invalid drinks and sizes, prices, prep times, and `toString()`. |
| `ConsoleViewTest.java` | Hasini Pottipati | GenAI-assisted preparation of 7 JUnit tests using scripted input and captured output. |
 
### GenAI Note
 
**Seongjin Nam:** I used ChatGPT to help implement OrderManager.java and draft OrderManagerTest.java based on our Deliverable 2 specification. I asked ChatGPT for help with FIFO queue operations, input validation, order cancellation, JUnit test cases, and predicting which classes would need changes for the Estimated Wait Times feature. I reviewed the suggested code against our MVC design and project requirements. Full integration testing is still pending.
 
**Liangru Ji:** GenAI was used to review parts of the code, help organize an initial draft, and suggest a basic structure for BrewQueueController and its tests. I reviewed and revised the suggestions to match our specification, coding style, and team project structure.
 
**Hasini Pottipati:** I used Claude to draft Order.java, ConsoleView.java, OrderTest.java, and ConsoleViewTest.java from our Deliverable 2 method signatures, validation table, and test plan. I asked it to follow the spec exactly and to explain how the classes connect. I reviewed the code to make sure Order and ConsoleView match our specification and that no model class depends on the view.
 
[TODO: Other team members should add how they used GenAI, or state that they did not use it.]
 