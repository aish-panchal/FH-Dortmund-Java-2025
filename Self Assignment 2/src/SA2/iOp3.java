package SA2;

public class iOp3 extends iOp{

	private String id;
	private String description;
	private avg[] resources;
	private float optime;
	private float openergy;
	
	private double[] factorypos= {1,1}; //position of the factory
	
	avg avg1 = new avg("",0,0); //id,max speed, consumption (kw/h)
	
	//setter
	public iOp3 ()
	{
		id= "Customer";
		description = "This operation handles the materials from the factory to the customer";
		optime=0;
		openergy=0;
	}
	
}
