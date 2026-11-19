# BrewQueue — CS 2114 Project 3

**Team 38:** Ash Satasia, Seongjin Nam, Hasini Pottipati, Liangru Ji  
**Current milestone:** Deliverable 4 — Second Prototype + Tests

## Project Overview

BrewQueue is a console-based drink ordering application. Customers place orders, and a barista can view, complete, or cancel orders. Pending orders are stored in a generic singly linked FIFO (first-in, first-out) queue so that the earliest order is completed first unless it has been canceled.

Our project follows a Model-View-Controller design:

- **Model:** `Order` stores order details, prices, and preparation-time estimates; `LinkedQueue<T>` stores pending orders; and `OrderManager` manages the queue, order numbers, cancellation, and ETA calculations.
- **View:** `ConsoleView` reads input and prints menus, orders, and messages. It does not store a second copy of the order queue.
- **Controller:** `BrewQueueController` interprets user choices and connects the view to the model.

## Current Features

1. Place an order with a customer name, Coffee/Latte/Tea, Hot/Iced, and Small/Medium/Large.
2. View the next pending order without removing it.
3. Complete the next order in FIFO order.
4. Display all pending orders.
5. Cancel an order by its number while preserving the order of the remaining customers.
6. Check the estimated wait until preparation starts and the estimated time until an order is ready.
7. Exit the application.

The program validates menu choices, names, drinks, sizes, and order numbers. Invalid console input is handled with a retry or an appropriate message instead of terminating the program.

### Prices and Preparation-Time Estimates

| Drink | Small price | Small prep time |
| --- | ---: | ---: |
| Coffee | $3.75 | 2.5 min |
| Latte | $4.00 | 3.0 min |
| Tea | $3.50 | 2.0 min |

Medium adds $1.00 and 1 minute; Large adds $1.50 and 2 minutes. Hot and Iced currently use the same price and estimated preparation time for a given drink and size.

ETA assumes one order is prepared at a time in queue order. `getEstimatedWaitMinutes(orderNumber)` sums the preparation times of orders ahead of that order. `getEstimatedReadyMinutes(orderNumber)` adds the selected order's preparation time. For example, if the first order needs 5 minutes and the second needs 3 minutes, the second order has a 5-minute wait to start and is estimated to be ready in 8 minutes. Completing or canceling an order changes the pending queue, so the next ETA query uses the updated order list. These are estimates, not a live countdown.

## How to Compile and Run

### Run the application in Eclipse

1. Open the BrewQueue project in Eclipse as a Java project. The Java files are in the **default package**.
2. Confirm that **JRE System Library** is present under the project.
3. Open `BrewQueueController.java`.
4. Right-click the file and choose **Run As → Java Application**.
5. Use the **Console** tab to enter options 1–7. Option **7** exits the program.

### Compile and run from a terminal (optional)

From the directory containing the source files:

```bash
javac LinkedQueue.java Order.java OrderManager.java ConsoleView.java BrewQueueController.java
java BrewQueueController
```

### Run JUnit 5 tests in Eclipse

1. Confirm **JUnit 5** is included in the project's Java Build Path.
2. Right-click each test file and select **Run As → JUnit Test**, or run all five test classes together.
3. Review the results in Eclipse's JUnit view. A green bar with zero failures and errors indicates that the executed tests passed.

| Test file | Test methods | Main coverage |
| --- | ---: | --- |
| `LinkedQueueTest.java` | 16 | Enqueue/dequeue, peek, empty queue, null input, FIFO ordering, interleaved operations, `toList()` |
| `OrderTest.java` | 22 | Construction, getters, validation, prices, preparation times, Hot/Iced choices |
| `OrderManagerTest.java` | 22 | Order creation, unique numbers, cancellation, FIFO behavior, ETA, invalid order numbers |
| `ConsoleViewTest.java` | 8 | Menu, input/output, pending orders, ETA display |
| `BrewQueueControllerTest.java` | 20 | Scripted console choices, ordering, temperature, ETA, invalid input, exit |
| **Total** | **88** | |

All five JUnit test classes were run successfully in Eclipse for Deliverable 4, with no reported test failures or errors. The model tests directly construct and exercise the model classes without requiring `ConsoleView` or `BrewQueueController`.

## Deliverable 3 Design Background

The first prototype established the basic order workflow and the separation between the queue, the order model, the console view, and the controller. `LinkedQueue<T>` uses private linked nodes with front and rear references. `OrderManager` uses the queue's public methods rather than accessing its internal nodes. `Order` stores immutable order details and calculates prices and preparation times.

Some choices changed from our initial specification: the implemented drink menu uses Coffee, Latte, and Tea; the estimated preparation time is a `double` because some drinks take half-minute increments; and input validation is divided between the model and the controller. The model rejects invalid order data, while the controller retries invalid console choices. The D3 plan identified **Estimated Wait Times** as the D4 change feature and identified saving orders to a file as a possible feature that we would not prioritize.

