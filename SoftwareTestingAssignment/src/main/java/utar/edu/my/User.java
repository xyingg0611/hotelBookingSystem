package utar.edu.my;

import java.util.ArrayList;
public class User 
{
	private String name;
	private int points;
	private String member_type;
	private boolean excl_reward;
	private ArrayList<Booking> bookings;
	private int roomsWishToBook;//only used for waiting list users
	ConsoleUtilities cu = new ConsoleUtilities();
	
	public void setRoomsWishToBook(int count)
	{
		if (count < 1 || count > this.getMaxRooms())
		{
			throw new IllegalArgumentException("Invalid room count");
		}
		roomsWishToBook = count;
	}
	
	public int getRoomsWishToBook() {
		return roomsWishToBook;
	}
	
	public User(String name, String member_type)
	{
		this.name = name;
		this.member_type = member_type;
		points = 0;
		bookings = new ArrayList<Booking>();
	}
	
	public void setName(String name) {
	    if (name == null || name.isEmpty() || name.isBlank()) {
	        throw new IllegalArgumentException("Invalid name format");
	    }
	    this.name = name;
	}


	
	public String getName()
	{
		return this.name;
	}
	
	public void setPoints(int points) {
		if (points < 0)
		{
			throw new IllegalArgumentException("Invalid point");
		}
		this.points = points;
	}
	
	public void addPoints(int points)
	{
		if(points != 100) throw new IllegalArgumentException("Only 100 points per booking");
		
		this.points += points;
	}
	
	public void setExclusiveReward(boolean ex) {
	   
	    
	    this.excl_reward = ex;
	}

	
	public int getMaxRooms()
	{

	    if (member_type.equals("VIP")) 
	    {
	        return 3;
	    } 
	    else if (member_type.equals("normal"))
	    {
	        return 2;
	    } 
	    else
	    {
	        return 1;
	    }
	}
	
	public int getPoints()
	{
		return points;
	}
	
	public ArrayList<Booking> getBookings() {
		
		return bookings;
	}
	
	public void usedExclusiveReward()
	{
		excl_reward = false;
	}
	
	public boolean bookRooms(int roomsCount)
	{
		if(roomsCount<1 || roomsCount>this.getMaxRooms()) throw new IllegalArgumentException();
		Booking booking = new Booking(this.name);
		boolean hasBooked = booking.setBooking(roomsCount, this);
		bookings.add(booking);
		return hasBooked;
	}
	
	public void setMemberType(String member_type)
	{
		if (!member_type.equals("VIP") && !member_type.equals("normal") && !member_type.equals("guest"))
		{
			throw new IllegalArgumentException("Invalid member type");
		}
		this.member_type = member_type;
	}
	
	public String getMemberType() {
		return member_type;
	}
	
	public void redeemExclusiveReward() {
		
	
		if(!excl_reward)
		{
			
			
			if(points >= 500)
			{
				excl_reward = true;
				points-=500;
				cu.println("Successfully redeemed an exclusive reward, enjoy it in your next booking!");
			}
			else
			{
				cu.println("You don't have enough points, please try again later.");
			}
		}
		else
		{
			cu.println("You already have an existing exclusive reward. Use it before redeeming another");
		}
	}
	
	
	public void redeemVipMember() {
		if(points >= 1000)
		{
			member_type = "VIP";
			points-=1000;
			cu.println("Successfully redeemed PERMANENT VIP membership! You are now a VIP member");
		}
		else {
			cu.println("You don't have enough points, please try again later.");
		}
	}
	
	public boolean hasExclusiveReward() {
		return excl_reward;
	}
}
