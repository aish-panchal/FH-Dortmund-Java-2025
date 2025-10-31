import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class movDelivery extends movementVehicle {
	private int ind;
	
    public movDelivery(String task, Date time, String filename, avg transport[], double storUnit[], double dispatcharea[], rawMaterial prod) {
		this.taskid =task;
		this.timestamp=time;
		this.sysfile= filename;
		this.location=storUnit;
		this.destination=dispatcharea;
		this.hasLowBat=false;
		
		this.movingmaterial=prod;
		this.tonnes = new storageManagement();
		
		this.avgs = new avg[transport.length];
		this.lowbat = new avg[avgs.length];
		ind=0;
		try {
		for(int i=0; i<transport.length;i++) {
			if(this.avgs[i]==null) {
				this.emov.handleVehicleNotFound();
			}
		    this.avgs[i]=transport[i];
		    this.avgs[i].setActSpeed(2); //slower because of the delicate materials
		}
		}catch(Throwable e) {System.out.println("Error: "+e.toString());}
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
			  
    }
	
    public double[] getlocation() { 
	return location;
    }
	
    public void movingtolocation(String loc) {//start movement from current location to destination
		status=in_progress;
		updateLog("transporting", loc);
		
		for(int i=0;i<avgs.length;i++) {
		    this.avgs[i].changepos(destination);
		    if(avgs[i].getComsup()<0.50) {
		    	this.lowbat[ind++]=avgs[i];
		    }
		}
		if(ind>0) {
			this.hasLowBat=true;
		}
		location = destination;
		
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
