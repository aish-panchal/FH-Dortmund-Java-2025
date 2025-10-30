import java.io.Console;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/*Type of tasks: {"toFactory", "toWarehouse","toDelivery","toCharge"}
 * */
public class main {
	
    public static void main (String[] args) {
		
	Date today = new java.util.Date();
	Scanner logname;
	logname = new Scanner(System.in);
	
	taskManagement workday = new taskManagement(today); 
	workday.takeOrder(50, "toFactory");//Order of 50 tones from warehouse to factory
	workday.takeOrder(100, "toWarehouse"); //Order of 100 tones from factory to warehouse
	workday.takeOrder(50, "toDelivery");//Order of 50 tones to Delivery
	
	while(true) {
		System.out.println("Enter searching log file: ");
		file_ops.openLog(logname.nextLine());
	}
	
    }

}