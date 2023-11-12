import java.util.LinkedList;
import java.util.Scanner;
import java.util.InputMismatchException;

public class EssenceEmporium {
    static LinkedList<User> users = new LinkedList<>();
    static User loggedInUser = null;
    static LinkedList<Item> items = new LinkedList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        users.add(new User("User1", "password1"));
        users.add(new User("User2", "password2"));
        users.add(new User("User3", "password3"));
      
        items.add(new Item("Dragon's Claw", 200.0, users.get(0)));
        items.add(new Item("Phoenix Feather", 500.0, users.get(1)));
        items.add(new Item("Tiger's Eye Gem", 300.0, users.get(2)));
        items.add(new Item("Sunburst Amulet", 550.0, users.get(0)));
        items.add(new Item("Shadow Cloak", 480.0, users.get(1)));
        items.add(new Item("Eternal Rose", 700.0, users.get(2)));
        items.add(new Item("Thunder Rune Stone", 250.0, users.get(0)));
        items.add(new Item("Astral Mirror", 600.0, users.get(1)));
        items.add(new Item("Starlight Elixir", 800.0, users.get(0)));
        items.add(new Item("Moonstone Ring", 400.0, users.get(1)));

        while (true) {
            System.out.println("Welcome to the Essence Emporium!");
            System.out.println("1. Log In");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            try {
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                if (choice == 1) {
                    logIn(scanner);
                } else if (choice == 2) {
                    register(scanner);
                } else if (choice == 3) {
                    System.out.println("Goodbye!");
                    System.exit(0);
                } else {
                    System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid numeric choice.");
                scanner.nextLine();
            }
        }
    }

    // user registration
    public static void register(Scanner scanner) {
        System.out.print("Enter a username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Enter a password: ");
        String password = scanner.nextLine().trim();

        if (!username.isEmpty() && !password.isEmpty()) {
            if (isUsernameTaken(username)) {
                System.out.println("Username already taken. Registration failed.");
            } else {
                User newUser = new User(username, password);
                users.add(newUser);
                System.out.println("Registration successful!");
                logIn(scanner);
            }
        } else {
            System.out.println("Username and password cannot be blank.");
        }
    }

    // user login
    public static void logIn(Scanner scanner) {
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        User user = findUser(username, password);

        if (user != null) {
            loggedInUser = user;
            System.out.println("Login successful!");
            displayAuctionMenu(scanner);
        } else {
            System.out.println("Login failed. Invalid credentials.");
        }
    }

