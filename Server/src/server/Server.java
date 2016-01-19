package server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Server extends JFrame implements ActionListener {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 7582152797042955560L;

	public static void main(String[] args) {
	 new Server();

	}
	
	//Socket Resource
	ServerSocket serverSocket;
	Socket socket;
	
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
	public void makeRoom(int roomNum , String title ,  String from ){
		
		Room room = new Room(roomNum ,title , from);
		
		room.addUser(from);
		
		room_list.add(room);
		
			
		
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
		int joinedRoomNum=0; // join되지 않았을때 0으로 초기화
				

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
						
						//서버에 채팅 내용 뿌려주기 
//						window_ta.append(nickName + ":" + info);
						
						//1. 서버에는 몇번방,누구로부터,누구에게 어떤 메시지 전송했는지 보여주기 .
						//2. 같은 Room에 있는 User들에게 메시지 다보내기 
						
						chatting(from ,joinedRoomNum, info);					
						
					}
					//방 생성
					else if(protocol.equals("NewRoom")){
						
						System.out.println("Server: NewRoom Protocol 받음");
						
						
						makeRoom(increaseRoomNum, info,from);
						
						window_ta.append("[방생성]"+from+" 님이 \""+info+"\""+ "방을 만드셨습니다.\n");
						
						joinedRoomNum = increaseRoomNum;
						
						for(int i=0;i< client_list.size();i++){
							client_list.get(i).dos.writeUTF("NewRoom/Server/client/"+info+","+increaseRoomNum);
						}
						
						increaseRoomNum++;
					}
					else if(protocol.equals("Join")){ // 방에 참여하기 버튼 눌럿을때 
						//from: 해당참여자  info: 참여하려는 방번호
						
						int roomNum = Integer.parseInt(info);
						
						Iterator it= room_list.iterator();
						
						while(it.hasNext()){
							Room room = (Room)it.next();
							if(room.getRoomNumber() == roomNum){
								
								//같은 방 멤버들에게 접속을 알리는 메시지 보내기 
								for(int i=0; i<client_list.size();i++){
									 if(client_list.get(i).joinedRoomNum == roomNum ){
										 client_list.get(i).dos.writeUTF("Join/Server/client/"+from);
									 }
								}
								
								room.userId_list.add(from); // 해당방에 user 추가
								
								//현재 유저 방번호를 지정해준다 
								for(int i=0;i<client_list.size();i++){
									if(client_list.get(i).nickName.equals(from)){
										client_list.get(i).joinedRoomNum = roomNum;
									}
								}
								
								//로그출력
								room.getRoomInfo();
								
								break;
							}							
						}
						
						window_ta.append("[대화방참여]"+from+":"+roomNum+"번방에 참여하였습니다.\n");
						
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


	// 채팅방 참여하기 구현 후에 구현하기
	public void chatting(String from ,int joinedRoomNum , String info) {
		// 접속자중에 같은 방번호 인 user에게 채팅 내용 보내기
		
		//디버깅 
		/*System.out.println("method name: chatting)");
		System.out.println("user.joinedRoomNum:"+user.joinedRoomNum);
		System.out.println("user.nickname"+user.nickName);
		System.out.println("from:"+from);
		System.out.println("info:"+info);
		*/
		try{
			for(int i=0;i<client_list.size();i++){
				if(client_list.get(i).joinedRoomNum == joinedRoomNum ){					
					client_list.get(i).dos.writeUTF("Chat/"+from+"/Client/"+info);
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}

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

					ServerSocket serverSocket = new ServerSocket(portNum);
					window_ta.append("서버가 준비되었습니다...\n");
					

					// 클라이언트 연결 요청을 계속 받기 위해서 반복
					while (true) {
						socket = serverSocket.accept();
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
			
			try {
				socket.close();
				serverSocket.close();
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
		}

	}
}
