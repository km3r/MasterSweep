//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import java.awt.event.*;

public class KaboomGUI extends JFrame implements TableModelListener {
    private String gamePrefix;
    private IBoard myBoard;
    private IController control;
    private JTable table;
    private JMenuBar menuBar;
    private JPanel statusPane;
    private JLabel flagLabel;
    private JLabel gameOverLabel;
    private int tileWidth = 35;
    private int tileHeight = 30;
    private ImageIcon background = null;

    public KaboomGUI(IBoard boardData, IController control) {
        super("Kaboom");
        this.myBoard = boardData;
        this.control = control;

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception var4) {
            System.err.println(var4);
        }

    }

    public void setTileDimensions(int imgWidth, int imgHeight) {
        this.tileWidth = imgWidth;
        this.tileHeight = imgHeight;
    }

    public void layoutGUI() {
        this.table = new JTable(this.myBoard);
        this.myBoard.addTableModelListener(this);
        PieceRenderer myRenderer = new PieceRenderer();
        this.table.setDefaultRenderer(String.class, myRenderer);
        TableColumn column = null;
        if(this.myBoard != null) {
            for(int mainMenu = 0; mainMenu < this.myBoard.getColumnCount(); ++mainMenu) {
                column = this.table.getColumnModel().getColumn(mainMenu);
                column.setMaxWidth(this.tileWidth);
                column.setMinWidth(this.tileWidth);
            }
        }

        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), 1));
        this.menuBar = new JMenuBar();
        JMenu var5 = this.getMenu();
        JMenuItem quitmenu = new JMenuItem("Quit");
        quitmenu.setMnemonic('Q');
        quitmenu.setAccelerator(KeyStroke.getKeyStroke(81, 8));
        quitmenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });
        var5.add(quitmenu);
        this.menuBar.add(var5);
        this.setJMenuBar(this.menuBar);
        this.statusPane = new JPanel();
        this.flagLabel = new JLabel();
        this.statusPane.add(this.flagLabel);
        this.gameOverLabel = new JLabel("");
        this.statusPane.add(this.gameOverLabel);
        this.statusPane.setAlignmentX(0.5F);
        this.getContentPane().add(this.statusPane);
        this.table.setSelectionMode(0);
        this.table.setCellSelectionEnabled(false);
        this.table.setRowHeight(this.tileHeight);
        this.table.setOpaque(true);
        this.table.setShowGrid(true);
        this.table.setAlignmentX(0.5F);
        this.getContentPane().add(this.table);
        this.table.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent ev) {
                int col = KaboomGUI.this.table.getSelectedColumn();
                int row = KaboomGUI.this.table.getSelectedRow();
                if(SwingUtilities.isRightMouseButton(ev)) {
                    row = (int)(ev.getPoint().getY() / (double)KaboomGUI.this.tileHeight);
                    col = (int)(ev.getPoint().getX() / (double)KaboomGUI.this.tileWidth);
                    KaboomGUI.this.myBoard.handleRightClick(row, col);
                } else {
                    KaboomGUI.this.myBoard.makeMove(row, col);
                }

            }
        });
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    public void tableChanged(TableModelEvent e) {
        int row = e.getFirstRow();
        int column = e.getColumn();
        TableModel model = (TableModel)e.getSource();
        this.flagLabel.setText(this.getFlagMsg());
        this.gameOverLabel.setText("");
        if(this.myBoard.isLost()) {
            this.gameOverLabel.setText(" Lost.");
        }

        if(this.myBoard.isWon()) {
            this.gameOverLabel.setText(" WON!");
        }

    }

    private String getFlagMsg() {
        return String.format("Flags: %2d/10", new Object[]{Integer.valueOf(this.myBoard.getFlagCount())});
    }

    private JMenu getMenu() {
        JMenu mnuGame = new JMenu("Game");
        char[] keys = this.control.getShortcutKeys();
        int item = 0;
        String[] arr$ = this.control.getMenuNames();
        int len$ = arr$.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            String name = arr$[i$];
            JMenuItem menu = new JMenuItem(name);
            menu.setMnemonic(keys[item]);
            menu.setAccelerator(KeyStroke.getKeyStroke(keys[item], 8));
            menu.addActionListener(this.control);
            mnuGame.add(menu);
            ++item;
        }

        return mnuGame;
    }

    public static void main(String[] args) {
        KaboomBoard myBoard = new KaboomBoard();
        Controller controller = new Controller(myBoard);
        KaboomGUI frame = new KaboomGUI(myBoard, controller);
        frame.layoutGUI();
        if(args.length > 0) {
            myBoard.loadFile(args[0]);
        } else {
            myBoard.newBoard();
        }

        frame.pack();
        frame.setVisible(true);
    }
}
