import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ForgetPassword extends JFrame {
    private JPanel pnlForgotPassword;
    private JButton btnResetPassword;
    private JTextField txtUsername;
    private JTextField txtAnswerFirst;
    private JTextField txtAnswerSecond;
    private JLabel lblFirstAnswer;
    private JLabel lblSecondAnswer;
    private JLabel lblUsername;

    public ForgetPassword() {
        initialize();
    }

    private void initialize() {
        setContentPane(pnlForgotPassword);
        setResizable(false);
        setBounds(100, 100, 320, 175);
        Helper.centreWindow(this);

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        pnlForgotPassword.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        pnlForgotPassword.registerKeyboardAction(e -> {
            try {
                ResetPassword();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        btnResetPassword.addActionListener(actionEvent -> {
            try {
                ResetPassword();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                ForgetPassword.visible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void ResetPassword() throws Exception {
        var username = txtUsername.getText();
        var answer1 = txtAnswerFirst.getText();
        var answer2 = txtAnswerSecond.getText();
        int status = Database.forgetPassword(username, answer1, answer2);
        if (status > 0) {
            setVisible(false);
            ResetPassword reset = new ResetPassword();
            reset.txtUsername.setText(username);
            reset.setVisible(true);
        } else {
            lblUsername.setForeground(Color.RED);
            lblFirstAnswer.setForeground(Color.RED);
            lblSecondAnswer.setForeground(Color.RED);
            Helper.showDialog("Wrong fields. Please check.");
        }
    }

    @SuppressWarnings("SameParameterValue")
    static void visible(boolean b) {
        ForgetPassword forget = new ForgetPassword();
        forget.setVisible(true);
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
