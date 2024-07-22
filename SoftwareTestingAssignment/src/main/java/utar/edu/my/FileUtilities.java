package utar.edu.my;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class FileUtilities {

	public String readStringsFromFile(String fileName) {
			File fileToRead = new File(fileName);
			Scanner inputStream = null;
			
			try {
				inputStream = new Scanner(fileToRead);
			}
			
			catch(FileNotFoundException e)
			{
				throw new IllegalArgumentException("File does not exist : " + fileName);
			}
			
			String lineRead = null;
			while (inputStream.hasNextLine())
			{
				lineRead = inputStream.nextLine();
			}
			inputStream.close();
			
			return lineRead;
		}
	public void writeAvailableRooms(String fileName, String data) {
		try {
			PrintWriter fw = new PrintWriter(fileName);
			fw.print(data);
			fw.close();
		}
		catch(IOException i)
		{
			System.out.println("Error outputting data: " + i.getMessage());
		}
	}


}
