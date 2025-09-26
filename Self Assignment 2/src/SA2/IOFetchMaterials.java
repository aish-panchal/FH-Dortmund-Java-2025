package SA2;

public class IOFetchMaterials extends IndustrialOperation{
    private double[] mine_pos = {5,10};
    private double[] warehouse_pos = {0,0};
    public IOFetchMaterials(String Fetchid, int tonnes){
	description = "fetch materials from the mine to the warehouse, the mine can only handle 2 AVGs to load/unload";
	id = Fetchid;
	resources = new avg[2];
	resources[0] = new avg("first vehicle", 100, 1);
	resources[1] = new avg("second vehicle", 100, 1);
	// fetch all the tonnes of materials from the factory
	for(int i = tonnes; i >= 0; i--){
	    if (i % 2 == 0){
		resources[0].changepos(mine_pos);
		resources[0].changepos(warehouse_pos);
	    }else{
		resources[1].changepos(mine_pos);
		resources[1].changepos(warehouse_pos);
	    }
	}
	IOtime += resources[0].overallTime() + resources[1].overallTime();
	IOenergy += resources[0].overallConsumption() + resources[1].overallConsumption();
    }
}
