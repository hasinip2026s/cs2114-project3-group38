import java.util.Scanner;

// -------------------------------------------------------------------------
/**
 * Connects OrderManager and ConsoleView and controls the application flow.
 *
 * @author Liangru Ji
 * @version 2026.11.11
 */
public class BrewQueueController
{

    // ~ Fields ................................................................

    private OrderManager orderManager;
    private ConsoleView view;
    private boolean running;

    private String coffee;
    private String latte;
    private String tea;

    private String small;
    private String medium;
    private String large;

    // ~ Constructors ..........................................................

    // ----------------------------------------------------------
    /**
     * Creates a controller with its model and view.
     *
     * @param orderManager
     *            The model that manages orders.
     * @param view
     *            The console view used for input and output.
     */
    public BrewQueueController(OrderManager orderManager, ConsoleView view)
    {
        this.orderManager = orderManager;
        this.view = view;
        running = false;
        coffee = "Coffee";
        latte = "Latte";
        tea = "Tea";
        small = "Small";
        medium = "Medium";
        large = "Large";
    }

    // ~ Public Methods ........................................................


    // ----------------------------------------------------------
    /**
     * Shows the menu and processes choices until the user exits.
     */
    public void run()
    {
        running = true;

        while (running)
        {
            view.showMainMenu();
            int choice = readNumber("Choose an option: ", 1, 6);

            if (!running)
            {
                break;
            }

            switch (choice)
            {
                case 1:
                    placeOrder();
                    break;
                case 2:
                    viewNextOrder();
                    break;
                case 3:
                    completeNextOrder();
                    break;
                case 4:
                    view.showPendingOrders(orderManager.getPendingOrders());
                    break;
                case 5:
                    cancelOrder();
                    break;
                case 6:
                    stopRunning();
                    break;
                default:
                    break;
            }
        }
    }


    // ----------------------------------------------------------
    /**
     * Creates the model, view, and controller and starts the program.
     *
     * @param args
     *            Command-line arguments are not used.
     */
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        OrderManager manager = new OrderManager();
        ConsoleView view = new ConsoleView(scanner);
        BrewQueueController controller = new BrewQueueController(manager, view);
        controller.run();
        scanner.close();
    }

    // ~ Private Methods .......................................................


    // ----------------------------------------------------------
    /**
     * Reads order information and asks the model to place the order.
     */
    private void placeOrder()
    {
        String[] drinkOptions = { coffee, latte, tea };
        String[] sizeOptions = { small, medium, large };

        String name = readName();
        if (!running)
        {
            return;
        }
        String drink = readChoice(drinkOptions);
        if (!running)
        {
            return;
        }
        String size = readChoice(sizeOptions);
        if (!running)
        {
            return;
        }
        try
        {
            Order order = orderManager.placeOrder(name, drink, size);
            view.showMessage("Order placed: " + order);
        }
        catch (IllegalArgumentException exception)
        {
            view.showMessage(
                "Could not place order: " + exception.getMessage());
        }
    }


    // ----------------------------------------------------------
    /**
     * Displays the next order without removing it.
     */
    private void viewNextOrder()
    {
        Order order = orderManager.viewNextOrder();

        if (order == null)
        {
            view.showMessage("No pending orders.");
        }
        else
        {
            view.showMessage("Next order: " + order);
        }
    }


    // ----------------------------------------------------------
    /**
     * Completes and displays the next order.
     */
    private void completeNextOrder()
    {
        Order order = orderManager.completeNextOrder();

        if (order == null)
        {
            view.showMessage("No pending orders.");
        }
        else
        {
            view.showMessage("Completed order: " + order);
        }
    }


    // ----------------------------------------------------------
    /**
     * Reads an order number and asks the model to cancel it.
     */
    private void cancelOrder()
    {
        int orderNumber = readNumber(
            "Enter the order number to cancel: ",
            1,
            Integer.MAX_VALUE);

        if (!running)
        {
            return;
        }
        if (orderManager.cancelOrder(orderNumber))
        {
            view.showMessage("Order #" + orderNumber + " canceled.");
        }
        else
        {
            view.showMessage("Order #" + orderNumber + " was not found.");
        }
    }


    // ----------------------------------------------------------
    /**
     * Reads a whole number within the given range.
     *
     * @param prompt
     *            The message shown before reading input.
     * @param min
     *            The smallest accepted number.
     * @param max
     *            The largest accepted number.
     * @return A valid number in the given range.
     */
    private int readNumber(String prompt, int min, int max)
    {
        while (running)
        {
            String input = view.readLine(prompt);
            if (input == null)
            {
                stopRunning();
                return min;
            }
            try
            {
                int number = Integer.parseInt(input);
                if (number >= min && number <= max)
                {
                    return number;
                }
            }
            catch (NumberFormatException exception)
            {
                // The message below also handles non-integer input.
            }

            view.showMessage(
                "Please enter a whole number from " + min + " to " + max + ".");
        }

        return min;
    }


    // ----------------------------------------------------------
    /**
     * Reads a customer name containing 1 to 30 characters.
     *
     * @return A valid customer name, or null if input ends.
     */
    private String readName()
    {
        while (running)
        {
            String name = view.readLine("Enter the customer name: ");
            if (name == null)
            {
                stopRunning();
                return null;
            }
            if (!name.isEmpty() && name.length() <= 30)
            {
                return name;
            }

            view.showMessage("Name must contain 1 to 30 characters.");
        }

        return null;
    }


    // ----------------------------------------------------------
    /**
     * Reads a listed choice by its number or name.
     *
     * @param options
     *            The choices available to the user.
     * @return The selected option, or null if input ends.
     */
    private String readChoice(String[] options)
    {
        while (running)
        {
            view.showChoices(options);
            String input = view.readLine("Enter a number or name: ");
            if (input == null)
            {
                stopRunning();
                return null;
            }
            try
            {
                int number = Integer.parseInt(input);
                if (number >= 1 && number <= options.length)
                {
                    return options[number - 1];
                }
            }
            catch (NumberFormatException exception)
            {
                for (String option : options)
                {
                    if (option.equalsIgnoreCase(input))
                    {
                        return option;
                    }
                }
            }

            view.showMessage("Please choose one of the listed options.");
        }

        return null;
    }


    // ----------------------------------------------------------
    /**
     * Displays a goodbye message and stops the menu loop.
     */
    private void stopRunning()
    {
        view.showMessage("Goodbye!");
        running = false;
    }

}
