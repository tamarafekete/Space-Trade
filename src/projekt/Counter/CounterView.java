package projekt.Counter;

import projekt.Crops.Crop;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

public class CounterView extends JPanel {
    private final Counter counter;
    private final JButton label;
    private int y;

    public CounterView(Counter counter, int x, int y, Crop crop) {
        this.counter = counter;
        this.y = y;
        setLayout(null);
        label = new JButton();
        if(counter.getSeconds()>60) {
            if (counter.getSeconds() % 60 > 9)
                label.setText(counter.getSeconds() / 60 + ":" + counter.getSeconds() % 60);
            else
                label.setText(counter.getSeconds() / 60 + ":0" + counter.getSeconds() % 60);
        }
        else
            label.setText(counter.getSeconds()+" s");
        label.setBackground(Color.lightGray);
        label.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        label.setFont(new Font(Font.SERIF, Font.BOLD, 20));
        label.setBounds(0, 0, 100, 50);
        add(label);
        setBounds(x, y, 100, 50);
        ImageIcon icon = new ImageIcon(crop.getImage());
        label.setIcon(icon);
    }

    public void paintComponent(Graphics gr){
        System.out.println("repaint");
        super.paintComponent(gr);
        if(counter.getSeconds()>60) {
            if (counter.getSeconds() % 60 > 9)
                label.setText(counter.getSeconds() / 60 + ":" + counter.getSeconds() % 60);
            else
                label.setText(counter.getSeconds() / 60 + ":0" + counter.getSeconds() % 60);
        }
        else
            label.setText(counter.getSeconds()+" s");
        System.out.println("repainting... "+counter.getSeconds());
    }

    @Override
    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }


}
