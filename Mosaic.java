import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.util.Random;
import java.util.ArrayList;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;

class MosaicGridPanel extends JPanel {
    private int red, green, blue;
    private String letter;
    private Color letterColor;
    private boolean circle;
    private boolean face;

    private static final Random myRandom = new Random();

    MosaicGridPanel() {
        super();
        SetRandomValues();

        // Respond to a click on a shape or face.
        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                face = !face;
                repaint();
            }
        });
    }

    final public void SetRandomValues() {
        red = GetNumberBetween(0, 255);
        green = GetNumberBetween(0, 255);
        blue = GetNumberBetween(0, 255);

        // Choose a random capital letter.
        letter = String.valueOf(
            (char) ('A' + GetNumberBetween(0, 25))
        );

        // Choose a circle or square.
        circle = GetNumberBetween(0, 1) == 1;

        // Choose a contrasting letter color.
        letterColor = new Color(
            GetContrastingColor(red),
            GetContrastingColor(green),
            GetContrastingColor(blue)
        );

        face = false;
        repaint();
    }

    private static int GetNumberBetween(int min, int max) {
        return min + myRandom.nextInt(max - min + 1);
    }

    private static int GetContrastingColor(int colorIn) {
        return (colorIn + 128) % 256;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int panelWidth = getWidth();
        int panelHeight = getHeight();

        // Calculate the shape size when the window is resized.
        int shapeSize = (int) (
            Math.min(panelWidth, panelHeight) * 0.75
        );

        int shapeX = (panelWidth - shapeSize) / 2;
        int shapeY = (panelHeight - shapeSize) / 2;

        // Draw a face if the tile has been clicked.
        if (face) {
            FaceDraw.drawFace(
                g,
                panelWidth / 2,
                panelHeight / 2,
                shapeSize
            );
            return;
        }

        // Draw the randomly colored circle or square.
        g.setColor(new Color(red, green, blue));

        if (circle) {
            g.fillOval(
                shapeX, shapeY, shapeSize, shapeSize
            );
        } else {
            g.fillRect(
                shapeX, shapeY, shapeSize, shapeSize
            );
        }

        // Draw the capital letter at the exact center.
        int fontSize = Math.max(10, shapeSize / 3);

        g.setFont(new Font(
            "TimesRoman", Font.PLAIN, fontSize
        ));

        g.setColor(letterColor);

        FontMetrics fm = g.getFontMetrics();

        int stringX =
            (panelWidth - fm.stringWidth(letter)) / 2;

        int stringY =
            (panelHeight - fm.getHeight()) / 2
            + fm.getAscent();

        g.drawString(letter, stringX, stringY);
    }
}

class MosaicFrame extends JFrame implements ActionListener {
    private ArrayList<MosaicGridPanel> tileList;

    public MosaicFrame() {
        setTitle("Mosaic");
        setBounds(200, 200, 1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        // Create the Randomize button.
        JPanel buttonPanel = new JPanel();
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        JButton randomize = new JButton("Randomize");
        buttonPanel.add(randomize);
        randomize.addActionListener(this);

        // Create the 12x12 grid.
        JPanel mosaicGridPanel = new JPanel();
        contentPane.add(mosaicGridPanel, BorderLayout.CENTER);
        mosaicGridPanel.setLayout(new GridLayout(12, 12));

        // Store all 144 tiles in an ArrayList.
        tileList = new ArrayList<MosaicGridPanel>();

        for (int i = 0; i < 144; i++) {
            MosaicGridPanel tile = new MosaicGridPanel();
            tileList.add(tile);
            mosaicGridPanel.add(tile);
        }
    }

    // Randomize all shapes, letters, and colors.
    public void actionPerformed(ActionEvent e) {
        for (MosaicGridPanel tile : tileList) {
            tile.SetRandomValues();
        }

        repaint();
    }
}

public class Mosaic {
    public static void main(String[] args) {
        System.out.println("Mosaic Starting up...");

        MosaicFrame myMosaicFrame = new MosaicFrame();
        myMosaicFrame.setVisible(true);
    }
}
