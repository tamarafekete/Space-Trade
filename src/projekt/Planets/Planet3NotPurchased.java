package projekt.Planets;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Planet3NotPurchased implements Planet {
    private final BufferedImage planetImg;
    private final JButton action;
    private final Integer price;

    public Planet3NotPurchased() throws IOException {
        planetImg = ImageIO.read(new File("img/Planet3NotPurchased.png"));
        price = 400;
        action = new JButton("Buy: " + price);
        action.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        action.setSize(100, 35);
        action.setFont(new Font(Font.SERIF, Font.BOLD, 20));
        action.setBackground(new Color(171, 195, 167));

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
        action.setLocation(105, 210);
    }

    public Integer getPrice() {
        return price;
    }

    @Override
    public Integer buy(Integer money) {
        return money;
    }
}
