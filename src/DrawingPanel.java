import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class DrawingPanel extends JPanel implements ActionListener, ChangeListener {

    private static int T_MIN = 0;
    private static int T_MAX = 1000;
    private static int T_INIT = 0;
    private static int T_INFLATION = 1000;

    private ArrayList<Vector2> points;

    private int width, height;

    private JLabel tLabel;

    private JSlider tSlider;

    private Timer repaintTimer;

    public DrawingPanel(int width, int height) {

        this.width = width;
        this.height = height;

        setPreferredSize(new Dimension(width, height));

        this.points = new ArrayList<Vector2>();
        this.points.add(new Vector2(50, 100));
        this.points.add(new Vector2(400, 375));

        this.repaintTimer = new Timer(20, this);

        JPanel slidePanel = new JPanel();

        this.tSlider = new JSlider(JSlider.HORIZONTAL, T_MIN, T_MAX, T_INIT);
        this.tSlider.addChangeListener(this);
        slidePanel.add(this.tSlider);

        this.tLabel = new JLabel(String.format("t = %.3f", (float)this.tSlider.getValue() / T_INFLATION));
        slidePanel.add(this.tLabel);

        add(slidePanel);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        drawLine(g, this.points.get(0), this.points.get(1));

        for (Vector2 p : this.points) {
            p.draw(g);
        }

        Vector2.lerp(this.points.get(0), this.points.get(1), (float)this.tSlider.getValue() / T_INFLATION).draw(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        this.tLabel.setText(String.format("t = %.2f", (float)this.tSlider.getValue() / T_INFLATION));
        repaint();
    }

    public void drawLine(Graphics g, Vector2 start, Vector2 end) {
        g.drawLine(Math.round(start.getX()), Math.round(start.getY()), Math.round(end.getX()), Math.round(end.getY()));
    }
}
