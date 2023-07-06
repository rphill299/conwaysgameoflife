/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conwaysgameoflife;

import apputilities.AppController;
import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JTextField;
import apputilities.*;
import javax.swing.Timer;

/**
 *
 * @author rhill
 */
public class ConwaysGameOfLifeController extends AppController {
    
    public final AbstractAction germAction = new GermAction();
    public final AbstractAction openAction = new OpenAction();
    public final AbstractAction backAction = new BackAction();
    public final AbstractAction playAction = new PlayAction();
    public final AbstractAction pauseAction = new PauseAction();
    public final AbstractAction forwardAction = new ForwardAction();
    public final AbstractAction saveAction = new SaveAction();
    public final AbstractAction configAction = new ConfigAction();
    public final AbstractAction loadAction = new LoadAction();
    public final AbstractAction stepTextAction = new StepTextAction();
    
    public ConwaysGameOfLifeController() {
        super();
    }
    
    public ConwaysGameOfLifeController(ConwaysGameOfLifeModel model, ConwaysGameOfLifeGUI gui) {
        super(model, gui);
    }
    
    @Override
    public void showGUI() {
        this.gui.setVisible(true);
    }
    
    public class GermAction extends AbstractAction {
        public GermAction() {
            super("", ConwaysGameOfLifeGUI.GERM_IMAGE_ICON);
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            ConwaysGameOfLifeGUI.Board.Button b = (ConwaysGameOfLifeGUI.Board.Button)e.getSource();
            ((ConwaysGameOfLifeGUI)(ConwaysGameOfLifeController.this.gui)).toggleAlive(b);
            ConwaysGameOfLifeController.this.gui.repaint();
        }
        
    }
    
    public class OpenAction extends AbstractAction {
        public OpenAction() {
            super("Open", ConwaysGameOfLifeGUI.OPEN_IMAGE_ICON);
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton b = (JButton)e.getSource();
            b.setBackground(Color.red);
            b.setOpaque(true);
            ConwaysGameOfLifeController.this.gui.repaint();
        }
        
    }
    
    public class BackAction extends AbstractAction {
        public BackAction() {
            super("Back", ConwaysGameOfLifeGUI.BACK_IMAGE_ICON);
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            ((ConwaysGameOfLifeGUI)(ConwaysGameOfLifeController.this.gui)).decrementIndex();
            int index = ((ConwaysGameOfLifeGUI)(ConwaysGameOfLifeController.this.gui)).getIndex();
            var step = ((ConwaysGameOfLifeModel)(ConwaysGameOfLifeController.this.model)).getStep(index);
            ((ConwaysGameOfLifeGUI)(ConwaysGameOfLifeController.this.gui)).setBoard(step);
            ConwaysGameOfLifeController.this.gui.repaint();
        }
        
    }
    
    public class PlayAction extends AbstractAction {
        public PlayAction() {
            super("Play", ConwaysGameOfLifeGUI.PLAY_IMAGE_ICON);
        }
        
        public Timer timer;
        
        @Override
        public void actionPerformed(ActionEvent e) {
            if (((ConwaysGameOfLifeModel)(ConwaysGameOfLifeController.this.model)).needsSeed()) {
                int[][] seed = ((ConwaysGameOfLifeGUI)(ConwaysGameOfLifeController.this.gui)).getSeed();
                ((ConwaysGameOfLifeModel)(ConwaysGameOfLifeController.this.model)).setSeed(seed);
            }
            this.timer = new Timer(100, new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int index = ((ConwaysGameOfLifeGUI)(ConwaysGameOfLifeController.this.gui)).getIndex();
                    if (index == ((ConwaysGameOfLifeGUI)(ConwaysGameOfLifeController.this.gui)).getNumSteps()) {
                        timer.stop();
                        return;
                    }
                    ((ConwaysGameOfLifeGUI)(ConwaysGameOfLifeController.this.gui)).setIndex(++index);
                    var step = ((ConwaysGameOfLifeModel)(ConwaysGameOfLifeController.this.model)).getStep(index);
                    ((ConwaysGameOfLifeGUI)(ConwaysGameOfLifeController.this.gui)).setBoard(step);
                    ConwaysGameOfLifeController.this.gui.repaint();
                }
            });
            timer.start();
        }
        
    }
    
    public class PauseAction extends AbstractAction {
        public PauseAction() {
            super("Pause", ConwaysGameOfLifeGUI.PAUSE_IMAGE_ICON);
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            ((PlayAction)ConwaysGameOfLifeController.this.playAction).timer.stop();
        }
        
    }
    
    public class ForwardAction extends AbstractAction {
        public ForwardAction() {
            super("Forward", ConwaysGameOfLifeGUI.FORWARD_IMAGE_ICON);
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            ((ConwaysGameOfLifeGUI)(ConwaysGameOfLifeController.this.gui)).incrementIndex();
            int index = ((ConwaysGameOfLifeGUI)(ConwaysGameOfLifeController.this.gui)).getIndex();
            ConwaysGameOfLifeModel.Step step;
            if (((ConwaysGameOfLifeModel)(ConwaysGameOfLifeController.this.model)).needsSeed()) {
                int[][] seed = ((ConwaysGameOfLifeGUI)(ConwaysGameOfLifeController.this.gui)).getSeed();
                step = ((ConwaysGameOfLifeModel)(ConwaysGameOfLifeController.this.model)).setSeed(seed);
            } else {
                step = ((ConwaysGameOfLifeModel)(ConwaysGameOfLifeController.this.model)).getStep(index);
            }
            ((ConwaysGameOfLifeGUI)(ConwaysGameOfLifeController.this.gui)).setBoard(step);
            ConwaysGameOfLifeController.this.gui.repaint();
        }
        
    }
    
    public class SaveAction extends AbstractAction {
        public SaveAction() {
            super("Save", ConwaysGameOfLifeGUI.SAVE_IMAGE_ICON);
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            //TODO save loadable tick file
        }
        
    }
    
    public class LoadAction extends AbstractAction {
        public LoadAction() {
            super("Load", ConwaysGameOfLifeGUI.LOAD_IMAGE_ICON);
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            //TODO load loadable tick file
        }
        
    }
    
    public class ConfigAction extends AbstractAction {
        public ConfigAction() {
            super("Configuration", ConwaysGameOfLifeGUI.CONFIG_IMAGE_ICON);
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            //TODO Invoke config pop-up
        }
    }
    
    public class StepTextAction extends AbstractAction {
        public StepTextAction() {
            super();
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            int i = Integer.valueOf(((JTextField)e.getSource()).getText());
            var step = ((ConwaysGameOfLifeModel)(ConwaysGameOfLifeController.this.model)).getStep(i);
            ((ConwaysGameOfLifeGUI)(ConwaysGameOfLifeController.this.gui)).setIndex(i);
            ((ConwaysGameOfLifeGUI)(ConwaysGameOfLifeController.this.gui)).setBoard(step);
            ConwaysGameOfLifeController.this.gui.repaint();
        }
    }
}
