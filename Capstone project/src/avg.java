/* Constructs AVGs and returns it data
 */

//add status: loaded/unloaded
public class avg {

	public String id;
	private static float maxspeed = 100; // km/h
	public double actspeed; // km/h
	private static double consumptionRate; // %/h
	private static double chargeRate = 50; // %/h
	private double batteryload; // %
	private double[] pos = new double[2]; // For initial position in the warehouse (x,y coordinates)
	private double overallTime;
	private double overallConsum;
	public String avgfile;

	// TODO--------convert timings into minutes for simulation

	public avg(String type, double comsup) {
		id = type;
		consumptionRate = comsup; // in %/h
		actspeed = 0; // Always starting at rest (in km/h)
		batteryload = 1; // 100% from start
		pos[0] = 0; // x-position
		pos[1] = 0; // y-position

		overallTime = 0;
		overallConsum = 0;
	}

	public void setActSpeed(double speed) { // Sets the actual speed of the vehicle
		if (speed <= maxspeed && speed > 0) {
			actspeed = speed;
		} else {
			actspeed = maxspeed;
		}
	}

	// changes position, entitles a battery consumption and a distance traveled
	public double[] changepos(double[] p2) { 
		double distance = 0;
		double time = 0;
		double batteryConsumed = 0;

		// distance traveled along x,y
		distance = Math.sqrt(Math.pow((pos[0] - p2[0]), 2) + Math.pow((pos[1] - p2[1]), 2));
		time = (distance / actspeed); // time taken to cover the distance at actual speed
		batteryConsumed = consumptionRate * time; // Battery consumed in % 
		batteryload -= batteryConsumed; // remaining battery level
		// new position of the AGV
		pos[0] = p2[0]; 
		pos[1] = p2[1];

		overallTime = time; // h; time of journey
		overallConsum += batteryConsumed; // calculating overall battery consumed for the Iop by this AGV
		return pos;
	}

	public double chargeBatteryPercentage(double amount) {
		double time = (amount * 100) / chargeRate;//h
		// overallTime += time;
		batteryload +=amount;
		overallConsum = 0;
		return time;//hours
	}

	public void wait_at_pos(double time) {
		// chargeBattery(time);
		double hours = time / 60;
		batteryload -= (consumptionRate * hours);
		overallConsum+= (consumptionRate*hours);
	}

	public double overallTime() {
		return overallTime * 60;// minutes
	}

	public double getConsump() { // get the overall battery consumption
		// Assumption here is for the capacity of the battery to be 50 kWh
		if(overallConsum>1) {
			overallConsum=1;
		}
		return (overallConsum); // % consumed
	}

	public String toString() {
		return id;
	}

	public String getInfo() {
		return "ID: " + id + "\n\n" + "Battery Load: " + (int) (batteryload * 100) + "%\n\n" + "Position: (" + pos[0]
				+ ", " + pos[1] + ")\n";
	}

}
