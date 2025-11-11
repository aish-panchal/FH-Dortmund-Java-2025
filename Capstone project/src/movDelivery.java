import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/* movDelivery: moving finished products from warehouse to dispatch area */
public class movDelivery extends movementVehicle {
	public movDelivery(String task, String filename, ArrayList<avg> avgsToBeUsed, ArrayList<avg> chargeQ,
			ArrayList<avg> readyVehicleQ, double storUnit[], double dispatcharea[], rawMaterial prod) {
		this.taskid = task;
		this.timestamp = new java.util.Date();
		this.sysfile = filename;
		this.location = storUnit;
		this.destination = dispatcharea;
		this.avgsToBeUsed = avgsToBeUsed;//working avg
		this.chargeQ = chargeQ; //charging queue
		this.readyVehicleQ = readyVehicleQ;//available avg list
		this.movingmaterial = prod;
		this.tonnes = new storageManagement();
		
		for (avg a : this.avgsToBeUsed) {
			a.setActSpeed(2);
		}
		int ind_place=2;
		
		loading(ind_place);
		movingtolocation(ind_place);
		unloading(ind_place);
	}

	public void loading(int end_index) {
		long loadtime = 20; // minutes
		status = in_progress;
		String start="["+location[0]+","+location[1]+"]";
		updateLog("loading", start);
		for (avg a : this.avgsToBeUsed) {
			a.changepos(location);
			a.wait_at_pos(loadtime);
		}
		// add duration of loading process
		this.timestamp.setTime(this.timestamp.getTime() + TimeUnit.MINUTES.toMillis(loadtime)); 
		status = done;
		updateLog("loading", start);// process finished and added to the log file
	}

	public void movingtolocation(int toplace) {// start movement from current location to destination
		status = in_progress;
		String src="["+location[0]+","+location[1]+"]";
		updateLog("transporting", end_destination[toplace]);

		location = destination;
		for (avg a : this.avgsToBeUsed) {
			a.changepos(destination);
		}
		//add duration of journey
		if (this.avgsToBeUsed.size() > 0) {
			this.timestamp.setTime(
					this.timestamp.getTime() + TimeUnit.MINUTES.toMillis((long) avgsToBeUsed.get(0).overallTime()));
		}
		status = done;
		updateLog("transporting", end_destination[toplace]);// process finished and added to the log file
	}
	
	public void unloading(int end) {
		long unloadtime = 20; // minutes
		status = in_progress;
		updateLog("unloading", end_destination[end]);
		for (avg a : this.avgsToBeUsed) {
			a.wait_at_pos(unloadtime);
		}
		// add duration of unloading process
		this.timestamp.setTime(this.timestamp.getTime() + TimeUnit.MINUTES.toMillis(unloadtime));

		status = done;
		updateLog("unloading", end_destination[end]);// process finished and added to the log file
		//checks battery status
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
		String upevent, produpdate;
		this.event = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss-SSS").format(this.timestamp) + ": " + taskid
				+ " Vehicle: ");
		String prodlog = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss-SSS").format(this.timestamp) + ": "
				+ this.movingmaterial.id + ": ");
		if (status) {
			for (avg a : this.avgsToBeUsed) {
				upevent = (this.event + a.id + " finished " + update + " the delivary at " + delivarea);
				file_ops.createUpdateLog(this.sysfile, upevent);
				file_ops.createUpdateLog(a.avgfile, upevent);
			}
			produpdate = (prodlog + " finished " + update + ".");
			file_ops.createUpdateLog(this.sysfile, produpdate);
			file_ops.createUpdateLog(this.tonnes.storageLog, produpdate);
		} else {

			for (avg a : this.avgsToBeUsed) {
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
