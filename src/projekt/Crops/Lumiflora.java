package projekt.Crops;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Lumiflora implements Crop {
    private BufferedImage img;

    public Lumiflora() throws IOException {
        img = ImageIO.read(new File("img/Lumiflora.png"));
    }

    @Override
    public Integer getPrice() {
        return 17;
    }

    @Override
    public Integer getSeedPrice() {
        return 6;
    }

    @Override
    public BufferedImage getImage() {
        return img;
    }

    @Override
    public int getHarvestTime() {
        return 95;
    }

    @Override
    public String getName() {
        return "Lumiflora";
    }
}
