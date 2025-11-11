import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/* movStorage: moving raw materials from warehouse to factory as well as moving finished
 * goods from factory to storage
 * */

//TODO ----- delete timestamp alternations and insert threadsleep, also readyvehicle doesn't return vehicles to the main avg list
public class movStorage extends movementVehicle {
	public movStorage(String task, double coord[], ArrayList<avg> avgsToBeUsed, ArrayList<avg> chargeQ,
			ArrayList<avg> readyVehicleQ, String logfile, double orig[], int coord_index, rawMaterial mat) {
		this.avgsToBeUsed = avgsToBeUsed; // working avgs
		this.chargeQ = chargeQ; // Charging queue
		this.readyVehicleQ = readyVehicleQ; // available avg list
		this.taskid = task;
		this.sysfile = logfile;

		this.movingmaterial = mat;
		this.tonnes = new storageManagement();

		this.location = orig;
		this.destination = coord;// x2, y2
		this.timestamp = new java.util.Date();

		try {
			if (avgsToBeUsed.size() < 1) {
				this.emov.handleVehicleNotFound();
			}
		} catch (Throwable e) {
			System.out.println("Error: " + e.toString());
		}

		this.loading(coord_index);
		this.movingtolocation(coord_index);
		this.unloading(coord_index);
	}

	public void loading(int destination_index) {
		long loadtime = 10; // minutes
		status = in_progress;
		String inplace="["+location[0]+","+location[1]+"]";
		updateLog("loading", inplace);// start process

		for (avg a : this.avgsToBeUsed) {
			a.changepos(location);
			a.wait_at_pos(loadtime);
		}

		this.timestamp.setTime(this.timestamp.getTime() + TimeUnit.MINUTES.toMillis(loadtime));
		status = done;
		updateLog("loading", inplace); // process finished
	}

	public void movingtolocation(int destination_index) {
		status = in_progress;
		String place=end_destination[destination_index];
		updateLog("moving", place);
		location = destination;
		for (avg a : this.avgsToBeUsed) {
			a.changepos(destination);
		}
		// add duration of the moving process
		this.timestamp.setTime(
				this.timestamp.getTime() + TimeUnit.MINUTES.toMillis((long) avgsToBeUsed.get(0).overallTime()));
		status = done;
		updateLog("journey", place);// process finished
	}

	public void unloading(int destination_index) {
		long unloadtime = 10; // minutes
		status = in_progress;
		String toplace=end_destination[destination_index];
		updateLog("unloading", toplace);
		
		for (avg a : this.avgsToBeUsed) {
			a.wait_at_pos(unloadtime);
		}

		this.timestamp.setTime(this.timestamp.getTime() + TimeUnit.MINUTES.toMillis(unloadtime));

		status = done;
		updateLog("unloading", toplace);// process finished and added to the log file
		// Checks avg battery status
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

	public void updateLog(String process, String location) {
		String update, itemupdate;
		this.event = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss-SSS").format(this.timestamp) + ": " + this.taskid
				+ " Vehicle: ");
		String itemlog = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss-SSS").format(this.timestamp) + ": "
				+ this.movingmaterial.id + ": ");
		if (status) {
			for (avg a : avgsToBeUsed) {
				update = (this.event + a.id + " finished " + process + ".");
				file_ops.createUpdateLog(this.sysfile, update);
				file_ops.createUpdateLog(a.avgfile, update);
			}
			itemupdate = (itemlog + " finished " + process + ".");
			file_ops.createUpdateLog(this.sysfile, itemupdate);
			file_ops.createUpdateLog(this.tonnes.storageLog, itemupdate);
		} else {
			for (avg a : avgsToBeUsed) {
				update = (this.event + a.id + " is " + process + " at " + location + ".");
				file_ops.createUpdateLog(this.sysfile, update);
				file_ops.createUpdateLog(a.avgfile, update);
			}
			itemupdate = (itemlog + " is " + process + " at " + location + ".");
			file_ops.createUpdateLog(this.sysfile, itemupdate);
			file_ops.createUpdateLog(this.tonnes.storageLog, itemupdate);
		}
	}



}
