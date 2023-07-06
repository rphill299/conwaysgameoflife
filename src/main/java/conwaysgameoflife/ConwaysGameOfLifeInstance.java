/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package conwaysgameoflife;

import apputilities.*;
import java.io.File;
import javax.swing.*;


/**
 *
 * @author rhill
 */
public class ConwaysGameOfLifeInstance extends AppInstance {
    
    public ConwaysGameOfLifeInstance(AppController ctrl) {
        super(ctrl);
    }
    
    @Override
    public void run() {
        this.showGUI();
    }
    
    public static void main(String[] args) {
        ConwaysGameOfLifeModel model = new ConwaysGameOfLifeModel(11,10, 50);
        ConwaysGameOfLifeController ctrl = new ConwaysGameOfLifeController();
        ConwaysGameOfLifeGUI gui = new ConwaysGameOfLifeGUI(ctrl);
        model.setController(ctrl);
        ctrl.setGUI(gui);
        ctrl.setModel(model);
        //dctrl.setCurrentStep();
        
        SwingUtilities.invokeLater(new ConwaysGameOfLifeInstance(ctrl));
    }                 // ^^ calls run() method
}                     //        of given arg in 
                      //            seperate process
