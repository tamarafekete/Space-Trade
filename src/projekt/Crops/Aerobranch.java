package projekt.Crops;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Aerobranch implements Crop {
    private BufferedImage img;

    public Aerobranch() throws IOException {
        img = ImageIO.read(new File("img/Aerobranch.png"));
    }

    @Override
    public Integer getPrice() {
        return 15;
    }

    @Override
    public Integer getSeedPrice() {
        return 5;
    }

    @Override
    public BufferedImage getImage() {
        return img;
    }

    @Override
    public int getHarvestTime() {
        return 75;
    }

    @Override
    public String getName() {
        return "Aerobranch";
    }
}
