package utar.edu.my;

import java.util.ArrayList;
public class WaitingList 
{
	private ArrayList<User> VIP;
	private ArrayList<User> member;
	private ArrayList<User> normal;
	ConsoleUtilities scanner = new ConsoleUtilities();

	public WaitingList(){
		VIP = new ArrayList<User>();
		member = new ArrayList<User>();
		normal = new ArrayList<User>();
	}
	
	//constructor used for testing purpose, to fix the input value in the removeWaiting()
	public WaitingList(ConsoleUtilities scanner){
		VIP = new ArrayList<User>();
		member = new ArrayList<User>();
		normal = new ArrayList<User>();
		this.scanner = scanner;
	}
	
	public void addWaiting(User user, int count) {
		if(user == null || count < 1 || count > user.getMaxRooms()) throw new IllegalArgumentException();
		
		if(user.getMemberType().equals("VIP"))
		{
			VIP.add(user);
			user.setRoomsWishToBook(count);
		}
		else if(user.getMemberType().equals("normal"))
		{
			member.add(user);
			user.setRoomsWishToBook(count);
		}
		else
		{
			normal.add(user);
			user.setRoomsWishToBook(count);
		}
	}	
	
	public ArrayList<User> getVipWaiting() {
		return VIP;
	}	
	
	public ArrayList<User> getMemberWaiting() {
		return member;
	}
	
	public ArrayList<User> getNormalWaiting() {
		return normal;
	}	
	
	public boolean removeWaiting(User user) {
		
		if (user == null) {
	        throw new IllegalArgumentException();
	    }
	    
		String choice;
		if(user.getMemberType().equals("VIP") && VIP.contains(user))
		{
			scanner.print("You are in a VIP waiting list. Do you wish to cancel this booking?(y/n):");
			choice = scanner.nextLine();
			if(choice.equals("y") || choice.equals("Y"))
			{
				
				VIP.remove(user);
				scanner.println("Successfully removed from waiting list");
				return true;
			}
			else 
				return false;
		}
		else if(user.getMemberType().equals("normal") && member.contains(user))
		{
			scanner.print("You are in a member waiting list. Do you wish to cancel this booking?(y/n):");
			choice = scanner.nextLine();
			if(choice.equals("y") || choice.equals("Y"))
			{
				member.remove(user);
				scanner.println("Successfully removed from waiting list");
				return true;
			}
			else return false;
		}
		else if(user.getMemberType().equals("guest") && normal.contains(user))
		{
			scanner.print("You are in a normal waiting list. Do you wish to cancel this booking?(y/n):");
			choice = scanner.nextLine();
			if(choice.equals("y") || choice.equals("Y"))
			{
				normal.remove(user);
				scanner.println("Successfully removed from waiting list");
				return true;
			}
			else return false;
		}
		else return false;
	}
}
