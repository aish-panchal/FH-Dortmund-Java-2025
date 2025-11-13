import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/* movStorage: moving raw materials from warehouse to factory as well as moving finished
 * goods from factory to storage
 * */
public class movStorage extends movementVehicle {
	public int index_loc;
	private Date starttime;

	public movStorage(String task, double coord[], int avgAmount, Semaphore vehicleMutex,
			ConcurrentLinkedQueue<avg> vehiclesInNeedOfCharging, ConcurrentLinkedQueue<avg> vehicles,
			String logfile,
			double orig[], int coord_index, int tons, storageManagement store, Semaphore store_lock)
			throws InterruptedException {

		this.movementMutex = vehicleMutex;
		this.taskid = task;
		this.sysfile = logfile;
		
		this.avgsToBeUsed = new ArrayList<avg>(); // working avgs
		this.chargeQ = vehiclesInNeedOfCharging; // Charging queue
		this.readyVehicleQ = vehicles; // available avg list

		this.tons = tons;
		this.store = store;
		this.store_lock = store_lock;

		this.index_loc = coord_index;
		this.location = orig;
		this.destination = coord;// x2, y2
		this.starttime = new java.util.Date();
		this.timestamp = new java.util.Date();
		this.overallduration = 0;

		this.movementMutex.acquire();
		while (avgAmount > avgsToBeUsed.size()) {
			if (this.readyVehicleQ.size() > 0) {
				avgsToBeUsed.add(this.readyVehicleQ.poll());
			} else {
				Thread.sleep(1000);
				this.emov.WaitingForVehicles();// Message for the GUI
			}
		}
		this.movementMutex.release();

	}

	public void loading(int destination_index) throws storageManagement.materialNotFoundException {
		this.starttime.setTime(this.timestamp.getTime());
		long loadtime = 10; // minutes
		status = in_progress;
		String inplace = "[" + location[0] + "," + location[1] + "]";
		updateLog("loading", inplace);// start process
		if (end_destination[destination_index] == "Factory") {
			System.out.println(tons
					+ " tons will be removed from storage (at movStorage load)");
			store.retrieve_raw_material(tons);

		}

		for (avg a : this.avgsToBeUsed) {
			a.changepos(location);
			a.wait_at_pos(loadtime);
		}
		// add duration of loading process
		this.timestamp.setTime(this.timestamp.getTime() + TimeUnit.MINUTES.toMillis(loadtime));
		status = done;
		updateLog("loading", inplace); // process finished
	}

	public void movingtolocation(int destination_index) {
		status = in_progress;
		String place = end_destination[destination_index];
		updateLog("moving", place);
		location = destination;
		for (avg a : this.avgsToBeUsed) {
			a.changepos(destination);
		}
		// add duration of the moving process
		this.timestamp.setTime(
				this.timestamp.getTime()
						+ TimeUnit.MINUTES.toMillis((long) avgsToBeUsed.get(0).overallTime()));
		status = done;
		updateLog("journey", place);// process finished
	}

	public void unloading(int destination_index)
			throws InterruptedException, storageManagement.noFreeStorageSpaceException {
		long unloadtime = 10; // minutes
		status = in_progress;
		String toplace = end_destination[destination_index];
		updateLog("unloading", toplace);
		if (toplace == "Factory") {
			System.out.println("at movStorage to factory, added " + tons
					+ " tons of processed material");
			try {
				store.store_processed_material(tons);
			} catch (storageManagement.noFreeStorageSpaceException e) {
				System.out.println(
						"Failed to return processed items to warehouse from factory, not enough space");
				throw e;
			}
		} else if (toplace == "Warehouse") {
			System.out.println("at movStorage to warehouse, added " + tons
					+ " tons of raw material");
			try {
				store.store_raw_material(tons);
			} catch (storageManagement.noFreeStorageSpaceException e) {
				System.out.println("Failed to store raw material in warehouse, not enough space");
				throw e;
			}
		}

		for (avg a : this.avgsToBeUsed) {
			a.wait_at_pos(unloadtime);
		}
		// add duration of unloading process
		// this.movementMutex.acquire();
		this.timestamp.setTime(this.timestamp.getTime() + TimeUnit.MINUTES.toMillis(unloadtime));
		this.overallduration = (double) (this.timestamp.getTime() - this.starttime.getTime()) / 3600000;
		// this.movementMutex.release();
		Thread.sleep(100);
		status = done;
		updateLog("unloading", toplace);// process finished and added to the log file
		// Checks avg battery status

		for (avg a : this.avgsToBeUsed) {
			a.changepos(destination);
			a.setActSpeed(5);
			// this.movementMutex.acquire();
			if (a.getConsump() > 0.50) {
				this.chargeQ.add(a);
			} else {
				this.readyVehicleQ.add(a);
			}

			// this.movementMutex.release();
		}
	}

	public double[] getlocation() {
		return location;
	}

	public void updateLog(String process, String location) {
		String update, itemupdate, taskupdate;
		this.event = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss-SSS").format(this.timestamp) + ": "
				+ this.taskid
				+ " Vehicle: ");
		String itemlog = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss-SSS").format(this.timestamp) + ": "
				+ this.tons + ": ");
		if (status) {
			for (avg a : avgsToBeUsed) {
				update = (this.event + a.id + " finished " + process + ".");
				file_ops.createUpdateLog(this.sysfile, update);
				file_ops.createUpdateLog(a.avgfile, update);
			}
			itemupdate = (itemlog + " finished " + process + ".");
			file_ops.createUpdateLog(this.sysfile, itemupdate);
		} else {
			for (avg a : avgsToBeUsed) {
				update = (this.event + a.id + " is " + process + " at " + location + ".");
				file_ops.createUpdateLog(this.sysfile, update);
				file_ops.createUpdateLog(a.avgfile, update);
			}
			itemupdate = (itemlog + " is " + process + " at " + location + ".");
			file_ops.createUpdateLog(this.sysfile, itemupdate);
		}
		if (this.overallduration > 0) {
			taskupdate = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.timestamp) + ": "
					+ this.taskid
					+ " mission completed. Overall duration: "
					+ String.format("%.2f", this.overallduration)
					+ " hours.");
			file_ops.createUpdateLog(this.sysfile, taskupdate);
		}
	}

	@Override
	public void run() {
		try {
			this.loading(this.index_loc);
		} catch (storageManagement.materialNotFoundException e) {
			System.out.println("Not enough raw material to take to factory");
			for (avg a : avgsToBeUsed) {
				this.readyVehicleQ.add(a);
			}
			System.out.println("Failed task due to lack of material");
			return;
		}
		
		this.movingtolocation(this.index_loc);
		
		try {
			this.unloading(this.index_loc);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (storageManagement.noFreeStorageSpaceException e) {
			for (avg a : this.avgsToBeUsed) {
				a.changepos(destination);
				a.setActSpeed(5);
				if (a.getConsump() > 0.50) {
					this.chargeQ.add(a);
				} else {
					this.readyVehicleQ.add(a);
				}
			}
			return;
		}
	}

}
