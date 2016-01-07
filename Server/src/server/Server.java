package server;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

public class Server extends JFrame implements ActionListener{
	
	public static void main(String[] args){
		Server server = new Server();
		
	}
	
	//JFrame Resource
	private JTextField port_tf;
	private JTextField msg_tf;
	
	JButton start_btn = new JButton("Start");
	JButton stop_btn = new JButton("Stop");
	JButton send_btn = new JButton("send");
	JTextArea window_ta = new JTextArea();
	
	//Socket Resource
	InputStream is ;
	OutputStream os;
	
	
	
	public void process(){
		addAction();
		
		
	}
	
	//Add actionListener 
	public void addAction(){
		start_btn.addActionListener(this);
		stop_btn.addActionListener(this);
		send_btn.addActionListener(this);
				
	}
	
	public void openSocket(int portNum){
		
		
		//thread Start
		Thread th = new Thread(new Runnable() {			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {			
					
					ServerSocket server = new ServerSocket(portNum);			
					window_ta.append("서버가 준비되었습니다...\n");
					 Socket socket = server.accept();
					 window_ta.append("client가 연결되었습니다.\n");
			
					 is = socket.getInputStream();
					 DataInputStream dis = new DataInputStream(is);
					 
					 //Client로 받은 메시지
					 String msg = dis.readUTF();
					 window_ta.append(msg);
					 
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
							
			}
		});
		// thread End
		th.start();
				
	}
	
	
	
	
	
	public Server() {
		setTitle("Server");
		getContentPane().setLayout(null);
		
		JLabel lblPort = new JLabel("Port");
		lblPort.setBounds(59, 300, 57, 15);
		getContentPane().add(lblPort);
		
		port_tf = new JTextField();
		port_tf.setBounds(127, 297, 116, 21);
		getContentPane().add(port_tf);
		port_tf.setColumns(10);
		
	
		start_btn.setBounds(39, 342, 97, 23);
		getContentPane().add(start_btn);
		
		
		stop_btn.setBounds(146, 342, 97, 23);
		getContentPane().add(stop_btn);
		
		
		window_ta.setBounds(12, 10, 266, 224);
		getContentPane().add(window_ta);
		
		msg_tf = new JTextField();
		msg_tf.setBounds(12, 246, 183, 21);
		getContentPane().add(msg_tf);
		msg_tf.setColumns(10);		
		
		send_btn.setBounds(209, 246, 69, 23);
		getContentPane().add(send_btn);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(38, 64, 2, 2);
		getContentPane().add(scrollPane);
		setSize(310,459);
		setVisible(true);
		
		process(); // program process
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == send_btn){
			System.out.println("send btn");
			
		}else if(e.getSource() == start_btn){			
			System.out.println("start btn");
			
			int portNum =Integer.parseInt(port_tf.getText());
			openSocket(portNum);
			
			
			
		}else if(e.getSource() == stop_btn){
			System.out.println("stop btn");
		}
		
	}
}
