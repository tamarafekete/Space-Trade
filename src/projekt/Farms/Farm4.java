package projekt.Farms;

import projekt.Crops.Crop;
import projekt.Crops.Shadestalker;
import projekt.Crops.Spikemaws;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Farm4 implements Farm {
    private BufferedImage backGround;
    private Clip clip;

    public Farm4() throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        backGround = ImageIO.read(new File("img/farm4.jpg"));
        AudioInputStream audio = AudioSystem.getAudioInputStream(new File("music/Farm4.wav"));
        clip = AudioSystem.getClip();
        clip.open(audio);
    }

    @Override
    public BufferedImage getBackGround() {
        return backGround;
    }

    @Override
    public Integer getFieldPrice() {
        return 200;
    }

    @Override
    public String getName() {
        return "Farm4";
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
        if (index == 0) return new Spikemaws();
        else return new Shadestalker();
    }
}
