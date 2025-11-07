import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class taskManagement {
	// coordinates of the different locations
	static final double warehouse[] = { 3, 1 };
	static final double factory[] = { 5, 6 };
	static final double dispatch[] = { 4, 5 };
	// amount of avg needed based on the amount of tonnes to be transported
	private static final int low_load = 5;
	private static final int medium_load = 10;
	private static final int high_load = 25;

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
	public chargingStation charge;
	public exception_handling etask = new exception_handling();

	public taskManagement(Date date, int noavgs) {
		this.orderno = 1;
		this.vehiclesInNeedOfCharging = new ArrayList<avg>();
		currentdate = date;
		ArrayList<avg> vehicles = new ArrayList<avg>();
		for (int i = 0; i < noavgs; i++) {
			avg a = new avg("avg." + i, 0.25);// id,consump %/h
			vehicles.add(a);
			a.setActSpeed(5);
			a.avgfile = (new SimpleDateFormat("yyyy-MM-dd").format(currentdate) + a.id + ".txt");// create/update vehicle file
			file_ops.createUpdateLog(a.avgfile, "Started task.");
		}
		this.vehicles = vehicles;

		file = ("log." + new SimpleDateFormat("yyyy-MM-dd").format(currentdate) + ".txt");

		this.charge = new chargingStation(vehicles, vehiclesInNeedOfCharging);
		this.chargingStationThread = new Thread(this.charge);
		this.chargingStationThread.start();
	}

	public void takeOrder(int load, String order, int id) throws exception_handling.ZeroTonnesException,
			exception_handling.InvalidOrderException, exception_handling, exception_handling.VehicleNotFoundException {
		ArrayList<avg> v = new ArrayList<avg>();
		orderID = (new SimpleDateFormat("yyyy-MM-dd").format(currentdate) + "Task" + "." + id);
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
			while (op_vehicles > this.vehicles.size()) {
				Thread.sleep(1000);
			}
			for (int i = 0; i < op_vehicles; i++) {
				v.add(this.vehicles.remove(i)); // removes avg from vehicles ArrayList and adds it to v
			}
		} catch (Throwable e) {
			System.out.println("Error: " + e.toString());
		}
		// manage order. Orders can be: {'toFactory', 'toWarehouse'. 'toDelivery'}
		this.orderno = id;
		this.manageOrder(order, load, v);
	}

	private void manageOrder(String task, int ton, ArrayList<avg> vehiclesToBeUsed) throws exception_handling,
			exception_handling.InvalidOrderException, exception_handling.VehicleNotFoundException {
		Date starttime = new java.util.Date();
		starttime.setTime(currentdate.getTime());
		double overallduration = 0;
		String entry;

		switch (task) {
		case "toFactory":
			ordermaterial = new rawMaterial("item" + orderno, "raw", ton, warehouse);
			move = new movStorage(this.orderID, currentdate, factory, vehiclesToBeUsed, vehiclesInNeedOfCharging,
					this.vehicles, file, warehouse, "warehouse", "factory", this.ordermaterial);
			overallduration = (double) (move.timestamp.getTime() - starttime.getTime()) / 3600000; // in hours

			entry = calculate_date(overallduration, move, this.orderID);
			file_ops.createUpdateLog(file, entry);
			currentdate.setTime(move.timestamp.getTime());// update taskmanager time
			break;

		case "toWarehouse":
			ordermaterial = new rawMaterial("item " + orderno, "product", ton, factory);
			move = new movStorage(this.orderID, currentdate, warehouse, vehiclesToBeUsed, vehiclesInNeedOfCharging,
					this.vehicles, file, factory, "factory", "warehouse", this.ordermaterial);
			overallduration = (double) (move.timestamp.getTime() - starttime.getTime()) / 3600000; // in hours

			entry = calculate_date(overallduration, move, this.orderID);
			file_ops.createUpdateLog(file, entry);
			currentdate.setTime(move.timestamp.getTime());// update taskmanager time
			break;

		case "toDelivery":
			ordermaterial = new rawMaterial("item " + orderno, "product", ton, warehouse);

			move = new movDelivery(this.orderID, currentdate, file, vehiclesToBeUsed, vehiclesInNeedOfCharging,
					vehicles, warehouse, dispatch, this.ordermaterial);
			overallduration = (double) (move.timestamp.getTime() - starttime.getTime()) / 3600000; // in hours

			entry = calculate_date(overallduration, move, this.orderID);
			file_ops.createUpdateLog(file, entry);
			currentdate.setTime(move.timestamp.getTime());// update taskmanager time
			break;

		case null, default:
			etask.handleInvalidOrder();
		}

	}

	private static String calculate_date(double overallduration, movementVehicle move, String orderID) {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(move.timestamp) + ": " + orderID
				+ " mission completed. Overall duration: " + String.format("%.2f", overallduration) + " hours.";
	}

}
