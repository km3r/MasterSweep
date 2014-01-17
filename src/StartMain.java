/**
 * Kyle Rosenthal
 * 1/15/14
 */
public class StartMain {
    public static ScreenEngine s;
    public static void main(String[] args) {
        s = new ScreenEngine();
        Thread t = new Thread(s);
        t.start();
    }
}
