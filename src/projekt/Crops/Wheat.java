package projekt.Crops;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Wheat implements Crop {
    private final BufferedImage img;

    public Wheat() throws IOException {
        img = ImageIO.read(new File("img/wheat.png"));
    }

    @Override
    public Integer getPrice() {
        return 6;
    }

    @Override
    public Integer getSeedPrice() {
        return 0;
    }

    @Override
    public BufferedImage getImage() {
        return img;
    }

    @Override
    public int getHarvestTime() {
        return 25;
    }

    @Override
    public String getName() {
        return "Wheat";
    }
}
