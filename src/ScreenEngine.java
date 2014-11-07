import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.IOException;

/**
 * Kyle Rosenthal
 * 1/15/14
 */
public class ScreenEngine extends JFrame implements Runnable {
    Graphics g2;
    SquareSpace[][] arr;

    int bCount;
    final int dX = 20;
    final int dY = 40;
    final static int SIZE = 400; // keep multiple of 20

    ScreenEngine()
    {


        reset();
        setBackground(Color.BLACK);
        setSize(SIZE+ 2*dX, SIZE+ 2*dY);
        setTitle("MasterSweep");
        setResizable(false);
        setVisible(true);
        createBufferStrategy(2);
        paintNow();
    }
    public void reset(){
        SquareSpace.bStart = 0;
        SquareSpace.totalCount = 400;
        bCount = 0;
        arr = new SquareSpace[SIZE/20][SIZE/20];
        for (int i = 0; i < SIZE/20; i++)
        {
            for (int j = 0; j < SIZE/20; j ++)
            {

                if (Math.random() < .12){
                    arr[i][j] = new SquareSpace(-1);
                    bCount++;
                }
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

        SquareSpace.bStart = bCount;



    }

    public void checkState(int x, int y)
    {
        int count = 0;
        if (arr[x][y].getState() == -1) return;
        if ( x-1 >= 0 && x-1 < 20)
        {
            if ( y-1 >= 0 && y-1 < 20 && arr[x-1][y-1].getState() == -1) count++;
            if ( y >= 0 && y < 20 && arr[x-1][y].getState() == -1) count++;
            if ( y+1 >= 0 && y+1 < 20 && arr[x-1][y+1].getState() == -1) count++;
        }
        if ( x >= 0 && x < 20){
            if ( y-1 >= 0 && y-1 < 20 && arr[x][y-1].getState() == -1) count++;
            // dont check itself
            if ( y+1 >= 0 && y+1 < 20 && arr[x][y+1].getState() == -1) count++;
        }
        if ( x+1 >= 0 && x+1 < 20){
            if ( y-1 >= 0 && y-1 < 20 && arr[x+1][y-1].getState() == -1) count++;
            if ( y >= 0 && y < 20 && arr[x+1][y].getState() == -1) count++;
            if ( y+1 >= 0 && y+1 < 20 && arr[x+1][y+1].getState() == -1) count++;
        }
        arr[x][y].setState(count);

    }


    
    public void paintNow(Graphics g){
        g.setColor(Color.black);
        g.fillRect(0,  0,SIZE+dX+dX,SIZE+dY+dY);
        g.setColor(Color.WHITE);
        g.fillRect(dX, dY, SIZE, SIZE);


        g.setColor(Color.black);
        g.fillRect(dX+dX,  SIZE + dY + (dY/2),200,200);
        g.setColor(Color.WHITE);
        g.drawString("BOMBS: " + bCount,dX+dX,  SIZE + dY + (dY/2));

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
                if ( e.getButton() == MouseEvent.BUTTON1){
                    reveal((e.getX() - dX) / 20, (e.getY() - dY) / 20);
                    if (SquareSpace.totalCount <= SquareSpace.bStart){
                        paintNow();
                        JOptionPane.showMessageDialog(StartMain.s, "You win, resetting board.....","WINNER!", JOptionPane.PLAIN_MESSAGE);
                        System.out.println("WIN");
                        reset();
                        paintNow();
                    }
                }
                else if (e.getButton() == MouseEvent.BUTTON3){
                    arr[(e.getX() - dX) / 20][(e.getY()-dY) / 20].flag();
                    if (arr[(e.getX() - dX) / 20][(e.getY()-dY) / 20].flagged) bCount--;
                    else if (!arr[(e.getX() - dX) / 20][(e.getY()-dY) / 20].isRevealed())bCount++;
                }

                paintNow();

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
                    paintNow();
                }**/
            }
        };
        addMouseListener(m);
        addMouseMotionListener(m);
      
    }

    public void paintNow(){
        BufferStrategy bs = getBufferStrategy();


            Graphics g = bs.getDrawGraphics();
            paintNow(g);
            g.dispose();
            bs.show();


    }


    public void reveal(int x, int y){

        if (!arr[x][y].isRevealed())arr[x][y].reveal();
        if (arr[x][y].getState() == -1 && !arr[x][y].flagged){
            paintNow();
            if (SquareSpace.totalCount >= 399)
            {
                reset();
                reveal(x,y);
                paintNow();
            } else {
                JOptionPane.showMessageDialog(this, "You lose, resetting board.....","GAME OVER", JOptionPane.PLAIN_MESSAGE);
                System.out.println("LOSE");
                reset();
                paintNow();
            }
            return;
        }
        if (arr[x][y].getState() != 0) return;



        for (int i = -1; i < 2; i++)
        {
            for (int j = -1; j < 2; j++)
            {
                if ((i != 0
                        || j != 0)
                        && x + i < 20
                        && x + i >= 0
                        && y + j < 20
                        && y + j >= 0
                        && !arr[x+i][y+j].isRevealed()){
                    arr[x+i][y+j].reveal();
                    if (arr[x+i][y+j].getState() == 0) reveal( x + i,y + j);
                }
            }
        }
    }
}
