package lesson_5;

public class MyThread extends Thread {

    float[] arr;
    int var;
    MyThread(float[] arr, int var) {
        this.arr = arr;
        this.var = var;
        start();
    }

    @Override
    public void run() {
            Main.NewArrayValue(arr,var);
    }

}
