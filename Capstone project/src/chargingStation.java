import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class chargingStation implements Runnable {

	private final int STATIONS = 10;

	private class pair {
		public avg avg;
		public double time;

		public pair(avg avg, double time) {
			this.avg = avg;
			this.time = time;
		}
	}

	private int availableStations;
	public ArrayList<avg> chargingQ;// avg to charge
	public ArrayList<avg> avg;
	public ArrayList<pair> charging;
	
	//public ArrayList<avg> charging;
	private boolean stationOccupied; // station status
	public String logf;// charging station file
	private String taskfile;// system file
	private String l_event; // event message template
	private double chargtime; // charging time depends on percentage consumed
	private Date accTime;// current date
	private exception_handling echarge = new exception_handling();

	public chargingStation(String taskfile,ArrayList<avg> avgs, ArrayList<avg> toCharge) {
		// initialize log file for all n charging stations
		accTime = new java.util.Date();
		this.taskfile= taskfile;
		logf = (new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(accTime) + "ChargingStation" + ".txt");
		file_ops.createUpdateLog(logf, "");
		this.availableStations = STATIONS;
		this.avg = avgs;
		this.chargingQ = toCharge;
		charging = new ArrayList<pair>();
		//charging = new ArrayList<avg>();
		l_event=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(accTime)+ ": Vehicle ");
	}
	
	//TODO ----- if all avgs are all unavailable then handle exception, wait blah blah

	@Override
	public void run() {
		//System.out.println("In thread");
		String startevent, endevent;
		accTime = new java.util.Date();
		while (true) {

			if (chargingQ.size() > 0) {	
				for (int i = 0; (i < chargingQ.size()) && (availableStations > 0); i++) {
					avg avgToBeCharged = chargingQ.get(0);
					double chargepercent =avgToBeCharged.getConsump();
					double chargeTime = avgToBeCharged.chargeBatteryPercentage(chargepercent);
					
					pair a = new pair(avgToBeCharged, chargeTime);
					charging.add(a);
					availableStations-= 1;
					startevent = (l_event + charging.get(charging.size()-1).avg.id+" is charging.");//avg.id+" is charging.");
					updateLogFile(charging.get(i).avg.avgfile,startevent);
					chargingQ.remove(0);
					getStationStatus();
				}
			}
		
			for(int i=0; i<charging.size();i++) {	
				charging.get(i).time -= 0.1;
				System.out.println("time: "+charging.get(i).time);
				if (charging.get(i).time <= 0) {
					System.out.println("2nd for loop!");
					charging.get(i).avg.getInfo();
					avg.add(charging.get(i).avg);
		
					endevent = (l_event + charging.get(i).avg.id+" is charged.");
					updateLogFile(charging.get(i).avg.avgfile,endevent);
					charging.remove(i);
					availableStations += 1; //update log finish charging
					getChargingstatus();
				}
				getStationStatus();
			}
			try {
				Thread.sleep(1000);
				l_event=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis())+ ": Vehicle ");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public String getChargingstatus() {
		String status;
		if (stationOccupied) {
			status = "Vehicle currently charging";
		} else {
			status = "Station is available";
		}
		return status;
	}

	public String getStationStatus() {
		return ("Currently charging: "+charging.size()+ " vehicles.\n\n"+"Stations available: "+(this.availableStations));
	}

	public void updateLogFile(String file, String event) {
		file_ops.createUpdateLog(taskfile, event);// update overall taskmanager file
		file_ops.createUpdateLog(file, event);// update vehicle file
		file_ops.createUpdateLog(logf, event);// update charging station file
	}
	
	public String getInfo() {
    	return "Station Status: " + getStationStatus();
    }
}
