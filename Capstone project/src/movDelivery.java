import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/* movDelivery: moving finished products from warehouse to dispatch area */
public class movDelivery extends movementVehicle {
	public int index_loc;
	private Date startdev;

	public movDelivery(String task, String filename, int avg_amount, Semaphore vMutex,
			ConcurrentLinkedQueue<avg> vehiclesInNeedOfCharging, ConcurrentLinkedQueue<avg> vehicles, double storUnit[],
			double dispatcharea[], int tons, storageManagement store, Semaphore store_lock)
			throws InterruptedException {

		this.movementMutex = vMutex;
		this.taskid = task;
		this.sysfile = filename;

		this.avgsToBeUsed = new ArrayList<avg>();// working avg
		this.chargeQ = vehiclesInNeedOfCharging; // charging queue
		this.readyVehicleQ = vehicles;// available avg list

		this.tons = tons;
		this.store = store;
		this.store_lock = store_lock;

		this.index_loc = 2;
		this.location = storUnit;
		this.destination = dispatcharea;
		this.startdev = new java.util.Date();
		this.timestamp = new java.util.Date();
		this.overallduration = 0;

		this.movementMutex.acquire();
		for (avg a : this.avgsToBeUsed) {
			a.setActSpeed(2);
		}
		while (avg_amount > this.avgsToBeUsed.size()) {
			if (this.readyVehicleQ.size() > 0) {
				this.avgsToBeUsed.add(this.readyVehicleQ.poll());
			} else {
				Thread.sleep(1000);
				this.emov.WaitingForVehicles(); // Message for the GUI
			}
		}
		this.movementMutex.release();

	}

	public void loading(int end_index) throws storageManagement.materialNotFoundException {
		this.startdev.setTime(this.timestamp.getTime());
		long loadtime = 20; // minutes
		status = in_progress;
		String start = "[" + location[0] + "," + location[1] + "]";
		updateLog("loading", start);
		store.retrieve_processed_material(tons);

		for (avg a : this.avgsToBeUsed) {
			a.changepos(location);
			a.wait_at_pos(loadtime);
		}
		// add duration of loading process
		this.timestamp.setTime(this.timestamp.getTime() + TimeUnit.MINUTES.toMillis(loadtime));
		status = done;
		updateLog("loading", start); // process finished
	}

	public void movingtolocation(int toplace) {
		status = in_progress;
		String src = "[" + location[0] + "," + location[1] + "]";
		updateLog("transporting", end_destination[toplace]);
		location = destination;
		for (avg a : this.avgsToBeUsed) {
			a.changepos(destination);
		}
		// add duration of journey
		this.timestamp.setTime(
				this.timestamp.getTime() + TimeUnit.MINUTES.toMillis((long) avgsToBeUsed.get(0).overallTime()));
		status = done;
		updateLog("transporting", end_destination[toplace]);// process finished and added to the log file
	}

	public void unloading(int end) throws InterruptedException {
		long unloadtime = 20; // minutes
		status = in_progress;
		updateLog("unloading", end_destination[end]);

		for (avg a : this.avgsToBeUsed) {
			a.wait_at_pos(unloadtime);
		}
		// add duration of unloading process
		this.timestamp.setTime(this.timestamp.getTime() + TimeUnit.MINUTES.toMillis(unloadtime));
		this.overallduration = (double) (this.timestamp.getTime() - this.startdev.getTime()) / 3600000;
		Thread.sleep(100);
		status = done;
		updateLog("unloading", end_destination[end]);// process finished and added to the log file
		// checks battery status
		for (avg a : this.avgsToBeUsed) {
			a.changepos(destination);
			a.setActSpeed(5);
			if (a.getConsump() > 0.50) {
				this.chargeQ.add(a);
			} else {
				this.readyVehicleQ.add(a);
			}
		}
	}

	public double[] getlocation() {
		return location;
	}

	public void updateLog(String update, String delivarea) {
		String upevent, produpdate, sysupdate;
		this.event = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss-SSS").format(this.timestamp) + ": " + taskid
				+ " Vehicle: ");
		String prodlog = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss-SSS").format(this.timestamp) + ": " + this.tons
				+ ": ");
		if (status) {
			for (avg a : this.avgsToBeUsed) {
				upevent = (this.event + a.id + " finished " + update + " the delivary at " + delivarea);
				file_ops.createUpdateLog(this.sysfile, upevent);
				file_ops.createUpdateLog(a.avgfile, upevent);
			}
			produpdate = (prodlog + " finished " + update + ".");
			file_ops.createUpdateLog(this.sysfile, produpdate);
		} else {
			for (avg a : this.avgsToBeUsed) {
				upevent = (this.event + a.id + " is " + update + " the delivery to the " + delivarea + ".");
				file_ops.createUpdateLog(this.sysfile, upevent);
				file_ops.createUpdateLog(a.avgfile, upevent);
			}
			produpdate = (prodlog + " is " + update + " at " + delivarea + ".");
			file_ops.createUpdateLog(this.sysfile, produpdate);
		}
		if (this.overallduration > 0) {
			sysupdate = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.timestamp) + ": " + this.taskid
					+ " mission completed. Overall duration: " + String.format("%.2f", this.overallduration)
					+ " hours.");
			file_ops.createUpdateLog(this.sysfile, sysupdate);
		}
	}

	@Override
	public void run() {
		try {
			this.loading(this.index_loc);
		} catch (storageManagement.materialNotFoundException e) {
			for (avg a : this.avgsToBeUsed) {
				a.changepos(destination);
				a.setActSpeed(5);
				if (a.getConsump() > 0.50) {
					this.chargeQ.add(a);
				} else {
					this.readyVehicleQ.add(a);
				}
			}
		}
		this.movingtolocation(this.index_loc);
		try {
			this.unloading(this.index_loc);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
