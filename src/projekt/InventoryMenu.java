package projekt;

import projekt.Crops.*;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.io.IOException;

public class InventoryMenu extends JMenuBar {
    private JMenu invMenu;
    private JMenuItem[] invMenuItem;
    private Crop[] invCrops;

    public InventoryMenu(Inventory inventory) throws IOException {
        setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        setSize(125, 50);

        setBackground(new Color(171, 195, 167));
        invMenu = new JMenu("Inventory");
        invMenu.setHorizontalAlignment(SwingConstants.CENTER);
        invMenu.setSize(150, 150);
        invMenu.setFont(new Font(Font.SERIF, Font.BOLD, 20));
        invCrops = new Crop[8];
        invCrops[0] = new Wheat();
        invCrops[1] = new Potato();
        invCrops[2] = new Aerobranch();
        invCrops[3] = new Lumiflora();
        invCrops[4] = new Graviroot();
        invCrops[5] = new Vaporweed();
        invCrops[6] = new Spikemaws();
        invCrops[7] = new Shadestalker();
        invMenuItem = new JMenuItem[8];
        for(int i=0;i<8;i++){
            invMenuItem[i] = new JMenuItem(": "+ inventory.getCrop(invCrops[i].getName()), new ImageIcon(invCrops[i].getImage()));
            invMenuItem[i].setFont(new Font(Font.SERIF, Font.PLAIN, 20));
            invMenuItem[i].setSize(150, 100);
            invMenuItem[i].setFocusPainted(false);
            invMenuItem[i].setRolloverEnabled(false);
            invMenu.add(invMenuItem[i]);
        }

        add(invMenu);
    }

    void setInvItem(Crop crop, int count) throws IOException {
        for(int i=0;i<2;i++){
            if(crop.getName().equals(invCrops[i].getName())){
                invMenuItem[i].setText(": "+count);
            }
        }

    }
}
