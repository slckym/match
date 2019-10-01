import javax.swing.*;
import javax.swing.border.Border;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.*;

public class Login extends JDialog{
    private JPasswordField txtPassword;
    private JTextField txtUsername;
    private JButton btnLogin;
    private JLabel lblUsername;
    private JLabel lblPassword;
    private JPanel pnlLogin;

    private Login(){
        setTitle("Login");
        setContentPane(pnlLogin);
        setModal(true);
        Helper.centreWindow(this);

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        pnlLogin.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        btnLogin.addActionListener(e -> {
            try {
                UserLogin();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    private void UserLogin() throws Exception {
        var username = txtUsername.getText();
        var password = txtPassword.getText();
        boolean status = Database.login(username, password);
        if (status){
            txtUsername.setBorder(BorderFactory.createLineBorder(Color.RED));
            txtPassword.setBorder(BorderFactory.createLineBorder(Color.RED));
        }
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        Login dialog = new Login();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
