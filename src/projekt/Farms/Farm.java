package projekt.Farms;

import projekt.Crops.Crop;

import java.awt.image.BufferedImage;
import java.io.IOException;

public interface Farm {
    BufferedImage getBackGround();

    Integer getFieldPrice();

    String getName();

    void playMusic();

    void stopMusic();

    Crop getCrop(int index) throws IOException;
}
