public class TreadMill implements Obstacle{

    private final int length;

    public TreadMill(int length){
        this.length = length;
    }

    @Override
    public boolean take(Activity activity) {
        if (activity.getJumpLimit() > length){
            activity.jump(length);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public String toString(){
        return "Дорожка: длина = " + length;
    }
}
