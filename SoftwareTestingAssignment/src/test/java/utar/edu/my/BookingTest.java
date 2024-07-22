package utar.edu.my;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JUnitParamsRunner.class)
public class BookingTest {
	Booking b = new Booking("XuanYing");
	
	//test get name of user
	@Test
	@Parameters({"Aik Suan,Aik Suan","XuanYing, XuanYing"})
	public void getNameOfUserWithValid(String name,String expectedResult)
	{
		Booking b = new Booking(name);
		assertEquals(expectedResult,b.getNameOfUser());
	}
	
	//test is pending valid and invalid
	@Test
	@Parameters(method = "isPendingParams")
	public void isPendingWithValid(String dataInitialize,int count,String member_type,boolean expectedResult)
	{
		FileUtilities fuMock = mock(FileUtilities.class);
		when(fuMock.readStringsFromFile(anyString())).thenReturn(dataInitialize);
		
		User userMock = mock(User.class);
		when(userMock.getMemberType()).thenReturn(member_type);
		if(member_type.equals("VIP"))
			when(userMock.getMaxRooms()).thenReturn(3);
		else if(member_type.equals("normal"))
			when(userMock.getMaxRooms()).thenReturn(2);
		else if(member_type.equals("guest"))
			when(userMock.getMaxRooms()).thenReturn(1);
		
		//no use
		Room roomMock = new Room();
		
		Booking booking  = new Booking(anyString(),fuMock,roomMock);
		booking.setBooking(count, userMock);
		
		if (expectedResult)
		{
			assertTrue(booking.isPending());
		}
		else 
		{
			assertFalse(booking.isPending());
		}
	}
	
	private Object[] isPendingParams() {
		return new Object[]
				{
						//true mean that the set booking methd successful, false mean the user is place in waiting list
						new Object[] {"3,5,5", 3, "VIP", "true"},
						new Object[] {"0,5,5", 2, "VIP", "true"},
						new Object[] {"0,0,5", 1, "VIP", "true"},
						new Object[] {"0,0,1", 3, "VIP", "false"},
						new Object[] {"0,0,1", 2, "VIP", "false"},
						new Object[] {"0,0,0", 1, "VIP", "false"},
						new Object[] {"3,5,5", 2, "normal", "true"},
						new Object[] {"0,5,5", 1, "normal", "true"},
						new Object[] {"0,0,1", 2, "normal", "false"},
						new Object[] {"0,0,0", 1, "normal", "false"},
						new Object[] {"0,5,5", 1, "guest", "true"},
						new Object[] {"0,0,0", 1, "guest", "false"}
				};
		
	}
	
	 @Test
	 @Parameters(method = "makePaymentValidParams")
	 public void testMakePaymentWithValid(int[] roomsBooked, String member_type, double expectedAmount) {
		 Room roomMock = mock(Room.class);
		 when(roomMock.getVipRoomsBooked()).thenReturn(roomsBooked[0]);
		 when(roomMock.getDeluxeRoomsBooked()).thenReturn(roomsBooked[1]);
		 when(roomMock.getStandardRoomsBooked()).thenReturn(roomsBooked[2]);
		 
		 ConsoleUtilities scannerMock = mock(ConsoleUtilities.class);
		 when(scannerMock.nextLine()).thenReturn("");
		 
		 FileUtilities fuMock = mock(FileUtilities.class);
		 User user = new User("XuanYing", member_type);
		 Booking booking = new Booking("XuanYing", scannerMock, fuMock, roomMock);
		 booking.makePayment(user);
	
		 
		 verify(scannerMock, times(1)).printf("Total payment due is RM %.2f\n" , expectedAmount);
	 }
	 
