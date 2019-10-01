import java.awt.*;

public class Helper {

    public static void centreWindow(Window frame) {

        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();

        int x = (int) ((width - frame.getWidth()) / 2);
        int y = (int) ((height - frame.getHeight()) / 2);

        frame.setLocation(x, y);
    }
}
