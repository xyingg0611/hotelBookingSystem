package utar.edu.my;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;
public class Application {
	String name;
	ConsoleUtilities scanner;
	static boolean terminate = false;
	int availableVipRooms;
	int availableDeluxeRooms;
	int availableStandardRooms;
	ArrayList<User> userList = new ArrayList<User>();
	WaitingList wl = new WaitingList();
	Application application;
	
	public Application() {
		scanner = new ConsoleUtilities();
	}
	public Application(ConsoleUtilities scanner) {
		this.scanner = scanner;
	}
	  public static void main(String[] args) { 
		  Application application = new Application();
		  application.readFromUserInfo();
		  while(!terminate) 
		  {
			  try {
				  application.start();
			  }
			  catch(IllegalArgumentException iae) {
				  System.out.println(iae.getMessage());
			  }
		  }
		  
		  //reset the available rooms to 5, 5, 5 everytime the program terminates
		  try {
			  PrintWriter pw = new PrintWriter("availablerooms.txt");
			  pw.print(5 + ",");
			  pw.print(5 + ",");
			  pw.println(5);
			  pw.close();
		  }
		  catch (IOException f)
		  {
			  System.out.println("Error outputting availablerooms.txt file: " + f.getMessage());
		  }
	  }
	  
	  public void start() {
		  int choice;
		  String input;
		  do {
			  scanner.println("\t\t\tWelcome to Hotel Room Booking System");
			  scanner.println("\t\t\t1. Login");
			  scanner.println("\t\t\t2. Register");
			  scanner.println("\t\t\t3. Continue as guest(If you were a guest user, please log in with your chosen name)");
			  scanner.println("\t\t\t4. Exit");
			  scanner.print("\t\t\tSelect your choice(1-4): ");
			  input = scanner.nextLine();
			  if(!input.equals("1")&&!input.equals("2")&&!input.equals("3")&&!input.equals("4"))
			  {
				  throw new IllegalArgumentException("\n\n\n\t\t\tPlease only choose between 1 - 4!");
			  }
		  }while(!input.equals("1")&&!input.equals("2")&&!input.equals("3")&&!input.equals("4"));
		  choice = Integer.parseInt(input);
		  if(choice == 1)
		  {
			  login();
		  }
		  else if(choice == 2)
		  {
			  register();
		  }
		  else if(choice == 3)
		  {
			  scanner.print("\t\t\tPlease enter your name to proceed: ");
			  name = scanner.nextLine();
			  for(int x = 0; x < userList.size(); x++)
			  {
				  if(userList.get(x).getName().equals(name))
				  {
					  scanner.println("\t\t\tName taken, please try again");
					  return;
				  }
			  }
			  String member_type = "guest";
			  User guestUser = new User(name, member_type);
			  userList.add(guestUser);
			  appendUserInfo(guestUser);
			  userInterface(guestUser);
		  }
		  else
		  {
			  scanner.println("\t\t\tThank you for using our service!");
			  terminate = true;
		  }
	  }

	private void login() {
		boolean foundUser = false;
		User user = null;
		scanner.print("\t\t\tEnter your name: ");
		name = scanner.nextLine();
		for(int x = 0; x < userList.size(); x++)
		{
			if(userList.get(x).getName().equals(name))
			{
				user = userList.get(x);
				foundUser = true;
			}
		}
		if(!foundUser)
		{
			scanner.println("\n\n\n\t\t\tNo user found. Please try again");
			return;
		}
		userInterface(user);
	}
	
	private void register() {
		User user;
		boolean nameTaken = false;
		scanner.print("\t\t\tEnter your name: ");
		name = scanner.nextLine();
		for(int x = 0; x < userList.size(); x++) {
			if(userList.get(x).getName().equals(name))
			{
				if(userList.get(x).getMemberType().equals("guest"))
				{
					scanner.println("You were once a guest member, successfully registered as a normal member. Please log in to continue");
					userList.get(x).setMemberType("normal");
					overwriteUserInfo();
					return;
				}
				else
				{
					nameTaken = true;
				}
			}
		}
		if(nameTaken)
		{
			name = "";
			scanner.println("\t\t\tName taken, please try again");
			return;
		}
		user = new User(name, "normal");
		userList.add(user);
		scanner.println("\t\t\tSuccessfully registered. Please login to continue");
		appendUserInfo(user);
	}
	
