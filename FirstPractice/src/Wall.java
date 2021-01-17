public class Wall implements Obstacle{

    private final int height;

    public Wall(int height){
        this.height = height;
    }

    @Override
    public boolean take(Activity man) {
        if (man.getJumpLimit() > height){
            man.jump(height);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public String toString(){
        return "Стена: высота = " + height;
    }
}
