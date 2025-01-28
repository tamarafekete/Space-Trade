package projekt.Crops;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Shadestalker implements Crop {
    private BufferedImage img;

    public Shadestalker() throws IOException {
        img = ImageIO.read(new File("img/Shadestalker.png"));
    }

    @Override
    public Integer getPrice() {
        return 40;
    }

    @Override
    public Integer getSeedPrice() {
        return 26;
    }

    @Override
    public BufferedImage getImage() {
        return img;
    }

    @Override
    public int getHarvestTime() {
        return 255;
    }

    @Override
    public String getName() {
        return "Shadestalker";
    }
}
