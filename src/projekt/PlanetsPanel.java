package projekt;

import projekt.Planets.*;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PlanetsPanel extends JPanel {
    private Planet[] planets;
    private BufferedImage backGround;
    private JTextField moneyLabel;
    private Inventory inventory;
    private PanelSwitcher switcher;
    private InventoryMenu invBar;
    private JButton saveGame;
    private JButton exit;
    private JButton offers;
    private Clip buySound;

    public PlanetsPanel(Inventory inventory1, PanelSwitcher switcher) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        this.switcher = switcher;
        inventory = inventory1;
        backGround = ImageIO.read(new File("img/background.jpg"));
        AudioInputStream audio = AudioSystem.getAudioInputStream(new File("music/buy.wav"));
        buySound = AudioSystem.getClip();
        buySound.open(audio);
        planets = new Planet[4];
        planets[0] = new Planet1Purchased();
        if (inventory.getPlanets()[0]) {
            planets[1] = new Planet2Purchased();
        } else {
            planets[1] = new Planet2NotPurchased();
        }
        if (inventory.getPlanets()[1]) {
            planets[2] = new Planet3Purchased();
        } else {
            planets[2] = new Planet3NotPurchased();
        }
        if (inventory.getPlanets()[2]) {
            planets[3] = new Planet4Purchased();
        } else {
            planets[3] = new Planet4NotPurchased();
        }
        moneyLabel = new JTextField("Money: " + inventory.getMoney(), SwingConstants.CENTER);
        moneyLabel.setEditable(false);


        setLayout(null);
        repaint();

        moneyLabel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        moneyLabel.setSize(150, 50);
        moneyLabel.setFont(new Font(Font.SERIF, Font.BOLD, 20));
        moneyLabel.setBackground(new Color(171, 195, 167));
        moneyLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(moneyLabel);

        saveGame = new JButton("Save");
        saveGame.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        saveGame.setSize(150, 50);
        saveGame.setFont(new Font(Font.SERIF, Font.BOLD, 20));
        saveGame.setBackground(Color.lightGray);
        saveGame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JFileChooser j = new JFileChooser("C:/Tamara/Labor/Java/JavaProjekt/saves", FileSystemView.getFileSystemView());
                j.setAcceptAllFileFilterUsed(false);
                FileNameExtensionFilter restrict = new FileNameExtensionFilter("dat files", "dat");
                j.addChoosableFileFilter(restrict);
                int r = j.showSaveDialog(null);
                if (r == JFileChooser.APPROVE_OPTION) {
                    inventory.saveToFile(j.getSelectedFile());
                }

            }
        });
        add(saveGame);

        exit = new JButton("Exit");
        exit.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        exit.setSize(150, 50);
        exit.setFont(new Font(Font.SERIF, Font.BOLD, 20));
        exit.setBackground(Color.lightGray);
        exit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                switcher.switchPanel("menu");
            }
        });
        add(exit);

        offers = new JButton("Offers");
        offers.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        offers.setSize(125, 50);
        offers.setFont(new Font(Font.SERIF, Font.BOLD, 20));
        offers.setBackground(new Color(171, 195, 167));
        offers.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try {
                    switcher.switchPanel("offers", inventory);
                } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        add(offers);

        planets[0].getAction().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try {
                    switcher.switchPanel("farm", 0, inventory);
                } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        for (int i = 0; i < 4; i++) {
            add(planets[i].getAction());
        }

        for (int i = 1; i < 4; i++) {
            if (!inventory.getPlanets()[i - 1]) {
                int finalI = i;
                planets[i].getAction().addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);
                        if (inventory.getMoney() >= planets[finalI].getPrice()) {
                            buySound.setFramePosition(0);
                            buySound.start();
                            inventory.setMoney(inventory.getMoney() - planets[finalI].getPrice());
                            moneyLabel.setText("Money: " + inventory.getMoney());
                            inventory.setPlanetI(true, finalI - 1);
                            if (finalI == 1) {
                                try {
                                    replacePlanet(finalI, new Planet2Purchased());
                                } catch (IOException ex) {
                                    throw new RuntimeException(ex);
                                }
                            } else if (finalI == 2) {
                                try {
                                    replacePlanet(finalI, new Planet3Purchased());
                                } catch (IOException ex) {
                                    throw new RuntimeException(ex);
                                }
                            } else {
                                try {
                                    replacePlanet(finalI, new Planet4Purchased());
                                } catch (IOException ex) {
                                    throw new RuntimeException(ex);
                                }
                            }
                        }
                    }
                });
            } else {
                int finalI = i;
                planets[i].getAction().addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);
                        try {
                            switcher.switchPanel("farm", finalI, inventory);
                        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                });
            }
        }

        invBar = new InventoryMenu(inventory);
        add(invBar);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                repositionComponents();
            }
        });

        repositionComponents();
    }

    private void repositionComponents() {
        moneyLabel.setLocation(0, 0);
        for (int i = 0; i < 4; i++) {
            planets[i].setActionLocation();
        }
        invBar.setLocation(moneyLabel.getWidth(), 0);
        exit.setLocation(550, 0);
        saveGame.setLocation(400, 0);
        offers.setLocation(moneyLabel.getWidth() + invBar.getWidth(), 0);
    }

    @Override
    protected void paintComponent(Graphics gr) {
        super.paintComponent(gr);
        gr.drawImage(backGround, 0, 0, getWidth(), getHeight(), null);

        gr.drawImage(planets[0].getPlanetImg(), 150, 285, 160, 150, null);
        gr.drawImage(planets[1].getPlanetImg(), 500, 280, 150, 150, null);
        gr.drawImage(planets[2].getPlanetImg(), 70, 80, 170, 170, null);
        gr.drawImage(planets[3].getPlanetImg(), 420, 60, 160, 150, null);
        //gr.setColor(Color.GRAY);
        //gr.drawRect(getWidth()/2-250, getHeight()/2-150, 500, 300);
    }

    private void replacePlanet(int index, Planet newPlanet) {
        try {
            remove(planets[index].getAction());
            planets[index] = newPlanet;
            planets[index].getAction().addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    int ind = index;
                    try {
                        switcher.switchPanel("farm", ind, inventory);
                    } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
                        throw new RuntimeException(ex);
                    }

                }
            });
            add(planets[index].getAction());
            repaint();
            repositionComponents();
        } catch (Exception ex) {
            throw new RuntimeException("Error replacing planet at index " + index, ex);
        }
    }

    public void setInventory(Inventory inventory) throws IOException {
        this.inventory = new Inventory(inventory);
        remove(invBar);
        invBar = new InventoryMenu(this.inventory);
        add(invBar);
        moneyLabel.setText("Money: " + inventory.getMoney());
        repositionComponents();
    }
}
