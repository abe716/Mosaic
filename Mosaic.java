import javax.swing.JFrame;
















class MosaicFrame extends JFrame{
    public MosaicFrame() {
        setBounds(200,200,1200,800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
public class Mosaic {
    public static void main(String[] args) {
        System.out.println("Mosaic starting up...");

        MosaicFrame myMosaicFrame = new MosaicFrame();
        myMosaicFrame.setVisible(true);
    }
    
}