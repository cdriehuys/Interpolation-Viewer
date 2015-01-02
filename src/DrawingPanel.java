import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class DrawingPanel extends JPanel implements ActionListener {

    private int width, height;

    private ArrayList<Vector2> points;

    private Timer repaintTimer;

    public DrawingPanel(int width, int height) {

        this.width = width;
        this.height = height;

        setPreferredSize(new Dimension(width, height));

        this.points = new ArrayList<Vector2>();
        this.points.add(new Vector2(25, 10));
        this.points.add(new Vector2(30, 25));

        this.repaintTimer = new Timer(20, this);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        for (Vector2 p : this.points) {
            p.draw(g);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}
