import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class exception_handling extends Exception {
	
	public void multipleExceptions() {
		try {
			File file = new File("missing.txt");
			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()){
				System.out.println(scanner.nextLine());
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			System.out.println("File is missing: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IO exception: : " + e.getMessage());
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
	
	
	//Task Management, Charging Station, Movement Vehicle exceptions
	public class InvalidOrderException extends Exception{
		public InvalidOrderException(String s) {
			super(s);
		}
	}
	//chained exception
	public void handleInvalidOrder() throws exception_handling.InvalidOrderException{ 
		try {
			InvalidOrderException orderex = new InvalidOrderException("Order not found");
		throw orderex;
		}catch (InvalidOrderException orderex) {
			throw (InvalidOrderException ) orderex.initCause(new Exception("Invalid order. Orders can be: {'toFactory', 'toWarehouse'. 'toDelivery'}"));
		}
	}
	
	public class ZeroTonnesException extends Exception{
		public ZeroTonnesException(String s) {
			super(s);
		}
	}
	
	public  void handleNullTonnes() throws exception_handling.ZeroTonnesException{
		throw new ZeroTonnesException("Can't process null tonnes");
	}
	
	public class VehicleNotFoundException extends Exception{
		public VehicleNotFoundException(String s) {
			super(s);
		}
	}
	
	public  void handleVehicleNotFound() throws exception_handling.VehicleNotFoundException{
		throw new VehicleNotFoundException("There's no vehicle in this index");
	}
	
}