	private void userInterface(User user) {
		readWaitingList();
		int choice;
		boolean loggedIn = true;
		String input;
		while(loggedIn)
		{
			do
			{
				scanner.println("*******************************************************************");
				Printer p = new Printer();
				p.printInfo(user.getName(), user.getMemberType(), "room");
				scanner.println("1. Book rooms");
				scanner.println("2. Display available rooms");
				scanner.println("3. Use points");
				scanner.println("4. Make payment");
				scanner.println("5. Cancel booking");
				scanner.println("6. Logout");
				scanner.println("*******************************************************************");
				scanner.print("Your choice(1-6): ");
				input = scanner.nextLine();
				if(!input.equals("1") && !input.equals("2") && !input.equals("3")&& !input.equals("4")&& !input.equals("5")&& !input.equals("6"))
				  {
					scanner.println("\n\n\nPlease only choose between 1 - 6!");
				  }
			}while(!input.equals("1") && !input.equals("2") && !input.equals("3")&& !input.equals("4")&& !input.equals("5")&& !input.equals("6"));
			choice = Integer.parseInt(input);
			switch(choice)
			{
			case 1:
				bookRooms(user);
				break;
				
			case 2:
				displayAvailableRooms();
				break;
				
			case 3:
				usePoints(user);
				break;
				
			case 4:
				makePayment(user);
				break;
				
			case 5:
				cancelBooking(user);
				break;
				
			default: 
				overwriteUserInfo();
				name = "";
				loggedIn = false;
				scanner.println("Successfully logged out");
				break;
			}
		}
	}
	public void bookRooms(User user)
	{
		for(Booking booking:user.getBookings())
		{
			if(booking.isPending())
			{
				scanner.println("You have a pending booking, please proceed with payment first");
				return;
			}
		}
		String decision;
		if(user.getMaxRooms() == 1)
		{
			scanner.println("You are a guest user, you can only book 1 standard room.");
		}
		else if(user.getMaxRooms() == 3)
		{
			scanner.println("You are a VIP registered user, you can book up to 3 rooms, including vip, deluxe, and/or standard rooms. *Subject to room availability.*");
		}
		else {
			scanner.println("You are a normal registered user, you can book up to 2 rooms only, deluxe and/or standard rooms. *Subject to room availability.*");
			scanner.println("If you have an exclusive reward, you can redeem a vip room.");
		}

		scanner.print("Do you wish to continue?(y/n): ");
		decision = scanner.nextLine();
		if(decision.equals("y") || decision.equals("Y"))
		{
			int roomsCount;//Store how many rooms the user wants to book
			String validateRoomsCount;//Accept user input on the rooms he/she wishes to book, then validates it
			boolean exceededAllowedRooms;//used for vips or normal members only, if they inputs more than max allowed / invalid input	

			do {
					if(user.getMaxRooms() == 3)
					{
						scanner.println("You can book up to " + user.getMaxRooms() + " rooms."); 
						scanner.print("Enter how many rooms you want to book: ");
						validateRoomsCount = scanner.nextLine();
						if(!validateRoomsCount.equals("1") && !validateRoomsCount.equals("2") && !validateRoomsCount.equals("3"))
						{
							scanner.println("Please enter the valid amount(1-" + user.getMaxRooms() + ")!");
						}
						exceededAllowedRooms = !validateRoomsCount.equals("1") && !validateRoomsCount.equals("2") && !validateRoomsCount.equals("3");
					}
					else if(user.getMaxRooms() == 2)
					{
						scanner.println("You can book up to " + user.getMaxRooms() + " rooms."); 
						scanner.print("Enter how many rooms you want to book: ");
						validateRoomsCount = scanner.nextLine();
						if(!validateRoomsCount.equals("1") && !validateRoomsCount.equals("2"))
						{
							scanner.println("Please enter the valid amount(1-" + user.getMaxRooms() + ")!");
						}
						exceededAllowedRooms = !validateRoomsCount.equals("1") && !validateRoomsCount.equals("2");
					}
					else
						{
							validateRoomsCount = "1";
							exceededAllowedRooms = false;
						}
				}while(exceededAllowedRooms);
			roomsCount = Integer.parseInt(validateRoomsCount);
			if(!user.bookRooms(roomsCount))
				{
				if((user.getMemberType().equals("VIP") && !wl.getVipWaiting().contains(user))||(user.getMemberType().equals("normal") && !wl.getMemberWaiting().contains(user))||(user.getMemberType().equals("guest") && !wl.getNormalWaiting().contains(user)))
				{
					wl.addWaiting(user, roomsCount);
				}
				else {
					if(user.getMemberType().equals("VIP"))
					{
						int index = wl.getVipWaiting().indexOf(user);
						wl.getVipWaiting().get(index).setRoomsWishToBook(roomsCount);
					}
					else if(user.getMemberType().equals("normal"))
					{
						int index = wl.getMemberWaiting().indexOf(user);
						wl.getMemberWaiting().get(index).setRoomsWishToBook(roomsCount);
					}
					else if(user.getMemberType().equals("guest"))
					{
						int index = wl.getNormalWaiting().indexOf(user);
						wl.getNormalWaiting().get(index).setRoomsWishToBook(roomsCount);
					}
				}
					overwriteWaitingList();
				}
			}
	}
	public void displayAvailableRooms()
	{
		try {
			File file = new File("availablerooms.txt");
			Scanner inputFile = new Scanner(file);
			inputFile.useDelimiter("[,\n]");
			while(inputFile.hasNext())
			{
				availableVipRooms = Integer.parseInt(inputFile.next().trim());
				availableDeluxeRooms = Integer.parseInt(inputFile.next().trim());
				availableStandardRooms = Integer.parseInt(inputFile.next().trim());
			}
			int [] availableRooms = {availableVipRooms, availableDeluxeRooms, availableStandardRooms};
			String [] roomTypes = {"VIP", "Deluxe", "Standard"};
			Room room = new Room();
			room.checkRoom(roomTypes, availableRooms);
			inputFile.close();
		}
		catch(FileNotFoundException f)
		{
			scanner.println("Error reading file: " + f.getMessage());
		}
	}
	public void usePoints(User user)
	{
		if(user.getMemberType().equals("guest"))
		{
			scanner.println("You are a guest user, please register to earn points(By using your guest name)");
		}
		else
		{
			int choice;
			String userInput;
			do 
			{
				scanner.println("******************************************************************************************");
				scanner.println("You can use points to redeem rewards! Earn 100 points everytime you finish a booking.");
				scanner.println("Redeem an exclusive reward to enjoy a vip room in your next booking! Points required: 500");
				scanner.println("Redeem a PERMANENT VIP membership. Points required: 1000");
				scanner.println("You currently have " + user.getPoints() + " points.");
				scanner.println("1. Redeem an exclusive reward");
				scanner.println("2. Redeem a permanent vip membership");
				scanner.println("3. Go back");
				scanner.println("******************************************************************************************");
				scanner.print("Your choice: ");
				userInput = scanner.nextLine();
				if(!userInput.equals("1") && !userInput.equals("2") && !userInput.equals("3")) 
				{
					scanner.println("Please only choose between 1-3!");
				}
			}while(!userInput.equals("1") && !userInput.equals("2") && !userInput.equals("3"));
			choice = Integer.parseInt(userInput);
			switch(choice)
			{
				case 1:
					user.redeemExclusiveReward();
					break;
				case 2:
					if(user.getMemberType().equals("VIP"))
					{
						System.out.println("You are already an vip member.");
						return;
					}
					
					user.redeemVipMember();
					break;
				default:
					break;
			}
		}
	}
	
