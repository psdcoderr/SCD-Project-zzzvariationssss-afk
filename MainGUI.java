package PL;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import BLL.BusinessLayer;
import BLL.Import_BO;
import BLL.PoemBO;
import BLL.Roots_BO;
import DAL.DBInterfaceFacade;
import DAL.DataLayerDB;
import DAL.DataLayerImport;
import DAL.DataLayerPoemDB;
import DAL.DataLayerRoots;
import DAL.Import_Interface;
import DAL.PoemInterface;

public class MainGUI extends JFrame {
    public MainGUI() {
        setTitle("Library Management System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel mainPanel = new JPanel(new BorderLayout());
        setContentPane(mainPanel);

        JLabel welcomeLabel = new JLabel("Welcome to the Library Management System");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(welcomeLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 2));
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        JButton booksButton = createStyledButton("BOOKS", Color.blue);
        JButton rootsButton = createStyledButton("ROOTS", Color.green);
        JButton poemsButton = createStyledButton("MANUAL ADD POEMS", Color.red);
        JButton importButton = createStyledButton("IMPORT POEMS", Color.orange);

        buttonPanel.add(booksButton);
        buttonPanel.add(rootsButton);
        buttonPanel.add(poemsButton);
        buttonPanel.add(importButton);
        booksButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openBooksPanel();
            }
        });

        rootsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openRootsPanel();
            }
        });

        poemsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openPoemsPanel();
            }
        });

        importButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openDataImportPanel();
            }
        });
    }

    private JButton createStyledButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setBackground(backgroundColor);
        button.setForeground(Color.white);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        return button;
    }

    private void openBooksPanel() {
        DBInterfaceFacade dataAccess = new DataLayerDB();
        BusinessLayer businessLayer = new BusinessLayer(dataAccess);
        PresentationLayer booksPanel = new PresentationLayer(businessLayer);
        booksPanel.setVisible(true);
    }

    private void openRootsPanel() {
        Roots_BO rootsBO = new Roots_BO(new DataLayerRoots());
        PL_Roots rootsPanel = new PL_Roots(rootsBO);
        rootsPanel.setVisible(true);
    }

    private void openPoemsPanel() {
        PoemInterface DAO = new DataLayerPoemDB();
        PoemBO poemBO = new PoemBO(DAO);
        PL_Poems poemsPanel = new PL_Poems(poemBO);
        poemsPanel.setVisible(true);
    }

    private void openDataImportPanel() {
        Import_Interface DAO = new DataLayerImport();
        Import_BO importBO = new Import_BO(DAO);
        Import_PL dataImportPanel = new Import_PL(importBO);
        dataImportPanel.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainGUI().setVisible(true);
            }
        });
    }
}
