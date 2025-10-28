import java.text.SimpleDateFormat;
import java.util.Date;

public class chargingStation {

    private static final boolean available = true;
    private static final boolean occupied = false;
	
    public avg[] chargingavg;
    private static final int stationsNo = 10;
    private int stationid;
    private boolean station_status;
    private String logf;
    private String l_event;
    private Date accTime;
	
    public chargingStation(int id, String file,Date currentT,avg[] vehicletocharge) {
	logf=file;
	accTime=currentT;
	stationid=id;
	station_status=available;
		
	chargingavg = new avg[vehicletocharge.length];
	for(int i=0;i<vehicletocharge.length;i++) {
	    chargingavg[i]=vehicletocharge[i];
	}
		
    }
	
    public void charging(avg[] avgcharge) {
		
	if(avgcharge.length>stationsNo) {
	    int lastcharge=10;
	}
		
	for(int i=0;i<stationsNo-1;i++) { //limited number of stations
	    avgcharge[i].chargeBatteryPercentage((1-avgcharge[i].getComsup()));
	    l_event=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(accTime)+ ": Vehicle "+avgcharge[i].id+" is charging.");
	    file_ops.createUpdateLog(logf, l_event);
	}
		
    }
    public void getChargingstatus() {
		
    }
    public void getStationstatus() {	
		
    }
	
}
