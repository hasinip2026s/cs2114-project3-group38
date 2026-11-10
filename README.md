# BrewQueue – Coffee Shop Order Manager

CS 2114 · Project 3 · Deliverable 3: First Prototype

Team: Ash Satasia, Seongjin Nam, Hasini Pottipati, Liangru Ji

BrewQueue is a console app where customers place drink orders and baristas complete them in the order they were received. Orders are stored in a singly linked queue (FIFO).

## How to Run

### Run the model (no view)

[TODO: name the driver class or test file, e.g. `ModelDriver.java` or `LinkedQueueTest.java`]

1. Open the project in Eclipse.
2. Run [TODO: driver/test file name].
3. [TODO: describe what it does, e.g. "It creates an `OrderManager`, places three orders, completes one, and prints the pending orders."]

### Run the thin view

1. Run [TODO: class with the `main` method, e.g. `BrewQueueController.java`].
2. A numbered menu appears in the console. Enter a number to choose an option.

## Reflection

### What changed from the specification?

[TODO: For each change, say what the spec said, what you built instead, and why.]

- **Classes:** [TODO]
- **Method signatures:** [TODO]
- **Where validation lives:** [TODO]
- **Test cases:** [TODO]

### Did the model really stand alone?

[TODO: Can the model run without `ConsoleView`? Did anything have to move across the model/view line? Does anything in the model still know about the view?]

### What happened to our unknowns?

In our scope, we listed three things we didn't know how to do yet:

- **Cancelling an order from the middle of the queue:** [TODO: figured out / still open]
- **Calculating estimated wait times:** [TODO: figured out / still open]
- **Saving pending orders to a file:** [TODO: figured out / still open]

### What doesn't work yet?

[TODO: MVP features, bad-input cases, or tests that are missing or failing.]

## Next Steps

### What is left to finish the MVP

In the order we will do them:

1. [TODO]
2. [TODO]
3. [TODO]

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
| `LinkedQueue.java` | [TODO] | [TODO] |
| `OrderManager.java` | [TODO] | [TODO] |
| `ConsoleView.java` | [TODO] | [TODO] |
| `BrewQueueController.java` | [TODO] | [TODO] |
| [TODO: test file] | [TODO] | [TODO] |

### GenAI Note

[TODO: How each person used GenAI (what they asked and what they changed), or state that it wasn't used.]
