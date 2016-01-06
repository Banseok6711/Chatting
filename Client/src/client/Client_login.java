package client;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import sun.tools.jar.CommandLine;

public class Client_login extends JFrame {

	public static void main(String[] args) {
		
		Client_login client_login=new Client_login();
		Client client = new Client();
	}

	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

	public Client_login() {
		getContentPane().setLayout(null);

		JLabel lblIp = new JLabel("IP");
		lblIp.setBounds(57, 130, 57, 15);
		getContentPane().add(lblIp);

		JLabel lblPort = new JLabel("Port");
		lblPort.setBounds(57, 169, 57, 15);
		getContentPane().add(lblPort);

		JLabel lblId = new JLabel("ID");
		lblId.setBounds(57, 213, 57, 15);
		getContentPane().add(lblId);

		textField = new JTextField();
		textField.setBounds(110, 127, 116, 21);
		getContentPane().add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setBounds(110, 166, 116, 21);
		getContentPane().add(textField_1);
		textField_1.setColumns(10);

		textField_2 = new JTextField();
		textField_2.setBounds(110, 210, 116, 21);
		getContentPane().add(textField_2);
		textField_2.setColumns(10);

		JButton btnNewButton = new JButton("Connect");
		btnNewButton.setBounds(94, 267, 97, 23);
		getContentPane().add(btnNewButton);

		setSize(305, 402);
		setVisible(true);
	}

}