    // search for a user by username and password
    public static User findUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    // check if a username is already taken
    public static boolean isUsernameTaken(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    // display the auction menu
    public static void displayAuctionMenu(Scanner scanner) {
        while (true) {
            System.out.println("\nWelcome, " + loggedInUser.getUsername() + "!");
            System.out.println("1. List Items");
            System.out.println("2. Bid on Items");
            System.out.println("3. Sort Items");
            System.out.println("4. Search Item");
            System.out.println("5. Delete Items");
            System.out.println("6. View All Items");
            System.out.println("7. Log Out");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            try {
                if (choice == 1) {
                    listItems(scanner);
                } else if (choice == 2) {
                    bidOnItems(scanner);
                } else if (choice == 3) {
                    mergeSortItems();
                    System.out.println("Items have been sorted.");
                } else if (choice == 4) {
                    searchItems(scanner);
                } else if (choice == 5) {
                    deleteItems(scanner); // Added the deleteItems method
                } else if (choice == 6) {
                    viewAllItems();
                } else if (choice == 7) {
                    System.out.println("Logging out.");
                    loggedInUser = null;
                    break;
                } else {
                    System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid numeric choice.");
                scanner.nextLine();
            }
        }
    }

    // mergeSortItems method:
    public static void mergeSortItems() {
        LinkedList<Item> sortedItems = mergeSort(items);
        items = sortedItems;
    }

    public static LinkedList<Item> mergeSort(LinkedList<Item> itemList) {
        int itemCount = itemList.size();
        if (itemCount <= 1) {
            return itemList;
        }

        int middle = itemCount / 2;
        LinkedList<Item> left = new LinkedList<>(itemList.subList(0, middle));
        LinkedList<Item> right = new LinkedList<>(itemList.subList(middle, itemCount));

        left = mergeSort(left);
        right = mergeSort(right);

        return merge(left, right);
    }

    public static LinkedList<Item> merge(LinkedList<Item> left, LinkedList<Item> right) {
        LinkedList<Item> merged = new LinkedList<>();
        while (!left.isEmpty() && !right.isEmpty()) {
            if (left.getFirst().getName().compareTo(right.getFirst().getName()) < 0) {
                merged.add(left.removeFirst());
            } else {
                merged.add(right.removeFirst());
            }
        }

        merged.addAll(left);
        merged.addAll(right);

        return merged;
    }

    // listing items for auction
    public static void listItems(Scanner scanner) {
        if (loggedInUser != null) {
            System.out.print("Enter the item name: ");
            String itemName = scanner.nextLine();
            System.out.print("Enter the starting bid price: ");
            double startingBid = scanner.nextDouble();
            scanner.nextLine();

            Item newItem = new Item(itemName, startingBid, loggedInUser);
            items.add(newItem);
            System.out.println("Item listed successfully!");
        } else {
            System.out.println("You must be logged in to list items.");
        }
    }

    public static void viewAllItems() {
        System.out.println("All Items for Bidding:");
        for (int i = 0; i < items.size(); i++) {
            System.out.println((i + 1) + ". " + items.get(i));
        }
    }

    // bidding on items
    public static void bidOnItems(Scanner scanner) {
        if (loggedInUser != null) {
            System.out.println("Available Items for Bidding:");
            for (int i = 0; i < items.size(); i++) {
                System.out.println((i + 1) + ". " + items.get(i));
            }

            System.out.print("Enter the item number you want to bid on (or 0 to cancel): ");
            int itemNumber = scanner.nextInt();
            scanner.nextLine();

            if (itemNumber >= 1 && itemNumber <= items.size()) {
                Item itemToBidOn = items.get(itemNumber - 1);

                // Check if the logged-in user is the seller
                if (!loggedInUser.equals(itemToBidOn.getSeller())) {
                    System.out.println("Current Bid: $" + itemToBidOn.getCurrentBid());
                    System.out.print("Enter your bid: $");
                    double bidAmount = scanner.nextDouble();

                    if (bidAmount > itemToBidOn.getStartingBid()) {
                        itemToBidOn.setCurrentBid(bidAmount);
                        itemToBidOn.setHighestBidder(loggedInUser);
                        System.out.println("Bid successful!");
                    } else {
                        System.out.println("Your bid must be higher than the current bid.");
                    }
                } else {
                    System.out.println("You cannot bid on your own item.");
                }
            } else if (itemNumber == 0) {
                System.out.println("Bidding canceled.");
            } else {
                System.out.println("Invalid item number. Please try again.");
            }
        } else {
            System.out.println("You must be logged in to bid on items.");
        }
    }

    public static void searchItems(Scanner scanner) {
        System.out.print("Enter the item name to search for: ");
        String itemName = scanner.nextLine();
        boolean found = false;

        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                System.out.println("Item found: " + item);
                found = true;
            }
        }

        if (!found) {
            System.out.println("Item not found.");
        }
    }

    // delete items
    public static void deleteItems(Scanner scanner) {
        if (loggedInUser != null) {
            System.out.println("Your Listed Items:");
            int i = 1;
            LinkedList<Item> itemsToRemove = new LinkedList<>();
            for (Item item : items) {
                if (item.getSeller() != null && item.getSeller().equals(loggedInUser)) {
                    System.out.println(i + ". " + item);
                    itemsToRemove.add(item);
                    i++;
                }
            }
            System.out.print("Enter the number of the item to delete (or 0 to cancel): ");
            int itemNumber = scanner.nextInt();
            scanner.nextLine();

            if (itemNumber >= 1 && itemNumber <= i - 1) {
                Item itemToDelete = itemsToRemove.get(itemNumber - 1);
                items.remove(itemToDelete); 

                for (int j = 0; j < items.size(); j++) {
                    if (items.get(j).equals(itemToDelete)) {
                        items.remove(j); 
                        break;
                    }
                }

                System.out.println("Item deleted successfully!");
            } else if (itemNumber == 0) {
                System.out.println("Deletion canceled.");
            } else {
                System.out.println("Invalid item number. Please try again.");
            }
        } else {
            System.out.println("You must be logged in to delete items.");
        }
    }

    // item class
    static class Item {
        private String name;
        private double startingBid;
        private double currentBid;
        private User seller; 
        private String highestBidder;

        public Item(String name, double startingBid, User seller) {
            this.name = name;
            this.startingBid = startingBid;
            this.currentBid = 0;
            this.seller = seller;
        }

        public String getName() {
            return name;
        }

        public double getStartingBid() {
            return startingBid;
        }

        public double getCurrentBid() {
            return currentBid;
        }

        public void setCurrentBid(double currentBid) {
            this.currentBid = currentBid;
        }

        public String getHighestBidder() {
            return highestBidder;
        }

        public User getSeller() {
            return seller;
        }

        public void setHighestBidder(User highestBidder) {
            this.highestBidder = highestBidder.getUsername();
        }

        @Override
        public String toString() {
            String bidder = (highestBidder != null) ? highestBidder : "No bidder yet";
            return name + " - Starting Bid: $" + startingBid + " - Current Bid: $" + currentBid +
                    " - Listed by: " + (getSeller() != null ? getSeller().getUsername() : "Unknown") +
                    " - Highest Bidder: " + bidder;
        }
    }

    // user class
    static class User {
        private String username;
        private String password;

        public User(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }
    }
}
