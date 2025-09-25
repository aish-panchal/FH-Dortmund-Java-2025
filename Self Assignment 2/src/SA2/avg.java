package SA2;
/*
 * Constructs AVGs and returns it data
 */

public class avg {
	
	private String id;
	private float maxspeed; // in km/h
	private double actspeed; // in km/h
	private double consumptionRate; // %/h
	private double chargeTime; // Time in hours
	private double batteryload; // in %
	private double[] pos = new double[2]; // For initial position in the warehouse (x,y coordinates)
	private double overallTime;
	private double overallConsum;
	
	
	
	// setter
	public avg (String type, float maxsp, double comsup) // AVG constructor
	{
		id=type;
		maxspeed=maxsp; // in km/h
		consumptionRate=comsup; // in %/h
		chargeTime=4; // time in hours to go from 0 to 100 %
		actspeed=0; // Always starting at rest (in km/h)
		batteryload=100; // 100% from start
		pos[0]=0; // x-position
		pos[1]=0; // y-position
	}
	
	public void setActSpeed(double speed) // Sets the actual speed of the vehicle
	{
		if (speed <= maxspeed && speed > 0) {
			actspeed=speed;
		} else { 
			actspeed = maxspeed;
		}
	}
	
	public double[] changepos (double[] p2) // change of position entitles a battery consumption and a distance traveled
	{
		double distance=0;
		double time = 0;
		double batteryConsumed = 0;
		
		distance = Math.abs(pos[0] - p2[0]) + Math.abs(pos[1] - p2[1]); // Actual distance travelled along x AND y
		time = distance / actspeed; // time taken to cover the distance at actual speed
		batteryConsumed = consumptionRate * time; // Battery consumed in % for the time the AGV runs
		batteryload = batteryload - batteryConsumed; // remaining battery level
		pos[0] = p2[0]; // new position of the AGV
		pos[1] = p2[1];
		
		overallTime += time; // calcuating overall time consumed for the Iop by this AGV
		overallConsum += batteryConsumed; // calcuating overall battery consumed for the Iop by this AGV
		
		return pos;
	}
	
	//getters
	
	public double getmaxSpeed() // returns the max speed of the AGV
	{
		return maxspeed;
	}
	
	public double getactSpeed() // returns the actual speed of the AGV
	{
		return actspeed;
	}
	
	public double getComsup() // get the overall battery consumption 
	// Assumption here is for the capacity of the battery to be 50 kWh
	{
		return ((overallConsum) * 50 / 100);
	}
	
	public double getchargeTime() // get charge time in hours based on amount of battery to be charged
	{
		double batteryConsumed = 100 - batteryload;
		return ((batteryConsumed * 4) / 100);
	}
	
	public double getbatteryLoad() // returns remaining battery level in the AGV
	{
		return batteryload;
	}
	
	public double[] getpos() // returns current position of the AGV
	{
		return pos;
	}
	
	
	
}
