
import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.Canvas;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Panel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Random;

class MosaicGridPanel extends Canvas {
    private int red, green, blue;
    private String letter;
    private Color letterColor;
    private boolean isCircle;
    private boolean isFace;

    private final Random myRandom = new Random();

    MosaicGridPanel() {
        SetRandomValue();

        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                isFace = !isFace;
                repaint();
            }
        });
    }

    final public void SetRandomValue() {
        red = GetNumberBetween(0, 250);
        green = GetNumberBetween(0, 250);
        blue = GetNumberBetween(0, 250);

        letter = String.valueOf(
            (char) ('A' + GetNumberBetween(0, 25))
        );

        isCircle = GetNumberBetween(0, 1) == 1;
        isFace = false;

        letterColor = new Color(
            GetContrastingColor(red),
            GetContrastingColor(green),
            GetContrastingColor(blue)
        );

        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        int panelWidth = getWidth();
        int panelHeight = getHeight();

        int size = Math.max(
            1, (int) (Math.min(panelWidth, panelHeight) * 0.75)
        );

        int x = (panelWidth - size) / 2;
        int y = (panelHeight - size) / 2;

        if (isFace) {
            FaceDraw.drawFace(
                g,
                panelWidth / 2,
                panelHeight / 2,
                size
            );
            return;
        }

        g.setColor(new Color(red, green, blue));

        if (isCircle) {
            g.fillOval(x, y, size, size);
        } else {
            g.fillRect(x, y, size, size);
        }

        g.setFont(new Font(
            "Serif", Font.BOLD, Math.max(10, size / 3)
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

    private static int GetContrastingColor(int colorIn) {
        return (colorIn + 128) % 256;
    }

    private int GetNumberBetween(int min, int max) {
        return min + myRandom.nextInt(max - min + 1);
    }
}

class MosaicFrame extends JFrame {
    private MosaicGridPanel[][] tiles =
        new MosaicGridPanel[12][12];

    public MosaicFrame() {
        setTitle("Mosaic");
        setBounds(200, 200, 1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        Panel buttonPanel = new Panel(
            new FlowLayout(FlowLayout.CENTER)
        );

        JButton randomize = new JButton("Randomize");

        randomize.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int row = 0; row < 12; row++) {
                    for (int col = 0; col < 12; col++) {
                        tiles[row][col].SetRandomValue();
                    }
                }
            }
        });

        buttonPanel.add(randomize);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        Panel mosaicGridPanel = new Panel(
            new GridLayout(12, 12)
        );

        for (int row = 0; row < 12; row++) {
            for (int col = 0; col < 12; col++) {
                tiles[row][col] = new MosaicGridPanel();
                mosaicGridPanel.add(tiles[row][col]);
            }
        }

        contentPane.add(mosaicGridPanel, BorderLayout.CENTER);

        setLocationRelativeTo(null);
    }
}

public class Mosaic {
    public static void main(String[] args) {
        System.out.println("Mosaic starting up...");

        MosaicFrame myMosaicFrame = new MosaicFrame();
        myMosaicFrame.setVisible(true);
    }
}
