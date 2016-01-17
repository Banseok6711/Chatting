package server;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
import java.util.StringTokenizer;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

public class Server extends JFrame implements ActionListener {

	public static void main(String[] args) {
		Server server = new Server();

	}

	// JFrame Resource
	private JTextField port_tf;
	private JTextField msg_tf;

	JButton start_btn = new JButton("Start");
	JButton stop_btn = new JButton("Stop");
	JButton send_btn = new JButton("send");
	JTextArea window_ta = new JTextArea();

	// 다중 클라이언트 집합
	Vector<UserInfo> client_list = new Vector<UserInfo>();
	Vector<Room> room_list = new Vector<Room>();

	UserInfo user;
	
	//채팅방 번호
	int increaseRoomNum=1;
	
	//채팅방 하나를 개설 
	public void makeRoom(String title , String roomNum , String id ){
		
		Room room = new Room(title , Integer.parseInt(roomNum));
		
		room.addUser(id);
		
		room_list.add(room);
		
		
		
		//1 . 새로 접속하는 User에게 기존 룸 정보를 뿌려주기
		// 2. 방이 생성될때마다 room List 에 추가 해주기			
//		for(int i=0 ; i< room_list.size();i++){
//				try {
//					user.dos.writeUTF("NewRoom/Server/"+id+"/"+room_list.get(i).getRoomTitle());
//					
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//		}
		
						
		
		// 채팅방 개설후 정보 콘솔로 출력해서 확인
		room.getRoomInfo();
	}

	class UserInfo extends Thread {

		// Socket Resource
		InputStream is;
		OutputStream os;
		DataInputStream dis;
		DataOutputStream dos;

		Socket socket;
		String nickName;
				

		UserInfo(Socket s) {
			socket = s;
			networkSetting();
		}

		public void networkSetting() {
			// Server입장에서 받을때
			try {
				is = socket.getInputStream();
				dis = new DataInputStream(is);

				// Server 에서 보낼때
				os = socket.getOutputStream();
				dos = new DataOutputStream(os);

				nickName = dis.readUTF();
				window_ta.append("client :" + nickName + " 접속하였습니다..\n");

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub

			while (true) {
				try {

					String msg = dis.readUTF();
					StringTokenizer st = new StringTokenizer(msg, "/");
					String protocol = st.nextToken();
					String from = st.nextToken();
					String to = st.nextToken();
					String info = st.nextToken();

					// 쪽지보내기 
					if (protocol.equals("Note") && !info.equals("null")) {
						for (int i = 0; i < client_list.size(); i++) {
							// 접속자 리스트중에서 쪽지대상의 객체일때
							if (client_list.get(i).nickName.equals(to)) {
								client_list.get(i).dos.writeUTF(protocol + "/" + from + "/" + to + "/" + info);
								
								//서버 화면창에 정보 표시 
								window_ta.append("쪽지보내기] :"+from+" -> "+to+"\n "
										+ "내용: "+info+"\n------------------------------------\n");
							}
						}
					}
					//채팅 
					else if(protocol.equals("Chat")) {
						window_ta.append(nickName + ":" + info);
					}
					//방 생성
					else if(protocol.equals("NewRoom")){
						
						System.out.println("Server: NewRoom Protocol 받음");
						//방이 생성됬다는 걸 알림
						JOptionPane.showMessageDialog(null, "방이 생성되었습니다!");
						
						// info에 title, 인원수 정보가 두가지로 받아서 ( ,로 구분하여서 분석하기) 
						StringTokenizer stk =new StringTokenizer(info, ",");
						String title =stk.nextToken();
						String roomNum = stk.nextToken();
						
						makeRoom(title, roomNum, from); // make newRoom
						
						for(int i=0;i< client_list.size();i++){
							client_list.get(i).dos.writeUTF("NewRoom/Server/client/"+title+","+roomNum);
						}
						
						
						
//						System.out.println("서버 :방생성"+title+","+maxMember+" from:"+from);
						
						
					}

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
	}

	public void process() {
		addAction();

	}

	// 메시지전송

	public void sendMsg() {
		try {
			// 모두에게 전송
			System.out.println("client_size:" + client_list.size());
			for (int i = 0; i < client_list.size(); i++) {
				client_list.get(i).dos.writeUTF("Chat/Server/Client/" + msg_tf.getText());
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

					// 클라이언트 연결 요청을 계속 받기 위해서 반복
					while (true) {
						socket = server.accept();
						window_ta.append("client가 연결되었습니다.\n");

						// Client와 통신할 소켓들을 Vector에 저장하기
						user = new UserInfo(socket);

						// Thread에 추가해줬더니 NullPointerException 발생함 (여기에 지정해줘야함)
						client_list.add(user);

						// 기존 접속자목록을 방금 접속한 Client에게 뿌려주기
						for (int i = 0; i < client_list.size(); i++) {
							// System.out.println("clientSize:"+
							// client_list.size());
							String nick = client_list.get(i).nickName;
							String nickMessage = "UserList/Server/Client/" + nick;

							user.dos.writeUTF(nickMessage);							
						}
						
						//기존 채팅방 목록을 방금 접속한 Client에게 뿌려주기 
						for(int i=0; i < room_list.size(); i++){
							user.dos.writeUTF("OldRoom/Server/Client/"+room_list.get(i).getRoomTitle()+","+room_list.get(i).getRoomNumber());
						}
						
						

						// 기존 접속자들에게 새로들어온 접속자를 알려줘야 한다.
						for (int i = 0; i < client_list.size() - 1; i++) {
							String nickMessage = "NewList/Server/Client/" + user.nickName;
							client_list.get(i).dos.writeUTF(nickMessage);
						}
											

						user.start();

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

		// 윈도우창에서의 위치설정
		setLocation(50, 50);

		process(); // program process
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == send_btn) {
			System.out.println("send btn");
			sendMsg();

		} else if (e.getSource() == start_btn) {
			System.out.println("start btn");

			// 테스트 편의를 위해서 임시 설정
			port_tf.setText("1111");
			int portNum = Integer.parseInt(port_tf.getText());
			openServerSocket(portNum);

		} else if (e.getSource() == stop_btn) {
			System.out.println("stop btn");
		}

	}
}
