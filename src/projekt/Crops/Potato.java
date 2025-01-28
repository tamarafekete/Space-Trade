package projekt.Crops;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Potato implements Crop {
    private BufferedImage img;

    public Potato() throws IOException {
        img = ImageIO.read(new File("img/potato.png"));
    }

    @Override
    public Integer getPrice() {
        return 9;
    }

    @Override
    public Integer getSeedPrice() {
        return 3;
    }

    @Override
    public BufferedImage getImage() {
        return img;
    }

    @Override
    public int getHarvestTime() {
        return 40;
    }

    @Override
    public String getName() {
        return "Potato";
    }
}