## Reflection

### How did our design absorb the D4 change?

Our main D4 change was **Estimated Wait Times**, which we planned in D3. We added `getEstimatedWaitMinutes(int orderNumber)` and `getEstimatedReadyMinutes(int orderNumber)` to `OrderManager`. These methods read the current pending orders in FIFO order and sum the preparation times supplied by `Order.getPrepMinutes()`. Because ETA is calculated on demand, we do not need to maintain a second queue or separately update saved ETA values after a cancellation or completed order.

We also added a Hot/Iced selection after drink choice, which was an additional improvement beyond our original ETA plan. The updated controller passes the chosen temperature with the drink to the model, and the order displays the temperature along with its size, price, and preparation time.

### D3 prediction vs. actual changes

| Class | D3 prediction | What actually happened in D4 |
| --- | --- | --- |
| `LinkedQueue.java` | No structural changes; reuse `toList()` | No new queue operation or structural change was needed for ETA. |
| `Order.java` | Should stay unchanged for ETA; use `getPrepMinutes()` | Existing preparation-time logic supported ETA, but we extended the accepted drink names for Hot/Iced. |
| `OrderManager.java` | Add `getEstimatedWaitMinutes()` | Added that method and `getEstimatedReadyMinutes()` to calculate both starting wait and total time until ready. |
| `ConsoleView.java` | Add ETA display | Added ETA output and updated the menu to show Check ETA and Exit. |
| `BrewQueueController.java` | Add ETA menu handling | Added ETA handling, changed Exit from option 6 to option 7, and added the Hot/Iced prompt. |

The ETA change mostly matched our prediction. The biggest difference was that Hot/Iced required modifying `Order`, even though we predicted that class would not need to change for ETA. Changing the number of menu options also required updating input validation and scripted controller tests. We revised and expanded the five test files to reflect the final behavior.

### Did the change cross the model/view line?

The new functionality travels through the controller, but responsibilities remain separated. `OrderManager` owns the queue and performs ETA calculations. `BrewQueueController` receives user choices, calls the model, and passes calculated results to `ConsoleView`. `ConsoleView` prints those results and does not calculate ETA or keep its own queue state. This means the model can be tested independently of the user interface.

### What changed from our D3 plan?

We kept the original queue behavior and the `placeOrder(customerName, drinkType, size)` method signature. We added the temperature step, the Check ETA menu option, and a second ETA method to distinguish the wait before preparation starts from the total time until an order is ready. We also updated our JUnit tests because older controller tests assumed option 6 meant Exit and did not provide Hot/Iced input. The new tests include ETA calculations, missing orders, invalid inputs, and behavior after completing or canceling orders.

### What does not work yet?

All core MVP actions and the planned ETA feature are implemented, and the current JUnit suite passes in Eclipse. The program does **not** maintain a real-time countdown, subtract elapsed time from an order currently being prepared, support multiple baristas preparing orders in parallel, or save orders between runs. We have not included those features in this prototype. We still want to review the final console messages and rehearse the demonstration on the merged project before the final build.

### What would make the next change cheaper?

A useful improvement would be to store temperature as a separate `Order` field or enum instead of putting `Hot` or `Iced` into the drink name. We could also move menu choices, prices, and prep times into a dedicated menu/configuration class. These changes would make it easier to add drinks or pricing rules without editing several arrays or relying on specific display strings. Keeping all ETA calculations in `OrderManager` already makes future presentation changes easier.

## Next Steps

### Remaining work for the final build (in priority order)

1. Recheck the merged project in Eclipse and confirm that all five JUnit test classes still pass after any teammate updates.
2. Manually test every console menu option, including invalid names, drink/temperature/size choices, non-numeric menu input, missing order numbers, and end-of-input behavior.
3. Verify that ETA changes as expected after completing or canceling pending orders and that FIFO order remains correct.
4. Review console wording, code readability, documentation, and the final file-by-file attribution record.
5. Practice the three-minute demonstration and make sure every group member can explain their contribution and the MVC/queue design.

### Stretch goals: committed vs. dropped

- **Committed:** Estimated Wait Times, Order Cancellation, and Hot/Iced selection. These features are implemented in the current prototype and will be kept for the final build.
- **Not committing to:** Saving pending orders to a file, a live countdown timer, support for multiple baristas, or a graphical interface. Our priority is to make the existing features reliable and well tested.

### Three-minute demo plan

