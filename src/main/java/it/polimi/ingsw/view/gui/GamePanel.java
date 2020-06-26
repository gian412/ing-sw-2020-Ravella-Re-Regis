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

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
        this.add(board, gridBagConstraints);

        gridBagConstraints.anchor = GridBagConstraints.LAST_LINE_START;
        this.add(new powerPanel(StaticFrame.getGod()));
    }
}
