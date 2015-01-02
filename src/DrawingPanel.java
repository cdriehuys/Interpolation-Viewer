import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class DrawingPanel extends JPanel implements ChangeListener {

    private static int T_MIN = 0;
    private static int T_MAX = 1000;
    private static int T_INIT = 0;

    private static Random random = new Random();

    private ArrayList<Vector2> curvePoints;

    private float t;

    private JLabel tLabel;

    private JSlider tSlider;

    private Vector2 p0, p1, p2, q0, q1;

    public DrawingPanel(int width, int height) {

        setPreferredSize(new Dimension(width, height));

        curvePoints = new ArrayList<Vector2>();
        genPoints();

        JPanel controlPanel = new JPanel();

        JButton newPointsBtn = new JButton("New Points");
        newPointsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                genPoints();
            }
        });
        controlPanel.add(newPointsBtn);

        tSlider = new JSlider(JSlider.HORIZONTAL, T_MIN, T_MAX, T_INIT);
        tSlider.addChangeListener(this);
        controlPanel.add(tSlider);

        tLabel = new JLabel(String.format("t = %.2f", (float)tSlider.getValue() / T_MAX));
        controlPanel.add(tLabel);

        add(controlPanel);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(Color.ORANGE);

        for (int i = 0; i < tSlider.getValue() - 2; i++) {
            drawLine(g2d, curvePoints.get(i), curvePoints.get(i + 1), 4);
        }

        g2d.setColor(Color.BLACK);

        drawLine(g2d, p0, p1, 2);
        drawLine(g2d, p1, p2, 2);

        drawLine(g2d, q0, q1);

        p0.draw(g);
        p1.draw(g);
        p2.draw(g);

        q0.draw(g);
        q1.draw(g);

        Vector2.lerp(q0, q1, t).draw(g);
    }

    @Override
    public void stateChanged(ChangeEvent e) {

        int slideT = tSlider.getValue();
        t = (float)slideT / T_MAX;
        tLabel.setText(String.format("t = %.2f", t));
        q0 = Vector2.lerp(p0, p1, t);
        q1 = Vector2.lerp(p1, p2, t);
        repaint();
    }

    public void genPoints() {

        p0 = new Vector2(random.nextInt(300) + 100, random.nextInt(300) + 100);
        p1 = new Vector2(random.nextInt(300) + 100, random.nextInt(300) + 100);
        p2 = new Vector2(random.nextInt(300) + 100, random.nextInt(300) + 100);
        q0 = Vector2.lerp(p0, p1, t);
        q1 = Vector2.lerp(p1, p2, t);

        curvePoints.clear();

        for (int i = 0; i <= T_MAX; i++) {
            float tTemp = (float)i / T_MAX;
            Vector2 q0Temp = Vector2.lerp(p0, p1, (float)i / T_MAX);
            Vector2 q1Temp = Vector2.lerp(p1, p2, (float)i / T_MAX);
            curvePoints.add(Vector2.lerp(q0Temp, q1Temp, tTemp));
        }

        repaint();
    }

    public void drawLine(Graphics2D g2d, Vector2 start, Vector2 end) {
        g2d.drawLine(Math.round(start.x), Math.round(start.y), Math.round(end.x), Math.round(end.y));
    }

    public void drawLine(Graphics2D g2d, Vector2 start, Vector2 end, int thickness) {
        Stroke prevStroke = g2d.getStroke();
        g2d.setStroke(new BasicStroke(thickness));
        g2d.drawLine(Math.round(start.x), Math.round(start.y), Math.round(end.x), Math.round(end.y));
        g2d.setStroke(prevStroke);
    }
}
