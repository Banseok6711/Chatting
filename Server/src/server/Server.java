package server;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.sun.org.apache.bcel.internal.generic.InstructionConstants.Clinit;

import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

public class Server extends JFrame implements ActionListener {

	
	
	public static void main(String[] args) {
		Server server = new Server();

	}
	
	
	
	
	
	class UserInfo extends Thread{
		
		// Socket Resource
		InputStream is;
		OutputStream os;
		DataInputStream dis ;
		DataOutputStream dos ;
		
		Socket socket;
		String nickName;
		
		UserInfo(Socket s){
			socket = s;			
			networkSetting();
		}
				
		public void networkSetting(){
			// Server입장에서 받을때
			try {
				is = socket.getInputStream();
				dis = new DataInputStream(is);
				
				// Server 에서 보낼때
				os = socket.getOutputStream();
				dos = new DataOutputStream(os);
				
				nickName =dis.readUTF();
				window_ta.append("client :"+nickName+" 접속하였습니다..\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

		}
		

		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			while(true){
				try {
					String msg =dis.readUTF();
					window_ta.append(nickName+":"+ msg);
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			}		
		}
	}

	// JFrame Resource
	private JTextField port_tf;
	private JTextField msg_tf;

	JButton start_btn = new JButton("Start");
	JButton stop_btn = new JButton("Stop");
	JButton send_btn = new JButton("send");
	JTextArea window_ta = new JTextArea();

	
	
	//다중 클라이언트 집합
	Vector client_list = new Vector();
	
	UserInfo user ;

	public void process() {
		addAction();

	}
	
	// 메시지전송
	
	public void sendMsg(){
		try {
			//모두에게 전송
			System.out.println("client_size:"+client_list.size());
			for(int i =0 ;i<client_list.size();i++){
				((UserInfo)client_list.get(i)).dos.writeUTF("Server:"+msg_tf.getText());
				System.out.println("client_size:"+client_list.size());
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Add actionListener
	public void addAction() {
		start_btn.addActionListener(this);
		stop_btn.addActionListener(this);
		send_btn.addActionListener(this);

	}
	

//
//		Thread th2 = new Thread(new Runnable() {
//			
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				}
//				
//			}
//		});
//		
//		th2.start();
		
//	}
	
	public void openServerSocket(final int portNum) {
		
		// thread Start
		Thread th = new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				try {
					
					
					ServerSocket server = new ServerSocket(portNum);
					window_ta.append("서버가 준비되었습니다...\n");
					Socket socket;
					
					
					//클라이언트 연결 요청을 계속 받기 위해서 반복
					while(true){
						socket = server.accept();
						window_ta.append("client가 연결되었습니다.\n");
						
												
						
						//Client와 통신할 소켓들을 Vector에 저장하기 
						user = new UserInfo(socket);
						
						user.start();
						client_list.add(user);
					
					}
					
					
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

		
		JScrollPane jp = new JScrollPane(window_ta);
		jp.setBounds(12, 10, 266, 224);
		getContentPane().add(jp);

		msg_tf = new JTextField();
		msg_tf.setBounds(12, 246, 183, 21);
		getContentPane().add(msg_tf);
		msg_tf.setColumns(10);

		send_btn.setBounds(209, 246, 69, 23);
		getContentPane().add(send_btn);

		
		setSize(310, 459);
		setVisible(true);
		
		//윈도우창에서의 위치설정
		setLocation(50, 50);

		process(); // program process
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == send_btn){
			System.out.println("send btn");
			sendMsg();
			
		}else if(e.getSource() == start_btn){			
			System.out.println("start btn");
			
			//테스트 편의를 위해서 임시 설정 
			port_tf.setText("1111");
			int portNum =Integer.parseInt(port_tf.getText());
			openServerSocket(portNum);
			
			
			
		}else if(e.getSource() == stop_btn){
			System.out.println("stop btn");
		}
		
	}
}
