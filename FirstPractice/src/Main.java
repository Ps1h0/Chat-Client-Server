public class Main {
    public static void main(String[] args) {
        Activity[] persons = {
                new Man("Никита", 40, 50),
                new Man("Вася", 50, 100),
                new Cat("Барсик", 5, 15),
                new Robot("R2D2", 5, 10)
        };

        for (Activity person: persons){
            person.take(new Wall(15), new TreadMill(100));
        }
    }
}
