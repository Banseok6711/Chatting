package server;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

public class Server extends JFrame {
	
	public static void main(String[] args){
		Server server = new Server();
	}
	
	private JTextField textField;
	private JTextField textField_1;
	
	
	public Server() {
		getContentPane().setLayout(null);
		
		JLabel lblPort = new JLabel("Port");
		lblPort.setBounds(47, 236, 57, 15);
		getContentPane().add(lblPort);
		
		textField = new JTextField();
		textField.setBounds(115, 233, 116, 21);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("서버 실행");
		btnNewButton.setBounds(27, 278, 97, 23);
		getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("서버 중지");
		btnNewButton_1.setBounds(134, 278, 97, 23);
		getContentPane().add(btnNewButton_1);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(12, 10, 250, 144);
		getContentPane().add(textArea);
		
		textField_1 = new JTextField();
		textField_1.setBounds(7, 168, 171, 21);
		getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		JButton btnNewButton_2 = new JButton("send");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_2.setBounds(193, 167, 69, 23);
		getContentPane().add(btnNewButton_2);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(38, 64, 2, 2);
		getContentPane().add(scrollPane);
		setSize(290,371);
		setVisible(true);
	}
}
