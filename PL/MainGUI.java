package PL;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

import BLL.BusinessLayer;
import BLL.ManualAddBO;
import BLL.PoemBO;
import BLL.RootsBO;
import DAL.DBInterfaceFacade;
import DAL.DataLayerDB;
import DAL.DataLayerPoemDB;
import DAL.DataLayerRoots;
import DAL.ManualAddDataLayer;
import DAL.ManualAddInterface;
import DAL.PoemInterface;

public class MainGUI extends JFrame {
    public MainGUI() {
        setTitle("Library Management System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel mainPanel = new JPanel(new BorderLayout());
        setContentPane(mainPanel);
        setBackground(Color.GRAY);

        JLabel welcomeLabel = new JLabel("موسوعة الشعر العربية في العصر الجاهلية");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(welcomeLabel, BorderLayout.NORTH);

        Font customFont = new Font("Italic", Font.PLAIN, 32);
        welcomeLabel.setFont(customFont);
        mainPanel.setBorder(new LineBorder(Color.BLACK, 3));
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(welcomeLabel, BorderLayout.NORTH);
        mainPanel.setBackground(new Color(0, 0, 0, 0));
        JPanel buttonPanel = new JPanel(new GridLayout(1, 4)); // Changed to a single row layout
        mainPanel.add(buttonPanel, BorderLayout.SOUTH); // Moved to the bottom

        JButton booksButton = createStyledButton("BOOKS", Color.cyan, 14);
        JButton rootsButton = createStyledButton("ROOTS", Color.cyan, 14);
        JButton poemsButton = createStyledButton("ADD POEMS", Color.cyan, 12);
        JButton importButton = createStyledButton("IMPORT POEMS DATA", Color.cyan, 12);
        JButton tokensButton = createStyledButton("ADD TOKENS", Color.cyan, 12);
        JButton editToken = createStyledButton("EDIT TOKENS", Color.cyan, 12); 
        
        buttonPanel.add(booksButton);
        buttonPanel.add(rootsButton);
        buttonPanel.add(poemsButton);
        buttonPanel.add(importButton);
        buttonPanel.add(tokensButton);
        buttonPanel.add(editToken);

        booksButton.addActionListener(e -> openBooksPanel());
        rootsButton.addActionListener(e -> openRootsPanel());
        poemsButton.addActionListener(e -> openPoemsPanel());
        importButton.addActionListener(e -> openDataImportPanel());
        tokensButton.addActionListener(e -> openTokenPanel());
        editToken.addActionListener(e -> openEditTokenPanel()); 
    
    }

    private JButton createStyledButton(String text, Color backgroundColor, int fontSize) {
        JButton button = new JButton(text);
        button.setBackground(backgroundColor);
        button.setForeground(Color.black);
        button.setFont(new Font("Arial", Font.BOLD, fontSize));
        return button;
    }

    private void openBooksPanel() {
        DBInterfaceFacade dataAccess = new DataLayerDB();
        BusinessLayer businessLayer = new BusinessLayer(dataAccess);
        PresentationLayer booksPanel = new PresentationLayer(businessLayer);
        booksPanel.setVisible(true);
    }

    private void openRootsPanel() {
        RootsBO rootsBO = new RootsBO(new DataLayerRoots());
        PL_Roots rootsPanel = new PL_Roots();
        rootsPanel.setVisible(true);
    }

    private void openPoemsPanel() {
        PoemInterface DAO = new DataLayerPoemDB();
        PoemBO poemBO = new PoemBO(DAO);
        PoemGUI poemsPanel = new PoemGUI();
        poemsPanel.setVisible(true);
    }
    
    private void openTokenPanel() {
        TokenPLOverall.AddToken.main(new String[0]);
    }
    private void openDataImportPanel() {
        ManualAddInterface DAO = new ManualAddDataLayer();
        ManualAddBO importBO = new ManualAddBO(DAO);
        ManuallyAddVerse dataImportPanel = new ManuallyAddVerse(importBO);
        dataImportPanel.setVisible(true);
    }
    private void openEditTokenPanel() {
        TokenPLOverall.EditTokenApp.main(new String[0]);
    }

    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainGUI().setVisible(true));
    }
}
