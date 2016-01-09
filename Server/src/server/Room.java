package server;

import java.util.Vector;

public class Room {
	
	Vector<String> userId_list =new Vector<String>();
	private int maxNumOfMembers;
	private String roomTitle;
	private int roomNumber; // 방번호
	
	public Room(String title , int maxNum, int roomNum){
		roomTitle = title;
		maxNumOfMembers = maxNum;
		roomNumber = roomNum;
				
	}

	public int getMaxNumOfMembers() {
		return maxNumOfMembers;
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
