import java.util.Scanner;

/**
 * Coordinates the BrewQueue model and console view. Based on the team's initial
 * controller design (Liangru Ji, 2026.11.11). D4 adds Hot/Iced selection and
 * ETA through the model.
 */
public class BrewQueueController
{
    private final OrderManager orderManager;
    private final ConsoleView view;
    private boolean running;

    private static final String[] DRINK_OPTIONS = { "Coffee", "Latte", "Tea" };
    private static final String[] TEMPERATURE_OPTIONS = { "Hot", "Iced" };
    private static final String[] SIZE_OPTIONS = { "Small", "Medium", "Large" };

    public BrewQueueController(OrderManager orderManager, ConsoleView view)
    {
        if (orderManager == null || view == null)
        {
            throw new IllegalArgumentException(
                "Model and view cannot be null.");
        }
        this.orderManager = orderManager;
        this.view = view;
        this.running = false;
    }


    public void run()
    {
        running = true;
        while (running)
        {
            view.showMainMenu();
            int choice = readNumber("Choose an option: ", 1, 7);
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
                    checkEta();
                    break;
                case 7:
                    stopRunning();
                    break;
                default:
                    break;
            }
        }
    }


    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        OrderManager manager = new OrderManager();
        ConsoleView view = new ConsoleView(scanner);
        BrewQueueController controller = new BrewQueueController(manager, view);
        controller.run();
        scanner.close();
    }


    private void placeOrder()
    {
        String name = readName();
        if (!running)
        {
            return;
        }

        view.showMessage("Choose a drink:");
        String drink = readChoice(DRINK_OPTIONS);
        if (!running)
        {
            return;
        }

        view.showMessage("Choose a temperature:");
        String temperature = readChoice(TEMPERATURE_OPTIONS);
        if (!running)
        {
            return;
        }

        view.showMessage("Choose a size:");
        String size = readChoice(SIZE_OPTIONS);
        if (!running)
        {
            return;
        }

        try
        {
            // The model accepts "Hot Latte", "Iced Latte", etc.
            Order order =
                orderManager.placeOrder(name, temperature + " " + drink, size);
            view.showMessage("Order placed: " + order);
            view.showEstimatedWaitTime(
                order.getOrderNumber(),
                orderManager.getEstimatedWaitMinutes(order.getOrderNumber()),
                orderManager.getEstimatedReadyMinutes(order.getOrderNumber()));
        }
        catch (IllegalArgumentException exception)
        {
            view.showMessage(
                "Could not place order: " + exception.getMessage());
        }
    }


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


    private void checkEta()
    {
        int orderNumber =
            readNumber("Enter order number: ", 1, Integer.MAX_VALUE);
        if (!running)
        {
            return;
        }
        double wait = orderManager.getEstimatedWaitMinutes(orderNumber);
        if (wait < 0)
        {
            view.showMessage("Order not found.");
            return;
        }
        double ready = orderManager.getEstimatedReadyMinutes(orderNumber);
        view.showEstimatedWaitTime(orderNumber, wait, ready);
    }


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
                // Retry using the same error message for all invalid numbers.
            }
            view.showMessage(
                "Please enter a whole number from " + min + " to " + max + ".");
        }
        return min;
    }


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


    private void stopRunning()
    {
        view.showMessage("Goodbye!");
        running = false;
    }
}
