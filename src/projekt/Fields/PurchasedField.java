package projekt.Fields;

import projekt.Farms.Farm;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PurchasedField implements Field {
    private final BufferedImage img;
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private final JMenuBar menuBar;
    private final JMenuItem[] menuItem;

    public PurchasedField(int x, int y, Farm farm) throws IOException {
        this.x = x;
        this.y = y;
        img = ImageIO.read(new File("img/fieldFence.png"));
        width = 300;
        height = width * img.getHeight() / img.getWidth();
        menuBar = new JMenuBar();
        JMenu menu = new JMenu("Plant");
        menuBar.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        menuBar.setSize(100, 35);
        menu.setFont(new Font(Font.SERIF, Font.BOLD, 20));
        menuBar.setBackground(Color.lightGray);
        menuBar.setLocation(x + width / 2 - menu.getWidth() / 2, y + height / 2 - menu.getHeight() / 2);
        menuItem = new JMenuItem[2];
        for (int i = 0; i < 2; i++) {
            menuItem[i] = new JMenuItem(farm.getCrop(i).getName(), new ImageIcon(farm.getCrop(i).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
            menuItem[i].setFont(new Font(Font.SERIF, Font.BOLD, 20));

        }
        menuItem[0] = new JMenuItem(farm.getCrop(0).getName(), new ImageIcon(farm.getCrop(0).getImage()));
        menuItem[1] = new JMenuItem(farm.getCrop(1).getName(), new ImageIcon(farm.getCrop(1).getImage()));
        menu.add(menuItem[0]);
        menu.add(menuItem[1]);
        menuBar.add(menu);

    }

    @Override
    public BufferedImage getImg() {
        return img;
    }

    @Override
    public JButton getAction() {
        return null;
    }

    @Override
    public JMenuItem getPlantItem(int index) {
        if (index == 0) return menuItem[0];
        else return menuItem[1];
    }

    @Override
    public JMenuBar getMenuBar() {
        return menuBar;
    }

    @Override
    public void setActionLocation() {
        menuBar.setLocation(x + width / 2 - menuBar.getWidth() / 2, y + height / 2 - menuBar.getHeight() / 2);
    }

    @Override
    public Integer buy(Integer money) {
        return money;
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
