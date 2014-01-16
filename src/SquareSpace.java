import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Kyle Rosenthal
 * 1/15/14
 */
public class SquareSpace {
    int state; // number of bombs next to, -1 if bomb
    boolean revealed;
    boolean flagged;
    public boolean mouseOver;
    static Image[] imgs;

    public void reveal()
    {
        revealed = true;
    }

    public void flag(){
        flagged = true;
    }
    public void mouse()
    {
        mouseOver = !mouseOver;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        if (state < 9 && state > - 2) this.state = state;
    }



    static{
        imgs = new Image[13];
        File f;
        for (int i = -1; i < 9;i++)
        {
            f = new File("res/"+i+".png");

            imgs[i+1] = null;
            try {
                imgs[i+1] = ImageIO.read(f);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try{
            f = new File("res/A.png");
            imgs[10] = ImageIO.read(f);
            f = new File("res/B.png");
            imgs[11] = ImageIO.read(f);
            f = new File("res/F.png");
            imgs[12] = ImageIO.read(f);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    SquareSpace(int state)
    {
        this.state = state;
        revealed = false;
        flagged = false;
        mouseOver = false;


    }
    SquareSpace(){
        state = 0;
        revealed = false;
        flagged = false;
        mouseOver = false;
    }


    @Override
    public String toString() {
        switch (state){
            case -1:
                return "Bomb";
            default:
                return  ""+state;
        }
    }

    public Color toColor()
    {
        if (!revealed) return Color.DARK_GRAY;
        switch (state){
            case -1:
                return Color.RED;
            default:
                return  Color.GRAY;
        }
    }

    public Image toIcon()
    {
        if (flagged) return imgs[12];
        else if (!revealed) {
            if (mouseOver) return imgs[11];
            else return imgs[10];
        }
        else return imgs[state+1];
    }
}
