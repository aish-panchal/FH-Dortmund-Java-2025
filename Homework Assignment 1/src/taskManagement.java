package HA1;

import java.text.SimpleDateFormat;
import java.util.Date;

public class taskManagement {
	//coordinates of the different locations
	static final double warehouse[]= {3,1};
	static final double factory[]= {5,6};
	static final double vehicle_storage[]= {0,0};
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

//		file_ops.createUpdateLog(file, "New log file");
		
	}
	
	//creates order id
	public void takeOrder( int load, String order) {
		
		orderID= ( "task."+new SimpleDateFormat("yyyy-MM-dd").format(currentdate)+"."+orderno);
		tonnes =load;		
		//determine range of the load
		if(load>100) {
			op_vehicles=high_load;
			}else if ((load<=100)&&(load>50)) {
				op_vehicles=medium_load;
				}else if(load<=50) {
					op_vehicles=low_load;
					}
		
		//add condition of creation of vehicles according to the existing inventory
		//add condition that if avg's are busy, then take more avgs from storage equipment
		//get avg's needed for the transportation of the tones
		//if(orderno==1) {
			vehicles=new avg[op_vehicles];
			for(int i=0; i<op_vehicles;i++) {
				vehicles[i]= new avg("avg."+i, 0.5); //id,consump
				vehicles[i].setActSpeed(5);
			}	
		//}
		
		start = new double[2];
		end = new double[2];
		
		//add conditions to classify the task
		switch (order) {
		case "toFactory":
			start=warehouse;
			end=factory;
			break;
		case "toWarehouse":
			start= factory;
			end=warehouse;
			break;
		case "toDelivery":
			start=warehouse;
			end=dispatch;
		case "toCharge":
			end= vehicle_storage;
		}
		
		//manage order
		this.manageOrder(order,start,end);
		
	}
	
	private void manageOrder(String task, double[] origin, double[] direction) {
		
		Date starttime=new java.util.Date();
		starttime.setTime(currentdate.getTime());
		double overallduration=0;
		String entry;
		
		switch (task) {
		case "toFactory":

			move = new movStorage(this.orderID,currentdate,direction, vehicles,file,origin,"warehouse","factory");
			overallduration= (double)(move.timestamp.getTime() - starttime.getTime())/3600000; //in hours
			
		//	System.out.println(move.timestamp+": "+this.orderID+" mission completed. Overall duration: "+String.format("%.2f", overallduration)+" hours.");
			entry=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(move.timestamp)+": "+this.orderID+" mission completed. Overall duration: "+String.format("%.2f", overallduration)+" hours.");
			file_ops.createUpdateLog(file, entry);
			currentdate.setTime(move.timestamp.getTime());//update taskmanager time
			orderno++;
			break;
			
		case "toWarehouse":
	
			move = new movStorage(this.orderID,currentdate,direction, vehicles,file,origin,"factory","warehouse");
			overallduration= (double)(move.timestamp.getTime() - starttime.getTime())/3600000; //in hours
			
		//	System.out.println(move.timestamp+": "+this.orderID+" mission completed. Overall duration: "+String.format("%.2f", overallduration)+" hours.");
			entry=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(move.timestamp)+": "+this.orderID+" mission completed. Overall duration: "+String.format("%.2f", overallduration)+" hours.");
			file_ops.createUpdateLog(file, entry);
			currentdate.setTime(move.timestamp.getTime());//update taskmanager time
			orderno++;
			break;
			
		case "toDelivery":
			System.out.println(starttime+": "+this.orderID+" operation started.");
			
			move = new movDelivery (this.orderID, currentdate, file, vehicles, origin, direction);
			overallduration= (double)(move.timestamp.getTime() - starttime.getTime())/3600000; //in hours
			
			//System.out.println(move.timestamp+": "+this.orderID+" mission completed. Overall duration: "+String.format("%.2f", overallduration)+" hours.");
			entry=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(move.timestamp)+": "+this.orderID+" mission completed. Overall duration: "+String.format("%.2f", overallduration)+" hours.");
			file_ops.createUpdateLog(file, entry);
			currentdate.setTime(move.timestamp.getTime());//update taskmanager time
			orderno++;
			break;
			
		case "toCharge":
			System.out.println(starttime+": "+this.orderID+" operation started.");
			charge = new chargingStation();
			break;
		}
				
	}
	
	private void monitorVehicles() {
		
	}
	
	public void manageFiles(boolean newday, int daypassed) {
		
		if(newday) {
			file_ops.archiveLog(file);//archives the logfile
			file_ops.deleteLog(file);//eliminates it from current file
		}
		
	}
}
