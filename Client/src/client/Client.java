package client;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.sun.corba.se.spi.orbutil.fsm.Action;

import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Client extends JFrame implements ActionListener{

	
	Client_login client_login=new Client_login();;
	
	public static void main(String[] args) {
		
		
		Client client = new Client();
	}
	
	private JTextField msg_tf;
	JTextArea chat_ta = new JTextArea();
	JButton shortMsg_btn = new JButton("ShortMsg");
	JButton join_btn = new JButton("Room Join");
	JButton create_btn = new JButton("Create Room");
	JButton send_btn = new JButton("send");
	
	
	public Client() {
		setTitle("Client");
		getContentPane().setLayout(null);
		

		chat_ta.setBounds(154, 10, 320, 381);
		getContentPane().add(chat_ta);
		

		shortMsg_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		shortMsg_btn.setBounds(12, 172, 130, 23);
		getContentPane().add(shortMsg_btn);
		
		JLabel lblNewLabel = new JLabel("User");
		lblNewLabel.setBounds(12, 14, 130, 15);
		getContentPane().add(lblNewLabel);
		
		JList list = new JList();
		list.setBounds(12, 38, 130, 124);
		getContentPane().add(list);
		
		msg_tf = new JTextField();
		msg_tf.setBounds(154, 401, 246, 21);
		getContentPane().add(msg_tf);
		msg_tf.setColumns(10);
		
		JLabel lblChattingRoom = new JLabel("Chatting Room");
		lblChattingRoom.setBounds(12, 205, 130, 15);
		getContentPane().add(lblChattingRoom);
		
		JList list_1 = new JList();
		list_1.setBounds(12, 230, 130, 124);
		getContentPane().add(list_1);
		
	
		join_btn.setBounds(12, 367, 130, 23);
		getContentPane().add(join_btn);
		

		create_btn.setBounds(12, 400, 130, 23);
		getContentPane().add(create_btn);
		
		
		send_btn.setBounds(406, 401, 68, 23);
		getContentPane().add(send_btn);
		
		setSize(502,498);
		setVisible(true);	
		
		setttingListener();		
	}
	
	public void setttingListener(){
		create_btn.addActionListener(this);
		join_btn.addActionListener(this);
		shortMsg_btn.addActionListener(this);
		send_btn.addActionListener(this);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == create_btn){
			
		}else if(e.getSource() == join_btn){
			
		}else if(e.getSource() == shortMsg_btn){
			
		}else if(e.getSource() == send_btn){
			System.out.println("send btn");
			

			client_login.sendMsg(msg_tf.getText());
					
		}
		
		
	}
}
