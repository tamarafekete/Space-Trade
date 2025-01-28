package projekt.Farms;

import projekt.Crops.Aerobranch;
import projekt.Crops.Crop;
import projekt.Crops.Lumiflora;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Farm2 implements Farm {
    private final BufferedImage backGround;
    private final Clip clip;

    public Farm2() throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        backGround = ImageIO.read(new File("img/farm2.jpg"));
        AudioInputStream audio = AudioSystem.getAudioInputStream(new File("music/Farm2.wav"));
        clip = AudioSystem.getClip();
        clip.open(audio);
    }

    @Override
    public BufferedImage getBackGround() {
        return backGround;
    }

    @Override
    public Integer getFieldPrice() {
        return 50;
    }

    @Override
    public String getName() {
        return "Farm2";
    }

    @Override
    public void playMusic() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    @Override
    public void stopMusic() {
        clip.stop();
    }

    @Override
    public Crop getCrop(int index) throws IOException {
        if (index == 0) return new Aerobranch();
        else return new Lumiflora();
    }
}
