package RootsPLOverall;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import DAL.DataLayerRoots;
import DAL.Roots_Interface;
import DAL.addRootDal;
import DAL.updateSingleRootDAL;

public class rootsPL extends JFrame {

    private static Roots_Interface rootsInterface = new DataLayerRoots();
    private static addRootDal addRootDal = new addRootDal();
    private static updateSingleRootDAL updateSingleRootDAL = new updateSingleRootDAL();

    private JTable rootsTable;
    private DefaultTableModel tableModel;

    public rootsPL() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Root Management System");
        setSize(800, 600);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tableModel = new DefaultTableModel();
        rootsTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(rootsTable);
        add(scrollPane);

        createMenu();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void createMenu() {
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu rootMenu = new JMenu("Root Menu");
        menuBar.add(rootMenu);

        JMenuItem addRootItem = new JMenuItem("Add Root");
        JMenuItem checkRootItem = new JMenuItem("Check Root");
        JMenuItem updateRootItem = new JMenuItem("Update Root");
        JMenuItem deleteRootItem = new JMenuItem("Delete Root");
        JMenuItem showAllRootsItem = new JMenuItem("Show All Roots");
        JMenuItem showRootDataItem = new JMenuItem("Show Root Data");
        JMenuItem addRootForTokenItem = new JMenuItem("Add Root for Token");
        JMenuItem updateSingleRootItem = new JMenuItem("Update Single Root");
        JMenuItem exitItem = new JMenuItem("Exit");

        rootMenu.add(addRootItem);
        rootMenu.add(checkRootItem);
        rootMenu.add(updateRootItem);
        rootMenu.add(deleteRootItem);
        rootMenu.add(showAllRootsItem);
        rootMenu.add(showRootDataItem);
        rootMenu.add(addRootForTokenItem);
        rootMenu.add(updateSingleRootItem);
        rootMenu.add(exitItem);

        addRootItem.addActionListener(e -> addRootDialog());
        checkRootItem.addActionListener(e -> checkRootDialog());
        updateRootItem.addActionListener(e -> updateRootDialog());
        deleteRootItem.addActionListener(e -> deleteRootDialog());
        showAllRootsItem.addActionListener(e -> showAllRoots());
        showRootDataItem.addActionListener(e -> showRootDataDialog());
        addRootForTokenItem.addActionListener(e -> addRootForTokenDialog());
        updateSingleRootItem.addActionListener(e -> updateSingleRootDialog());
        exitItem.addActionListener(e -> System.exit(0));
    }

    private void addRootDialog() {
        JTextField rootField = new JTextField();
        JTextField tIdField = new JTextField();

        Object[] message = {
                "Root:", rootField,
                "t_id:", tIdField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Add Root", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String root = rootField.getText();
            int t_id = Integer.parseInt(tIdField.getText());

            rootsInterface.addRoot(root, t_id);
            JOptionPane.showMessageDialog(null, "Root added successfully!");
            updateTable();
        }
    }

    private void checkRootDialog() {
        String rootName = JOptionPane.showInputDialog("Enter root name to check:");
        boolean rootExists = rootsInterface.checkRoot(rootName);
        JOptionPane.showMessageDialog(null, "Root '" + rootName + "' exists: " + rootExists);
    }

    private void updateRootDialog() {
        JTextField oldRootField = new JTextField();
        JTextField newRootField = new JTextField();

        Object[] message = {
                "Old Root:", oldRootField,
                "New Root:", newRootField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Update Root", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String oldRoot = oldRootField.getText();
            String newRoot = newRootField.getText();

            rootsInterface.updateRoot(oldRoot, newRoot);
            JOptionPane.showMessageDialog(null, "Root updated successfully!");
            updateTable();
        }
    }

    private void deleteRootDialog() {
        String rootToDelete = JOptionPane.showInputDialog("Enter root to delete:");
        rootsInterface.deleteRoot(rootToDelete);
        JOptionPane.showMessageDialog(null, "Root deleted successfully!");
        updateTable();
    }

    private void showAllRoots() {
        updateTable();
    }

    private void showRootDataDialog() {
        String rootToShow = JOptionPane.showInputDialog("Enter root to show data:");
        List<String> rootData = rootsInterface.showRootData(rootToShow);

        StringBuilder message = new StringBuilder("=== Root Data for ").append(rootToShow).append(" ===\n");
        for (String verse : rootData) {
            message.append(verse).append("\n");
        }

        JOptionPane.showMessageDialog(null, message.toString());
    }

    private void addRootForTokenDialog() {
        JTextField tIdField = new JTextField();
        JTextField rootField = new JTextField();

        Object[] message = {
                "t_id:", tIdField,
                "Root:", rootField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Add Root for Token", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            int t_id = Integer.parseInt(tIdField.getText());
            String root = rootField.getText();

            addRootDal.addrootfortoken(t_id, root);
            JOptionPane.showMessageDialog(null, "Root added successfully!");
            updateTable();
        }
    }

    private void updateSingleRootDialog() {
        JTextField oldRootField = new JTextField();
        JTextField newRootField = new JTextField();

        Object[] message = {
                "Old Root:", oldRootField,
                "New Root:", newRootField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Update Single Root", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String oldRoot = oldRootField.getText();
            String newRoot = newRootField.getText();

            updateSingleRootDAL.updateRoot(oldRoot, newRoot);
            JOptionPane.showMessageDialog(null, "Single Root updated successfully!");
            updateTable();
        }
    }

    private void updateTable() {
        tableModel.setColumnCount(0);
        tableModel.setRowCount(0);

        Map<Integer, String> allRoots = rootsInterface.showAllRoots();
        tableModel.addColumn("Verse Count");
        tableModel.addColumn("Root");

        for (Map.Entry<Integer, String> entry : allRoots.entrySet()) {
            Object[] row = {entry.getKey(), entry.getValue()};
            tableModel.addRow(row);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new rootsPL());
    }
}
