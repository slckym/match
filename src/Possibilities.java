import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Possibilities extends JFrame {
    private JPanel pnlPossibilities;

    public Possibilities() {
        initialize();
    }

    public static void main(String[] args) {
        Possibilities.visible(true);
    }

    @SuppressWarnings("SameParameterValue")
    static void visible(boolean b) {
        Possibilities dialog = new Possibilities();
        dialog.setVisible(b);
    }

    private void initialize() {
        setContentPane(pnlPossibilities);
        setTitle("Possibilities");
        setBounds(100, 100, 500, 300);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        pnlPossibilities.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        Helper.centreWindow(this);
    }

    private void onCancel() {
        setVisible(false);
    }
}
