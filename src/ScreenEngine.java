import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.io.File;
import java.io.IOException;

/**
 * Kyle Rosenthal
 * 1/15/14
 */
public class ScreenEngine extends JFrame implements Runnable {
    Graphics g2;
    SquareSpace[][] arr;

    final int dX = 20;
    final int dY = 40;
    final int SIZE = 400; // keep multiple of 20
    ScreenEngine()
    {

        arr = new SquareSpace[SIZE/20][SIZE/20];
        for (int i = 0; i < SIZE/20; i++)
        {
            for (int j = 0; j < SIZE/20; j ++)
            {
                arr[i][j] = new SquareSpace(0);
            }
        }


        setBackground(Color.BLACK);
        setSize(SIZE+ 2*dX, SIZE+ 2*dY);
        setTitle("MasterSweep");
        setVisible(true);
    }


    public void paint(Graphics g){
        g.setColor(Color.WHITE);
        g.fillRect(dX, dY, SIZE, SIZE);


        for (int i = 0; i < SIZE/20; i++)
        {
            for (int j = 0; j < SIZE/20; j ++)
            {
                g.drawImage(arr[i][j].toIcon(),dX+ i*20,dY + j*20, null);
            }
        }

    }

    @Override
    public void run() {
        g2 = getGraphics();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        System.out.print("Test");


        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
            }

            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                super.mouseWheelMoved(e);
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
            }
        });
        /**
        while(true)
        {
            repaint();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } //*/
    }
}
