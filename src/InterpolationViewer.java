import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class InterpolationViewer extends JFrame {

    public static final int WIN_WIDTH = 700;
    public static final int WIN_HEIGHT = 700;

    public InterpolationViewer() {

        DrawingPanel panel = new DrawingPanel(WIN_WIDTH, WIN_HEIGHT);
        add(panel);

        pack();

        setTitle("Interpolation Viewer");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {

        try {
            SwingUtilities.invokeLater(new Runnable() {

                @Override
                public void run() {
                    new InterpolationViewer();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
}
