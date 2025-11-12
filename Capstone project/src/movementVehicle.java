import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;

/*locations:
 * Vehicle storage: (0,0)
 * Warehouse: (3,1)
 * Factory: (5,6)
 * Dispatch area in warehouse: (4,5)
 * */

abstract class movementVehicle implements Runnable {

	protected double location[] = new double[2];
	protected double destination[] = new double[2];
	protected String end_destination[] = { "Factory", "Warehouse", "Dispatch area" };
	protected ArrayList<avg> avgsToBeUsed;
	protected ConcurrentLinkedQueue<avg> chargeQ; // stores vehicles to be charged
	protected ConcurrentLinkedQueue<avg> readyVehicleQ;
	protected Semaphore movementMutex;

	public String sysfile;
	public String file_name;
	protected String event;
	protected String taskid;
	protected Date timestamp;
	protected double overallduration;
	
	protected rawMaterial movingmaterial;
	protected exception_handling emov = new exception_handling();
	protected storageManagement store;

	protected boolean status;
	protected static boolean done = true;
	protected static boolean in_progress = false;

	abstract void loading(int start);// starts and finishes loading

	abstract void unloading(int end) throws InterruptedException;// starts and finishes unloading

	abstract double[] getlocation(); // get's current location of the avgs

	abstract void movingtolocation(int loc); // start movement from current location to destination

	abstract void updateLog(String update, String x1y1); // add events current and finished to the log file
}