	public void makePayment(User user)
	{
		Booking foundPendingBooking = null;
		if(user.getBookings().size()>0)
		{
			for(Booking booking:user.getBookings())
			{
				if(booking.isPending())
				{
					foundPendingBooking = booking;
					break;
				}
					
			}
			if(foundPendingBooking == null)
			{
				scanner.println("You don't have any pending bookings, please try again later");
			}
			else
			{
				foundPendingBooking.makePayment(user);
			}
		}
		else
			scanner.println("You don't have any pending bookings, please try again later");
	}
	
	public void cancelBooking(User user) {
		String choice;
		if(wl.removeWaiting(user))
		{
			overwriteWaitingList();
			return;
		}
		boolean hasPendingBooking = false;
		if(user.getBookings().size() > 0)
		{
			for(int x = 0; x < user.getBookings().size(); x++)
			{
				if(user.getBookings().get(x).isPending())
				{
					hasPendingBooking = true;
					scanner.println("You currently have a pending booking");
					scanner.println("You have booked " + user.getBookings().get(x).checkRoomsBooked() + " rooms.");
					scanner.print("Do you wish to cancel this booking?(y/n): ");
					choice = scanner.nextLine();
					if(choice.equals("y") || choice.equals("Y"))
					{
						user.getBookings().get(x).cancelBooking();
					}
				}
			}
			if(!hasPendingBooking)
				scanner.println("You don't have any pending bookings.");
		}
		else
		{
			scanner.println("You don't have any pending bookings.");
		}
	}
	
