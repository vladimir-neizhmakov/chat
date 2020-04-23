package lesson_5;

public class Main {

    static final int size = 10000000;
    static final int h = size / 2;
    static float[] arr = new float[size];

    private static void FillTheArray(float [] arr){
        for (int i = 0; i < size; i++) {
            arr[i]=1f;
        }
    }

    public static void NewArrayValue(float [] arr, int var){
        for (int i = 0+var; i < arr.length+var; i++) {
            //Исключил ошибку вычислений при разделении массива на 2
            arr[(i-var)] = (float)(arr[(i-var)] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
    }

    public static void main(String[] args) {
        System.out.println("Время работы метода 1 составило, мс.: " + Method_1());
        System.out.println("Время работы метода 2 составило, мс.: " + Method_2());
    }

    private static long Method_1(){
        FillTheArray(arr);
        long a = System.currentTimeMillis();
        NewArrayValue(arr,0);
        return (System.currentTimeMillis() - a);

    }

    private static long Method_2(){
        FillTheArray(arr);
        float[] a1 = new float[h];
        float[] a2 = new float[h];
        long a = System.currentTimeMillis();
        System.arraycopy(arr, 0, a1, 0, h);
        System.arraycopy(arr, h, a2, 0, h);
        MyThread t0 = new MyThread(a1,0);
        MyThread t1 = new MyThread(a2,h);
        try {
            t0.join();
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.arraycopy(a1, 0, arr, 0, h);
        System.arraycopy(a2, 0, arr, h, h);
        return (System.currentTimeMillis() - a);
    }
}
