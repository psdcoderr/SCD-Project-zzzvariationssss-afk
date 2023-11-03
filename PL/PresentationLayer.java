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

import BLL.BusinessLayer;
import DAL.DBInterfaceFacade;
import DAL.DataLayerDB;
import DTO.BooksDTO;

public class PresentationLayer extends JFrame {
    private BusinessLayer businessLayer;
    private JTextField titleField;
    private JTextField authorField;
    private JTextField yearPassedField;
    private JTextArea resultArea;

    public PresentationLayer(BusinessLayer businessLayer) {
        this.businessLayer = businessLayer;

        setTitle("Book Management System");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2));

        JLabel titleLabel = new JLabel("Title:");
        titleField = new JTextField(20);
        JLabel authorLabel = new JLabel("Author:");
        authorField = new JTextField(20);
        JLabel yearPassedLabel = new JLabel("Year Passed:");
        yearPassedField = new JTextField(20);

        JButton addButton = new JButton("Add Book");
        JButton updateButton = new JButton("Update Book");
        JButton deleteButton = new JButton("Delete Book");
        JButton viewButton = new JButton("View All Books");
        JButton viewSingleButton = new JButton("View Single Book");


        resultArea = new JTextArea(10, 30);
        resultArea.setEditable(false);

        panel.add(titleLabel);
        panel.add(titleField);
        panel.add(authorLabel);
        panel.add(authorField);
        panel.add(yearPassedLabel);
        panel.add(yearPassedField);
        panel.add(addButton);
        panel.add(updateButton);
        panel.add(deleteButton);
        panel.add(viewButton);
        panel.add(viewSingleButton);

        JScrollPane scrollPane = new JScrollPane(resultArea);

        panel.add(scrollPane);

        add(panel, BorderLayout.CENTER);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addBook();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateBook();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteBook();
            }
        });

        viewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewAllBooks();
            }
        });
        
        viewSingleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewSingleBook();
            }
        });

    }

    private void addBook() {
        String title = titleField.getText();
        String author = authorField.getText();
        String yearPassed = yearPassedField.getText();
        businessLayer.addData(title, author, yearPassed);
        clearFields();
        resultArea.setText("Book added successfully.");
    }

    private void updateBook() {
        String title = titleField.getText();
        String author = authorField.getText();
        String yearPassed = yearPassedField.getText();
        String newTitle = JOptionPane.showInputDialog("Enter new title:");
        businessLayer.updateBook(title, newTitle, author, yearPassed);
        clearFields();
        resultArea.setText("Book updated successfully.");
    }

    private void deleteBook() {
        String title = titleField.getText();
        businessLayer.delBook(title);
        clearFields();
        resultArea.setText("Book deleted successfully.");
    }

    private void viewAllBooks() {
        List<BooksDTO> allBooks = businessLayer.ShowAllBooks();
        resultArea.setText("");
        for (BooksDTO book : allBooks) {
            resultArea.append(book + "\n");
        }
    }

    private void clearFields() {
        titleField.setText("");
        authorField.setText("");
        yearPassedField.setText("");
    }
    
    private void viewSingleBook() {
        String title = titleField.getText().trim();
        if (!title.isEmpty()) {
            BooksDTO book = businessLayer.showSingleBook(title);
            if (book != null) {
                resultArea.setText("Book Found!\nDetails are:\n" +
                                   "Title: " + book.getTitle() +
                                   "\nAuthor: " + book.getAuthor() +
                                   "\nYear Passed: " + book.getYearPassed());
            } else {
                resultArea.setText("Book Not found!");
            }
        } else {
            resultArea.setText("Please enter a title to search.");
        }
    }


    public static void main(String[] args) {
        DBInterfaceFacade dataAccess = new DataLayerDB();
        BusinessLayer businessLayer = new BusinessLayer(dataAccess);

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new PresentationLayer(businessLayer).setVisible(true);
            }
        });
    }
}
