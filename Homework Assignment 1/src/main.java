

import java.text.SimpleDateFormat;
import java.util.Date;

/*Type of tasks: {"toFactory", "toWarehouse","toDelivery","toCharge"}
 * */
public class main {
	
	public static void main (String[] args) {
		
		Date today = new java.util.Date();
		int day=0;

		taskManagement workday = new taskManagement(today); //array of days //depending on number of days should archive/delete files
		
		workday.takeOrder(50, "toFactory");//Order of 50 tones from warehouse to factory
		workday.takeOrder(100, "toWarehouse");
		workday.takeOrder(200, "toDelivery");
	}

}
