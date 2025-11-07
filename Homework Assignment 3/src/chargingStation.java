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
	private boolean stationOccupied; // station status
	public String logf;// charging station file
	private String taskfile;// system file
	private String l_event; // event message template
	private double chargtime; // charging time depends on percentage consumed
	private Date accTime;// current date
	private exception_handling echarge = new exception_handling();

	public chargingStation(ArrayList<avg> avgs, ArrayList<avg> toCharge) {
		// initialize log file for all n charging stations
		accTime = new java.util.Date();
		logf = (new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(accTime) + "ChargingStation" + ".txt");
		file_ops.createUpdateLog(logf, "");
		this.availableStations = STATIONS;
		this.avg = avgs;
		this.chargingQ = toCharge;
		charging = new ArrayList<pair>();
		l_event=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(accTime)+ ": Vehicle ");
	}

	@Override
	public void run() {
		System.out.println("In thread");
		String startevent, endevent;
		accTime = new java.util.Date();
		while (true) {
			if (chargingQ.size() > 0) {
				for (int i = 0; i < chargingQ.size() && availableStations > 0; i++) {
					avg avgToBeCharged = chargingQ.get(chargingQ.size() - 1);
					chargingQ.remove(chargingQ.size() - 1);
					double chargepercent = avgToBeCharged.getComsup();
					double chargeTime = avgToBeCharged.chargeBatteryPercentage(chargepercent);
					pair a = new pair(avgToBeCharged, chargeTime);
					charging.add(a);
					availableStations -= 1;
					System.out.println("In charging");//Update log to vehicles started charging
					startevent = (l_event + charging.get(i).avg.id+" is charging.");
					updateLogFile(startevent);
				}
			}
			for(int i=0; i<charging.size();i++) {
				charging.get(i).time -= 0.1;
				if (charging.get(i).time < 0) {
					avg.add(charging.get(i).avg);
					endevent = (l_event + charging.get(i).avg.id+" is charged.");
					updateLogFile(endevent);
					charging.remove(i);
					availableStations += 1; //update log finish charging
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

	//main idea who's tracking the time and who is going to act on it, charging station.
	
	// public chargingStation(int id, String file,Date currentT,avg[]
	// vehicletocharge) throws exception_handling.VehicleNotFoundException {
	// stationid= ("Charging Station "+id);
	// taskfile=file;
	// accTime=currentT;
	// stationOccupied=true;
	// l_event=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(accTime)+"
	// "+stationid+": ");

	// chargingavg = new avg[vehicletocharge.length];
	// for(int i=0;i<vehicletocharge.length;i++) {
	// chargingavg[i]=vehicletocharge[i];
	// }

	// charging(chargingavg);
	// }

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
				chargepercent = avgcharge[i].getComsup();
				chargtime = avgcharge[i].chargeBatteryPercentage(chargepercent);
				waittime = TimeUnit.HOURS.toMillis((long) chargtime);
				startevent = (l_event + avgcharge[i].id + " is charging.");
				endevent = (l_event + avgcharge[i].id + " is charged.");
				updateLogFile(startevent);

				/*
				 * file_ops.createUpdateLog(taskfile, logupdate);
				 * file_ops.createUpdateLog(avgcharge[i].avgfile, logupdate);//update vehicle
				 * file file_ops.createUpdateLog(logf, logupdate);// update charging station
				 * file
				 */
				Thread.sleep(waittime); // charging time
				updateLogFile(endevent);
			}

		} catch (Throwable e) {
			System.out.println("Error: " + e.toString());
		}

		// getStationstatus(avgcharge);
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

	public boolean getStationStatus() {
		return stationOccupied;
	}

	/*
	 * public void getStationstatus(avg[] batstat) { String lupdate;
	 * accTime.setTime(accTime.getTime()+TimeUnit.HOURS.toMillis((long) chargtime));
	 * String endevent=(new
	 * SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(accTime)+": ");
	 * stationOccupied=false; try { for(int i=0;i<batstat.length;i++) {
	 * if(batstat[i]== null) { echarge.handleVehicleNotFound(); }
	 * 
	 * lupdate=(endevent+batstat[i].id+" is charged.");
	 * file_ops.createUpdateLog(taskfile, lupdate);
	 * file_ops.createUpdateLog(batstat[i].avgfile, lupdate);//update vehicle file
	 * file_ops.createUpdateLog(logf, lupdate);// update charging station file }
	 * }catch(Throwable e) {System.out.println("Error: "+e.toString());} }
	 */

	public void updateLogFile(String event) {
		//file_ops.createUpdateLog(taskfile, event);// update overall taskmanager file
		// file_ops.createUpdateLog(batstat[i].avgfile, event);// update vehicle file
		file_ops.createUpdateLog(logf, event);// update charging station file
	}
}
