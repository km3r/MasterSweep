import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * The logic of the board.
 *
 * @author Kyle Rosenthal
 * @version 5/7/2015
 */
public class KaboomBoard implements IBoard {
    private SquareSpace[][] board;
    private Integer[] oldBoard;
    private final static int SIZE = 10;

    private int currBCount = 10;
    private static final int kbStart = 10;
    private boolean lost = false;
    TableModelListener tListener;

    public KaboomBoard()
    {
        reset();
    }

    public void reset()
    {
        Integer[] bombs= new Integer[kbStart];
        Set<Integer> set = new HashSet<>();
        Random rng = new Random();
        while(set.size() < kbStart)
        {
            set.add(rng.nextInt(SIZE*SIZE));
        }
        Iterator iterator = set.iterator();
        for (int num = 0; num < bombs.length; num++)
        {
            bombs[num] = (int) iterator.next();
        }
        reset(bombs);
    }

    public void reset(Integer[] bombs)
    {
        oldBoard = bombs;
        lost = false;
        SquareSpace.bStart = bombs.length;
        SquareSpace.totalCount = SIZE*SIZE;
        currBCount = bombs.length;
        board = new SquareSpace[SIZE][SIZE];
        for (int row = 0; row < SIZE; row++)
        {
            for (int col = 0; col < SIZE; col ++)
            {
                board[row][col] = new SquareSpace();
            }
        }
        for (int num = 0; num < bombs.length; num++)
        {
            board[bombs[num]/SIZE][bombs[num]%SIZE].setState(-1);
        }
        for (int row = 0; row < SIZE; row++)
        {
            for (int col = 0; col < SIZE; col ++)
            {
                checkState(row,col);
            }
        }
    }

    public void checkState(int xPos, int yPos)
    {
        int count = 0;
        if (board[xPos][yPos].getState() == -1) return;
        if ( xPos-1 >= 0 && xPos-1 < SIZE)
        {
            if ( yPos-1 >= 0 && yPos-1 < SIZE && board[xPos-1][yPos-1].getState() == -1) count++;
            if ( yPos >= 0 && yPos < SIZE && board[xPos-1][yPos].getState() == -1) count++;
            if ( yPos+1 >= 0 && yPos+1 < SIZE && board[xPos-1][yPos+1].getState() == -1) count++;
        }
        if ( xPos >= 0 && xPos < SIZE){
            if ( yPos-1 >= 0 && yPos-1 < SIZE && board[xPos][yPos-1].getState() == -1) count++;
            // dont check itself
            if ( yPos+1 >= 0 && yPos+1 < SIZE && board[xPos][yPos+1].getState() == -1) count++;
        }
        if ( xPos+1 >= 0 && xPos+1 < SIZE){
            if ( yPos-1 >= 0 && yPos-1 < SIZE && board[xPos+1][yPos-1].getState() == -1) count++;
            if ( yPos >= 0 && yPos < SIZE && board[xPos+1][yPos].getState() == -1) count++;
            if ( yPos+1 >= 0 && yPos+1 < SIZE && board[xPos+1][yPos+1].getState() == -1) count++;
        }
        board[xPos][yPos].setState(count);
        changed();
    }

    private void changed()
    {
        if (tListener != null)
        {
            tListener.tableChanged(new TableModelEvent(this,0,SIZE));
        }
    }

    public void reveal(int xPos, int yPos){

        if (!board[xPos][yPos].isRevealed())
        {
            board[xPos][yPos].reveal();
        }
        else if (board[xPos][yPos].getState() == -1 && !board[xPos][yPos].flagged)
        {
            lost = true;
        }
        else if (board[xPos][yPos].getState() == 0)
        {
            for (int row = -1; row < 2; row++)
            {
                for (int col = -1; col < 2; col++)
                {
                    if ((row != 0
                            || col != 0)
                            && xPos + row < SIZE
                            && xPos + row >= 0
                            && yPos + col < SIZE
                            && yPos + col >= 0
                            && !board[xPos + row][yPos + col].isRevealed())
                    {
                        board[xPos + row][yPos + col].reveal();
                        if (board[xPos + row][yPos + col].getState() == 0)
                        {
                            reveal(xPos + row, yPos + col);
                        }
                    }
                }
            }
        }
    }
    @Override
    public void makeMove(int xPos, int yPos)
    {
        reveal(xPos, yPos);
        changed();
    }
    @Override
    public void handleRightClick(int xPos, int yPos)
    {
        System.out.print("test");
        board[xPos][yPos].flag();
        changed();
    }
    @Override
    public void newBoard()
    {
        reset();
        changed();
    }
    @Override
    public void restart()
    {
        reset(oldBoard);
        changed();
    }
    @Override
    public void clear()
    {
        for (int row = 0; row < board.length; row++)
        {
            for (int col = 0; col < board[0].length; col ++)
            {
                if (board[row][col].display().equals("0"))
                {
                    board[row][col].reveal();
                }
            }
        }
        changed();
    }
    @Override
    public void loadFile(String fileName)
    {
        try
        {
            Scanner scanner = new Scanner(new File(fileName));
            ArrayList<Integer> bombs = new ArrayList<Integer>();
            while (scanner.hasNextInt())
            {
                bombs.add(SIZE*scanner.nextInt() + scanner.nextInt());
            }
            reset((Integer[]) bombs.toArray());
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }
    @Override
    public boolean isLost()
    {
        return lost;
    }
    @Override
    public boolean isWon()
    {
        return SquareSpace.totalCount == currBCount;
    }
    @Override
    public int getFlagCount()
    {
        return SquareSpace.flags;
    }
    @Override
    public int getRowCount()
    {
        return board.length;
    }
    @Override
    public int getColumnCount()
    {
        return board[0].length;
    }
    @Override
    public String getColumnName(int columnIndex)
    {
        return "" + (char)(columnIndex + 'A');
    }
    @Override
    public Class<?> getColumnClass(int columnIndex)
    {
        return String.class;
    }
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
        return rowIndex >= 0 && rowIndex < board.length
                && columnIndex >= 0 && columnIndex < board[0].length;
    }



    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        return board[rowIndex][columnIndex].display();
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex)
    {
        if (aValue instanceof Integer)
        {
            board[rowIndex][columnIndex].setState((Integer) aValue);
        }
        else if (aValue instanceof SquareSpace)
        {
            board[rowIndex][columnIndex] = (SquareSpace) aValue;
        }
    }
    @Override
    public void addTableModelListener(TableModelListener listener) {
        tListener = listener;
    }
    @Override
    public void removeTableModelListener(TableModelListener listener) {
        if (tListener == listener)
        {
            tListener  = null;
        }
    }
}
