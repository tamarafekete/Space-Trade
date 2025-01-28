package projekt.Fields;

import projekt.Farms.Farm;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class NotPurchasedField implements Field {
    private BufferedImage img;
    private JButton action;
    private int x, y;
    private int width, height;
    private Farm farm;

    public NotPurchasedField(int x, int y, Farm farm) throws IOException {
        this.x = x;
        this.y = y;
        this.farm = farm;
        img = ImageIO.read(new File("img/fieldFenceNotP.png"));
        width = 300;
        height = width * img.getHeight() / img.getWidth();
        //height = 150;
        action = new JButton("Buy: " + farm.getFieldPrice());
        action.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        action.setSize(100, 35);
        action.setFont(new Font(Font.SERIF, Font.BOLD, 20));
        action.setBackground(new Color(171, 195, 167));
    }

    @Override
    public BufferedImage getImg() {
        return img;
    }

    @Override
    public JButton getAction() {
        return action;
    }

    @Override
    public JMenuItem getPlantItem(int index) {
        return null;
    }

    @Override
    public JMenuBar getMenuBar() {
        return null;
    }

    @Override
    public void setActionLocation() {
        action.setLocation(x + width / 2 - action.getWidth() / 2, y + height / 2 - action.getHeight() / 2);
    }

    @Override
    public Integer buy(Integer money) {
        return money - farm.getFieldPrice();
    }

    @Override
    public int getXX() {
        return x;
    }

    @Override
    public int getYY() {
        return y;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }
}
