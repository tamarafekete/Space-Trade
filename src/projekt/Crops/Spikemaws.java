package projekt.Crops;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Spikemaws implements Crop {
    private BufferedImage img;

    public Spikemaws() throws IOException {
        img = ImageIO.read(new File("img/Spikemaws.png"));
    }

    @Override
    public Integer getPrice() {
        return 35;
    }

    @Override
    public Integer getSeedPrice() {
        return 20;
    }

    @Override
    public BufferedImage getImage() {
        return img;
    }

    @Override
    public int getHarvestTime() {
        return 215;
    }

    @Override
    public String getName() {
        return "Spikemaws";
    }
}
