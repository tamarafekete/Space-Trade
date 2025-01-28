package projekt;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainMenuPanel extends JPanel {
    private final BufferedImage background;
    private final JLabel name;
    private final JButton startGame;
    private final JButton loadGame;

    public MainMenuPanel() throws IOException {
        background = ImageIO.read(new File("img/background.jpg"));

        name = new JLabel("Space Trade", SwingConstants.CENTER);
        startGame = new JButton("Start new game");
        loadGame = new JButton("Load game");

        setLayout(null);
        repaint();
        name.setSize(300, 50);
        name.setFont(new Font(Font.SERIF, Font.BOLD, 40));
        name.setForeground(Color.CYAN);
        add(name);


        startGame.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        startGame.setSize(200, 50);
        startGame.setFont(new Font(Font.SERIF, Font.BOLD, 20));
        startGame.setBackground(Color.lightGray);
        add(startGame);
        loadGame.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        loadGame.setSize(200, 50);
        loadGame.setFont(new Font(Font.SERIF, Font.BOLD, 20));
        loadGame.setBackground(Color.LIGHT_GRAY);
        add(loadGame);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                repositionComponents();
            }
        });

        repositionComponents();
    }

    private void repositionComponents() {
        name.setLocation(
                (getWidth() - name.getWidth()) / 2,
                getHeight() / 4 - name.getHeight() / 2
        );

        startGame.setLocation(
                (getWidth() - startGame.getWidth()) / 2,
                getHeight() / 2 - startGame.getHeight() - 10
        );

        loadGame.setLocation(
                (getWidth() - loadGame.getWidth()) / 2,
                getHeight() / 2 + 10
        );
    }

    @Override
    protected void paintComponent(Graphics gr) {
        super.paintComponent(gr);
        gr.drawImage(background, 0, 0, getWidth(), getHeight(), null);
    }

    public JButton getStartGame() {
        return startGame;
    }

    public JButton getLoadGame() {
        return loadGame;
    }
}
