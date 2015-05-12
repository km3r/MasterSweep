import java.awt.event.ActionEvent;

/**
 * (short description)
 *
 * @author Kyle Rosenthal
 * @version 5/7/2015
 */
public class Controller implements IController
{
    private KaboomBoard myBoard;

    public Controller(KaboomBoard myBoard)
    {
        this.myBoard = myBoard;
    }

    @Override
    public String[] getMenuNames()
    {
        String[] arr = {"New", "Restart", "Cheat"};
        return arr;
    }

    @Override
    public char[] getShortcutKeys()
    {
        char[] arr = {'N','R','C'};
        return arr;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent)
    {
        switch (actionEvent.getActionCommand())
        {
            case "New":
                myBoard.reset();
                break;
            case "Restart":
                myBoard.restart();
                break;
            case "Cheat":
                myBoard.clear();
                break;
        }
    }
}
