package PL;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import BLL.PoemBO;
import DAL.DataLayerPoemDB;
import DAL.PoemInterface;

public class PL_PoemParser extends JFrame {
    private PoemBO poemBO;

    private JTextField bookTitleField;
    private JTextField authorField;
    private JButton parseButton;

    public PL_PoemParser(PoemBO poemBO) {
        this.poemBO = poemBO;

        setTitle("Poem Parser");
        setSize(400, 200);
        setLayout(new BorderLayout());
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        bookTitleField = new JTextField();
        authorField = new JTextField();
        parseButton = new JButton("Parse Poems");

        inputPanel.add(new JLabel("Book Title:"));
        inputPanel.add(bookTitleField);
        inputPanel.add(new JLabel("Author:"));
        inputPanel.add(authorField);
        inputPanel.add(new JLabel(""));
        inputPanel.add(parseButton);

        add(inputPanel, BorderLayout.CENTER);

        parseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parsePoems();
            }
        });
    }

    private void parsePoems() {
        String bookTitle = bookTitleField.getText();
        String author = authorField.getText();

        if (!bookTitle.isEmpty() && !author.isEmpty()) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int result = fileChooser.showOpenDialog(this);

            if (result == JFileChooser.APPROVE_OPTION) {
                String fileName = fileChooser.getSelectedFile().getAbsolutePath();
                List<String> parsedVerses = poemBO.ParsePoems(fileName, bookTitle, author);

                StringBuilder resultText = new StringBuilder("Poems Parsed and Added Successfully:\n");
                for (String verse : parsedVerses) {
                    resultText.append(verse).append("\n");
                }

                JOptionPane.showMessageDialog(this, resultText.toString(), "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Please choose a file.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please fill in all the fields.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
                    e.printStackTrace();
                }

                PoemInterface DAO = new DataLayerPoemDB();
                PoemBO poemBO = new PoemBO(DAO);

                PL_PoemParser frame = new PL_PoemParser(poemBO);
                frame.setVisible(true);
            }
        });
    }
}
