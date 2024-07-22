package utar.edu.my;

import static org.junit.Assert.*;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(value = Suite.class)
@SuiteClasses (value = {

		WaitingListTest.class,
		BookingTest.class,
		IntegrationTest.class,
		UserTest.class,
		TestUserWithTestDataFromTextFile.class,
		TestBookingWithTestDataFromTextFile.class
})


public class TestSuite {

}
