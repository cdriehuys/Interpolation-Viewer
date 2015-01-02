import javax.swing.*;
import java.awt.*;

public class DrawingPanel extends JPanel {

    private int width, height;

    public DrawingPanel(int width, int height) {

        this.width = width;
        this.height = height;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);


    }
}
