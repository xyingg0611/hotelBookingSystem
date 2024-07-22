package utar.edu.my;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.junit.*;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
@RunWith(JUnitParamsRunner.class)
public class IntegrationTest {
	/*
	 * The integration test will first lets multiple users book rooms at their maximum rooms allowed,
	 * then it will selects a certain number of users to cancel Booking, which includes both users who successfully 
	 * placed a booking and users who did not book a booking
	 * This IntegrationTest Primarily test the cancelBooking() method as implemented in the Application class
	 * as addWaiting() has been tested in the unit testing of WaitingList class: WaitingListTest
	 * 
	 * Test Objective #1:
	 * If the users who successfully placed a booking are selected to cancel booking, we will test whether their booking
	 * isPending() value is set back to false after
	 * 
	 * Test Objective #2:
	 * If the users who did not place a booking are selected to cancel booking, we will test whether they are removed
	 * from their respective waiting lists according to member_type
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
	

	//this test will mock the ConsoleUtilities class in order to fix the answer to "y" when user is prompted to cancel
	//booking
	//As usual, we will assume every user book the maximum allowed of rooms to truly test the reliability 
	//potential of the hotel room booking system
	@Test//no1
	@Parameters(method = "getParamsForTestBookingAndCancel")
	public void testBookingAndCancel(User[] users, User[] usersCancelBooking, User[] expectedRemainingUsersInWL, User[] usersBookingPendingStillTrue) {
		ConsoleUtilities cuMock = mock(ConsoleUtilities.class);
		when(cuMock.nextLine()).thenReturn("y");
	 	WaitingList wl = new WaitingList(cuMock);
	 		
	 	for(User user : users)
	 	{
	 		boolean bookingOutcome = user.bookRooms(user.getMaxRooms());
	 		if(!bookingOutcome)
	 		{
	 			wl.addWaiting(user, user.getMaxRooms());
	 		}
	 	}
	 	
	 	ArrayList<User> actualUsersLeftInWL = new ArrayList<User>();
	 	for(User user : usersCancelBooking)
	 	{
	 	/*
	 	* cancelOutcome will check if the user were placed in waiting list, 
	 	* if yes, it will remove the user from the WL and return a true boolean value
	 	* which will then be tested whether the user has been removed from the WL ArrayList
	 	* if no, it will return a false boolean value, and will proceed to cancel the successfully placed booking
	 	* which will then be tested whether their previous successfully placed booking pending status is set to false
	 	*/
	 		boolean cancelBookingFromWL = wl.removeWaiting(user);
	 		if(!cancelBookingFromWL)
	 		{
	 			for(int x = 0; x < user.getBookings().size(); x++) {
	 				user.getBookings().get(x).cancelBooking();
	 				assertFalse(user.getBookings().get(x).isPending());
	 			}
	 		}
	 	}
			if(wl.getVipWaiting().size()>0)
			{
				for(int x = 0; x < wl.getVipWaiting().size(); x++)
					actualUsersLeftInWL.add(wl.getVipWaiting().get(x));
			}
			if(wl.getMemberWaiting().size()>0)
			{
				for(int x = 0; x < wl.getMemberWaiting().size(); x++)
					actualUsersLeftInWL.add(wl.getMemberWaiting().get(x));
			}
			if(wl.getNormalWaiting().size()>0)
			{
				for(int x = 0; x < wl.getNormalWaiting().size(); x++)
					actualUsersLeftInWL.add(wl.getNormalWaiting().get(x));
			}
			User [] actualUsersLeftArray = new User[actualUsersLeftInWL.size()];
			actualUsersLeftArray = actualUsersLeftInWL.toArray(actualUsersLeftArray);
			assertArrayEquals(expectedRemainingUsersInWL, actualUsersLeftArray);
	 	for(int x = 0; x < usersBookingPendingStillTrue.length; x++)
	 	{
	 			assertTrue(usersBookingPendingStillTrue[x].getBookings().get(0).isPending());
	 	}
	}
	
	 private Object[] getParamsForTestBookingAndCancel() {
	    	return new Object[] {
	    			//1st parameter: the users that want to book rooms
	    			//2nd parameter: the selected users who want to cancel booking
	    			//3rd parameter: the remaining users who are still in Waiting List
	    			//4th parameter: the users whose successfully placed a booking, but didn't cancel
	    			new Object[] {
	    					new User[] {vip1, vip2, vip3, vip4, vip5, vip6}, 
	    					new User[] {vip5, vip6}, 
	    					new User[] {},
	    					new User[] {vip1, vip2, vip3, vip4}
	    			},
	    			new Object[] {
	    					new User[] {vip1, vip2, normal1, normal2, normal3, vip6, guest1, normal4, guest4, vip5}, 
	    					new User[] {guest1, vip6, normal4}, 
	    					new User[] {vip5, guest4},
	    					new User[] {vip1, vip2, normal1, normal2, normal3}
	    			}
	    	};
	    }
}