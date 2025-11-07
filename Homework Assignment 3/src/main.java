import java.io.Console;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

//Type of tasks: {"toFactory", "toWarehouse","toDelivery","toCharge"}
  
public class main {
	
    public static void main (String[] args) throws exception_handling.ZeroTonnesException, exception_handling.InvalidOrderException, exception_handling, exception_handling.VehicleNotFoundException {
		
    	
    	//humanMachineInterface.launch(args);
		Date today = new java.util.Date();
		Scanner logname;
		logname = new Scanner(System.in);
		taskManagement workday = new taskManagement(today,25); //date and avg in total 
		//workday.takeOrder(50, "toFactory");//Order of 50 tones from warehouse to factory
		//workday.takeOrder(100, "toWarehouse");
		while(true) {
			System.out.println("loop");
			workday.takeOrder(50, "toDelivery");
			
		}
		
		
		/*while(true) {
			System.out.println("Enter searching log file: ");
			file_ops.openLog(logname.nextLine());
		}*/
    }

}