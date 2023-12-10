package PL;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import BLL.PoemBO;
import DAL.DataLayerPoemDB;
import DAL.PoemInterface;
import DTO.BooksDTO;

public class PL_Poems extends JFrame {
    private PoemBO poemBO;
    private DefaultTableModel bookTableModel;
    private DefaultTableModel poemTableModel;
    private JTable bookTable;
    private JTable poemTable;

    private JTextField filenameField;
    private JButton browseButton;
    private JButton parseButton;
    private JTextField bookTitleField;
    private JTextField authorField;
    private JTextField yearPassedField;
    private JButton viewAllPoemsButton;
    private JButton viewSinglePoemButton;
    private JTextField viewSinglePoemField;
    private JButton updatePoemButton;
    private JButton deletePoemButton;
    private JTextField updatePoemTitleField;
    private JTextField updatedPoemField;
    private JButton viewAllBooksButton;

    private JTextField bookTitleFieldParser;
    private JTextField authorFieldParser;
    private JButton parseButtonParser;

    public PL_Poems(PoemBO poemBO) {
        this.poemBO = poemBO;

        setTitle("Poem Management");
        setSize(800, 400);
        setLayout(new BorderLayout());
        setBackground(Color.GRAY);

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        JMenuBar menuBar = createMenuBar();
        setJMenuBar(menuBar);

        JPanel bookPanel = new JPanel();
        bookPanel.setLayout(new GridLayout(1, 2));

        JPanel poemPanel = new JPanel();
        poemPanel.setLayout(new GridLayout(3, 2));

        bookTable = new JTable();
        bookTableModel = new DefaultTableModel();
        bookTable.setModel(bookTableModel);
        bookTableModel.addColumn("Book Title");
        bookTableModel.addColumn("Author");
        bookTableModel.addColumn("Year Passed");
        JScrollPane bookScrollPane = new JScrollPane(bookTable);
        bookPanel.add(bookScrollPane);
        bookTable.setVisible(true);

        poemTable = new JTable();
        poemTableModel = new DefaultTableModel();
        poemTable.setModel(poemTableModel);
        JScrollPane poemScrollPane = new JScrollPane(poemTable);
        poemPanel.add(poemScrollPane);
        poemTable.setVisible(true);

        JLabel filenameLabel = new JLabel("File Name:");
        filenameField = new JTextField(20);
        browseButton = new JButton("Browse");
        parseButton = new JButton("Parse and Add Poems");

        JLabel bookTitleLabel = new JLabel("Book Title:");
        bookTitleField = new JTextField(20);
        JLabel authorLabel = new JLabel("Author:");
        authorField = new JTextField(20);
        JLabel yearPassedLabel = new JLabel("Year Passed:");
        yearPassedField = new JTextField(20);

        viewAllBooksButton = new JButton("View All Books");
        viewAllPoemsButton = new JButton("View All Poems");
        viewSinglePoemButton = new JButton("View Single Poem");
        viewSinglePoemField = new JTextField(20);

        updatePoemButton = new JButton("Update Poem");
        deletePoemButton = new JButton("Delete Poem");
        updatePoemTitleField = new JTextField(20);
        updatedPoemField = new JTextField(20);

        poemPanel.add(createHorizontalBox(filenameLabel, filenameField, browseButton, parseButton));
        poemPanel.add(
                createHorizontalBox(bookTitleLabel, bookTitleField, authorLabel, authorField, yearPassedLabel,
                        yearPassedField));
        poemPanel.add(createHorizontalBox(viewAllPoemsButton, viewSinglePoemField, viewSinglePoemButton));
        poemPanel.add(createHorizontalBox(updatePoemTitleField, updatedPoemField, updatePoemButton, deletePoemButton));

        JPanel poemParserPanel = new JPanel();
        poemParserPanel.setLayout(new GridLayout(3, 2));

        bookTitleFieldParser = new JTextField();
        authorFieldParser = new JTextField();
        parseButtonParser = new JButton("Parse Poems");

        poemParserPanel.add(new JLabel("Book Title:"));
        poemParserPanel.add(bookTitleFieldParser);
        poemParserPanel.add(new JLabel("Author:"));
        poemParserPanel.add(authorFieldParser);
        poemParserPanel.add(new JLabel(""));
        poemParserPanel.add(parseButtonParser);

        add(poemPanel, BorderLayout.NORTH);
        add(poemParserPanel, BorderLayout.CENTER);
        add(bookPanel, BorderLayout.SOUTH);

        customizeComponents();

        browseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                browseFile();
            }
        });

        parseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                parseAndAddPoems();
            }
        });

        viewAllPoemsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewAllPoems();
            }
        });

        viewSinglePoemButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                poemBO.viewSinglePoem(getTitle());
            }
        });

        updatePoemButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updatePoem();
            }
        });

        deletePoemButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deletePoem();
            }
        });

        parseButtonParser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parsePoemsParser();
            }
        });

        viewAllBooksButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewAllBooks();
            }
        });

        loadBooks();
    }

    private void parsePoemsParser() {
        String bookTitle = bookTitleFieldParser.getText();
        String author = authorFieldParser.getText();
        if (!bookTitle.isEmpty() && !author.isEmpty()) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int result = fileChooser.showOpenDialog(this);

            if (result == JFileChooser.APPROVE_OPTION) {
                String fileName = fileChooser.getSelectedFile().getAbsolutePath();
                try {
                    List<String> parsedVerses = poemBO.ParsePoems(fileName, bookTitle, author);
                    updatePoemTable(parsedVerses);
                    JOptionPane.showMessageDialog(this, "Poems parsed and added to the database successfully.");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error adding poems to the database.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please choose a file.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please fill in all the fields.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viewAllBooks() {
        List<BooksDTO> books = poemBO.getAllBooks();
        updateBookTable(books);
    }

    private void browseFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Text files", "txt"));
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            filenameField.setText(selectedFile.getAbsolutePath());
        }
    }

    private void viewAllPoems() {
        List<String> poems = poemBO.viewAllPoems();
        updatePoemTable(poems);
    }

    private void updatePoem() {
        String oldTitle = updatePoemTitleField.getText();
        String newTitle = updatedPoemField.getText();

        if (!oldTitle.isEmpty() && !newTitle.isEmpty()) {
            try {
                poemBO.updatePoem(oldTitle, newTitle, "", "");
                JOptionPane.showMessageDialog(this, "Poem updated successfully.");
                viewAllPoems(); // Refresh the view after updating
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error updating the poem.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please fill in all the fields.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deletePoem() {
        String title = updatePoemTitleField.getText();

        if (!title.isEmpty()) {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this poem?",
                    "Confirm Deletion", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    poemBO.deletePoem(title);
                    JOptionPane.showMessageDialog(this, "Poem deleted successfully.");
                    viewAllPoems(); // Refresh the view after deletion
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error deleting the poem.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please enter a poem title.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void customizeComponents() {
        bookTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        bookTable.setGridColor(Color.BLACK);
        bookTable.setShowGrid(true);
        bookTable.setSelectionBackground(new Color(200, 200, 255));
        bookTable.setSelectionForeground(Color.BLACK);

        browseButton.setBackground(new Color(135, 206, 250));
        browseButton.setForeground(Color.BLACK);
        browseButton.setFont(new Font("Arial", Font.PLAIN, 14));
        parseButton.setBackground(new Color(50, 205, 50));
        parseButton.setForeground(Color.BLACK);
        parseButton.setFont(new Font("Arial", Font.PLAIN, 14));
        viewAllPoemsButton.setBackground(new Color(255, 165, 0));
        viewAllPoemsButton.setForeground(Color.BLACK);
        viewAllPoemsButton.setFont(new Font("Arial", Font.PLAIN, 14));
        viewSinglePoemButton.setBackground(new Color(255, 165, 0));
        viewSinglePoemButton.setForeground(Color.BLACK);
        viewSinglePoemButton.setFont(new Font("Arial", Font.PLAIN, 14));
        viewAllBooksButton.setBackground(new Color(255, 165, 0));
        viewAllBooksButton.setForeground(Color.BLACK);
        viewAllBooksButton.setFont(new Font("Arial", Font.PLAIN, 14));
    }

    private void parseAndAddPoems() {
        String filename = filenameField.getText();
        String bookTitle = bookTitleField.getText();
        String author = authorField.getText();
        String yearPassed = yearPassedField.getText();

        if (!filename.isEmpty() && !bookTitle.isEmpty() && !author.isEmpty() && !yearPassed.isEmpty()) {
            try {
                List<String> poems = poemBO.addData(filename, bookTitle, author, yearPassed);
                updatePoemTable(poems);
                JOptionPane.showMessageDialog(this, "Poems parsed and added to the database successfully.");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error adding poems to the database.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please fill in all the fields.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadBooks() {
        List<BooksDTO> books = poemBO.loadBooks();
        updateBookTable(books);
    }

    private void updateBookTable(List<BooksDTO> books) {
        bookTableModel.setRowCount(0);
        for (BooksDTO book : books) {
            bookTableModel.addRow(new Object[] { book.getTitle(), book.getAuthor(), book.getYearPassed() });
        }
    }

    private void updatePoemTable(List<String> poems) {
        poemTableModel.setRowCount(0);
        for (String poem : poems) {
            poemTableModel.addRow(new Object[] { poem });
        }
    }

    private Box createHorizontalBox(JComponent... components) {
        Box box = Box.createHorizontalBox();
        for (JComponent component : components) {
            box.add(component);
        }
        return box;
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        fileMenu.add(exitMenuItem);
        menuBar.add(fileMenu);
        return menuBar;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                        | UnsupportedLookAndFeelException e) {
                    e.printStackTrace();
                }
                PoemInterface poemDB = new DataLayerPoemDB();
                PoemBO poemBO = new PoemBO(poemDB);
                PL_Poems poemsUI = new PL_Poems(poemBO);
                poemsUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                poemsUI.setVisible(true);
            }
        });
    }
}
