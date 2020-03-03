import javafx.geometry.Pos;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main extends JFrame {
    private JPanel pnlMain;
    private JButton btnMatchAdditions;
    private JButton btnPossibilities;
    private JButton btnCalender;
    private JButton btnLeague;

    private Main() {
        initialize();
        btnCalender.addActionListener(actionEvent -> {
            try {
                Calender.visible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        btnMatchAdditions.addActionListener(actionEvent -> {
            Addition.visible(true);
        });
        btnPossibilities.addActionListener(actionEvent -> {
            try {
                Possibilities.visible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        btnLeague.addActionListener(actionEvent -> {
            try {
                League.visible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static void main(String[] args) {
        Main.visible(true);
    }

    @SuppressWarnings("SameParameterValue")
    static void visible(boolean b) {
        Main main = new Main();
        main.setVisible(b);
    }

    private void initialize() {
        setContentPane(pnlMain);
        setBounds(100, 100, 320, 300);
        setTitle("Selection Page");
        Helper.centreWindow(this);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        // call onCancel() on ESCAPE
        pnlMain.registerKeyboardAction(e -> dispose(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }
}
