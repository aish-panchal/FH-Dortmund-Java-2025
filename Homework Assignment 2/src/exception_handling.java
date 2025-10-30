import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class exception_handling {
	
	public void multipleExceptions() {
		try {
			int[] arr = {1, 2, 3};
			int num = arr[4];
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("ArrayIndexOutofBoundsException demo: " + e.getMessage());
		} 
		
		try {
			int division = 7 / 0;
		} catch (ArithmeticException e) {
			System.out.println("ArithmeticException demo: " + e.getMessage());
		}
	}
	
	public void rethrowingExceptions() {
		
	}
	
	public void resourceManagement() {
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get("testFile.txt"))){
		    writer.write("Log event: recorded"); 		
		} catch (IOException e) {
			System.out.println("IOException demo for resource management: " + e.getMessage());
		}
	}
	
	public void chainingExceptions() {
		
	}
	
}
