package utar.edu.my;


public class Room 
{
	private ConsoleUtilities scanner = new ConsoleUtilities();
	private int vip;
	private int deluxe;
	private int standard;
	
	public boolean checkRoom(String[] room_type, int[] availableRooms) {
		scanner.println("Below shows the available rooms: ");
		scanner.println(room_type[0] + " rooms available: " + availableRooms[0]);
		scanner.println(room_type[1] + " rooms available: " + availableRooms[1]);
		scanner.println(room_type[2] + " rooms available: " + availableRooms[2]);
		return false;
	}
	public void bookVip(int count) {
		this.vip = count;
	}
	public void bookDeluxe(int count) {
		this.deluxe = count;
	}
	public void bookStandard(int count) {
		this.standard = count;
	}
	public int getVipRoomsBooked() {
		return vip;
	}
	public int getDeluxeRoomsBooked() {
		return deluxe;
	}
	public int getStandardRoomsBooked() {
		return standard;
	}
}
