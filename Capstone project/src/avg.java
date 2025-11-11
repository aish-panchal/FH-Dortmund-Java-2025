/*
 * Constructs AVGs and returns it data
 * changes: made maxspeed and chargeR a fixed value
 */

//add statues: loaded/unloaded

public class avg{

    public String id;
    private static float maxspeed=100; // in km/h
    public double actspeed; // in km/h
    private static double consumptionRate; // %/h
    private static double chargeRate=50; // %/h
    private double batteryload; // in %
    private double[] pos = new double[2]; // For initial position in the warehouse (x,y coordinates)
    private double overallTime;
    private double overallConsum;
    private double dis;
    public String avgfile;
    
    public avg (String type, double comsup){//(String type, float maxsp, double comsup, double chargerate){ // AVG constructor
	id=type;
	//	maxspeed=maxsp; // in km/h
	consumptionRate=comsup; // in %/h
	//chargeRate=chargerate; // %/h
	actspeed=0; // Always starting at rest (in km/h)
	batteryload=1; // 100% from start
	pos[0]=0; // x-position
	pos[1]=0; // y-position
	
	overallTime=0;
	overallConsum=0;
    }
	
    public void setActSpeed(double speed){ // Sets the actual speed of the vehicle
	if (speed <= maxspeed && speed > 0) {
	    actspeed=speed;
	} else { 
	    actspeed = maxspeed;
	}
    }
   
    //need to change this into minutes
	
    public double[] changepos (double[] p2){ // change of position entitles a battery consumption and a distance traveled
	double distance=0;
	double time = 0;
	double batteryConsumed = 0;
		
	//distance = Math.abs(pos[0] - p2[0]) + Math.abs(pos[1] - p2[1]); // Actual distance travelled along x AND y
	distance = Math.sqrt(Math.pow((pos[0] - p2[0]),2)+Math.pow((pos[1] - p2[1]),2));
	time =(distance / actspeed); // time taken to cover the distance at actual speed
	batteryConsumed = consumptionRate * time; // Battery consumed in % for the time the AGV runs
	batteryload -= batteryConsumed; // remaining battery level
	pos[0] = p2[0]; // new position of the AGV
	pos[1] = p2[1];

	//chargeBatteryPercentage(batteryConsumed);
	overallTime = time; // calculating overall time consumed for the Iop by this AGV
	overallConsum += batteryConsumed; // calculating overall battery consumed for the Iop by this AGV
	//	dis=distance;
	return pos;
    }

    public double chargeBatteryPercentage(double amount){
	double time = (amount*100) / chargeRate;
	//	overallTime += time;
	batteryload = 1;
	overallConsum=0;
	return time;
	// time is in hours, and can be fractional
    }
   /*
    public void chargeBattery(double chargeHours){
	// charge batter for _time_
	batteryload += chargeRate * chargeHours;
	if(batteryload > 100){
	    batteryload = 100;
	}
	//overallTime += chargeHours;
    }*/

    public void wait_at_pos(double time){
	//chargeBattery(time);
	double hours=time/60;
	batteryload-= (consumptionRate*hours);
	//overallConsum+= (consumptionRate*hours);
    }

    public double overallTime(){
   	return overallTime*60;//minutes
    }
    /* public double getdistance(){
       return dis;
       }
    */
    public double getConsump(){ // get the overall battery consumption 
	// Assumption here is for the capacity of the battery to be 50 kWh
   	return (1-overallConsum); //remaining battery
    }
    
    public String toString() {
    	return id;
    }
    
    public String getInfo() {
    	return "ID: " + id + "\n\n" + "Battery Load: " + 
    			(int) (batteryload * 100) + "%\n\n" + "Position: (" + pos[0] + ", " + 
    			pos[1] + ")\n";
    }
	
}
