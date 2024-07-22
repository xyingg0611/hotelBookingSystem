package utar.edu.my;

public class Booking 
{
	private boolean pending = false; //Used to check if the current booking is pending or not
	private Room room;
	private String nameOfUser;
	private int availableVipRooms;
	private int availableDeluxeRooms;
	private int availableStandardRooms;
	FileUtilities fu;
	ConsoleUtilities scanner = new ConsoleUtilities();
	
	//For testing purpose -- initialize available rooms for all types
	public Booking(String name, FileUtilities fu, Room room) {
		nameOfUser = name;
		this.fu = fu;
		this.room = room;
		readAvailableRoomsFromFile();
	}
	
	//for testing purpose -- check if actually added to waiting list
	public Booking(String name, ConsoleUtilities scanner, FileUtilities fu)
	{
		this.nameOfUser = name;
		this.scanner = scanner;
		this.fu = fu;
		readAvailableRoomsFromFile();
	}
	
	//for testing purpose -- check makePayment
	public Booking(String name, ConsoleUtilities scanner, FileUtilities fu, Room room)
	{
		this.scanner = scanner;
		this.nameOfUser = name;
		this.scanner = scanner;
		this.fu = fu;
		this.room = room;
		readAvailableRoomsFromFile();
	}
	
	public Booking(String name)
	{
		room  = new Room();
		fu = new FileUtilities();
		nameOfUser = name;
		readAvailableRoomsFromFile();
	}
	
	public String getNameOfUser() {
		return nameOfUser;
	}
	
	public boolean isPending()
	{
		return pending;
	}
	
	public void makePayment(User user) {
		if(user == null) throw new IllegalArgumentException("Null user");
		double amount = room.getVipRoomsBooked() * 2500 + room.getDeluxeRoomsBooked() * 1000 + room.getStandardRoomsBooked() * 500;
		scanner.printf("Vip room price: \tRM %.2f\nDeluxe room price: \tRM %.2f\nStandard room price: \tRM %.2f\n", 2500, 1500, 500);
		scanner.println("You have booked " + room.getVipRoomsBooked() + " vip room(s), " + room.getDeluxeRoomsBooked() + " deluxe room(s), and " + room.getStandardRoomsBooked() + " standard room(s).");
		scanner.printf("Total payment due is RM %.2f\n" , amount);
		scanner.println("Press enter to pay...");
		scanner.nextLine();
		scanner.printf("You have successfully paid RM %.2f\n" , amount);
		scanner.println("Press enter to continue...");
		scanner.nextLine();
		finishPreviousBooking(user);
	}
	
	public void finishPreviousBooking(User user) {
		if(user == null) throw new IllegalArgumentException("Null user");
		
		pending = false;
		readAvailableRoomsFromFile();
		availableVipRooms += room.getVipRoomsBooked();
		availableDeluxeRooms += room.getDeluxeRoomsBooked();
		availableStandardRooms += room.getStandardRoomsBooked();
		printAvailableRoomsToFile();
		user.addPoints(100);
	}
	
