
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/* movStorage: moving raw materials from warehouse to factory as well as moving finished
 * goods from factory to storage
 * */
public class movStorage extends movementVehicle {
    public movStorage(String task, Date time, double coord[], ArrayList<avg> avgsToBeUsed, ArrayList<avg> chargeQ,
		      ArrayList<avg> readyVehicleQ, String logfile, double orig[], String src, String dst,
		      rawMaterial mat) {
	this.avgsToBeUsed = avgsToBeUsed;
	this.chargeQ = chargeQ;
	this.readyVehicleQ = readyVehicleQ;
	this.taskid = task;
	this.sysfile = logfile;

	this.movingmaterial = mat;
	this.tonnes = new storageManagement();

	this.location = orig;
	this.destination = coord;// x2, y2
	this.timestamp = time;

	try {
	    if (avgsToBeUsed.size() < 1) {
		this.emov.handleVehicleNotFound();
	    }
	} catch (Throwable e) {
	    System.out.println("Error: " + e.toString());
	}

	this.loading(src);
	this.movingtolocation(dst);
	this.unloading(dst);
    }

    public void loading(String inplace) {
	long loadtime = 10; // minutes
	status = in_progress;
	updateLog("loading", inplace);

	for (int i = 0; i < avgs.length; i++) {
	    avgs[i].changepos(location);// set avgs to loading location
	    avgs[i].wait_at_pos(loadtime);
	}

	this.timestamp.setTime(this.timestamp.getTime() + TimeUnit.MINUTES.toMillis(loadtime)); // add duration
	// of the
	// loading
	// process
	// retrieves the material from a location

	status = done;
	updateLog("loading", inplace); // process finished and added to the log file
    }

    public void unloading(String toplace) {
	long unloadtime = 10; // minutes
	status = in_progress;
	updateLog("unloading", toplace);
	for (int i = 0; i < avgs.length; i++) {
	    avgs[i].wait_at_pos(unloadtime);
	}

	this.timestamp.setTime(this.timestamp.getTime() + TimeUnit.MINUTES.toMillis(unloadtime)); // add
	// duration
	// of
	// the
	// unloading
	// process,
	// also
	// assuming
	// perfect
	// sync
	// stores material in a location

	status = done;
	updateLog("unloading", toplace);// process finished and added to the log file
	for(int i=0;i<avgs.length;i++) {
	    this.avgs[i].changepos(destination);
	    if(avgs[i].getComsup()<0.50) {
		this.chargeQ.add(avgs[i]);
	    }else{
		this.readyVehicleQ.add(avgs[i]);
	    }
	}

    }

    public double[] getlocation() {
	return location;
    }

    public void movingtolocation(String place) {
	status = in_progress;
	updateLog("moving", place);

	location = destination;

	this.timestamp.setTime(
			       this.timestamp.getTime() + TimeUnit.MINUTES.toMillis((long) avgs[0].overallTime()));// add
	// duration
	// of
	// the
	// moving
	// process
	status = done;
	updateLog("journey", place);// process finished and added to the log file
    }

    public void updateLog(String process, String location) {
	String update, itemupdate;
	this.event = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.timestamp) + ": " + taskid
		      + " Vehicle: ");
	String itemlog = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.timestamp) + ": "
			  + this.movingmaterial.id + ": ");
	if (status) {

	    for (int j = 0; j < avgs.length; j++) {
		update = (this.event + avgs[j].id + " finished " + process + ".");
		file_ops.createUpdateLog(this.sysfile, update);
		file_ops.createUpdateLog(avgs[j].avgfile, update);
	    }
	    itemupdate = (itemlog + " finished " + process + ".");
	    file_ops.createUpdateLog(this.sysfile, itemupdate);
	    file_ops.createUpdateLog(this.tonnes.storageLog, itemupdate);
	} else {

	    for (int j = 0; j < avgs.length; j++) {
		update = (this.event + avgs[j].id + " is " + process + " at " + location + ".");
		file_ops.createUpdateLog(this.sysfile, update);
		file_ops.createUpdateLog(avgs[j].avgfile, update);
	    }
	    itemupdate = (itemlog + " is " + process + " at " + location + ".");
	    file_ops.createUpdateLog(this.sysfile, itemupdate);
	    file_ops.createUpdateLog(this.tonnes.storageLog, itemupdate);
	}
    }

}
