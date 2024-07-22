package utar.edu.my;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;

import java.io.*;
import java.util.ArrayList;

import org.junit.*;
import org.junit.runner.RunWith;

import junitparams.Parameters;
import junitparams.JUnitParamsRunner;

@RunWith(JUnitParamsRunner.class)
public class UserTest {

	User u = new User("TestUser", "member");
	
	//test the setRoomsWishToBook
    @Test
    @Parameters({"guest, 1, 1", "normal, 2, 2", "VIP, 3, 3"})
    public void setRoomWishToBookWithValid(String member_type, int count, int expectedResult) {
        User u = new User("TestUser", member_type); // Create a User object with a name and member type
        u.setRoomsWishToBook(count); 
        assertEquals(expectedResult, u.getRoomsWishToBook()); 
    }
    
    //test getRoomsWishToBook
    @Test
    @Parameters({"guest, 1, 1", "normal, 2, 2", "VIP, 3, 3"})
    public void getRoomsWishToBookWithValid(String member_type, int count,int expectedResult)
    {
    	User u = new User("TestUser", member_type);
    	u.setRoomsWishToBook(count);
    	int actualResult = u.getRoomsWishToBook();
    	assertEquals(expectedResult,actualResult);
    }
    
    //test set name
    @Test
    @Parameters({"Jason,Jason","Emily,Emily"})
    public void setNameWithValid(String name,String expectedResult)
    {
    	//User u = new User("TestUser", "member");
    	u.setName(name);
    	String actualResult = u.getName();
    	assertEquals(expectedResult,actualResult);
    }
    
    //tes get name
    @Test
    @Parameters({"Jason,Jason","June,June"})
    public void getNameWithValid(String name, String expectedResult)
    {
    	User u1 = new User(name, "guest");
    	String actualResult = u1.getName();
    	assertEquals(expectedResult,actualResult);
    }
    
    //test getPoints
    @Test
    @Parameters({"500,500","600,600"})
    public void getPointsWithValid(int points,int expectedResult)
    {
    	//User u = new User("XuanYing","member");
    	u.setPoints(points);
    	assertEquals(expectedResult, u.getPoints());
    }
    
    //test setPoints
    @Test
    @Parameters({"100,100","200,200", "300,300","400,400"})
    public void setPointsWithValid(int points, int expectedResult)
    {
    	//User u = new User("TestUser", "member");
    	u.setPoints(points);
    	assertEquals(expectedResult,u.getPoints());
    }
    
    
    @Test
    @Parameters({"normal,100,100","VIP,100,100"})
    public void addPointsWithValid(String membership_type,int points,int expectedResult)
    {
    	User u = new User("TestUser", membership_type); 
    	u.addPoints(points);
    	assertEquals(expectedResult,u.getPoints());
    }
    
    //test setExclusive reward
    @Test
    @Parameters({"true, true", "false, false"})
    public void setExclusiveRewardWithValid(boolean excl_reward, boolean expected_result)
    {
    	User u = new User("TestUser", "normal"); 
    	u.setExclusiveReward(excl_reward);
    	if(expected_result)
    		assertTrue(u.hasExclusiveReward()); 
    	else
    		assertFalse(u.hasExclusiveReward());
    }
    
    //test getMaxRooom
    @Test
    @Parameters({"VIP,3","normal,2","guest,1"})
    public void getMaxRoomWithValid(String member_type, int expectedResult)
    {
    	User u = new User("XuanYing", member_type); 
    	int actualResult = u.getMaxRooms();
    	assertEquals(expectedResult,actualResult);
    }
   
    //test arrayList<Booking> get Bookings()
    @Test
    public void getBookingsTestWithValid() {
        User u = new User("XuanYing", "normal");
        u.bookRooms(2);
        u.bookRooms(1);
        
        Booking booking1 = u.getBookings().get(0);
        Booking booking2 = u.getBookings().get(1);
        
        ArrayList<Booking> expectedBookings = new ArrayList<Booking>();
        expectedBookings.add(booking1);
        expectedBookings.add(booking2);
        Booking[] expectedBookingsArray = new Booking[expectedBookings.size()];
        expectedBookingsArray = expectedBookings.toArray(expectedBookingsArray);
        
        ArrayList<Booking> actualBookings = u.getBookings();
        Booking[] actualBookingsArray = new Booking[actualBookings.size()];
        actualBookingsArray = actualBookings.toArray(actualBookingsArray);
        
        
        assertArrayEquals(expectedBookingsArray, actualBookingsArray); 
    }
    
    //test used ExclusiveReward
    @Test
    @Parameters({"false, false", "true, false"})
    public void testUsedExclusiveRewardWithValid(boolean exlc_reward, boolean expected_result) {
    	User u = new User("xy", "VIP");
    	u.setExclusiveReward(exlc_reward);
    	u.usedExclusiveReward();
    	if(expected_result)
    		assertTrue(u.hasExclusiveReward());
    	else
    		assertFalse(u.hasExclusiveReward());
    }
    
   
    
