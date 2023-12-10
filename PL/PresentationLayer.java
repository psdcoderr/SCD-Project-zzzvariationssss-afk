package PL;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import BLL.BusinessLayer;
import BLL.BusinessLayerInterface;
import DAL.DBInterfaceFacade;
import DAL.DataLayerDB;
import DTO.BooksDTO;

public class PresentationLayer extends JFrame {
	 private BusinessLayerInterface businessLayer;

	    private JTextField addTitleField, addAuthorField, addYearField;
	    private JTextField searchField;
	    private JButton addButton, searchButton, showAllButton;
	    private JTable booksTable;
	    private DefaultTableModel tableModel;

	    private JTable poemsTable;
	    private DefaultTableModel poemsTableModel;
	    
	    public PresentationLayer(BusinessLayerInterface businessLayer) {
	        this.businessLayer = businessLayer;

	        setTitle("Book Management System");
	        setSize(800, 600);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	        initComponents();
	        initLayout();

	        booksTable.addMouseListener(new MouseAdapter() {
	            @Override
	            public void mouseClicked(MouseEvent e) {
	                int selectedRow = booksTable.getSelectedRow();
	                if (selectedRow >= 0 && selectedRow < tableModel.getRowCount()) {
	                    String selectedTitle = tableModel.getValueAt(selectedRow, 0).toString();
	                    showPoemsInTable(selectedTitle);
	                }
	            }
	        });
	    }

