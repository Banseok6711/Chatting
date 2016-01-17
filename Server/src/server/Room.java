package server;

import java.util.Vector;

public class Room {
	
	Vector<String> userId_list =new Vector<String>();
	private String roomTitle;
	private int roomNumber; // 방번호
	private String maker ;//room Maker
	
	public String getMaker() {
		return maker;
	}

	public void setMaker(String maker) {
		this.maker = maker;
	}

	public Room(String title , int roomNum){
		roomTitle = title;
		roomNumber = roomNum;
				
	}

	
	public String getRoomTitle() {
		return roomTitle;
	}

	public int getRoomNumber() {
		return roomNumber;
	}
	
	public void addUser(String id){
		userId_list.add(id);
	}
	
	public void getRoomInfo(){
		System.out.println("방번호:"+roomNumber);
		System.out.println("방제"+roomTitle);
		
		System.out.println("방 접속자 목록 :");
		
		for(String id:userId_list){
			System.out.println(id);
		}
		System.out.println("==============");
	}

}
