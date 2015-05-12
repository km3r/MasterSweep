/**
 * Represents a single square on the board.
 *
 * @author Kyle Rosenthal
 * @version 5/7/2015
 */
public class SquareSpace {
    int state; // number of bombs next to, -1 if bomb
    boolean revealed;
    boolean flagged;
    public boolean mouseOver;
    public static int totalCount = 400;
    public static int bStart;
    public static int flags;
    private static String[] vals = {"X"," ","1","2","3","4","5","6","7","8","9","F"};

    public SquareSpace(){
        state = 0;
        revealed = false;
        flagged = false;
        mouseOver = false;
    }

    public void reveal()
    {
        totalCount--;
        if (!flagged) revealed = true;
    }

    public void flag()
    {
        if (!revealed)
        {
            if (flagged)
            {
                flags--;
            }
            else
            {
                flags++;
            }
            flagged = !flagged;
        }
    }

    public int getState()
    {
        return state;
    }

    public String display()
    {
        String disp = "-";
        if (flagged)
        {
            disp = vals[vals.length-1];
        }
        if (revealed)
        {
            disp = vals[state+1];
        }
        return disp;
    }

    public void setState(int state)
    {
        if (state != 0 && this.state == 0)
        {
            totalCount--;
        }
        if (state < 9 && state > - 2)
        {
            this.state = state;
        }

    }

    public boolean isRevealed()
    {
        return revealed;
    }
}
