import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class movDelivery extends movementVehicle {
	public movDelivery(String task, Date time, String filename, ArrayList<avg> avgsToBeUsed, ArrayList<avg> chargeQ,
			ArrayList<avg> readyVehicleQ, double storUnit[], double dispatcharea[], rawMaterial prod) {
		this.taskid = task;
		this.timestamp = time;
		this.sysfile = filename;
		this.location = storUnit;
		this.destination = dispatcharea;
		this.avgsToBeUsed = avgsToBeUsed;
		this.chargeQ = chargeQ;
		this.readyVehicleQ = readyVehicleQ;
		this.movingmaterial = prod;
		this.tonnes = new storageManagement();
		for (avg a : avgsToBeUsed) {
			a.setActSpeed(2);
		}
		loading("warehouse");
		movingtolocation("dispatch area");
		unloading("dispatch area");
	}

	public void loading(String start) {// starts and finishes loading
		long loadtime = 20; // minutes
		status = in_progress;
		updateLog("loading", start);
		for (avg a : avgsToBeUsed) {
			a.changepos(location);
			a.wait_at_pos(loadtime);
		}

		this.timestamp.setTime(this.timestamp.getTime() + TimeUnit.MINUTES.toMillis(loadtime)); // add duration of the
																								// unloading process,
																								// also assuming perfect
																								// sync

		status = done;
		updateLog("loading", start);// process finished and added to the log file
	}

	public void unloading(String end) {// starts and finishes unloading
		long unloadtime = 20; // minutes
		status = in_progress;
		updateLog("unloading", end);
		for (avg a : avgsToBeUsed) {
			a.wait_at_pos(unloadtime);
		}

		this.timestamp.setTime(this.timestamp.getTime() + TimeUnit.MINUTES.toMillis(unloadtime)); // add duration of the
																									// unloading
																									// process, also
																									// assuming perfect
																									// sync

		status = done;
		updateLog("unloading", "dispatch area.");// process finished and added to the log file
		for (avg a : avgsToBeUsed) {
			a.changepos(destination);
			a.setActSpeed(5);
			if (a.getComsup() < 0.50) {
				System.out.println("lowbat");
				this.chargeQ.add(a);
			} else {
				this.readyVehicleQ.add(a);
			}
		}
	}

	public double[] getlocation() {
		return location;
	}

	public void movingtolocation(String loc) {// start movement from current location to destination
		status = in_progress;
		updateLog("transporting", loc);

		location = destination;
		for (avg a : avgsToBeUsed) {
			a.changepos(destination);
		}

		if (avgsToBeUsed.size() > 0) {
			this.timestamp.setTime(
					this.timestamp.getTime() + TimeUnit.MINUTES.toMillis((long) avgsToBeUsed.get(0).overallTime())); // add
																														// duration
																														// of
																														// the
																														// unloading
																														// process,
																														// also
																														// assuming
																														// perfect
																														// sync
		}
		status = done;
		updateLog("transporting", loc);// process finished and added to the log file

	}

	public void updateLog(String update, String delivarea) { // add events current and finished to the log file
		String upevent, produpdate;
		this.event = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss-SSS").format(this.timestamp) + ": " + taskid
				+ " Vehicle: ");
		String prodlog = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss-SSS").format(this.timestamp) + ": "
				+ this.movingmaterial.id + ": ");
		if (status) {
			for (avg a : avgsToBeUsed) {
				upevent = (this.event + a.id + " finished " + update + " the delivary at the " + delivarea);
				file_ops.createUpdateLog(this.sysfile, upevent);
				file_ops.createUpdateLog(a.avgfile, upevent);
			}
			produpdate = (prodlog + " finished " + update + ".");
			file_ops.createUpdateLog(this.sysfile, produpdate);
			file_ops.createUpdateLog(this.tonnes.storageLog, produpdate);
		} else {

			for (avg a : avgsToBeUsed) {
				upevent = (this.event + a.id + " is " + update + " the delivery to the " + delivarea + ".");
				file_ops.createUpdateLog(this.sysfile, upevent);
				file_ops.createUpdateLog(a.avgfile, upevent);
			}
			produpdate = (prodlog + " is " + update + " at " + delivarea + ".");
			file_ops.createUpdateLog(this.sysfile, produpdate);
			file_ops.createUpdateLog(this.tonnes.storageLog, produpdate);
		}
	}
}
