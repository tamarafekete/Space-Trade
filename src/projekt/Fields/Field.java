package projekt.Fields;

import javax.swing.*;
import java.awt.image.BufferedImage;

public interface Field {
    BufferedImage getImg();

    JButton getAction();

    JMenuItem getPlantItem(int index);

    JMenuBar getMenuBar();

    void setActionLocation();

    Integer buy(Integer money);

    int getXX();

    int getYY();

    int getWidth();

    int getHeight();
}
