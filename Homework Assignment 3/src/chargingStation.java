import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class chargingStation {
	
	private static final int STATIONS = 10;
	
    public avg[] chargingavg;//avg to charge
    private String stationid; //station id
    private boolean stationOccupied; //station status
    public String logf;//charging station file
    private String taskfile;//system file
    private String l_event; //event message template
    private double chargtime; //charging time depends on percentage consumed
    private Date accTime;//current date
    private exception_handling echarge = new exception_handling();
	
    public chargingStation() {
    	//initialize log file for all 10 charging stations 
    	accTime= new java.util.Date();
    	logf=(new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(accTime)+"ChargingStation"+".txt");
		file_ops.createUpdateLog(logf, "");
    }
    
    public chargingStation(int id, String file,Date currentT,avg[] vehicletocharge) throws exception_handling.VehicleNotFoundException {
		stationid= ("Charging Station "+id);
    	taskfile=file;
		accTime=currentT;	
		stationOccupied=true;
		l_event=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(accTime)+" "+stationid+": ");
		
		chargingavg = new avg[vehicletocharge.length];
		for(int i=0;i<vehicletocharge.length;i++) {
			chargingavg[i]=vehicletocharge[i];
		}
		
		charging(chargingavg);	
    }
	
    public void charging(avg[] avgcharge) throws exception_handling.VehicleNotFoundException {
    	String startevent, endevent;
    	double chargepercent;
    	long waittime;
		try {
    	for(int i=0;i<avgcharge.length;i++) {
    		if(avgcharge[i]== null) {
		    	echarge.handleVehicleNotFound();
		    }
    		stationOccupied=true;
    		chargepercent = avgcharge[i].getComsup();
	    	chargtime=avgcharge[i].chargeBatteryPercentage(chargepercent);
	    	waittime = TimeUnit.HOURS.toMillis((long) chargtime);
		    startevent=(l_event+avgcharge[i].id+" is charging.");
		    endevent=(l_event+avgcharge[i].id+" is charged.");
		    updateLogFile(startevent);
		    
		    /*file_ops.createUpdateLog(taskfile, logupdate);
		    file_ops.createUpdateLog(avgcharge[i].avgfile, logupdate);//update vehicle file
		    file_ops.createUpdateLog(logf, logupdate);// update charging station file
		    */
		    Thread.sleep(waittime); //charging time
		    updateLogFile(endevent);
		    }
    	
    	}catch(Throwable e) {System.out.println("Error: "+e.toString());}
    	
    	//getStationstatus(avgcharge);
    }
    
    public String getChargingstatus() {
    	String status;
    	if(stationOccupied) {
    		status = "Vehicle currently charging";
    	}else {
    		status="Station is available";
    	}
    	return status;
    }
    
    public boolean getStationStatus() {
    	return stationOccupied;
    }
    
    /*public void getStationstatus(avg[] batstat) {	
    	String lupdate;
		accTime.setTime(accTime.getTime()+TimeUnit.HOURS.toMillis((long) chargtime));
		String endevent=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(accTime)+": ");
		stationOccupied=false;
		try {	
		for(int i=0;i<batstat.length;i++) {
			if(batstat[i]== null) {
		    	echarge.handleVehicleNotFound();
		    }
			
	    	lupdate=(endevent+batstat[i].id+" is charged.");
		    file_ops.createUpdateLog(taskfile, lupdate);
		    file_ops.createUpdateLog(batstat[i].avgfile, lupdate);//update vehicle file
		    file_ops.createUpdateLog(logf, lupdate);// update charging station file
		    }
		}catch(Throwable e) {System.out.println("Error: "+e.toString());}
    }*/
    
    public void updateLogFile(String event) {
    	file_ops.createUpdateLog(taskfile, event);//update overall taskmanager file
	    file_ops.createUpdateLog(batstat[i].avgfile, event);//update vehicle file
	    file_ops.createUpdateLog(logf, event);// update charging station file
    }
	
}
