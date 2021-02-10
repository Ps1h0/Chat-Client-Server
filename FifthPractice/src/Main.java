import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        new ArrayConcurrency().process();

        final int size = 10000000;
        float[] arr = new float[size];
        Arrays.fill(arr, 1);
        ezCalc(arr);
    }

    public static void ezCalc(float[] arr){
        long start = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        long end = System.currentTimeMillis() - start;
        System.out.println("Время в цикле: " + end);
    }
}
