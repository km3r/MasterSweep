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

                if (Math.random() < .2) arr[i][j] = new SquareSpace(-1);
                else {
                    arr[i][j] = new SquareSpace(0);

                }
            }
        }
        for (int i = 0; i < SIZE/20; i++)
        {
            for (int j = 0; j < SIZE/20; j ++)
            {
                checkState(i,j);
            }
        }



        setBackground(Color.BLACK);
        setSize(SIZE+ 2*dX, SIZE+ 2*dY);
        setTitle("MasterSweep");
        setResizable(false);
        setVisible(true);
    }

    public void checkState(int x, int y)
    {
        int count = 0;
        if (arr[x][y].getState() == -1) return;
        if ( x-1 > 0 && x-1 < 20)
        {
            if ( y-1 > 0 && y-1 < 20 && arr[x-1][y-1].getState() == -1) count++;
            if ( y > 0 && y < 20 && arr[x-1][y].getState() == -1) count++;
            if ( y+1 > 0 && y+1 < 20 && arr[x-1][y+1].getState() == -1) count++;
        }
        if ( x > 0 && x < 20){
            if ( y-1 > 0 && y-1 < 20 && arr[x][y-1].getState() == -1) count++;
            // dont check itself
            if ( y+1 > 0 && y+1 < 20 && arr[x][y+1].getState() == -1) count++;
        }
        if ( x+1 > 0 && x+1 < 20){
            if ( y-1 > 0 && y-1 < 20 && arr[x+1][y-1].getState() == -1) count++;
            if ( y > 0 && y < 20 && arr[x+1][y].getState() == -1) count++;
            if ( y+1 > 0 && y+1 < 20 && arr[x+1][y+1].getState() == -1) count++;
        }
        arr[x][y].setState(count);

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


        MouseAdapter m = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                arr[(e.getX() - dX) / 20][(e.getY()-dY) / 20].reveal();
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


            //int mouseX = -1,mouseY = -1;
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);


                /**if (e.getX() > SIZE + dX || e.getY() > SIZE + dY || e.getX() < dX || e.getY() < dY){
                    if (mouseX != -1)
                    {
                        arr[((mouseX - dX) / 20)][((mouseY-dY) / 20)].mouseOver = false;
                    }


                    mouseX = e.getX();
                    mouseY = e.getY();

                    arr[(mouseX - dX) / 20][(mouseY-dY) / 20].mouseOver = true;
                    repaint();
                }**/
            }
        };
        addMouseListener(m);
        addMouseMotionListener(m);
    }
}
