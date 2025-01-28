package projekt;

import projekt.Counter.Counter;
import projekt.Counter.CounterControl;
import projekt.Counter.CounterView;
import projekt.Crops.Crop;
import projekt.Farms.Farm;
import projekt.Fields.Field;
import projekt.Fields.NotPurchasedField;
import projekt.Fields.PurchasedField;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class FarmPanel extends JPanel {
    private final Farm farm;
    private Inventory inventory;
    private final JTextField moneyLabel;
    private final JButton back;
    private final JButton[] harvest;
    private final Field[] fields;
    private final Counter[] counters;
    private final CounterView[] counterViews;
    private final CounterControl[] counterControls;
    private InventoryMenu invBar;
    private final Clip plantSound;
    private final Clip buySound;

    public FarmPanel(Farm farm1, Inventory inventory1, PanelSwitcher switcher) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        farm = farm1;
        inventory = new Inventory(inventory1);

        moneyLabel = new JTextField("Money: " + inventory.getMoney(), SwingConstants.CENTER);
        moneyLabel.setEditable(false);
        back = new JButton("Back");

        AudioInputStream audio = AudioSystem.getAudioInputStream(new File("music/plant.wav"));
        plantSound = AudioSystem.getClip();
        plantSound.open(audio);
        audio = AudioSystem.getAudioInputStream(new File("music/buy.wav"));
        buySound = AudioSystem.getClip();
        buySound.open(audio);

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

        fields = new Field[4];
        if (inventory.getFieldI(0, farm))
            fields[0] = new PurchasedField(15, 375, farm);
        else
            fields[0] = new NotPurchasedField(15, 375, farm);
        if (inventory.getFieldI(1, farm))
            fields[1] = new PurchasedField(365, 375, farm);
        else
            fields[1] = new NotPurchasedField(365, 375, farm);
        if (inventory.getFieldI(2, farm))
            fields[2] = new PurchasedField(15, 225, farm);
        else
            fields[2] = new NotPurchasedField(15, 225, farm);
        if (inventory.getFieldI(3, farm))
            fields[3] = new PurchasedField(365, 225, farm);
        else
            fields[3] = new NotPurchasedField(365, 225, farm);

        for (int i = 0; i < 4; i++) {
            if (!inventory.getFieldI(i, farm))
                add(fields[i].getAction());
            else
                add(fields[i].getMenuBar());
        }

        harvest = new JButton[4];
        for (int i = 0; i < 4; i++) {
            harvest[i] = new JButton("Harvest");
            harvest[i].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
            harvest[i].setSize(100, 35);
            harvest[i].setFont(new Font(Font.SERIF, Font.BOLD, 15));
            harvest[i].setBackground(Color.lightGray);
        }

        counters = new Counter[4];
        counterControls = new CounterControl[4];
        counterViews = new CounterView[4];

        for (int i = 0; i < 4; i++) {
            if (!inventory.getFieldI(i, farm)) {
                int finalI = i;
                fields[i].getAction().addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);
                        if (inventory.getMoney() >= farm.getFieldPrice()) {
                            buySound.setFramePosition(0);
                            buySound.start();
                            inventory.setMoney(inventory.getMoney() - farm.getFieldPrice());
                            moneyLabel.setText("Money: " + inventory.getMoney());
                            inventory.setFieldI(true, finalI, farm);
                            if (finalI == 0) {
                                try {
                                    replaceField(finalI, new PurchasedField(15, 375, farm));
                                } catch (IOException ex) {
                                    throw new RuntimeException(ex);
                                }
                            } else if (finalI == 1) {
                                try {
                                    replaceField(finalI, new PurchasedField(365, 375, farm));
                                } catch (IOException ex) {
                                    throw new RuntimeException(ex);
                                }
                            } else if (finalI == 2) {
                                try {
                                    replaceField(finalI, new PurchasedField(15, 225, farm));
                                } catch (IOException ex) {
                                    throw new RuntimeException(ex);
                                }
                            } else {
                                try {
                                    replaceField(finalI, new PurchasedField(365, 225, farm));
                                } catch (IOException ex) {
                                    throw new RuntimeException(ex);
                                }
                            }
                        }
                    }
                });
            } else {
                int finalI = i;
                for (int j = 0; j < 2; j++) {
                    int finalJ = j;
                    fields[i].getPlantItem(j).addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            remove(fields[finalI].getMenuBar());
                            plantSound.setFramePosition(0);
                            plantSound.start();
                            try {
                                Crop newCrop = farm.getCrop(finalJ);
                                counters[finalI] = new Counter(newCrop.getHarvestTime());
                                counterViews[finalI] = new CounterView(counters[finalI], fields[finalI].getMenuBar().getX(), fields[finalI].getMenuBar().getY(), newCrop);
                                counterViews[finalI].setBounds(fields[finalI].getMenuBar().getX(), fields[finalI].getMenuBar().getY(), 100, 50);
                                counterControls[finalI] = new CounterControl(counters[finalI], counterViews[finalI]);
                                add(counterViews[finalI]);
                                counterControls[finalI].start(farm.getCrop(finalJ), FarmPanel.this, finalI, fields[finalI]);
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                            repaint();
                        }
                    });
                }
            }
        }

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
        for (int i = 0; i < 4; i++) {
            fields[i].setActionLocation();
            if (fields[i].getMenuBar() != null) harvest[i].setLocation(fields[i].getMenuBar().getLocation());
        }
        invBar.setLocation(moneyLabel.getWidth(), 0);
    }

    @Override
    public void paintComponent(Graphics gr) {
        super.paintComponent(gr);
        gr.drawImage(farm.getBackGround(), 0, 0, getWidth(), getHeight(), null);
        for (int i = 0; i < 4; i++) {
            gr.drawImage(fields[i].getImg(), fields[i].getXX(), fields[i].getYY(), fields[i].getWidth(), fields[i].getHeight(), null);
        }
    }

    private void replaceField(int index, Field newField) {
        try {
            remove(fields[index].getAction());
            fields[index] = newField;
            for (int i = 0; i < 2; i++) {
                int finalI = i;
                fields[index].getPlantItem(i).addActionListener(e -> {
                    remove(fields[index].getMenuBar());
                    plantSound.setFramePosition(0);
                    plantSound.start();
                    try {
                        Crop newCrop = farm.getCrop(finalI);
                        counters[index] = new Counter(newCrop.getHarvestTime());
                        counterViews[index] = new CounterView(counters[index], fields[index].getMenuBar().getX(), fields[index].getMenuBar().getY(), newCrop);
                        counterViews[index].setBounds(fields[index].getMenuBar().getX(), fields[index].getMenuBar().getY(), 100, 50);
                        counterControls[index] = new CounterControl(counters[index], counterViews[index]);
                        add(counterViews[index]);
                        counterControls[index].start(farm.getCrop(finalI), this, index, fields[index]);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    repaint();
                });
            }
            add(fields[index].getMenuBar());
            revalidate();
            repaint();
            repositionComponents();
        } catch (Exception ex) {
            throw new RuntimeException("Error replacing planet at index " + index, ex);
        }
    }

    public void setFieldBack(int index, Field field, Crop crop) throws IOException {
        remove(counterViews[index]);
        for (MouseListener ml : harvest[index].getMouseListeners()) {
            harvest[index].removeMouseListener(ml);
        }
        harvest[index].setIcon(new ImageIcon(crop.getImage()));
        harvest[index].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                remove(harvest[index]);
                fields[index] = field;
                inventory.setCrops(crop.getName(), inventory.getCrop(crop.getName()) + 1);
                try {
                    invBar.setInvItem(crop, inventory.getCrop(crop.getName()));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                add(fields[index].getMenuBar());
                revalidate();
                repaint();
                repositionComponents();
            }
        });
        add(harvest[index]);
        revalidate();
        repaint();
        repositionComponents();
    }

    public void setInventory(Inventory inventory) throws IOException {
        this.inventory = new Inventory(inventory);
        moneyLabel.setText("Money: " + this.inventory.getMoney());
        remove(invBar);
        invBar = new InventoryMenu(this.inventory);
        add(invBar);
        repositionComponents();
    }
}
