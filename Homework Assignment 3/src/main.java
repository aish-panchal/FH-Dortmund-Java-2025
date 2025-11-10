/*import java.io.Console;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

//Type of tasks: {"toFactory", "toWarehouse","toDelivery","toCharge"}
  
public class main {
	
    public static void main (String[] args) throws exception_handling.ZeroTonnesException, exception_handling.InvalidOrderException, exception_handling, exception_handling.VehicleNotFoundException {
	  	//humanMachineInterface.launch(args);
		Date today = new java.util.Date();
		//Scanner logname;
		//logname = new Scanner(System.in);
		taskManagement workday = new taskManagement(today,25); //date and avg in total 
		
		Thread t1 = new Thread(() -> {
			try {
				workday.takeOrder(50, "toFactory",1);
			} catch (exception_handling.ZeroTonnesException | exception_handling.InvalidOrderException
					| exception_handling | exception_handling.VehicleNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		Thread t2 = new Thread(() ->{
			try {
				workday.takeOrder(200, "toDelivery",2);
			} catch (exception_handling.ZeroTonnesException | exception_handling.InvalidOrderException
					| exception_handling | exception_handling.VehicleNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		t1.start();
		t2.start();
		
		//Order of 100 tones from warehouse to factory
		//workday.takeOrder(100, "toWarehouse");
		
		/*while(true) {
			System.out.println("Enter searching log file: ");
			file_ops.openLog(logname.nextLine());
		}*
    }

}*/