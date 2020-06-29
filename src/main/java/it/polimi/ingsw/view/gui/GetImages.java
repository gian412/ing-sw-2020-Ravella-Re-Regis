package it.polimi.ingsw.view.gui;


import it.polimi.ingsw.utils.GodType;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class GetImages {

    public static Image img;

    static {
        try {
            img = ImageIO.read(GetImages.class.getClassLoader().getResource("Apollo.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Image getImage(String actualGod) throws IOException {

        return img;
    }

    /*public static void main (String[] args) {
        System.out.println(img.toString());
    }*/

}
