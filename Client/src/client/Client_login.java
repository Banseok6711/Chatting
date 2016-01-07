package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import sun.tools.jar.CommandLine;

public class Client_login extends JFrame implements ActionListener{

	/*public static void main(String[] args) {
		
		Client_login client_login=new Client_login();
		Client client = new Client();
	}*/

	// GUI Resource
	private JTextField ip_tf;
	private JTextField port_tf;
	private JTextField id_tf;
	
	JButton connect_btn= new JButton("Connect");
	
	//Socket Resource
	DataOutputStream dos;
	
	public void sendMsg(String msg){
		try {
			
			dos.writeUTF(msg+"\n");
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Client_login() {
		setTitle("Client_login");
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

		ip_tf = new JTextField();
		ip_tf.setBounds(110, 127, 116, 21);
		getContentPane().add(ip_tf);
		ip_tf.setColumns(10);

		port_tf = new JTextField();
		port_tf.setBounds(110, 166, 116, 21);
		getContentPane().add(port_tf);
		port_tf.setColumns(10);

		id_tf = new JTextField();
		id_tf.setBounds(110, 210, 116, 21);
		getContentPane().add(id_tf);
		id_tf.setColumns(10);

		
		connect_btn.setBounds(94, 267, 97, 23);
		getContentPane().add(connect_btn);

		setSize(319, 423);
		setVisible(true);
		
		startProcess();
		
	}
	
	public void settingListener(){
		connect_btn.addActionListener(this);
	}

	private void startProcess() {
		settingListener();
		
		
	}
	
	private void openSocket(String ip , int port){
		
		String id = id_tf.getText();

		Thread th = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub

				try {
					Socket socket = new Socket(ip, port);
					
					//데이터 보낼때
					OutputStream os = socket.getOutputStream();
					dos = new DataOutputStream(os);
					
					dos.writeUTF("Client: "+id+" 연결 성공!\n");
					
					
					// 데이터 받을때
					InputStream is = socket.getInputStream();
					DataInputStream dis = new DataInputStream(is);
					
										
				} catch (IOException  e) {
					e.printStackTrace();
				} 

				
			}
		});
		// Thread End
		
		th.start();
				
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == connect_btn){
			System.out.println("connect btn ");
			
			String ip = ip_tf.getText();
			int port = Integer.parseInt(port_tf.getText());
			
			openSocket(ip , port);
			
		}
		
	}

}
