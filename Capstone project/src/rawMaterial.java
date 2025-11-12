public class rawMaterial{
    public String id;
    public String type;
    public int amount;
    public double[] location = new double[2];
    public rawMaterial(String id, String type, int amount, double[] location){
	this.id = id;
	this.type = type;
	this.amount = amount;
	this.location = location;
    }
    
    public String toString() {
    	return "Type: " + type + "\n\nAmount: " + amount + " tonnes\n\nLocation: (" + location[0] + ", " + location[1] + ")";
    }
}
