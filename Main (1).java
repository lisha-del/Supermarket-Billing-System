import java.util.*;

public class Main {

  static String[] itemNames = {"Rice", "Milk", "Bread"};
  static String[] units = {"kg", "litre", "packet"};
  static int[] prices = {60, 30, 40};

  static Queue<String> queue = new LinkedList<>();

  static HashMap<String, Stack<Item>> customers =
      new HashMap<>();

  static class Item {
    String name;
    int quantity;
    int price;
    String unit;

    Item(String name, int quantity, int price, String unit) {
      this.name = name;
      this.quantity = quantity;
      this.price = price;
      this.unit = unit;
    }
  }

  public static void main(String[] args) {

    addCustomer("Riya");
    addCustomer("Aman");
    addCustomer("Rahul");

    addItem("Riya", 0, 1);
    addItem("Riya", 2, 2);

    addItem("Aman", 1, 2);
    addItem("Aman", 2, 1);

    addItem("Rahul", 0, 2);
    addItem("Rahul", 1, 1);

    Scanner sc = new Scanner(System.in);

    System.out.print("Enter Customer Name: ");
    String name = sc.nextLine().trim().toLowerCase();

    if (!customers.containsKey(name)) {
      System.out.println("Customer not found.");
      return;
    }

    Stack<Item> items = customers.get(name);

    int total = 0;

    System.out.println("\nBill for "
        + name.substring(0, 1).toUpperCase()
        + name.substring(1));

    System.out.println("-----------------------------");

    for (int i = 0; i < items.size(); i++) {

      Item item = items.get(i);

      int amount = item.price * item.quantity;

      System.out.println(item.name + " x "
          + item.quantity + " " + item.unit
          + " = " + amount);

      total += amount;
    }

    System.out.println("-----------------------------");
    System.out.println("Total Bill = " + total);
  }

  static void addCustomer(String name) {

    queue.add(name.toLowerCase());
    customers.put(name.toLowerCase(), new Stack<Item>());
  }

  static void addItem(String customer, int itemIndex,
            int quantity) {

    Stack<Item> items = customers.get(customer.toLowerCase());

    Item item = new Item(
        itemNames[itemIndex],
        quantity,
        prices[itemIndex],
        units[itemIndex]
    );

    items.push(item);
  }
}