	 private Object[] makePaymentValidParams()
	 {
		 return new Object[]
				 {
							new Object[] {new int[]{3,0,0},"VIP", 7500},
							new Object[] {new int[]{2,1,0},"VIP", 6000},
							new Object[] {new int[]{2,0,1},"VIP", 5500},
							new Object[] {new int[]{1,2,0},"VIP", 4500},
							new Object[] {new int[]{1,1,1},"VIP", 4000},
							new Object[] {new int[]{1,0,2},"VIP", 3500},
							new Object[] {new int[]{0,3,0},"VIP", 3000},
							new Object[] {new int[]{0,2,1},"VIP", 2500},
							new Object[] {new int[]{0,1,2},"VIP", 2000},
							new Object[] {new int[]{0,0,3},"VIP", 1500},
							
							new Object[] {new int[]{2,0,0},"VIP", 5000},
							new Object[] {new int[]{1,1,0},"VIP", 3500},
							new Object[] {new int[]{1,0,1},"VIP", 3000},
							new Object[] {new int[]{0,2,0},"VIP", 2000},
							new Object[] {new int[]{0,1,1},"VIP", 1500},
							new Object[] {new int[]{0,0,2},"VIP", 1000},
							new Object[] {new int[]{0,0,2},"VIP", 1000},
							

							new Object[] {new int[]{1,0,0},"VIP", 2500},
							new Object[] {new int[]{0,1,0},"VIP", 1000},
							new Object[] {new int[]{0,0,1},"VIP", 500},
							new Object[] {new int[]{0,0,0},"VIP", 0},
							

							new Object[] {new int[]{1,1,0},"normal", 3500},
							new Object[] {new int[]{1,0,1},"normal", 3000},
							new Object[] {new int[]{0,2,0},"normal", 2000},
							new Object[] {new int[]{0,1,1},"normal", 1500},
							new Object[] {new int[]{0,0,2},"normal", 1000},
							new Object[] {new int[]{1,0,0},"normal", 2500},
							new Object[] {new int[]{0,1,0},"normal", 1000},
							new Object[] {new int[]{0,0,1},"normal", 500},
							new Object[] {new int[]{0,0,0},"normal", 0},
							

							new Object[] {new int[]{0,0,1},"guest", 500},
							new Object[] {new int[]{0,0,0},"guest", 0}
							
				 };
	 }
	 
	 @Test(expected=IllegalArgumentException.class)
	 public void makePaymentInvalid() {
		 User nullUser = null;
		 Booking b = new Booking("test");
		 b.makePayment(nullUser);
	 }
	
	 
	 //test finish previous booking
	 @Test
	 @Parameters(method = "getParamsForFinishPreviousBooking")
	 public void finishPreviousBooking(String dataInitialize, int[] roomsBooked, String expectedResult) {
	     FileUtilities fuMock = mock(FileUtilities.class);
	     when(fuMock.readStringsFromFile(anyString())).thenReturn(dataInitialize);
	     
	     Room roomMock = mock(Room.class);
	     when(roomMock.getVipRoomsBooked()).thenReturn(roomsBooked[0]);
	     when(roomMock.getDeluxeRoomsBooked()).thenReturn(roomsBooked[1]);
	     when(roomMock.getStandardRoomsBooked()).thenReturn(roomsBooked[2]);
	     
	     User user = new User("XuanYing", "anyMember");
	     
	     Booking booking = new Booking(user.getName(), fuMock, roomMock);
	     booking.finishPreviousBooking(user);
	     
	     verify(fuMock).writeAvailableRooms(anyString(), eq(expectedResult));
	     
	     //every booking finished grants the user 100 points
	     assertEquals(100, user.getPoints());
	 }

	 private Object[] getParamsForFinishPreviousBooking() {
	     return new Object[]{
	             new Object[]{"3,0,0", new int[]{2, 0, 0}, "5,0,0"},
	             new Object[]{"0,0,0", new int[]{2, 0, 0}, "2,0,0"},
	             new Object[]{"0,2,0", new int[]{2, 1, 0}, "2,3,0"},
	             new Object[]{"0,0,2", new int[]{2, 0, 0}, "2,0,2"},
	             new Object[]{"3,0,2", new int[]{0, 3, 0}, "3,3,2"},
	             new Object[]{"3,0,2", new int[]{1, 2, 0}, "4,2,2"},
	             new Object[]{"1,0,2", new int[]{2, 1, 0}, "3,1,2"},
	             new Object[]{"3,0,2", new int[]{0, 3, 0}, "3,3,2"},
	             new Object[]{"1,0,2", new int[]{2, 0, 1}, "3,0,3"},
	             new Object[]{"3,0,2", new int[]{0, 0, 3}, "3,0,5"},
	             new Object[]{"3,0,2", new int[]{0, 1, 2}, "3,1,4"},
	             new Object[]{"3,0,2", new int[]{0, 2, 1}, "3,2,3"}
	     };
	 }
	