	public boolean setBooking(int count, User user) {
		if(count < 1 || count > user.getMaxRooms()) throw new IllegalArgumentException("Invalid value: " + count);
		
		boolean addedToWaitingList = false;
		if(user.getMemberType().equals("VIP"))
		{
			if(count == 3)
			{
				if(availableVipRooms >= 3)
				{
					room.bookVip(3);
					room.bookDeluxe(0);
					room.bookStandard(0);
				}
				else if(availableVipRooms == 2 && availableDeluxeRooms >=1)
				{
					room.bookVip(2);
					room.bookDeluxe(1);
					room.bookStandard(0);
				}
				else if(availableVipRooms == 2 && availableDeluxeRooms == 0 && availableStandardRooms >=1)
				{
					room.bookVip(2);
					room.bookDeluxe(0);
					room.bookStandard(1);
				}
				else if (availableVipRooms == 1 && availableDeluxeRooms >=2)
				{
					room.bookVip(1);
					room.bookDeluxe(2);
					room.bookStandard(0);
				}
				else if(availableVipRooms == 1 && availableDeluxeRooms == 1 && availableStandardRooms >=1)
				{
					room.bookVip(1);
					room.bookDeluxe(1);
					room.bookStandard(1);
				}
				else if(availableVipRooms == 1 && availableDeluxeRooms == 0 && availableStandardRooms >=2)
				{
					room.bookVip(1);
					room.bookDeluxe(0);
					room.bookStandard(2);
				}
				else if(availableVipRooms == 0 && availableDeluxeRooms >= 3)
				{
					room.bookVip(0);
					room.bookDeluxe(3);
					room.bookStandard(0);
				}
				else if(availableVipRooms == 0 && availableDeluxeRooms == 2 && availableStandardRooms >=1)
				{
					room.bookVip(0);
					room.bookDeluxe(2);
					room.bookStandard(1);
				}
				else if(availableVipRooms == 0 && availableDeluxeRooms == 1 && availableStandardRooms >=2)
				{
					room.bookVip(0);
					room.bookDeluxe(1);
					room.bookStandard(2);
				}
				else if(availableVipRooms == 0 && availableDeluxeRooms == 0 && availableStandardRooms >= 3)
				{
					room.bookVip(0);
					room.bookDeluxe(0);
					room.bookStandard(3);
				}
				else {
					scanner.println("Not enough rooms are available.\nAdded to the VIP Waiting List");
					addedToWaitingList = true;
				}
			}
			else if(count == 2)
			{
				if(availableVipRooms >= 2)
				{
					room.bookVip(2);
					room.bookDeluxe(0);
					room.bookStandard(0);
				}
				else if(availableVipRooms ==1 && availableDeluxeRooms>=1)
				{
					room.bookVip(1);
					room.bookDeluxe(1);
					room.bookStandard(0);
				}
				else if(availableVipRooms == 1 && availableDeluxeRooms == 0 && availableStandardRooms >= 1)
				{
					room.bookVip(1);
					room.bookDeluxe(0);
					room.bookStandard(1);
				}
				else if(availableVipRooms == 0 && availableDeluxeRooms >=2)
				{
					room.bookVip(0);
					room.bookDeluxe(2);
					room.bookStandard(0);
				}
				else if (availableVipRooms == 0 && availableDeluxeRooms == 1 && availableStandardRooms >= 1)
				{
					room.bookVip(0);
					room.bookDeluxe(1);
					room.bookStandard(1);
				}
				else if(availableVipRooms == 0 && availableDeluxeRooms == 0 && availableStandardRooms >=2)
				{
					room.bookVip(0);
					room.bookDeluxe(0);
					room.bookStandard(2);
				}
				else
				{
					scanner.println("Not enough rooms are available.\nAdded to the VIP Waiting List");
					addedToWaitingList = true;
				}
			}
			else
			{
				if(availableVipRooms >=1)
				{
					room.bookVip(1);
					room.bookDeluxe(0);
					room.bookStandard(0);
				}
				else if(availableVipRooms == 0 && availableDeluxeRooms >= 1)
				{
					room.bookVip(0);
					room.bookDeluxe(1);
					room.bookStandard(0);
				}
				else if(availableVipRooms == 0 && availableDeluxeRooms == 0 && availableStandardRooms >=1)
				{
					room.bookVip(0);
					room.bookDeluxe(0);
					room.bookStandard(1);
				}
				else
				{
					scanner.println("Not enough rooms are available.\nAdded to the VIP Waiting List");
					addedToWaitingList = true;
				}
			}
		}
		else if(user.getMemberType().equals("normal"))
		{
			if(count == 2)
			{
				if(user.hasExclusiveReward())
				{
					if(availableVipRooms >=1 && availableDeluxeRooms >=1)
					{
						room.bookVip(1);
						room.bookDeluxe(1);
						room.bookStandard(0);
						user.usedExclusiveReward();
					}
					else if(availableVipRooms >=1 && availableDeluxeRooms ==0 && availableStandardRooms >=1)
					{
						room.bookVip(1);
						room.bookDeluxe(0);
						room.bookStandard(1);
						user.usedExclusiveReward();
					}
					else if(availableDeluxeRooms >=2)
					{
						room.bookVip(0);
						room.bookDeluxe(2);
						room.bookStandard(0);
					}
					else if(availableDeluxeRooms==1 && availableStandardRooms>=1)
					{
						room.bookVip(0);
						room.bookDeluxe(1);
						room.bookStandard(1);
					}
					else if(availableDeluxeRooms == 0 && availableStandardRooms>=2)
					{
						room.bookVip(0);
						room.bookDeluxe(0);
						room.bookStandard(2);
					}
					else {
						scanner.println("Not enough rooms are available. \nAdded to member waiting list");
						addedToWaitingList = true;
					}
				}
				else
				{
					if(availableDeluxeRooms >=2)
					{
						room.bookVip(0);
						room.bookDeluxe(2);
						room.bookStandard(0);
					}
					else if(availableDeluxeRooms == 1 && availableStandardRooms >=1)
					{
						room.bookVip(0);
						room.bookDeluxe(1);
						room.bookStandard(1);
					}
					else if(availableDeluxeRooms == 0 && availableStandardRooms >=2)
					{
						room.bookVip(0);
						room.bookDeluxe(0);
						room.bookStandard(2);
					}
					else {
						scanner.println("Not enough rooms are available.\nAdded to the member waiting list");
						addedToWaitingList = true;
					}
				}
			}
			if(count == 1)
			{
				if(user.hasExclusiveReward() && availableVipRooms >=1)
				{
					room.bookVip(1);
					room.bookDeluxe(0);
					room.bookStandard(0);
					user.usedExclusiveReward();
				}
				else if(availableDeluxeRooms >=1)
				{
					room.bookVip(0);
					room.bookDeluxe(1);
					room.bookStandard(0);
				}
				else if(availableStandardRooms >=1)
				{
					room.bookVip(0);
					room.bookDeluxe(0);
					room.bookStandard(1);
				}
				else {
					scanner.println("Not enough rooms are available.\nAdded to the member waiting list");
					addedToWaitingList = true;
				}
			}
		}
		else
		{
			if(availableStandardRooms >=1)
			{
				room.bookVip(0);
				room.bookDeluxe(0);
				room.bookStandard(1);
			}
			else {
				scanner.println("Not enough rooms are available.\nAdded to the normal waiting list");
				addedToWaitingList = true;
			}
		}
		if(!addedToWaitingList)
		{
			pending = true;
			scanner.println("You have booked " + room.getVipRoomsBooked() + " VIP room(s), " + room.getDeluxeRoomsBooked() + " deluxe room(s), and " + room.getStandardRoomsBooked() + " standard room(s).");
			availableVipRooms -= room.getVipRoomsBooked();
			availableDeluxeRooms -= room.getDeluxeRoomsBooked();
			availableStandardRooms -= room.getStandardRoomsBooked();
			//update the available rooms stored in the textfile
			printAvailableRoomsToFile();
			return true;
		}
		else {
			return false;
		}
	}
	
