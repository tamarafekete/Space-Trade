package projekt;

import projekt.Farms.*;

import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GameFrame extends JFrame implements PanelSwitcher {
    private PlanetsPanel planetsPanel;
    private final CardLayout layout;
    private final JPanel cardPanel;
    private final FarmPanel[] farmPanel;
    private final Farm[] farm;
    private OffersPanel offersPanel;

    public GameFrame() throws IOException {
        layout = new CardLayout();
        cardPanel = new JPanel();
        cardPanel.setLayout(layout);
        add(cardPanel);

        MainMenuPanel menuPanel = new MainMenuPanel();
        cardPanel.add(menuPanel, "menu");


        layout.show(cardPanel, "menu");
        PanelSwitcher th = this;
        menuPanel.getStartGame().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try {
                    planetsPanel = new PlanetsPanel(new Inventory(50, new Boolean[]{false, false, false}, new Boolean[][]{{true, false, false, false}, {true, false, false, false}, {true, false, false, false}, {true, false, false, false}}, null), th);
                } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
                    throw new RuntimeException(ex);
                }
                cardPanel.add(planetsPanel, "planets");
                layout.show(cardPanel, "planets");
            }
        });
        menuPanel.getLoadGame().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JFileChooser j = new JFileChooser("C:/Tamara/Labor/Java/Javaprojekt/saves", FileSystemView.getFileSystemView());
                j.setAcceptAllFileFilterUsed(false);
                FileNameExtensionFilter restrict = new FileNameExtensionFilter("dat files", "dat");
                j.addChoosableFileFilter(restrict);
                int r = j.showOpenDialog(null);
                if (r == JFileChooser.APPROVE_OPTION) {
                    try {
                        planetsPanel = new PlanetsPanel(new Inventory(j.getSelectedFile().getPath()), th);
                    } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                cardPanel.add(planetsPanel, "planets");
                layout.show(cardPanel, "planets");
            }
        });
        farm = new Farm[4];
        farmPanel = new FarmPanel[4];

        BufferedImage icon = ImageIO.read(new File("img/icon.png"));
        setIconImage(icon);
        setTitle("Space Trade");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(100, 100, 700, 500);
        setVisible(true);
        setResizable(false);
    }

    public static void main(String[] args) throws IOException {
        new GameFrame();
    }


    @Override
    public void switchPanel(String panelName, Inventory inventory) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        if (panelName.equals("planets")) {
            planetsPanel.setInventory(inventory);
            layout.show(cardPanel, "planets");
            for (int i = 0; i < 4; i++) {
                if (farm[i] != null) farm[i].stopMusic();
            }
        } else {
            if (offersPanel == null) {
                offersPanel = new OffersPanel(inventory, this);
                cardPanel.add(offersPanel, "offers");
            } else {
                offersPanel.setInventory(inventory);
            }
            layout.show(cardPanel, "offers");
        }

    }

    @Override
    public void switchPanel(String panelName, int ind, Inventory inventory) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        if (farmPanel[ind] == null) {
            if (ind == 0)
                farm[ind] = new Farm1();
            else if (ind == 1)
                farm[ind] = new Farm2();
            else if (ind == 2)
                farm[ind] = new Farm3();
            else
                farm[ind] = new Farm4();
            farmPanel[ind] = new FarmPanel(farm[ind], inventory, this);
            cardPanel.add(farmPanel[ind], panelName + ind);
        } else {
            farmPanel[ind].setInventory(inventory);
        }
        layout.show(cardPanel, panelName + ind);
        farm[ind].playMusic();
    }

    @Override
    public void switchPanel(String panelName) {
        layout.show(cardPanel, panelName);
        cardPanel.remove(planetsPanel);
        for (int i = 0; i < 4; i++) {
            if (farmPanel[i] != null) cardPanel.remove(farmPanel[i]);
        }
    }
}
