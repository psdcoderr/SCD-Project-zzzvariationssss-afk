package TokenPLOverall;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import BLL.EditTokenBLL;

public class EditTokenApp extends JFrame {

	private EditTokenBLL editTokenBll;
	private JTextField verseTextField;
	private JTextArea tokensTextArea;
	private JTextField oldTokenTextField;
	private JTextField newTokenTextField;

	public EditTokenApp() {
		editTokenBll = new EditTokenBLL();

		initializeUI();
	}

	private void initializeUI() {
		setTitle("Edit Token App");
		setSize(400, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		getContentPane().add(panel);
		placeComponents(panel);

		setVisible(true);
	}

	private void placeComponents(JPanel panel) {
		panel.setLayout(null);

		JLabel verseLabel = new JLabel("Verse:");
		verseLabel.setBounds(10, 20, 80, 25);
		panel.add(verseLabel);

		verseTextField = new JTextField(20);
		verseTextField.setBounds(100, 20, 165, 25);
		panel.add(verseTextField);

		JButton showTokensButton = new JButton("Show Tokens");
		showTokensButton.setBounds(10, 50, 150, 25);
		panel.add(showTokensButton);

		tokensTextArea = new JTextArea();
		JScrollPane scrollPane = new JScrollPane(tokensTextArea); // Add a scroll pane
		scrollPane.setBounds(10, 80, 350, 100);
		panel.add(scrollPane);

		JLabel oldTokenLabel = new JLabel("Old Token:");
		oldTokenLabel.setBounds(10, 190, 80, 25);
		panel.add(oldTokenLabel);

		oldTokenTextField = new JTextField(20);
		oldTokenTextField.setBounds(100, 190, 165, 25);
		panel.add(oldTokenTextField);

		JLabel newTokenLabel = new JLabel("New Token:");
		newTokenLabel.setBounds(10, 220, 80, 25);
		panel.add(newTokenLabel);

		newTokenTextField = new JTextField(20);
		newTokenTextField.setBounds(100, 220, 165, 25);
		panel.add(newTokenTextField);

		JButton updateTokenButton = new JButton("Update Token");
		updateTokenButton.setBounds(10, 250, 150, 25);
		panel.add(updateTokenButton);

		showTokensButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showTokensButtonClicked();
			}
		});

		updateTokenButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateTokenButtonClicked();
			}
		});
	}

	private void showTokensButtonClicked() {
		String verse = verseTextField.getText();
		List<String> tokens = editTokenBll.getAllTokensInVerse(verse);

		StringBuilder tokensText = new StringBuilder();
		for (String token : tokens) {
			tokensText.append(token).append("\n");
		}

		tokensTextArea.setText(tokensText.toString());
	}

	//main functionality here.
	private void updateTokenButtonClicked() {
		String oldToken = oldTokenTextField.getText();
		String newToken = newTokenTextField.getText();
		String verse=verseTextField.getText();
		editTokenBll.updateVerse(verse,oldToken,newToken);
		editTokenBll.updateToken(verse,oldToken, newToken);
		JOptionPane.showMessageDialog(this, "Token updated successfully");
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new EditTokenApp();
			}
		});
	}
}
