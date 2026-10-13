
import java.awt.Graphics;
import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.Graphics2D;

public class FaceDraw {

    public static void drawFace(
            Graphics g, int centerX, int centerY, int size) {

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setStroke(new BasicStroke(
            Math.max(1.0f, size * 0.025f)
        ));

        int x = centerX - size / 2;
        int y = centerY - size / 2;

        // Face
        g2.setColor(new Color(255, 220, 90));
        g2.fillOval(x, y, size, size);

        g2.setColor(Color.BLACK);
        g2.drawOval(x, y, size, size);

        // Eyes
        int eyeSize = Math.max(2, size / 10);
        int eyeY = centerY - size / 6;

        g2.fillOval(
            centerX - size / 4,
            eyeY,
            eyeSize,
            eyeSize
        );

        g2.fillOval(
            centerX + size / 5,
            eyeY,
            eyeSize,
            eyeSize
        );

        // Smile
        g2.drawArc(
            centerX - size / 4,
            centerY - size / 8,
            size / 2,
            size / 2,
            200,
            140
        );

        g2.dispose();
    }
}
