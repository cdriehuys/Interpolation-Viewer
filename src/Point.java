import java.awt.*;

public class Point {

    public static int DEFAULT_RADIUS = 5;


    private int x, y;

    public Point(int x, int y) {

        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g) {

        g.fillOval(this.x, this.y, DEFAULT_RADIUS, DEFAULT_RADIUS);
    }
}
