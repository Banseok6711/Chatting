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
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import sun.swing.StringUIClientPropertyKey;
import sun.tools.jar.CommandLine;
import sun.util.locale.StringTokenIterator;
import javax.swing.JTextPane;
import javax.swing.JPanel;
import javax.swing.JDesktopPane;
import javax.swing.JScrollPane;

public class Client_login extends JFrame implements ActionListener {

	public Client client;

	// GUI Resource
	private JTextField ip_tf;
	private JTextField port_tf;
	private JTextField id_tf;

	String id;
	JButton connect_btn = new JButton("Connect");

	// Socket Resource
	DataOutputStream dos;
	DataInputStream dis;

	public void sendMsg(String msg) {
		try {

			dos.writeUTF("Chat/" + id_tf + "/Server/" + msg);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Client_login(Client c) {
		client = c;

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

		// 윈도우창에서의 위치설정
		setLocation(400, 50);

		startProcess();

	}

	public void settingListener() {
		connect_btn.addActionListener(this);

		// 테스트 편의를 위해서 임시 설정
		ip_tf.setText("127.0.0.1");
		port_tf.setText("1111");
	}

	private void startProcess() {
		settingListener();

	}

	private void openSocket(final String ip, final int port) {

		id = id_tf.getText();

		Thread th = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				try {
					Socket socket = new Socket(ip, port);

					// 데이터 보낼때
					OutputStream os = socket.getOutputStream();
					dos = new DataOutputStream(os);

					dos.writeUTF(id);

					client.user_jl.setText("User :"+ id);
					
					// 이미 기존에 접속한 리스트를 추가

					// 데이터 받을때 (일단 client는 보내는것만 처리 후에 나중에 하기)

					InputStream is = socket.getInputStream();
					dis = new DataInputStream(is);

					while (true) {
						String msg = dis.readUTF();
						System.out.println("Server로부터 받은메시지:" + msg);

						StringTokenizer st = new StringTokenizer(msg, "/");
						String protocol = st.nextToken();
						String from = st.nextToken();
						String to = st.nextToken();
						String info = st.nextToken();
						
					

						if (protocol.equals("Chat")) {
							client.chat_ta.append(info + "\n");

						} else if (protocol.equals("UserList")) {// 자신을 표시하게 List에 [나] 라고 표시하기 
							
							client.settingUserList(info);
							
						} else if (protocol.equals("NewList")) { // 새로 접속한 사람있을
																	// 때 message
							client.settingUserList(info);
						} else if (protocol.equals("Note")) {
							try {
								String note = JOptionPane.showInputDialog(from + " : " + info);
								dos.writeUTF("Note/" + to + "/" + from + "/" + note); 																							
							} catch (IOException e1) {
								e1.printStackTrace();
							}															
							
						}else if(protocol.equals("NewRoom") || protocol.equals("OldRoom")){		
							
							StringTokenizer st2= new StringTokenizer(info, ",");
							String title = st2.nextToken();
							String num = st2.nextToken();
							
							client.settingRoomList(to,title,num); // to : roomMaker , info: roomTitle
						}else if(protocol.equals("Join")){
							//info 새로 Join한 user의 이름
							client.chat_ta.append("[참가]"+info+" 님이 대화방에 참여하였습니다. \n");
						}

					}

				} catch (IOException e) {
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
		if (e.getSource() == connect_btn) {
			System.out.println("connect btn ");
			
			this.dispose();

			String ip = ip_tf.getText();
			int port = Integer.parseInt(port_tf.getText());

			openSocket(ip, port);
			
			

		}

	}
}