	//test set booking with valid
	@Test
	@Parameters(method = "GetDataForTestSetBooking")
	public void testSetBooking(String dataInitialize, int count, String member_type, boolean exclusive_reward, int expectedResult[]) {
		FileUtilities fuMock = mock(FileUtilities.class);
		when(fuMock.readStringsFromFile(anyString())).thenReturn(dataInitialize);
		
		User userMock = new User("Xuanying", member_type);
		userMock.setExclusiveReward(exclusive_reward);
		Room roomMock = mock(Room.class);
		
		Booking booking = new Booking(anyString(), fuMock, roomMock);
		booking.setBooking(count, userMock);

		verify(roomMock).bookVip(expectedResult[0]);
		verify(roomMock).bookDeluxe(expectedResult[1]);
		verify(roomMock).bookStandard(expectedResult[2]);
		if(exclusive_reward && dataInitialize.startsWith("1"))
			assertFalse(userMock.hasExclusiveReward());
	}
	
	private Object[] GetDataForTestSetBooking() {
		return new Object[] {//set the available rooms, rooms user want to book, users' member_type, expected rooms allocation to users
				new Object[] {"3,5,5", 3, "VIP", false, new int[]{3, 0, 0}},
				new Object[] {"2,5,5", 3, "VIP", false, new int[]{2, 1, 0}},
				new Object[] {"2,0,1", 3, "VIP", false, new int[]{2, 0, 1}},
				new Object[] {"1,3,5", 3, "VIP", false, new int[]{1, 2, 0}},
				new Object[] {"1,1,5", 3, "VIP", false, new int[]{1, 1, 1}},
				new Object[] {"1,0,5", 3, "VIP", false, new int[]{1, 0, 2}},
				new Object[] {"0,5,5", 3, "VIP", false, new int[]{0, 3, 0}},
				new Object[] {"0,2,5", 3, "VIP", false, new int[]{0, 2, 1}},
				new Object[] {"0,1,5", 3, "VIP", false, new int[]{0, 1, 2}},
				new Object[] {"0,0,5", 3, "VIP", false, new int[]{0, 0, 3}},
				new Object[] {"2,5,5", 2, "VIP", false, new int[]{2, 0, 0}},
				new Object[] {"1,5,5", 2, "VIP", false, new int[]{1, 1, 0}},
				new Object[] {"1,0,5", 2, "VIP", false, new int[]{1, 0, 1}},
				new Object[] {"0,2,5", 2, "VIP", false, new int[]{0, 2, 0}},
				new Object[] {"0,1,5", 2, "VIP", false, new int[]{0, 1, 1}},
				new Object[] {"0,0,5", 2, "VIP", false, new int[]{0, 0, 2}},
				new Object[] {"1,5,5", 1, "VIP", false, new int[]{1, 0, 0}},
				new Object[] {"0,5,5", 1, "VIP", false, new int[]{0, 1, 0}},
				new Object[] {"0,0,5", 1, "VIP", false, new int[]{0, 0, 1}},
				new Object[] {"5,2,5", 2, "normal", false, new int[]{0, 2, 0}},
				new Object[] {"5,1,5", 2, "normal", false, new int[]{0, 1, 1}},
				new Object[] {"5,0,2", 2, "normal", false, new int[]{0, 0, 2}},
				new Object[] {"5,5,5", 1, "normal", false, new int[]{0, 1, 0}},
				new Object[] {"5,0,1", 1, "normal", false, new int[]{0, 0, 1}},
				new Object[] {"5,5,5", 2, "normal", true, new int[]{1, 1, 0}},
				new Object[] {"5,0,1", 2, "normal", true, new int[]{1, 0, 1}},
				new Object[] {"0,5,5", 2, "normal", true, new int[]{0, 2, 0}},
				new Object[] {"0,1,1", 2, "normal", true, new int[]{0, 1, 1}},
				new Object[] {"0,0,1", 1, "guest", false, new int[]{0, 0, 1}}
		};
	}
	
