package it.polimi.ingsw.view.gui;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class GUILoader {

    private GUILoader() {
    }

    /**
     * loads the GUI
     * 
     * @author Elia Ravella
     */
    public static void loadGUI() {
        StaticFrame.initFrame("Santorini");
        StaticFrame.addPanel(new LoginPanel());
        GetImages.loadAll();

        StaticFrame.setSize(835, 831);
        StaticFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        StaticFrame.setVisible(true);
    }
}
