package client;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.sun.corba.se.spi.orbutil.fsm.Action;

import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JList;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

public class Client extends JFrame implements ActionListener{

	
	static Client_login client_login;
	
	public static void main(String[] args) {
		
		Client client = new Client();
		client_login=new Client_login(client);

	}
	
	public void settingUserList(String id){
		System.out.println("settingUserList 실행");
		
		list_vector.add(id);
		
		userList.setListData(list_vector);
	}
	
	
	private JTextField msg_tf;
	
	JList userList;
	JList roomList;
	
	JTextArea chat_ta = new JTextArea();
	JButton shortMsg_btn = new JButton("ShortMsg");
	JButton join_btn = new JButton("Room Join");
	JButton create_btn = new JButton("Create Room");
	JButton send_btn = new JButton("send");
	
	Vector list_vector=new Vector(); 
			
	public Client() {
		setTitle("Client");
		getContentPane().setLayout(null);
		

//		chat_ta.setBounds(154, 10, 320, 381);
//		getContentPane().add(chat_ta);		
		
		//ScrollPane 추가 
		JScrollPane sp = new JScrollPane();
		sp.setBounds(154, 10, 320, 381);
		sp.setViewportView(chat_ta);		
		getContentPane().add(sp);
		
	
		shortMsg_btn.setBounds(12, 172, 130, 23);
		getContentPane().add(shortMsg_btn);
		
		JLabel lblNewLabel = new JLabel("User");
		lblNewLabel.setBounds(12, 14, 130, 15);
		getContentPane().add(lblNewLabel);
		
		userList = new JList<JLabel>();
		userList.setBounds(12, 38, 130, 124);
		getContentPane().add(userList);
		
		msg_tf = new JTextField();
		msg_tf.setBounds(154, 401, 246, 21);
		getContentPane().add(msg_tf);
		msg_tf.setColumns(10);
		
		JLabel lblChattingRoom = new JLabel("Chatting Room");
		lblChattingRoom.setBounds(12, 205, 130, 15);
		getContentPane().add(lblChattingRoom);
		
		roomList = new JList();
		roomList.setBounds(12, 230, 130, 124);
		getContentPane().add(roomList);
		
	
		join_btn.setBounds(12, 367, 130, 23);
		getContentPane().add(join_btn);
		

		create_btn.setBounds(12, 400, 130, 23);
		getContentPane().add(create_btn);
		
		
		send_btn.setBounds(406, 401, 68, 23);
		getContentPane().add(send_btn);
		
	
				
		setSize(502,498);
		setVisible(true);	
		
		//윈도우창에서의 위치설정
		setLocation(700, 50);
		
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
