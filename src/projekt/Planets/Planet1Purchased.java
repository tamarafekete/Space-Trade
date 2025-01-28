package projekt.Planets;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Planet1Purchased implements Planet {
    private final BufferedImage planetImg;
    private final JButton action;

    public Planet1Purchased() throws IOException {
        planetImg = ImageIO.read(new File("img/Earth.png"));
        action = new JButton("Enter");
        action.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        action.setSize(100, 35);
        action.setFont(new Font(Font.SERIF, Font.BOLD, 20));
        action.setBackground(Color.lightGray);
    }

    @Override
    public BufferedImage getPlanetImg() {
        return planetImg;
    }

    @Override
    public JButton getAction() {
        return action;
    }

    @Override
    public void setActionLocation() {
        action.setLocation(180, 400);
    }

    public Integer getPrice() {
        return null;
    }

    @Override
    public Integer buy(Integer money) {
        return money;
    }


}
