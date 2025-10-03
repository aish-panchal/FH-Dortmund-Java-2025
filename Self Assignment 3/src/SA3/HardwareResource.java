package SA3;

public abstract class HardwareResource extends NHResource{
    protected double[] location;
    public double[] location(){
	return location;
    }
    public void set_location(double[] loc){
	location = loc;
    }
}
