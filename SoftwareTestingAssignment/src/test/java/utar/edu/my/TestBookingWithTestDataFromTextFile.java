package utar.edu.my;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.BeforeClass;
import org.junit.Test;

public class TestBookingWithTestDataFromTextFile {

	static ArrayList<String[]> linesRead;
	static Scanner input;
	
	@BeforeClass
	public static void setupClass() {
		linesRead = new ArrayList<String[]>();
		String fileName = "getNameOfUserWithValid.txt";
		input = null;

		try {
			input = new Scanner(new File(fileName));
		}

		catch (FileNotFoundException e) {
			System.out.println("Error opening the file " + fileName);
			System.exit(0);
		}

		while (input.hasNextLine()) {
			String singleLine = input.nextLine();
			String[] tokens = singleLine.split(",");
			linesRead.add(tokens);
		}
		input.close();
	}
	
	@Test
	public void testSetRoomsWishToBookWithValidFromTxt() {
		for(int x = 0; x < linesRead.size(); x++)
		{
			String name_set = linesRead.get(x)[0];
			String expectedResult = linesRead.get(x)[1];
			Booking b = new Booking(name_set);
			assertEquals(expectedResult, b.getNameOfUser());
		}
	}

}
