package SA3;

public class IndustrialOperation{
        public String id;
	public String description;
	public avg[] resources; //stores AVGS needed in this operation
	public float IOtime;
	public float IOenergy;
	public float IOcost;
	public HumanOp Hoperation; //employess in the operation
	public TransportOp transport;
    
    public float IOtime(){
	return IOtime;
    }
    
    public float IOenergy(){
	return IOenergy;
    }

    public int AVGAmount(){
	return resources.length;
    }
	public float getOpCost()
    {
    	return IOcost;
    }
}