	public void cancelBooking() {
		
		
		readAvailableRoomsFromFile();
		this.pending = false;
		availableVipRooms += room.getVipRoomsBooked();
		availableDeluxeRooms += room.getDeluxeRoomsBooked();
		availableStandardRooms += room.getStandardRoomsBooked();
		
		printAvailableRoomsToFile();
	}
	
	public int checkRoomsBooked() {
		
		return room.getVipRoomsBooked() + room.getDeluxeRoomsBooked() + room.getStandardRoomsBooked();
	}
	
	public void readAvailableRoomsFromFile() {
		String data;
		
		try {
			data = fu.readStringsFromFile("availablerooms.txt");
			
			if (data == null) {
	            throw new IllegalArgumentException("Data read from file is null");
	        }
		}
		catch(IllegalArgumentException f)
		{
			scanner.println(f.getMessage());
			return;
		}
			String [] parts = data.split(",");
			availableVipRooms = Integer.parseInt(parts[0]);
			availableDeluxeRooms = Integer.parseInt(parts[1]);
			availableStandardRooms = Integer.parseInt(parts[2]);
	}
	
	public void printAvailableRoomsToFile() {
		String data = availableVipRooms + "," + availableDeluxeRooms + "," + availableStandardRooms;
		fu.writeAvailableRooms("availablerooms.txt", data);
	}
}
