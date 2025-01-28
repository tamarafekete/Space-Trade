package projekt.Crops;

import java.awt.image.BufferedImage;

public interface Crop {
    Integer getPrice();

    Integer getSeedPrice();

    BufferedImage getImage();

    int getHarvestTime();

    String getName();
}
