import javax.swing.*;

public class InterpolationViewer extends JFrame {

    public static final int WIN_WIDTH = 500;
    public static final int WIN_HEIGHT = 500;

    public InterpolationViewer() {

        setTitle("Interpolation Viewer");
        setSize(WIN_WIDTH, WIN_HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                new InterpolationViewer();
            }
        });
    }
}