	private void overwriteUserInfo() {
		try {
			PrintWriter pw = new PrintWriter("userinfo.txt");
			int i = 0;
			while(i < userList.size())
			{
				pw.print(userList.get(i).getName()+",");
				pw.print(userList.get(i).getMemberType()+",");
				pw.print(userList.get(i).getPoints()+",");
				pw.println(userList.get(i).hasExclusiveReward());
				i++;
			}
			pw.close();
		}
		catch(FileNotFoundException f)
		{
			scanner.println("Error finding file: " + f.getMessage());
		}
	}
	
	private void readFromUserInfo()
	{
		  try {
			  File file = new File("userinfo.txt");
			  Scanner fileScanner= new Scanner(file);
			  fileScanner.useDelimiter("[,\n]");
			  while(fileScanner.hasNext())
			  {
				  String name;
				  String member_type;
				  int points;
				  boolean exclusive_reward;
				  name = fileScanner.next();
				  member_type = fileScanner.next();
				  points = Integer.parseInt(fileScanner.next());
				  exclusive_reward = Boolean.parseBoolean(fileScanner.nextLine());
				  User user = new User(name, member_type);
				  user.setPoints(points);
				  user.setExclusiveReward(exclusive_reward);
				  userList.add(user);
			  }
			  fileScanner.close();
		  }
		  catch (FileNotFoundException f){
			  scanner.println("Error, cant read userinfo.txt file: " + f.getMessage());
		  }
	}
	private void appendUserInfo(User user)
	{
		try
		{
			FileWriter fw = new FileWriter("userinfo.txt", true);
			PrintWriter pw = new PrintWriter(fw);
			pw.print(user.getName() +",");
			pw.print(user.getMemberType()+ ",");
			pw.print(user.getPoints() +",");
			pw.println(user.hasExclusiveReward());
			fw.close();
		}
		catch(IOException f)
		{
			scanner.println("Error outputting user info: " + f.getMessage());
		}
	}
	
	private void overwriteWaitingList() {
		try {
			PrintWriter pw = new PrintWriter("waitinglist.txt");
			int i = 0;
			while(i < wl.getVipWaiting().size())
			{
				pw.print(wl.getVipWaiting().get(i).getName() + ",");
				pw.println(wl.getVipWaiting().get(i).getRoomsWishToBook());
				i++;
			}
			i = 0;
			while(i < wl.getMemberWaiting().size())
			{
				pw.print(wl.getMemberWaiting().get(i).getName() +",");
				pw.println(wl.getMemberWaiting().get(i).getRoomsWishToBook());
				i++;
			}
			i = 0;
			while(i < wl.getNormalWaiting().size())
			{
				pw.print(wl.getNormalWaiting().get(i).getName()+",");
				pw.println(wl.getNormalWaiting().get(i).getRoomsWishToBook());
				i++;
			}
			pw.close();
		}
		catch(IOException f) {
			scanner.println("Error occurred overwriting waitinglist.txt file: " + f.getMessage());
		}
	}
	private void readWaitingList() {
		try 
		{
			wl.getVipWaiting().clear();
			wl.getMemberWaiting().clear();
			wl.getNormalWaiting().clear();
			File file = new File("waitinglist.txt");
			Scanner scanner = new Scanner(file);
			ArrayList<String> usersInWaitingList = new ArrayList<String>();
			ArrayList<String> roomsWishToBookByUser = new ArrayList<String>();
			scanner.useDelimiter("[,\n]");
			while (scanner.hasNext())
			{
				usersInWaitingList.add(scanner.next());
				roomsWishToBookByUser.add(scanner.next().trim());
			}
			for(int x = 0; x < usersInWaitingList.size(); x++)
			{
				for(int y = 0; y < userList.size(); y++)
				{
					if(userList.get(y).getName().equals(usersInWaitingList.get(x)))
					{
						wl.addWaiting(userList.get(y), Integer.parseInt(roomsWishToBookByUser.get(x)));
					}
				}
			}
			roomsWishToBookByUser.clear();
			usersInWaitingList.clear();
			scanner.close();
		}
		catch(FileNotFoundException f)
		{
			scanner.println("Error reading from waitinglist.txt: " + f.getMessage());
		}
	}
	

}

