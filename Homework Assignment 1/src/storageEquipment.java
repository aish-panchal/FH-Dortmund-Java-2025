// represents a shelf or some kind of container
public class storageEquipment{
    private double[] location = new double[2];
    private boolean occupied;

    public storageEquipment(double[] location){
	this.location = location;
	this.occupied = false;
    }
    public void load(){
	occupied = true;
    }
    public void unload(){
	occupied = false;
    }
    public boolean is_occupied(){
	// returns true if occupied
	return occupied;
    }
    public double[] equipmentLocation(){
	return location;
    }
    public void setLocation(double[] location){
	this.location = location;
    }
}
