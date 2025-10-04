package SA3;

public class IOFactory extends IndustrialOperation{
    private double[] factory = {2,3};
    private double time_per_tonne = 10;
    private double[] warehouse_pos = {0,0};
    public IOFactory(String Factoryid, int tonnes){
	description = "take materials tonne by tonne to the factory, and wait for the factory to process them before returning with them";
	id = Factoryid;
	resources = new avg[6];
	for(int i = 0; i < 6; i++){
	    resources[i] = new avg(i+"th vehicle", 100, 1, 10);
	    resources[i].setActSpeed(100);
	}
	// fetch all the tonnes of materials from the factory
	out:
	while (tonnes > 0){
	    for(avg vehicle: resources){
		if(tonnes == 0) break out; // check if there is more work to do
		vehicle.changepos(factory); // go to factory
		vehicle.wait_at_pos(time_per_tonne); // wait 
		vehicle.changepos(warehouse_pos); // return with goods
		tonnes -= 1; // tonne is done
	    }
	}
	for(avg vehicle: resources){
	    IOtime += vehicle.overallTime();
	    IOenergy += vehicle.getComsup();
	}

	transport = new TransportOp (resources,IOenergy);
    Hoperation = new HumanOp("Loader",10,4);
    	
	transport.operationtime(IOtime);
    	
    	IOtime=Hoperation.getoptime()+transport.getoptime();
    	IOcost=transport.operationalCost()+Hoperation.operationalCost();
    }
}
