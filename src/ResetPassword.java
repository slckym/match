import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

public class ResetPassword extends JFrame {
    private JPanel pnlResetPassword;
    private JButton buttonOK;
    private JPasswordField txtPassword;
    private JPasswordField txtPasswordConfirm;
    private JLabel lblPassword;
    private JLabel lblPasswordConfirm;
    public JTextField txtUsername;
    private JLabel lblUsername;

    public ResetPassword() {
        initialize();
        setContentPane(pnlResetPassword);
        getRootPane().setDefaultButton(buttonOK);
    }

    private void initialize() {
        setContentPane(pnlResetPassword);
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
        pnlResetPassword.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        pnlResetPassword.registerKeyboardAction(e -> {
            try {
                ChangePassword();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        buttonOK.addActionListener(actionEvent -> {
            try {
                ChangePassword();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    private void ChangePassword() throws SQLException {
        var password = txtPassword.getText();
        var passwordConfirm = txtPasswordConfirm.getText();
        var username = txtUsername.getText();

        if (password.isEmpty() || passwordConfirm.isEmpty()) {
            Helper.showDialog("Password fields blank. Please check.");
            lblPassword.setForeground(Color.RED);
            lblPasswordConfirm.setForeground(Color.RED);
        } else {
            if (!password.equals(passwordConfirm)) {
                Helper.showDialog("Password different. Please check.");
                lblPassword.setForeground(Color.RED);
                lblPasswordConfirm.setForeground(Color.RED);
            } else {
                boolean status = Database.updatePassword(username, password);
                if (status) {
                    setVisible(false);
                    Helper.showDialog("Password changed.");
                } else {
                    lblPassword.setForeground(Color.RED);
                    lblPasswordConfirm.setForeground(Color.RED);
                    Helper.showDialog("Wrong fields. Please check.");
                }
            }
        }

    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                ResetPassword.visible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @SuppressWarnings("SameParameterValue")
    static void visible(boolean b) {
        ResetPassword reset = new ResetPassword();
        reset.setVisible(true);
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
