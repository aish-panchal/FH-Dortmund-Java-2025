package SA2;

// this industrial process will encompass the delivery and production of X tonnes of product
public class IndustrialProcess{
    public String id = "";
    private float duration = 0;
    private int energy_consumed = 0;
    private int avg_num = 0;
    private IndustrialOperation[] operations;
    private int tonnes_processed;

    public IndustrialProcess(int product_tonnes, String ProcessID){
	// calculate and call the stuff
	id = ProcessID;
	operations = new IndustrialOperation[3];
	operations[0] = new IOFetchMaterials("fetch " + id , product_tonnes);
	operations[1] = new IOFactory("refine " + id, product_tonnes);
	operations[2] = new IOCustomer("deliver" + id, product_tonnes);
	tonnes_processed = product_tonnes;
	update_energy_avg_time();
    }

    private void update_energy_avg_time(){
	for(IndustrialOperation proc : operations){
	    energy_consumed += proc.IOenergy();
	    duration += proc.IOtime();
	    avg_num += proc.AVGAmount();
	}
    }

    public void ProcessDuration(){
	System.out.println("Process " + id +  " needed " + Math.round(duration) + " minutes");
    }

    public void ProcessResources(){
	System.out.println("Process " + id + " used " + energy_consumed + " kilowatts of energy");
	System.out.println("Process " + id + " used " + avg_num + " AVGs");
    }

    public void ProcessPrintInfo(){
	ProcessDuration();
	ProcessResources();
	System.out.println("Process " + id + " processed " + tonnes_processed + " tonnes\n");
    }

    
}
