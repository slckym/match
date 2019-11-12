import net.proteanit.sql.DbUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class Calender extends JFrame {
    private JPanel pnlCalender;
    private JComboBox cmbTeamFirst;
    private JComboBox cmbTeamSecond;
    private JTable tblResults;
    private JButton btnFilter;
    private JLabel lblTeamFirst;
    private JLabel lblTeamSecond;
    private JScrollPane scrollResultTable;
    private JButton btnAllData;

    private Calender() {
        Database.fillHistoryTable(null, null, tblResults);
        initialize();
        try {
            Helper.fillCombobox(cmbTeamFirst);
            Helper.fillCombobox(cmbTeamSecond);
            cmbTeamSecond.setSelectedIndex(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        btnFilter.addActionListener(e -> {
            String teamFirst = Objects.requireNonNull(cmbTeamFirst.getSelectedItem()).toString();
            String teamSecond = Objects.requireNonNull(cmbTeamSecond.getSelectedItem()).toString();
            if(cmbTeamFirst.getSelectedIndex() != cmbTeamSecond.getSelectedIndex()){
                Database.fillHistoryTable(teamFirst, teamSecond, tblResults);
                lblTeamFirst.setForeground(Color.black);
                lblTeamSecond.setForeground(Color.black);
            }
            else{
                Helper.showDialog("Select different team");
                lblTeamFirst.setForeground(Color.red);
                lblTeamSecond.setForeground(Color.red);
                cmbTeamSecond.requestFocus();
            }
        });
         btnAllData.addActionListener(actionEvent -> {
             Database.fillHistoryTable(null, null, tblResults);
         });
        tblResults.setDefaultEditor(Object.class, null);
        tblResults.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                JTable table = (JTable) e.getSource();
                Point point = e.getPoint();
                if (e.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    int row = table.rowAtPoint(point);
                    DefaultTableModel model = (DefaultTableModel) tblResults.getModel();
                    AtomicReferenceArray<Object> result = new AtomicReferenceArray<>(new Object[model.getColumnCount()]);
                    for (int i = 0; i < model.getColumnCount(); i++) {
                        result.set(i, model.getValueAt(row, i));
                    }
                    int rowId = (int) result.get(0);
                    Helper.showDialog(String.valueOf(rowId));
                }
            }
        });
    }

    public static void main(String[] args) {
        Calender.visible(true);
    }

    @SuppressWarnings("SameParameterValue")
    static void visible(boolean b) {
        Calender calender = new Calender();
        calender.setVisible(b);
    }

    private void initialize() {
        setContentPane(pnlCalender);
        setSize(960, 700);
        setTitle("Match Calender");
        setResizable(false);
        Helper.centreWindow(this);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        pnlCalender.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onCancel() {
        setVisible(false);
    }

}
