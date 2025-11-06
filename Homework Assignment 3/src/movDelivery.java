import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class movDelivery extends movementVehicle {
    public movDelivery(String task, Date time, String filename, ArrayList<avg> avgsToBeUsed, ArrayList<avg> chargeQ,
		       ArrayList<avg> readyVehicleQ, double storUnit[], double dispatcharea[], rawMaterial prod) {
	this.taskid =task;
	this.timestamp=time;
	this.sysfile= filename;
	this.location=storUnit;
	this.destination=dispatcharea;
		
	this.movingmaterial=prod;
	this.tonnes = new storageManagement();
	for(avg a: avgsToBeUsed){
	    a.setActSpeed(2);
	}	
	loading("warehouse");
	movingtolocation("dispatch area");
	unloading("dispatch area");
    }
	
    public void loading(String start){//starts and finishes loading
	long loadtime=20; //minutes
	status=in_progress;
	updateLog("loading", start);
	for(int i=0;i<avgs.length;i++) {
	    avgs[i].changepos(location);//set avgs to loading location
	    this.avgs[i].wait_at_pos(loadtime);  
	}
		
	this.timestamp.setTime( this.timestamp.getTime()+TimeUnit.MINUTES.toMillis(loadtime) ); //add duration of the unloading process, also assuming perfect sync
		
	status=done;
	updateLog("loading",start);//process finished and added to the log file
    }
	
    public void unloading(String end) {//starts and finishes unloading
	long unloadtime=20; //minutes
	status=in_progress;
	updateLog("unloading", end);
	for(int i=0;i<avgs.length;i++) {
	    this.avgs[i].wait_at_pos(unloadtime);  
	}
	
	this.timestamp.setTime( this.timestamp.getTime()+TimeUnit.MINUTES.toMillis(unloadtime) ); //add duration of the unloading process, also assuming perfect sync
		
	status=done;
	updateLog("unloading","dispatch area.");//process finished and added to the log file
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
	
    public void movingtolocation(String loc) {//start movement from current location to destination
	status=in_progress;
	updateLog("transporting", loc);

	location = destination;
	for (int i = 0; i < avgs.length; i++) {
	    avgs[i].changepos(destination);
	}

	this.timestamp.setTime( this.timestamp.getTime()+TimeUnit.MINUTES.toMillis((long) this.avgs[0].overallTime()) ); //add duration of the unloading process, also assuming perfect sync
	status=done;
	updateLog("transporting",loc);//process finished and added to the log file
	
    } 
	
    public void updateLog(String update, String delivarea) { //add events current and finished to the log file
     	String upevent, produpdate;
    	this.event= (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.timestamp) + ": "+taskid+" Vehicle: ");
    	String prodlog=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.timestamp) + ": "+this.movingmaterial.id+": ");
	if(status) {
	    for(int j=0; j<avgs.length;j++) {
		upevent= (this.event+avgs[j].id+ " finished "+ update+" the delivary at the "+delivarea);
		file_ops.createUpdateLog(this.sysfile, upevent);
		file_ops.createUpdateLog(avgs[j].avgfile, upevent);
	    }
	    produpdate = (prodlog+" finished "+update+".");
	    file_ops.createUpdateLog(this.sysfile, produpdate);
	    file_ops.createUpdateLog(this.tonnes.storageLog, produpdate);
	}else {
	    for(int j=0; j<avgs.length;j++) {
		upevent= (this.event+avgs[j].id+ " is "+update+" the delivery to the "+delivarea+".");
		file_ops.createUpdateLog(this.sysfile, upevent);
		file_ops.createUpdateLog(avgs[j].avgfile, upevent);
	    }
	    produpdate = (prodlog+" is "+update+ " at "+delivarea+".");
	    file_ops.createUpdateLog(this.sysfile, produpdate);
	    file_ops.createUpdateLog(this.tonnes.storageLog, produpdate);
	}
    }
}
