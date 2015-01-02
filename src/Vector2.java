import java.awt.*;

public class Vector2 {

    public static int DEFAULT_RADIUS = 8;


    private float x, y;

    public Vector2(float x, float y) {

        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g) {

        g.fillOval(Math.round(this.x), Math.round(this.y), DEFAULT_RADIUS, DEFAULT_RADIUS);
    }

    public static Vector2 lerp(Vector2 start, Vector2 end, float t) {

        t = (t < 0) ? 0 : t;
        t = (t > 1) ? 1 : t;

        return new Vector2((1 - t) * start.x + t * end.x, (1 - t) * start.y + t * end.y);
    }

    /*
    GETTER AND SETTER METHODS
     */

    public float getX() { return this.x; }
    public void setX(int x) { this.x = x; }

    public float getY() { return this.y; }
    public void setY(int y) { this.y = y; }
}
