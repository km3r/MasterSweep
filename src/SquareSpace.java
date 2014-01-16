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

    public void reveal()
    {
        revealed = true;
    }

    public void flag(){
        flagged = true;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        if (state < 9 && state > - 2) this.state = state;
    }



    SquareSpace(int state)
    {
        this.state = state;
        revealed = false;
        flagged = false;
    }
    SquareSpace(){
        state = 0;
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
        File f;
        if (!revealed) f = new File("res/A.png");
        else if (flagged) f = new File("res/F.png");
        else f = new File("res/"+state+".png");

        Image i = null;
        try {
            i = ImageIO.read(f);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return i;
    }
}
