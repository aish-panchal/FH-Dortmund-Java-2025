import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class chargingStation {

   // private static final boolean available = true;
    //private static final boolean occupied = false;
	
    public avg[] chargingavg;
    private int stationid;
    private boolean station_status;
    private String logf;//charging station file
    private String taskfile;//system file
    private String l_event;
    private double chargtime;
    private Date accTime;
	
    public chargingStation(String file,Date currentT,avg[] vehicletocharge) {
	taskfile=file;
	logf=(new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(currentT)+"ChargingStation"+".txt");
	file_ops.createUpdateLog(logf, "");
	accTime=currentT;
	//stationid=id;
	//station_status=available;	
	chargingavg = new avg[vehicletocharge.length];
	l_event=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(accTime)+ ": Vehicle ");
	charging(vehicletocharge);	
    }
	
    public void charging(avg[] avgcharge) {
    	String logupdate;
			
    	for(int i=0;i<avgcharge.length;i++) {
    	chargtime=avgcharge[i].chargeBatteryPercentage((1-avgcharge[i].getComsup()));
	    logupdate=(l_event+avgcharge[i].id+" is charging.");
	    file_ops.createUpdateLog(taskfile, logupdate);
	    file_ops.createUpdateLog(avgcharge[i].avgfile, logupdate);//update vehicle file
	    file_ops.createUpdateLog(logf, logupdate);// update charging station file
    	}
    	
    	getStationstatus(avgcharge);
    }
    
    public void getChargingstatus() {
		
    }
    public void getStationstatus(avg[] batstat) {	
   	
    String lupdate;
	accTime.setTime(accTime.getTime()+TimeUnit.HOURS.toMillis((long) chargtime));
	 String endevent=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(accTime)+" Vehicle: ");
	for(int i=0;i<batstat.length;i++) {
    	lupdate=(endevent+batstat[i].id+" is charged.");
	    file_ops.createUpdateLog(taskfile, lupdate);
	    file_ops.createUpdateLog(batstat[i].avgfile, lupdate);//update vehicle file
	    file_ops.createUpdateLog(logf, lupdate);// update charging station file
    	}
    }
	
}
