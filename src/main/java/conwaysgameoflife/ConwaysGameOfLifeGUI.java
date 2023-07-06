/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conwaysgameoflife;

import apputilities.AppGUI;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author rhill
 */
public class ConwaysGameOfLifeGUI extends AppGUI {
    
    public static final ImageIcon CGOL_IMAGE_ICON = new ImageIcon(RESOURCE_PATH+"cgol.png");
    public static final ImageIcon GERM_IMAGE_ICON = new ImageIcon(RESOURCE_PATH+"germ.png");
    public static final ImageIcon OPEN_IMAGE_ICON = new ImageIcon(RESOURCE_PATH+"open16.png");
    public static final ImageIcon BACK_IMAGE_ICON = new ImageIcon(RESOURCE_PATH+"left16.png");
    public static final ImageIcon PLAY_IMAGE_ICON = new ImageIcon(RESOURCE_PATH+"play16.png");
    public static final ImageIcon PAUSE_IMAGE_ICON = new ImageIcon(RESOURCE_PATH+"pause16.png");
    public static final ImageIcon FORWARD_IMAGE_ICON = new ImageIcon(RESOURCE_PATH+"right16.png");
    public static final ImageIcon SAVE_IMAGE_ICON = new ImageIcon(RESOURCE_PATH+"save16.png");
    public static final ImageIcon CONFIG_IMAGE_ICON = new ImageIcon(RESOURCE_PATH+"gear16.png");
    public static final ImageIcon LOAD_IMAGE_ICON= new ImageIcon(RESOURCE_PATH+"load16.png");
    
    private int rows, cols, numSteps, index = 0;
    private Board board;
    private StatPanel statPanel;
    private JTextField stepTextField;
    
    //public ConwaysGameOfLifeController ctrl;
    
    public ConwaysGameOfLifeGUI(ConwaysGameOfLifeController ctrl) {
        super(ctrl);
        this.setUpFrame();
        
        this.getConfiguration();
        
        this.setUpView();
    }
    