    private void initComponents() {
    	 addTitleField = new JTextField();
         addAuthorField = new JTextField();
         addYearField = new JTextField();
         addButton = new JButton("Add Book");
         searchField = new JTextField();
         searchButton = new JButton("Search Book");
         showAllButton = new JButton("Show All Books");

         String[] columnNames = {"Title", "Author", "Year Passed", "Update", "Delete"};
         tableModel = new DefaultTableModel(columnNames, 0);
         booksTable = new JTable(tableModel);
         
         String[] poemsColumnNames = {"Poem"};
         poemsTableModel = new DefaultTableModel(poemsColumnNames, 0);
         poemsTable = new JTable(poemsTableModel);

         addButton.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 addBook();
             }
         });

         searchButton.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 searchBook();
             }
         });

         showAllButton.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 populateTable();
             }
         });

         booksTable.getColumnModel().getColumn(3).setCellRenderer(new ButtonRenderer());
         booksTable.getColumnModel().getColumn(3).setCellEditor(new ButtonEditor(new JTextField()));
         booksTable.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer());
         booksTable.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor(new JTextField()));

        populateTable();
    }

    private void initLayout() {
        setLayout(new BorderLayout());

        JPanel addPanel = new JPanel(new GridLayout(4, 2));
        addPanel.add(new JLabel("Title:"));
        addPanel.add(addTitleField);
        addPanel.add(new JLabel("Author:"));
        addPanel.add(addAuthorField);
        addPanel.add(new JLabel("Year Passed:"));
        addPanel.add(addYearField);
        addPanel.add(new JLabel(""));
        addPanel.add(addButton);

        JPanel searchPanel = new JPanel(new FlowLayout());
        JLabel searchLabel = new JLabel("Search Book:");
        searchField.setColumns(20);
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(showAllButton);

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(addPanel, BorderLayout.NORTH);
        leftPanel.add(searchPanel, BorderLayout.CENTER);
        leftPanel.add(buttonPanel, BorderLayout.SOUTH);

        JPanel tablesPanel = new JPanel(new GridLayout(1, 2));

        JScrollPane booksScrollPane = new JScrollPane(booksTable);
        JScrollPane poemsScrollPane = new JScrollPane(poemsTable);

        tablesPanel.add(booksScrollPane);
        tablesPanel.add(poemsScrollPane);

        add(leftPanel, BorderLayout.WEST);
        add(tablesPanel, BorderLayout.CENTER);
    }



    private void showPoemsInTable(String bookTitle) {
        List<String> poems = businessLayer.show_poems(bookTitle);

        DefaultTableModel tableModel = (DefaultTableModel) poemsTable.getModel();
        tableModel.setRowCount(0);

        for (String poem : poems) {
            tableModel.addRow(new Object[]{poem});
        }
    }


    
    private void addBook() {
        String title = addTitleField.getText();
        String author = addAuthorField.getText();
        String yearPassedText = addYearField.getText();
        try {
            int yearPassed = Integer.parseInt(yearPassedText);

            if (isValidInput(title, author, yearPassedText)) {
                businessLayer.addData(title, author, String.valueOf(yearPassed));
                clearAddFields();
                populateTable();
            } else {
                JOptionPane.showMessageDialog(this, "Please enter valid data in all fields.", "Invalid Input", JOptionPane.WARNING_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid integer for the year.", "Invalid Year", JOptionPane.WARNING_MESSAGE);
        }
    }


    private boolean isValidInput(String title, String author, String yearPassed) {
        return !title.trim().isEmpty() && !author.trim().isEmpty() && !yearPassed.trim().isEmpty();
    }

    private void searchBook() {
        String searchTerm = searchField.getText().toLowerCase();

        tableModel.setRowCount(0);
        List<BooksDTO> booksList = businessLayer.showAllBooks();
        for (BooksDTO book : booksList) {
            if (book.getTitle().toLowerCase().contains(searchTerm)
                    || book.getAuthor().toLowerCase().contains(searchTerm)
                    || book.getYearPassed().toLowerCase().contains(searchTerm)) {
                Object[] rowData = {book.getTitle(), book.getAuthor(), book.getYearPassed(), "Update", "Delete"};
                tableModel.addRow(rowData);
            }
        }
    }

    private void populateTable() {
        tableModel.setRowCount(0);

        List<BooksDTO> booksList = businessLayer.showAllBooks();
        for (BooksDTO book : booksList) {
            Object[] rowData = {book.getTitle(), book.getAuthor(), book.getYearPassed(), "Update", "Delete"};
            tableModel.addRow(rowData);
        }
    }

    private void clearAddFields() {
        addTitleField.setText("");
        addAuthorField.setText("");
        addYearField.setText("");
    }


    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        protected JButton button;

        private String label;

        private boolean isPushed;

        public ButtonEditor(JTextField textField) {
            super(textField);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                }
            });
        }

        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            if (isSelected) {
                button.setForeground(table.getSelectionForeground());
                button.setBackground(table.getSelectionBackground());
            } else {
                button.setForeground(table.getForeground());
                button.setBackground(UIManager.getColor("Button.background"));
            }
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            isPushed = true;
            return button;
        }

        public Object getCellEditorValue() {
            if (isPushed) {
                SwingUtilities.invokeLater(() -> {
                    int selectedRow = booksTable.getSelectedRow();
                    if (selectedRow >= 0 && selectedRow < tableModel.getRowCount()) {
                        String title = tableModel.getValueAt(selectedRow, 0).toString();
                        if (label.equals("Update")) {
                            updateBook(title);
                        } else if (label.equals("Delete")) {
                            deleteBook(title);
                        }
                    } else {
                        JOptionPane.showMessageDialog(PresentationLayer.this, "Please select a row for update or delete.", "No Row Selected", JOptionPane.WARNING_MESSAGE);
                    }
                    isPushed = false;
                });
            }
            return label;
        }

        private void updateBook(String title) {
        
            BooksDTO existingBook = businessLayer.showSingleBook(title);
            JFrame updateFrame = new JFrame("Update Book");
            updateFrame.setSize(400, 300);
            updateFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            updateFrame.setLayout(new GridLayout(5, 2));
            JTextField titleField = new JTextField(existingBook.getTitle());
            JTextField authorField = new JTextField(existingBook.getAuthor());
            JTextField yearPassedField = new JTextField(existingBook.getYearPassed());

            JButton updateButton = new JButton("Update");

            updateButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String updatedTitle = titleField.getText();
                    String updatedAuthor = authorField.getText();
                    String updatedYearPassed = yearPassedField.getText();
                    if (isValidInput(updatedTitle, updatedAuthor, updatedYearPassed)) {
                        businessLayer.updateBook(title, updatedTitle, updatedAuthor, updatedYearPassed);
                        populateTable();
                        updateFrame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(updateFrame, "Please enter valid data in all fields.", "Invalid Input", JOptionPane.WARNING_MESSAGE);
                    }
                }
            });

            updateFrame.add(new JLabel("Title:"));
            updateFrame.add(titleField);
            updateFrame.add(new JLabel("Author:"));
            updateFrame.add(authorField);
            updateFrame.add(new JLabel("Year Passed:"));
            updateFrame.add(yearPassedField);
            updateFrame.add(new JLabel(""));
            updateFrame.add(updateButton);

            updateFrame.setVisible(true);
        }

        private void deleteBook(String title) {
            int option = JOptionPane.showConfirmDialog(PresentationLayer.this, "Are you sure you want to delete the book '" + title + "'?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                businessLayer.deleteBook(title);
                populateTable();
            }
        }
    }
    
    
    public static void main(String[] args) {
    	SwingUtilities.invokeLater(() -> {
    		DBInterfaceFacade dataLayer = new DataLayerDB();
    		BusinessLayerInterface businessLayer = new BusinessLayer(dataLayer);
    		PresentationLayer gui = new PresentationLayer(businessLayer);
    		gui.setVisible(true);
    	});
    }
}
