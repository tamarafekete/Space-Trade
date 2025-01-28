package projekt.Counter;

import projekt.Crops.Crop;
import projekt.FarmPanel;
import projekt.Fields.Field;

import javax.swing.*;
import java.io.IOException;

public class CounterControl {
    private final Counter counter;
    private final CounterView view;

    public CounterControl(Counter counter, CounterView view) {
        this.view = view;
        this.counter = counter;
    }

    public void start(Crop crop, FarmPanel farmPanel, int index, Field field) {
        Timer timer = new Timer(1000, e -> {
            if (counter.getSeconds() > 0) {
                counter.setSeconds(counter.getSeconds() - 1);
                view.repaint();
            } else {
                ((Timer) e.getSource()).stop();
                try {
                    SwingUtilities.invokeLater(() -> {
                        try {
                            farmPanel.setFieldBack(index, field, crop);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    });
                } catch (Exception ex) {
                    throw new RuntimeException("Error updating inventory", ex);
                }
            }
        });
        timer.start();
    }
}