	//test set booking with invalid
	@Test
	@Parameters(method="GetDataForTestSetBookingNotEnoughDesiredRooms")
	public void testSetBookingNotEnoughDesiredRooms(String dataInitialize, int count, String member_type, String expectedResult) {
		ConsoleUtilities cuMock = mock(ConsoleUtilities.class);
		FileUtilities fuMock = mock(FileUtilities.class);
		when(fuMock.readStringsFromFile(anyString())).thenReturn(dataInitialize); 
		
		User userMock = new User("xuanying", member_type);
		
		Booking booking = new Booking(anyString(), cuMock, fuMock);
		booking.setBooking(count, userMock);
		
		verify(cuMock).println(expectedResult);
	}
	
	private Object[] GetDataForTestSetBookingNotEnoughDesiredRooms() {
		return new Object[] {//set the available rooms, rooms user want to book, users' member_type, expected rooms allocation to users
				new Object[] {"0,0,2", 3, "VIP", "Not enough rooms are available.\nAdded to the VIP Waiting List"},
				new Object[] {"0,1,1", 3, "VIP", "Not enough rooms are available.\nAdded to the VIP Waiting List"},
				new Object[] {"1,0,1", 3, "VIP", "Not enough rooms are available.\nAdded to the VIP Waiting List"},
				new Object[] {"1,1,0", 3, "VIP", "Not enough rooms are available.\nAdded to the VIP Waiting List"},
				new Object[] {"2,0,0", 3, "VIP", "Not enough rooms are available.\nAdded to the VIP Waiting List"},
				new Object[] {"0,2,0", 3, "VIP", "Not enough rooms are available.\nAdded to the VIP Waiting List"},
				new Object[] {"0,0,0", 3, "VIP", "Not enough rooms are available.\nAdded to the VIP Waiting List"},
				new Object[] {"1,0,0", 2, "VIP", "Not enough rooms are available.\nAdded to the VIP Waiting List"},
				new Object[] {"0,1,0", 2, "VIP", "Not enough rooms are available.\nAdded to the VIP Waiting List"},
				new Object[] {"0,0,1", 2, "VIP", "Not enough rooms are available.\nAdded to the VIP Waiting List"},
				new Object[] {"0,0,0", 2, "VIP", "Not enough rooms are available.\nAdded to the VIP Waiting List"},
				new Object[] {"0,0,0", 1, "VIP", "Not enough rooms are available.\nAdded to the VIP Waiting List"},
				new Object[] {"0,0,1", 2, "normal", "Not enough rooms are available.\nAdded to the member waiting list"},
				new Object[] {"0,1,0", 2, "normal", "Not enough rooms are available.\nAdded to the member waiting list"},
				new Object[] {"0,0,1", 2, "normal", "Not enough rooms are available.\nAdded to the member waiting list"},
				new Object[] {"0,0,0", 1, "normal", "Not enough rooms are available.\nAdded to the member waiting list"},
				new Object[] {"0,0,0", 1, "guest", "Not enough rooms are available.\nAdded to the normal waiting list"}	
		};
	}
	
	
	//test cancel booking -done
	@Test
    @Parameters(method = "cancelBookingValidParams")
    public void cancelBookingWithValid(String dataInitialize, int[] roomBooked, int[] expectedResult)
	{
        FileUtilities fuMock = mock(FileUtilities.class);
        when(fuMock.readStringsFromFile(anyString())).thenReturn(dataInitialize);
        
        Room roomMock = mock(Room.class);
        when(roomMock.getVipRoomsBooked()).thenReturn(roomBooked[0]);
        when(roomMock.getDeluxeRoomsBooked()).thenReturn(roomBooked[1]);
        when(roomMock.getStandardRoomsBooked()).thenReturn(roomBooked[2]);
        
        Booking booking = new Booking("XuanYing", fuMock, roomMock);
        
        booking.cancelBooking();
        
        verify(fuMock).writeAvailableRooms(anyString(), eq(expectedResult[0] + "," + expectedResult[1] + "," + expectedResult[2]));
    }

