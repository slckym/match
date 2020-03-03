import javax.swing.*;
import java.awt.event.*;

public class Possibilities extends JFrame {
    private JPanel pnlPossibilities;
    private JButton btnHighPossibilities;
    private JButton btnLowPossibilities;

    public Possibilities() {
        initialize();
    }

    private void initialize() {
        setContentPane(pnlPossibilities);
        setTitle("HighPossibilities");
        setBounds(100, 100, 250, 200);
        Helper.centreWindow(this);

        btnHighPossibilities.addActionListener(e -> {
            try {
                HighPossibilities.visible(true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        btnLowPossibilities.addActionListener(e -> {
            try {
                LowPossibilities.visible(true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        pnlPossibilities.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

    }

    @SuppressWarnings("SameParameterValue")
    static void visible(boolean b) throws Exception {
        Possibilities dialog = new Possibilities();
        dialog.setVisible(b);
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) throws Exception {
        Possibilities.visible(true);
    }
}
