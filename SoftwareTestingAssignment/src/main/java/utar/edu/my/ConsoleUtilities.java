package utar.edu.my;
import java.util.Scanner;
public class ConsoleUtilities {
	
	Scanner scanner = new Scanner(System.in);

	public void print(String message)
	{
		System.out.print(message);
	}
	
	public void println(String message) {
		System.out.println(message);
	}
	
	public void printf(String message, double x, double y, double z)
	{
		System.out.printf(message, x, y, z);
	}
	
	public void printf(String message, double x)
	{
		System.out.printf(message, x);
	}
	
	public String nextLine() {
		return scanner.nextLine();
	}
}
