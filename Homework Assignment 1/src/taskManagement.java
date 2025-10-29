import java.text.SimpleDateFormat;
import java.util.Date;

public class taskManagement {
    //coordinates of the different locations
    static final double warehouse[]= {3,1};
    static final double factory[]= {5,6};
 //   static final double vehicle_storage[]= {0,0};
    static final double dispatch[]= {4,5};
    //amount of avg needed based on the amount of tonnes to be transported
    private static final int low_load=5; 
    private static final int medium_load=10;
    private static final int high_load=25;

    public file_ops log_file;
    public String file;

    private int op_vehicles=0; //vehicles needed
    private avg vehicles[];
    private int tonnes;
    
    private String orderID;
    public Date currentdate;
    private int orderno=1;
    
    private movementVehicle move;
    private chargingStation charge;
    private double[] start;
    private double[] end;
    
    public taskManagement(Date date) {
	currentdate = date;
	file= ("log."+new SimpleDateFormat("yyyy-MM-dd").format(currentdate)+".txt");

	//file_ops.createUpdateLog(file, "New log file");
    }
	
    //creates order id
    public void takeOrder( int load, String order) {
		
	orderID= ( new SimpleDateFormat("yyyy-MM-dd").format(currentdate)+"Task"+"."+orderno);
	tonnes =load;		
	//determine range of the load
	if(load>100) {
	    op_vehicles=high_load;
	}else if ((load<=100)&&(load>50)) {
	    op_vehicles=medium_load;
	}else if(load<=50) {
	    op_vehicles=low_load;
	}
		
	vehicles=new avg[op_vehicles];
	for(int i=0; i<op_vehicles;i++) {
	    vehicles[i]= new avg("avg."+i, 0.25);//id,consump %/h
	    vehicles[i].setActSpeed(5);
	    vehicles[i].avgfile=(new SimpleDateFormat("yyyy-MM-dd").format(currentdate)+vehicles[i].id+".txt");//create/update vehicle file
	    file_ops.createUpdateLog(vehicles[i].avgfile, "Started task.");
	}	
	
		
	start = new double[2];
	end = new double[2];
	
	//manage order. Orders can be: {'toFactory', 'toWarehouse'. 'toDelivery'}
	this.manageOrder(order);
    }
	
    private void manageOrder(String task) {
		
	Date starttime=new java.util.Date();
	starttime.setTime(currentdate.getTime());
	double overallduration=0;
	String entry;
		
	switch (task) {
	case "toFactory":

	    move = new movStorage(this.orderID, currentdate, factory, vehicles, file, warehouse, "warehouse", "factory");
	    overallduration= (double)(move.timestamp.getTime() - starttime.getTime())/3600000; //in hours
			
	    entry=calculate_date(overallduration, move, this.orderID);
	    file_ops.createUpdateLog(file, entry);
	    currentdate.setTime(move.timestamp.getTime());//update taskmanager time
	    orderno++;
	    if(this.move.hasLowBat) {
	    	charge = new chargingStation( file, currentdate, this.move.lowbat);
	    } 
	    break;
			
	case "toWarehouse":
	
	    move = new movStorage(this.orderID,currentdate,warehouse, vehicles,file,factory,"factory","warehouse");
	    overallduration = (double)(move.timestamp.getTime() - starttime.getTime())/3600000; //in hours
			
	    entry = calculate_date(overallduration, move, this.orderID);
	    file_ops.createUpdateLog(file, entry);
	    currentdate.setTime(move.timestamp.getTime());//update taskmanager time
	    orderno++;
	    if(this.move.hasLowBat) {
	    	charge = new chargingStation( file, currentdate, this.move.lowbat);
	    }
	    break;
			
	case "toDelivery":
			
	    move = new movDelivery (this.orderID, currentdate, file, vehicles, warehouse, dispatch);
	    overallduration= (double)(move.timestamp.getTime() - starttime.getTime())/3600000; //in hours

	    entry= calculate_date(overallduration, move, this.orderID);
	    file_ops.createUpdateLog(file, entry);
	    currentdate.setTime(move.timestamp.getTime());//update taskmanager time
	    orderno++;
	    if(this.move.hasLowBat) {
	    	 charge = new chargingStation( file, currentdate, this.move.lowbat);    	 
	    }
	    break;
	}
				
    }

    private static String calculate_date(double overallduration, movementVehicle move, String orderID){
	return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(move.timestamp)+": "
	    +orderID+" mission completed. Overall duration: "
	    +String.format("%.2f", overallduration)+" hours.";
    }
   
    /*
    private void monitorVehicles() {
		
    }
	
    public void manageFiles(boolean newday, int daypassed) {
		
	if(newday) {
	    file_ops.archiveLog(file);//archives the logfile
	    file_ops.deleteLog(file);//eliminates it from current file
	}
		
    }*/
}
