package utar.edu.my;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import org.junit.*;

public class TestUserWithTestDataFromTextFile {
	
	static ArrayList<String[]> linesRead;
	static Scanner input;
	
	@BeforeClass
	public static void setupClass() {
		linesRead = new ArrayList<String[]>();
		String fileName = "setRoomsWishToBookWithValid.txt";
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
			String member_type = linesRead.get(x)[0];
			int count = Integer.parseInt(linesRead.get(x)[1]);
			int expectedResult = Integer.parseInt(linesRead.get(x)[2]);
			User u = new User("test", member_type);
			u.setRoomsWishToBook(count);
			assertEquals(expectedResult, u.getRoomsWishToBook());
		}
	}
}
