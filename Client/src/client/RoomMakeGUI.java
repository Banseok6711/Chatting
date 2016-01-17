package client;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Vector;
import java.awt.Color;
import javax.swing.SwingConstants;

import com.sun.corba.se.spi.orbutil.fsm.Action;
import com.sun.java.swing.plaf.windows.resources.windows;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;

 class RoomMakeGUI extends JFrame implements ActionListener{
	 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static int roomNum=1; 
	 
	private JTextField roomTitle_tf;
	
	Vector<Integer> numbers =new Vector<Integer>();
	
	JButton create_btn = new JButton("생성");
	JButton cancel_btn = new JButton("취소");
	
	Client_login client_login;
	
	RoomMakeGUI(Client_login c){
		
		client_login = c;
		
		getContentPane().setLayout(null);
		
		JLabel label = new JLabel("방만들기");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setForeground(Color.BLUE);
		label.setFont(new Font("굴림", Font.PLAIN, 30));
		label.setBounds(66, 12, 261, 40);
		getContentPane().add(label);
		
		JLabel label_1 = new JLabel("제목");
		label_1.setFont(new Font("굴림", Font.PLAIN, 20));
		label_1.setBounds(28, 100, 67, 40);
		getContentPane().add(label_1);
		
		
		
		roomTitle_tf = new JTextField();
		roomTitle_tf.setBounds(109, 106, 265, 32);
		getContentPane().add(roomTitle_tf);
		roomTitle_tf.setColumns(10);
				
		create_btn.setBounds(54, 289, 105, 27);
		getContentPane().add(create_btn);
		
	
		cancel_btn.setBounds(230, 289, 105, 27);
		getContentPane().add(cancel_btn);
		
		setSize(430, 398);
		
		setLocation(600, 300);
		setVisible(true);
		
		resourceSettting();
		listenerSetting();
		
				
	}
	
	public void resourceSettting(){
		// 인원수 설정
		for(int i=2;i<10;i++){
			numbers.add(i);
		}
	}
	
	public void listenerSetting(){
		create_btn.addActionListener(this);
		cancel_btn.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		//제목 , 최대인원정보 넘겨주기 (방생성시 Protocol : Create
		if(e.getSource() == create_btn){
			
			String title = roomTitle_tf.getText();
						
			String info = title+","+roomNum;
			
			try {
				
				
//				client_login.client.list_room.removeAllElements(); // room List 들을 다시 다 지우고 밑에서 다시 추가
				
				 
				
				client_login.dos.writeUTF("NewRoom/"+client_login.id+"/Server/"+info);
//				this.dispose(); 
				roomNum++;
				
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}else if(e.getSource() == cancel_btn){
			// 윈도우창 닫기 
			this.dispose(); 
		}
		
	}
	
	
	
}
