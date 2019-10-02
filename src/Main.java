import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JDialog {
    private JPanel contentPane;
    private JButton btnMatchAdditions;
    private JButton btnPosibilities;
    private JButton btnCalender;

    private Main() {
        initialize();
        btnCalender.addActionListener(actionEvent -> {
            setVisible(false);
            Calender.visible(true);
        });
        btnMatchAdditions.addActionListener(actionEvent -> {
            setVisible(false);
            Addition.visible(true);
        });
    }

    private void initialize() {
        setContentPane(contentPane);
        setModal(true);
        setBounds(100, 100, 320, 300);
        setTitle("Selection Page");
        Helper.centreWindow(this);
    }

    public static void main(String[] args) {
        Main.visible(true);
    }

    @SuppressWarnings("SameParameterValue")
    static void visible(boolean b) {
        Main main = new Main();
        main.setVisible(b);
        System.exit(0);
    }
}
