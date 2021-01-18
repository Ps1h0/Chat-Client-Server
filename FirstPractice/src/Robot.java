public class Robot implements Activity {

    private final String name;
    private final int maxJump;
    private final int maxRun;

    public Robot(String name, int maxJump, int maxRun){
        this.name = name;
        this.maxJump = maxJump;
        this.maxRun = maxRun;
    }

    @Override
    public void jump(int height) {
        System.out.println(name + " прыгнул на " + height);
    }

    @Override
    public int getJumpLimit() {
        return maxJump;
    }

    @Override
    public void run(int distance) {
        System.out.println(name + " пробежал " + distance);
    }

    @Override
    public int getRunLimit() {
        return maxRun;
    }

    @Override
    public String getName() {
        return name;
    }
}
