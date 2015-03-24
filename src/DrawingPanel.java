import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class DrawingPanel extends JPanel implements ActionListener {

    private static int MARGIN = 50;

    private static float T_STEP = .005f;

    private static Random random = new Random();

    private ArrayList<Vector2> targets;

    private BufferedImage prevPoints;

    private Color pointColor;

    private float t;
    private float startHue, endHue;

    private Graphics2D prevPointsGraphics;

    private Timer animationTimer;

    private Vector2 p0, p1, p2, q0, q1;

    public DrawingPanel(int width, int height) {

        setPreferredSize(new Dimension(width, height));
        setSize(getPreferredSize());

        targets = new ArrayList<Vector2>();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double screenWidth = screenSize.getWidth();
        double screenHeight = screenSize.getHeight();

        prevPoints = new BufferedImage((int)screenWidth, (int)screenHeight, BufferedImage.TYPE_INT_ARGB);
        prevPointsGraphics = prevPoints.createGraphics();

        startHue = .9f;
        endHue = .9f;

        pointColor = Color.getHSBColor(startHue, 1f, 1f);

        prevPointsGraphics.setColor(pointColor);

        genPoints();

        JPanel controlPanel = new JPanel();

        add(controlPanel);

        animationTimer = new Timer(20, this);
        animationTimer.start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.drawImage(prevPoints, 0, 0, null);

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

        Vector2 curPoint = Vector2.lerp(q0, q1, t);

        curPoint.draw(g2d);

        curPoint.draw(prevPointsGraphics);

        pointColor = shiftColor(pointColor);
        prevPointsGraphics.setColor(pointColor);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        t += T_STEP;

        if (t > 1) {
            targets.remove(0);
            genPoints();
            genColors();
            t = 0;
        }

        q0 = Vector2.lerp(p0, p1, t);
        q1 = Vector2.lerp(p1, p2, t);

        pointColor = Color.getHSBColor(Vector.lerp(startHue, endHue, t), 1, 1);

        repaint();
    }

    public void genPoints() {

        while(targets.size() < 3) {
            targets.add(new Vector2(random.nextInt(getWidth() - (2 * MARGIN)) + MARGIN,
                                    random.nextInt(getHeight() - (2 * MARGIN)) + MARGIN,
                                    3));
        }

        p0 = Vector2.lerp(targets.get(0), targets.get(1), .5f);
        p1 = targets.get(1);
        p2 = Vector2.lerp(targets.get(1), targets.get(2), .5f);

        repaint();
    }

    public void genColors() {

        startHue = endHue;
        endHue = random.nextFloat();
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

    public Color shiftColor(Color current) {

        int range = 30;

        int red = current.getRed();
        int green = current.getGreen();
        int blue = current.getBlue();

        red += random.nextInt(range) - range / 2;
        if (red < 50) red = 50;
        else if (red > 255) red = 255;

        green += random.nextInt(range) - range / 2;
        if (green < 50) green = 50;
        else if (green > 255) green = 255;

        blue += random.nextInt(range) - range / 2;
        if (blue < 50) blue = 50;
        else if (blue > 255) blue = 255;

        return new Color(red, green, blue);
    }
}
