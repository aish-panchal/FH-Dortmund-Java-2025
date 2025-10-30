import java.util.Date;

/*locations:
 * Vehicle storage: (0,0)
 * Warehouse: (3,1)
 * Factory: (5,6)
 * Dispatch area in warehouse: (4,5)
 * */

abstract class movementVehicle {
	
    protected double location[]=new double[2];
    protected double destination[]=new double[2];
    
    public String sysfile; 
    public String file_name;
    protected String event;
    protected String taskid;
    protected avg avgs[];
    protected avg lowbat[]; //stores vehicles that need to charge
    public boolean hasLowBat; //battery < 50%
    protected Date timestamp;
    protected storageManagement tonnes;
    protected rawMaterial movingmaterial;
	
    protected boolean status;
    protected static boolean done=true;
    protected static boolean in_progress=false;
	
    abstract void loading(String start);//starts and finishes loading
    abstract void unloading(String end);//starts and finishes unloading
    abstract double[] getlocation(); //get's current location of the avgs
    abstract void movingtolocation(String loc); //start movement from current location to destination
    abstract void updateLog(String update, String x1y1); //add events current and finished to the log file
}
