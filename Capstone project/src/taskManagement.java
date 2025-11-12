import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Semaphore;

public class taskManagement {
	// coordinates of the different locations
	static final double warehouse[] = { 3, 1 };
	static final double factory[] = { 5, 6 };
	static final double dispatch[] = { 4, 5 };
	// amount of avg needed based on the amount of tonnes to be transported
	private static final int low_load = 5;
	private static final int medium_load = 10;
	private static final int high_load = 25;

	public Semaphore vehicleMutex = new Semaphore(1);
	public String file;
	private String orderID;
	public Date currentdate;
	public int orderno;
	private int op_vehicles = 0; // vehicles needed
	public ArrayList<avg> vehicles;
	public ArrayList<avg> vehiclesInNeedOfCharging;
	public rawMaterial ordermaterial;
	public movementVehicle move;
	public Thread chargingStationThread;
	// public Thread movementThread;
	public chargingStation charge;
	public exception_handling etask = new exception_handling();

	public taskManagement(Date date, int noavgs) {
		this.orderno = 1;
		this.vehicles = new ArrayList<avg>();
		this.vehiclesInNeedOfCharging = new ArrayList<avg>();
		currentdate = date;

		for (int i = 0; i < noavgs; i++) {
			avg a = new avg("avg." + i, 0.25);// id,consump %/h
			a.setActSpeed(5);
			// create/update vehicle file
			a.avgfile = (new SimpleDateFormat("yyyy-MM-dd").format(currentdate) + a.id + ".txt");
			file_ops.createUpdateLog(a.avgfile, "Started task.");
			this.vehicles.add(a);
		}

		file = ("log." + new SimpleDateFormat("yyyy-MM-dd").format(currentdate) + ".txt");

		this.charge = new chargingStation(file, vehicles, vehiclesInNeedOfCharging);
		this.chargingStationThread = new Thread(this.charge);
		this.chargingStationThread.start();
	}

	public void takeOrder(int load, String order)
			throws exception_handling.ZeroTonnesException, exception_handling.InvalidOrderException, exception_handling,
			exception_handling.VehicleNotFoundException, InterruptedException {

		ArrayList<avg> v = new ArrayList<avg>();// vehicles that'll work in the order

		orderID = (new SimpleDateFormat("yyyy-MM-dd").format(currentdate) + "Task" + "." + this.orderno++);
		// determine range of the load
		try {
			if (load > 100) {
				op_vehicles = high_load;
			} else if ((load <= 100) && (load > 50)) {
				op_vehicles = medium_load;
			} else if ((load <= 50) && (load > 0)) {
				op_vehicles = low_load;
			} else {
				etask.handleNullTonnes();
			}
			/*
			 * while (op_vehicles > this.vehicles.size()) {
			 * System.out.println("we are waiting on new vehicles to be used");
			 * Thread.sleep(1000); }
			 */
			int vind = 0;
			/*
			 * for (int i = 0; i < op_vehicles; i++) { // adds avg to v and removes it from
			 * vehicles ArrayList v.add(this.vehicles.get(vind));
			 * this.vehicles.remove(vind); }
			 */
		} catch (Throwable e) {
			System.out.println("Error: " + e.toString());
		}
		// manage order. Orders can be: {'toFactory', 'toWarehouse'. 'toDelivery'}
		this.manageOrder(order, load, v);
	}

	// TODO--------delete event_update and just update log file on movement classes
	private void manageOrder(String task, int ton, ArrayList<avg> vehiclesToBeUsed)
			throws exception_handling, exception_handling.InvalidOrderException,
			exception_handling.VehicleNotFoundException, InterruptedException {
		Date starttime = new java.util.Date();
		starttime.setTime(currentdate.getTime());
		double overallduration = 0;
		String entry;
		Thread movementThread;
		switch (task) {
		case "toFactory":

			ordermaterial = new rawMaterial("item" + orderno, "raw", ton, warehouse);
			move = new movStorage(this.orderID, factory, this.op_vehicles, vehicleMutex, vehiclesInNeedOfCharging,
					this.vehicles, file, warehouse, 0, this.ordermaterial);
			// move = new movStorage(this.orderID, factory, vehiclesToBeUsed,
			// vehiclesInNeedOfCharging, this.vehicles,
			// file, warehouse, 0, this.ordermaterial);*/
			movementThread = new Thread(move);
			movementThread.start();
			overallduration = (double) (move.timestamp.getTime() - starttime.getTime()) / 3600000; // in
			// hours

			event_update(overallduration, this.move);
			// currentdate.setTime(move.timestamp.getTime());// update taskmanager time
			break;

		case "toWarehouse":
			ordermaterial = new rawMaterial("item " + orderno, "product", ton, factory);
			move = new movStorage(this.orderID, warehouse, this.op_vehicles, vehicleMutex, vehiclesInNeedOfCharging,
					this.vehicles, file, factory, 1, this.ordermaterial);

			movementThread = new Thread(move);
			movementThread.start();

			overallduration = (double) (move.timestamp.getTime() - starttime.getTime()) / 3600000; // in
			// hours

			event_update(overallduration, this.move);
			// currentdate.setTime(move.timestamp.getTime());// update taskmanager time
			break;

		case "toDelivery":
			ordermaterial = new rawMaterial("item " + orderno, "product", ton, warehouse);

			move = new movDelivery(this.orderID, file, this.op_vehicles, vehicleMutex, vehiclesInNeedOfCharging,
					vehicles, warehouse, dispatch, this.ordermaterial);

			movementThread = new Thread(move);
			movementThread.start();

			overallduration = (double) (move.timestamp.getTime() - starttime.getTime()) / 3600000; // in
			// hours

			event_update(overallduration, this.move);
			// currentdate.setTime(move.timestamp.getTime());// update taskmanager time
			break;

		case null, default:
			etask.handleInvalidOrder();
			this.orderno -= 1;// increase order number
		}
	}

	private void event_update(double overalltime, movementVehicle move) {
		String entry = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(move.timestamp) + ": " + this.orderID
				+ " mission completed. Overall duration: " + String.format("%.2f", overalltime) + " hours.");
		file_ops.createUpdateLog(file, entry);
	}

}
