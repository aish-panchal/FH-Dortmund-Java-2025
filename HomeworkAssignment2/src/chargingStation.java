import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class chargingStation {
	
    public avg[] chargingavg;
    private int stationid;
    private boolean station_status;
    private String logf;//charging station file
    private String taskfile;//system file
    private String l_event;
    private double chargtime;
    private Date accTime;
    private exception_handling echarge = new exception_handling();
	
    public chargingStation(String file,Date currentT,avg[] vehicletocharge) throws exception_handling.VehicleNotFoundException {
		taskfile=file;
		logf=(new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(currentT)+"ChargingStation"+".txt");
		file_ops.createUpdateLog(logf, "");
		accTime=currentT;
		
		chargingavg = new avg[vehicletocharge.length];
		l_event=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(accTime)+ ": Vehicle ");
		charging(vehicletocharge);	
    }
	
    public void charging(avg[] avgcharge) throws exception_handling.VehicleNotFoundException {
    	String logupdate;
		try {
    	for(int i=0;i<avgcharge.length;i++) {
    		if(avgcharge[i]== null) {
		    	echarge.handleVehicleNotFound();
		    }
	    	chargtime=avgcharge[i].chargeBatteryPercentage((1-avgcharge[i].getComsup()));
		    logupdate=(l_event+avgcharge[i].id+" is charging.");
		    file_ops.createUpdateLog(taskfile, logupdate);
		    file_ops.createUpdateLog(avgcharge[i].avgfile, logupdate);//update vehicle file
		    file_ops.createUpdateLog(logf, logupdate);// update charging station file
		    }
    	}catch(Throwable e) {System.out.println("Error: "+e.toString());}
    	
    	getStationstatus(avgcharge);
    }
    
    public void getChargingstatus() {
		
    }
    public void getStationstatus(avg[] batstat) {	
    	String lupdate;
		accTime.setTime(accTime.getTime()+TimeUnit.HOURS.toMillis((long) chargtime));
		String endevent=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(accTime)+" Vehicle: ");
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
    }
	
}
