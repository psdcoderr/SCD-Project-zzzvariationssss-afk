package TokenPLOverall;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import BLL.TokenBO;

public class AddToken {
    public static void main(String[] args) {
        TokenBO tokenBLL = new TokenBO();
        List<String> allVerses = tokenBLL.getAllVerses();

        JFrame frame = new JFrame("Verse Display");
        frame.setSize(400, 300);

        DefaultTableModel model = new DefaultTableModel();
        JTable table = new JTable(model);

        model.addColumn("Verse");

        for (String verse : allVerses) {
            model.addRow(new Object[]{verse});
        }
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane);

        JPanel buttonPanel = new JPanel();

        DefaultListModel<String> verseListModel = new DefaultListModel<>();
        JList<String> verseList = new JList<>(verseListModel);
        verseList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        frame.add(new JScrollPane(verseList));

        JLabel tokenLabel = new JLabel("Enter Tokens:");
        frame.add(tokenLabel);

        JTextArea tokenTextArea = new JTextArea();
        frame.add(tokenTextArea);

        JButton addButton = new JButton("Add Tokens");
        buttonPanel.add(addButton);
        frame.add(buttonPanel);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] selectedRows = table.getSelectedRows();
                if (selectedRows.length > 0) {
                    for (int selectedRow : selectedRows) {
                        String selectedVerse = (String) table.getValueAt(selectedRow, 0);
                        verseListModel.addElement(selectedVerse);
                    }
                    String tokens = tokenTextArea.getText();

                    for (int i = 0; i < verseListModel.size(); i++) {
                        String selectedVerse = verseListModel.getElementAt(i);
                        tokenBLL.addNewToken(selectedVerse, tokens);
                    }

                    verseListModel.clear();
                    tokenTextArea.setText("");

                } else {
                    JOptionPane.showMessageDialog(frame, "Please select one or more verses from the table.");
                }
            }
        });

        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.setVisible(true);
    }
}
