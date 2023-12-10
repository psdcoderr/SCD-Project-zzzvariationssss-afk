package PL;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import BLL.RootsBO;
import DAL.DataLayerRoots;
import RootsPLOverall.PL_SingleRoot;
import RootsPLOverall.PL_ViewAll;
import RootsPLOverall.PL_addSingleRoot;
import RootsPLOverall.PL_updateSingleRoot;

public class PL_Roots extends JFrame {

    private final RootsBO rootsBO;

    public PL_Roots() {
        super("Root Management System");

        rootsBO = new RootsBO(new DataLayerRoots());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());
        JButton addSingleRootButton = new JButton("Add Single Root");
        JButton singleRootSearchButton = new JButton("Single Root Search");
        JButton updateSingleRootButton = new JButton("Update Single Root");
        JButton viewAllRootsButton = new JButton("View All Roots");

        addSingleRootButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PL_addSingleRoot();
            }
        });

        singleRootSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PL_SingleRoot(rootsBO).setVisible(true);
            }
        });

        updateSingleRootButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PL_updateSingleRoot();
            }
        });

        viewAllRootsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PL_ViewAll(rootsBO).setVisible(true);
            }
        });
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addSingleRootButton);
        buttonPanel.add(singleRootSearchButton);
        buttonPanel.add(updateSingleRootButton);
        buttonPanel.add(viewAllRootsButton);

        add(buttonPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PL_Roots();
            }
        });
    }
}