    //this test assumes that the available rooms are always set to 5, 5, 5 for vip deluxe and standard rooms
    //tests for not enough available rooms will be tested in booking module (setBooking() method)
    @Test
    @Parameters({"VIP, 3, true", "normal, 2, true", "guest, 1, true"})
    public void testBookRoomsValid(String member_type, int count, boolean ER) {
    	User u = new User("test", member_type);
    	boolean AR = u.bookRooms(count);
    	assertEquals(ER, AR);
    }

    
    //test setMemberType
    @Test
    @Parameters({"normal,normal","guest,guest","VIP,VIP"})
    public void setMemberTypeWithValid(String member_type,String expectedResult)
    {
    	User u = new User("XuanYing",anyString());
    	u.setMemberType(member_type);
    	assertEquals(expectedResult,u.getMemberType());
    }
    
    //test get member type
    @Test
    @Parameters({"guest,guest","normal,normal","VIP,VIP"})
    public void getMemberTypeWithValid(String member_type,String expectedResult)
    {
    	User u = new User("XuanYing",member_type);
    	assertEquals(expectedResult,u.getMemberType());
    }
    
    //test redeemExclusive reward
    @Test
    @Parameters({"false,500,0","false, 501, 1", "false, 499, 499"})
    public void redeemExclusiveRewardWithValid(boolean excl_reward, int points, int expectedResult) {
        User u = new User("XuanYing", "member");
        u.setPoints(points); 
        u.setExclusiveReward(excl_reward); 
        u.redeemExclusiveReward(); 
        assertEquals(expectedResult, u.getPoints());
    }
    
    //test redeemVipMember
    @Test
    @Parameters({"normal, 1000, VIP", "normal, 1001, VIP", "normal, 999, normal", "normal, 799, normal"})
    public void redeemVipMemberWithValid(String member_type, int points, String expectedResult) {
        User u = new User("XuanYing", member_type);
        u.setPoints(points); 
        u.redeemVipMember(); 
        assertEquals(expectedResult, u.getMemberType());
    }
    
    @Test
    @Parameters({"VIP, true", "normal, true", "VIP, false", "normal, false"})
    public void testHasExclusiveRewardWithValid(String member_type, boolean exclusive_reward){
    	User u = new User("XuanYing", member_type);
    	u.setExclusiveReward(exclusive_reward);
    	if(exclusive_reward)
    		assertTrue(u.hasExclusiveReward());
    	else
    		assertFalse(u.hasExclusiveReward());
    }
    
    //test the set rooms wish to book invalid
    @Test(expected=(IllegalArgumentException.class))
    @Parameters({"VIP, -1", "VIP, 0", "VIP, 5", "VIP, 6", "VIP, 4", "normal, -1", "normal, 0", "normal, 3", "guest, -1", "guest, 0", "guest, 2"})
    public void setRoomsWishToBookWithInvalid(String member_type, int count)
    {
    	User u = new User("xy", member_type);
    	u.setRoomsWishToBook(count);  
    }
    
    //test set Name
    @Test(expected = IllegalArgumentException.class)
    @Parameters(method = "setNameInvalidParams")
    public void setNameWithInvalid(String name) {
        u.setName(name);
    }
    
    private Object[] setNameInvalidParams()
    {
    	return new Object[]
    			{
    					new Object[] {""},
    					new Object[] {" "},
    					new Object[] {"	"},
    					new Object[] {"		"},
    					new Object[] {null}
    			};
    }
    
    //test set points
    @Test(expected = IllegalArgumentException.class)
    @Parameters ({"-1","-10","-100"})
    public void setPointsWithInvalid(int points)
    {
    	u.setPoints(points);
    }
    
    //test add point with invalid
    @Test(expected = IllegalArgumentException.class)
    @Parameters({"101","99"})
    public void addPointsWithInvalid(int points)
    {
    	u.addPoints(points);
    }
    
    //test get member type
    @Test(expected = IllegalArgumentException.class)
    @Parameters({"vip",
    	"Vip",
    	"vIp",
    	"Normal",
    	"NORMal",
    	"NORMAL",
    	"Guest",
    	"GUEST",
    	"null",
    	"",	
    	" ",
    	"	"
    	})
    public void setMemberTypeWithInvalid(String member_type)
    {
    	u.setMemberType(member_type);
    }
    
    @AfterClass
    public static void afterTest() {
    	try {
	    	PrintWriter pw = new PrintWriter("availablerooms.txt");
	    	pw.println("5,5,5");
	    	pw.close();
    	}
    	catch(IOException f)
    	{
    		System.out.println("Error resetting the availablerooms.txt file in UserTest.java" + f.getMessage());
    	}
    }
}
