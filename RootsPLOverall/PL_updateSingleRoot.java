package RootsPLOverall;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import BLL.updateSingleRootBLL;

public class PL_updateSingleRoot extends JFrame {
    private updateSingleRootBLL rootBLL;

    private DefaultTableModel versesTableModel;
    private JTable versesTable;

    private DefaultTableModel tokensTableModel;
    private JTable tokensTable;

    private DefaultTableModel rootsTableModel;
    private JTable rootsTable;

    private JTextField oldRootTextField;
    private JTextField newRootTextField;

    public PL_updateSingleRoot() {
        rootBLL = new updateSingleRootBLL();

        initializeUI();
        loadDataToVersesTable();

       // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setTitle("Root Update System");
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

        // Roots Table
        rootsTableModel = new DefaultTableModel(new String[]{"Root"}, 0);
        rootsTable = new JTable(rootsTableModel);
        JScrollPane rootsScrollPane = new JScrollPane(rootsTable);

        // Fetch Tokens Button
        JButton fetchTokensButton = new JButton("Fetch Tokens");
        fetchTokensButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fetchTokens();
            }
        });

        // Old Root Text Field
        oldRootTextField = new JTextField();
        oldRootTextField.setPreferredSize(new Dimension(150, 30));
        oldRootTextField.setBorder(BorderFactory.createTitledBorder("Old Root"));

        // New Root Text Field
        newRootTextField = new JTextField();
        newRootTextField.setPreferredSize(new Dimension(150, 30));
        newRootTextField.setBorder(BorderFactory.createTitledBorder("New Root"));

        // Update Root Button
        JButton updateRootButton = new JButton("Update Root");
        updateRootButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateRoot();
            }
        });

        // Panel for buttons and text fields
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(fetchTokensButton);
        buttonPanel.add(oldRootTextField);
        buttonPanel.add(newRootTextField);
        buttonPanel.add(updateRootButton);

        // Split pane for Tokens and Roots
        JSplitPane splitPaneTokens = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tokensScrollPane, rootsScrollPane);
        splitPaneTokens.setResizeWeight(0.5);

        // Split pane for Verses and Tokens/Roots
        JSplitPane splitPaneVerses = new JSplitPane(JSplitPane.VERTICAL_SPLIT, versesScrollPane, splitPaneTokens);
        splitPaneVerses.setResizeWeight(0.5);

        // Panel for Tables and Split Panes
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(splitPaneVerses, BorderLayout.CENTER);

        // Adding components to the main frame
        add(tablePanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Adding a mouse listener to the tokensTable to fetch roots for the selected token
        tokensTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                fetchRootsForToken();
            }
        });
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
        rootsTableModel.setRowCount(0);

        // Populate tokens table
        for (String token : tokens) {
            tokensTableModel.addRow(new Object[]{token});
        }
    }

    private void fetchRootsForToken() {
        // Get selected token
        int selectedRow = tokensTable.getSelectedRow();
        if (selectedRow >= 0) {
            String selectedToken = (String) tokensTableModel.getValueAt(selectedRow, 0);

            // Get roots for the selected token
            List<String> roots = rootBLL.getRootsForToken(selectedToken);

            // Clear existing data
            rootsTableModel.setRowCount(0);

            // Populate roots table
            for (String root : roots) {
                rootsTableModel.addRow(new Object[]{root});
            }

            // Copy the selected root to the "Old Root" text field
            if (roots.size() > 0) {
                oldRootTextField.setText(roots.get(0));
            }
        }
    }

    private void updateRoot() {
        // Get selected token(s)
        int[] selectedRows = tokensTable.getSelectedRows();
        List<String> selectedTokens = rootBLL.getSelectedTokens(selectedRows);

        // Get old and new roots from text fields
        String oldRoot = oldRootTextField.getText();
        String newRoot = newRootTextField.getText();

        // Update roots for selected tokens
        for (String token : selectedTokens) {
            rootBLL.updateRoot(oldRoot, newRoot);
        }

        // Show success message (you may want to enhance this)
        JOptionPane.showMessageDialog(this, "Roots updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PL_updateSingleRoot();
            }
        });
    }
}
