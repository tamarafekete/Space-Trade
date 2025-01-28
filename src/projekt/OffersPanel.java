package projekt;

import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class OffersPanel extends JPanel {
    Inventory inventory;
    private final BufferedImage backGround;
    private final JTextField moneyLabel;
    private InventoryMenu invBar;
    private final JButton back;
    private final Offer[] offers;
    private final OfferView[] offerViews;

    public OffersPanel(Inventory inventory1, PanelSwitcher switcher) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        inventory = new Inventory(inventory1);
        backGround = ImageIO.read(new File("img/background.jpg"));
        moneyLabel = new JTextField("Money: " + inventory.getMoney(), SwingConstants.CENTER);
        moneyLabel.setEditable(false);
        back = new JButton("Back");

        setLayout(null);
        repaint();

        moneyLabel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        moneyLabel.setSize(150, 50);
        moneyLabel.setFont(new Font(Font.SERIF, Font.BOLD, 20));
        moneyLabel.setBackground(new Color(171, 195, 167));
        moneyLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(moneyLabel);

        back.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        back.setSize(150, 50);
        back.setFont(new Font(Font.SERIF, Font.BOLD, 20));
        back.setBackground(Color.lightGray);
        back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try {
                    switcher.switchPanel("planets", inventory);
                } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        add(back);

        invBar = new InventoryMenu(inventory);
        add(invBar);

        offers = new Offer[5];
        for (int i = 0; i < 5; i++) {
            offers[i] = new Offer(inventory.getPlanets());
        }
        offerViews = new OfferView[5];
        for (int i = 0; i < 5; i++) {
            offerViews[i] = new OfferView(offers[i]);
            offerViews[i].setSellBackGround(offers[i].isAvailable(inventory));
            add(offerViews[i]);
        }

        setButtons();

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
        back.setLocation(550, 0);
        invBar.setLocation(moneyLabel.getWidth(), 0);
        int x = 150, y = 75;
        for (int i = 0; i < 5; i++) {
            offerViews[i].setLocation(x, y + i * 80);
            offerViews[i].setSellLocation();
        }
    }

    @Override
    protected void paintComponent(Graphics gr) {
        gr.drawImage(backGround, 0, 0, getWidth(), getHeight(), null);
    }

    public void setInventory(Inventory inventory) throws IOException {
        this.inventory = new Inventory(inventory);
        for (int i = 0; i < offerViews.length; i++) {
            offerViews[i].setSellBackGround(offers[i].isAvailable(inventory));
        }
        remove(invBar);
        invBar = new InventoryMenu(this.inventory);
        moneyLabel.setText("Money: " + this.inventory.getMoney());
        add(invBar);
        setButtons();
        repositionComponents();
    }

    private void sell(int ind) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        remove(offerViews[ind].getSell());
        remove(offerViews[ind]);
        setInventory(offers[ind].sellOffer(inventory));
        offers[ind] = new Offer(inventory.getPlanets());
        offerViews[ind] = new OfferView(offers[ind]);
        add(offerViews[ind]);
        setButtons();
        repositionComponents();
        revalidate();
        repaint();
    }

    private void setButtons() {
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            offerViews[i].setSellBackGround(offers[i].isAvailable(inventory));
            for (MouseListener ml : offerViews[i].getSell().getMouseListeners()) {
                offerViews[i].getSell().removeMouseListener(ml);
            }
            if (offers[i].isAvailable(inventory)) {
                offerViews[i].getSell().addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);
                        try {
                            sell(finalI);
                        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                });
            }
        }
    }
}


