import java.awt.*;

public class Vector2 {

    public static int DEFAULT_RADIUS = 5;

    public float x, y;

    public Vector2(float x, float y) {

        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g) {

        g.fillOval(Math.round(this.x) - DEFAULT_RADIUS, Math.round(this.y) - DEFAULT_RADIUS, DEFAULT_RADIUS * 2, DEFAULT_RADIUS * 2);
    }

    public static Vector2 lerp(Vector2 start, Vector2 end, float t) {

        t = (t < 0) ? 0 : t;
        t = (t > 1) ? 1 : t;

        return new Vector2((1 - t) * start.x + t * end.x, (1 - t) * start.y + t * end.y);
    }
}
