package SA2;

public class IOCustomer extends IndustrialOperation{
    private double[] customer = {100,20};
    private double[] warehouse_pos = {0,0};
    private int vehicle_limit = 50;
    public IOCustomer(String Customerid, int tonnes){
	description = "Deliver the refined product to the customer";
	id = Customerid;
	// commission vehicles
	resources = new avg[vehicle_limit];
	for(int i = 0; i < vehicle_limit; i++){
	    resources[i] = new avg(i+"th vehicle", 100, 1);
	    resources[i].setActSpeed(100);
	}
	// fetch all the tonnes of materials from the factory
	out:
	while (tonnes > 0){
	    for(avg vehicle: resources){
		if(tonnes == 0) break out; // check if there is more work to do
		vehicle.changepos(customer); // go to factory
		vehicle.changepos(warehouse_pos); // return with goods
		tonnes -= 1; // tonne is done
	    }
	}
	for(avg vehicle: resources){
	    IOtime += vehicle.overallTime();
	    IOenergy += vehicle.getComsup();
	}
    }
}
