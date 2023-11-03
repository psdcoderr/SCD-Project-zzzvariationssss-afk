package PL;


import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import BLL.Import_BO;
import DAL.DataLayerImport;
import DAL.Import_Interface;

public class Presentation_layer extends JFrame {
    private Import_BO importBO;

    private JTextField bookTitleField;
    private JTextField authorField;
    private JTextField yearPublishedField;
    private JTextField poemTitleField;
    private JTextField versesField;
    private JButton importButton;

    public Presentation_layer(Import_BO importBO) {
        this.importBO = importBO;

        setTitle("Data Import");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2));

        JLabel bookTitleLabel = new JLabel("Book Title:");
        bookTitleField = new JTextField(20);
        JLabel authorLabel = new JLabel("Author:");
        authorField = new JTextField(20);
        JLabel yearPublishedLabel = new JLabel("Year Passed:");
        yearPublishedField = new JTextField(20);
        JLabel poemTitleLabel = new JLabel("Poem Title:");
        poemTitleField = new JTextField(20);
        JLabel versesLabel = new JLabel("Verses:");
        versesField = new JTextField(20);

        importButton = new JButton("Import Data");

        panel.add(bookTitleLabel);
        panel.add(bookTitleField);
        panel.add(authorLabel);
        panel.add(authorField);
        panel.add(yearPublishedLabel);
        panel.add(yearPublishedField);
        panel.add(poemTitleLabel);
        panel.add(poemTitleField);
        panel.add(versesLabel);
        panel.add(versesField);
        panel.add(importButton);

        add(panel, BorderLayout.CENTER);

        importButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                importData();
            }
        });
    }

    private void importData() {
        String bookTitle = bookTitleField.getText();
        String author = authorField.getText();
        String yearPublished = yearPublishedField.getText();
        String poemTitle = poemTitleField.getText();
        String[] verses = versesField.getText().split("---");

        importBO.importData(bookTitle, author, yearPublished, poemTitle, verses);
        JOptionPane.showMessageDialog(this, "Data imported successfully.");
    }

    public static void main(String[] args) {
        Import_Interface DAO = new DataLayerImport();
        Import_BO importBO = new Import_BO(DAO);

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Presentation_layer(importBO).setVisible(true);
            }
        });
    }
}
//GUI