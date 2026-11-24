import java.io.*;
import java.nio.file.*;
import java.util.*;
public class Main {
  static Queue<String> queue = new LinkedList<>();
  static Map<String, Integer> price = new HashMap<>(
      Map.of("Rice", 60, "Milk", 30, "Bread", 40));
  static Scanner sc = new Scanner(System.in);
  public static void main(String[] args) throws Exception {
    if (new File("queue.txt").exists())
      queue.addAll(Files.readAllLines(Paths.get("queue.txt")));
    while (true) {
      System.out.print("1.Add 2.Bill 3.View 4.Exit: ");
      String c = sc.nextLine().trim();
      if (c.equals("1")) addCustomer();
      else if (c.equals("2")) billNext();
      else if (c.equals("3")) viewQueue();
      else if (c.equals("4")) {
        Files.write(Paths.get("queue.txt"), queue);
        break;
      }
    }
  }
  static void addCustomer() {
    System.out.print("Name: ");
    String n = sc.nextLine().trim();
    queue.add(n);
    System.out.println(n + " added to billing queue. Position: "
        + queue.size());
  }
  static void billNext() throws Exception {
    if (queue.isEmpty()) {
      System.out.println("Queue is empty. Cannot bill.");
      return;
    }
    String cust = queue.poll();
    Stack<String> items = new Stack<>();
    while (true) {
      System.out.print("Item: ");
      String in = sc.nextLine().trim().toLowerCase();
      if (in.equals("done")) break;
      if (in.equals("undo")) {
        if (!items.isEmpty()) System.out.println("Last item "
            + items.pop().split(" ")[0] + " removed.");
        continue;
      }
      String[] t = in.split(" x");
      String n = t[0].isEmpty() ? "" : Character.toUpperCase(
          t[0].charAt(0)) + t[0].substring(1);
      int q = t.length > 1 ? Integer.parseInt(t[1]) : 1;
      if (!price.containsKey(n)) {
        System.out.println("Item " + n
            + " not found in price list.");
        continue;
      }
      items.push(n + " x" + q + " = " + price.get(n) * q);
    }
    String out = "Bill for " + cust + ": ";
    int total = 0;
    for (String s : items) {
      out += s + " | ";
      total += Integer.parseInt(
          s.substring(s.lastIndexOf(' ') + 1));
    }
    out += "Total " + total;
    System.out.println(out);
    try (FileWriter fw = new FileWriter("bills.txt", true)) {
      fw.write(out + "\n");
    }
    if (queue.isEmpty())
      System.out.println("Queue is now empty.");
  }
  static void viewQueue() {
    if (queue.isEmpty()) System.out.println("Queue is empty.");
    int i = 1;
    for (String s : queue) System.out.println((i++) + ". " + s);
  }
}
