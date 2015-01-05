import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class DrawingPanel extends JPanel implements ActionListener {

    private static int MARGIN = 50;
    private static int T_MAX = 1000;

    private static float T_STEP = .005f;

    private static Random random = new Random();

    private ArrayList<ArrayList<Vector2>> curvePoints;
    private ArrayList<Vector2> targets;

    private float t;

    private int curveIndex;

    private Timer animationTimer;

    private Vector2 p0, p1, p2, q0, q1;

    public DrawingPanel(int width, int height) {

        setPreferredSize(new Dimension(width, height));
        setSize(getPreferredSize());

        targets = new ArrayList<Vector2>();
        curveIndex = 0;
        curvePoints = new ArrayList<ArrayList<Vector2>>();

        genPoints();

        JPanel controlPanel = new JPanel();

        /*
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
        */

        add(controlPanel);

        animationTimer = new Timer(20, this);
        animationTimer.start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(Color.ORANGE);

        for (int i = 0; i <= curveIndex; i++) {
            for (int j = 0; j < ((i == curveIndex) ? t * T_MAX - 2 : T_MAX - 1); j++) {
                drawLine(g2d, curvePoints.get(i).get(j), curvePoints.get(i).get(j + 1), 4);
            }
        }

        g2d.setColor(Color.GRAY);

        drawLine(g2d, p0, p1, 2);
        drawLine(g2d, p1, p2, 2);

        drawLine(g2d, q0, q1);


        g2d.setColor(Color.RED);

        for (Vector2 p : targets)
            p.draw(g2d);

        g2d.setColor(Color.BLACK);

        p0.draw(g2d);
        p1.draw(g2d);
        p2.draw(g2d);

        q0.draw(g2d);
        q1.draw(g2d);

        Vector2.lerp(q0, q1, t).draw(g2d);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        t += T_STEP;

        if (t > 1) {
            targets.remove(0);
            curveIndex++;
            genPoints();
            t = 0;
        }

        q0 = Vector2.lerp(p0, p1, t);
        q1 = Vector2.lerp(p1, p2, t);

        repaint();
    }

    /*
    @Override
    public void stateChanged(ChangeEvent e) {

        int slideT = tSlider.getValue();
        t = (float)slideT / T_MAX;
        tLabel.setText(String.format("t = %.2f", t));
        q0 = Vector2.lerp(p0, p1, t);
        q1 = Vector2.lerp(p1, p2, t);
        repaint();
    }
    */

    public void genPoints() {

        while(targets.size() < 3) {
            targets.add(new Vector2(random.nextInt(getWidth() - (2 * MARGIN)) + MARGIN,
                                    random.nextInt(getHeight() - (2 * MARGIN)) + MARGIN,
                                    3));
        }

        p0 = Vector2.lerp(targets.get(0), targets.get(1), .5f);
        p1 = targets.get(1);
        p2 = Vector2.lerp(targets.get(1), targets.get(2), .5f);

        while (curvePoints.size() < curveIndex + 1) {
            curvePoints.add(new ArrayList<Vector2>());
        }

        for (int i = 0; i <= T_MAX; i++) {
            float tTemp = (float)i / T_MAX;
            Vector2 q0Temp = Vector2.lerp(p0, p1, (float)i / T_MAX);
            Vector2 q1Temp = Vector2.lerp(p1, p2, (float)i / T_MAX);
            curvePoints.get(curveIndex).add(Vector2.lerp(q0Temp, q1Temp, tTemp));
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
