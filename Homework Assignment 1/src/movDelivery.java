

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class movDelivery extends movementVehicle {
	
	public movDelivery(String task, Date time, String filename, avg transport[], double storUnit[], double dispatcharea[]) {
		
		this.taskid =task;
		this.timestamp=time;
		this.file_name= filename;
		this.location=storUnit;
		this.destination=dispatcharea;
		
		this.avgs = new avg[transport.length];
		for(int i=0; i<transport.length;i++) {
			this.avgs[i]=transport[i];
			this.avgs[i].setActSpeed(2); //slower because of the delicate materials
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
		  
	}
	
	public double[] getlocation() { //get's current location of the avgs
		return location;
	}
	
	public void movingtolocation(String loc) {
		status=in_progress;
		updateLog("transporting", loc);
		for(int i=0;i<avgs.length;i++) {
			this.avgs[i].changepos(destination); 
			}
		location = destination;
		this.timestamp.setTime( this.timestamp.getTime()+TimeUnit.MINUTES.toMillis((long) this.avgs[0].overallTime()) ); //add duration of the unloading process, also assuming perfect sync
		status=done;
		updateLog("transporting",loc);//process finished and added to the log file
	} //start movement from current location to destination
	
	
	public void updateLog(String update, String delivarea) { //add events current and finished to the log file
		if(status) {
			for(int j=0; j<avgs.length;j++) {
				this.event= (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.timestamp) + ": "+taskid+" Vehicle: "+avgs[j].id+ " finished "+ update+" the delivary at the "+delivarea);
				file_ops.createUpdateLog(this.file_name, this.event);
				
				//System.out.println(this.timestamp + ": "+taskid+" Vehicle: "+avgs[j].id+ " finished delivery.");
				continue;
			}
		}else {
			for(int j=0; j<avgs.length;j++) {
				this.event= (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.timestamp) + ": "+taskid+" Vehicle: "+avgs[j].id+ " is "+update+" the delivery to the "+delivarea+".");
				file_ops.createUpdateLog(this.file_name, this.event);
				
			//	System.out.println(this.timestamp + ": "+taskid+" Vehicle: "+avgs[j].id+ " is "+process+ " at "+location+".");
				continue;
			}
		}
	}
}