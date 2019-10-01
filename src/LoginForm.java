import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginForm  extends JDialog{
    private JPasswordField txtPassword;
    private JTextField txtUsername;
    private JButton btnLogin;
    private JLabel lblUsername;
    private JLabel lblPassword;
    private JPanel pnlLogin;

    private LoginForm(){
        setTitle("Login");
        setContentPane(pnlLogin);
        setModal(true);
        Helper.centreWindow(this);

        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
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
        pnlLogin.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        LoginForm dialog = new LoginForm();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
