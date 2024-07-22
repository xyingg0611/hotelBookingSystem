package utar.edu.my;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatcher.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.junit.*;
import org.junit.runner.RunWith;

import junitparams.Parameters;
import junitparams.JUnitParamsRunner;

@RunWith(JUnitParamsRunner.class)
public class WaitingListTest {
	/*
	 * When a user decides to book a room, the user.bookrooms method will invoke the setBooking method from Booking
	 * class, which will return a true boolean value when the setBooking method has successfully booked the desired rooms
	 * according to the user, if not it will return a false value
	 * Test Objective #1
	 * When a false value is returned, we will add the user to the waitinglist in the application, this is where we will
	 * test whether the users who did not successfully book the desired rooms count they wished, will be added to the 
	 * WaitingList according to their member_type, we will assess whether the correct amount of users in the respective
	 * WaitingList(VIP, Member, Normal) are the same as expected
	 * 
	 * #Test Objective #2
	 * As well as whether the array of users in the actual WaitingLists are equal to the expected array
	 */
	@Before
	public void setUpTests() {
		//this is to set up the availablerooms.txt file to 5,5,5 Before each test
		try {
	    	PrintWriter pw = new PrintWriter("availablerooms.txt");
	    	pw.println("5,5,5");
	    	pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	@After
	public void afterTests() {
		try {
			//this is to reset the availablerooms.txt file to 5,5,5 after each test
	    	PrintWriter pw = new PrintWriter("availablerooms.txt");
	    	pw.println("5,5,5");
	    	pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	//these user instance variables are only used for addWaiting() and removeWaiting() testing purposes
	//these are not used in the get methods testing
	User vip1 = new User("xy","VIP");
	User vip2 = new User("as","VIP");
	User vip3 = new User("ch","VIP");
	User vip4 = new User("ws","VIP");
	User vip5 = new User("jason","VIP");
	User vip6 = new User("jasmine","VIP");
	User normal1 = new User("nor1", "normal");
	User normal2 = new User("nor2", "normal");
	User normal3 = new User("nor3", "normal");
	User normal4 = new User("nor4", "normal");
	User normal5 = new User("nor5", "normal");
	User normal6 = new User("nor6", "normal");
	User guest1 = new User("guest1", "guest");
	User guest2 = new User("guest2", "guest");
	User guest3 = new User("guest3", "guest");
	User guest4 = new User("guest4", "guest");
	User guest5 = new User("guest5", "guest");
	User guest6 = new User("guest6", "guest");
	

	//this test assumes that every user will book the maximum amount of rooms 
	//and checks if any users added to waiting list is same as the expectedResults
	 	@Test//no1
	    @Parameters(method = "getParamsForAddWaiting")
	    public void testAddWaiting(User[] users, User[] expectedResults, int [] expectedNumbersForEachMemberTypeInWaitingList) {

	 		int vipcount = 0;
	 		int membercount = 0;
	 		int normalcount = 0;
	 		
	 		User[] expectedVipWaitingList = new User[expectedNumbersForEachMemberTypeInWaitingList[0]];
	 		User[] expectedMemberWaitingList = new User[expectedNumbersForEachMemberTypeInWaitingList[1]];
	 		User[] expectedNormalWaitingList = new User[expectedNumbersForEachMemberTypeInWaitingList[2]];
	    	WaitingList wl = new WaitingList();
	    	
	    	for(int x = 0; x < users.length; x++)
	    	{
	    		boolean bookingOutcome = users[x].bookRooms(users[x].getMaxRooms());
	    		if(!bookingOutcome)
	    		{
	    			wl.addWaiting(users[x], users[x].getMaxRooms());
	    		}
	    	}
	    	assertEquals(expectedNumbersForEachMemberTypeInWaitingList[0], wl.getVipWaiting().size());
	    	assertEquals(expectedNumbersForEachMemberTypeInWaitingList[1], wl.getMemberWaiting().size());
	    	assertEquals(expectedNumbersForEachMemberTypeInWaitingList[2], wl.getNormalWaiting().size());
	    	for(int x = 0; x < expectedResults.length; x++)
	    	{
	    		if(expectedResults[x].getMemberType().equals("VIP"))
	    		{
	    			expectedVipWaitingList[vipcount] = expectedResults[x];
	    			vipcount++;
	    		}
	    		else if(expectedResults[x].getMemberType().equals("normal"))
	    		{
	    			expectedMemberWaitingList[membercount] = expectedResults[x];
	    			membercount++;
	    		}
	    		else if(expectedResults[x].getMemberType().equals("guest"))
	    		{
	    			expectedNormalWaitingList[normalcount] = expectedResults[x];
	    			normalcount++;
	    		}
	    	}
	    	//The tests below do assertArrayEquals for every type of waiting list if their size is more than 0
	    	if(expectedNumbersForEachMemberTypeInWaitingList[0]>0)
	    	{
		    	User[] actualUsersArray = new User[wl.getVipWaiting().size()];
		    	actualUsersArray = wl.getVipWaiting().toArray(actualUsersArray);
		    	assertArrayEquals(expectedVipWaitingList, actualUsersArray);
	    	}
	    	if(expectedNumbersForEachMemberTypeInWaitingList[1]>0)
	    	{
		    	User[] actualUsersArray = new User[wl.getMemberWaiting().size()];
		    	actualUsersArray = wl.getMemberWaiting().toArray(actualUsersArray);
		    	assertArrayEquals(expectedMemberWaitingList, actualUsersArray);
	    	}
	    	if(expectedNumbersForEachMemberTypeInWaitingList[2]>0)
	    	{
		    	User[] actualUsersArray = new User[wl.getNormalWaiting().size()];
		    	actualUsersArray = wl.getNormalWaiting().toArray(actualUsersArray);
		    	assertArrayEquals(expectedNormalWaitingList, actualUsersArray);
	    	}
	    	
	    }
	
	 private Object[] getParamsForAddWaiting() {
	    	return new Object[] {
	    			//1st parameter: the users that want to book rooms ,
	    			//2nd parameter: expected users that are added to waiting list, 
	    			//3rd parameter: expected numbers of each member_type that were added to WL {vip, normal, guest}
	    			new Object[] {new User[] {vip1, vip2, vip3, vip4, vip5, vip6}, new User[] {vip6}, new int[] {1, 0, 0}},
	    			new Object[] {new User[] {normal1, normal2, normal3, normal4,normal5,normal6}, new User[] {normal6}, new int[] {0,1,0}},
	    			new Object[] {new User[] {guest1, guest2, guest3, guest4, guest5, guest6}, new User[] {guest6}, new int[] {0,0,1}},
	    			new Object[] {new User[] {vip1, normal3, normal1, normal2, guest5, guest6}, new User[] {}, new int[] {0,0,0}},
	    			new Object[] {new User[] {vip1, vip2, normal1, normal2, normal3, normal4, vip3, vip4, vip5, guest5, guest6}, new User[] {vip3, vip4, vip5, guest6}, new int[] {3,0,1}},
	    			new Object[] {new User[] {vip1, vip2, vip3, normal1, normal2, normal3, normal4, guest1, vip4, vip5, guest5, guest6}, new User[] {normal4, guest1, vip4, vip5, guest5, guest6}, new int[] {2,1,3}},
	    			new Object[] {new User[] {vip1, vip2, vip3, vip4,vip5,vip6, normal1, normal2, normal3, normal4, guest1, guest4, guest6}, new User[] {vip6, normal1, normal2, normal3, normal4, guest1, guest4, guest6}, new int[] {1,4,3}},
	    	};
	    }
	    
	 
	 //this test is similar to the test above, just that the every normal user here are set to have exclusive reward
	 //since the normal users can redeem vip room if they have exclusive reward
	 @Test//no2
	 @Parameters(method = "getParamsForTestAddWaitingWithExclusiveReward")
	 public void testAddWaitingWithExclusiveReward(User[] users, User[] expectedResults, int [] expectedNumbersForEachMemberTypeInWaitingList) {
			
	 		int vipcount = 0;
	 		int membercount = 0;
	 		int normalcount = 0;
	 		
	 		for(User x : users)
	 		{
	 			if(x.getMemberType().equals("normal"))
	 			{
	 				x.setExclusiveReward(true);
	 			}
	 		}
	 		
	 		User[] expectedVipWaitingList = new User[expectedNumbersForEachMemberTypeInWaitingList[0]];
	 		User[] expectedMemberWaitingList = new User[expectedNumbersForEachMemberTypeInWaitingList[1]];
	 		User[] expectedNormalWaitingList = new User[expectedNumbersForEachMemberTypeInWaitingList[2]];
	    	WaitingList wl = new WaitingList();
	    	
	    	for(int x = 0; x < users.length; x++)
	    	{
	    		boolean bookingOutcome = users[x].bookRooms(users[x].getMaxRooms());
	    		if(!bookingOutcome)
	    		{
	    			wl.addWaiting(users[x], users[x].getMaxRooms());
	    		}
	    	}
	    	assertEquals(expectedNumbersForEachMemberTypeInWaitingList[0], wl.getVipWaiting().size());
	    	assertEquals(expectedNumbersForEachMemberTypeInWaitingList[1], wl.getMemberWaiting().size());
	    	assertEquals(expectedNumbersForEachMemberTypeInWaitingList[2], wl.getNormalWaiting().size());
	    	for(int x = 0; x < expectedResults.length; x++)
	    	{
	    		if(expectedResults[x].getMemberType().equals("VIP"))
	    		{
	    			expectedVipWaitingList[vipcount] = expectedResults[x];
	    			vipcount++;
	    		}
	    		else if(expectedResults[x].getMemberType().equals("normal"))
	    		{
	    			expectedMemberWaitingList[membercount] = expectedResults[x];
	    			membercount++;
	    		}
	    		else if(expectedResults[x].getMemberType().equals("guest"))
	    		{
	    			expectedNormalWaitingList[normalcount] = expectedResults[x];
	    			normalcount++;
	    		}
	    	}
	    	//The tests below do assertArrayEquals for every type of waiting list if their size is more than 0
	    	if(expectedNumbersForEachMemberTypeInWaitingList[0]>0)
	    	{
		    	User[] actualUsersArray = new User[wl.getVipWaiting().size()];
		    	actualUsersArray = wl.getVipWaiting().toArray(actualUsersArray);
		    	assertArrayEquals(expectedVipWaitingList, actualUsersArray);
	    	}
	    	if(expectedNumbersForEachMemberTypeInWaitingList[1]>0)
	    	{
		    	User[] actualUsersArray = new User[wl.getMemberWaiting().size()];
		    	actualUsersArray = wl.getMemberWaiting().toArray(actualUsersArray);
		    	assertArrayEquals(expectedMemberWaitingList, actualUsersArray);
	    	}
	    	if(expectedNumbersForEachMemberTypeInWaitingList[2]>0)
	    	{
		    	User[] actualUsersArray = new User[wl.getNormalWaiting().size()];
		    	actualUsersArray = wl.getNormalWaiting().toArray(actualUsersArray);
		    	assertArrayEquals(expectedNormalWaitingList, actualUsersArray);
	    	}
	    	
	 }

	 private Object[] getParamsForTestAddWaitingWithExclusiveReward() {
		 return new Object[] {
				 new Object[] {new User[] {normal1, normal2, normal3, normal4, normal5, vip2, normal6, guest1, guest2}, new User[] {guest1, guest2}, new int[] {0,0,2}},
				 new Object[] {new User[] {vip1, normal1, normal2, normal3, normal4, normal5, vip2, normal6, guest1, guest2}, new User[] {vip2, guest1, guest2}, new int[] {1,0,2}},
				 new Object[] {new User[] {normal1, normal2, normal3, vip1, normal4, guest3, vip2, normal6, guest1, guest2}, new User[] {normal6, guest1, guest2}, new int[] {0,1,2}},
				 new Object[] {new User[] {normal1, normal2, normal3, normal4, vip1, normal5, normal6, guest1, guest2}, new User[] {guest1, guest2}, new int[] {0,0,2}},
				 new Object[] {new User[] {vip1, vip2, vip3, vip4, normal1, normal2, normal3, guest1, normal4, guest2, guest4, vip6}, new User[] {normal2, normal3, normal4, guest2, guest4, vip6}, new int[] {1,3,2}},
				 new Object[] {new User[] {normal1, normal2, normal3, normal4, vip1, normal5, normal6, guest6, guest2, vip2, vip3, vip4, guest5}, new User[] {guest6, guest2, vip2, vip3, vip4, guest5}, new int[] {3,0,3}},
				 new Object[] {new User[] {vip1, vip2, normal3, normal4, vip1, normal5, normal6, guest6, guest2, vip5, vip3, vip4, guest5}, new User[] {normal6, guest6, guest2, vip5,vip3, vip4, guest5}, new int[] {3,1,3}},
				 
		 };
	 }
    
    
    //test getVipWaitingList
	 //for vip members
	 //This test is just to test whether the user will be correctly placed into their respective waiting list
    @Test
    public void getVipWaitingTest()
    {
    	User [] users = {
    	new User("xy", "VIP"),//0
    			
    	new User("xy", "VIP"),//1
    	new User("xy", "VIP"),//2
    	
    	new User("ws", "normal"),//3
    	new User("xy", "VIP"),//4
    	new User("ws", "normal"),//5
    	new User("xy", "VIP"),//6
    	new User("ws", "normal"),//7
    
    	new User("ws", "guest"),//8
    	new User("xy", "VIP"),//9
    	new User("xy", "VIP"),//10
    	new User("ws", "guest")};//11
    	WaitingList wl = new WaitingList();
    	
    	//add the waiting in the instance, 
    	//the 1 is the wish of room that the user want to book
    	for(User user : users)
    		wl.addWaiting(user, 1);
    	
    	User[] expectedVIPWaitingListUsers = new User[] {users[0], users[1], users[2], users[4], users[6], users[9], users[10]};
    	
    	ArrayList<User> actualVIPWaitingList = wl.getVipWaiting();
    	User[] actualVIPWaitingListUsers = new User[actualVIPWaitingList.size()];
    	actualVIPWaitingListUsers = actualVIPWaitingList.toArray(actualVIPWaitingListUsers);
    	
    	assertArrayEquals(expectedVIPWaitingListUsers, actualVIPWaitingListUsers);
    }
    
    //test get member waiting list valid
    //for normal member
    //This test is just to test whether the user will be correctly placed into their respective waiting list
    @Test
    public void getMemberWaitingTest() {
    	User [] users = {
    	new User("xy", "VIP"),//0
    			
    	new User("xy", "VIP"),//1
    	new User("xy", "VIP"),//2
    	
    	new User("ws", "normal"),//3
    	new User("xy", "VIP"),//4
    	new User("ws", "normal"),//5
    	new User("xy", "VIP"),//6
    	new User("ws", "normal"),//7
    
    	new User("ws", "guest"),//8
    	new User("xy", "VIP"),//9
    	new User("xy", "VIP"),//10
    	new User("ws", "guest")};//11
    	WaitingList wl = new WaitingList();
    	
    	//add the waiting in the instance, 
    	//the 1 is the wish of room that the user want to book
    	for(User user : users)
    		wl.addWaiting(user, 1);
    	
    	User[] expectedMemberWaitingListUsers = new User[] {users[3], users[5], users[7]};
    	
    	ArrayList<User> actualMemberWaitingList = wl.getMemberWaiting();
    	User[] actualMemberWaitingListUsers = new User[actualMemberWaitingList.size()];
    	actualMemberWaitingListUsers = actualMemberWaitingList.toArray(actualMemberWaitingListUsers);
    	
    	assertArrayEquals(expectedMemberWaitingListUsers, actualMemberWaitingListUsers);
    }
    
   
    //test get normal waiting list valid
    //for guest
	 //This test is just to test whether the user will be correctly placed into their respective waiting list
    @Test
    public void getNormalWaitingTest()
    {
    	User [] users = {
    	new User("xy", "VIP"),//0
    			
    	new User("xy", "VIP"),//1
    	new User("xy", "VIP"),//2
    	
    	new User("ws", "normal"),//3
    	new User("xy", "VIP"),//4
    	new User("ws", "normal"),//5
    	new User("xy", "VIP"),//6
    	new User("ws", "normal"),//7
    
    	new User("ws", "guest"),//8
    	new User("xy", "VIP"),//9
    	new User("xy", "VIP"),//10
    	new User("ws", "guest")};//11
    	WaitingList wl = new WaitingList();
    	
    	//add the waiting in the instance
    	for(User user : users)
    		wl.addWaiting(user, 1);
    	
    	User[] expectedNormalWaitingListUsers = new User[] {users[8], users[11]};
    	
    	ArrayList<User> actualNormalWaitingList = wl.getNormalWaiting();
    	User[] actualNormalWaitingListUsers = new User[actualNormalWaitingList.size()];
    	actualNormalWaitingListUsers = actualNormalWaitingList.toArray(actualNormalWaitingListUsers);
    	
    	assertArrayEquals(expectedNormalWaitingListUsers, actualNormalWaitingListUsers);
    }
    
  
    //test remove waiting list
    //since the removeWaiting() will ask the user to confirm, we will fix the answer to yes("y")
    @Test
    @Parameters(method = "provideTestDataForRemoveWaiting")
    public void testRemoveWaitingWithValid(User[] initialUsers, User[] usersToRemove, User[] expectedUsersLeft) {
        ConsoleUtilities cuMock = mock(ConsoleUtilities.class);
        when(cuMock.nextLine()).thenReturn("Y");
        WaitingList wl = new WaitingList(cuMock);

        // Add initial users to the waiting list
        for (User user : initialUsers) {
            wl.addWaiting(user, user.getMaxRooms());
        }

        // Remove users from the waiting list
        for (User user : usersToRemove) {
            wl.removeWaiting(user);
        }
        
        ArrayList<User>actualUsersLeftAL = new ArrayList<User>();
        
        for(User user: wl.getVipWaiting())
        	actualUsersLeftAL.add(user);
        for(User user: wl.getMemberWaiting())
        	actualUsersLeftAL.add(user);
        for(User user: wl.getNormalWaiting())
        	actualUsersLeftAL.add(user);
        User[] actualUsersLeft = new User[actualUsersLeftAL.size()];
        
        actualUsersLeft = actualUsersLeftAL.toArray(actualUsersLeft);

        assertArrayEquals(expectedUsersLeft, actualUsersLeft);
    }

    private Object[] provideTestDataForRemoveWaiting() {

        return new Object[]{ 
        		new Object[]{
        				new User[]{vip1, vip2, vip3, vip4}, // Initial users
        				new User[]{vip1}, // user that will remove in waiting list
        				new User[]{vip2, vip3, vip4} //expected result
        		},
        		new Object[] {
        				new User[] {vip1, vip2, normal1, normal2, guest1, guest2},
        				new User[] {vip1, guest1, normal1},
        				new User[] {vip2, normal2, guest2}
        		},
        		new Object[] {
        				new User[] {normal1, vip2, vip1, normal2, guest1, guest2, guest3},
        				new User[] {vip2, guest1, normal1, guest3},
        				new User[] {vip1, normal2, guest2}
        		},
        		new Object[] {
        				new User[] {vip1, vip2, vip3, vip4, vip5, vip6, normal1, normal2, normal3, normal4, guest1, guest2, guest3, guest4},
        				new User[] {},
        				new User[] {vip1, vip2, vip3, vip4, vip5, vip6, normal1, normal2, normal3, normal4, guest1, guest2, guest3, guest4}
        		},
        		new Object[] {
        				new User[] {vip1, vip2, vip3, vip4, vip5, vip6, normal1, normal2, normal3, normal4, guest1, guest2, guest3, guest4},
        				new User[] {vip1, vip2, vip3, vip4, vip5, vip6, normal1, normal2, normal3, normal4, guest1, guest2, guest3, guest4},
        				new User[] {}
        		},
        		new Object[] {
        				new User[] {vip1, vip2, vip3, vip4, vip5, vip6, normal1, normal2, normal3, normal4, guest1, guest2, guest3, guest4},
        				new User[] {vip1, vip3, vip4, vip5, vip6, normal1, normal3, normal4, guest1, guest3, guest4},
        				new User[] {vip2, normal2, guest2}
        		},
        		
        };
    }

    //Test the invalid values passing to the addWaiting() method
    @Test(expected= IllegalArgumentException.class)
    @Parameters(method = "getDataForTestAddWaitingInvalid")
    public void testAddWaitingInvalid(User user, int count)
    {
    	WaitingList wl = new WaitingList();
    	wl.addWaiting(user, count);
    }
    
    private Object[] getDataForTestAddWaitingInvalid() {
    	User user1 = null;
    	User vip = new User("xy", "VIP");
    	User normal = new User("xy", "normal");
    	User guest = new User("xy", "guest");
    	return new Object[] {
    			new Object[] {user1, 1},
    			new Object[] {vip, 0},
    			new Object[] {vip, 4},
    			new Object[] {normal, 0},
    			new Object[] {normal, 3},
    			new Object[] {guest, 0},
    			new Object[] {guest, 2},
    	};
    }
    //Test the invalid values passing to the removeWaiting() method
    @Test(expected= IllegalArgumentException.class)
    @Parameters(method = "getDataForTestRemoveWaitingInvalid")
    public void testRemoveWaitingInvalid(User user)
    {
    	WaitingList wl = new WaitingList();
    	wl.removeWaiting(user);
    }
    
    private Object[] getDataForTestRemoveWaitingInvalid() {
    	User user1 = null;
    	return new Object[] {
    			new Object[] {user1},
    	};
    }
   
}