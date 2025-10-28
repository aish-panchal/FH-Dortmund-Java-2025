
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
/* movStorage: moving raw materials from warehouse to factory as well as moving finished
 * goods from factory to storage
 * */
public class movStorage extends movementVehicle {
	
    long start;
    double duration=0;
	
    public movStorage(String task, Date time, double coord[], avg avgs[], String logfile, double orig[],String xyo, String xy1) {
	taskid=task;
	this.file_name=logfile;
	this.location=orig;
	this.destination= coord;// x2, y2
	this.timestamp=time;
	this.avgs = new avg[avgs.length];
	for(int i=0;i<avgs.length;i++) {
	    this.avgs[i]=avgs[i];
	}
		
	this.loading(xyo);
	this.movingtolocation(xy1);
	this.unloading(xy1);
    }
	
    public void loading(String inplace) {
	long loadtime=10; //minutes
	status=in_progress;
	start=this.timestamp.getTime();
	updateLog("loading", inplace);
	for(int i=0;i<avgs.length;i++) {
	    avgs[i].changepos(location);//set avgs to loading location
	    avgs[i].wait_at_pos(loadtime);
	}
	this.timestamp.setTime( this.timestamp.getTime()+TimeUnit.MINUTES.toMillis(loadtime) ); //add duration of the loading process
	status=done;
	updateLog("loading",inplace); //process finished and added to the log file
    }
	
    public void unloading(String toplace) {
	long unloadtime=10; //minutes
	status=in_progress;
	updateLog("unloading", toplace);
	for(int i=0;i<avgs.length;i++) {
	    avgs[i].wait_at_pos(unloadtime);  
	}
	this.timestamp.setTime( this.timestamp.getTime()+TimeUnit.MINUTES.toMillis(unloadtime) ); //add duration of the unloading process, also assuming perfect sync
	status=done;
	updateLog("unloading",toplace);//process finished and added to the log file
		  
    }
	
    public double[] getlocation() {
	double currentloc[] = {1,2};
	return currentloc;
    }
	
    public void movingtolocation(String place) {
	status=in_progress;
	updateLog("moving",place);
	for(int i=0;i<avgs.length;i++) {
	    avgs[i].changepos(destination);
	}
		  
	location=destination;

	this.timestamp.setTime( this.timestamp.getTime()+TimeUnit.MINUTES.toMillis( (long) avgs[0].overallTime()) );//add duration of the moving process
	status=done;
	updateLog("journey", place);//process finished and added to the log file 
    }
	
    public void updateLog(String process, String location) {
	if(status) {
	    for(int j=0; j<avgs.length;j++) {
		this.event= (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.timestamp) + ": "+taskid+" Vehicle: "+avgs[j].id+ " finished "+process+ ".");
		file_ops.createUpdateLog(this.file_name, this.event);
				
		//System.out.println(this.timestamp + ": "+taskid+" Vehicle: "+avgs[j].id+ " finished "+process+ ".");
		continue;
	    }
	}else {
	    for(int j=0; j<avgs.length;j++) {
		this.event= (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.timestamp) + ": "+taskid+" Vehicle: "+avgs[j].id+ " is "+process+ " at "+location+".");
		file_ops.createUpdateLog(this.file_name, this.event);
				
		//	System.out.println(this.timestamp + ": "+taskid+" Vehicle: "+avgs[j].id+ " is "+process+ " at "+location+".");
		continue;
	    }
	}		
    }

}
