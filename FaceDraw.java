
import java.awt.Graphics;
import java.awt.Color;

public class FaceDraw {

    public static void drawFace(
            Graphics g, int centerX, int centerY, int size) {

        int x = centerX - size / 2;
        int y = centerY - size / 2;

        // Draw the face.
        g.setColor(new Color(255, 220, 90));
        g.fillOval(x, y, size, size);

        g.setColor(Color.BLACK);
        g.drawOval(x, y, size, size);

        // Draw the eyes.
        int eyeSize = Math.max(2, size / 10);
        int eyeY = centerY - size / 6;

        g.fillOval(
                centerX - size / 4,
                eyeY,
                eyeSize,
                eyeSize);

        g.fillOval(
                centerX + size / 5,
                eyeY,
                eyeSize,
                eyeSize);

        // Draw the smile.
        g.drawArc(
                centerX - size / 4,
                centerY - size / 8,
                size / 2,
                size / 2,
                200,
                140);
    }
}