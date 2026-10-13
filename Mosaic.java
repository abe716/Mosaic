import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;









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