import java.util.Date;

//Type of tasks: {"toFactory", "toWarehouse","toDelivery","toCharge"}

public class main {

	public static void main(String[] args)
			throws exception_handling.ZeroTonnesException, exception_handling.InvalidOrderException, exception_handling,
			exception_handling.VehicleNotFoundException, InterruptedException {
		// humanMachineInterface.launch(args);
		Date today = new java.util.Date();
		// Scanner logname;
		// logname = new Scanner(System.in);
		taskManagement workday = new taskManagement(today, 51); // date and avg in total

		// workday.takeOrder(10, "toWarehouse");
		// workday.takeOrder(5, "toFactory");
		// workday.takeOrder(5, "toDelivery");// wrong task
		while (true) {
			workday.takeOrder(70, "toWarehouse");// 10 avgs
			workday.takeOrder(150, "toDelivery");// 25 avgs
			workday.takeOrder(10, "toWarehouse");// 5 avgs
			// Thread.sleep(100);
		}
//		if (load > 100) {
//			op_vehicles = high_load;
//		} else if ((load <= 100) && (load > 50)) {
//			op_vehicles = medium_load;
//		} else if ((load <= 50) && (load > 0)) {
//			op_vehicles = low_load;
//		} else {
		/*
		 * while(true) { System.out.println("Enter searching log file: ");
		 * file_ops.openLog(logname.nextLine()); }
		 */
	}

}