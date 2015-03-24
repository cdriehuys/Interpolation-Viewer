public class Vector {

    public float x;

    public Vector(float x) {

        this.x = x;
    }

    public static float lerp(float start, float end, float t) {

        t = (t < 0) ? 0 : t;
        t = (t > 1) ? 1 : t;

        return (1 - t) * start + t * end;
    }
}
