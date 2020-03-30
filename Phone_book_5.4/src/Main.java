import java.util.*;

public class Main {
  private static final String PHONE_NUMBER = "\\d+"; // "^\\+?\\d+((\\s|\\-)?\\d+)+$";
  private static final String NAME = "^[a-zA-Z]{2,}((\\-|\\s)[a-zA-Z]{2,})*$";
  // Пример ввода : 89346790374
  // Bill
  private static TreeMap<String, String> phoneBook = new TreeMap<>();
  public static Scanner scanner = new Scanner(System.in);

  static void addPhone(String name) {
    String phone;
    do {
      //            Scanner scanner = new Scanner(System.in);
      phone = scanner.nextLine();

    } while (!phone.matches(PHONE_NUMBER));

    phoneBook.put(name, phone);
  }

  public static void addName(String phone) {
    String name;
    do {
      // Scanner scanner = new Scanner(System.in);
      name = scanner.nextLine();
    } while (!name.matches(NAME));

    phoneBook.put(name, phone);
  }
  //    public static void PrintPhoneBook(HashMap<String, String> phoneBook) {
  //        System.out.println("Все контакты " + Main.phoneBook);
  //        System.out.println();
  public static void printPhoneBook(SortedMap<String, String> phoneBook) {
    System.out.println("Телефонный справочник: ");
    for (SortedMap.Entry<String, String> key : Main.phoneBook.entrySet()) {
      System.out.println(key.getValue() + ": " + key.getKey());
    }
  }

  public static void printByPhone(String phone) {
    if (phoneBook.containsValue(phone)) {
      //  phoneBook.forEach((key, value) -> System.out.println(key + ":" + value));
      Map.Entry<String, String> entry = phoneBook.entrySet().iterator().next();
      String name = entry.getKey();
      System.out.println(name);

    } else {
      System.out.println("Абонент с таким номером не найден");
      //        String result = phoneBook.get(phone);
      //        if (result == null) return "абонент с таким номером не найден";
      //        return result;
    }
  }

  public static void printByName(String name) {
    if (phoneBook.containsKey(name)) System.out.println(phoneBook.get(name));
    else {
      System.out.println("Такого контакта не существует");
    }
    //        List<String> result = new ArrayList<String>();
    //        for (Map.Entry entry : phoneBook.entrySet()) {
    //            if (name.equalsIgnoreCase((String) entry.getValue())) {
    //                result.add((String) entry.getKey());
    //            }
    //        }
    //        if (result.size() == 0) result.add("абонент с таким именем не найден");
    //        return result.toArray(new String[0]);
  }

  public static void main(String[] args) {
    //        Scanner scanner = new Scanner(System.in);
    for (; ; ) {
      System.out.println("Введите Номер Телефона и Имя \n");

      String userInput = scanner.nextLine();

      // String userInput = scanner.nextLine();

      if (userInput.equals("LIST")) {
        printPhoneBook(phoneBook);

      } else if (userInput.matches(NAME)) {
        if (phoneBook.keySet().contains(userInput)) {
          printByName(userInput);

        } else {
          addPhone(userInput);
        }
      } else if (userInput.matches(PHONE_NUMBER)) {
        if (phoneBook.values().contains(userInput)) {
          printByPhone(userInput);
        } else {
          addName(userInput);
        }
      } else {
        System.out.println("Неверный ввод, попробуйте ещё раз!");
      }
    }
  }
}

//    private static void printMap (Map<String, String> map)
//    {
//        for (String key : map.keySet()) {
//            System.out.println(key + "=>" + map.get(key));
//        }
//    }
