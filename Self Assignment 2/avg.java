package SA2;
/*
 * Constructs AVGs and returns it data
 */

public class avg {
	
	private String id;
	private double maxspeed;
	private double actspeed;
	private double consumptionRate;
	private double chargeTime; //Time
	private double batteryload; 
	//private double[] pos= {0,0};// initial position (in the warehouse)
	
	
	//setter
	public avg (String type, float maxsp, double comsup)
	{
		id= type;
		maxspeed=maxsp;
		consumptionRate=comsup;
		chargeTime=4; //hours Time
		actspeed=0;
		batteryload=100; //100% from start
		//pos= (0,0);//position
	}
	
	public void setActSpeed(double speed)
	{
		actspeed=speed;
	}
	
	public double changepos (double[] p2) // change of position entitles a battery consumption and a distance traveled
	{
		double distance=0;
		distance = 0;//math
		batteryload =0;//math
		//pos=p2; //new position
		
		return distance;
	}
	
	//getters
	
	public double getmaxSpeed()
	{
		return maxspeed;
	}
	
	public double getactSpeed()
	{
		return actspeed;
	}
	
	public double getComsup()
	{
		return consumptionRate;
	}
	
	public double getchargeTime()
	{
		return chargeTime;
	}
	
	public double getbatteryLoad()
	{
		return batteryload;
	}
	
	/*public double[] getpos()
	{
		return pos;
	}*/
	
	
	
}