    private Object[] cancelBookingValidParams() {
        return new Object[] {
        		//test vip
                new Object[] {"2,5,5", new int[]{3, 0, 0}, new int[]{5, 5, 5}},
                new Object[] {"1,1,5", new int[]{0, 3, 0}, new int[]{1, 4, 5}},
                new Object[] {"1,3,1", new int[]{0, 0, 3}, new int[]{1, 3, 4}},
                new Object[] {"1,3,5", new int[]{2, 1, 0}, new int[]{3, 4, 5}},
                new Object[] {"1,3,4", new int[]{2, 0, 1}, new int[]{3, 3, 5}},
                new Object[] {"1,3,4", new int[]{0, 2, 1}, new int[]{1, 5, 5}},
                new Object[] {"5,3,1", new int[]{0, 1, 2}, new int[]{5, 4, 3}},
                new Object[] {"2,3,1", new int[]{1, 2, 0}, new int[]{3, 5, 1}},
                new Object[] {"2,3,1", new int[]{1, 1, 1}, new int[]{3, 4, 2}},
                
                //test normal member
                new Object[] {"5,1,5", new int[]{0, 2, 0} ,new int[]{5, 3, 5}},
                new Object[] {"5,1,3", new int[]{0, 0, 2}, new int[]{5, 1, 5}},
                new Object[] {"5,3,1", new int[]{0, 1, 1}, new int[]{5, 4, 2}},
                
                //test guest
                new Object[] {"5,3,1", new int[]{0, 0, 1}, new int[]{5, 3, 2}}
        };
    }
    
    //test check rooms booked
    @Test
    @Parameters(method = "checkRoomsBookedValidParam")
    public void checkRoomsBookedWithValid(String dataInitialize,int []roomBooked,int expectedResult)
    {
    	FileUtilities fuMock = mock(FileUtilities.class);
    	when(fuMock.readStringsFromFile(anyString())).thenReturn(dataInitialize);
    	
    	Room roomMock = mock(Room.class);
    	when(roomMock.getVipRoomsBooked()).thenReturn(roomBooked[0]);
    	when(roomMock.getDeluxeRoomsBooked()).thenReturn(roomBooked[1]);
    	when(roomMock.getStandardRoomsBooked()).thenReturn(roomBooked[2]);
    	
    	Booking booking = new Booking("XuanYing",fuMock,roomMock);
    	
    	int actualResult = booking.checkRoomsBooked();
    	assertEquals(expectedResult,actualResult);
    }
    
   private Object[] checkRoomsBookedValidParam()
   {
	   return new Object[]
			   {
					   //test vip 
					   new Object[] {"5,5,5",new int[]{1,0,0},1},
					   new Object[] {"5,5,5",new int[]{1,1,0},2},
					   new Object[] {"5,5,5",new int[]{1,1,1},3},
					   new Object[] {"5,5,5",new int[]{2,1,1},4},
					   new Object[] {"5,5,5",new int[]{2,2,1},5},
					   new Object[] {"5,5,5",new int[]{2,2,2},6},
					   new Object[] {"5,5,5",new int[]{3,2,2},7},
					   new Object[] {"5,5,5",new int[]{3,3,2},8},
					   new Object[] {"5,5,5",new int[]{3,3,3},9},
					   new Object[] {"5,5,5",new int[]{4,3,3},10},
					   new Object[] {"5,5,5",new int[]{4,4,3},11},
					   new Object[] {"5,5,5",new int[]{4,4,4},12},
					   new Object[] {"5,5,5",new int[]{5,4,4},13},
					   new Object[] {"5,5,5",new int[]{5,5,4},14},
					   new Object[] {"5,5,5",new int[]{5,5,5},15}
			   };
					   
   }
	   
	@Test
	public void readAvailableRoomsFromFileTest() {
		FileUtilities fuMock = mock(FileUtilities.class);
		Room room = new Room();
		
		//it calls the readStringsFromFile() method twice,
		//once in the Booking constructor, once in the readAvailableRoomsFromFileTest() method
		Booking booking = new Booking("xy", fuMock, room);
		booking.readAvailableRoomsFromFile();
		verify(fuMock, atLeastOnce()).readStringsFromFile(anyString());
	}
	    
	//test print availlable room to file no ability to do it
	
	@Test
	public void printAvailableRoomsToFile() {
		FileUtilities fuMock = mock(FileUtilities.class);
		Room room = new Room();
		Booking booking = new Booking("test", fuMock, room);
		booking.printAvailableRoomsToFile();
		verify(fuMock).writeAvailableRooms(anyString(), anyString());
	}
}

