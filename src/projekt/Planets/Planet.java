package projekt.Planets;

import javax.swing.*;
import java.awt.image.BufferedImage;

public interface Planet {
    BufferedImage getPlanetImg();

    JButton getAction();

    void setActionLocation();

    Integer getPrice();

    Integer buy(Integer money);
}
