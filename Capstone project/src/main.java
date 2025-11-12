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
		taskManagement workday = new taskManagement(today, 25); // date and avg in total
		/*
		 * Thread t1 = new Thread(() -> { try { workday.takeOrder(50, "toFactory",1); }
		 * catch (exception_handling.ZeroTonnesException |
		 * exception_handling.InvalidOrderException | exception_handling |
		 * exception_handling.VehicleNotFoundException e) { // TODO Auto-generated catch
		 * block e.printStackTrace(); } }); Thread t2 = new Thread(() ->{ try {
		 * workday.takeOrder(200, "toDelivery",2); } catch
		 * (exception_handling.ZeroTonnesException |
		 * exception_handling.InvalidOrderException | exception_handling |
		 * exception_handling.VehicleNotFoundException e) { // TODO Auto-generated catch
		 * block e.printStackTrace(); } });
		 * 
		 * t1.start(); t2.start();
		 */
		// Order of 100 tones from warehouse to factory
		// workday.takeOrder(10, "toWarehouse");
		// workday.takeOrder(5,"toFactory");
		// workday.takeOrder(5, "toDelivery");//wrong task
		int i = 1;
		while (true) {
			workday.takeOrder(10, "toFactory");// 5 avg
			System.out.println("task: " + i++);
			// workday.takeOrder(50, "toWarehouse");// 10
			// System.out.println("task: " + i++);
			// workday.takeOrder(150, "toDelivery");// 25
			// workday.takeOrder(50, "toWarehouse");// 10
			Thread.sleep(100);
		}

		/*
		 * while(true) { System.out.println("Enter searching log file: ");
		 * file_ops.openLog(logname.nextLine()); }
		 */
	}

}