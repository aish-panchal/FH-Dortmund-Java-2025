import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ConcurrentLinkedQueue;

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
	public ConcurrentLinkedQueue<avg> chargingQ;// avg to charge
	public ConcurrentLinkedQueue<avg> avg;
	public ArrayList<pair> charging;

	public String logf;// charging station file
	private String taskfile;// system file
	private String l_event; // event message template
	private Date accTime;// current date
	private exception_handling echarge = new exception_handling();

	public chargingStation(String taskfile, ConcurrentLinkedQueue<avg> vehicles,
			ConcurrentLinkedQueue<avg> vehiclesInNeedOfCharging) {
		// initialize log file for all n charging stations
		accTime = new java.util.Date();
		this.taskfile = taskfile;
		logf = (new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(accTime) + "ChargingStation" + ".txt");
		file_ops.createUpdateLog(logf, "");
		this.availableStations = STATIONS;
		this.avg = vehicles;
		this.chargingQ = vehiclesInNeedOfCharging;
		charging = new ArrayList<pair>();
		l_event = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(accTime) + ": Vehicle ");
	}

	@Override
	public void run() {
		try {
			chargeManagement();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void chargeManagement() throws InterruptedException {
		String startevent, endevent;
		accTime = new java.util.Date();
		while (true) {

			if (chargingQ.size() > 0) {
				for (int i = 0; (i < chargingQ.size()) && (availableStations > 0); i++) {
					avg avgToBeCharged = chargingQ.poll();
					double chargepercent = avgToBeCharged.getConsump();
					double chargeTime = avgToBeCharged.chargeBatteryPercentage(chargepercent);

					pair a = new pair(avgToBeCharged, chargeTime);
					charging.add(a);
					availableStations -= 1;
					Thread.sleep(500);
					startevent = (l_event + charging.get(charging.size() - 1).avg.id + " is charging.");
					updateLogFile(charging.get(charging.size() - 1).avg.avgfile, startevent);
					getStationStatus();
				}
			}

			for (int i = 0; i < charging.size(); i++) {
				charging.get(i).time -= 0.1;

				if (charging.get(i).time <= 0) {
					avg.add(charging.get(i).avg);
					Thread.sleep(500);
					endevent = (l_event + charging.get(i).avg.id + " is charged.");
					updateLogFile(charging.get(i).avg.avgfile, endevent);
					charging.remove(i);
					availableStations += 1; // update log finish charging
					getStationStatus();
				}
				getStationStatus();
			}
			try {
				Thread.sleep(50);
				l_event = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis())
						+ ": Vehicle ");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public String getStationStatus() {
		return ("Currently charging: " + charging.size() + " vehicles.\n\n" + "Stations available: "
				+ (this.availableStations));
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
