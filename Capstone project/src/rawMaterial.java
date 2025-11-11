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
}
