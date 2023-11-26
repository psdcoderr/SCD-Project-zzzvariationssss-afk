package RootsPLOverall;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import BLL.addSingleRootBLL;

public class PL_addSingleRoot extends JFrame {
    private addSingleRootBLL rootBLL;

    private DefaultTableModel versesTableModel;
    private JTable versesTable;

    private DefaultTableModel tokensTableModel;
    private JTable tokensTable;

    private DefaultTableModel suggestedRootsTableModel;
    private JTable suggestedRootsTable;

    private JTextField rootsTextField;

    public PL_addSingleRoot() {
        rootBLL = new addSingleRootBLL();

        initializeUI();
        loadDataToVersesTable();

      //  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setTitle("Root Management System");
        setVisible(true);
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        // Verses Table
        versesTableModel = new DefaultTableModel(new String[]{"Verse"}, 0);
        versesTable = new JTable(versesTableModel);
        JScrollPane versesScrollPane = new JScrollPane(versesTable);

        // Tokens Table
        tokensTableModel = new DefaultTableModel(new String[]{"Token"}, 0);
        tokensTable = new JTable(tokensTableModel);
        JScrollPane tokensScrollPane = new JScrollPane(tokensTable);

        // Suggested Roots Table
        suggestedRootsTableModel = new DefaultTableModel(new String[]{"Suggested Root"}, 0);
        suggestedRootsTable = new JTable(suggestedRootsTableModel);
        JScrollPane suggestedRootsScrollPane = new JScrollPane(suggestedRootsTable);

        // Fetch Tokens Button
        JButton fetchTokensButton = new JButton("Fetch Tokens");
        fetchTokensButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fetchTokens();
            }
        });

        // Add Root Button
        JButton addRootButton = new JButton("Add Root");
        addRootButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addRoot();
            }
        });

        // Suggested Roots Button
        JButton suggestedRootsButton = new JButton("Suggested Roots");
        suggestedRootsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showSuggestedRoots();
            }
        });

        // Roots Text Field
        rootsTextField = new JTextField();
        rootsTextField.setPreferredSize(new Dimension(150, 30));

        // Panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(fetchTokensButton);
        buttonPanel.add(addRootButton);
        buttonPanel.add(suggestedRootsButton);
        buttonPanel.add(new JLabel("Enter Roots:"));
        buttonPanel.add(rootsTextField);

        // Split pane for Tokens and Suggested Roots
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tokensScrollPane, suggestedRootsScrollPane);
        splitPane.setResizeWeight(0.5);

        // Panel for Tables and Split Pane
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(versesScrollPane, BorderLayout.NORTH);
        tablePanel.add(splitPane, BorderLayout.CENTER);

        // Adding components to the main frame
        add(tablePanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadDataToVersesTable() {
        List<String> allVerses = rootBLL.getAllVerses();
        for (String verse : allVerses) {
            versesTableModel.addRow(new Object[]{verse});
        }
    }

    private void fetchTokens() {
        // Get selected verse(s)
        int[] selectedRows = versesTable.getSelectedRows();
        List<String> selectedVerses = rootBLL.getSelectedVerses(selectedRows);

        // Get tokens for selected verses
        List<String> tokens = rootBLL.getTokensForVerses(selectedVerses);

        // Clear existing data
        tokensTableModel.setRowCount(0);

        // Populate tokens table
        for (String token : tokens) {
            tokensTableModel.addRow(new Object[]{token});
        }
    }

    private void showSuggestedRoots() {
        // Get selected token(s)
        int[] selectedRows = tokensTable.getSelectedRows();
        List<String> selectedTokens = rootBLL.getSelectedTokens(selectedRows);

        // Clear existing data
        suggestedRootsTableModel.setRowCount(0);

        // Populate suggested roots table
        for (String token : selectedTokens) {
            List<String> suggestedRoots = rootBLL.getSuggestedRoots(token);
            suggestedRootsTableModel.addRow(suggestedRoots.toArray());
        }
    }

    private void addRoot() {
        // Get selected token(s)
        int[] selectedRows = tokensTable.getSelectedRows();
        List<String> selectedTokens = rootBLL.getSelectedTokens(selectedRows);

        // Get roots from the text field
        String roots = rootsTextField.getText();

        // Add roots to tokens
        for (String token : selectedTokens) {
        	rootBLL.addRootsToTokens(Arrays.asList(token), roots);
        }

        // Show success message (you may want to enhance this)
        JOptionPane.showMessageDialog(this, "Roots added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PL_addSingleRoot();
            }
        });
    }
}
