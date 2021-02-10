import java.util.Arrays;

public class ArrayConcurrency {
    private static final int size = 10000000;
    private static final int h = size / 2;
    private float[] arr = new float[size];

    public void process(){
        long start = System.currentTimeMillis();

        Arrays.fill(arr, 1);

        float a1[] = new float[h];
        float a2[] = new float[h];

        System.arraycopy(arr, 0, a1, 0, h);
        System.arraycopy(arr, h, a2, 0, h);

        Thread t1 = new Thread(() ->{
            valcValues(a1);
        });

        Thread t2 = new Thread(() ->{
            valcValues(a2);
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        System.arraycopy(a1, 0, arr, 0, h);
        System.arraycopy(a2, 0, arr, h, h);

        long end = System.currentTimeMillis() - start;
        System.out.println("Время в потоках: " + end);
    }

    private void valcValues(float[] values){
        for(int i = 0; i < values.length; i++){
            values[i] = calc(values[i], i);
        }
    }

    private float calc(float val, int increment){
        return (float)(val * Math.sin(0.2f + increment / 5)
                * Math.cos(0.2f + increment / 5)
                * Math.cos(0.4f + increment / 2));
    }

}
