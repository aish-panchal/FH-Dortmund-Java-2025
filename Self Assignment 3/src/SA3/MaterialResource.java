package SA3;

public class MaterialResource extends NHResource{
    public double[] location;
    private String type;
    private double tons;
    public MaterialResource(double[] location, String type, double tons){
	this.location = location;
	this.type = type;
	this.tons = tons;
    }
    public void change_tons(double change_amount){
	// add the amount (positive or negative) to the tonnage
	tons += change_amount;
    }
    public double tons(){
	return tons;
    }
    public String type(){
	return type;
    }
    
}
