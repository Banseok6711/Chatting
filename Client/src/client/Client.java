package client;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Client extends JFrame {
	private JTextField textField;
		
		
	public Client() {
		getContentPane().setLayout(null);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(154, 10, 320, 381);
		getContentPane().add(textArea);
		
		JButton btnNewButton = new JButton("쪽지보내기");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setBounds(12, 172, 130, 23);
		getContentPane().add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("User");
		lblNewLabel.setBounds(12, 14, 130, 15);
		getContentPane().add(lblNewLabel);
		
		JList list = new JList();
		list.setBounds(12, 38, 130, 124);
		getContentPane().add(list);
		
		textField = new JTextField();
		textField.setBounds(154, 401, 246, 21);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblChattingRoom = new JLabel("Chatting Room");
		lblChattingRoom.setBounds(12, 205, 130, 15);
		getContentPane().add(lblChattingRoom);
		
		JList list_1 = new JList();
		list_1.setBounds(12, 230, 130, 124);
		getContentPane().add(list_1);
		
		JButton btnNewButton_1 = new JButton("Room Join");
		btnNewButton_1.setBounds(12, 367, 130, 23);
		getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Create Room");
		btnNewButton_2.setBounds(12, 400, 130, 23);
		getContentPane().add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("send");
		btnNewButton_3.setBounds(406, 401, 68, 23);
		getContentPane().add(btnNewButton_3);
		
		setSize(502,498);
		setVisible(true);
			
		
		
	}
}
