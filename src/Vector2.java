import java.awt.*;

public class Vector2 {

    public static int DEFAULT_RADIUS = 8;


    private int x, y;

    public Vector2(int x, int y) {

        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g) {

        g.fillOval(this.x, this.y, DEFAULT_RADIUS, DEFAULT_RADIUS);
    }
}
