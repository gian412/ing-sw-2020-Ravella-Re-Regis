package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.utils.GodType;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class GamePanel extends JPanel {
    private final String PATH = "src/main/java/it/polimi/ingsw/utils/graphics/";

    class powerPanel extends JPanel{
            public powerPanel(GodType god){
                Image image;
                JLabel powerLabel;
                try {
                    image = ImageIO.read(new File(PATH + god.getCapitalizedName() + "_power.png"))
                            .getScaledInstance(150, 100, Image.SCALE_DEFAULT);
                    powerLabel = new JLabel(new ImageIcon(image));

                    this.add(powerLabel);
                    this.setSize(150, 100);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
    }

    public GamePanel(JPanel board){
        this.setLayout(new GridLayout(0, 2));
        this.add(board);
        this.add(new powerPanel(StaticFrame.getGod()));
    }

    private GridBagConstraints setConstraints(int x, int y, double weightx, double weighty){
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.weightx = weightx;
        gridBagConstraints.weighty = weighty;
        gridBagConstraints.gridx = x;
        gridBagConstraints.gridy = y;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;

        return gridBagConstraints;
    }
}
