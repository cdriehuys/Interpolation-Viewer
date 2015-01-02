import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class DrawingPanel extends JPanel implements ActionListener {

    private int width, height;

    private ArrayList<Point> points;

    private Timer repaintTimer;

    public DrawingPanel(int width, int height) {

        this.width = width;
        this.height = height;

        setPreferredSize(new Dimension(width, height));

        this.points = new ArrayList<Point>();
        this.points.add(new Point(25, 10));
        this.points.add(new Point(30, 25));

        this.repaintTimer = new Timer(20, this);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        for (Point p : this.points) {
            p.draw(g);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}
