package projekt.Crops;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Vaporweed implements Crop {
    private BufferedImage img;

    public Vaporweed() throws IOException {
        img = ImageIO.read(new File("img/Vaporweed.png"));
    }

    @Override
    public Integer getPrice() {
        return 25;
    }

    @Override
    public Integer getSeedPrice() {
        return 16;
    }

    @Override
    public BufferedImage getImage() {
        return img;
    }

    @Override
    public int getHarvestTime() {
        return 150;
    }

    @Override
    public String getName() {
        return "Vaporweed";
    }
}
