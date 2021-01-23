import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

public class Main {

    public static void main(String[] args) {

        String src = "abc abc abcd a ab aa a ab gg gl hf ez breezy ez ";
        String[] data = src.split(" +");
        TreeMap<String, Integer> map = new TreeMap<>();
        for (String word : data) {
            map.put(word, map.getOrDefault(word, 0) + 1);
        }
        System.out.println(map);



        PhoneBook phoneBook = new PhoneBook();
        phoneBook.add("Nikita", "123");
        phoneBook.add("Maksim", "345");
        phoneBook.add("Nikita", "35634567");

        System.out.println(phoneBook);
        System.out.println(phoneBook.get("Nikita"));
        System.out.println(phoneBook.get("Nikitaf"));

    }
}
