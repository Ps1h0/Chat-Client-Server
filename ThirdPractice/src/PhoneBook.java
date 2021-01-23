import java.util.*;

public class PhoneBook {

    private final Map<String, Set<String>> pairs;

    public PhoneBook(){
        this.pairs = new HashMap<>();
    }

    public void add(String name, String number){
        if(pairs.containsKey(name)){
            pairs.get(name).add(number);
            return;
        }
        Set<String> numbers = new HashSet<>();
        numbers.add(number);
        pairs.put(name, numbers);
    }

    public Set<String> get(String name){
       return pairs.getOrDefault(name, Collections.emptySet());
    }

    @Override
    public String toString() {
        return "PhoneBook{" +
                "pairs=" + pairs +
                '}';
    }
}
