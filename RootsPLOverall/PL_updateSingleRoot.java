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

        versesTableModel = new DefaultTableModel(new String[]{"Verse"}, 0);
        versesTable = new JTable(versesTableModel);
        JScrollPane versesScrollPane = new JScrollPane(versesTable);

        tokensTableModel = new DefaultTableModel(new String[]{"Token"}, 0);
        tokensTable = new JTable(tokensTableModel);
        JScrollPane tokensScrollPane = new JScrollPane(tokensTable);

        rootsTableModel = new DefaultTableModel(new String[]{"Root"}, 0);
        rootsTable = new JTable(rootsTableModel);
        JScrollPane rootsScrollPane = new JScrollPane(rootsTable);

        JButton fetchTokensButton = new JButton("Fetch Tokens");
        fetchTokensButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fetchTokens();
            }
        });

        oldRootTextField = new JTextField();
        oldRootTextField.setPreferredSize(new Dimension(150, 30));
        oldRootTextField.setBorder(BorderFactory.createTitledBorder("Old Root"));

        newRootTextField = new JTextField();
        newRootTextField.setPreferredSize(new Dimension(150, 30));
        newRootTextField.setBorder(BorderFactory.createTitledBorder("New Root"));

        JButton updateRootButton = new JButton("Update Root");
        updateRootButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateRoot();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(fetchTokensButton);
        buttonPanel.add(oldRootTextField);
        buttonPanel.add(newRootTextField);
        buttonPanel.add(updateRootButton);

        JSplitPane splitPaneTokens = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tokensScrollPane, rootsScrollPane);
        splitPaneTokens.setResizeWeight(0.5);

        JSplitPane splitPaneVerses = new JSplitPane(JSplitPane.VERTICAL_SPLIT, versesScrollPane, splitPaneTokens);
        splitPaneVerses.setResizeWeight(0.5);

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(splitPaneVerses, BorderLayout.CENTER);

        add(tablePanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

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
        int[] selectedRows = versesTable.getSelectedRows();
        List<String> selectedVerses = rootBLL.getSelectedVerses(selectedRows);

        List<String> tokens = rootBLL.getTokensForVerses(selectedVerses);

        tokensTableModel.setRowCount(0);
        rootsTableModel.setRowCount(0);

        for (String token : tokens) {
            tokensTableModel.addRow(new Object[]{token});
        }
    }

    private void fetchRootsForToken() {
        int selectedRow = tokensTable.getSelectedRow();
        if (selectedRow >= 0) {
            String selectedToken = (String) tokensTableModel.getValueAt(selectedRow, 0);

            List<String> roots = rootBLL.getRootsForToken(selectedToken);

            rootsTableModel.setRowCount(0);

            for (String root : roots) {
                rootsTableModel.addRow(new Object[]{root});
            }

            if (roots.size() > 0) {
                oldRootTextField.setText(roots.get(0));
            }
        }
    }

    private void updateRoot() {
        int[] selectedRows = tokensTable.getSelectedRows();
        List<String> selectedTokens = rootBLL.getSelectedTokens(selectedRows);

        String oldRoot = oldRootTextField.getText();
        String newRoot = newRootTextField.getText();

        for (String token : selectedTokens) {
            rootBLL.updateRoot(oldRoot, newRoot);
        }

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
