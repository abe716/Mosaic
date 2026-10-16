
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.SwingUtilities;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import java.util.ArrayList;

public class Mosaic {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MosaicFrame myMosaicFrame = new MosaicFrame();
            myMosaicFrame.setVisible(true);
        });
    }
}

class MosaicFrame extends JFrame implements ActionListener {
    private ArrayList<MosaicGridPanel> tileList;
    private JButton randomize;

    public MosaicFrame() {
        setTitle("Mosaic");
        setSize(900, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        tileList = new ArrayList<MosaicGridPanel>();

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        randomize = new JButton("Randomize");
        randomize.addActionListener(this);
        buttonPanel.add(randomize);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        JPanel mosaicGridPanel = new JPanel();
        mosaicGridPanel.setLayout(new GridLayout(12, 12));
        contentPane.add(mosaicGridPanel, BorderLayout.CENTER);

        for (int i = 0; i < 144; i++) {
            MosaicGridPanel tile = new MosaicGridPanel();
            tileList.add(tile);
            mosaicGridPanel.add(tile);
        }
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == randomize) {
            for (MosaicGridPanel tile : tileList) {
                tile.SetRandomValues();
            }
            repaint();
        }
    }

    @Override
    public void paint(Graphics g) {
        System.out.println("Start paint***");

        if (tileList != null) {
            for (MosaicGridPanel tile : tileList) {
                System.out.println(tile.toString());
            }
        }

        super.paint(g);
    }
}

class MosaicGridPanel extends JPanel {
    private int red;
    private int green;
    private int blue;
    private String letter;
    private Color letterColor;
    private boolean circle;
    private boolean face;

    private static final Random myRandom = new Random();

    public MosaicGridPanel() {
        SetRandomValues();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                face = !face;
                repaint();
            }
        });
    }

    public MosaicGridPanel(
            int red, int green, int blue,
            String letter, boolean circle) {

        setRed(red);
        setGreen(green);
        setBlue(blue);
        setLetter(letter);
        setCircle(circle);
        setLetterColor(new Color(
                (red + 128) % 256,
                (green + 128) % 256,
                (blue + 128) % 256));
        face = false;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                face = !face;
                repaint();
            }
        });
    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }

    public String getLetter() {
        return letter;
    }

    public Color getLetterColor() {
        return letterColor;
    }

    public boolean isCircle() {
        return circle;
    }

    public boolean isFace() {
        return face;
    }

    public void setRed(int red) {
        if (red < 0 || red > 255) {
            throw new IllegalArgumentException("Red must be 0-255");
        }
        this.red = red;
    }

    public void setGreen(int green) {
        if (green < 0 || green > 255) {
            throw new IllegalArgumentException("Green must be 0-255");
        }
        this.green = green;
    }

    public void setBlue(int blue) {
        if (blue < 0 || blue > 255) {
            throw new IllegalArgumentException("Blue must be 0-255");
        }
        this.blue = blue;
    }

    public void setLetter(String letter) {
        if (letter == null || !letter.matches("[A-Z]")) {
            throw new IllegalArgumentException(
                    "Letter must be one capital letter");
        }
        this.letter = letter;
    }

    public void setLetterColor(Color letterColor) {
        if (letterColor == null) {
            throw new IllegalArgumentException(
                    "Letter color cannot be null");
        }
        this.letterColor = letterColor;
    }

    public void setCircle(boolean circle) {
        this.circle = circle;
    }

    public void setFace(boolean face) {
        this.face = face;
        repaint();
    }

    public void SetRandomValues() {
        red = GetNumberBetween(0, 255);
        green = GetNumberBetween(0, 255);
        blue = GetNumberBetween(0, 255);

        char randomLetter =
                (char) ('A' + GetNumberBetween(0, 25));
        letter = String.valueOf(randomLetter);

        circle = GetNumberBetween(0, 1) == 1;
        face = false;

        letterColor = new Color(
                (red + 128) % 256,
                (green + 128) % 256,
                (blue + 128) % 256);

        System.out.println(toString());
        repaint();
    }

    private int GetNumberBetween(int min, int max) {
        return myRandom.nextInt(max - min + 1) + min;
    }

    @Override
    public String toString() {
        return "Tile [Shape=" + (circle ? "Circle" : "Square")
                + ", Letter=" + letter
                + ", Red=" + red
                + ", Green=" + green
                + ", Blue=" + blue + "]";
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int panelWidth = getWidth();
        int panelHeight = getHeight();
        int shapeSize = (int) (
                Math.min(panelWidth, panelHeight) * 0.75);

        int shapeX = (panelWidth - shapeSize) / 2;
        int shapeY = (panelHeight - shapeSize) / 2;

        if (face) {
            FaceDraw.drawFace(
                    g, panelWidth / 2, panelHeight / 2, shapeSize);
            return;
        }

        g.setColor(new Color(red, green, blue));

        if (circle) {
            g.fillOval(shapeX, shapeY, shapeSize, shapeSize);
        } else {
            g.fillRect(shapeX, shapeY, shapeSize, shapeSize);
        }

        g.setColor(letterColor);

        int fontSize = Math.max(10, shapeSize / 3);
        g.setFont(new Font("Times New Roman", Font.PLAIN, fontSize));

        FontMetrics metrics = g.getFontMetrics();
        int textWidth = metrics.stringWidth(letter);
        int textHeight = metrics.getAscent();

        int textX = panelWidth / 2 - textWidth / 2;
        int textY = panelHeight / 2
                + (metrics.getAscent() - metrics.getDescent()) / 2;

        g.drawString(letter, textX, textY);
    }
}