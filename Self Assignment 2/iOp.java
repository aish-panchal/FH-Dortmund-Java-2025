package SA2;

public class iOp {
	
	private String id;
	private String description;
	private avg[] resources; //stores AVGS needed in this operation
	private float optime;
	private float openergy;
	
	private double[] factorypos= {1,1}; //position of the factory
	
	
	//setter
	public iOp ()
	{
		id= "Fetch";
		description = "This operation fetch raw materials";
		optime=0;
		openergy=0;
	}
	
	public avg[] setavgUsed()
	{
		avg avg1 = new avg("",0,0); //id,max speed, consumption (kw/h)
		
		return resources;
	}
	
	/*
	//getters
	
	
	public float getopDuration()
	{
		return optime;
	}
	
	public float getopEnergy()
	{
		return openergy;
	}
	*/
	
	public int getavgUsed()
	{
		int noavg= resources.length;//number of AVGs used in the operation
		
		return noavg;
	}
	
	public void getAll()
	{
		System.out.println("Operation: " + id);
		System.out.println("Operation's description: " + description);
		System.out.println("This operation requires: " + resources.length + "AVGs, ");
		System.out.println(optime + "hours, and");
		System.out.println(openergy);
	}

}
