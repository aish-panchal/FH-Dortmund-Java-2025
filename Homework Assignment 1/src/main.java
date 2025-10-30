import java.io.Console;
import java.text.SimpleDateFormat;
import java.util.Date;


/*Type of tasks: {"toFactory", "toWarehouse","toDelivery","toCharge"}
 * */
public class main {
	
    public static void main (String[] args) {
		
	Date today = new java.util.Date();

	taskManagement workday = new taskManagement(today); 
	workday.takeOrder(50, "toFactory");//Order of 50 tones from warehouse to factory
	workday.takeOrder(100, "toWarehouse");
	workday.takeOrder(50, "toDelivery");
	
	file_ops.openLog("ChargingStation");
	
    }

}