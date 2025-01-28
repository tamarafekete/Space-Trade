package projekt;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public interface PanelSwitcher {
    void switchPanel(String panelName, Inventory inventory) throws IOException, UnsupportedAudioFileException, LineUnavailableException;

    void switchPanel(String panelName, int ind, Inventory inventory) throws IOException, UnsupportedAudioFileException, LineUnavailableException;

    void switchPanel(String panelName);
}
