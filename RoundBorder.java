import java.awt.Color;
import javax.swing.border.AbstractBorder;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Insets;

public class RoundBorder extends AbstractBorder {
    private int radius;
    private Color color; // Added color field

    RoundBorder(int radius) {
        this.radius = radius;
        this.color = Color.BLACK; // Default color
    }

    RoundBorder(int radius, Color color) {
        this.radius = radius;
        this.color = color; // Allow setting the color
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(this.color); // Use the specified color
        g2d.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        g2d.dispose();
    }

    @Override
    public Insets getBorderInsets(Component c, Insets insets) {
        insets.top = insets.bottom = insets.left = insets.right = radius;
        return insets;
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(radius, radius, radius, radius);
    }

    @Override
    public boolean isBorderOpaque() {
        return false;
    }
}
