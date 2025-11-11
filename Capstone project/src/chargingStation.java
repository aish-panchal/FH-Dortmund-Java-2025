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

	@Override
	public void run() {
		//System.out.println("In thread");
		String startevent, endevent;
		accTime = new java.util.Date();
		while (true) {

			if (chargingQ.size() > 0) {
				avg avgToBeCharged=new avg("",0.15);
				
				for (int i = 0; (i < chargingQ.size()) && (availableStations > 0); i++) {
					//avgToBeCharged = chargingQ.get(0);//chargingQ.size() - 1);
					double chargepercent =chargingQ.get(i).getConsump(); //avgToBeCharged.getConsump();
					double chargeTime = chargingQ.get(i).chargeBatteryPercentage(chargepercent);//avgToBeCharged.chargeBatteryPercentage(chargepercent);
					
					System.out.println("charging time: "+chargeTime+"hours");
					//TODO ---------- why are we storing charging time if we're not using it?
					
					pair a = new pair(chargingQ.get(i),chargeTime);//avgToBeCharged, chargeTime);
					charging.add(a);
					availableStations-= 1;
					//Update log to vehicles started charging
					startevent = (l_event + charging.get(i).avg.id+" is charging.");//avg.id+" is charging.");
					updateLogFile(charging.get(i).avg.avgfile,startevent);
				}
			//}
			int j=0;
			for(int i=0; i<charging.size();i++) {
				charging.get(i).time -= 0.01;
				if (charging.get(j).time < 0) {
					charging.get(j).avg.getInfo();
					avg.add(charging.get(i).avg);
					l_event=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis())+ ": Vehicle ");
					endevent = (l_event + charging.get(j).avg.id+" is charged.");
					updateLogFile(charging.get(j).avg.avgfile,endevent);
					charging.remove(j);
					availableStations += 1; //update log finish charging
					chargingQ.remove(j);//chargingQ.size() - 1);
					getChargingstatus();
				}
			}
			}
			try {
				Thread.sleep(1000);
				l_event=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis())+ ": Vehicle ");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
/*
	public void charging(avg[] avgcharge) throws exception_handling.VehicleNotFoundException {
		String startevent, endevent;
		double chargepercent;
		long waittime;
		try {
			for (int i = 0; i < avgcharge.length; i++) {
				if (avgcharge[i] == null) {
					echarge.handleVehicleNotFound();
				}
				stationOccupied = true;
				chargepercent = avgcharge[i].getConsump();
				chargtime = avgcharge[i].chargeBatteryPercentage(chargepercent);
				waittime = TimeUnit.HOURS.toMillis((long) chargtime);
				startevent = (l_event + avgcharge[i].id + " is charging.");
				endevent = (l_event + avgcharge[i].id + " is charged.");
				updateLogFile(,startevent);

				Thread.sleep(waittime); // charging time
				updateLogFile(endevent);
			}

		} catch (Throwable e) {
			System.out.println("Error: " + e.toString());
		}
	}
*/
	public String getChargingstatus() {
		String status;
		if (stationOccupied) {
			status = "Vehicle currently charging";
		} else {
			status = "Station is available";
		}
		return status;
	}

	//TODO------add to GUI
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
