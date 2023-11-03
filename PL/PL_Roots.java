package PL;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import BLL.Roots_BO;
import DAL.DataLayerRoots;

public class PL_Roots extends JFrame {
    private Roots_BO rootsBO;
    private JTextField rootField;
    private JTextArea resultArea;

    public PL_Roots(Roots_BO rootsBO) {
        this.rootsBO = rootsBO;

        setTitle("Root Management System");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2));

        JLabel rootLabel = new JLabel("Root:");
        rootField = new JTextField(20);

        JButton addButton = new JButton("Add Root");
        JButton updateButton = new JButton("Update Root");
        JButton deleteButton = new JButton("Delete Root");
        JButton viewButton = new JButton("View All Roots");

        resultArea = new JTextArea(10, 30);
        resultArea.setEditable(false);

        panel.add(rootLabel);
        panel.add(rootField);
        panel.add(addButton);
        panel.add(updateButton);
        panel.add(deleteButton);
        panel.add(viewButton);

        JScrollPane scrollPane = new JScrollPane(resultArea);

        panel.add(scrollPane);

        add(panel, BorderLayout.CENTER);
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addRoot();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateRoot();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteRoot();
            }
        });

        viewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewAllRoots();
            }
        });
    }

    private void addRoot() {
        String root = rootField.getText();
        rootsBO.addRoot(root);
        clearFields();
        resultArea.setText("Root added successfully.");
    }

    private void updateRoot() {
        String root = rootField.getText();
        String newRoot = JOptionPane.showInputDialog("Enter new root:");
        rootsBO.updateRoot(root, newRoot);
        clearFields();
        resultArea.setText("Root updated successfully.");
    }

    private void deleteRoot() {
        String root = rootField.getText();
        rootsBO.deleteRoot(root);
        clearFields();
        resultArea.setText("Root deleted successfully.");
    }

    private void viewAllRoots() {
        List<String> allRoots = rootsBO.showAllRoots();
        resultArea.setText("");
        for (String root : allRoots) {
            resultArea.append(root + "\n");
        }
    }

    private void clearFields() {
        rootField.setText("");
    }

    public static void main(String[] args) {
        Roots_BO rootsBO = new Roots_BO(new DataLayerRoots());

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new PL_Roots(rootsBO).setVisible(true);
            }
        });
    }
    //this is presentation layer of roots
}