    public void setBoard(ConwaysGameOfLifeModel.Step step) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.setButtonColor(this.board.buttonGrid[i][j], step.board[i][j]);
            }
        }
        this.statPanel.aliveStat.setStat(step.alive);
        this.statPanel.deadStat.setStat(step.dead);
        this.statPanel.daliveStat.setStat(step.dalive);
        this.statPanel.ddeadStat.setStat(step.ddead);
    }
    
    public void setIndex(int i) {
        this.index = i<0?0:i>numSteps?numSteps:i;
        this.stepTextField.setText(String.valueOf(this.index));
    }
    
    public void incrementIndex() {
        this.setIndex(this.index+1);
    }
    
    public void decrementIndex() {
        this.setIndex(this.index-1);
    }
    
    public int getIndex() {
        return this.index;
    }
    
    public int getNumSteps() {
        return this.numSteps;
    }
    
    public int[][] getSeed() {
        int[][] seed = new int[this.rows][this.cols];
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                this.board.buttonGrid[i][j].setEnabled(false);
                if (this.board.buttonGrid[i][j].getBackground().equals(Color.white)) {
                    seed[i][j] = -1;
                } else {
                    seed[i][j] = 0;
                }
            }
        }
        return seed;
    }
    
    public void toggleAlive(Board.Button button) {
        if (button.getBackground() == Color.white) {
            button.setBackground(Color.red);
            this.statPanel.incrementAlive();
        } else {
            button.setBackground(Color.white);
            this.statPanel.decrementAlive();
        }
    }
    
    private void setButtonColor(Board.Button button, int n) {
        if (n == -1) {
            button.setBackground(Color.white);
        } else {
            int tmp = 255-(n*25);
            button.setBackground(new Color(tmp<0?0:tmp, 0, 0));
        }
    }
    
    private void setUpFrame() {
        this.setSize((int)(SCREEN_HEIGHT*0.9), (int)(SCREEN_HEIGHT*0.9));
        this.setBackground(Color.GREEN);
        
        this.setIconImage(CGOL_IMAGE_ICON.getImage());
        this.setTitle("Conway's Game of Life");
        this.setLocation((SCREEN_WIDTH - this.getWidth())/2, 
                         (SCREEN_HEIGHT - this.getHeight())/2);
        this.setLayout(new BorderLayout());
    }
    
    private void getConfiguration() {
        if (true) {
            // check if serialized configuration 
            this.cols = 10;
            this.rows = 11;
            this.numSteps = 50;
        }
        else {
            // ask for configuration in popup
            this.cols = Integer.parseInt(JOptionPane.showInputDialog("Enter graph width: ",""));
            this.rows = Integer.parseInt(JOptionPane.showInputDialog("Enter graph height: ",""));
            this.numSteps = Integer.parseInt(JOptionPane.showInputDialog("Enter number of steps: ",""));
        }
    }
    
    private void setUpView() {
        //create components
        JMenuBar menubar = setUpMenuBar();
        Container boardAndToolBar = this.setUpBoardAndToolBar();
        this.statPanel = new StatPanel(this.rows*this.cols);
        
        //add components to GUI 
        this.setJMenuBar(menubar);
        this.add(boardAndToolBar, BorderLayout.CENTER);
        this.add(statPanel, BorderLayout.PAGE_END);
        this.pack();
    }

    private Container setUpBoardAndToolBar() {
        Container ret = new Container();
        //make toolbar
        JToolBar toolbar = this.setUpToolBar();
        //make cgol grid inside scrollpane
        this.board = new Board((ConwaysGameOfLifeController)(this.ctrl));
        JScrollPane scrollBoard = new JScrollPane(this.board);
        //make button panel
        JPanel buttonPanel = setUpButtonPanel();
        //add components
        ret.setLayout(new BorderLayout());
        ret.add(scrollBoard, BorderLayout.CENTER);
        ret.add(toolbar, BorderLayout.PAGE_START);
        ret.add(buttonPanel, BorderLayout.PAGE_END);
        return ret;
    }
    
    private JToolBar setUpToolBar() {
        JToolBar ret = new JToolBar();
        ret.add(new JButton(((ConwaysGameOfLifeController)(this.ctrl)).openAction));
        ret.add(new JButton(((ConwaysGameOfLifeController)(this.ctrl)).saveAction));
        ret.add(new JButton(((ConwaysGameOfLifeController)(this.ctrl)).loadAction));
        ret.add(new JButton(((ConwaysGameOfLifeController)(this.ctrl)).configAction));
        
        return ret;
    }
    
    private JMenuBar setUpMenuBar() {
        JMenuBar ret = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu editMenu = new JMenu("Edit");
        JMenu helpMenu = new JMenu("Help");
        //file menu
        fileMenu.add(new JButton(((ConwaysGameOfLifeController)(this.ctrl)).openAction));
        fileMenu.add(new JButton(((ConwaysGameOfLifeController)(this.ctrl)).saveAction));
        fileMenu.add(new JButton(((ConwaysGameOfLifeController)(this.ctrl)).loadAction));
        //edit menu
        editMenu.add(new JButton(((ConwaysGameOfLifeController)(this.ctrl)).backAction));
        editMenu.add(new JButton(((ConwaysGameOfLifeController)(this.ctrl)).playAction));
        editMenu.add(new JButton(((ConwaysGameOfLifeController)(this.ctrl)).pauseAction));
        editMenu.add(new JButton(((ConwaysGameOfLifeController)(this.ctrl)).forwardAction));
        editMenu.addSeparator();
        editMenu.add(new JButton(((ConwaysGameOfLifeController)(this.ctrl)).configAction));
        //help menu
        
        ret.add(fileMenu);
        ret.add(editMenu);
        ret.add(helpMenu);
        
        return ret;
    }

    private JPanel setUpButtonPanel() {
        JPanel ret = new JPanel();
        ret.add(new JButton(((ConwaysGameOfLifeController)(this.ctrl)).backAction));
        ret.add(new JButton(((ConwaysGameOfLifeController)(this.ctrl)).playAction));
        ret.add(new JButton(((ConwaysGameOfLifeController)(this.ctrl)).pauseAction));
        ret.add(new JButton(((ConwaysGameOfLifeController)(this.ctrl)).forwardAction));
        this.stepTextField = new JTextField("0", 5);
        this.stepTextField.addActionListener(((ConwaysGameOfLifeController)(this.ctrl)).stepTextAction);
        ret.add(this.stepTextField);
        
        return ret;
    }

    public class Board extends JComponent {
        public int rows = ConwaysGameOfLifeGUI.this.rows;
        public int cols = ConwaysGameOfLifeGUI.this.cols;
        public Button[][] buttonGrid = new Button[this.rows][this.cols];
        ConwaysGameOfLifeController ctrl;
                
        public Board(ConwaysGameOfLifeController ctrl) {
            this.ctrl = ctrl;
            this.setLayout(new GridLayout(this.rows, this.cols));
            for (int i = 0; i < this.rows; i++) {
                for (int j = 0; j < this.cols; j++) {
                    var tmp = new Button(this.ctrl, i, j);
                    this.add(tmp);
                    this.buttonGrid[i][j] = tmp;
                    
                }
            }
        }
        
            public class Button extends JButton {
                ConwaysGameOfLifeController ctrl;
                public int row, col;

                public Button(ConwaysGameOfLifeController ctrl, int row, int col) {
                    super(ctrl.germAction);
                    this.ctrl = ctrl;
                    this.row = row;
                    this.col = col;
                    this.setBackground(Color.white);
                    this.setOpaque(true);
                    this.setBorderPainted(false);
                }
            }
    }
    

    
    public class StatPanel extends JPanel {
        int numCells;
        StatField aliveStat, deadStat, daliveStat, ddeadStat;
        
        public StatPanel(int numCells) {
            JPanel alivePanel, deadPanel;
            this.numCells = numCells;
            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            alivePanel = new JPanel();
            deadPanel = new JPanel();
            alivePanel.add(this.aliveStat = new StatField("Alive", 0));
            alivePanel.add(this.daliveStat = new StatField("Change in living", 0));
            deadPanel.add(this.deadStat = new StatField("Dead", this.numCells));
            deadPanel.add(this.ddeadStat = new StatField("Change in deceased", 0));
            this.add(alivePanel);
            this.add(deadPanel);
        }
        
        public void incrementAlive() {
            this.aliveStat.incrementStat();
            this.daliveStat.incrementStat();
            this.deadStat.decrementStat();
            this.ddeadStat.decrementStat();
        }
        
        public void decrementAlive() {
            this.aliveStat.decrementStat();
            this.daliveStat.decrementStat();
            this.deadStat.incrementStat();
            this.ddeadStat.incrementStat();
        }
        
        public void setAlive(int n) {
            this.aliveStat.setStat(n);
        }
        
        public void setDead(int n) {
            this.deadStat.setStat(n);
        }
        
        public void setChangeAlive(int n) {
            this.daliveStat.setStat(n);
        }
        
        public void setChangeDead(int n) {
            this.ddeadStat.setStat(n);
        }
        
        private class StatField extends JPanel {
            public StatField(String title, int stat) {
                this.stat = stat;
                label = new JLabel(title + ": ");
                textField = new JTextField(String.valueOf(stat), 3);
                textField.setEditable(false);
                this.add(label);
                this.add(textField);
            }

            void setStat(int stat) {
                this.stat = stat;
                this.textField.setText(String.valueOf(this.stat));
            }
            
            void incrementStat() {
                this.stat++;
                this.textField.setText(String.valueOf(this.stat));
            }
            
            void decrementStat() {
                this.stat--;
                this.textField.setText(String.valueOf(this.stat));
            }

            int getStat() {
                return stat;
            }

            private final JLabel label;
            private final JTextField textField;
            private int stat;
        }
    }
}