| Time | Demo action |
| --- | --- |
| **0:00–0:20** | Introduce BrewQueue, the linked FIFO queue, and the MVC structure. |
| **0:20–1:05** | Place two orders with different drinks, sizes, and Hot/Iced options; show their order details. |
| **1:05–1:35** | Display pending orders and use Check ETA for the second order. Explain wait-to-start versus ready-in time. |
| **1:35–2:05** | Complete the first order and show the second order's updated ETA. |
| **2:05–2:35** | Cancel a pending order, then check its number again to show the not-found response. |
| **2:35–3:00** | Demonstrate an invalid input retry, exit, and show the passing Eclipse JUnit results. |

### Who does what?

| Team member | Remaining responsibility |
| --- | --- |
| **Seongjin Nam** | Verify `OrderManager` ETA and cancellation behavior, review model tests, and coordinate the D4 integration. |
| **Ash Satasia** | Review `LinkedQueue` behavior and interleaved enqueue/dequeue tests to confirm FIFO correctness. |
| **Hasini Pottipati** | Review `Order` validation, Hot/Iced behavior, and `ConsoleView` output and tests. |
| **Liangru Ji** | Review controller input flow and scripted controller tests; coordinate the three-minute console demo. |
| **All members** | Review the final merged project, verify attribution, and prepare for the TA grading session. |

## Attribution

The table distinguishes **original contributions** from **Deliverable 4 revisions and integration**. A GitHub commit that updates a file does not mean its committer wrote the entire original file. In D4, Seongjin Nam integrated a ChatGPT-assisted revision of the project files and ran the JUnit suite in Eclipse. Earlier team members' original contributions remain credited below.

| File | Team member(s) and contribution |
| --- | --- |
| `LinkedQueue.java` | **Ash Satasia:** Original singly linked generic FIFO queue implementation. **Seongjin Nam:** Included the queue in the D4 integrated build and verified its compatibility with the new features. |
| `LinkedQueueTest.java` | **Ash Satasia:** Original queue test suite and FIFO/edge-case testing. **Seongjin Nam:** Integrated and reviewed D4 test revisions, including the interleaved operation coverage, with ChatGPT assistance. |
| `Order.java` | **Hasini Pottipati:** Original order class, validation, price and preparation-time logic. **Seongjin Nam:** Integrated ChatGPT-assisted Hot/Iced support and related validation changes for D4. |
| `OrderTest.java` | **Hasini Pottipati:** Original order unit tests. **Seongjin Nam:** Integrated ChatGPT-assisted updates for Hot/Iced and invalid-input cases and ran the revised tests. |
| `OrderManager.java` | **Seongjin Nam:** Order management, queue integration, validation, cancellation, and D4 ETA calculations; used ChatGPT assistance for implementation and revisions. |
| `OrderManagerTest.java` | **Seongjin Nam:** Model tests for placement, FIFO behavior, validation, cancellation, and ETA; used ChatGPT to develop and revise test cases and ran them in Eclipse. |
| `ConsoleView.java` | **Hasini Pottipati:** Original console view and input/output methods. **Seongjin Nam:** Integrated ChatGPT-assisted ETA output and updated menu text for D4. |
| `ConsoleViewTest.java` | **Hasini Pottipati:** Original view tests. **Seongjin Nam:** Integrated ChatGPT-assisted updates for the new menu and ETA display and ran the tests. |
| `BrewQueueController.java` | **Liangru Ji:** Original controller structure and application flow. **Seongjin Nam:** Integrated ChatGPT-assisted Hot/Iced prompts, ETA handling, and updated menu flow for D4. |
| `BrewQueueControllerTest.java` | **Liangru Ji:** Original scripted controller tests. **Seongjin Nam:** Integrated ChatGPT-assisted updates for temperature selection, ETA actions, and menu changes and ran the tests. |

### GenAI Usage Note

**Seongjin Nam:** I used ChatGPT while developing and revising `OrderManager`, the ETA feature, Hot/Iced integration, and JUnit tests. I also used it to troubleshoot Eclipse compilation and Git issues and to help draft and organize this README. ChatGPT suggested code and test changes across multiple team files, and I integrated those revisions into the D4 project. I then ran the JUnit tests in Eclipse and tested the console application. I understand that I am responsible for explaining the changes and the code I submitted.

**Liangru Ji:** As described in our earlier project documentation, GenAI was used to review code, help organize an initial draft, and suggest the structure for `BrewQueueController` and its tests. The suggested work was reviewed and revised to fit the team's design.

**Hasini Pottipati:** As described in our earlier project documentation, Claude was used to help draft `Order`, `ConsoleView`, and their tests using the planned method signatures and test cases. The resulting code was reviewed against the specification and MVC responsibilities.

**Team note:** The original README did not include an individual GenAI-use statement for Ash Satasia. The team should confirm Ash's actual GenAI use (or non-use) before the final submission rather than making an unsupported claim. All team members should review the attribution table for accuracy before submitting.
