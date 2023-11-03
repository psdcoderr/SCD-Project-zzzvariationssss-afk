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

import BLL.PoemBO;
import DAL.DataLayerPoemDB;
import DAL.PoemInterface;

public class PL_Poems extends JFrame {
    private PoemBO poemBO;

    private JTextField filenameField;
    private JButton parseButton;

    public PL_Poems(PoemBO poemBO) {
        this.poemBO = poemBO;

        setTitle("Poem Management");
        setSize(400, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2));
        
        JLabel filenameLabel = new JLabel("File Name:");
        filenameField = new JTextField(20);
        parseButton = new JButton("Parse and Add Poems");

        panel.add(filenameLabel);
        panel.add(filenameField);
        panel.add(parseButton);

        add(panel, BorderLayout.CENTER);

        parseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                parseAndAddPoems();
            }
        });
    }

    private void parseAndAddPoems() {
        String filename = filenameField.getText();
        poemBO.addData(filename);
        JOptionPane.showMessageDialog(this, "Poems parsed and added to the database successfully.");
    }

    public static void main(String[] args) {
        PoemInterface DAO = new DataLayerPoemDB();
        PoemBO poemBO = new PoemBO(DAO);

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new PL_Poems(poemBO).setVisible(true);
            }
        });
    }
}

