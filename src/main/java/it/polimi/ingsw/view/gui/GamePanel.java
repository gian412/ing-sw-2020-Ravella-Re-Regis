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
                            .getScaledInstance(300, 201, Image.SCALE_DEFAULT);
                    powerLabel = new JLabel(new ImageIcon(image));

                    this.add(powerLabel);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
    }

    public GamePanel(JPanel board){
        this.setLayout(new GridBagLayout());

        GridBagConstraints gridBagConstraints = setConstraints(0, 0, 1, 0.9);
        this.add(board, gridBagConstraints);

        gridBagConstraints = setConstraints(0, 1, 1, 0.1);
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
