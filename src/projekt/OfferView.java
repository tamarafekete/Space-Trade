package projekt;

import javax.swing.*;
import java.awt.*;

public class OfferView extends JPanel {
    private Offer offer;
    private JButton sell;

    public OfferView(Offer offer) {
        setLayout(null);
        this.offer = offer;
        setSize(400, 75);
        setBackground(Color.lightGray);
        setFont(new Font(Font.SERIF, Font.BOLD, 20));

        sell = new JButton("Sell: " + offer.price());
        sell.setSize(100, 50);
        sell.setFont(new Font(Font.SERIF, Font.BOLD, 20));
        add(sell);

        repaint();
        revalidate();
    }

    public JButton getSell() {
        return sell;
    }

    public void setSellBackGround(Boolean isAv) {
        if (isAv) {
            sell.setBackground(new Color(171, 195, 167));
            sell.setForeground(Color.BLACK);
        } else {
            sell.setBackground(Color.darkGray);
            sell.setForeground(Color.lightGray);
        }
    }

    public void setSellLocation() {
        sell.setLocation(287, 12);
    }

    @Override
    protected void paintComponent(Graphics gr) {
        super.paintComponent(gr);
        int x = 10, y = 5;
        for (int i = 0; i < offer.getCrops().length && i < 3; i++) {
            gr.drawImage(offer.getCrops()[i].getImage(), x + i * 40, y, null);
        }
        for (int i = 3; i < offer.getCrops().length; i++) {
            gr.drawImage(offer.getCrops()[i].getImage(), x + i * 40 - 120, y + 35, null);
        }
    }
}
