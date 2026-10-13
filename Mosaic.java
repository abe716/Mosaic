import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Random;






class MosaicGridPanel extends JPanel {
    private int red, green, blue;
    private String letter;

    MosaicGridPanel() {
        super();
        SetRandomValue();

    }

    final public void SetRandomValue() {
        red = GetNumberBetween(0,250);
        green = GetNumberBetween(0,250);
        blue = GetNumberBetween(0,250);

        letter = "x";
        if (GetNumberBetween(0,1) == 1) {
            letter = "O";

        }
    }

    private static int GetNumberBetween(int min, int max) {
            Random myRandom = new Random();
            return min + myRandom.nextInt(max-min+1);

        }
    }

class MosaicFrame extends JFrame{
    public MosaicFrame() {
        setBounds(200,200,1200,800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        JButton randomize = new JButton("Randomize");
        buttonPanel.add(randomize);

        JPanel MosaicGridPanel = new JPanel();
        contentPane.add(MosaicGridPanel, BorderLayout.CENTER);
        MosaicGridPanel.setLayout(new GridLayout(12,12));





    }
}
public class Mosaic {
    public static void main(String[] args) {
        System.out.println("Mosaic starting up...");

        MosaicFrame myMosaicFrame = new MosaicFrame();
        myMosaicFrame.setVisible(true);
    }
    
}