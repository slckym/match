import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.Objects;
import java.util.function.Supplier;

class Helper {

    static void centreWindow(Window frame) {

        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();

        int x = (width - frame.getWidth()) / 2;
        int y = (height - frame.getHeight()) / 2;

        frame.setLocation(x, y);
    }

    static void showDialog(String message) {
        JOptionPane.showMessageDialog(
                ((Supplier<JDialog>) () -> {
                    final JDialog dialog = new JDialog();
                    dialog.setAlwaysOnTop(true);
                    return dialog;
                }).get()
                , message);
    }

    static void fillCombobox(JComboBox cmb) throws SQLException {
        var teams = Database.getAllTeams();
        while (Objects.requireNonNull(teams).next()) {
            cmb.addItem(teams.getString("team_name"));
        }
    }
}
