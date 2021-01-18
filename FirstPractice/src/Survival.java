public interface Survival {

    String getName();

    default void take(Obstacle... obstacles){
        for (Obstacle obstacle : obstacles){
            if (!obstacle.take((Activity) this)){
                System.out.println("Препятствие " + obstacle + " не может быть преодолено участником " + getName());
                return;
            }
        }
        System.out.println("Все препятствия были преодолены участником " + getName());
    }
}
