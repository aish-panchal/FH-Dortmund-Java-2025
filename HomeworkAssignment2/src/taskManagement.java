import java.text.SimpleDateFormat;
import java.util.Date;


public class taskManagement {
    //coordinates of the different locations
    static final double warehouse[]= {3,1};
    static final double factory[]= {5,6};
    static final double dispatch[]= {4,5};
    //amount of avg needed based on the amount of tonnes to be transported
    private static final int low_load=5; 
    private static final int medium_load=10;
    private static final int high_load=25;

    public String file;
    private String orderID;
    public Date currentdate;
    public int orderno=1;
    private int op_vehicles=0; //vehicles needed
    public avg vehicles[];
    public rawMaterial ordermaterial;
    public movementVehicle move;
    public chargingStation charge;
    public exception_handling etask = new exception_handling();
    
    public taskManagement(Date date) {
	currentdate = date;
	file= ("log."+new SimpleDateFormat("yyyy-MM-dd").format(currentdate)+".txt");
    }
	
    //creates order id
    public void takeOrder( int load, String order) throws exception_handling.ZeroTonnesException, exception_handling.InvalidOrderException, exception_handling, exception_handling.VehicleNotFoundException {
		
		orderID= ( new SimpleDateFormat("yyyy-MM-dd").format(currentdate)+"Task"+"."+orderno);
		//determine range of the load
		try {
		if(load>100) {
		    op_vehicles=high_load;
		}else if ((load<=100)&&(load>50)) {
		    op_vehicles=medium_load;
		}else if((load<=50)&&(load>0)) {
		    op_vehicles=low_load;
		} else {
			etask.handleNullTonnes();
		}
		// exception_handling.NullTonnesException e = ;
		
		} catch(Throwable e) {
			System.out.println("Error: "+e.toString());
		}
		
		vehicles=new avg[op_vehicles];
		for(int i=0; i<op_vehicles;i++) {
		    vehicles[i]= new avg("avg."+i, 0.25);//id,consump %/h
		    vehicles[i].setActSpeed(5);
		    vehicles[i].avgfile=(new SimpleDateFormat("yyyy-MM-dd").format(currentdate)+vehicles[i].id+".txt");//create/update vehicle file
		    file_ops.createUpdateLog(vehicles[i].avgfile, "Started task.");
		}	
		
		//manage order. Orders can be: {'toFactory', 'toWarehouse'. 'toDelivery'}
		this.manageOrder(order,load);
    }
	
    private void manageOrder(String task, int ton) throws exception_handling, exception_handling.InvalidOrderException, exception_handling.VehicleNotFoundException  {
		Date starttime=new java.util.Date();
		starttime.setTime(currentdate.getTime());
		double overallduration=0;
		String entry;
			
		switch (task) {
		case "toFactory":
			ordermaterial = new rawMaterial("item"+orderno,"raw",ton,warehouse);
		    move = new movStorage(this.orderID, currentdate, factory, vehicles, file, warehouse, "warehouse", "factory",this.ordermaterial);
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
			ordermaterial = new rawMaterial("item "+orderno,"product",ton,factory);
		    move = new movStorage(this.orderID,currentdate,warehouse, vehicles,file,factory,"factory","warehouse",this.ordermaterial);
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
			ordermaterial = new rawMaterial("item "+orderno,"product",ton,warehouse);	
		    move = new movDelivery (this.orderID, currentdate, file, vehicles, warehouse, dispatch,this.ordermaterial);
		    overallduration= (double)(move.timestamp.getTime() - starttime.getTime())/3600000; //in hours
	
		    entry= calculate_date(overallduration, move, this.orderID);
		    file_ops.createUpdateLog(file, entry);
		    currentdate.setTime(move.timestamp.getTime());//update taskmanager time
		    orderno++;
		    if(this.move.hasLowBat) {
		    	 charge = new chargingStation( file, currentdate, this.move.lowbat);    	 
		    }
		    break;
		    
		case null, default:
			etask.handleInvalidOrder();
	}
				
    }

    private static String calculate_date(double overallduration, movementVehicle move, String orderID){
    	return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(move.timestamp)+": "
	    +orderID+" mission completed. Overall duration: "
	    +String.format("%.2f", overallduration)+" hours.";
    }
}
