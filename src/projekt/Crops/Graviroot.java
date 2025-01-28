package projekt.Crops;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Graviroot implements Crop {
    private BufferedImage img;

    public Graviroot() throws IOException {
        img = ImageIO.read(new File("img/Graviroot.png"));
    }

    @Override
    public Integer getPrice() {
        return 23;
    }

    @Override
    public Integer getSeedPrice() {
        return 12;
    }

    @Override
    public BufferedImage getImage() {
        return img;
    }

    @Override
    public int getHarvestTime() {
        return 135;
    }

    @Override
    public String getName() {
        return "Graviroot";
    }
}
