package PL;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import BLL.PoemBO;
import DAL.DataLayerPoemDB;
import DTO.BooksDTO;

public class PoemGUI extends JFrame {
    private PoemBO poemBO;
    private DefaultTableModel tableModel;
    private JTable poemTable;

    public PoemGUI() {
        initialize();
        setPoemBO(new PoemBO(new DataLayerPoemDB())); 
        loadDataIntoTable();
        setTitle("Poem Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        add(createTablePanel(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);
    }

    private void initialize() {
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Poem Title");
        poemTable = new JTable(tableModel);
    }

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JScrollPane scrollPane = new JScrollPane(poemTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        return tablePanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton viewAllButton = new JButton("View All Poems");
        JButton viewSingleButton = new JButton("View Single Poem");
        JButton addButton = new JButton("Add Poem");
        JButton updateButton = new JButton("Update Poem");
        JButton deleteButton = new JButton("Delete Poem");

        poemTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    viewSinglePoem();
                }
            }
        });
        
        viewAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadDataIntoTable();
            }
        });

        viewSingleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewSinglePoem();
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addPoem();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updatePoem();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deletePoem();
            }
        });

        buttonPanel.add(viewAllButton);
        buttonPanel.add(viewSingleButton);
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        return buttonPanel;
    }

    private void loadDataIntoTable() {
        tableModel.setRowCount(0);
        List<BooksDTO> poems = poemBO.getAllBooks();

        for (BooksDTO poem : poems) {
            tableModel.addRow(new Object[]{poem.getTitle()});
        }
    }

    private void viewSinglePoem() {
        int selectedRow = poemTable.getSelectedRow();
        if (selectedRow != -1) {
            String poemTitle = (String) poemTable.getValueAt(selectedRow, 0);
            List<String> verses = poemBO.viewSinglePoem(poemTitle);

            DefaultTableModel verseTableModel = new DefaultTableModel();
            verseTableModel.addColumn("Verse");

            JTable verseTable = new JTable(verseTableModel);
            verseTable.setEnabled(false); 
            for (String verse : verses) {
                verseTableModel.addRow(new Object[]{verse});
            }

            JDialog verseDialog = new JDialog(this, "Verses for Poem: " + poemTitle, true);
            verseDialog.setLayout(new BorderLayout());
            verseDialog.add(new JScrollPane(verseTable), BorderLayout.CENTER);
            verseDialog.pack();
            verseDialog.setLocationRelativeTo(this);
            verseDialog.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a poem to view.", "View Single Poem", JOptionPane.WARNING_MESSAGE);
        }
    }


    private void displayVerses(String poemTitle, List<String> verses) {
        StringBuilder versesText = new StringBuilder("Verses for Poem: " + poemTitle + "\n\n");
        for (String verse : verses) {
            versesText.append(verse).append("\n");
        }
        JTextArea textArea = new JTextArea(versesText.toString());
        textArea.setEditable(false);
        JOptionPane.showMessageDialog(this, new JScrollPane(textArea), "View Single Poem", JOptionPane.INFORMATION_MESSAGE);
    }

    private void addPoem() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose a file containing poems");
        int returnValue = fileChooser.showOpenDialog(this);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            String selectedFile = fileChooser.getSelectedFile().getPath();
            String bookTitle = JOptionPane.showInputDialog(this, "Enter Book Title:");
            String author = JOptionPane.showInputDialog(this, "Enter Author:");
            String yearPassed = JOptionPane.showInputDialog(this, "Enter Year Passed:");

            List<String> verseList = poemBO.addData(selectedFile, bookTitle, author, yearPassed);
            if (verseList != null && !verseList.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Poems added successfully.", "Add Poem", JOptionPane.INFORMATION_MESSAGE);
                loadDataIntoTable(); // Refresh the table
            } else {
                JOptionPane.showMessageDialog(this, "Error adding poems.", "Add Poem", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void updatePoem() {
        int selectedRow = poemTable.getSelectedRow();
        if (selectedRow != -1) {
            String oldTitle = (String) poemTable.getValueAt(selectedRow, 0);
            String newTitle = JOptionPane.showInputDialog(this, "Enter New Title:", oldTitle);
            String newAuthor = JOptionPane.showInputDialog(this, "Enter New Author:");
            String newYearPassed = JOptionPane.showInputDialog(this, "Enter New Year Passed:");

            poemBO.updatePoem(oldTitle, newTitle, newAuthor, newYearPassed);
            JOptionPane.showMessageDialog(this, "Poem updated successfully.", "Update Poem", JOptionPane.INFORMATION_MESSAGE);
            loadDataIntoTable();
        } else {
            JOptionPane.showMessageDialog(this, "Please select a poem to update.", "Update Poem", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void deletePoem() {
        int selectedRow = poemTable.getSelectedRow();
        if (selectedRow != -1) {
            String poemTitle = (String) poemTable.getValueAt(selectedRow, 0);
            int confirmDialog = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete the poem?", "Delete Poem", JOptionPane.YES_NO_OPTION);
            if (confirmDialog == JOptionPane.YES_OPTION) {
                poemBO.deletePoem(poemTitle);
                JOptionPane.showMessageDialog(this, "Poem deleted successfully.", "Delete Poem", JOptionPane.INFORMATION_MESSAGE);
                loadDataIntoTable(); 
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a poem to delete.", "Delete Poem", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PoemGUI().setVisible(true);
            }
        });
    }

    public void setPoemBO(PoemBO poemBO) {
        this.poemBO = poemBO;
    }
}
