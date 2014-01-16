/**
 * Kyle Rosenthal
 * 1/15/14
 */
public class StartMain {
    public static void main(String[] args) {
        ScreenEngine s = new ScreenEngine();
        Thread t = new Thread(s);
        t.start();
    }
}
