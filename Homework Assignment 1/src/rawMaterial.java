public class rawMaterial{
    public String type;
    public int amount;
    public double[] location = new double[2];
    public rawMaterial(String type, int amount, double[] location){
	this.type = type;
	this.amount = amount;
	this.location = location;
    }
}
